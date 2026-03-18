package org.aspose.slides.foss;

import java.util.List;

/**
 * Represents a collection of master slides.
 */
public interface IMasterSlideCollection {

    /**
     * Returns the number of master slides in the collection.
     *
     * @return the count
     */
    int size();

    /**
     * Returns the master slide at the given index.
     *
     * @param index the zero-based index
     * @return the master slide
     */
    IMasterSlide get(int index);

    /**
     * Returns this collection as an unmodifiable {@link List}.
     *
     * @return a list view of the master slides
     */
    List<IMasterSlide> asICollection();

    /**
     * Returns this collection as an {@link Iterable}.
     *
     * @return an iterable view of the master slides
     */
    Iterable<IMasterSlide> asIEnumerable();

    /**
     * Adds a clone of the specified master slide to the collection.
     *
     * @param sourceMaster the master slide to clone
     * @return the newly added master slide
     */
    IMasterSlide addClone(IMasterSlide sourceMaster);
}
