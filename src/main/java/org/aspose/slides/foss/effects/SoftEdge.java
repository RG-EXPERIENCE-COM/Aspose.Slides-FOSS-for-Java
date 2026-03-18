package org.aspose.slides.foss.effects;

import org.w3c.dom.Element;

/**
 * Represents a soft edge effect backed by an OOXML {@code <a:softEdge>} element.
 */
public final class SoftEdge implements ISoftEdge {

    private static final double EMU_PER_POINT = 12700.0;

    private final Element element;
    private final Runnable saveCallback;

    /**
     * Creates a new SoftEdge backed by the given XML element.
     *
     * @param element      the {@code <a:softEdge>} element
     * @param saveCallback callback invoked after mutations; may be {@code null}
     */
    public SoftEdge(Element element, Runnable saveCallback) {
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
    public IImageTransformOperation asIImageTransformOperation() {
        return this;
    }

    private void save() {
        if (saveCallback != null) saveCallback.run();
    }
}
