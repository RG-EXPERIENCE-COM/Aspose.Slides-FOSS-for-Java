package org.aspose.slides.foss;

/**
 * Represents manager which holds behavior of the notes slide placeholders,
 * including header placeholder.
 */
public interface INotesSlideHeaderFooterManager extends IBaseHandoutNotesSlideHeaderFooterManager {

    /**
     * Returns this instance as {@link IBaseHandoutNotesSlideHeaderFooterManager}.
     *
     * @return this manager as the base handout/notes slide interface
     */
    IBaseHandoutNotesSlideHeaderFooterManager asIBaseHandoutNotesSlideHeaderFooterManag();

    /**
     * Returns this instance as {@link IBaseSlideHeaderFooterManager}.
     *
     * @return this manager as the base slide interface
     */
    IBaseSlideHeaderFooterManager asIBaseSlideHeaderFooterManager();

    /**
     * Returns this instance as {@link IBaseHeaderFooterManager}.
     *
     * @return this manager as the base interface
     */
    IBaseHeaderFooterManager asIBaseHeaderFooterManager();
}
