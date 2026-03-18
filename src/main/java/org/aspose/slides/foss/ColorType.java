package org.aspose.slides.foss;

/**
 * Represents different color modes.
 */
public enum ColorType {
    /** Color is not defined at all. */
    NOT_DEFINED("NotDefined"),
    /** Standard 24bit RGB color. */
    RGB("RGB"),
    /** High definition RGB color. */
    RGB_PERCENTAGE("RGBPercentage"),
    /** High definition HSL color. */
    HSL("HSL"),
    /** Scheme color. */
    SCHEME("Scheme"),
    /** System color. */
    SYSTEM("System"),
    /** Preset Color. */
    PRESET("Preset");

    private final String value;

    ColorType(String value) {
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
