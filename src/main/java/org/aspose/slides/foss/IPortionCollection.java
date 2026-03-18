package org.aspose.slides.foss;

/**
 * Represents a collection of text portions.
 */
public interface IPortionCollection extends Iterable<IPortion> {

    /**
     * Gets the portion at the specified index.
     *
     * @param index the zero-based index
     * @return the portion
     */
    IPortion get(int index);

    /**
     * Returns the number of portions in the collection.
     *
     * @return the count
     */
    int size();

    /**
     * Gets the number of elements actually contained in the collection.
     * Read-only.
     *
     * @return the count
     */
    int count();

    /**
     * Returns this collection as an iterable.
     *
     * @return an iterable over the portions
     */
    Iterable<IPortion> asIEnumerable();

    /**
     * Adds a portion to the end of the collection.
     *
     * @param value the portion to add
     */
    void add(IPortion value);

    /**
     * Returns the index of the specified portion.
     *
     * @param item the portion to locate
     * @return the zero-based index, or -1 if not found
     */
    int indexOf(IPortion item);

    /**
     * Inserts a portion at the specified index.
     *
     * @param index the zero-based index at which to insert
     * @param value the portion to insert
     */
    void insert(int index, IPortion value);

    /**
     * Removes all portions from the collection.
     */
    void clear();

    /**
     * Determines whether the collection contains the specified portion.
     *
     * @param item the portion to locate
     * @return {@code true} if the portion is found
     */
    boolean contains(IPortion item);

    /**
     * Removes the first occurrence of the specified portion.
     *
     * @param item the portion to remove
     * @return {@code true} if the portion was removed
     */
    boolean remove(IPortion item);

    /**
     * Removes the portion at the specified index.
     *
     * @param index the zero-based index
     */
    void removeAt(int index);

    /**
     * Gets a value indicating whether the collection is read-only.
     *
     * @return {@code true} if the collection is read-only
     */
    boolean isReadOnly();
}
