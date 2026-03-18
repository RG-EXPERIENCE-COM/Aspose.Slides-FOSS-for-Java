package org.aspose.slides.foss;

/**
 * Represents a layout slide.
 */
public interface ILayoutSlide extends IBaseSlide {

    /**
     * Returns the master slide for this layout. Read/write.
     *
     * @return the master slide
     */
    IMasterSlide getMasterSlide();

    /**
     * Sets the master slide for this layout.
     *
     * @param value the master slide
     */
    void setMasterSlide(IMasterSlide value);

    /**
     * Returns the layout type of this slide. Read-only.
     *
     * @return the slide layout type
     */
    SlideLayoutType getLayoutType();
}
