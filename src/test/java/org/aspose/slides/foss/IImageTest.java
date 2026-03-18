package org.aspose.slides.foss;

import org.aspose.slides.foss.drawing.Size;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for the {@link IImage} interface contract.
 *
 * <p>Uses a minimal stub implementation to verify the interface
 * properties and save behaviour.</p>
 */
class IImageTest {

    /** Minimal concrete implementation for testing the interface contract. */
    private static final class StubImage implements IImage {

        private final int width;
        private final int height;

        StubImage(int width, int height) {
            this.width = width;
            this.height = height;
        }

        @Override
        public Size getSize() {
            return new Size(width, height);
        }

        @Override
        public int getWidth() {
            return width;
        }

        @Override
        public int getHeight() {
            return height;
        }

        @Override
        public void save(String filename) {
            // stub: no-op
        }

        @Override
        public void save(String filename, String format) {
            // stub: no-op
        }

        @Override
        public void save(OutputStream stream, String format) {
            try {
                stream.write(new byte[]{0x42}); // write a marker byte
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void save(String filename, String format, int quality) {
            // stub: no-op
        }

        @Override
        public void save(OutputStream stream, String format, int quality) {
            try {
                stream.write(new byte[]{0x42});
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void close() {
            // stub: no-op
        }
    }

    private IImage image;

    @BeforeEach
    void setUp() {
        image = new StubImage(640, 480);
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
     * width and height properties
     * return expected values.
     */
    @Test
    void widthAndHeightMatchExpectedValues() {
        assertThat(image.getWidth()).isEqualTo(640);
        assertThat(image.getHeight()).isEqualTo(480);
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
     * stream with quality produces a non-empty buffer.
     */
    @Test
    void saveToStreamWithQualityProducesNonEmptyOutput() {
        var buf = new ByteArrayOutputStream();
        image.save(buf, "jpeg", 85);
        assertThat(buf.size()).isGreaterThan(0);
    }

    /**
     * IImage extends AutoCloseable and can be used with try-with-resources.
     */
    @Test
    void canBeUsedWithTryWithResources() throws Exception {
        try (IImage img = new StubImage(100, 100)) {
            assertThat(img.getWidth()).isEqualTo(100);
        }
    }
}
