package org.aspose.slides.foss.internal.pptx;

/**
 * Reference to a master slide in the presentation.
 *
 * <p>Holds the unique master slide ID and the relationship ID that links
 * the master entry in {@code presentation.xml} to the actual master slide part.</p>
 */
public final class MasterReference {

    private final long masterId;
    private final String relId;

    /**
     * Creates a master slide reference.
     *
     * @param masterId the unique master slide ID ({@code id} attribute)
     * @param relId    the relationship ID ({@code r:id} attribute)
     */
    public MasterReference(long masterId, String relId) {
        this.masterId = masterId;
        this.relId = relId;
    }

    /** Returns the unique master slide ID. */
    public long getMasterId() {
        return masterId;
    }

    /** Returns the relationship ID. */
    public String getRelId() {
        return relId;
    }
}
