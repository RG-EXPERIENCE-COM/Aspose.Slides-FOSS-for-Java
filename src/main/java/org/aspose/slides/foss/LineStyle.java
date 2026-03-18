package org.aspose.slides.foss;

/**
 * Represents the style of a line.
 */
public enum LineStyle {
    /** Not defined. */
    NOT_DEFINED("NotDefined"),
    /** Single. */
    SINGLE("Single"),
    /** Thin Thin. */
    THIN_THIN("ThinThin"),
    /** Thick Thin. */
    THICK_THIN("ThickThin"),
    /** Thin Thick. */
    THIN_THICK("ThinThick"),
    /** Thick Between Thin. */
    THICK_BETWEEN_THIN("ThickBetweenThin");

    private final String value;

    LineStyle(String value) {
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
