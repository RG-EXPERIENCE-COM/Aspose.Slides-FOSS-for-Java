package org.aspose.slides.foss;

import org.w3c.dom.Element;

/**
 * Abstract base class for graphical objects on a slide.
 *
 * <p>Combines the shape hierarchy ({@link Shape}) with the
 * {@link IGraphicalObject} contract.</p>
 */
public abstract class GraphicalObject extends Shape implements IGraphicalObject {

    /**
     * Creates a {@code GraphicalObject} backed by the given XML element.
     *
     * @param xmlElement   the shape XML element
     * @param saveCallback callback invoked after mutations; may be {@code null}
     */
    public GraphicalObject(Element xmlElement, Runnable saveCallback) {
        super(xmlElement, saveCallback);
    }

    /**
     * Creates a {@code GraphicalObject} with no backing element.
     */
    public GraphicalObject() {
        super();
    }
}
