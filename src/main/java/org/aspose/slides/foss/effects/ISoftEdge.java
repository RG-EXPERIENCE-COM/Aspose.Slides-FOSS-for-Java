package org.aspose.slides.foss.effects;

/**
 * Represents a Soft Edge effect. The edges of the shape are blurred,
 * while the fill is not affected.
 */
public interface ISoftEdge extends IImageTransformOperation {

    /**
     * Returns the soft edge radius in points.
     *
     * @return the radius
     */
    double getRadius();

    /**
     * Sets the soft edge radius in points.
     *
     * @param value the radius
     */
    void setRadius(double value);
}
