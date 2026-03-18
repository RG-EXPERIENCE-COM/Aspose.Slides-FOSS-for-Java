package org.aspose.slides.foss.drawing;

import java.util.Objects;

/**
 * Represents a rectangle defined by position and size using floating-point coordinates.
 */
public final class RectangleF {

    private final float x;
    private final float y;
    private final float width;
    private final float height;

    /**
     * Creates a new RectangleF.
     *
     * @param x      the x-coordinate of the upper-left corner
     * @param y      the y-coordinate of the upper-left corner
     * @param width  the width
     * @param height the height
     */
    public RectangleF(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Gets the x-coordinate of the upper-left corner.
     *
     * @return the x-coordinate
     */
    public float getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the upper-left corner.
     *
     * @return the y-coordinate
     */
    public float getY() {
        return y;
    }

    /**
     * Gets the width.
     *
     * @return the width
     */
    public float getWidth() {
        return width;
    }

    /**
     * Gets the height.
     *
     * @return the height
     */
    public float getHeight() {
        return height;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof RectangleF other
                && Float.compare(x, other.x) == 0
                && Float.compare(y, other.y) == 0
                && Float.compare(width, other.width) == 0
                && Float.compare(height, other.height) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, width, height);
    }

    @Override
    public String toString() {
        return "RectangleF(x=%s, y=%s, width=%s, height=%s)".formatted(x, y, width, height);
    }
}
