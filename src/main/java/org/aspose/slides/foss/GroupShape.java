package org.aspose.slides.foss;

import org.w3c.dom.Element;

/**
 * Represents a group of shapes on a slide.
 *
 * <p>A {@code GroupShape} acts as a container that holds a collection of
 * child shapes, allowing them to be manipulated as a single unit.</p>
 */
public final class GroupShape extends Shape implements IGroupShape {

    /**
     * Creates a {@code GroupShape} backed by the given XML element.
     *
     * @param xmlElement   the shape XML element
     * @param saveCallback callback invoked after mutations; may be {@code null}
     */
    public GroupShape(Element xmlElement, Runnable saveCallback) {
        super(xmlElement, saveCallback);
    }

    /**
     * Creates a {@code GroupShape} with no backing element.
     */
    public GroupShape() {
        super();
    }

    @Override
    public IShapeCollection getShapes() {
        return new ShapeCollection();
    }
}
