package org.aspose.slides.foss;

/**
 * Represents an AutoShape.
 */
public interface IAutoShape extends IGeometryShape {

    /**
     * Returns the TextFrame object for the AutoShape. Read-only.
     *
     * @return the text frame, or {@code null}
     */
    ITextFrame getTextFrame();

    /**
     * Specifies if the shape is a text box.
     *
     * @return {@code true} if the shape is a text box
     */
    boolean isTextBox();

    /**
     * Allows to get base IGeometryShape interface. Read-only.
     *
     * @return this shape as IGeometryShape
     */
    IGeometryShape getAsIGeometryShape();

    /**
     * Adds a new text frame to the shape with the specified text.
     *
     * <p>If the shape already contains a text body, it is removed and replaced.
     * The shape is marked as a text box. Line breaks ({@code \r\n}, {@code \r},
     * {@code \n}) in the text create separate paragraphs.</p>
     *
     * @param text the text content; may be {@code null}
     * @return the created text frame, or {@code null} if the shape has no backing element
     */
    ITextFrame addTextFrame(String text);
}
