package org.aspose.slides.foss;

import java.io.InputStream;
import java.util.List;

/**
 * Represents a collection of images in a presentation.
 */
public interface IImageCollection extends Iterable<IPPImage> {

    /**
     * Returns the number of images in the collection.
     *
     * @return the count
     */
    int size();

    /**
     * Gets the image at the specified index.
     *
     * @param index the zero-based index
     * @return the presentation image
     */
    IPPImage get(int index);

    /**
     * Adds an image to the collection from an {@link IImage}.
     *
     * @param image the image to add
     * @return the added presentation image
     */
    IPPImage addImage(IImage image);

    /**
     * Adds an image to the collection from raw byte data.
     *
     * @param imageData the raw image bytes
     * @return the added presentation image
     */
    IPPImage addImage(byte[] imageData);

    /**
     * Adds an image to the collection from an input stream.
     *
     * @param stream the input stream containing image data
     * @return the added presentation image
     */
    IPPImage addImage(InputStream stream);

    /**
     * Returns this collection as an unmodifiable {@link List}.
     *
     * @return an unmodifiable list of presentation images
     */
    List<IPPImage> asICollection();

    /**
     * Returns this collection as an {@link Iterable}.
     *
     * @return an iterable over the presentation images
     */
    Iterable<IPPImage> asIEnumerable();
}
