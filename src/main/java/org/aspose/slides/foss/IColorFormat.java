package org.aspose.slides.foss;

import org.aspose.slides.foss.drawing.Color;

/**
 * Represents a color used in a presentation.
 *
 * <p>Provides access to the color value through multiple representations:
 * RGB, scheme color, preset color, and individual component access.</p>
 */
public interface IColorFormat extends IFillParamSource {

    /**
     * Returns the color definition method.
     *
     * @return the color type
     */
    ColorType getColorType();

    /**
     * Sets the color definition method.
     *
     * @param value the color type
     */
    void setColorType(ColorType value);

    /**
     * Returns the resulting color (sRGB). Setting this also sets the color type to RGB
     * and clears all color transformations.
     *
     * @return the color
     */
    Color getColor();

    /**
     * Sets the color as an sRGB value, clearing any existing color definition.
     *
     * @param value the color
     */
    void setColor(Color value);

    /**
     * Returns the color preset.
     *
     * @return the preset color
     */
    PresetColor getPresetColor();

    /**
     * Sets the color preset.
     *
     * @param value the preset color
     */
    void setPresetColor(PresetColor value);

    /**
     * Returns the color identified by a color scheme.
     *
     * @return the scheme color
     */
    SchemeColor getSchemeColor();

    /**
     * Sets the color identified by a color scheme.
     *
     * @param value the scheme color
     */
    void setSchemeColor(SchemeColor value);

    /**
     * Returns the red component of a color (0-255).
     *
     * @return the red component
     */
    int getR();

    /**
     * Sets the red component of a color (0-255).
     *
     * @param value the red component
     */
    void setR(int value);

    /**
     * Returns the green component of a color (0-255).
     *
     * @return the green component
     */
    int getG();

    /**
     * Sets the green component of a color (0-255).
     *
     * @param value the green component
     */
    void setG(int value);

    /**
     * Returns the blue component of a color (0-255).
     *
     * @return the blue component
     */
    int getB();

    /**
     * Sets the blue component of a color (0-255).
     *
     * @param value the blue component
     */
    void setB(int value);

    /**
     * Returns the red component as a float (0.0-1.0).
     *
     * @return the red component as a float
     */
    float getFloatR();

    /**
     * Sets the red component as a float (0.0-1.0).
     *
     * @param value the red component as a float
     */
    void setFloatR(float value);

    /**
     * Returns the green component as a float (0.0-1.0).
     *
     * @return the green component as a float
     */
    float getFloatG();

    /**
     * Sets the green component as a float (0.0-1.0).
     *
     * @param value the green component as a float
     */
    void setFloatG(float value);

    /**
     * Returns the blue component as a float (0.0-1.0).
     *
     * @return the blue component as a float
     */
    float getFloatB();

    /**
     * Sets the blue component as a float (0.0-1.0).
     *
     * @param value the blue component as a float
     */
    void setFloatB(float value);

    /**
     * Returns the hue component (0-360).
     *
     * @return the hue component
     */
    float getHue();

    /**
     * Sets the hue component (0-360).
     *
     * @param value the hue component
     */
    void setHue(float value);

    /**
     * Returns the saturation component (0-100).
     *
     * @return the saturation component
     */
    float getSaturation();

    /**
     * Sets the saturation component (0-100).
     *
     * @param value the saturation component
     */
    void setSaturation(float value);

    /**
     * Returns the luminance component (0-100).
     *
     * @return the luminance component
     */
    float getLuminance();

    /**
     * Sets the luminance component (0-100).
     *
     * @param value the luminance component
     */
    void setLuminance(float value);
}
