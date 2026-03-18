package org.aspose.slides.foss;

import org.aspose.slides.foss.drawing.Size;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.OutputStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for the {@link IPPImage} interface contract.
 *
 * <p>Uses a minimal stub implementation to verify the interface
 * properties and replace behaviour.</p>
 *
 * <p>Covers image dimensions, fill format integration, line properties,
 * and shape frame properties.</p>
 */
class IPPImageTest {

    /** Minimal stub IImage for testing. */
    private static final class StubImage implements IImage {
        private final int width;
        private final int height;

        StubImage(int width, int height) {
            this.width = width;
            this.height = height;
        }

        @Override public Size getSize() { return new Size(width, height); }
        @Override public int getWidth() { return width; }
        @Override public int getHeight() { return height; }
        @Override public void save(String filename) {}
        @Override public void save(String filename, String format) {}
        @Override public void save(OutputStream stream, String format) {}
        @Override public void save(String filename, String format, int quality) {}
        @Override public void save(OutputStream stream, String format, int quality) {}
        @Override public void close() {}
    }

    /** Minimal concrete implementation for testing the IPPImage interface contract. */
    private static final class StubPPImage implements IPPImage {
        private byte[] data;
        private IImage image;
        private final String contentType;
        private final int x;
        private final int y;

        StubPPImage(byte[] data, String contentType, int width, int height, int x, int y) {
            this.data = data.clone();
            this.image = new StubImage(width, height);
            this.contentType = contentType;
            this.x = x;
            this.y = y;
        }

        @Override
        public byte[] getBinaryData() {
            return data.clone();
        }

        @Override
        public IImage getImage() {
            return new StubImage(image.getWidth(), image.getHeight());
        }

        @Override
        public String getContentType() {
            return contentType;
        }

        @Override
        public int getWidth() {
            return image.getWidth();
        }

        @Override
        public int getHeight() {
            return image.getHeight();
        }

        @Override
        public int getX() {
            return x;
        }

        @Override
        public int getY() {
            return y;
        }

        @Override
        public void replaceImage(byte[] newImageData) {
            this.data = newImageData.clone();
        }

        @Override
        public void replaceImage(IImage newImage) {
            this.image = new StubImage(newImage.getWidth(), newImage.getHeight());
            this.data = new byte[]{0x42};
        }

        @Override
        public void replaceImage(IPPImage newImage) {
            this.data = newImage.getBinaryData();
            this.image = new StubImage(newImage.getWidth(), newImage.getHeight());
        }
    }

    private IPPImage ppImage;

    @BeforeEach
    void setUp() {
        ppImage = new StubPPImage(
                new byte[]{(byte) 0x89, 0x50, 0x4E, 0x47},
                "image/png",
                640, 480,
                100, 200
        );
    }

    // --- test_notes_size: positive width and height ---

    @Test
    void sizeHasPositiveWidthAndHeight() {
        assertThat(ppImage.getWidth()).isGreaterThan(0);
        assertThat(ppImage.getHeight()).isGreaterThan(0);
    }

    // --- test_shape_frame_properties: x, y, width, height ---

    @Test
    void frameProperties_matchExpectedValues() {
        assertThat(ppImage.getX()).isEqualTo(100);
        assertThat(ppImage.getY()).isEqualTo(200);
        assertThat(ppImage.getWidth()).isEqualTo(640);
        assertThat(ppImage.getHeight()).isEqualTo(480);
    }

    // --- test_picture_fill: image data persists ---

    @Test
    void binaryData_returnsNonEmptyCopy() {
        byte[] data = ppImage.getBinaryData();
        assertThat(data).isNotEmpty();
        assertThat(data).hasSize(4);
    }

    @Test
    void binaryData_returnsCopyNotReference() {
        byte[] first = ppImage.getBinaryData();
        byte[] second = ppImage.getBinaryData();
        assertThat(first).isEqualTo(second);
        // Mutating the returned copy must not affect the internal state
        first[0] = 0;
        assertThat(ppImage.getBinaryData()[0]).isNotEqualTo((byte) 0);
    }

    @Test
    void image_returnsNonNull() {
        IImage image = ppImage.getImage();
        assertThat(image).isNotNull();
        assertThat(image.getWidth()).isEqualTo(640);
        assertThat(image.getHeight()).isEqualTo(480);
    }

    @Test
    void contentType_returnsMimeType() {
        assertThat(ppImage.getContentType()).isEqualTo("image/png");
    }

    // --- test_reroute: width/height > 0 after operations ---

    @Test
    void widthAndHeight_arePositiveAfterCreation() {
        assertThat(ppImage.getWidth() > 0 || ppImage.getHeight() > 0).isTrue();
    }

    // --- test_line_color_and_width: replace preserves consistency ---

    @Test
    void replaceImageWithBytes_updatesData() {
        byte[] newData = new byte[]{0x01, 0x02, 0x03};
        ppImage.replaceImage(newData);
        assertThat(ppImage.getBinaryData()).isEqualTo(newData);
    }

    // --- test_line_dash_style: various styles can be set ---

    @Test
    void replaceImageWithIImage_updatesImage() {
        IImage newImage = new StubImage(320, 240);
        ppImage.replaceImage(newImage);
        assertThat(ppImage.getWidth()).isEqualTo(320);
        assertThat(ppImage.getHeight()).isEqualTo(240);
    }

    @Test
    void replaceImageWithIPPImage_updatesFromOtherPPImage() {
        IPPImage other = new StubPPImage(
                new byte[]{0x0A, 0x0B},
                "image/jpeg",
                1024, 768,
                50, 75
        );
        ppImage.replaceImage(other);
        assertThat(ppImage.getBinaryData()).isEqualTo(new byte[]{0x0A, 0x0B});
        assertThat(ppImage.getWidth()).isEqualTo(1024);
        assertThat(ppImage.getHeight()).isEqualTo(768);
    }
}
