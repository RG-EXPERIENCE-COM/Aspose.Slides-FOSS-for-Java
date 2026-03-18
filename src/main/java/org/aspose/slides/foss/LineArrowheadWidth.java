package org.aspose.slides.foss;

/**
 * Represents the width of an arrowhead.
 */
public enum LineArrowheadWidth {
    /** Not defined. */
    NOT_DEFINED("NotDefined"),
    /** Narrow. */
    NARROW("Narrow"),
    /** Medium. */
    MEDIUM("Medium"),
    /** Wide. */
    WIDE("Wide");

    private final String value;

    LineArrowheadWidth(String value) {
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
