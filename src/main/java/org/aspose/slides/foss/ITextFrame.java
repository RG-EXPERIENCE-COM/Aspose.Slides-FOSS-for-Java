package org.aspose.slides.foss;

/**
 * Represents a TextFrame.
 */
public interface ITextFrame extends ISlideComponent, IPresentationComponent {

    /**
     * Returns the list of all paragraphs in a frame. Read-only {@link IParagraphCollection}.
     *
     * @return the paragraph collection
     */
    IParagraphCollection getParagraphs();

    /**
     * Gets the plain text for a TextFrame. Read/write {@link String}.
     *
     * @return the text
     */
    String getText();

    /**
     * Sets the plain text for a TextFrame.
     *
     * @param value the text
     */
    void setText(String value);

    /**
     * Returns the formatting object for this TextFrame object.
     * Read-only {@link ITextFrameFormat}.
     *
     * @return the text frame format
     */
    ITextFrameFormat getTextFrameFormat();

    /**
     * Returns the parent shape, or {@code null} if the parent object does not
     * implement the {@link IShape} interface. Read-only.
     *
     * @return the parent shape, or {@code null}
     */
    IShape getParentShape();

    /**
     * Returns the parent cell, or {@code null} if the parent object does not
     * implement the {@link ICell} interface. Read-only.
     *
     * @return the parent cell, or {@code null}
     */
    ICell getParentCell();

    /**
     * Returns the base {@link ISlideComponent} interface. Read-only.
     *
     * @return this instance as {@link ISlideComponent}
     */
    ISlideComponent asISlideComponent();
}
