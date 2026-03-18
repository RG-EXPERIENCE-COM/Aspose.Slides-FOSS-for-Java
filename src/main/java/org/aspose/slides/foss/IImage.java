package org.aspose.slides.foss;

import org.aspose.slides.foss.drawing.Size;

import java.io.OutputStream;

/**
 * Represents a raster or vector image.
 */
public interface IImage extends AutoCloseable {

    /**
     * Gets the size of the image.
     *
     * @return the image size
     */
    Size getSize();

    /**
     * Gets the width of the image in pixels.
     *
     * @return the width in pixels
     */
    int getWidth();

    /**
     * Gets the height of the image in pixels.
     *
     * @return the height in pixels
     */
    int getHeight();

    /**
     * Saves the image to the specified file.
     *
     * @param filename the path to the output file
     */
    void save(String filename);

    /**
     * Saves the image to the specified file in the given format.
     *
     * @param filename the path to the output file
     * @param format   the image format (e.g. "png", "jpeg")
     */
    void save(String filename, String format);

    /**
     * Saves the image to the specified stream in the given format.
     *
     * @param stream the output stream to write to
     * @param format the image format (e.g. "png", "jpeg")
     */
    void save(OutputStream stream, String format);

    /**
     * Saves the image to the specified file in the given format and quality.
     *
     * @param filename the path to the output file
     * @param format   the image format (e.g. "png", "jpeg")
     * @param quality  the image quality (0-100)
     */
    void save(String filename, String format, int quality);

    /**
     * Saves the image to the specified stream in the given format and quality.
     *
     * @param stream  the output stream to write to
     * @param format  the image format (e.g. "png", "jpeg")
     * @param quality the image quality (0-100)
     */
    void save(OutputStream stream, String format, int quality);
}
