package org.aspose.slides.foss;

import java.util.List;

/**
 * Represents a base class for collection of a layout slides.
 */
public interface ILayoutSlideCollection extends Iterable<ILayoutSlide> {

    /**
     * Returns the layout slide at the given index.
     *
     * @param index the zero-based index
     * @return the layout slide
     */
    ILayoutSlide get(int index);

    /**
     * Returns the number of layout slides in the collection.
     *
     * @return the count
     */
    int size();

    /**
     * Returns {@code true} if this collection contains no layout slides.
     *
     * @return {@code true} if empty
     */
    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns this collection as an unmodifiable {@link List}.
     *
     * @return an unmodifiable list view of this collection
     */
    List<ILayoutSlide> asICollection();

    /**
     * Returns this collection as an {@link Iterable}.
     *
     * @return an iterable view of this collection
     */
    Iterable<ILayoutSlide> asIEnumerable();

    /**
     * Returns the first layout slide with the specified type.
     *
     * @param type the desired layout type
     * @return the matching layout slide, or {@code null} if not found
     */
    ILayoutSlide getByType(SlideLayoutType type);
}
