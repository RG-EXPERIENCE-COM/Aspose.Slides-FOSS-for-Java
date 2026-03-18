package org.aspose.slides.foss;

import org.aspose.slides.foss.drawing.Size;
import org.aspose.slides.foss.internal.pptx.ImageUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Represents a raster or vector image.
 */
public final class Image implements IImage {

    private byte[] data;
    private String contentType;
    private int width;
    private int height;

    /**
     * Creates an uninitialized image.
     *
     * <p>Call {@link #initInternal(byte[], String)} to populate image data.</p>
     */
    public Image() {
        // default constructor; requires initInternal to be called
    }

    /**
     * Internal initialization with image data.
     *
     * @param data        the raw image bytes
     * @param contentType the MIME type of the image (e.g. "image/png")
     */
    public void initInternal(byte[] data, String contentType) {
        this.data = data;
        this.contentType = contentType;
        int[] dims = ImageUtils.getImageDimensions(data);
        this.width = dims[0];
        this.height = dims[1];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Size getSize() {
        return new Size(width, height);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWidth() {
        return width;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHeight() {
        return height;
    }

    /**
     * Gets the MIME content type of the image.
     *
     * @return the content type string
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Gets the raw image data.
     *
     * @return the image bytes
     */
    public byte[] getData() {
        return data;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(String filename) {
        try {
            Files.write(Path.of(filename), data);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(String filename, String format) {
        save(filename);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(String filename, String format, int quality) {
        save(filename);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(OutputStream stream, String format) {
        try {
            stream.write(data);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(OutputStream stream, String format, int quality) {
        save(stream, format);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        // no external resources to release
    }
}
