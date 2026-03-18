package org.aspose.slides.foss.effects;

import org.aspose.slides.foss.IColorFormat;
import org.aspose.slides.foss.RectangleAlignment;

/**
 * Represents an Outer Shadow effect.
 */
public interface IOuterShadow extends IImageTransformOperation {

    /**
     * Returns the blur radius in points. Default value is 0 pt.
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
     * Returns the shadow direction in degrees. Default value is 0 (left-to-right).
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
     * Returns the shadow distance in points. Default value is 0 pt.
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
     * Returns the shadow color format. Read-only {@link IColorFormat}.
     *
     * @return the color format
     */
    IColorFormat getShadowColor();

    /**
     * Returns the rectangle alignment. Default value is {@link RectangleAlignment#NOT_DEFINED}.
     *
     * @return the rectangle alignment
     */
    RectangleAlignment getRectangleAlign();

    /**
     * Sets the rectangle alignment.
     *
     * @param value the rectangle alignment
     */
    void setRectangleAlign(RectangleAlignment value);

    /**
     * Returns the horizontal skew angle in degrees. Default value is 0.
     *
     * @return the horizontal skew angle
     */
    double getSkewHorizontal();

    /**
     * Sets the horizontal skew angle in degrees.
     *
     * @param value the horizontal skew angle
     */
    void setSkewHorizontal(double value);

    /**
     * Returns the vertical skew angle in degrees. Default value is 0.
     *
     * @return the vertical skew angle
     */
    double getSkewVertical();

    /**
     * Sets the vertical skew angle in degrees.
     *
     * @param value the vertical skew angle
     */
    void setSkewVertical(double value);

    /**
     * Indicates whether the shadow rotates together with the shape. Default value is {@code true}.
     *
     * @return {@code true} if the shadow rotates with the shape
     */
    boolean getRotateShadowWithShape();

    /**
     * Sets whether the shadow rotates together with the shape.
     *
     * @param value {@code true} to rotate the shadow with the shape
     */
    void setRotateShadowWithShape(boolean value);

    /**
     * Returns the horizontal scaling factor as a percentage. Default value is 100%.
     * Negative values cause a flip.
     *
     * @return the horizontal scale percentage
     */
    double getScaleHorizontal();

    /**
     * Sets the horizontal scaling factor as a percentage.
     *
     * @param value the horizontal scale percentage
     */
    void setScaleHorizontal(double value);

    /**
     * Returns the vertical scaling factor as a percentage. Default value is 100%.
     * Negative values cause a flip.
     *
     * @return the vertical scale percentage
     */
    double getScaleVertical();

    /**
     * Sets the vertical scaling factor as a percentage.
     *
     * @param value the vertical scale percentage
     */
    void setScaleVertical(double value);
}
