package org.aspose.slides.foss;

/**
 * Constants which define light directions.
 */
public enum LightingDirection {
    /** Not defined. */
    NOT_DEFINED("NotDefined"),
    /** Top Left. */
    TOP_LEFT("TopLeft"),
    /** Top. */
    TOP("Top"),
    /** Top Right. */
    TOP_RIGHT("TopRight"),
    /** Right. */
    RIGHT("Right"),
    /** Bottom Right. */
    BOTTOM_RIGHT("BottomRight"),
    /** Bottom. */
    BOTTOM("Bottom"),
    /** Bottom Left. */
    BOTTOM_LEFT("BottomLeft"),
    /** Left. */
    LEFT("Left");

    private final String value;

    LightingDirection(String value) {
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
