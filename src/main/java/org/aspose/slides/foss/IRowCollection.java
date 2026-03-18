package org.aspose.slides.foss;

import java.util.List;

/**
 * Represents a collection of rows in a table.
 */
public interface IRowCollection {

    /**
     * Gets the row at the specified index.
     *
     * @param index the zero-based index
     * @return the row
     */
    IRow get(int index);

    /**
     * Returns the number of rows in the collection.
     *
     * @return the count
     */
    int size();

    /**
     * Adds a clone of the given row to the collection.
     *
     * @param templ            the row template to clone
     * @param withAttachedRows if {@code true}, also clone attached rows
     * @return the cloned rows
     */
    IRow[] addClone(IRow templ, boolean withAttachedRows);

    /**
     * Inserts a clone of the given row at the specified index.
     *
     * @param index            the zero-based index at which to insert
     * @param templ            the row template to clone
     * @param withAttachedRows if {@code true}, also clone attached rows
     * @return the cloned rows
     */
    IRow[] insertClone(int index, IRow templ, boolean withAttachedRows);

    /**
     * Removes the row at the specified index.
     *
     * @param firstRowIndex    the zero-based index of the first row to remove
     * @param withAttachedRows if {@code true}, also remove attached rows
     */
    void removeAt(int firstRowIndex, boolean withAttachedRows);

    /**
     * Returns the collection as a {@link List}.
     *
     * @return list view of all rows
     */
    List<IRow> asICollection();

    /**
     * Returns the collection as an {@link Iterable}.
     *
     * @return iterable over all rows
     */
    Iterable<IRow> asIEnumerable();
}
