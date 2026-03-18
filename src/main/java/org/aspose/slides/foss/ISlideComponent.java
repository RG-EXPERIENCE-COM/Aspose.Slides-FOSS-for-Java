package org.aspose.slides.foss;

/**
 * Represents a component of a slide.
 */
public interface ISlideComponent extends IPresentationComponent {

    /**
     * Returns the parent slide. Read-only {@link IBaseSlide}.
     *
     * @return the parent slide
     */
    IBaseSlide getSlide();

    /**
     * Returns the base {@link IPresentationComponent} interface. Read-only.
     *
     * @return this instance as {@link IPresentationComponent}
     */
    IPresentationComponent asIPresentationComponent();
}
