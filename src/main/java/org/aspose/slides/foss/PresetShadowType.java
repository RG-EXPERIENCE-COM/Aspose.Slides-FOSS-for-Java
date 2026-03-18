package org.aspose.slides.foss;

/**
 * Represents a preset for a shadow effect.
 */
public enum PresetShadowType {
    /** Top Left Drop Shadow. */
    TOP_LEFT_DROP_SHADOW("TopLeftDropShadow"),
    /** Top Left Large Drop Shadow. */
    TOP_LEFT_LARGE_DROP_SHADOW("TopLeftLargeDropShadow"),
    /** Back Left Long Perspective Shadow. */
    BACK_LEFT_LONG_PERSPECTIVE_SHADOW("BackLeftLongPerspectiveShadow"),
    /** Back Right Long Perspective Shadow. */
    BACK_RIGHT_LONG_PERSPECTIVE_SHADOW("BackRightLongPerspectiveShadow"),
    /** Top Left Double Drop Shadow. */
    TOP_LEFT_DOUBLE_DROP_SHADOW("TopLeftDoubleDropShadow"),
    /** Bottom Right Small Drop Shadow. */
    BOTTOM_RIGHT_SMALL_DROP_SHADOW("BottomRightSmallDropShadow"),
    /** Front Left Long Perspective Shadow. */
    FRONT_LEFT_LONG_PERSPECTIVE_SHADOW("FrontLeftLongPerspectiveShadow"),
    /** Front Right Long Perspective Shadow. */
    FRONT_RIGHT_LONG_PERSPECTIVE_SHADOW("FrontRightLongPerspectiveShadow"),
    /** Outer Box Shadow 3D. */
    OUTER_BOX_SHADOW_3D("OuterBoxShadow3D"),
    /** Inner Box Shadow 3D. */
    INNER_BOX_SHADOW_3D("InnerBoxShadow3D"),
    /** Back Center Perspective Shadow. */
    BACK_CENTER_PERSPECTIVE_SHADOW("BackCenterPerspectiveShadow"),
    /** Top Right Drop Shadow. */
    TOP_RIGHT_DROP_SHADOW("TopRightDropShadow"),
    /** Front Bottom Shadow. */
    FRONT_BOTTOM_SHADOW("FrontBottomShadow"),
    /** Back Left Perspective Shadow. */
    BACK_LEFT_PERSPECTIVE_SHADOW("BackLeftPerspectiveShadow"),
    /** Back Right Perspective Shadow. */
    BACK_RIGHT_PERSPECTIVE_SHADOW("BackRightPerspectiveShadow"),
    /** Bottom Left Drop Shadow. */
    BOTTOM_LEFT_DROP_SHADOW("BottomLeftDropShadow"),
    /** Bottom Right Drop Shadow. */
    BOTTOM_RIGHT_DROP_SHADOW("BottomRightDropShadow"),
    /** Front Left Perspective Shadow. */
    FRONT_LEFT_PERSPECTIVE_SHADOW("FrontLeftPerspectiveShadow"),
    /** Front Right Perspective Shadow. */
    FRONT_RIGHT_PERSPECTIVE_SHADOW("FrontRightPerspectiveShadow"),
    /** Top Left Small Drop Shadow. */
    TOP_LEFT_SMALL_DROP_SHADOW("TopLeftSmallDropShadow");

    private final String value;

    PresetShadowType(String value) {
        this.value = value;
    }

    /**
     * Returns the string value of this constant.
     *
     * @return the string value
     */
    public String getValue() {
        return value;
    }
}
