package org.aspose.slides.foss;

import org.w3c.dom.Element;

/**
 * Represents a single gradient stop.
 *
 * <p>Wraps an OOXML {@code <a:gs>} element.
 * Extends {@link PVIObject} to participate in the slide/presentation component hierarchy.</p>
 */
public final class GradientStop extends PVIObject implements IGradientStop {

    private Element gsElement;
    private Runnable saveCallback;

    /**
     * Creates an empty {@code GradientStop} with no backing element.
     * Call {@link #initInternal(Element, Runnable, IBaseSlide)} to bind to an XML element.
     */
    public GradientStop() {
        // no-op; call initInternal to bind to an XML element
    }

    /**
     * Creates a new GradientStop backed by the given {@code <a:gs>} element.
     *
     * @param gsElement    the gs XML element
     * @param saveCallback callback invoked after mutations; may be {@code null}
     */
    public GradientStop(Element gsElement, Runnable saveCallback) {
        this.gsElement = gsElement;
        this.saveCallback = saveCallback;
    }

    /**
     * Initializes this gradient stop from the given {@code <a:gs>} element,
     * save callback, and parent slide.
     *
     * <p>The slide-part save is represented as a {@link Runnable} callback.</p>
     *
     * @param gsElement    the OOXML {@code <a:gs>} element
     * @param saveCallback callback invoked after mutations (typically {@code slidePart::save});
     *                     may be {@code null}
     * @param parentSlide  the slide this gradient stop belongs to; may be {@code null}
     * @return this instance, for fluent chaining
     */
    public GradientStop initInternal(Element gsElement, Runnable saveCallback, IBaseSlide parentSlide) {
        this.gsElement = gsElement;
        this.saveCallback = saveCallback;
        this.parentSlide = parentSlide;
        return this;
    }

    /**
     * Persists changes by invoking the save callback, if one was provided.
     *
     * <p>Invokes the slide-part save callback when one is present.</p>
     */
    public void save() {
        if (saveCallback != null) {
            saveCallback.run();
        }
    }

    @Override
    public double getPosition() {
        String pos = gsElement.getAttribute("pos");
        if (pos == null || pos.isEmpty()) return 0.0;
        return Long.parseLong(pos) / 100000.0;
    }

    @Override
    public void setPosition(double value) {
        gsElement.setAttribute("pos", String.valueOf(Math.round(value * 100000)));
        save();
    }

    @Override
    public IColorFormat getColor() {
        return new ColorFormat(gsElement, saveCallback);
    }
}
