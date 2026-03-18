package org.aspose.slides.foss.effects;

import org.aspose.slides.foss.IColorFormat;

/**
 * Represents a glow effect applied to a shape.
 */
public interface IGlow extends IImageTransformOperation {

    /**
     * Returns the glow radius in points.
     *
     * @return the glow radius
     */
    double getRadius();

    /**
     * Sets the glow radius in points.
     *
     * @param value the glow radius
     */
    void setRadius(double value);

    /**
     * Returns the color format for the glow effect.
     *
     * @return the color format
     */
    IColorFormat getColor();
}
