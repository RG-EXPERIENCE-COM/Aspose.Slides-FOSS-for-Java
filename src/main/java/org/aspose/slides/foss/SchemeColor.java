package org.aspose.slides.foss;

/**
 * Represents colors in a color scheme.
 */
public enum SchemeColor {
    /** Not defined. */
    NOT_DEFINED("NotDefined"),
    /** Background 1. */
    BACKGROUND1("Background1"),
    /** Text 1. */
    TEXT1("Text1"),
    /** Background 2. */
    BACKGROUND2("Background2"),
    /** Text 2. */
    TEXT2("Text2"),
    /** Accent 1. */
    ACCENT1("Accent1"),
    /** Accent 2. */
    ACCENT2("Accent2"),
    /** Accent 3. */
    ACCENT3("Accent3"),
    /** Accent 4. */
    ACCENT4("Accent4"),
    /** Accent 5. */
    ACCENT5("Accent5"),
    /** Accent 6. */
    ACCENT6("Accent6"),
    /** Hyperlink. */
    HYPERLINK("Hyperlink"),
    /** Followed Hyperlink. */
    FOLLOWED_HYPERLINK("FollowedHyperlink"),
    /** Style Color. */
    STYLE_COLOR("StyleColor"),
    /** Dark 1. */
    DARK1("Dark1"),
    /** Light 1. */
    LIGHT1("Light1"),
    /** Dark 2. */
    DARK2("Dark2"),
    /** Light 2. */
    LIGHT2("Light2");

    private final String value;

    SchemeColor(String value) {
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
