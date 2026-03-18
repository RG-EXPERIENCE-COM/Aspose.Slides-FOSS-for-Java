package org.aspose.slides.foss;

/**
 * Represents the line dash style.
 */
public enum LineDashStyle {
    /** Not defined. */
    NOT_DEFINED("NotDefined"),
    /** Solid. */
    SOLID("Solid"),
    /** Dot. */
    DOT("Dot"),
    /** Dash. */
    DASH("Dash"),
    /** Large dash. */
    LARGE_DASH("LargeDash"),
    /** Dash-dot. */
    DASH_DOT("DashDot"),
    /** Large dash-dot. */
    LARGE_DASH_DOT("LargeDashDot"),
    /** Large dash-dot-dot. */
    LARGE_DASH_DOT_DOT("LargeDashDotDot"),
    /** System dash. */
    SYSTEM_DASH("SystemDash"),
    /** System dot. */
    SYSTEM_DOT("SystemDot"),
    /** System dash-dot. */
    SYSTEM_DASH_DOT("SystemDashDot"),
    /** System dash-dot-dot. */
    SYSTEM_DASH_DOT_DOT("SystemDashDotDot"),
    /** Custom dash pattern. */
    CUSTOM("Custom");

    private final String value;

    LineDashStyle(String value) {
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
