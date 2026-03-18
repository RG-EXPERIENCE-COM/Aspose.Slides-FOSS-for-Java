package org.aspose.slides.foss;

/**
 * Represents source file format.
 */
public enum SourceFormat {
    /** PPT format. */
    PPT("Ppt"),
    /** PPTX format. */
    PPTX("Pptx"),
    /** ODP format. */
    ODP("Odp");

    private final String value;

    SourceFormat(String value) {
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
