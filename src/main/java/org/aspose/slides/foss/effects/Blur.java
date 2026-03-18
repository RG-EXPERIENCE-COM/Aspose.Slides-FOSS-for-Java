package org.aspose.slides.foss.effects;

import org.aspose.slides.foss.IBaseSlide;
import org.aspose.slides.foss.IPresentationComponent;
import org.w3c.dom.Element;

/**
 * Represents a Blur effect that is applied to the entire shape,
 * including its fill. All color channels, including alpha, are affected.
 */
public final class Blur extends ImageTransformOperation implements IBlur {

    private static final double EMU_PER_POINT = 12700.0;

    private Element element;
    private Runnable saveCallback;

    /**
     * Creates an uninitialized Blur. Call {@link #initInternal} before use.
     */
    public Blur() {
        super();
    }

    /**
     * Creates a new Blur backed by the given XML element.
     *
     * @param element      the {@code <a:blur>} element
     * @param saveCallback callback invoked after mutations; may be {@code null}
     */
    public Blur(Element element, Runnable saveCallback) {
        super();
        this.element = element;
        this.saveCallback = saveCallback;
    }

    /**
     * Creates a new Blur backed by the given XML element with a parent slide reference.
     *
     * @param element      the {@code <a:blur>} element
     * @param saveCallback callback invoked after mutations; may be {@code null}
     * @param parentSlide  the parent slide; may be {@code null}
     */
    public Blur(Element element, Runnable saveCallback, IBaseSlide parentSlide) {
        super(parentSlide);
        this.element = element;
        this.saveCallback = saveCallback;
    }

    /**
     * Initializes this Blur with the given backing element, save callback, and parent slide.
     *
     * @param element      the {@code <a:blur>} XML element
     * @param saveCallback callback invoked after mutations to persist changes; may be {@code null}
     * @param parentSlide  the parent slide object
     */
    public void initInternal(Element element, Runnable saveCallback, IBaseSlide parentSlide) {
        this.element = element;
        this.saveCallback = saveCallback;
        this.parentSlide = parentSlide;
    }

    @Override
    public double getRadius() {
        String rad = element.getAttribute("rad");
        if (rad == null || rad.isEmpty()) return 0.0;
        try {
            return Long.parseLong(rad) / EMU_PER_POINT;
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    @Override
    public void setRadius(double value) {
        element.setAttribute("rad", String.valueOf(Math.round(value * EMU_PER_POINT)));
        save();
    }

    @Override
    public boolean isGrow() {
        String grow = element.getAttribute("grow");
        if (grow == null || grow.isEmpty()) return true;
        return "1".equals(grow) || "true".equals(grow);
    }

    @Override
    public void setGrow(boolean value) {
        element.setAttribute("grow", value ? "1" : "0");
        save();
    }

    /**
     * Persists changes by invoking the save callback, if one is set.
     */
    private void save() {
        if (saveCallback != null) {
            saveCallback.run();
        }
    }
}
