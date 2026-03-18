package org.aspose.slides.foss.internal.pptx;

/**
 * Reference to a slide in the presentation.
 *
 * <p>Holds the unique slide ID and the relationship ID that links
 * the slide entry in {@code presentation.xml} to the actual slide part.</p>
 */
public final class SlideReference {

    private final int slideId;
    private final String relId;

    /**
     * Creates a slide reference.
     *
     * @param slideId the unique slide ID ({@code id} attribute)
     * @param relId   the relationship ID ({@code r:id} attribute)
     */
    public SlideReference(int slideId, String relId) {
        this.slideId = slideId;
        this.relId = relId;
    }

    /** Returns the unique slide ID. */
    public int getSlideId() {
        return slideId;
    }

    /** Returns the relationship ID. */
    public String getRelId() {
        return relId;
    }
}
