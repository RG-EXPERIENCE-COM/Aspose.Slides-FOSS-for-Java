package org.aspose.slides.foss;

/**
 * Represents the gradient style.
 */
public enum GradientDirection {
    /** Not defined. */
    NOT_DEFINED("NotDefined"),
    /** From Top Left Corner. */
    FROM_CORNER1("FromCorner1"),
    /** From Top Right Corner. */
    FROM_CORNER2("FromCorner2"),
    /** From Bottom Left Corner. */
    FROM_CORNER3("FromCorner3"),
    /** From Bottom Right Corner. */
    FROM_CORNER4("FromCorner4"),
    /** From Center. */
    FROM_CENTER("FromCenter");

    private final String value;

    GradientDirection(String value) {
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
