package org.aspose.slides.foss;

/**
 * Represents a heading pair indicating a grouping of document parts.
 */
public final class HeadingPair implements IHeadingPair {

    private String name;
    private int count;

    /**
     * Initializes internal state. Called after construction.
     *
     * @param name  the heading name
     * @param count the part count
     */
    public void initInternal(String name, int count) {
        this.name = name;
        this.count = count;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "HeadingPair(name=%s, count=%d)".formatted(name, count);
    }
}
