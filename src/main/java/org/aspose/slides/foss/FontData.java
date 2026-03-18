package org.aspose.slides.foss;

import java.util.Objects;

/**
 * Represents a font definition identified by font name.
 *
 * @param fontName the typeface name of the font
 */
public record FontData(String fontName) implements IFontData {

    /**
     * Creates a new FontData with the given font name.
     *
     * @param fontName the typeface name; must not be {@code null}
     */
    public FontData {
        Objects.requireNonNull(fontName, "fontName must not be null");
    }

    @Override
    public String getFontName() {
        return fontName;
    }

    /**
     * Returns the font name resolved against the given theme.
     * <p>This default implementation ignores the theme and returns the
     * font name as-is.</p>
     *
     * @param theme the theme used to resolve the font name
     * @return the resolved font name
     */
    @Override
    public String getFontName(Object theme) {
        return fontName;
    }
}
