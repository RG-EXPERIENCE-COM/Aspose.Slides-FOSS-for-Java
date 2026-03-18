package org.aspose.slides.foss.effects;

import org.aspose.slides.foss.ColorFormat;
import org.aspose.slides.foss.IBaseSlide;
import org.aspose.slides.foss.IColorFormat;
import org.aspose.slides.foss.IPresentationComponent;
import org.aspose.slides.foss.IPresentation;
import org.aspose.slides.foss.ISlideComponent;
import org.w3c.dom.Element;

/**
 * Represents an inner shadow effect backed by an OOXML {@code <a:innerShdw>} element.
 */
public final class InnerShadow implements IInnerShadow, ISlideComponent {

    private static final double EMU_PER_POINT = 12700.0;
    private static final double ANGLE_SCALE = 60000.0;

    private final Element element;
    private final Runnable saveCallback;
    private IBaseSlide parentSlide;

    /**
     * Creates a new InnerShadow backed by the given XML element.
     *
     * @param element      the {@code <a:innerShdw>} element
     * @param saveCallback callback invoked after mutations; may be {@code null}
     */
    public InnerShadow(Element element, Runnable saveCallback) {
        this.element = element;
        this.saveCallback = saveCallback;
    }

    /**
     * Creates a new InnerShadow backed by the given XML element with a parent slide reference.
     *
     * @param element      the {@code <a:innerShdw>} element
     * @param saveCallback callback invoked after mutations; may be {@code null}
     * @param parentSlide  the parent slide; may be {@code null}
     */
    public InnerShadow(Element element, Runnable saveCallback, IBaseSlide parentSlide) {
        this.element = element;
        this.saveCallback = saveCallback;
        this.parentSlide = parentSlide;
    }

    @Override
    public double getBlurRadius() {
        return parseEmu("blurRad");
    }

    @Override
    public void setBlurRadius(double value) {
        element.setAttribute("blurRad", String.valueOf(Math.round(value * EMU_PER_POINT)));
        save();
    }

    @Override
    public double getDirection() {
        String dir = element.getAttribute("dir");
        if (dir == null || dir.isEmpty()) return 0.0;
        try {
            return Long.parseLong(dir) / ANGLE_SCALE;
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    @Override
    public void setDirection(double value) {
        element.setAttribute("dir", String.valueOf(Math.round(value * ANGLE_SCALE)));
        save();
    }

    @Override
    public double getDistance() {
        return parseEmu("dist");
    }

    @Override
    public void setDistance(double value) {
        element.setAttribute("dist", String.valueOf(Math.round(value * EMU_PER_POINT)));
        save();
    }

    @Override
    public IColorFormat getShadowColor() {
        return new ColorFormat(element, saveCallback);
    }

    @Override
    public IBaseSlide getSlide() {
        return parentSlide;
    }

    @Override
    public IPresentationComponent asIPresentationComponent() {
        return this;
    }

    @Override
    public IImageTransformOperation asIImageTransformOperation() {
        return this;
    }

    @Override
    public IPresentation getPresentation() {
        if (parentSlide != null) {
            return parentSlide.getPresentation();
        }
        return null;
    }

    private double parseEmu(String attr) {
        String val = element.getAttribute(attr);
        if (val == null || val.isEmpty()) return 0.0;
        try {
            return Long.parseLong(val) / EMU_PER_POINT;
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private void save() {
        if (saveCallback != null) saveCallback.run();
    }
}
