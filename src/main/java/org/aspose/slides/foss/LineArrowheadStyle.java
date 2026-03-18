package org.aspose.slides.foss;

/**
 * Represents the style of an arrowhead.
 */
public enum LineArrowheadStyle {
    /** Not defined. */
    NOT_DEFINED("NotDefined"),
    /** None. */
    NONE("None"),
    /** Triangle. */
    TRIANGLE("Triangle"),
    /** Stealth. */
    STEALTH("Stealth"),
    /** Diamond. */
    DIAMOND("Diamond"),
    /** Oval. */
    OVAL("Oval"),
    /** Open. */
    OPEN("Open");

    private final String value;

    LineArrowheadStyle(String value) {
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
