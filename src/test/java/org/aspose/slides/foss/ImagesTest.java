package org.aspose.slides.foss;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ImagesTest {

    // Minimal valid PNG: 1x1 pixel, RGBA
    private static final byte[] MINIMAL_PNG = {
        (byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A, // PNG signature
        0x00, 0x00, 0x00, 0x0D, 0x49, 0x48, 0x44, 0x52,         // IHDR chunk
        0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x01,         // 1x1
        0x08, 0x02, 0x00, 0x00, 0x00, (byte) 0x90, 0x77, 0x53,
        (byte) 0xDE, 0x00, 0x00, 0x00, 0x0C, 0x49, 0x44, 0x41,
        0x54, 0x08, (byte) 0xD7, 0x63, (byte) 0xF8, (byte) 0xCF,
        (byte) 0xC0, 0x00, 0x00, 0x00, 0x02, 0x00, 0x01,
        (byte) 0xE2, 0x21, (byte) 0xBC, 0x33, 0x00, 0x00, 0x00,
        0x00, 0x49, 0x45, 0x4E, 0x44, (byte) 0xAE, 0x42, 0x60,
        (byte) 0x82
    };

    // JPEG magic bytes + minimal data
    private static final byte[] JPEG_HEADER = {
        (byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xE0,
        0x00, 0x10, 0x4A, 0x46, 0x49, 0x46
    };

    @Test
    void fromFile_readsPngAndGuessesContentType(@TempDir Path tempDir) throws Exception {
        Path pngFile = tempDir.resolve("test.png");
        Files.write(pngFile, MINIMAL_PNG);

        try (IImage image = Images.fromFile(pngFile.toString())) {
            assertThat(image).isNotNull();
            assertThat(((Image) image).getContentType()).isEqualTo("image/png");
            assertThat(((Image) image).getData()).isEqualTo(MINIMAL_PNG);
        }
    }

    @Test
    void fromFile_throwsOnNullPath() {
        assertThatThrownBy(() -> Images.fromFile(null))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    void fromFile_throwsOnMissingFile() {
        assertThatThrownBy(() -> Images.fromFile("/nonexistent/path/image.png"))
            .isInstanceOf(UncheckedIOException.class);
    }

    @Test
    void fromStream_guessesContentTypeFromData() throws Exception {
        InputStream stream = new ByteArrayInputStream(MINIMAL_PNG);

        try (IImage image = Images.fromStream(stream)) {
            assertThat(image).isNotNull();
            assertThat(((Image) image).getContentType()).isEqualTo("image/png");
            assertThat(((Image) image).getData()).isEqualTo(MINIMAL_PNG);
        }
    }

    @Test
    void fromStream_withExplicitContentType() throws Exception {
        InputStream stream = new ByteArrayInputStream(JPEG_HEADER);

        try (IImage image = Images.fromStream(stream, "image/jpeg")) {
            assertThat(image).isNotNull();
            assertThat(((Image) image).getContentType()).isEqualTo("image/jpeg");
            assertThat(((Image) image).getData()).isEqualTo(JPEG_HEADER);
        }
    }

    @Test
    void fromStream_throwsOnNullStream() {
        assertThatThrownBy(() -> Images.fromStream((InputStream) null))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    void fromStream_unknownFormatFallsBackToOctetStream() throws Exception {
        byte[] unknownData = {0x01, 0x02, 0x03, 0x04, 0x05};
        InputStream stream = new ByteArrayInputStream(unknownData);

        try (IImage image = Images.fromStream(stream)) {
            assertThat(((Image) image).getContentType()).isEqualTo("application/octet-stream");
        }
    }
}
