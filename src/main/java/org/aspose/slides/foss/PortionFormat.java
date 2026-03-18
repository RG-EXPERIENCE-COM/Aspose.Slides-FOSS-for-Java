package org.aspose.slides.foss;

import org.w3c.dom.Element;

/**
 * Represents text portion formatting properties.
 *
 * <p>Extends {@link BasePortionFormat} with additional properties
 * specific to portion-level formatting.</p>
 */
public final class PortionFormat extends BasePortionFormat implements IPortionFormat {

    /**
     * Creates a new PortionFormat with a detached element.
     */
    public PortionFormat() {
        super();
    }

    /**
     * Creates a PortionFormat backed by an existing element.
     *
     * @param rprElement   the {@code <a:rPr>} XML element
     * @param saveCallback callback invoked after mutations; may be {@code null}
     */
    public PortionFormat(Element rprElement, Runnable saveCallback) {
        super(rprElement, saveCallback);
    }
}
