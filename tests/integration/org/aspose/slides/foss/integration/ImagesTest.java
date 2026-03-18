package org.aspose.slides.foss.integration;
import org.aspose.slides.foss.*;

import org.aspose.slides.foss.export.SaveFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Integration tests for ImageCollection and PictureFrame.
 */
class ImagesTest implements AutoCloseable {

    @TempDir
    Path tempDir;

    @Override
    public void close() {
        // TempDir handles cleanup
    }

    /**
     * Saves a Presentation to a
     * temporary file, disposes the original, and reopens from that file.
     */
    private Presentation saveAndReopen(Presentation pres) throws IOException {
        String path = tempDir.resolve("roundtrip.pptx").toString();
        pres.save(path, SaveFormat.PPTX);
        pres.dispose();
        return new Presentation(path);
    }

    /**
     * Creates a minimal 10x10 PNG with the given RGB color.
     */
    private static byte[] createTestPng(int r, int g, int b) throws IOException {
        BufferedImage buffered = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        int argb = (255 << 24) | (r << 16) | (g << 8) | b;
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                buffered.setRGB(x, y, argb);
            }
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(buffered, "png", baos);
        return baos.toByteArray();
    }

    // --- test_add_image ---

    @Test
    void testAddImage() throws IOException {
        try (Presentation pres = new Presentation()) {
            pres.getImages().addImage(createTestPng(255, 0, 0));
            assertThat(pres.getImages().size()).isGreaterThanOrEqualTo(1);
        }
    }

    // --- test_multiple_images ---

    @Test
    void testMultipleImages() throws IOException {
        try (Presentation pres = new Presentation()) {
            int[][] colors = {{255, 0, 0}, {0, 255, 0}, {0, 0, 255}};
            for (int[] c : colors) {
                pres.getImages().addImage(createTestPng(c[0], c[1], c[2]));
            }
            assertThat(pres.getImages().size()).isGreaterThanOrEqualTo(3);

            List<IPPImage> imgs = new ArrayList<>();
            for (IPPImage img : pres.getImages()) {
                imgs.add(img);
            }
            assertThat(imgs).hasSizeGreaterThanOrEqualTo(3);
        }
    }

    // --- test_picture_frame ---

    @Test
    void testPictureFrame() throws IOException {
        try (var pres = new Presentation()) {
            IPPImage img = pres.getImages().addImage(createTestPng(0, 0, 255));
            pres.getSlides().get(0).getShapes().addPictureFrame(
                    ShapeType.RECTANGLE, 50, 50, 100, 100, img);
            assertThat(pres.getSlides().get(0).getShapes().size()).isGreaterThanOrEqualTo(1);

            try (var pres2 = saveAndReopen(pres)) {
                assertThat(pres2.getSlides().get(0).getShapes().size()).isGreaterThanOrEqualTo(1);
            }
        }
    }

    // --- test_image_from_file ---

    @Test
    void testImageFromFile() throws IOException {
        Path testDataDir = Path.of("tests", "test_data");
        Path imgPath = testDataDir.resolve("lotus.png");
        assumeTrue(Files.exists(imgPath), "lotus.png not in test_data");

        try (Presentation pres = new Presentation()) {
            byte[] imgData = Files.readAllBytes(imgPath);
            IPPImage ppImg = pres.getImages().addImage(imgData);
            pres.getSlides().get(0).getShapes().addPictureFrame(
                    ShapeType.RECTANGLE, 50, 50, 200, 200, ppImg);
            assertThat(pres.getSlides().get(0).getShapes().size()).isGreaterThanOrEqualTo(1);
        }
    }
}
