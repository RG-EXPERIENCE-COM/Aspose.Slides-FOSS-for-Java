package org.aspose.slides.foss;

/**
 * Represents the type of text underline.
 */
public enum TextUnderlineType {
    /** Not defined. */
    NOT_DEFINED("NotDefined"),
    /** None. */
    NONE("None"),
    /** Words. */
    WORDS("Words"),
    /** Single. */
    SINGLE("Single"),
    /** Double. */
    DOUBLE("Double"),
    /** Heavy. */
    HEAVY("Heavy"),
    /** Dotted. */
    DOTTED("Dotted"),
    /** Heavy Dotted. */
    HEAVY_DOTTED("HeavyDotted"),
    /** Dashed. */
    DASHED("Dashed"),
    /** Heavy Dashed. */
    HEAVY_DASHED("HeavyDashed"),
    /** Long Dashed. */
    LONG_DASHED("LongDashed"),
    /** Heavy Long Dashed. */
    HEAVY_LONG_DASHED("HeavyLongDashed"),
    /** Dot Dash. */
    DOT_DASH("DotDash"),
    /** Heavy Dot Dash. */
    HEAVY_DOT_DASH("HeavyDotDash"),
    /** Dot Dot Dash. */
    DOT_DOT_DASH("DotDotDash"),
    /** Heavy Dot Dot Dash. */
    HEAVY_DOT_DOT_DASH("HeavyDotDotDash"),
    /** Wavy. */
    WAVY("Wavy"),
    /** Heavy Wavy. */
    HEAVY_WAVY("HeavyWavy"),
    /** Double Wavy. */
    DOUBLE_WAVY("DoubleWavy");

    private final String value;

    TextUnderlineType(String value) {
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
