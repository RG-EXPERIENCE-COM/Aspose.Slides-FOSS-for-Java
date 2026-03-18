package org.aspose.slides.foss;

/**
 * Represents a shape with geometry (preset or custom).
 */
public interface IGeometryShape extends IShape {

    /**
     * Returns the shape's style object. Read-only.
     *
     * @return the shape style, or {@code null} if not available
     */
    IShapeStyle getShapeStyle();

    /**
     * Gets the AutoShape type. Read/write.
     *
     * @return the shape type
     */
    @Override
    ShapeType getShapeType();

    /**
     * Sets the AutoShape type.
     *
     * @param value the shape type
     */
    void setShapeType(ShapeType value);

    /**
     * Gets the collection of shape adjustment values.
     *
     * @return the adjustment values, or null
     */
    IAdjustValueCollection getAdjustments();
}
