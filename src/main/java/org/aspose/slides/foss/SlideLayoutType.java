package org.aspose.slides.foss;

/**
 * Represents the slide layout type.
 */
public enum SlideLayoutType {
    /** Custom. */
    CUSTOM("Custom"),
    /** Title. */
    TITLE("Title"),
    /** Text. */
    TEXT("Text"),
    /** Two Column Text. */
    TWO_COLUMN_TEXT("TwoColumnText"),
    /** Table. */
    TABLE("Table"),
    /** Text and Chart. */
    TEXT_AND_CHART("TextAndChart"),
    /** Chart and Text. */
    CHART_AND_TEXT("ChartAndText"),
    /** Diagram. */
    DIAGRAM("Diagram"),
    /** Chart. */
    CHART("Chart"),
    /** Text and Clip Art. */
    TEXT_AND_CLIP_ART("TextAndClipArt"),
    /** Clip Art and Text. */
    CLIP_ART_AND_TEXT("ClipArtAndText"),
    /** Title Only. */
    TITLE_ONLY("TitleOnly"),
    /** Blank. */
    BLANK("Blank"),
    /** Text and Object. */
    TEXT_AND_OBJECT("TextAndObject"),
    /** Object and Text. */
    OBJECT_AND_TEXT("ObjectAndText"),
    /** Object. */
    OBJECT("Object"),
    /** Title and Object. */
    TITLE_AND_OBJECT("TitleAndObject"),
    /** Text and Media. */
    TEXT_AND_MEDIA("TextAndMedia"),
    /** Media and Text. */
    MEDIA_AND_TEXT("MediaAndText"),
    /** Object Over Text. */
    OBJECT_OVER_TEXT("ObjectOverText"),
    /** Text Over Object. */
    TEXT_OVER_OBJECT("TextOverObject"),
    /** Text and Two Objects. */
    TEXT_AND_TWO_OBJECTS("TextAndTwoObjects"),
    /** Two Objects and Text. */
    TWO_OBJECTS_AND_TEXT("TwoObjectsAndText"),
    /** Two Objects Over Text. */
    TWO_OBJECTS_OVER_TEXT("TwoObjectsOverText"),
    /** Four Objects. */
    FOUR_OBJECTS("FourObjects"),
    /** Vertical Text. */
    VERTICAL_TEXT("VerticalText"),
    /** Clip Art and Vertical Text. */
    CLIP_ART_AND_VERTICAL_TEXT("ClipArtAndVerticalText"),
    /** Vertical Title and Text. */
    VERTICAL_TITLE_AND_TEXT("VerticalTitleAndText"),
    /** Vertical Title and Text Over Chart. */
    VERTICAL_TITLE_AND_TEXT_OVER_CHART("VerticalTitleAndTextOverChart"),
    /** Two Objects. */
    TWO_OBJECTS("TwoObjects"),
    /** Object and Two Object. */
    OBJECT_AND_TWO_OBJECT("ObjectAndTwoObject"),
    /** Two Objects and Object. */
    TWO_OBJECTS_AND_OBJECT("TwoObjectsAndObject"),
    /** Section Header. */
    SECTION_HEADER("SectionHeader"),
    /** Two Text and Two Objects. */
    TWO_TEXT_AND_TWO_OBJECTS("TwoTextAndTwoObjects"),
    /** Title Object and Caption. */
    TITLE_OBJECT_AND_CAPTION("TitleObjectAndCaption"),
    /** Picture and Caption. */
    PICTURE_AND_CAPTION("PictureAndCaption");

    private static final java.util.Map<String, SlideLayoutType> BY_VALUE;

    static {
        var map = new java.util.HashMap<String, SlideLayoutType>();
        for (SlideLayoutType type : values()) {
            map.put(type.value, type);
        }
        BY_VALUE = java.util.Map.copyOf(map);
    }

    private final String value;

    SlideLayoutType(String value) {
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

    /**
     * Returns the enum constant matching the given string value, or {@link #CUSTOM}
     * if no match is found.
     *
     * @param value the string value to look up
     * @return the matching constant, or {@code CUSTOM}
     */
    public static SlideLayoutType fromValue(String value) {
        if (value == null) {
            return CUSTOM;
        }
        return BY_VALUE.getOrDefault(value, CUSTOM);
    }
}
