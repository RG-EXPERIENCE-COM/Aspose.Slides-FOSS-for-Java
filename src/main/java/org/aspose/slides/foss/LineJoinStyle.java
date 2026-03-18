package org.aspose.slides.foss;

/**
 * Represents the lines join style.
 */
public enum LineJoinStyle {
    /** Not defined. */
    NOT_DEFINED("NotDefined"),
    /** Round. */
    ROUND("Round"),
    /** Bevel. */
    BEVEL("Bevel"),
    /** Miter. */
    MITER("Miter");

    private final String value;

    LineJoinStyle(String value) {
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
