package org.aspose.slides.foss.effects;

import org.aspose.slides.foss.ColorFormat;
import org.aspose.slides.foss.IColorFormat;
import org.w3c.dom.Element;

/**
 * Represents a glow effect backed by an OOXML {@code <a:glow>} element.
 */
public final class Glow implements IGlow, IImageTransformOperation {

    private static final double EMU_PER_POINT = 12700.0;

    private final Element element;
    private final Runnable saveCallback;

    /**
     * Creates a new Glow backed by the given XML element.
     *
     * @param element      the {@code <a:glow>} element
     * @param saveCallback callback invoked after mutations; may be {@code null}
     */
    public Glow(Element element, Runnable saveCallback) {
        this.element = element;
        this.saveCallback = saveCallback;
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
    public IColorFormat getColor() {
        return new ColorFormat(element, saveCallback);
    }

    /** {@inheritDoc} */
    @Override
    public IImageTransformOperation asIImageTransformOperation() {
        return this;
    }

    private void save() {
        if (saveCallback != null) saveCallback.run();
    }
}
