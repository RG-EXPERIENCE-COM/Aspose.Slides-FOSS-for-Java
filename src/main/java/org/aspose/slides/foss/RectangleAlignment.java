package org.aspose.slides.foss;

/**
 * Defines 2-dimension alignment.
 */
public enum RectangleAlignment {
    /** Not defined. */
    NOT_DEFINED("NotDefined"),
    /** Top Left. */
    TOP_LEFT("TopLeft"),
    /** Top. */
    TOP("Top"),
    /** Top Right. */
    TOP_RIGHT("TopRight"),
    /** Left. */
    LEFT("Left"),
    /** Center. */
    CENTER("Center"),
    /** Right. */
    RIGHT("Right"),
    /** Bottom Left. */
    BOTTOM_LEFT("BottomLeft"),
    /** Bottom. */
    BOTTOM("Bottom"),
    /** Bottom Right. */
    BOTTOM_RIGHT("BottomRight");

    private final String value;

    RectangleAlignment(String value) {
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
