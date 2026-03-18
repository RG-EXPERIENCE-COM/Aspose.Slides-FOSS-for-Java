package org.aspose.slides.foss.effects;

import org.aspose.slides.foss.ColorFormat;
import org.aspose.slides.foss.IBaseSlide;
import org.aspose.slides.foss.IColorFormat;
import org.aspose.slides.foss.PresetShadowType;
import org.w3c.dom.Element;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a preset shadow effect backed by an OOXML {@code <a:prstShdw>} element.
 */
public final class PresetShadow implements IPresetShadow {

    private static final double EMU_PER_POINT = 12700.0;
    private static final double ANGLE_SCALE = 60000.0;

    /** Maps OOXML preset shadow values to {@link PresetShadowType} enum names. */
    private static final Map<String, String> PRST_MAP = Map.ofEntries(
            Map.entry("shdw1", "TOP_LEFT_DROP_SHADOW"),
            Map.entry("shdw2", "TOP_LEFT_LARGE_DROP_SHADOW"),
            Map.entry("shdw3", "BACK_LEFT_LONG_PERSPECTIVE_SHADOW"),
            Map.entry("shdw4", "BACK_RIGHT_LONG_PERSPECTIVE_SHADOW"),
            Map.entry("shdw5", "TOP_LEFT_DOUBLE_DROP_SHADOW"),
            Map.entry("shdw6", "BOTTOM_RIGHT_SMALL_DROP_SHADOW"),
            Map.entry("shdw7", "FRONT_LEFT_LONG_PERSPECTIVE_SHADOW"),
            Map.entry("shdw8", "FRONT_RIGHT_LONG_PERSPECTIVE_SHADOW"),
            Map.entry("shdw9", "OUTER_BOX_SHADOW_3D"),
            Map.entry("shdw10", "INNER_BOX_SHADOW_3D"),
            Map.entry("shdw11", "BACK_CENTER_PERSPECTIVE_SHADOW"),
            Map.entry("shdw12", "TOP_RIGHT_DROP_SHADOW"),
            Map.entry("shdw13", "FRONT_BOTTOM_SHADOW"),
            Map.entry("shdw14", "BACK_LEFT_PERSPECTIVE_SHADOW"),
            Map.entry("shdw15", "BACK_RIGHT_PERSPECTIVE_SHADOW"),
            Map.entry("shdw16", "BOTTOM_LEFT_DROP_SHADOW"),
            Map.entry("shdw17", "BOTTOM_RIGHT_DROP_SHADOW"),
            Map.entry("shdw18", "FRONT_LEFT_PERSPECTIVE_SHADOW"),
            Map.entry("shdw19", "FRONT_RIGHT_PERSPECTIVE_SHADOW"),
            Map.entry("shdw20", "TOP_LEFT_SMALL_DROP_SHADOW")
    );

    /** Reverse map: enum name to OOXML value. */
    private static final Map<String, String> PRST_MAP_REV =
            PRST_MAP.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

    private Element element;
    private Runnable saveCallback;
    private IBaseSlide parentSlide;

    /**
     * Creates an uninitialized PresetShadow. Call {@link #initInternal} before use.
     */
    public PresetShadow() {
    }

    /**
     * Creates a new PresetShadow backed by the given XML element.
     *
     * @param element      the {@code <a:prstShdw>} element
     * @param saveCallback callback invoked after mutations; may be {@code null}
     */
    public PresetShadow(Element element, Runnable saveCallback) {
        this.element = element;
        this.saveCallback = saveCallback;
    }

    /**
     * Initializes this PresetShadow with the given backing element, save callback, and parent slide.
     *
     * @param element      the {@code <a:prstShdw>} XML element
     * @param saveCallback callback invoked after mutations to persist changes; may be {@code null}
     * @param parentSlide  the parent slide object; may be {@code null}
     */
    public void initInternal(Element element, Runnable saveCallback, IBaseSlide parentSlide) {
        this.element = element;
        this.saveCallback = saveCallback;
        this.parentSlide = parentSlide;
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
    public PresetShadowType getPreset() {
        String val = element.getAttribute("prst");
        if (val == null || val.isEmpty()) {
            return null;
        }
        String enumName = PRST_MAP.get(val);
        if (enumName == null) {
            return null;
        }
        return PresetShadowType.valueOf(enumName);
    }

    @Override
    public void setPreset(PresetShadowType value) {
        if (value == null) {
            element.removeAttribute("prst");
        } else {
            String ooxmlVal = PRST_MAP_REV.get(value.name());
            if (ooxmlVal != null) {
                element.setAttribute("prst", ooxmlVal);
            }
        }
        save();
    }

    @Override
    public IImageTransformOperation asIImageTransformOperation() {
        return this;
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

    private void save() {
        if (saveCallback != null) saveCallback.run();
    }
}
