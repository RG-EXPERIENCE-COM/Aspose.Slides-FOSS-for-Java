package org.aspose.slides.foss;

/**
 * Represents a pattern fill format.
 */
public interface IPatternFormat {

    /**
     * Returns or sets the pattern style. Read/write.
     *
     * @return the pattern style
     */
    PatternStyle getPatternStyle();

    /**
     * Sets the pattern style.
     *
     * @param value the pattern style
     */
    void setPatternStyle(PatternStyle value);

    /**
     * Returns the foreground pattern color. Read-only.
     *
     * @return the foreground color format
     */
    IColorFormat getForeColor();

    /**
     * Returns the background pattern color. Read-only.
     *
     * @return the background color format
     */
    IColorFormat getBackColor();
}
