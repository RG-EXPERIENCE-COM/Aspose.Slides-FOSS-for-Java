package org.aspose.slides.foss;

/**
 * Base class for property-value-inheritance (PVI) objects
 * that are bound to a slide and presentation.
 *
 * <p>Encapsulates the pattern of an object being associated with a parent slide
 * and, through that, with a presentation.</p>
 */
public class PVIObject implements ISlideComponent, IPresentationComponent {

    /** The parent slide this object is associated with. */
    protected IBaseSlide parentSlide;

    /**
     * Creates a PVIObject with no parent slide.
     */
    public PVIObject() {
    }

    /**
     * Creates a PVIObject associated with the given slide.
     *
     * @param parentSlide the parent slide
     */
    public PVIObject(IBaseSlide parentSlide) {
        this.parentSlide = parentSlide;
    }

    @Override
    public IBaseSlide getSlide() {
        return parentSlide;
    }

    @Override
    public IPresentation getPresentation() {
        if (parentSlide != null) {
            return parentSlide.getPresentation();
        }
        return null;
    }

    /**
     * Returns this object as an {@link IPresentationComponent}.
     *
     * @return this instance
     */
    public IPresentationComponent asIPresentationComponent() {
        return this;
    }
}
