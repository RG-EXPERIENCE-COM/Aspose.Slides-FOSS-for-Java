package org.aspose.slides.foss.effects;

import org.aspose.slides.foss.ColorFormat;
import org.aspose.slides.foss.IBaseSlide;
import org.aspose.slides.foss.IColorFormat;
import org.aspose.slides.foss.RectangleAlignment;
import org.w3c.dom.Element;

import java.util.Map;

/**
 * Represents an outer shadow effect backed by an OOXML {@code <a:outerShdw>} element.
 */
public final class OuterShadow extends ImageTransformOperation implements IOuterShadow {

    private static final double EMU_PER_POINT = 12700.0;
    private static final double ANGLE_SCALE = 60000.0;
    private static final double SCALE_FACTOR = 1000.0;

    /** OOXML alignment abbreviation to {@link RectangleAlignment} enum name. */
    private static final Map<String, String> ALGN_MAP = Map.of(
            "tl", "TOP_LEFT", "t", "TOP", "tr", "TOP_RIGHT",
            "l", "LEFT", "ctr", "CENTER", "r", "RIGHT",
            "bl", "BOTTOM_LEFT", "b", "BOTTOM", "br", "BOTTOM_RIGHT"
    );

    /** Reverse map: {@link RectangleAlignment} enum name to OOXML abbreviation. */
    private static final Map<String, String> ALGN_MAP_REV;
    static {
        var rev = new java.util.HashMap<String, String>();
        ALGN_MAP.forEach((k, v) -> rev.put(v, k));
        ALGN_MAP_REV = Map.copyOf(rev);
    }

    private Element element;
    private Runnable saveCallback;

    /**
     * Creates an uninitialized OuterShadow. Call {@link #initInternal} before use.
     */
    public OuterShadow() {
        super();
    }

    /**
     * Creates a new OuterShadow backed by the given XML element.
     *
     * @param element      the {@code <a:outerShdw>} element
     * @param saveCallback callback invoked after mutations; may be {@code null}
     */
    public OuterShadow(Element element, Runnable saveCallback) {
        super();
        this.element = element;
        this.saveCallback = saveCallback;
    }

    /**
     * Creates a new OuterShadow backed by the given XML element with a parent slide reference.
     *
     * @param element      the {@code <a:outerShdw>} element
     * @param saveCallback callback invoked after mutations; may be {@code null}
     * @param parentSlide  the parent slide; may be {@code null}
     */
    public OuterShadow(Element element, Runnable saveCallback, IBaseSlide parentSlide) {
        super(parentSlide);
        this.element = element;
        this.saveCallback = saveCallback;
    }

    /**
     * Initializes this OuterShadow with the given backing element, save callback, and parent slide.
     *
     * @param element      the {@code <a:outerShdw>} XML element
     * @param saveCallback callback invoked after mutations to persist changes; may be {@code null}
     * @param parentSlide  the parent slide object
     */
    public void initInternal(Element element, Runnable saveCallback, IBaseSlide parentSlide) {
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
        return parseAngle("dir");
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
    public RectangleAlignment getRectangleAlign() {
        String val = element.getAttribute("algn");
        if (val == null || val.isEmpty()) return RectangleAlignment.BOTTOM;
        String name = ALGN_MAP.get(val);
        if (name == null) return RectangleAlignment.NOT_DEFINED;
        return RectangleAlignment.valueOf(name);
    }

    @Override
    public void setRectangleAlign(RectangleAlignment value) {
        if (value == RectangleAlignment.NOT_DEFINED) {
            element.removeAttribute("algn");
        } else {
            String ooxmlVal = ALGN_MAP_REV.get(value.name());
            if (ooxmlVal != null) {
                element.setAttribute("algn", ooxmlVal);
            }
        }
        save();
    }

    @Override
    public double getSkewHorizontal() {
        return parseAngle("kx");
    }

    @Override
    public void setSkewHorizontal(double value) {
        element.setAttribute("kx", String.valueOf(Math.round(value * ANGLE_SCALE)));
        save();
    }

    @Override
    public double getSkewVertical() {
        return parseAngle("ky");
    }

    @Override
    public void setSkewVertical(double value) {
        element.setAttribute("ky", String.valueOf(Math.round(value * ANGLE_SCALE)));
        save();
    }

    @Override
    public boolean getRotateShadowWithShape() {
        String val = element.getAttribute("rotWithShape");
        if (val == null || val.isEmpty()) return true;
        return !"0".equals(val) && !"false".equalsIgnoreCase(val);
    }

    @Override
    public void setRotateShadowWithShape(boolean value) {
        element.setAttribute("rotWithShape", value ? "1" : "0");
        save();
    }

    @Override
    public double getScaleHorizontal() {
        return parseScale("sx", 100.0);
    }

    @Override
    public void setScaleHorizontal(double value) {
        element.setAttribute("sx", String.valueOf(Math.round(value * SCALE_FACTOR)));
        save();
    }

    @Override
    public double getScaleVertical() {
        return parseScale("sy", 100.0);
    }

    @Override
    public void setScaleVertical(double value) {
        element.setAttribute("sy", String.valueOf(Math.round(value * SCALE_FACTOR)));
        save();
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

    private double parseAngle(String attr) {
        String val = element.getAttribute(attr);
        if (val == null || val.isEmpty()) return 0.0;
        try {
            return Long.parseLong(val) / ANGLE_SCALE;
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private double parseScale(String attr, double defaultValue) {
        String val = element.getAttribute(attr);
        if (val == null || val.isEmpty()) return defaultValue;
        try {
            return Long.parseLong(val) / SCALE_FACTOR;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Persists changes by invoking the save callback, if one is set.
     */
    private void save() {
        if (saveCallback != null) saveCallback.run();
    }
}
