package org.aspose.slides.foss;

/**
 * Represents a gradient stop.
 */
public interface IGradientStop {

    /**
     * Returns or sets the position (0..1) of a gradient stop. Read/write.
     *
     * @return the position
     */
    double getPosition();

    /**
     * Sets the position of a gradient stop.
     *
     * @param value the position (0..1)
     */
    void setPosition(double value);

    /**
     * Returns the color of a gradient stop. Read-only.
     *
     * @return the color format
     */
    IColorFormat getColor();
}
