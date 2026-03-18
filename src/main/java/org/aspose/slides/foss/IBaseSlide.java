package org.aspose.slides.foss;

import org.aspose.slides.foss.theme.IThemeable;

/**
 * Represents common data for all slide types.
 */
public interface IBaseSlide extends IThemeable, ISlideComponent, IPresentationComponent {

    /**
     * Returns the name of a slide. Read/write.
     *
     * @return the slide name
     */
    String getName();

    /**
     * Sets the name of a slide.
     *
     * @param name the slide name
     */
    void setName(String name);

    /**
     * Returns the ID of a slide. Read-only.
     *
     * @return the slide ID
     */
    int getSlideId();

    /**
     * Returns the shapes of a slide. Read-only.
     *
     * @return the shape collection
     */
    IShapeCollection getShapes();
}
