package org.aspose.slides.foss;

import org.w3c.dom.Element;

/**
 * Represents a shape on a slide.
 */
public interface IShape extends ISlideComponent, IPresentationComponent, IHyperlinkContainer {

    /**
     * Determines whether the shape is a text holder. Read-only.
     *
     * @return {@code true} if the shape is a text holder
     */
    boolean isTextHolder();

    /**
     * Returns the placeholder for this shape. Read-only.
     *
     * @return the placeholder, or {@code null}
     */
    IPlaceholder getPlaceholder();

    /**
     * Returns the shape's custom data. Read-only.
     *
     * @return the custom data, or {@code null}
     */
    ICustomData getCustomData();

    /**
     * Returns the raw shape frame's properties. Read/write.
     *
     * @return the raw shape frame, or {@code null}
     */
    IShapeFrame getRawFrame();

    /**
     * Sets the raw shape frame's properties.
     *
     * @param value the raw shape frame
     */
    void setRawFrame(IShapeFrame value);

    /**
     * Returns the shape frame's properties. Read/write.
     *
     * @return the shape frame
     */
    IShapeFrame getFrame();

    /**
     * Sets the shape frame's properties.
     *
     * @param value the shape frame
     */
    void setFrame(IShapeFrame value);

    /**
     * Returns the line format for this shape. Read-only.
     *
     * @return the line format
     */
    ILineFormat getLineFormat();

    /**
     * Returns the 3-D format for this shape. Read-only.
     *
     * @return the 3-D format
     */
    IThreeDFormat getThreeDFormat();

    /**
     * Returns the effect format for this shape. Read-only.
     *
     * @return the effect format
     */
    IEffectFormat getEffectFormat();

    /**
     * Returns the fill format for this shape. Read-only.
     *
     * @return the fill format
     */
    IFillFormat getFillFormat();

    /**
     * Determines whether the shape is hidden. Read/write.
     *
     * @return {@code true} if the shape is hidden
     */
    boolean isHidden();

    /**
     * Sets whether the shape is hidden.
     *
     * @param value {@code true} to hide the shape
     */
    void setHidden(boolean value);

    /**
     * Returns the position of a shape in the z-order. Read-only.
     *
     * @return the z-order position
     */
    int getZOrderPosition();

    /**
     * Returns the number of connection sites on the shape. Read-only.
     *
     * @return the connection site count
     */
    int getConnectionSiteCount();

    /**
     * Returns the rotation angle of the shape in degrees.
     * A positive value indicates clockwise rotation. Read/write.
     *
     * @return the rotation in degrees
     */
    double getRotation();

    /**
     * Sets the rotation angle of the shape in degrees.
     *
     * @param rotation the rotation in degrees
     */
    void setRotation(double rotation);

    /**
     * Gets the x-coordinate of the upper-left corner of the shape, in points. Read/write.
     *
     * @return the x-coordinate in points
     */
    double getX();

    /**
     * Sets the x-coordinate of the upper-left corner of the shape, in points.
     *
     * @param x the x-coordinate in points
     */
    void setX(double x);

    /**
     * Gets the y-coordinate of the upper-left corner of the shape, in points. Read/write.
     *
     * @return the y-coordinate in points
     */
    double getY();

    /**
     * Sets the y-coordinate of the upper-left corner of the shape, in points.
     *
     * @param y the y-coordinate in points
     */
    void setY(double y);

    /**
     * Gets the width of the shape, in points. Read/write.
     *
     * @return the width in points
     */
    double getWidth();

    /**
     * Sets the width of the shape, in points.
     *
     * @param width the width in points
     */
    void setWidth(double width);

    /**
     * Gets the height of the shape, in points. Read/write.
     *
     * @return the height in points
     */
    double getHeight();

    /**
     * Sets the height of the shape, in points.
     *
     * @param height the height in points
     */
    void setHeight(double height);

    /**
     * Returns or sets the alternative text associated with a shape. Read/write.
     *
     * @return the alternative text
     */
    String getAlternativeText();

    /**
     * Sets the alternative text associated with a shape.
     *
     * @param value the alternative text
     */
    void setAlternativeText(String value);

    /**
     * Returns or sets the title of alternative text associated with a shape. Read/write.
     *
     * @return the alternative text title
     */
    String getAlternativeTextTitle();

    /**
     * Sets the title of alternative text associated with a shape.
     *
     * @param value the alternative text title
     */
    void setAlternativeTextTitle(String value);

    /**
     * Returns or sets the name of a shape. Read/write.
     *
     * @return the name
     */
    String getName();

    /**
     * Sets the name of a shape.
     *
     * @param value the name
     */
    void setName(String value);

    /**
     * Gets or sets the 'Mark as decorative' option. Read/write.
     *
     * @return {@code true} if the shape is decorative
     */
    boolean isDecorative();

    /**
     * Sets the 'Mark as decorative' option.
     *
     * @param value {@code true} to mark as decorative
     */
    void setDecorative(boolean value);

    /**
     * Returns an internal, presentation-scoped identifier. Read-only.
     *
     * @return the unique id
     */
    int getUniqueId();

    /**
     * Returns a slide-scoped unique identifier that remains constant
     * for the lifetime of the shape. Read-only.
     *
     * @return the office interop shape id
     */
    int getOfficeInteropShapeId();

    /**
     * Determines whether the shape is grouped. Read-only.
     *
     * @return {@code true} if the shape is grouped
     */
    boolean isGrouped();

    /**
     * Allows to get base {@link ISlideComponent} interface. Read-only.
     *
     * @return this instance as {@link ISlideComponent}
     */
    ISlideComponent getAsISlideComponent();

    /**
     * Gets the AutoShape type.
     *
     * @return the shape type
     */
    ShapeType getShapeType();

    /**
     * Returns the {@code cNvPr} element for this shape, or {@code null}.
     * Internal API used for shape identification.
     *
     * @return the cNvPr element, or null
     */
    Element getCNvPr();
}
