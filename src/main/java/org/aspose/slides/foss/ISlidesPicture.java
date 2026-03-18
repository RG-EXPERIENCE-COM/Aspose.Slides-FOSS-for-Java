package org.aspose.slides.foss;

/**
 * Represents a picture in a presentation.
 */
public interface ISlidesPicture {

    /**
     * Returns the embedded image. Read/write {@link IPPImage}.
     *
     * @return the embedded image, or {@code null} if not set
     */
    IPPImage getImage();

    /**
     * Sets the embedded image.
     *
     * @param value the image from the presentation's image collection
     */
    void setImage(IPPImage value);

    /**
     * Returns the linked image's URL. Read/write.
     *
     * @return the link path, or an empty string if not linked
     */
    String getLinkPathLong();

    /**
     * Sets the linked image's URL.
     *
     * @param value the link URL, or empty string to remove
     */
    void setLinkPathLong(String value);

    /**
     * Allows to get base {@link IPresentationComponent} interface. Read-only.
     *
     * @return this object as {@link IPresentationComponent}
     */
    IPresentationComponent asIPresentationComponent();

    /**
     * Allows to get base {@link ISlideComponent} interface. Read-only.
     *
     * @return this object as {@link ISlideComponent}
     */
    ISlideComponent asISlideComponent();
}
