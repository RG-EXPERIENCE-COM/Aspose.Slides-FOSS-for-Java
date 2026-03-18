package org.aspose.slides.foss;

/**
 * Represents an image in a presentation.
 */
public interface IPPImage {

    /**
     * Returns the copy of an image's data. Read-only.
     *
     * @return a copy of the binary image data
     */
    byte[] getBinaryData();

    /**
     * Returns the copy of an image. Read-only.
     *
     * @return a copy of the image
     */
    IImage getImage();

    /**
     * Returns a MIME type of an image. Read-only.
     *
     * @return the content type string (e.g. "image/png")
     */
    String getContentType();

    /**
     * Returns the width of an image. Read-only.
     *
     * @return the width in pixels
     */
    int getWidth();

    /**
     * Returns the height of an image. Read-only.
     *
     * @return the height in pixels
     */
    int getHeight();

    /**
     * Returns the X-offset of an image. Read-only.
     *
     * @return the x-offset
     */
    int getX();

    /**
     * Returns the Y-offset of an image. Read-only.
     *
     * @return the y-offset
     */
    int getY();

    /**
     * Replaces the image data with new raw binary data.
     *
     * @param newImageData the new image data
     */
    void replaceImage(byte[] newImageData);

    /**
     * Replaces the image with the specified {@link IImage}.
     *
     * @param newImage the new image
     */
    void replaceImage(IImage newImage);

    /**
     * Replaces the image with another presentation image.
     *
     * @param newImage the new presentation image
     */
    void replaceImage(IPPImage newImage);
}
