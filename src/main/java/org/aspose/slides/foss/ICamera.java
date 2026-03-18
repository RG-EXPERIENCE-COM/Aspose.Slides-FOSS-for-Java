package org.aspose.slides.foss;

/**
 * Represents Camera.
 */
public interface ICamera {

    /**
     * Camera type. Read/write.
     *
     * @return the camera preset type
     */
    CameraPresetType getCameraType();

    /**
     * Sets the camera type.
     *
     * @param value the camera preset type
     */
    void setCameraType(CameraPresetType value);

    /**
     * Camera FOV (0-180 deg, field of view). Read/write.
     *
     * @return the field of view angle in degrees
     */
    double getFieldOfViewAngle();

    /**
     * Sets the camera FOV.
     *
     * @param value the field of view angle in degrees
     */
    void setFieldOfViewAngle(double value);

    /**
     * Camera zoom (positive value in percentage). Read/write.
     *
     * @return the zoom percentage
     */
    double getZoom();

    /**
     * Sets the camera zoom.
     *
     * @param value the zoom percentage
     */
    void setZoom(double value);

    /**
     * Sets the camera rotation.
     *
     * @param latitude   the latitude in degrees
     * @param longitude  the longitude in degrees
     * @param revolution the revolution in degrees
     */
    void setRotation(double latitude, double longitude, double revolution);

    /**
     * Gets the camera rotation as [latitude, longitude, revolution] in degrees.
     *
     * @return an array of three doubles
     */
    double[] getRotation();
}
