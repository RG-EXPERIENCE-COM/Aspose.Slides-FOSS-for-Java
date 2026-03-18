package org.aspose.slides.foss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a collection of layout slides.
 */
public class LayoutSlideCollection implements ILayoutSlideCollection {

    private final List<ILayoutSlide> layouts;

    /**
     * Creates an empty LayoutSlideCollection.
     */
    public LayoutSlideCollection() {
        this.layouts = new ArrayList<>();
    }

    /**
     * Creates a LayoutSlideCollection with the given layouts.
     *
     * @param layouts the initial layout slides
     */
    public LayoutSlideCollection(List<ILayoutSlide> layouts) {
        this.layouts = layouts != null ? new ArrayList<>(layouts) : new ArrayList<>();
    }

    @Override
    public ILayoutSlide get(int index) {
        return layouts.get(index);
    }

    @Override
    public int size() {
        return layouts.size();
    }

    @Override
    public List<ILayoutSlide> asICollection() {
        return Collections.unmodifiableList(layouts);
    }

    @Override
    public Iterable<ILayoutSlide> asIEnumerable() {
        return Collections.unmodifiableList(layouts);
    }

    @Override
    public Iterator<ILayoutSlide> iterator() {
        return Collections.unmodifiableList(layouts).iterator();
    }

    @Override
    public ILayoutSlide getByType(SlideLayoutType type) {
        for (var layout : layouts) {
            if (layout.getLayoutType() == type) {
                return layout;
            }
        }
        return null;
    }

    /**
     * Returns the internal list of layout slides.
     *
     * @return the list
     */
    List<ILayoutSlide> getInternalList() {
        return layouts;
    }
}
