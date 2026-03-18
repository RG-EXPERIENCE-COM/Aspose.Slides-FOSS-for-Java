package org.aspose.slides.foss;

/**
 * Represents format of a text frame.
 */
public interface ITextFrameFormat {

    /**
     * Returns the left margin in points. Read/write.
     *
     * @return the left margin
     */
    double getMarginLeft();

    /**
     * Sets the left margin in points.
     *
     * @param value the left margin
     */
    void setMarginLeft(double value);

    /**
     * Returns the right margin in points. Read/write.
     *
     * @return the right margin
     */
    double getMarginRight();

    /**
     * Sets the right margin in points.
     *
     * @param value the right margin
     */
    void setMarginRight(double value);

    /**
     * Returns the top margin in points. Read/write.
     *
     * @return the top margin
     */
    double getMarginTop();

    /**
     * Sets the top margin in points.
     *
     * @param value the top margin
     */
    void setMarginTop(double value);

    /**
     * Returns the bottom margin in points. Read/write.
     *
     * @return the bottom margin
     */
    double getMarginBottom();

    /**
     * Sets the bottom margin in points.
     *
     * @param value the bottom margin
     */
    void setMarginBottom(double value);

    /**
     * Returns the text wrapping flag. Read/write.
     *
     * @return the nullable bool value
     */
    NullableBool getWrapText();

    /**
     * Sets the text wrapping flag.
     *
     * @param value the nullable bool value
     */
    void setWrapText(NullableBool value);

    /**
     * Returns the text anchoring type. Read/write.
     *
     * @return the anchoring type
     */
    TextAnchorType getAnchoringType();

    /**
     * Sets the text anchoring type.
     *
     * @param value the anchoring type
     */
    void setAnchoringType(TextAnchorType value);

    /**
     * Returns the text centering mode. Read/write.
     *
     * @return the nullable bool value
     */
    NullableBool getCenterText();

    /**
     * Sets the text centering mode.
     *
     * @param value the nullable bool value
     */
    void setCenterText(NullableBool value);

    /**
     * Returns the text vertical type. Read/write.
     *
     * @return the text vertical type
     */
    TextVerticalType getTextVerticalType();

    /**
     * Sets the text vertical type.
     *
     * @param value the text vertical type
     */
    void setTextVerticalType(TextVerticalType value);

    /**
     * Returns the text autofit type. Read/write.
     *
     * @return the autofit type
     */
    TextAutofitType getAutofitType();

    /**
     * Sets the text autofit type.
     *
     * @param value the autofit type
     */
    void setAutofitType(TextAutofitType value);

    /**
     * Returns the number of columns. Read/write.
     *
     * @return the column count
     */
    int getColumnCount();

    /**
     * Sets the number of columns.
     *
     * @param value the column count
     */
    void setColumnCount(int value);

    /**
     * Returns the spacing between columns in points. Read/write.
     *
     * @return the column spacing
     */
    double getColumnSpacing();

    /**
     * Sets the spacing between columns.
     *
     * @param value the column spacing
     */
    void setColumnSpacing(double value);

    /**
     * Returns the 3D format of the text frame.
     * Read-only {@link IThreeDFormat}.
     *
     * @return the 3D format
     */
    IThreeDFormat getThreeDFormat();

    /**
     * Returns whether text is kept flat. Read/write.
     *
     * @return {@code true} if text is kept flat
     */
    boolean isKeepTextFlat();

    /**
     * Sets whether text is kept flat.
     *
     * @param value {@code true} to keep text flat
     */
    void setKeepTextFlat(boolean value);

    /**
     * Returns the text rotation angle in degrees. Read/write.
     *
     * @return the rotation angle
     */
    double getRotationAngle();

    /**
     * Sets the text rotation angle in degrees.
     *
     * @param value the rotation angle
     */
    void setRotationAngle(double value);

    /**
     * Returns the text transform shape type. Read/write.
     *
     * @return the text shape type
     */
    TextShapeType getTransform();

    /**
     * Sets the text transform shape type.
     *
     * @param value the text shape type
     */
    void setTransform(TextShapeType value);
}
