package org.aspose.slides.foss;

import org.aspose.slides.foss.drawing.RectangleF;

/**
 * Represents shape frame's properties.
 */
public interface IShapeFrame {

    /**
     * Returns the x-coordinate of the upper-left corner. Read-only.
     *
     * @return the x-coordinate
     */
    double getX();

    /**
     * Returns the y-coordinate of the upper-left corner. Read-only.
     *
     * @return the y-coordinate
     */
    double getY();

    /**
     * Returns the width. Read-only.
     *
     * @return the width
     */
    double getWidth();

    /**
     * Returns the height. Read-only.
     *
     * @return the height
     */
    double getHeight();

    /**
     * Returns the rotation angle in degrees. Read-only.
     *
     * @return the rotation
     */
    double getRotation();

    /**
     * Returns the x-coordinate of the center. Read-only.
     *
     * @return the center x-coordinate
     */
    double getCenterX();

    /**
     * Returns the y-coordinate of the center. Read-only.
     *
     * @return the center y-coordinate
     */
    double getCenterY();

    /**
     * Determines whether the frame is flipped horizontally. Read-only.
     *
     * @return the flip horizontal value
     */
    NullableBool getFlipH();

    /**
     * Determines whether the frame is flipped vertically. Read-only.
     *
     * @return the flip vertical value
     */
    NullableBool getFlipV();

    /**
     * Returns the coordinates of this frame as a rectangle. Read-only.
     *
     * @return the bounding rectangle
     */
    RectangleF getRectangle();

    /**
     * Creates a deep copy of this shape frame.
     *
     * @return the cloned shape frame
     */
    IShapeFrame cloneT();
}
