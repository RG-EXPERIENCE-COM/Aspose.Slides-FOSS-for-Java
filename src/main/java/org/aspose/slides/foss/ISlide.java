package org.aspose.slides.foss;

/**
 * Represents a slide in a presentation.
 */
public interface ISlide extends IBaseSlide {

    /**
     * Returns the 1-based slide number.
     * Index of slide in collection is always equal to {@code slideNumber - 1}.
     * Read/write.
     *
     * @return the slide number
     */
    int getSlideNumber();

    /**
     * Sets the 1-based slide number.
     *
     * @param value the slide number
     */
    void setSlideNumber(int value);

    /**
     * Determines whether the specified slide is hidden during a slide show.
     * Read/write.
     *
     * @return {@code true} if the slide is hidden
     */
    boolean isHidden();

    /**
     * Sets whether the specified slide is hidden during a slide show.
     *
     * @param value {@code true} to hide the slide
     */
    void setHidden(boolean value);

    /**
     * Returns the layout slide for the current slide. Read/write.
     *
     * @return the layout slide, or {@code null} if none
     */
    ILayoutSlide getLayoutSlide();

    /**
     * Sets the layout slide for the current slide.
     *
     * @param value the layout slide
     */
    void setLayoutSlide(ILayoutSlide value);

    /**
     * Returns the notes slide manager for this slide. Read-only.
     *
     * @return the notes slide manager
     */
    INotesSlideManager getNotesSlideManager();

    /**
     * Returns comments on this slide, optionally filtered by author.
     *
     * @param author the author to filter by, or {@code null} for all comments
     * @return array of matching comments
     */
    IComment[] getSlideComments(ICommentAuthor author);

    /**
     * Removes this slide from the presentation.
     */
    void remove();
}
