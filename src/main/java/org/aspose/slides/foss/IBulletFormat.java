package org.aspose.slides.foss;

/**
 * Represents paragraph bullet formatting properties.
 */
public interface IBulletFormat {

    /**
     * Returns the bullet type of a paragraph with no inheritance. Read/write.
     *
     * @return the bullet type
     */
    BulletType getType();

    /**
     * Sets the bullet type of a paragraph.
     *
     * @param value the bullet type
     */
    void setType(BulletType value);

    /**
     * Returns the bullet char of a paragraph with no inheritance. Read/write.
     *
     * @return the bullet character
     */
    String getChar();

    /**
     * Sets the bullet char of a paragraph.
     *
     * @param value the bullet character
     */
    void setChar(String value);

    /**
     * Returns the bullet font of a paragraph with no inheritance. Read/write.
     *
     * @return the bullet font, or {@code null}
     */
    IFontData getFont();

    /**
     * Sets the bullet font of a paragraph.
     *
     * @param value the bullet font
     */
    void setFont(IFontData value);

    /**
     * Returns the bullet height of a paragraph with no inheritance.
     * Value {@code Float.NaN} determines that bullet inherits height from
     * the first portion in the paragraph. Read/write.
     *
     * @return the bullet height
     */
    float getHeight();

    /**
     * Sets the bullet height of a paragraph.
     *
     * @param value the bullet height
     */
    void setHeight(float value);

    /**
     * Returns the color format of a bullet of a paragraph with no inheritance. Read-only.
     *
     * @return the bullet color format
     */
    IColorFormat getColor();

    /**
     * Returns the first number used for group of numbered bullets with no inheritance. Read/write.
     *
     * @return the starting number
     */
    int getNumberedBulletStartWith();

    /**
     * Sets the first number used for group of numbered bullets.
     *
     * @param value the starting number
     */
    void setNumberedBulletStartWith(int value);

    /**
     * Returns the style of a numbered bullet with no inheritance. Read/write.
     *
     * @return the numbered bullet style
     */
    NumberedBulletStyle getNumberedBulletStyle();

    /**
     * Sets the style of a numbered bullet.
     *
     * @param value the numbered bullet style
     */
    void setNumberedBulletStyle(NumberedBulletStyle value);

    /**
     * Determines whether the bullet has own color or inherits it from
     * the first portion in the paragraph. Read/write.
     *
     * @return the nullable boolean
     */
    NullableBool getIsBulletHardColor();

    /**
     * Sets whether the bullet has own color or inherits it.
     *
     * @param value the nullable boolean
     */
    void setIsBulletHardColor(NullableBool value);

    /**
     * Determines whether the bullet has own font or inherits it from
     * the first portion in the paragraph. Read/write.
     *
     * @return the nullable boolean
     */
    NullableBool getIsBulletHardFont();

    /**
     * Sets whether the bullet has own font or inherits it.
     *
     * @param value the nullable boolean
     */
    void setIsBulletHardFont(NullableBool value);

    /**
     * Returns the picture used as a bullet in a paragraph with no inheritance. Read-only.
     *
     * @return the bullet picture, or {@code null} if no paragraph properties element
     */
    ISlidesPicture getPicture();
}
