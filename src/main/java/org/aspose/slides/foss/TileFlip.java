package org.aspose.slides.foss;

/**
 * Defines tile flipping mode.
 */
public enum TileFlip {
    /** Not defined. */
    NOT_DEFINED("NotDefined"),
    /** No Flip. */
    NO_FLIP("NoFlip"),
    /** Flip X. */
    FLIP_X("FlipX"),
    /** Flip Y. */
    FLIP_Y("FlipY"),
    /** Flip Both. */
    FLIP_BOTH("FlipBoth");

    private final String value;

    TileFlip(String value) {
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
