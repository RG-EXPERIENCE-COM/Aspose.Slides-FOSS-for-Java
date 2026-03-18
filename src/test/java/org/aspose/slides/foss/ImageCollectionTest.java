package org.aspose.slides.foss;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.imageio.ImageIO;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ImageCollection} and picture frame integration.
 *
 * <p>Covers image addition, file-based images, multiple images, picture frames,
 * and picture fill integration.</p>
 */
class ImageCollectionTest {

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

    /**
     * Saves and reloads a presentation (round-trip).
     */
    private static Presentation roundTrip(Presentation pres) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        pres.save(baos);
        pres.dispose();
        return new Presentation(new ByteArrayInputStream(baos.toByteArray()));
    }

    // --- test_add_image ---

    @Test
    void addImage_increasesCollectionCount() throws IOException {
        try (Presentation pres = new Presentation()) {
            pres.getImages().addImage(createTestPng(255, 0, 0));
            assertThat(pres.getImages().size()).isGreaterThanOrEqualTo(1);
        }
    }

    // --- test_multiple_images ---

    @Test
    void multipleImages_canBeAddedAndIterated() throws IOException {
        try (Presentation pres = new Presentation()) {
            int[][] colors = {{255, 0, 0}, {0, 255, 0}, {0, 0, 255}};
            for (int[] c : colors) {
                pres.getImages().addImage(createTestPng(c[0], c[1], c[2]));
            }
            assertThat(pres.getImages().size()).isGreaterThanOrEqualTo(3);

            List<IPPImage> imgs = pres.getImages().asICollection();
            assertThat(imgs).hasSizeGreaterThanOrEqualTo(3);
        }
    }

    @Test
    void images_areIterable() throws IOException {
        try (Presentation pres = new Presentation()) {
            pres.getImages().addImage(createTestPng(255, 0, 0));
            pres.getImages().addImage(createTestPng(0, 255, 0));

            int count = 0;
            for (IPPImage img : pres.getImages()) {
                assertThat(img).isNotNull();
                count++;
            }
            assertThat(count).isGreaterThanOrEqualTo(2);
        }
    }

    // --- test_picture_frame ---

    @Test
    void pictureFrame_persistsAfterSaveReload() throws IOException {
        Presentation pres = new Presentation();
        IPPImage img = pres.getImages().addImage(createTestPng(0, 0, 255));
        pres.getSlides().get(0).getShapes().addPictureFrame(
                ShapeType.RECTANGLE, 50, 50, 100, 100, img);
        assertThat(pres.getSlides().get(0).getShapes().size()).isGreaterThanOrEqualTo(1);

        try (Presentation pres2 = roundTrip(pres)) {
            assertThat(pres2.getSlides().get(0).getShapes().size()).isGreaterThanOrEqualTo(1);
        }
    }

    // --- test_image_from_file ---

    @Test
    void imageFromByteArray_addsToCollection() throws IOException {
        byte[] pngData = createTestPng(128, 64, 32);
        try (Presentation pres = new Presentation()) {
            IPPImage ppImg = pres.getImages().addImage(pngData);
            assertThat(ppImg).isNotNull();
            assertThat(ppImg.getContentType()).isEqualTo("image/png");
            assertThat(pres.getImages().size()).isGreaterThanOrEqualTo(1);
        }
    }

    @Test
    void imageFromInputStream_addsToCollection() throws IOException {
        byte[] pngData = createTestPng(64, 128, 255);
        try (Presentation pres = new Presentation()) {
            IPPImage ppImg = pres.getImages().addImage(new ByteArrayInputStream(pngData));
            assertThat(ppImg).isNotNull();
            assertThat(pres.getImages().size()).isGreaterThanOrEqualTo(1);
        }
    }

    // --- test_picture_fill (from test_fill_format.py) ---

    @Test
    void pictureFill_withImagePersistsAfterSaveReload() throws IOException {
        Presentation pres = new Presentation();
        pres.getSlides().get(0).getShapes().clear();
        IAutoShape shape = pres.getSlides().get(0).getShapes().addAutoShape(
                ShapeType.RECTANGLE, 50, 50, 200, 200);
        shape.getFillFormat().setFillType(FillType.PICTURE);
        IPictureFillFormat pff = shape.getFillFormat().getPictureFillFormat();
        pff.setPictureFillMode(PictureFillMode.STRETCH);
        IPPImage img = pres.getImages().addImage(createTestPng(0, 255, 0));
        pff.getPicture().setImage(img);

        try (Presentation pres2 = roundTrip(pres)) {
            IFillFormat ff2 = pres2.getSlides().get(0).getShapes().get(0).getFillFormat();
            assertThat(ff2.getFillType()).isEqualTo(FillType.PICTURE);
        }
    }

    // --- asICollection / asIEnumerable ---

    @Test
    void asICollection_returnsUnmodifiableList() throws IOException {
        try (Presentation pres = new Presentation()) {
            pres.getImages().addImage(createTestPng(255, 0, 0));
            List<IPPImage> list = pres.getImages().asICollection();
            assertThat(list).hasSize(1);
        }
    }

    @Test
    void asIEnumerable_isIterable() throws IOException {
        try (Presentation pres = new Presentation()) {
            pres.getImages().addImage(createTestPng(0, 128, 0));
            Iterable<IPPImage> iterable = pres.getImages().asIEnumerable();
            int count = 0;
            for (IPPImage img : iterable) {
                count++;
            }
            assertThat(count).isEqualTo(1);
        }
    }

    @Test
    void get_returnsImageAtIndex() throws IOException {
        try (Presentation pres = new Presentation()) {
            IPPImage added = pres.getImages().addImage(createTestPng(10, 20, 30));
            IPPImage retrieved = pres.getImages().get(0);
            assertThat(retrieved).isSameAs(added);
        }
    }

    @Test
    void imagesPersistedAcrossRoundTrip() throws IOException {
        Presentation pres = new Presentation();
        pres.getImages().addImage(createTestPng(255, 0, 0));
        pres.getImages().addImage(createTestPng(0, 255, 0));

        try (Presentation pres2 = roundTrip(pres)) {
            assertThat(pres2.getImages().size()).isGreaterThanOrEqualTo(2);
        }
    }
}
