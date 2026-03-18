package org.aspose.slides.foss;

/**
 * Represents common text portion formatting properties.
 *
 * <p>This interface defines the contract for accessing and modifying run-level
 * text properties backed by OOXML {@code <a:rPr>} elements.</p>
 */
public interface IBasePortionFormat {

    /**
     * Returns the LineFormat properties for text outlining. No inheritance applied. Read-only.
     *
     * @return the line format, or {@code null} if not available
     */
    ILineFormat getLineFormat();

    /**
     * Returns the text FillFormat properties. No inheritance applied. Read-only.
     *
     * @return the fill format, or {@code null} if not available
     */
    IFillFormat getFillFormat();

    /**
     * Returns the text EffectFormat properties. No inheritance applied. Read-only.
     *
     * @return the effect format, or {@code null} if not available
     */
    IEffectFormat getEffectFormat();

    /**
     * Returns the color used to highlight a text. No inheritance applied. Read-only.
     *
     * @return the highlight color format, or {@code null} if not available
     */
    IColorFormat getHighlightColor();

    /**
     * Returns the LineFormat properties used to outline underline line. No inheritance applied. Read-only.
     *
     * @return the underline line format, or {@code null} if not available
     */
    ILineFormat getUnderlineLineFormat();

    /**
     * Returns the underline line FillFormat properties. No inheritance applied. Read-only.
     *
     * @return the underline fill format, or {@code null} if not available
     */
    IFillFormat getUnderlineFillFormat();

    /**
     * Determines whether the font is bold. No inheritance applied.
     *
     * @return the bold state
     */
    NullableBool getFontBold();

    /**
     * Sets whether the font is bold. No inheritance applied.
     *
     * @param value the bold state
     */
    void setFontBold(NullableBool value);

    /**
     * Determines whether the font is italic. No inheritance applied.
     *
     * @return the italic state
     */
    NullableBool getFontItalic();

    /**
     * Sets whether the font is italic. No inheritance applied.
     *
     * @param value the italic state
     */
    void setFontItalic(NullableBool value);

    /**
     * Determines whether the numbers should ignore text eastern language-specific vertical text layout.
     * No inheritance applied.
     *
     * @return the kumimoji state
     */
    NullableBool getKumimoji();

    /**
     * Sets whether the numbers should ignore text eastern language-specific vertical text layout.
     * No inheritance applied.
     *
     * @param value the kumimoji state
     */
    void setKumimoji(NullableBool value);

    /**
     * Determines whether the height of a text should be normalized. No inheritance applied.
     *
     * @return the normalise height state
     */
    NullableBool getNormaliseHeight();

    /**
     * Sets whether the height of a text should be normalized. No inheritance applied.
     *
     * @param value the normalise height state
     */
    void setNormaliseHeight(NullableBool value);

    /**
     * Determines whether the text shouldn't be proofed. No inheritance applied.
     *
     * @return the proof disabled state
     */
    NullableBool getProofDisabled();

    /**
     * Sets whether the text shouldn't be proofed. No inheritance applied.
     *
     * @param value the proof disabled state
     */
    void setProofDisabled(NullableBool value);

    /**
     * Returns the text underline type. No inheritance applied.
     *
     * @return the underline type
     */
    TextUnderlineType getFontUnderline();

    /**
     * Sets the text underline type. No inheritance applied.
     *
     * @param value the underline type
     */
    void setFontUnderline(TextUnderlineType value);

    /**
     * Returns the type of text capitalization. No inheritance applied.
     *
     * @return the cap type
     */
    TextCapType getTextCapType();

    /**
     * Sets the type of text capitalization. No inheritance applied.
     *
     * @param value the cap type
     */
    void setTextCapType(TextCapType value);

    /**
     * Returns the strikethrough type of a text. No inheritance applied.
     *
     * @return the strikethrough type
     */
    TextStrikethroughType getStrikethroughType();

    /**
     * Sets the strikethrough type of a text. No inheritance applied.
     *
     * @param value the strikethrough type
     */
    void setStrikethroughType(TextStrikethroughType value);

    /**
     * Determines whether the underline style has own LineFormat properties or inherits from text.
     *
     * @return the hard underline line state
     */
    NullableBool getIsHardUnderlineLine();

