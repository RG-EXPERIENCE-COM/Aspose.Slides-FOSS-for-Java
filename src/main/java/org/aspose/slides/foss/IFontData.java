package org.aspose.slides.foss;

/**
 * Represents a font definition.
 */
public interface IFontData {

    /**
     * Returns the font name.
     *
     * @return the font name
     */
    String getFontName();

    /**
     * Returns the font name, resolved against the given theme.
     *
     * @param theme the theme used to resolve the font name
     * @return the resolved font name
     */
    String getFontName(Object theme);
}
