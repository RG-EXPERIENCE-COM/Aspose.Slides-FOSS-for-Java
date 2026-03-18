package org.aspose.slides.foss;

/**
 * Represents common data for all slide types.
 */
public class BaseSlide implements IBaseSlide {

    private String name = "";
    private int slideId;
    private IShapeCollection shapesCollection;

    /**
     * Creates a BaseSlide with default values.
     */
    public BaseSlide() {
    }

    /**
     * Creates a BaseSlide with the given name and ID.
     *
     * @param name    the slide name
     * @param slideId the slide ID
     */
    public BaseSlide(String name, int slideId) {
        this.name = name != null ? name : "";
        this.slideId = slideId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name != null ? name : "";
    }

    @Override
    public int getSlideId() {
        return slideId;
    }

    @Override
    public IShapeCollection getShapes() {
        if (shapesCollection == null) {
            shapesCollection = new ShapeCollection();
        }
        return shapesCollection;
    }

    @Override
    public IBaseSlide getSlide() {
        return this;
    }

    @Override
    public IPresentationComponent asIPresentationComponent() {
        return this;
    }

    /**
     * Returns the slide part object for this slide.
     *
     * <p>Subclasses should override this method to return the appropriate
     * part object ({@code SlidePart}, {@code LayoutSlidePart}, or
     * {@code MasterSlidePart}).</p>
     *
     * @return the slide part, or {@code null} if no part is associated
     */
    protected Object getSlidePart() {
        return null;
    }

    @Override
    public IPresentation getPresentation() {
        return null;
    }
}
