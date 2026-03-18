package org.aspose.slides.foss;

/**
 * Represents a notes slide in a presentation.
 */
public interface INotesSlide extends IBaseSlide {

    /**
     * Returns the text frame with notes text.
     *
     * @return the notes text frame
     */
    ITextFrame getNotesTextFrame();

    /**
     * Returns the header/footer manager for this notes slide.
     *
     * @return the header/footer manager
     */
    INotesSlideHeaderFooterManager getHeaderFooterManager();

    /**
     * Returns the parent slide this notes slide is associated with.
     *
     * @return the parent slide
     */
    ISlide getParentSlide();

    /**
     * Allows to get the base {@link IBaseSlide} interface.
     *
     * @return this instance as {@link IBaseSlide}
     */
    IBaseSlide asIBaseSlide();
}
