package org.aspose.slides.foss;

/**
 * Represents the type of the extended bullets.
 */
public enum BulletType {
    /** Not defined. */
    NOT_DEFINED("NotDefined"),
    /** None. */
    NONE("None"),
    /** Symbol bullets. */
    SYMBOL("Symbol"),
    /** Numbered bullets. */
    NUMBERED("Numbered"),
    /** Picture bullets. */
    PICTURE("Picture");

    private final String value;

    BulletType(String value) {
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
