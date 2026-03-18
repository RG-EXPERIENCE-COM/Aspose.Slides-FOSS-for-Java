package org.aspose.slides.foss;

import java.util.List;

/**
 * Represents a collection of cells.
 */
public interface ICellCollection extends ISlideComponent, IPresentationComponent {

    /**
     * Gets the cell at the specified index.
     *
     * @param index the zero-based index
     * @return the cell
     */
    ICell get(int index);

    /**
     * Returns the number of cells in the collection.
     *
     * @return the count
     */
    int size();

    /**
     * Returns the base {@link ISlideComponent} interface. Read-only.
     *
     * @return this instance as {@link ISlideComponent}
     */
    ISlideComponent asISlideComponent();

    /**
     * Returns the collection as a {@link List}.
     *
     * @return list view of all cells
     */
    List<ICell> asICollection();

    /**
     * Returns the collection as an {@link Iterable}.
     *
     * @return iterable over all cells
     */
    Iterable<ICell> asIEnumerable();
}
