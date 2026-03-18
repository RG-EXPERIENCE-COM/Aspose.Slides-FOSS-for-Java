package org.aspose.slides.foss.effects;

/**
 * Represents a Blur effect that is applied to the entire shape,
 * including its fill. All color channels, including alpha, are affected.
 */
public interface IBlur extends IImageTransformOperation {

    /**
     * Returns the blur radius in points.
     *
     * @return the blur radius
     */
    double getRadius();

    /**
     * Sets the blur radius in points.
     *
     * @param value the blur radius
     */
    void setRadius(double value);

    /**
     * Returns whether the blur boundary grows beyond the shape bounds.
     *
     * @return {@code true} if grow is enabled
     */
    boolean isGrow();

    /**
     * Sets whether the blur boundary grows beyond the shape bounds.
     *
     * @param value {@code true} to enable grow
     */
    void setGrow(boolean value);
}
