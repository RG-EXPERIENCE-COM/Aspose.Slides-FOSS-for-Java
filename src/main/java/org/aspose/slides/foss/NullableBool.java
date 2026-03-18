package org.aspose.slides.foss;

/**
 * Represents triple boolean values.
 */
public enum NullableBool {
    /** Boolean value is undefined. */
    NOT_DEFINED("NotDefined"),
    /** False value. */
    FALSE("False"),
    /** True value. */
    TRUE("True");

    private final String value;

    NullableBool(String value) {
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
