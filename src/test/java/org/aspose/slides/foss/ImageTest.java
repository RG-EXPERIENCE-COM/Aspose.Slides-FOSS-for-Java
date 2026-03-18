package org.aspose.slides.foss;

import org.aspose.slides.foss.drawing.Size;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for the {@link Image} class.
 */
class ImageTest {

    private byte[] pngData;
    private Image image;

    /** Creates a minimal 100x80 PNG in memory for testing. */
    @BeforeEach
    void setUp() throws IOException {
        BufferedImage buffered = new BufferedImage(100, 80, BufferedImage.TYPE_INT_ARGB);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(buffered, "png", baos);
        pngData = baos.toByteArray();

        image = new Image();
        image.initInternal(pngData, "image/png");
    }

    /**
     * size has positive width and height.
     */
    @Test
    void sizeHasPositiveWidthAndHeight() {
        Size size = image.getSize();
        assertThat(size.getWidth()).isGreaterThan(0);
        assertThat(size.getHeight()).isGreaterThan(0);
    }

    /**
     * width and height return
     * expected values matching the source image dimensions.
     */
    @Test
    void widthAndHeightMatchSourceDimensions() {
        assertThat(image.getWidth()).isEqualTo(100);
        assertThat(image.getHeight()).isEqualTo(80);
    }

    /**
     * Width and height are consistent with the size object.
     */
    @Test
    void widthAndHeightConsistentWithSize() {
        assertThat(image.getWidth()).isEqualTo(image.getSize().getWidth());
        assertThat(image.getHeight()).isEqualTo(image.getSize().getHeight());
    }

    /**
     * saving to a stream produces
     * a non-empty buffer.
     */
    @Test
    void saveToStreamProducesNonEmptyOutput() {
        var buf = new ByteArrayOutputStream();
        image.save(buf, "png");
        assertThat(buf.size()).isGreaterThan(0);
    }

    /**
     * saving to a
     * stream with quality produces non-empty output.
     */
    @Test
    void saveToStreamWithQualityProducesNonEmptyOutput() {
        var buf = new ByteArrayOutputStream();
        image.save(buf, "png", 85);
        assertThat(buf.size()).isGreaterThan(0);
    }

    /**
     * image width and height are greater than zero
     * after initialization with valid image data.
     */
    @Test
    void dimensionsArePositiveAfterInit() {
        assertThat(image.getWidth()).isGreaterThan(0);
        assertThat(image.getHeight()).isGreaterThan(0);
    }

    /**
     * image data persists correctly
     * when saved to a file and reloaded.
     */
    @Test
    void saveToFilePersistsData(@TempDir Path tempDir) throws IOException {
        Path outFile = tempDir.resolve("output.png");
        image.save(outFile.toString());

        byte[] reloaded = Files.readAllBytes(outFile);
        assertThat(reloaded).isEqualTo(pngData);
    }

    /**
     * content type and raw data are
     * preserved after initialization.
     */
    @Test
    void contentTypeAndDataPreserved() {
        assertThat(image.getContentType()).isEqualTo("image/png");
        assertThat(image.getData()).isEqualTo(pngData);
    }

    /**
     * Image implements AutoCloseable and can be used with try-with-resources.
     */
    @Test
    void canBeUsedWithTryWithResources() throws Exception {
        try (Image img = new Image()) {
            img.initInternal(pngData, "image/png");
            assertThat(img.getWidth()).isEqualTo(100);
        }
    }

    /**
     * Closing is idempotent — calling close() twice must not throw.
     */
    @Test
    void closeIsIdempotent() {
        image.close();
        image.close(); // second call should be harmless
    }
}
