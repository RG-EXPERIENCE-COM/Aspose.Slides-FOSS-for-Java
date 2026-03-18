package org.aspose.slides.foss;

/**
 * Represents the type of text capitalisation.
 */
public enum TextCapType {
    /** Not defined. */
    NOT_DEFINED("NotDefined"),
    /** None. */
    NONE("None"),
    /** Small. */
    SMALL("Small"),
    /** All. */
    ALL("All");

    private final String value;

    TextCapType(String value) {
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
