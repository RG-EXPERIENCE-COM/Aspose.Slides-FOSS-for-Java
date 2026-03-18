package org.aspose.slides.foss;

import java.util.List;

/**
 * Represents collection of columns in a table.
 */
public interface IColumnCollection {

    /**
     * Gets the column at the specified index.
     *
     * @param index the zero-based index
     * @return the column
     */
    IColumn get(int index);

    /**
     * Returns the number of columns in the collection.
     *
     * @return the count
     */
    int size();

    /**
     * Creates a copy of the specified template column and adds it to the end.
     *
     * @param templ                the template column
     * @param withAttachedColumns  whether to clone attached columns
     * @return the newly created columns
     */
    List<IColumn> addClone(IColumn templ, boolean withAttachedColumns);

    /**
     * Creates a copy of the specified template column and inserts it at the specified index.
     *
     * @param index                the insertion index
     * @param templ                the template column
     * @param withAttachedColumns  whether to clone attached columns
     * @return the newly created columns
     */
    List<IColumn> insertClone(int index, IColumn templ, boolean withAttachedColumns);

    /**
     * Removes the column at the specified index.
     *
     * @param firstColumnIndex     the column index to remove
     * @param withAttachedRows     whether to remove attached rows
     */
    void removeAt(int firstColumnIndex, boolean withAttachedRows);

    /**
     * Returns the collection as a {@link List}.
     *
     * @return list view of all columns
     */
    List<IColumn> asICollection();

    /**
     * Returns the collection as an {@link Iterable}.
     *
     * @return iterable over all columns
     */
    Iterable<IColumn> asIEnumerable();
}
