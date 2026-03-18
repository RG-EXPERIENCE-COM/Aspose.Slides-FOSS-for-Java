package org.aspose.slides.foss;

/**
 * Represents a collection of all layout slides in a presentation.
 *
 * <p>Extends the concept of a layout slide collection with methods for
 * adding/cloning layout slides in context of uniting of the individual
 * collections of master's layout slides.</p>
 */
public interface IGlobalLayoutSlideCollection extends ILayoutSlideCollection {

    /**
     * Returns this collection as an {@link ILayoutSlideCollection}.
     *
     * @return this collection viewed as an {@code ILayoutSlideCollection}
     */
    ILayoutSlideCollection asILayoutSlideCollection();
}
