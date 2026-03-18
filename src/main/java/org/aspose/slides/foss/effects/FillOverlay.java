package org.aspose.slides.foss.effects;

import org.aspose.slides.foss.FillBlendMode;
import org.aspose.slides.foss.FillFormat;
import org.aspose.slides.foss.IBaseSlide;
import org.aspose.slides.foss.IFillFormat;
import org.aspose.slides.foss.IPresentation;
import org.aspose.slides.foss.IPresentationComponent;
import org.aspose.slides.foss.ISlideComponent;
import org.w3c.dom.Element;

import java.util.Map;

/**
 * Represents a Fill Overlay effect. A fill overlay may be used to specify
 * an additional fill for an object and blend the two fills together.
 */
public final class FillOverlay extends ImageTransformOperation implements IFillOverlay, ISlideComponent {

    /** OOXML blend attribute value &rarr; {@link FillBlendMode} mapping. */
    private static final Map<String, FillBlendMode> BLEND_MAP = Map.of(
            "over", FillBlendMode.OVERLAY,
            "mult", FillBlendMode.MULTIPLY,
            "screen", FillBlendMode.SCREEN,
            "darken", FillBlendMode.DARKEN,
            "lighten", FillBlendMode.LIGHTEN
    );

    /** Reverse mapping: {@link FillBlendMode} name &rarr; OOXML attribute value. */
    private static final Map<FillBlendMode, String> BLEND_MAP_REV = Map.of(
            FillBlendMode.OVERLAY, "over",
            FillBlendMode.MULTIPLY, "mult",
            FillBlendMode.SCREEN, "screen",
            FillBlendMode.DARKEN, "darken",
            FillBlendMode.LIGHTEN, "lighten"
    );

    private Element element;
    private Runnable saveCallback;

    /**
     * Creates an uninitialized {@code FillOverlay}.
     * Call {@link #initInternal(Element, Runnable, IBaseSlide)} to complete initialization.
     */
    public FillOverlay() {
    }

    /**
     * Creates a new FillOverlay backed by the given XML element.
     *
     * @param element      the {@code <a:fillOverlay>} element
     * @param saveCallback callback invoked after mutations; may be {@code null}
     */
    public FillOverlay(Element element, Runnable saveCallback) {
        this.element = element;
        this.saveCallback = saveCallback;
    }

    /**
     * Creates a new FillOverlay backed by the given XML element with a parent slide reference.
     *
     * @param element      the {@code <a:fillOverlay>} element
     * @param saveCallback callback invoked after mutations; may be {@code null}
     * @param parentSlide  the parent slide; may be {@code null}
     */
    public FillOverlay(Element element, Runnable saveCallback, IBaseSlide parentSlide) {
        super(parentSlide);
        this.element = element;
        this.saveCallback = saveCallback;
    }

    /**
     * Initializes this fill overlay with the backing XML element, save callback,
     * and parent slide reference.
     *
     * @param element      the {@code <a:fillOverlay>} XML element
     * @param saveCallback callback invoked after mutations (e.g. slide part save); may be {@code null}
     * @param parentSlide  the parent slide; may be {@code null}
     */
    public void initInternal(Element element, Runnable saveCallback, IBaseSlide parentSlide) {
        this.element = element;
        this.saveCallback = saveCallback;
        this.parentSlide = parentSlide;
    }

    @Override
    public IFillFormat getFillFormat() {
        return new FillFormat(element, saveCallback);
    }

    @Override
    public FillBlendMode getBlend() {
        String val = element.getAttribute("blend");
        if (val == null || val.isEmpty()) {
            return FillBlendMode.OVERLAY;
        }
        FillBlendMode mode = BLEND_MAP.get(val);
        return mode != null ? mode : FillBlendMode.OVERLAY;
    }

    @Override
    public void setBlend(FillBlendMode value) {
        String ooxmlVal = BLEND_MAP_REV.get(value);
        if (ooxmlVal != null) {
            element.setAttribute("blend", ooxmlVal);
        }
        save();
    }

    @Override
    public IPresentationComponent asIPresentationComponent() {
        return this;
    }

    /**
     * Persists changes by invoking the save callback, if one was provided.
     */
    void save() {
        if (saveCallback != null) {
            saveCallback.run();
        }
    }
}
