package org.aspose.slides.foss;

import java.util.List;

/**
 * Represents a collection of all layout slides in presentation.
 *
 * <p>Extends {@link LayoutSlideCollection} with methods for adding/cloning
 * layout slides in context of uniting of the individual collections of
 * master's layout slides.</p>
 */
public final class GlobalLayoutSlideCollection extends LayoutSlideCollection implements IGlobalLayoutSlideCollection {

    /**
     * Creates an empty GlobalLayoutSlideCollection.
     */
    public GlobalLayoutSlideCollection() {
        super();
    }

    /**
     * Creates a GlobalLayoutSlideCollection with the given layouts.
     *
     * @param layouts the initial layout slides
     */
    public GlobalLayoutSlideCollection(List<ILayoutSlide> layouts) {
        super(layouts);
    }

    /**
     * Internal initialization with a list of all layout slides.
     *
     * <p>Replaces the current contents of this collection with the given layouts.</p>
     *
     * @param layouts the layout slides to set
     */
    public void initInternal(List<ILayoutSlide> layouts) {
        List<ILayoutSlide> internal = getInternalList();
        internal.clear();
        if (layouts != null) {
            internal.addAll(layouts);
        }
    }

    /**
     * Adds a layout slide to the collection.
     *
     * @param layout the layout slide to add
     */
    public void add(ILayoutSlide layout) {
        getInternalList().add(layout);
    }

    /**
     * Returns this collection as an {@link ILayoutSlideCollection}.
     *
     * @return this collection viewed as an {@code ILayoutSlideCollection}
     */
    @Override
    public ILayoutSlideCollection asILayoutSlideCollection() {
        return this;
    }
}
