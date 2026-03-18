package org.aspose.slides.foss;

/**
 * Represents a light rig.
 */
public interface ILightRig {

    /**
     * Returns the light direction. Read/write.
     *
     * @return the lighting direction
     */
    LightingDirection getDirection();

    /**
     * Sets the light direction.
     *
     * @param value the lighting direction
     */
    void setDirection(LightingDirection value);

    /**
     * Returns the light type. Read/write.
     *
     * @return the light rig preset type
     */
    LightRigPresetType getLightType();

    /**
     * Sets the light type.
     *
     * @param value the light rig preset type
     */
    void setLightType(LightRigPresetType value);

    /**
     * Sets the rotation of the light rig.
     *
     * @param latitude   the latitude in degrees
     * @param longitude  the longitude in degrees
     * @param revolution the revolution in degrees
     */
    void setRotation(double latitude, double longitude, double revolution);

    /**
     * Gets the rotation as [latitude, longitude, revolution] in degrees.
     *
     * @return an array of three doubles
     */
    double[] getRotation();
}
