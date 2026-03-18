package org.aspose.slides.foss;

/**
 * Represents a collection of paragraphs.
 */
public interface IParagraphCollection extends Iterable<IParagraph> {

    /**
     * Gets the paragraph at the specified index.
     *
     * @param index the zero-based index
     * @return the paragraph
     */
    IParagraph get(int index);

    /**
     * Returns the number of paragraphs in the collection.
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
     * Returns the base {@link ISlideComponent} interface. Read-only.
     *
     * @return this instance as {@link ISlideComponent}, or {@code null}
     */
    ISlideComponent asISlideComponent();

    /**
     * Returns this collection as an iterable.
     *
     * @return an iterable over the paragraphs
     */
    Iterable<IParagraph> asIEnumerable();

    /**
     * Adds a paragraph to the end of the collection.
     *
     * @param value the paragraph to add
     */
    void add(IParagraph value);

    /**
     * Inserts a paragraph at the specified index.
     *
     * @param index the zero-based index at which to insert
     * @param value the paragraph to insert
     */
    void insert(int index, IParagraph value);

    /**
     * Removes all paragraphs from the collection.
     */
    void clear();

    /**
     * Removes the paragraph at the specified index.
     *
     * @param index the zero-based index
     */
    void removeAt(int index);

    /**
     * Removes the first occurrence of the specified paragraph.
     *
     * @param item the paragraph to remove
     * @return {@code true} if the paragraph was removed
     */
    boolean remove(IParagraph item);

    /**
     * Gets a value indicating whether the collection is read-only.
     *
     * @return {@code true} if the collection is read-only
     */
    boolean isReadOnly();

    /**
     * Determines whether the collection contains the specified paragraph.
     *
     * @param item the paragraph to locate
     * @return {@code true} if the paragraph is found
     */
    boolean contains(IParagraph item);

    /**
     * Returns the zero-based index of the first occurrence of the specified paragraph.
     *
     * @param item the paragraph to locate
     * @return the index, or {@code -1} if not found
     */
    int indexOf(IParagraph item);

    /**
     * Returns this instance as {@link IPresentationComponent}.
     *
     * @return this instance as {@link IPresentationComponent}, or {@code null}
     */
    IPresentationComponent asIPresentationComponent();
}
