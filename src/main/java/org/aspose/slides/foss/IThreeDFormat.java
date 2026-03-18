package org.aspose.slides.foss;

/**
 * Represents 3-D properties.
 */
public interface IThreeDFormat extends IThreeDParamSource {

    /**
     * Returns the width of an extrusion contour. Read/write.
     *
     * @return the contour width
     */
    double getContourWidth();

    /**
     * Sets the width of an extrusion contour.
     *
     * @param value the contour width
     */
    void setContourWidth(double value);

    /**
     * Returns the height of an extrusion effect. Read/write.
     *
     * @return the extrusion height
     */
    double getExtrusionHeight();

    /**
     * Sets the height of an extrusion effect.
     *
     * @param value the extrusion height
     */
    void setExtrusionHeight(double value);

    /**
     * Returns the depth of a 3D shape. Read/write.
     *
     * @return the depth
     */
    double getDepth();

    /**
     * Sets the depth of a 3D shape.
     *
     * @param value the depth
     */
    void setDepth(double value);

    /**
     * Returns the top bevel. Read-only {@link IShapeBevel}.
     *
     * @return the top bevel
     */
    IShapeBevel getBevelTop();

    /**
     * Returns the bottom bevel. Read-only {@link IShapeBevel}.
     *
     * @return the bottom bevel
     */
    IShapeBevel getBevelBottom();

    /**
     * Returns the contour color. Read-only {@link IColorFormat}.
     *
     * @return the contour color
     */
    IColorFormat getContourColor();

    /**
     * Returns the extrusion color. Read-only {@link IColorFormat}.
     *
     * @return the extrusion color
     */
    IColorFormat getExtrusionColor();

    /**
     * Returns the camera settings. Read-only {@link ICamera}.
     *
     * @return the camera
     */
    ICamera getCamera();

    /**
     * Returns the light rig settings. Read-only {@link ILightRig}.
     *
     * @return the light rig
     */
    ILightRig getLightRig();

    /**
     * Returns the material type. Read/write.
     *
     * @return the material preset type
     */
    MaterialPresetType getMaterial();

    /**
     * Sets the material type.
     *
     * @param value the material preset type
     */
    void setMaterial(MaterialPresetType value);
}
