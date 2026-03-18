package org.aspose.slides.foss;

/**
 * Represents the length of an arrowhead.
 */
public enum LineArrowheadLength {
    /** Not defined. */
    NOT_DEFINED("NotDefined"),
    /** Short. */
    SHORT("Short"),
    /** Medium. */
    MEDIUM("Medium"),
    /** Long. */
    LONG("Long");

    private final String value;

    LineArrowheadLength(String value) {
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
