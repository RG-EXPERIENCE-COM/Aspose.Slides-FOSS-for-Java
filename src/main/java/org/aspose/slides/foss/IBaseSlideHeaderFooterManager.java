package org.aspose.slides.foss;

/**
 * Represents base interface for slide header/footer managers that manage
 * footer, slide number, and date-time placeholders.
 */
public interface IBaseSlideHeaderFooterManager extends IBaseHeaderFooterManager {

    /**
     * Gets value indicating that a footer placeholder is present.
     *
     * @return {@code true} if the footer placeholder is visible
     */
    boolean isFooterVisible();

    /**
     * Gets value indicating that a page number placeholder is present.
     *
     * @return {@code true} if the slide number placeholder is visible
     */
    boolean isSlideNumberVisible();

    /**
     * Gets value indicating that a date-time placeholder is present.
     *
     * @return {@code true} if the date-time placeholder is visible
     */
    boolean isDateTimeVisible();

    /**
     * Modifies footer placeholder visibility.
     *
     * @param isVisible {@code true} to show the footer placeholder
     */
    void setFooterVisibility(boolean isVisible);

    /**
     * Modifies page number placeholder visibility.
     *
     * @param isVisible {@code true} to show the slide number placeholder
     */
    void setSlideNumberVisibility(boolean isVisible);

    /**
     * Modifies date-time placeholder visibility.
     *
     * @param isVisible {@code true} to show the date-time placeholder
     */
    void setDateTimeVisibility(boolean isVisible);

    /**
     * Assigns text content to the footer placeholder.
     *
     * @param text the footer text
     */
    void setFooterText(String text);

    /**
     * Assigns text content to the date-time placeholder.
     *
     * @param text the date-time text
     */
    void setDateTimeText(String text);
}
