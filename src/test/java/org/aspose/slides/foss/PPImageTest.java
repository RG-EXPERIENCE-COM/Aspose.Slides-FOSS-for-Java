package org.aspose.slides.foss;

import org.aspose.slides.foss.internal.pptx.OpcPackage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for the {@link PPImage} class.
 *
 * <p>Covers positive dimensions, image data persistence, replace behavior,
 * content type preservation, and dimension properties.</p>
 */
class PPImageTest {

    private static final String PART_NAME = "ppt/media/image1.png";

    private byte[] pngData;
    private OpcPackage pkg;
    private PPImage ppImage;

    /** Creates a minimal 120x90 PNG and initialises a PPImage backed by an OpcPackage. */
    @BeforeEach
    void setUp() throws IOException {
        pngData = createPng(120, 90);
        pkg = new OpcPackage();
        pkg.setPartBytes(PART_NAME, pngData);

        ppImage = new PPImage();
        ppImage.initInternal(pkg, PART_NAME, pngData, "image/png");
    }

    // --- test_notes_size: positive width and height ---

    /** Dimensions are positive after init. */
    @Test
    void sizeHasPositiveWidthAndHeight() {
        assertThat(ppImage.getWidth()).isGreaterThan(0);
        assertThat(ppImage.getHeight()).isGreaterThan(0);
    }

    // --- test_shape_frame_properties: x, y, width, height ---

    /** Width and height match source image. */
    @Test
    void widthAndHeightMatchSourceDimensions() {
        assertThat(ppImage.getWidth()).isEqualTo(120);
        assertThat(ppImage.getHeight()).isEqualTo(90);
    }

    /** x and y offsets are always zero. */
    @Test
    void xAndYAreZero() {
        assertThat(ppImage.getX()).isEqualTo(0);
        assertThat(ppImage.getY()).isEqualTo(0);
    }

    // --- test_reroute: width/height > 0 after operations ---

    /** Width or height is positive after creation. */
    @Test
    void dimensionsArePositiveAfterInit() {
        assertThat(ppImage.getWidth() > 0 || ppImage.getHeight() > 0).isTrue();
    }

    // --- test_picture_fill: image data persists ---

    /** Binary data returns non-empty copy. */
    @Test
    void binaryDataReturnsNonEmptyCopy() {
        byte[] data = ppImage.getBinaryData();
        assertThat(data).isNotEmpty();
        assertThat(data).isEqualTo(pngData);
    }

    /** Binary data returns a defensive copy, not the internal reference. */
    @Test
    void binaryDataReturnsCopyNotReference() {
        byte[] first = ppImage.getBinaryData();
        byte[] second = ppImage.getBinaryData();
        assertThat(first).isEqualTo(second);
        // Mutating the returned copy must not affect internal state
        first[0] = 0;
        assertThat(ppImage.getBinaryData()).isEqualTo(pngData);
    }

    /** getImage() returns a non-null IImage with matching dimensions. */
    @Test
    void imageReturnsNonNullWithCorrectDimensions() {
        IImage image = ppImage.getImage();
        assertThat(image).isNotNull();
        assertThat(image.getWidth()).isEqualTo(120);
        assertThat(image.getHeight()).isEqualTo(90);
    }

    // --- test_line_dash_style: content type preserved ---

    /** Content type is preserved. */
    @Test
    void contentTypeIsPreserved() {
        assertThat(ppImage.getContentType()).isEqualTo("image/png");
    }

    // --- test_line_color_and_width: replace preserves consistency ---

    /** ReplaceImage(byte[]) updates data. */
    @Test
    void replaceImageWithBytesUpdatesData() throws IOException {
        byte[] newPng = createPng(200, 150);
        ppImage.replaceImage(newPng);

        assertThat(ppImage.getBinaryData()).isEqualTo(newPng);
        assertThat(ppImage.getWidth()).isEqualTo(200);
        assertThat(ppImage.getHeight()).isEqualTo(150);
        assertThat(ppImage.getContentType()).isEqualTo("image/png");
    }

    /** replaceImage(byte[]) also updates the OPC package part. */
    @Test
    void replaceImageWithBytesUpdatesPackagePart() throws IOException {
        byte[] newPng = createPng(64, 64);
        ppImage.replaceImage(newPng);

        assertThat(pkg.getPartBytes(PART_NAME)).isEqualTo(newPng);
    }

    /** replaceImage(IImage) updates from an Image object. */
    @Test
    void replaceImageWithIImageUpdatesData() throws IOException {
        byte[] newPng = createPng(80, 60);
        Image newImage = new Image();
        newImage.initInternal(newPng, "image/png");

        ppImage.replaceImage((IImage) newImage);

        assertThat(ppImage.getWidth()).isEqualTo(80);
        assertThat(ppImage.getHeight()).isEqualTo(60);
    }

    /** replaceImage(IPPImage) copies data from another PPImage. */
    @Test
    void replaceImageWithIPPImageCopiesData() throws IOException {
        byte[] otherPng = createPng(50, 40);
        PPImage other = new PPImage();
        other.initInternal(pkg, "ppt/media/image2.png", otherPng, "image/png");

        ppImage.replaceImage((IPPImage) other);

        assertThat(ppImage.getBinaryData()).isEqualTo(otherPng);
        assertThat(ppImage.getWidth()).isEqualTo(50);
        assertThat(ppImage.getHeight()).isEqualTo(40);
    }

    /** replaceImage with null throws NullPointerException. */
    @Test
    void replaceImageWithNullThrows() {
        assertThatThrownBy(() -> ppImage.replaceImage((byte[]) null))
                .isInstanceOf(NullPointerException.class);
    }

    // --- helper ---

    private static byte[] createPng(int width, int height) throws IOException {
        BufferedImage buffered = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(buffered, "png", baos);
        return baos.toByteArray();
    }
}
