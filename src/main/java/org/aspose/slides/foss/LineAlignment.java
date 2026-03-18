package org.aspose.slides.foss;

/**
 * Represents the lines alignment type.
 */
public enum LineAlignment {
    /** Not defined. */
    NOT_DEFINED("NotDefined"),
    /** Center. */
    CENTER("Center"),
    /** Inset. */
    INSET("Inset");

    private final String value;

    LineAlignment(String value) {
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
