package org.aspose.slides.foss.drawing;

import java.util.Objects;

/**
 * Represents a 2D size with integer dimensions.
 */
public final class Size {

    /** Empty size with dimensions (0, 0). */
    public static final Size EMPTY = new Size(0, 0);

    private int width;
    private int height;

    /**
     * Initializes a new instance of Size with the specified dimensions.
     *
     * @param width  the width
     * @param height the height
     */
    public Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Initializes a new instance of Size with dimensions (0, 0).
     */
    public Size() {
        this(0, 0);
    }

    /**
     * Gets the width.
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the width.
     *
     * @param width the width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Gets the height.
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the height.
     *
     * @param height the height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Returns {@code true} if both dimensions are zero.
     *
     * @return true if empty
     */
    public boolean isEmpty() {
        return width == 0 && height == 0;
    }

    /**
     * Adds two sizes component-wise.
     *
     * @param a the first size
     * @param b the second size
     * @return a new Size whose dimensions are the sums of the inputs
     */
    public static Size add(Size a, Size b) {
        return new Size(a.width + b.width, a.height + b.height);
    }

    /**
     * Subtracts one size from another component-wise.
     *
     * @param a the size to subtract from
     * @param b the size to subtract
     * @return a new Size whose dimensions are the differences
     */
    public static Size subtract(Size a, Size b) {
        return new Size(a.width - b.width, a.height - b.height);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Size other && width == other.width && height == other.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height);
    }

    @Override
    public String toString() {
        return "Size(width=%d, height=%d)".formatted(width, height);
    }
}
