package org.aspose.slides.foss;

/**
 * Represents text autofit mode.
 */
public enum TextAutofitType {
    /** Not defined. */
    NOT_DEFINED("NotDefined"),
    /** None. */
    NONE("None"),
    /** Font size and line spacing will be reduced to fit the shape. */
    NORMAL("Normal"),
    /** Shape size will be changed to fit the text. */
    SHAPE("Shape");

    private final String value;

    TextAutofitType(String value) {
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
