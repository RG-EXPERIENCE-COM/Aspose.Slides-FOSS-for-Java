package org.aspose.slides.foss;

import org.w3c.dom.Element;

/**
 * Represents a single geometry adjustment value backed by an OOXML {@code <a:gd>} element.
 */
public final class AdjustValue implements IAdjustValue {

    private Element gdElement;
    private Runnable saveCallback;

    /**
     * Creates an empty {@code AdjustValue} with no backing element.
     * Call {@link #initInternal(Element, Runnable)} to bind to an XML element.
     */
    public AdjustValue() {
        // no-op; call initInternal to bind to an XML element
    }

    /**
     * Creates an AdjustValue backed by the given {@code <a:gd>} element.
     *
     * @param gdElement    the OOXML guide element
     * @param saveCallback callback invoked after mutations; may be {@code null}
     */
    public AdjustValue(Element gdElement, Runnable saveCallback) {
        this.gdElement = gdElement;
        this.saveCallback = saveCallback;
    }

    /**
     * Initializes this adjust value from the given {@code <a:gd>} element.
     *
     * @param gdElement    the OOXML guide element
     * @param saveCallback callback invoked after mutations; may be {@code null}
     * @return this instance, for fluent chaining
     */
    public AdjustValue initInternal(Element gdElement, Runnable saveCallback) {
        this.gdElement = gdElement;
        this.saveCallback = saveCallback;
        return this;
    }

    @Override
    public String getName() {
        return gdElement.getAttribute("name");
    }

    @Override
    public long getRawValue() {
        String fmla = gdElement.getAttribute("fmla");
        if (fmla != null && fmla.startsWith("val ")) {
            try {
                return Long.parseLong(fmla.substring(4).strip());
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

    @Override
    public void setRawValue(long value) {
        gdElement.setAttribute("fmla", "val " + value);
        if (saveCallback != null) {
            saveCallback.run();
        }
    }

    @Override
    public double getAngleValue() {
        return getRawValue() / 60000.0;
    }

    @Override
    public void setAngleValue(double value) {
        setRawValue(Math.round(value * 60000.0));
    }
}
