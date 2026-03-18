package org.aspose.slides.foss;

/**
 * Determines how picture will fill area.
 */
public enum PictureFillMode {
    /** Tile. */
    TILE("Tile"),
    /** Stretch. */
    STRETCH("Stretch");

    private final String value;

    PictureFillMode(String value) {
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
