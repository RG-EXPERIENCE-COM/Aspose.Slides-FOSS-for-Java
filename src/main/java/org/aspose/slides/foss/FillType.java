package org.aspose.slides.foss;

/**
 * Specifies the interior fill type of various visual objects.
 */
public enum FillType {
    /** Not defined. */
    NOT_DEFINED("NotDefined"),
    /** No fill. */
    NO_FILL("NoFill"),
    /** Solid. */
    SOLID("Solid"),
    /** Gradient. */
    GRADIENT("Gradient"),
    /** Pattern. */
    PATTERN("Pattern"),
    /** Picture. */
    PICTURE("Picture"),
    /** Group. */
    GROUP("Group");

    private final String value;

    FillType(String value) {
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
