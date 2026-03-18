package org.aspose.slides.foss;

/**
 * Represents vertical font alignment.
 */
public enum FontAlignment {
    /** Default. */
    DEFAULT("Default"),
    /** Automatic. */
    AUTOMATIC("Automatic"),
    /** Top. */
    TOP("Top"),
    /** Center. */
    CENTER("Center"),
    /** Bottom. */
    BOTTOM("Bottom"),
    /** Baseline. */
    BASELINE("Baseline");

    private final String value;

    FontAlignment(String value) {
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
