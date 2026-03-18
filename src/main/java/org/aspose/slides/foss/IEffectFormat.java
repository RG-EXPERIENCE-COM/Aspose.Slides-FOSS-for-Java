package org.aspose.slides.foss;

import org.aspose.slides.foss.effects.*;

/**
 * Represents effect formatting properties.
 */
public interface IEffectFormat extends IEffectParamSource {

    /**
     * Returns {@code true} if all effects are disabled.
     *
     * @return whether no effects are applied
     */
    boolean isNoEffects();

    /**
     * Returns the blur effect, or {@code null} if not present.
     *
     * @return the blur effect
     */
    IBlur getBlurEffect();

    /**
     * Sets or removes the blur effect.
     *
     * @param value the blur effect, or {@code null} to remove
     */
    void setBlurEffect(IBlur value);

    /**
     * Returns the fill overlay effect, or {@code null} if not present.
     *
     * @return the fill overlay effect
     */
    IFillOverlay getFillOverlayEffect();

    /**
     * Sets or removes the fill overlay effect.
     *
     * @param value the fill overlay effect, or {@code null} to remove
     */
    void setFillOverlayEffect(IFillOverlay value);

    /**
     * Returns the glow effect, or {@code null} if not present.
     *
     * @return the glow effect
     */
    IGlow getGlowEffect();

    /**
     * Sets or removes the glow effect.
     *
     * @param value the glow effect, or {@code null} to remove
     */
    void setGlowEffect(IGlow value);

    /**
     * Returns the inner shadow effect, or {@code null} if not present.
     *
     * @return the inner shadow effect
     */
    IInnerShadow getInnerShadowEffect();

    /**
     * Sets or removes the inner shadow effect.
     *
     * @param value the inner shadow effect, or {@code null} to remove
     */
    void setInnerShadowEffect(IInnerShadow value);

    /**
     * Returns the outer shadow effect, or {@code null} if not present.
     *
     * @return the outer shadow effect
     */
    IOuterShadow getOuterShadowEffect();

    /**
     * Sets or removes the outer shadow effect.
     *
     * @param value the outer shadow effect, or {@code null} to remove
     */
    void setOuterShadowEffect(IOuterShadow value);

    /**
     * Returns the preset shadow effect, or {@code null} if not present.
     *
     * @return the preset shadow effect
     */
    IPresetShadow getPresetShadowEffect();

    /**
     * Sets or removes the preset shadow effect.
     *
     * @param value the preset shadow effect, or {@code null} to remove
     */
    void setPresetShadowEffect(IPresetShadow value);

    /**
     * Returns the reflection effect, or {@code null} if not present.
     *
     * @return the reflection effect
     */
    IReflection getReflectionEffect();

    /**
     * Sets or removes the reflection effect.
     *
     * @param value the reflection effect, or {@code null} to remove
     */
    void setReflectionEffect(IReflection value);

    /**
     * Returns the soft edge effect, or {@code null} if not present.
     *
     * @return the soft edge effect
     */
    ISoftEdge getSoftEdgeEffect();

    /**
     * Sets or removes the soft edge effect.
     *
     * @param value the soft edge effect, or {@code null} to remove
     */
    void setSoftEdgeEffect(ISoftEdge value);

    /**
     * Returns this object as an {@link IEffectParamSource}.
     *
     * @return this object
     */
    IEffectParamSource asIEffectParamSource();

    /**
     * Sets a blur effect with the specified parameters.
     *
     * @param radius the blur radius in points
     * @param grow   whether the blur grows beyond the shape bounds
     */
    void setBlurEffect(double radius, boolean grow);

    /** Enables the blur effect (creates element if absent). */
    void enableBlurEffect();

    /** Enables the fill overlay effect (creates element if absent). */
    void enableFillOverlayEffect();

    /** Enables the glow effect (creates element if absent). */
    void enableGlowEffect();

    /** Enables the inner shadow effect (creates element if absent). */
    void enableInnerShadowEffect();

    /** Enables the outer shadow effect (creates element if absent). */
    void enableOuterShadowEffect();

    /** Enables the preset shadow effect (creates element if absent). */
    void enablePresetShadowEffect();

    /** Enables the reflection effect (creates element if absent). */
    void enableReflectionEffect();

    /** Enables the soft edge effect (creates element if absent). */
    void enableSoftEdgeEffect();

    /** Disables the blur effect (removes element). */
    void disableBlurEffect();

    /** Disables the fill overlay effect (removes element). */
    void disableFillOverlayEffect();

    /** Disables the glow effect (removes element). */
    void disableGlowEffect();

    /** Disables the inner shadow effect (removes element). */
    void disableInnerShadowEffect();

    /** Disables the outer shadow effect (removes element). */
    void disableOuterShadowEffect();

    /** Disables the preset shadow effect (removes element). */
    void disablePresetShadowEffect();

    /** Disables the reflection effect (removes element). */
    void disableReflectionEffect();

    /** Disables the soft edge effect (removes element). */
    void disableSoftEdgeEffect();
}
