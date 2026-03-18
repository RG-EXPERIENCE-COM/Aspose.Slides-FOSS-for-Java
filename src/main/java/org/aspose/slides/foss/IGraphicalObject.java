package org.aspose.slides.foss;

/**
 * Represents abstract graphical object.
 */
public interface IGraphicalObject extends IShape {

    /**
     * Returns shape's locks.
     * Read-only.
     *
     * @return the graphical object lock
     */
    IGraphicalObjectLock getGraphicalObjectLock();
}
