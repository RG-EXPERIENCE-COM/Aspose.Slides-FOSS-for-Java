package org.aspose.slides.foss;

/**
 * Manages the notes slide for a given slide.
 */
public interface INotesSlideManager {

    /**
     * Returns the notes slide for this slide, or {@code null} if none exists.
     *
     * @return the notes slide, or {@code null}
     */
    INotesSlide getNotesSlide();

    /**
     * Adds a new notes slide to this slide.
     *
     * @return the newly created notes slide
     */
    INotesSlide addNotesSlide();

    /**
     * Removes the notes slide from this slide.
     */
    void removeNotesSlide();
}
