package org.aspose.slides.foss.drawing;

import java.util.Objects;

/**
 * Represents a 2D point with float coordinates.
 */
public final class PointF {

    /** Empty point with coordinates (0, 0). */
    public static final PointF EMPTY = new PointF(0f, 0f);

    private float x;
    private float y;

    /**
     * Initializes a new instance of PointF with the specified coordinates.
     *
     * @param x the X coordinate
     * @param y the Y coordinate
     */
    public PointF(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Initializes a new instance of PointF with coordinates (0, 0).
     */
    public PointF() {
        this(0f, 0f);
    }

    /**
     * Gets the X coordinate.
     *
     * @return the X coordinate
     */
    public float getX() {
        return x;
    }

    /**
     * Sets the X coordinate.
     *
     * @param x the X coordinate
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Gets the Y coordinate.
     *
     * @return the Y coordinate
     */
    public float getY() {
        return y;
    }

    /**
     * Sets the Y coordinate.
     *
     * @param y the Y coordinate
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Returns {@code true} if both coordinates are zero.
     *
     * @return true if empty
     */
    public boolean isEmpty() {
        return x == 0f && y == 0f;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof PointF other && x == other.x && y == other.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "PointF(x=%s, y=%s)".formatted(x, y);
    }
}
