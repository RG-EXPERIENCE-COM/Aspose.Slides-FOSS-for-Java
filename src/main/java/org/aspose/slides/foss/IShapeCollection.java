package org.aspose.slides.foss;

import java.util.List;

/**
 * Represents a collection of shapes.
 */
public interface IShapeCollection {

    /**
     * Gets the parent group shape for this collection, or {@code null} if this
     * collection belongs directly to a slide.
     *
     * @return the parent group shape, or {@code null}
     */
    IGroupShape getParentGroup();

    /**
     * Returns this collection as an unmodifiable {@link List}.
     *
     * @return an unmodifiable list view of the shapes
     */
    List<IShape> asICollection();

    /**
     * Returns this collection as an {@link Iterable}.
     *
     * @return an iterable over the shapes
     */
    Iterable<IShape> asIEnumerable();

    /**
     * Gets the shape at the specified index.
     *
     * @param index the zero-based index
     * @return the shape
     */
    IShape get(int index);

    /**
     * Returns the number of shapes in the collection.
     *
     * @return the count
     */
    int size();

    /**
     * Creates a new AutoShape and adds it to the collection.
     *
     * @param shapeType the type of AutoShape
     * @param x         the x-coordinate in points
     * @param y         the y-coordinate in points
     * @param width     the width in points
     * @param height    the height in points
     * @return the created AutoShape
     */
    IAutoShape addAutoShape(ShapeType shapeType, double x, double y, double width, double height);

    /**
     * Creates a new AutoShape and adds it to the collection.
     *
     * @param shapeType          the type of AutoShape
     * @param x                  the x-coordinate in points
     * @param y                  the y-coordinate in points
     * @param width              the width in points
     * @param height             the height in points
     * @param createFromTemplate whether to create from the default template
     * @return the created AutoShape
     */
    IAutoShape addAutoShape(ShapeType shapeType, double x, double y, double width, double height, boolean createFromTemplate);

    /**
     * Creates a new AutoShape and inserts it at the specified index.
     *
     * @param index     the zero-based index
     * @param shapeType the type of AutoShape
     * @param x         the x-coordinate in points
     * @param y         the y-coordinate in points
     * @param width     the width in points
     * @param height    the height in points
     * @return the created AutoShape
     */
    IAutoShape insertAutoShape(int index, ShapeType shapeType, double x, double y, double width, double height);

    /**
     * Creates a new AutoShape and inserts it at the specified index.
     *
     * @param index              the zero-based index
     * @param shapeType          the type of AutoShape
     * @param x                  the x-coordinate in points
     * @param y                  the y-coordinate in points
     * @param width              the width in points
     * @param height             the height in points
     * @param createFromTemplate whether to create from the default template
     * @return the created AutoShape
     */
    IAutoShape insertAutoShape(int index, ShapeType shapeType, double x, double y, double width, double height, boolean createFromTemplate);

    /**
     * Adds a connector to the collection.
     *
     * @param shapeType the type of connector
     * @param x         the x-coordinate in points
     * @param y         the y-coordinate in points
     * @param width     the width in points
     * @param height    the height in points
     * @return the created connector
     */
    IConnector addConnector(ShapeType shapeType, double x, double y, double width, double height);

    /**
     * Adds a connector to the collection.
     *
     * @param shapeType          the type of connector
     * @param x                  the x-coordinate in points
     * @param y                  the y-coordinate in points
     * @param width              the width in points
     * @param height             the height in points
     * @param createFromTemplate whether to create from the default template
     * @return the created connector
     */
    IConnector addConnector(ShapeType shapeType, double x, double y, double width, double height, boolean createFromTemplate);

    /**
     * Creates a connector and inserts it at the specified index.
     *
     * @param index     the zero-based index
     * @param shapeType the type of connector
     * @param x         the x-coordinate in points
     * @param y         the y-coordinate in points
     * @param width     the width in points
     * @param height    the height in points
     * @return the created connector
     */
    IConnector insertConnector(int index, ShapeType shapeType, double x, double y, double width, double height);

    /**
     * Creates a connector and inserts it at the specified index.
     *
     * @param index              the zero-based index
     * @param shapeType          the type of connector
     * @param x                  the x-coordinate in points
     * @param y                  the y-coordinate in points
     * @param width              the width in points
     * @param height             the height in points
     * @param createFromTemplate whether to create from the default template
     * @return the created connector
     */
    IConnector insertConnector(int index, ShapeType shapeType, double x, double y, double width, double height, boolean createFromTemplate);

    /**
     * Adds a picture frame to the collection.
     *
     * @param shapeType the type of shape
     * @param x         the x-coordinate in points
     * @param y         the y-coordinate in points
     * @param width     the width in points
     * @param height    the height in points
     * @param image     the image
     * @return the created picture frame
     */
    IPictureFrame addPictureFrame(ShapeType shapeType, double x, double y, double width, double height, IPPImage image);

    /**
     * Creates a picture frame and inserts it at the specified index.
     *
     * @param index     the zero-based index
     * @param shapeType the type of shape
     * @param x         the x-coordinate in points
     * @param y         the y-coordinate in points
     * @param width     the width in points
     * @param height    the height in points
     * @param image     the image
     * @return the created picture frame
     */
    IPictureFrame insertPictureFrame(int index, ShapeType shapeType, double x, double y, double width, double height, IPPImage image);

    /**
     * Adds a table to the collection.
     *
     * @param x          the x-coordinate in points
     * @param y          the y-coordinate in points
     * @param colWidths  the column widths
     * @param rowHeights the row heights
     * @return the created table
     */
    ITable addTable(double x, double y, double[] colWidths, double[] rowHeights);

    /**
     * Creates a table and inserts it at the specified index.
     *
     * @param index      the zero-based index
     * @param x          the x-coordinate in points
     * @param y          the y-coordinate in points
     * @param colWidths  the column widths
     * @param rowHeights the row heights
     * @return the created table
     */
    ITable insertTable(int index, double x, double y, double[] colWidths, double[] rowHeights);

    /**
     * Returns the index of the specified shape.
     *
     * @param shape the shape to locate
     * @return the zero-based index, or -1 if not found
     */
    int indexOf(IShape shape);

    /**
     * Returns all shapes as an array.
     *
     * @return an array of shapes
     */
    IShape[] toArray();

    /**
     * Returns a range of shapes as an array.
     *
     * @param startIndex the zero-based start index
     * @param count      the number of elements to return
     * @return an array of shapes
     */
    IShape[] toArray(int startIndex, int count);

    /**
     * Moves a shape to the specified position.
     *
     * @param index the new zero-based position
     * @param shape the shape to reorder
     */
    void reorder(int index, IShape shape);

    /**
     * Moves multiple shapes to the specified position.
     *
     * @param index  the new zero-based starting position
     * @param shapes the shapes to reorder
     */
    void reorder(int index, IShape[] shapes);

    /**
     * Removes the shape at the specified index.
     *
     * @param index the zero-based index
     */
    void removeAt(int index);

    /**
     * Removes the specified shape from the collection.
     *
     * @param shape the shape to remove
     */
    void remove(IShape shape);

    /**
     * Removes all shapes from the collection.
     */
    void clear();
}
