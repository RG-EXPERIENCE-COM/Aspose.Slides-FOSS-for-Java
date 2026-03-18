package org.aspose.slides.foss.effects;

import org.aspose.slides.foss.IBaseSlide;
import org.aspose.slides.foss.RectangleAlignment;
import org.w3c.dom.Element;

import java.util.Map;

/**
 * Represents a reflection effect backed by an OOXML {@code <a:reflection>} element.
 */
public final class Reflection extends ImageTransformOperation implements IReflection {

    private static final double EMU_PER_POINT = 12700.0;
    private static final double ANGLE_SCALE = 60000.0;
    private static final double SCALE_FACTOR = 1000.0;

    /** OOXML abbreviation to {@link RectangleAlignment} enum constant. */
    private static final Map<String, RectangleAlignment> ALGN_MAP = Map.of(
            "tl", RectangleAlignment.TOP_LEFT,
            "t", RectangleAlignment.TOP,
            "tr", RectangleAlignment.TOP_RIGHT,
            "l", RectangleAlignment.LEFT,
            "ctr", RectangleAlignment.CENTER,
            "r", RectangleAlignment.RIGHT,
            "bl", RectangleAlignment.BOTTOM_LEFT,
            "b", RectangleAlignment.BOTTOM,
            "br", RectangleAlignment.BOTTOM_RIGHT
    );

    /** Reverse map: {@link RectangleAlignment} to OOXML abbreviation. */
    private static final Map<RectangleAlignment, String> ALGN_MAP_REV = Map.of(
            RectangleAlignment.TOP_LEFT, "tl",
            RectangleAlignment.TOP, "t",
            RectangleAlignment.TOP_RIGHT, "tr",
            RectangleAlignment.LEFT, "l",
            RectangleAlignment.CENTER, "ctr",
            RectangleAlignment.RIGHT, "r",
            RectangleAlignment.BOTTOM_LEFT, "bl",
            RectangleAlignment.BOTTOM, "b",
            RectangleAlignment.BOTTOM_RIGHT, "br"
    );

    private Element element;
    private Runnable saveCallback;

    /**
     * Creates an uninitialized Reflection. Call {@link #initInternal} before use.
     */
    public Reflection() {
        super();
    }

    /**
     * Creates a new Reflection backed by the given XML element.
     *
     * @param element      the {@code <a:reflection>} element
     * @param saveCallback callback invoked after mutations; may be {@code null}
     */
    public Reflection(Element element, Runnable saveCallback) {
        super();
        this.element = element;
        this.saveCallback = saveCallback;
    }

    /**
     * Creates a new Reflection backed by the given XML element with a parent slide reference.
     *
     * @param element      the {@code <a:reflection>} element
     * @param saveCallback callback invoked after mutations; may be {@code null}
     * @param parentSlide  the parent slide; may be {@code null}
     */
    public Reflection(Element element, Runnable saveCallback, IBaseSlide parentSlide) {
        super(parentSlide);
        this.element = element;
        this.saveCallback = saveCallback;
    }

    /**
     * Initializes this Reflection with the given backing element, save callback, and parent slide.
     *
     * @param element      the {@code <a:reflection>} XML element
     * @param saveCallback callback invoked after mutations to persist changes; may be {@code null}
     * @param parentSlide  the parent slide object
     */
    public void initInternal(Element element, Runnable saveCallback, IBaseSlide parentSlide) {
        this.element = element;
        this.saveCallback = saveCallback;
        this.parentSlide = parentSlide;
    }

    @Override
    public double getStartPosAlpha() {
        return parseScale("stPos", 0.0);
    }

    @Override
    public void setStartPosAlpha(double value) {
        element.setAttribute("stPos", String.valueOf(Math.round(value * SCALE_FACTOR)));
        save();
    }

    @Override
    public double getEndPosAlpha() {
        return parseScale("endPos", 100.0);
    }

    @Override
    public void setEndPosAlpha(double value) {
        element.setAttribute("endPos", String.valueOf(Math.round(value * SCALE_FACTOR)));
        save();
    }

    @Override
    public double getFadeDirection() {
        return parseAngle("fadeDir", 90.0);
    }

    @Override
    public void setFadeDirection(double value) {
        element.setAttribute("fadeDir", String.valueOf(Math.round(value * ANGLE_SCALE)));
        save();
    }

    @Override
    public double getStartReflectionOpacity() {
        return parseScale("stA", 100.0);
    }

    @Override
    public void setStartReflectionOpacity(double value) {
        element.setAttribute("stA", String.valueOf(Math.round(value * SCALE_FACTOR)));
        save();
    }

    @Override
    public double getEndReflectionOpacity() {
        return parseScale("endA", 0.0);
    }

    @Override
    public void setEndReflectionOpacity(double value) {
        element.setAttribute("endA", String.valueOf(Math.round(value * SCALE_FACTOR)));
        save();
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
        return parseAngle("dir", 0.0);
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
    public RectangleAlignment getRectangleAlign() {
        String val = element.getAttribute("algn");
        if (val == null || val.isEmpty()) return RectangleAlignment.BOTTOM;
        RectangleAlignment result = ALGN_MAP.get(val);
        return result != null ? result : RectangleAlignment.NOT_DEFINED;
    }

    @Override
    public void setRectangleAlign(RectangleAlignment value) {
        if (value == RectangleAlignment.NOT_DEFINED) {
            element.removeAttribute("algn");
        } else {
            String ooxmlVal = ALGN_MAP_REV.get(value);
            if (ooxmlVal != null) {
                element.setAttribute("algn", ooxmlVal);
            }
        }
        save();
    }

    @Override
    public double getSkewHorizontal() {
        return parseAngle("kx", 0.0);
    }

    @Override
    public void setSkewHorizontal(double value) {
        element.setAttribute("kx", String.valueOf(Math.round(value * ANGLE_SCALE)));
        save();
    }

    @Override
    public double getSkewVertical() {
        return parseAngle("ky", 0.0);
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
        return "1".equals(val);
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

    private double parseAngle(String attr, double defaultValue) {
        String val = element.getAttribute(attr);
        if (val == null || val.isEmpty()) return defaultValue;
        try {
            return Long.parseLong(val) / ANGLE_SCALE;
        } catch (NumberFormatException e) {
            return defaultValue;
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
