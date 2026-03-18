package org.aspose.slides.foss;

/**
 * Represents properties of shape's main face relief.
 */
public interface IShapeBevel {

    /**
     * Returns the width of the bevel. Read/write.
     *
     * @return the width
     */
    double getWidth();

    /**
     * Sets the width of the bevel.
     *
     * @param value the width
     */
    void setWidth(double value);

    /**
     * Returns the height of the bevel. Read/write.
     *
     * @return the height
     */
    double getHeight();

    /**
     * Sets the height of the bevel.
     *
     * @param value the height
     */
    void setHeight(double value);

    /**
     * Returns the bevel type. Read/write.
     *
     * @return the bevel preset type
     */
    BevelPresetType getBevelType();

    /**
     * Sets the bevel type.
     *
     * @param value the bevel preset type
     */
    void setBevelType(BevelPresetType value);
}
