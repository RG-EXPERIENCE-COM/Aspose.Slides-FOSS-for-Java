package org.aspose.slides.foss;

/**
 * Determines blend mode.
 */
public enum FillBlendMode {
    /** Darken. */
    DARKEN("Darken"),
    /** Lighten. */
    LIGHTEN("Lighten"),
    /** Multiply. */
    MULTIPLY("Multiply"),
    /** Overlay. */
    OVERLAY("Overlay"),
    /** Screen. */
    SCREEN("Screen");

    private final String value;

    FillBlendMode(String value) {
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
