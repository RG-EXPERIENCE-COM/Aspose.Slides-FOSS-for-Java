package org.aspose.slides.foss;

/**
 * Represents base interface for handout and notes slide header/footer managers.
 * Extends {@link IBaseSlideHeaderFooterManager} with header placeholder support.
 */
public interface IBaseHandoutNotesSlideHeaderFooterManager extends IBaseSlideHeaderFooterManager {

    /**
     * Gets value indicating that a header placeholder is present.
     *
     * @return {@code true} if the header placeholder is visible
     */
    boolean isHeaderVisible();

    /**
     * Modifies header placeholder visibility.
     *
     * @param isVisible {@code true} to show the header placeholder
     */
    void setHeaderVisibility(boolean isVisible);

    /**
     * Assigns text content to the header placeholder.
     *
     * @param text the header text
     */
    void setHeaderText(String text);
}
