package org.aspose.slides.foss.drawing;

import java.util.Objects;

/**
 * Represents a 2D size with float dimensions.
 */
public final class SizeF {

    /** Empty size with dimensions (0, 0). */
    public static final SizeF EMPTY = new SizeF(0f, 0f);

    private float width;
    private float height;

    /**
     * Initializes a new instance of SizeF with the specified dimensions.
     *
     * @param width  the width
     * @param height the height
     */
    public SizeF(float width, float height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Initializes a new instance of SizeF with dimensions (0, 0).
     */
    public SizeF() {
        this(0f, 0f);
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
     * Sets the width.
     *
     * @param width the width
     */
    public void setWidth(float width) {
        this.width = width;
    }

    /**
     * Gets the height.
     *
     * @return the height
     */
    public float getHeight() {
        return height;
    }

    /**
     * Sets the height.
     *
     * @param height the height
     */
    public void setHeight(float height) {
        this.height = height;
    }

    /**
     * Returns {@code true} if both dimensions are zero.
     *
     * @return true if empty
     */
    public boolean isEmpty() {
        return width == 0f && height == 0f;
    }

    /**
     * Adds two sizes component-wise.
     *
     * @param a the first size
     * @param b the second size
     * @return a new SizeF whose dimensions are the sums of the inputs
     */
    public static SizeF add(SizeF a, SizeF b) {
        return new SizeF(a.width + b.width, a.height + b.height);
    }

    /**
     * Converts this SizeF to a Size by truncating to integer dimensions.
     *
     * @return a new Size with truncated dimensions
     */
    public Size toSize() {
        return new Size((int) width, (int) height);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof SizeF other && width == other.width && height == other.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height);
    }

    @Override
    public String toString() {
        return "SizeF(width=%s, height=%s)".formatted(width, height);
    }
}
