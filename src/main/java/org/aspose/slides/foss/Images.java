package org.aspose.slides.foss;

import org.aspose.slides.foss.internal.pptx.ImageUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Methods to instantiate and work with {@link IImage}.
 */
public final class Images {

    private Images() {
        // utility class
    }

    /**
     * Creates an image from a file path.
     *
     * <p>The content type is determined by inspecting the file's magic bytes.</p>
     *
     * @param path the path to the image file
     * @return the image
     * @throws UncheckedIOException if the file cannot be read
     */
    public static IImage fromFile(String path) {
        Objects.requireNonNull(path, "path must not be null");
        try {
            byte[] data = Files.readAllBytes(Path.of(path));
            String contentType = ImageUtils.guessContentType(data);
            var img = new Image();
            img.initInternal(data, contentType);
            return img;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Creates an image from an input stream.
     *
     * <p>The content type is determined by inspecting the data's magic bytes.</p>
     *
     * @param stream the input stream containing image data
     * @return the image
     * @throws UncheckedIOException if the stream cannot be read
     */
    public static IImage fromStream(InputStream stream) {
        Objects.requireNonNull(stream, "stream must not be null");
        try {
            byte[] data = stream.readAllBytes();
            String contentType = ImageUtils.guessContentType(data);
            var img = new Image();
            img.initInternal(data, contentType);
            return img;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Creates an image from an input stream with an explicit content type.
     *
     * @param stream      the input stream containing image data
     * @param contentType the MIME type of the image
     * @return the image
     * @throws UncheckedIOException if the stream cannot be read
     */
    public static IImage fromStream(InputStream stream, String contentType) {
        Objects.requireNonNull(stream, "stream must not be null");
        Objects.requireNonNull(contentType, "contentType must not be null");
        try {
            byte[] data = stream.readAllBytes();
            var img = new Image();
            img.initInternal(data, contentType);
            return img;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
