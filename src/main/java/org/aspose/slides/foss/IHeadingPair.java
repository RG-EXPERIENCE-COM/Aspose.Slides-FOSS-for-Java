package org.aspose.slides.foss;

/**
 * Represents a heading pair that indicates a grouping of document parts
 * and the number of parts in each group.
 */
public interface IHeadingPair {

    /**
     * Returns the name of the heading pair.
     *
     * @return the heading pair name
     */
    String getName();

    /**
     * Returns the count of parts in this group.
     *
     * @return the part count
     */
    int getCount();
}
