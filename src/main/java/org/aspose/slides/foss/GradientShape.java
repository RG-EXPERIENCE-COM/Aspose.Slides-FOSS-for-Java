package org.aspose.slides.foss;

/**
 * Represents the shape of gradient fill.
 */
public enum GradientShape {
    /** Not defined. */
    NOT_DEFINED("NotDefined"),
    /** Linear. */
    LINEAR("Linear"),
    /** Rectangle. */
    RECTANGLE("Rectangle"),
    /** Radial. */
    RADIAL("Radial"),
    /** Path. */
    PATH("Path");

    private final String value;

    GradientShape(String value) {
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
