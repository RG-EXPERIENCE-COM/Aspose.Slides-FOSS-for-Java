package org.aspose.slides.foss;

/**
 * Represents the type of text strikethrough.
 */
public enum TextStrikethroughType {
    /** Not defined. */
    NOT_DEFINED("NotDefined"),
    /** None. */
    NONE("None"),
    /** Single. */
    SINGLE("Single"),
    /** Double. */
    DOUBLE("Double");

    private final String value;

    TextStrikethroughType(String value) {
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
