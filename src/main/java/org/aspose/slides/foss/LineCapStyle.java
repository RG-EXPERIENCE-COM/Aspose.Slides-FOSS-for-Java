package org.aspose.slides.foss;

/**
 * Represents the line cap style.
 */
public enum LineCapStyle {
    /** Not defined. */
    NOT_DEFINED("NotDefined"),
    /** Round. */
    ROUND("Round"),
    /** Square. */
    SQUARE("Square"),
    /** Flat. */
    FLAT("Flat");

    private final String value;

    LineCapStyle(String value) {
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
