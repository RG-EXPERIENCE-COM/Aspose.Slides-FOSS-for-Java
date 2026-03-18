package org.aspose.slides.foss;

import org.aspose.slides.foss.drawing.RectangleF;

import java.util.Objects;

/**
 * Represents an immutable shape frame with position, size, rotation, and flip properties.
 */
public final class ShapeFrame implements IShapeFrame {

    private final double x;
    private final double y;
    private final double width;
    private final double height;
    private final NullableBool flipH;
    private final NullableBool flipV;
    private final double rotation;

    /**
     * Creates a new ShapeFrame.
     *
     * @param x             the x-coordinate
     * @param y             the y-coordinate
     * @param width         the width
     * @param height        the height
     * @param flipH         horizontal flip
     * @param flipV         vertical flip
     * @param rotationAngle the rotation angle in degrees
     */
    public ShapeFrame(double x, double y, double width, double height,
                      NullableBool flipH, NullableBool flipV, double rotationAngle) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.flipH = flipH;
        this.flipV = flipV;
        this.rotation = rotationAngle;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public double getRotation() {
        return rotation;
    }

    @Override
    public double getCenterX() {
        return x + width / 2.0;
    }

    @Override
    public double getCenterY() {
        return y + height / 2.0;
    }

    @Override
    public NullableBool getFlipH() {
        return flipH;
    }

    @Override
    public NullableBool getFlipV() {
        return flipV;
    }

    @Override
    public RectangleF getRectangle() {
        return new RectangleF((float) x, (float) y, (float) width, (float) height);
    }

    @Override
    public IShapeFrame cloneT() {
        return new ShapeFrame(x, y, width, height, flipH, flipV, rotation);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ShapeFrame other)) return false;
        return Double.compare(x, other.x) == 0
                && Double.compare(y, other.y) == 0
                && Double.compare(width, other.width) == 0
                && Double.compare(height, other.height) == 0
                && Double.compare(rotation, other.rotation) == 0
                && flipH == other.flipH
                && flipV == other.flipV;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, width, height, rotation, flipH, flipV);
    }

    @Override
    public String toString() {
        return "ShapeFrame[x=" + x + ", y=" + y + ", w=" + width + ", h=" + height
                + ", rot=" + rotation + "]";
    }
}
