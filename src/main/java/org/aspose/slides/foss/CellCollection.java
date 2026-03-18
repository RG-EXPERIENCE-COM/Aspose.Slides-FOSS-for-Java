package org.aspose.slides.foss;

import org.aspose.slides.foss.internal.BaseCollection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a collection of cells.
 */
public class CellCollection extends BaseCollection<ICell> implements ICellCollection {

    private List<ICell> cells;
    private Object slidePart;
    private IBaseSlide parentSlide;

    /**
     * Creates an empty CellCollection.
     */
    public CellCollection() {
    }

    /**
     * Creates a new CellCollection with the given cells.
     *
     * @param cells the cells
     */
    public CellCollection(List<ICell> cells) {
        this.cells = cells != null ? new ArrayList<>(cells) : new ArrayList<>();
    }

    /**
     * Creates a new CellCollection with the given cells and parent slide.
     *
     * @param cells       the cells
     * @param parentSlide the parent slide
     */
    public CellCollection(List<ICell> cells, IBaseSlide parentSlide) {
        this.cells = cells != null ? new ArrayList<>(cells) : new ArrayList<>();
        this.parentSlide = parentSlide;
    }

    /**
     * Initializes this collection with the given cells, slide part, and parent slide.
     *
     * @param cells       the list of cells
     * @param slidePart   the slide part associated with this collection
     * @param parentSlide the parent slide
     * @return this collection, for method chaining
     */
    public CellCollection initInternal(List<ICell> cells, Object slidePart, IBaseSlide parentSlide) {
        this.cells = cells;
        this.slidePart = slidePart;
        this.parentSlide = parentSlide;
        return this;
    }

    @Override
    public ICell get(int index) {
        if (cells == null) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: 0");
        }
        return cells.get(index);
    }

    @Override
    public int size() {
        if (cells == null) {
            return 0;
        }
        return cells.size();
    }

    /**
     * Returns an unmodifiable view of the internal cell list.
     *
     * @return the cells
     */
    public List<ICell> asList() {
        if (cells == null) {
            return List.of();
        }
        return Collections.unmodifiableList(cells);
    }

    @Override
    public List<ICell> asICollection() {
        if (cells == null) {
            return List.of();
        }
        return List.copyOf(cells);
    }

    @Override
    public Iterable<ICell> asIEnumerable() {
        if (cells == null) {
            return List.of();
        }
        return Collections.unmodifiableList(cells);
    }

    @Override
    public IBaseSlide getSlide() {
        return parentSlide;
    }

    /**
     * Sets the parent slide for this collection.
     *
     * @param parentSlide the parent slide
     */
    public void setParentSlide(IBaseSlide parentSlide) {
        this.parentSlide = parentSlide;
    }

    /**
     * Returns the slide part associated with this collection.
     *
     * @return the slide part
     */
    public Object getSlidePart() {
        return slidePart;
    }

    @Override
    public IPresentation getPresentation() {
        if (parentSlide != null) {
            return parentSlide.getPresentation();
        }
        return null;
    }

    @Override
    public ISlideComponent asISlideComponent() {
        return this;
    }

    @Override
    public IPresentationComponent asIPresentationComponent() {
        return this;
    }

    @Override
    public Iterator<ICell> iterator() {
        if (cells == null) {
            return Collections.emptyIterator();
        }
        return Collections.unmodifiableList(cells).iterator();
    }
}
