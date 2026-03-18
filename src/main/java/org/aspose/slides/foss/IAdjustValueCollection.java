package org.aspose.slides.foss;

import java.util.List;

/**
 * Represents a collection of shape adjustment values.
 */
public interface IAdjustValueCollection extends Iterable<IAdjustValue> {

    /**
     * Gets the number of adjustment values.
     *
     * @return the count
     */
    int size();

    /**
     * Gets the adjustment value at the specified index.
     *
     * @param index the zero-based index
     * @return the adjustment value
     */
    IAdjustValue get(int index);

    /**
     * Returns the collection as a {@link List} of {@link IAdjustValue}.
     *
     * @return an unmodifiable list of all adjustment values
     */
    List<IAdjustValue> asICollection();

    /**
     * Returns the collection as an {@link Iterable} of {@link IAdjustValue}.
     *
     * @return an iterable over all adjustment values
     */
    Iterable<IAdjustValue> asIEnumerable();
}
