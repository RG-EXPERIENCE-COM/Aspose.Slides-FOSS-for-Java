package org.aspose.slides.foss;

/**
 * Represents a group of shapes on a slide.
 */
public interface IGroupShape extends IShape {

    /**
     * Returns the collection of shapes inside the group.
     *
     * @return the shape collection
     */
    IShapeCollection getShapes();
}