    /**
     * Sets whether the underline style has own LineFormat properties or inherits from text.
     *
     * @param value the hard underline line state
     */
    void setIsHardUnderlineLine(NullableBool value);

    /**
     * Determines whether the underline style has own FillFormat properties or inherits from text.
     *
     * @return the hard underline fill state
     */
    NullableBool getIsHardUnderlineFill();

    /**
     * Sets whether the underline style has own FillFormat properties or inherits from text.
     *
     * @param value the hard underline fill state
     */
    void setIsHardUnderlineFill(NullableBool value);

    /**
     * Returns the font height of a portion. {@code Float.NaN} means height is undefined
     * and should be inherited from the Master.
     *
     * @return the font height in points
     */
    float getFontHeight();

    /**
     * Sets the font height of a portion. {@code Float.NaN} clears the attribute.
     *
     * @param value the font height in points
     */
    void setFontHeight(float value);

    /**
     * Returns the superscript or subscript text value from -100% (subscript) to 100% (superscript).
     * {@code Float.NaN} means value is undefined.
     *
     * @return the escapement percentage
     */
    float getEscapement();

    /**
     * Sets the superscript or subscript text value.
     * {@code Float.NaN} clears the attribute.
     *
     * @param value the escapement percentage
     */
    void setEscapement(float value);

    /**
     * Returns the minimal font size for which kerning should be switched on.
     * {@code Float.NaN} means value is undefined.
     *
     * @return the kerning minimal size in points
     */
    float getKerningMinimalSize();

    /**
     * Sets the minimal font size for which kerning should be switched on.
     * {@code Float.NaN} clears the attribute.
     *
     * @param value the kerning minimal size in points
     */
    void setKerningMinimalSize(float value);

    /**
     * Returns the intercharacter spacing increment.
     * {@code Float.NaN} means value is undefined.
     *
     * @return the spacing in points
     */
    float getSpacing();

    /**
     * Sets the intercharacter spacing increment.
     * {@code Float.NaN} clears the attribute.
     *
     * @param value the spacing in points
     */
    void setSpacing(float value);

    /**
     * Returns the Latin font info. {@code null} means font is undefined.
     *
     * @return the Latin font data, or {@code null}
     */
    IFontData getLatinFont();

    /**
     * Sets the Latin font info. {@code null} removes the font element.
     *
     * @param value the Latin font data
     */
    void setLatinFont(IFontData value);

    /**
     * Returns the East Asian font info. {@code null} means font is undefined.
     *
     * @return the East Asian font data, or {@code null}
     */
    IFontData getEastAsianFont();

    /**
     * Sets the East Asian font info. {@code null} removes the font element.
     *
     * @param value the East Asian font data
     */
    void setEastAsianFont(IFontData value);

    /**
     * Returns the complex script font info. {@code null} means font is undefined.
     *
     * @return the complex script font data, or {@code null}
     */
    IFontData getComplexScriptFont();

    /**
     * Sets the complex script font info. {@code null} removes the font element.
     *
     * @param value the complex script font data
     */
    void setComplexScriptFont(IFontData value);

    /**
     * Returns the symbolic font info. {@code null} means font is undefined.
     *
     * @return the symbol font data, or {@code null}
     */
    IFontData getSymbolFont();

    /**
     * Sets the symbolic font info. {@code null} removes the font element.
     *
     * @param value the symbol font data
     */
    void setSymbolFont(IFontData value);

    /**
     * Returns the Id of a proofing language. Used for checking spelling and grammar.
     *
     * @return the language id, or {@code null}
     */
    String getLanguageId();

    /**
     * Sets the Id of a proofing language.
     *
     * @param value the language id; {@code null} removes the attribute
     */
    void setLanguageId(String value);

    /**
     * Returns the Id of an alternative language.
     *
     * @return the alternative language id, or {@code null}
     */
    String getAlternativeLanguageId();

    /**
     * Sets the Id of an alternative language.
     *
     * @param value the alternative language id; {@code null} removes the attribute
     */
    void setAlternativeLanguageId(String value);

    /**
     * Gets a value indicating whether spell checking is enabled for the text portion.
     *
     * @return {@code true} if spell checking is enabled
     */
    boolean getSpellCheck();

    /**
     * Sets a value indicating whether spell checking is enabled for the text portion.
     *
     * @param value {@code true} to enable spell checking
     */
    void setSpellCheck(boolean value);
}
