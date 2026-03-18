package org.aspose.slides.foss;

/**
 * Represents paragraph formatting properties.
 */
public interface IParagraphFormat {

    /**
     * Returns the bullet formatting. Read-only {@link IBulletFormat}.
     *
     * @return the bullet format
     */
    IBulletFormat getBullet();

    /**
     * Returns the depth of the paragraph. Read/write.
     *
     * @return the paragraph depth
     */
    int getDepth();

    /**
     * Sets the depth of the paragraph.
     *
     * @param value the paragraph depth
     */
    void setDepth(int value);

    /**
     * Returns the text alignment. Read/write.
     *
     * @return the text alignment
     */
    TextAlignment getAlignment();

    /**
     * Sets the text alignment.
     *
     * @param value the text alignment
     */
    void setAlignment(TextAlignment value);

    /**
     * Returns the amount of space between lines within a paragraph. Read/write.
     *
     * @return the line spacing
     */
    double getSpaceWithin();

    /**
     * Sets the amount of space between lines within a paragraph.
     *
     * @param value the line spacing
     */
    void setSpaceWithin(double value);

    /**
     * Returns the amount of space before the first line of a paragraph. Read/write.
     *
     * @return the space before
     */
    double getSpaceBefore();

    /**
     * Sets the amount of space before the first line of a paragraph.
     *
     * @param value the space before
     */
    void setSpaceBefore(double value);

    /**
     * Returns the amount of space after the last line of a paragraph. Read/write.
     *
     * @return the space after
     */
    double getSpaceAfter();

    /**
     * Sets the amount of space after the last line of a paragraph.
     *
     * @param value the space after
     */
    void setSpaceAfter(double value);

    /**
     * Returns the East Asian line break setting. Read/write.
     *
     * @return the nullable bool value
     */
    NullableBool getEastAsianLineBreak();

    /**
     * Sets the East Asian line break setting.
     *
     * @param value the nullable bool value
     */
    void setEastAsianLineBreak(NullableBool value);

    /**
     * Returns the right-to-left setting. Read/write.
     *
     * @return the nullable bool value
     */
    NullableBool getRightToLeft();

    /**
     * Sets the right-to-left setting.
     *
     * @param value the nullable bool value
     */
    void setRightToLeft(NullableBool value);

    /**
     * Returns the Latin line break setting. Read/write.
     *
     * @return the nullable bool value
     */
    NullableBool getLatinLineBreak();

    /**
     * Sets the Latin line break setting.
     *
     * @param value the nullable bool value
     */
    void setLatinLineBreak(NullableBool value);

    /**
     * Returns the hanging punctuation setting. Read/write.
     *
     * @return the nullable bool value
     */
    NullableBool getHangingPunctuation();

    /**
     * Sets the hanging punctuation setting.
     *
     * @param value the nullable bool value
     */
    void setHangingPunctuation(NullableBool value);

    /**
     * Returns the left margin of the paragraph. Read/write.
     *
     * @return the left margin
     */
    double getMarginLeft();

    /**
     * Sets the left margin of the paragraph.
     *
     * @param value the left margin
     */
    void setMarginLeft(double value);

    /**
     * Returns the right margin of the paragraph. Read/write.
     *
     * @return the right margin
     */
    double getMarginRight();

    /**
     * Sets the right margin of the paragraph.
     *
     * @param value the right margin
     */
    void setMarginRight(double value);

    /**
     * Returns the indent of the paragraph. Read/write.
     *
     * @return the indent
     */
    double getIndent();

    /**
     * Sets the indent of the paragraph.
     *
     * @param value the indent
     */
    void setIndent(double value);

    /**
     * Returns the default tab size. Read/write.
     *
     * @return the default tab size
     */
    double getDefaultTabSize();

    /**
     * Sets the default tab size.
     *
     * @param value the default tab size
     */
    void setDefaultTabSize(double value);

    /**
     * Returns the font alignment. Read/write.
     *
     * @return the font alignment
     */
    FontAlignment getFontAlignment();

    /**
     * Sets the font alignment.
     *
     * @param value the font alignment
     */
    void setFontAlignment(FontAlignment value);

    /**
     * Returns the default portion format. Read-only.
     *
     * @return the default portion format
     */
    IBasePortionFormat getDefaultPortionFormat();
}
