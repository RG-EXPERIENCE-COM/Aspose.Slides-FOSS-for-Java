package org.aspose.slides.foss;

import java.util.List;

/**
 * Represents a collection of slides in a presentation.
 */
public interface ISlideCollection extends Iterable<ISlide> {

    /**
     * Returns the slide at the given index.
     *
     * @param index the zero-based index
     * @return the slide
     */
    ISlide get(int index);

    /** Returns the number of slides. */
    int size();

    /**
     * Returns this collection as a {@link List}.
     *
     * @return a list view of the slides
     */
    List<ISlide> asICollection();

    /**
     * Returns this collection as an {@link Iterable}.
     *
     * @return an iterable view of the slides
     */
    Iterable<ISlide> asIEnumerable();

    /**
     * Adds a clone of the specified slide to the end of the collection.
     *
     * @param sourceSlide the slide to clone
     * @return the new slide
     */
    ISlide addClone(ISlide sourceSlide);

    /**
     * Adds a clone of the specified slide to the end of the collection,
     * applying the specified destination layout.
     *
     * @param sourceSlide the slide to clone
     * @param destLayout  the layout to apply
     * @return the new slide
     */
    ISlide addClone(ISlide sourceSlide, ILayoutSlide destLayout);

    /**
     * Adds a clone of the specified slide to the end of the collection,
     * applying the specified master slide.
     *
     * @param sourceSlide              the slide to clone
     * @param destMaster               the destination master slide
     * @param allowCloneMissingLayout  whether to allow cloning if the layout is missing
     * @return the new slide
     */
    ISlide addClone(ISlide sourceSlide, IMasterSlide destMaster, boolean allowCloneMissingLayout);

    /**
     * Inserts a clone of the specified slide at the given index.
     *
     * @param index       the zero-based insertion index
     * @param sourceSlide the slide to clone
     * @return the new slide
     */
    ISlide insertClone(int index, ISlide sourceSlide);

    /**
     * Inserts a clone of the specified slide at the given index,
     * applying the specified destination layout.
     *
     * @param index       the zero-based insertion index
     * @param sourceSlide the slide to clone
     * @param destLayout  the layout to apply
     * @return the new slide
     */
    ISlide insertClone(int index, ISlide sourceSlide, ILayoutSlide destLayout);

    /**
     * Inserts a clone of the specified slide at the given index,
     * applying the specified master slide.
     *
     * @param index                    the zero-based insertion index
     * @param sourceSlide              the slide to clone
     * @param destMaster               the destination master slide
     * @param allowCloneMissingLayout  whether to allow cloning if the layout is missing
     * @return the new slide
     */
    ISlide insertClone(int index, ISlide sourceSlide, IMasterSlide destMaster, boolean allowCloneMissingLayout);

    /**
     * Returns an array containing all slides in the collection.
     *
     * @return an array of slides
     */
    ISlide[] toArray();

    /**
     * Returns an array of slides from the given range.
     *
     * @param startIndex the zero-based start index
     * @param count      the number of slides to include
     * @return an array of slides
     */
    ISlide[] toArray(int startIndex, int count);

    /**
     * Adds a new empty slide to the end of the collection with the specified layout.
     *
     * @param layout the layout slide for the new slide
     * @return the new slide
     */
    ISlide addEmptySlide(ILayoutSlide layout);

    /**
     * Inserts a new empty slide at the specified index with the given layout.
     *
     * @param index  the zero-based insertion index
     * @param layout the layout slide for the new slide
     * @return the new slide
     */
    ISlide insertEmptySlide(int index, ILayoutSlide layout);

    /**
     * Removes the first occurrence of the specified slide from the collection.
     *
     * @param value the slide to remove
     */
    void remove(ISlide value);

    /**
     * Removes the slide at the specified index.
     *
     * @param index the zero-based index of the slide to remove
     */
    void removeAt(int index);

    /**
     * Returns the zero-based index of the specified slide, or {@code -1} if not found.
     *
     * @param slide the slide to locate
     * @return the index, or {@code -1}
     */
    int indexOf(ISlide slide);
}
