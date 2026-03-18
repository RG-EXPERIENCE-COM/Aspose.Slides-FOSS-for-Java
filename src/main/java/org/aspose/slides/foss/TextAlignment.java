package org.aspose.slides.foss;

/**
 * Represents different text alignment styles.
 */
public enum TextAlignment {
    /** Not defined. */
    NOT_DEFINED("NotDefined"),
    /** Left. */
    LEFT("Left"),
    /** Center. */
    CENTER("Center"),
    /** Right. */
    RIGHT("Right"),
    /** Justify. */
    JUSTIFY("Justify"),
    /** Justify Low. */
    JUSTIFY_LOW("JustifyLow"),
    /** Distributed. */
    DISTRIBUTED("Distributed");

    private final String value;

    TextAlignment(String value) {
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
