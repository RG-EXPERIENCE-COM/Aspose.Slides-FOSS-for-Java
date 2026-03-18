package org.aspose.slides.foss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a collection of all layout slides of the defined master slide.
 * Extends {@link LayoutSlideCollection} with methods for adding/inserting/removing/cloning/reordering
 * layout slides in context of the individual collections of master's layout slides.
 */
public final class MasterLayoutSlideCollection extends LayoutSlideCollection implements IMasterLayoutSlideCollection {

    private List<ILayoutSlide> layouts;

    /**
     * Creates an empty MasterLayoutSlideCollection.
     */
    public MasterLayoutSlideCollection() {
        super();
        this.layouts = new ArrayList<>();
    }

    /**
     * Creates a MasterLayoutSlideCollection with the given layouts.
     *
     * @param layouts the initial layout slides
     */
    public MasterLayoutSlideCollection(List<ILayoutSlide> layouts) {
        super();
        this.layouts = layouts != null ? new ArrayList<>(layouts) : new ArrayList<>();
    }

    /**
     * Internal initialization with a list of layout slides.
     *
     * @param layouts the layout slides to initialize with
     */
    void initInternal(List<ILayoutSlide> layouts) {
        this.layouts = layouts != null ? layouts : new ArrayList<>();
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
    @Override
    List<ILayoutSlide> getInternalList() {
        return layouts;
    }
}
