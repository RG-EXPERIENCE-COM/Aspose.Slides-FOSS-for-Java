package org.aspose.slides.foss;

/**
 * text box alignment within a text area.
 */
public enum TextAnchorType {
    /** Not defined. */
    NOT_DEFINED("NotDefined"),
    /** Top. */
    TOP("Top"),
    /** Center. */
    CENTER("Center"),
    /** Bottom. */
    BOTTOM("Bottom"),
    /** Justified. */
    JUSTIFIED("Justified"),
    /** Distributed. */
    DISTRIBUTED("Distributed");

    private final String value;

    TextAnchorType(String value) {
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
