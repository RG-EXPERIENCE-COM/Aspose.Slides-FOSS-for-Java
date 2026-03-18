package org.aspose.slides.foss;

/**
 * Represents a portion of text inside a text paragraph.
 */
public interface IPortion extends ISlideComponent {

    /**
     * Returns formatting object which contains explicitly set formatting properties
     * of the text portion with no inheritance applied.
     * Read-only {@link IPortionFormat}.
     *
     * @return the portion format
     */
    IPortionFormat getPortionFormat();

    /**
     * Gets the text of this portion. Read/write.
     *
     * @return the text
     */
    String getText();

    /**
     * Sets the text of this portion.
     *
     * @param value the text
     */
    void setText(String value);

    /**
     * Returns the parent slide component.
     * Read-only {@link ISlideComponent}.
     *
     * @return this instance as {@link ISlideComponent}
     */
    ISlideComponent asISlideComponent();

    /**
     * Returns the parent presentation component.
     * Read-only {@link IPresentationComponent}.
     *
     * @return this instance as {@link IPresentationComponent}
     */
    IPresentationComponent asIPresentationComponent();
}
