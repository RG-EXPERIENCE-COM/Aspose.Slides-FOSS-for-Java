package org.aspose.slides.foss.effects;

import org.aspose.slides.foss.RectangleAlignment;

/**
 * Represents a reflection effect applied to a shape.
 */
public interface IReflection extends IImageTransformOperation {

    /**
     * Returns the start position (along the alpha gradient ramp) of the start alpha value (percents).
     *
     * @return the start position alpha
     */
    double getStartPosAlpha();

    /**
     * Sets the start position (along the alpha gradient ramp) of the start alpha value (percents).
     *
     * @param value the start position alpha
     */
    void setStartPosAlpha(double value);

    /**
     * Returns the end position (along the alpha gradient ramp) of the end alpha value (percents).
     *
     * @return the end position alpha
     */
    double getEndPosAlpha();

    /**
     * Sets the end position (along the alpha gradient ramp) of the end alpha value (percents).
     *
     * @param value the end position alpha
     */
    void setEndPosAlpha(double value);

    /**
     * Returns the direction to offset the reflection (angle).
     *
     * @return the fade direction in degrees
     */
    double getFadeDirection();

    /**
     * Sets the direction to offset the reflection (angle).
     *
     * @param value the fade direction in degrees
     */
    void setFadeDirection(double value);

    /**
     * Returns the starting reflection opacity (percents).
     *
     * @return the starting reflection opacity
     */
    double getStartReflectionOpacity();

    /**
     * Sets the starting reflection opacity (percents).
     *
     * @param value the starting reflection opacity
     */
    void setStartReflectionOpacity(double value);

    /**
     * Returns the end reflection opacity (percents).
     *
     * @return the end reflection opacity
     */
    double getEndReflectionOpacity();

    /**
     * Sets the end reflection opacity (percents).
     *
     * @param value the end reflection opacity
     */
    void setEndReflectionOpacity(double value);

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
     * Returns the direction of reflection in degrees.
     *
     * @return the direction in degrees
     */
    double getDirection();

    /**
     * Sets the direction of reflection in degrees.
     *
     * @param value the direction in degrees
     */
    void setDirection(double value);

    /**
     * Returns the distance of reflection in points.
     *
     * @return the distance
     */
    double getDistance();

    /**
     * Sets the distance of reflection in points.
     *
     * @param value the distance
     */
    void setDistance(double value);

    /**
     * Returns the rectangle alignment.
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
     * Returns the horizontal skew angle in degrees.
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
     * Returns the vertical skew angle in degrees.
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
     * Indicates whether the reflection should rotate with the shape if the shape is rotated.
     *
     * @return {@code true} if the reflection rotates with the shape
     */
    boolean getRotateShadowWithShape();

    /**
     * Sets whether the reflection should rotate with the shape if the shape is rotated.
     *
     * @param value {@code true} to rotate the reflection with the shape
     */
    void setRotateShadowWithShape(boolean value);

    /**
     * Returns the horizontal scaling factor as a percentage. Negative values cause a flip.
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
     * Returns the vertical scaling factor as a percentage. Negative values cause a flip.
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
