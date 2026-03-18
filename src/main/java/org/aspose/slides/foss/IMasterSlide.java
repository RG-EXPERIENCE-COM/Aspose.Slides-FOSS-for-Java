package org.aspose.slides.foss;

/**
 * Represents a master slide in a presentation.
 */
public interface IMasterSlide extends IBaseSlide {

    /**
     * Returns the collection of layout slides belonging to this master.
     * Read-only {@link ILayoutSlideCollection}.
     *
     * @return the layout slide collection
     */
    ILayoutSlideCollection getLayoutSlides();
}
