package org.aspose.slides.foss;

/**
 * Represents fill formatting options.
 */
public interface IFillFormat {

    /**
     * Returns the type of filling. Read/write.
     *
     * @return the fill type
     */
    FillType getFillType();

    /**
     * Sets the type of filling.
     *
     * @param value the fill type
     */
    void setFillType(FillType value);

    /**
     * Returns the fill color. Read-only.
     *
     * @return the solid fill color format
     */
    IColorFormat getSolidFillColor();

    /**
     * Returns the gradient fill format. Read-only.
     *
     * @return the gradient format
     */
    IGradientFormat getGradientFormat();

    /**
     * Returns the pattern fill format. Read-only.
     *
     * @return the pattern format
     */
    IPatternFormat getPatternFormat();

    /**
     * Returns the picture fill format. Read-only.
     *
     * @return the picture fill format
     */
    IPictureFillFormat getPictureFillFormat();

    /**
     * Determines whether the fill should be rotated with the shape. Read/write.
     *
     * @return the rotate-with-shape flag
     */
    NullableBool getRotateWithShape();

    /**
     * Sets whether the fill should be rotated with the shape.
     *
     * @param value the rotate-with-shape flag
     */
    void setRotateWithShape(NullableBool value);
}
