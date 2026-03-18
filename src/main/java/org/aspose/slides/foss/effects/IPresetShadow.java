package org.aspose.slides.foss.effects;

import org.aspose.slides.foss.IColorFormat;
import org.aspose.slides.foss.PresetShadowType;

/**
 * Represents a Preset Shadow effect.
 */
public interface IPresetShadow extends IImageTransformOperation {

    /**
     * Returns the direction of the shadow in degrees. Read/write.
     *
     * @return the direction in degrees
     */
    double getDirection();

    /**
     * Sets the direction of the shadow in degrees.
     *
     * @param value the direction in degrees
     */
    void setDirection(double value);

    /**
     * Returns the distance of the shadow in points. Read/write.
     *
     * @return the distance in points
     */
    double getDistance();

    /**
     * Sets the distance of the shadow in points.
     *
     * @param value the distance in points
     */
    void setDistance(double value);

    /**
     * Returns the shadow color format. Read-only {@link IColorFormat}.
     *
     * @return the color format
     */
    IColorFormat getShadowColor();

    /**
     * Returns the preset shadow type. Read/write {@link PresetShadowType}.
     *
     * @return the preset shadow type
     */
    PresetShadowType getPreset();

    /**
     * Sets the preset shadow type.
     *
     * @param value the preset shadow type
     */
    void setPreset(PresetShadowType value);
}
