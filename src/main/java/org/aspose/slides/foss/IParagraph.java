package org.aspose.slides.foss;

/**
 * Represents a text paragraph.
 */
public interface IParagraph {

    /**
     * Returns the collection of text portions in this paragraph.
     * Read-only {@link IPortionCollection}.
     *
     * @return the portion collection
     */
    IPortionCollection getPortions();

    /**
     * Returns the paragraph formatting properties.
     * Read-only {@link IParagraphFormat}.
     *
     * @return the paragraph format
     */
    IParagraphFormat getParagraphFormat();

    /**
     * Gets the text of this paragraph. Read/write.
     *
     * @return the paragraph text
     */
    String getText();

    /**
     * Sets the text of this paragraph.
     *
     * @param value the paragraph text
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
