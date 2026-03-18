package org.aspose.slides.foss.effects;

import org.aspose.slides.foss.IColorFormat;

/**
 * Represents an inner shadow effect applied to a shape.
 */
public interface IInnerShadow extends IImageTransformOperation {

    /**
     * Returns the blur radius in points.
     *
     * @return the blur radius
     */
    double getBlurRadius();

    /**
     * Sets the blur radius in points.
     *
     * @param value the blur radius
     */
    void setBlurRadius(double value);

    /**
     * Returns the shadow direction in degrees.
     *
     * @return the direction in degrees
     */
    double getDirection();

    /**
     * Sets the shadow direction in degrees.
     *
     * @param value the direction in degrees
     */
    void setDirection(double value);

    /**
     * Returns the shadow distance in points.
     *
     * @return the distance
     */
    double getDistance();

    /**
     * Sets the shadow distance in points.
     *
     * @param value the distance
     */
    void setDistance(double value);

    /**
     * Returns the shadow color format.
     *
     * @return the color format
     */
    IColorFormat getShadowColor();
}
