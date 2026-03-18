package org.aspose.slides.foss;

/**
 * Represents a picture fill style.
 */
public interface IPictureFillFormat extends IFillParamSource {

    /**
     * Returns or sets the dpi which is used to fill a picture. Read/write.
     *
     * @return the dpi value
     */
    int getDpi();

    /**
     * Sets the dpi which is used to fill a picture.
     *
     * @param value the dpi value
     */
    void setDpi(int value);

    /**
     * Returns or sets the picture fill mode. Read/write {@link PictureFillMode}.
     *
     * @return the picture fill mode
     */
    PictureFillMode getPictureFillMode();

    /**
     * Sets the picture fill mode.
     *
     * @param value the picture fill mode
     */
    void setPictureFillMode(PictureFillMode value);

    /**
     * Returns the picture. Read-only {@link ISlidesPicture}.
     *
     * @return the picture
     */
    ISlidesPicture getPicture();

    /**
     * Returns or sets the number of percents of real image width that are cropped
     * off the left of the picture. Read/write.
     *
     * @return the crop left percentage
     */
    float getCropLeft();

    /**
     * Sets the number of percents of real image width that are cropped off the left
     * of the picture.
     *
     * @param value the crop left percentage
     */
    void setCropLeft(float value);

    /**
     * Returns or sets the number of percents of real image height that are cropped
     * off the top of the picture. Read/write.
     *
     * @return the crop top percentage
     */
    float getCropTop();

    /**
     * Sets the number of percents of real image height that are cropped off the top
     * of the picture.
     *
     * @param value the crop top percentage
     */
    void setCropTop(float value);

    /**
     * Returns or sets the number of percents of real image width that are cropped
     * off the right of the picture. Read/write.
     *
     * @return the crop right percentage
     */
    float getCropRight();

    /**
     * Sets the number of percents of real image width that are cropped off the right
     * of the picture.
     *
     * @param value the crop right percentage
     */
    void setCropRight(float value);

    /**
     * Returns or sets the number of percents of real image height that are cropped
     * off the bottom of the picture. Read/write.
     *
     * @return the crop bottom percentage
     */
    float getCropBottom();

    /**
     * Sets the number of percents of real image height that are cropped off the
     * bottom of the picture.
     *
     * @param value the crop bottom percentage
     */
    void setCropBottom(float value);

    /**
     * Returns or sets left edge of the fill rectangle that is defined by a percentage
     * offset from the left edge of the shape's bounding box. A positive percentage
     * specifies an inset, while a negative percentage specifies an outset. Read/write.
     *
     * @return the stretch offset left percentage
     */
    float getStretchOffsetLeft();

    /**
     * Sets left edge of the fill rectangle defined by a percentage offset from
     * the left edge of the shape's bounding box.
     *
     * @param value the stretch offset left percentage
     */
    void setStretchOffsetLeft(float value);

    /**
     * Returns or sets top edge of the fill rectangle that is defined by a percentage
     * offset from the top edge of the shape's bounding box. A positive percentage
     * specifies an inset, while a negative percentage specifies an outset. Read/write.
     *
     * @return the stretch offset top percentage
     */
    float getStretchOffsetTop();

    /**
     * Sets top edge of the fill rectangle defined by a percentage offset from
     * the top edge of the shape's bounding box.
     *
     * @param value the stretch offset top percentage
     */
    void setStretchOffsetTop(float value);

    /**
     * Returns or sets right edge of the fill rectangle that is defined by a percentage
     * offset from the right edge of the shape's bounding box. A positive percentage
     * specifies an inset, while a negative percentage specifies an outset. Read/write.
     *
     * @return the stretch offset right percentage
     */
    float getStretchOffsetRight();

    /**
     * Sets right edge of the fill rectangle defined by a percentage offset from
     * the right edge of the shape's bounding box.
     *
     * @param value the stretch offset right percentage
     */
    void setStretchOffsetRight(float value);

    /**
     * Returns or sets bottom edge of the fill rectangle that is defined by a percentage
     * offset from the bottom edge of the shape's bounding box. A positive percentage
     * specifies an inset, while a negative percentage specifies an outset. Read/write.
     *
     * @return the stretch offset bottom percentage
     */
    float getStretchOffsetBottom();

    /**
     * Sets bottom edge of the fill rectangle defined by a percentage offset from
     * the bottom edge of the shape's bounding box.
     *
     * @param value the stretch offset bottom percentage
     */
    void setStretchOffsetBottom(float value);

    /**
     * Returns or sets the horizontal offset of the texture from the shape's origin
     * in points. A positive value moves the texture to the right, while a negative
     * value moves it to the left. Read/write.
     *
     * @return the tile offset X in points
     */
    float getTileOffsetX();

    /**
     * Sets the horizontal offset of the texture from the shape's origin in points.
     *
     * @param value the tile offset X in points
     */
    void setTileOffsetX(float value);

    /**
     * Returns or sets the vertical offset of the texture from the shape's origin
     * in points. A positive value moves the texture down, while a negative value
     * moves it up. Read/write.
     *
     * @return the tile offset Y in points
     */
    float getTileOffsetY();

    /**
     * Sets the vertical offset of the texture from the shape's origin in points.
     *
     * @param value the tile offset Y in points
     */
    void setTileOffsetY(float value);

    /**
     * Returns or sets the horizontal scale for the texture fill as a percentage.
     * Read/write.
     *
     * @return the tile scale X percentage
     */
    float getTileScaleX();

    /**
     * Sets the horizontal scale for the texture fill as a percentage.
     *
     * @param value the tile scale X percentage
     */
    void setTileScaleX(float value);

    /**
     * Returns or sets the vertical scale for the texture fill as a percentage.
     * Read/write.
     *
     * @return the tile scale Y percentage
     */
    float getTileScaleY();

    /**
     * Sets the vertical scale for the texture fill as a percentage.
     *
     * @param value the tile scale Y percentage
     */
    void setTileScaleY(float value);

    /**
     * Returns or sets how the texture is aligned within the shape. This setting
     * controls the starting point of the texture pattern and how it repeats across
     * the shape. Read/write {@link RectangleAlignment}.
     *
     * @return the tile alignment
     */
    RectangleAlignment getTileAlignment();

    /**
     * Sets how the texture is aligned within the shape.
     *
     * @param value the tile alignment
     */
    void setTileAlignment(RectangleAlignment value);

    /**
     * Flips the texture tile around its horizontal, vertical or both axis.
     * Read/write {@link TileFlip}.
     *
     * @return the tile flip mode
     */
    TileFlip getTileFlip();

    /**
     * Sets the tile flip mode.
     *
     * @param value the tile flip mode
     */
    void setTileFlip(TileFlip value);
}
