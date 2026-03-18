package org.aspose.slides.foss;

/**
 * Represents a gradient format.
 */
public interface IGradientFormat extends IFillParamSource {

    /**
     * Returns or sets the flipping mode for a gradient. Read/write.
     *
     * @return the tile flip mode
     */
    TileFlip getTileFlip();

    /**
     * Sets the flipping mode for a gradient.
     *
     * @param value the tile flip mode
     */
    void setTileFlip(TileFlip value);

    /**
     * Returns or sets the style of a gradient. Read/write.
     *
     * @return the gradient direction
     */
    GradientDirection getGradientDirection();

    /**
     * Sets the style of a gradient.
     *
     * @param value the gradient direction
     */
    void setGradientDirection(GradientDirection value);

    /**
     * Returns or sets the angle of a gradient. Read/write.
     *
     * @return the angle in degrees
     */
    double getLinearGradientAngle();

    /**
     * Sets the angle of a gradient.
     *
     * @param value the angle in degrees
     */
    void setLinearGradientAngle(double value);

    /**
     * Determines whether a gradient is scaled. Read/write.
     *
     * @return the nullable boolean
     */
    NullableBool getLinearGradientScaled();

    /**
     * Sets whether a gradient is scaled.
     *
     * @param value the nullable boolean
     */
    void setLinearGradientScaled(NullableBool value);

    /**
     * Returns or sets the shape of a gradient. Read/write.
     *
     * @return the gradient shape
     */
    GradientShape getGradientShape();

    /**
     * Sets the shape of a gradient.
     *
     * @param value the gradient shape
     */
    void setGradientShape(GradientShape value);

    /**
     * Returns the collection of gradient stops. Read-only.
     *
     * @return the gradient stop collection
     */
    IGradientStopCollection getGradientStops();
}
