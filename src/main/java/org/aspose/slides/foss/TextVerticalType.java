package org.aspose.slides.foss;

/**
 * Determines vertical writing mode for a text.
 */
public enum TextVerticalType {
    /** Not defined. */
    NOT_DEFINED("NotDefined"),
    /** Horizontal. */
    HORIZONTAL("Horizontal"),
    /** Vertical. */
    VERTICAL("Vertical"),
    /** Vertical 270. */
    VERTICAL270("Vertical270"),
    /** Word Art Vertical. */
    WORD_ART_VERTICAL("WordArtVertical"),
    /** East Asian Vertical. */
    EAST_ASIAN_VERTICAL("EastAsianVertical"),
    /** Mongolian Vertical. */
    MONGOLIAN_VERTICAL("MongolianVertical"),
    /** Word Art Vertical Right to Left. */
    WORD_ART_VERTICAL_RIGHT_TO_LEFT("WordArtVerticalRightToLeft");

    private final String value;

    TextVerticalType(String value) {
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
