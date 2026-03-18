package org.aspose.slides.foss;

import org.aspose.slides.foss.drawing.SizeF;
import org.aspose.slides.foss.internal.pptx.PresentationPart;

/**
 * Represents the size of a notes slide.
 *
 * <p>When backed by a {@link PresentationPart} (via {@link #initInternal}),
 * the size is read from and written to the underlying XML as EMU values.
 * Otherwise a local {@link SizeF} is used.</p>
 */
public final class NotesSize implements INotesSize {

    /** 1 point = 12 700 EMUs. */
    private static final float EMU_PER_POINT = 12_700f;

    private PresentationPart presentationPart;
    private SizeF size;

    /**
     * Creates a NotesSize with default dimensions.
     */
    public NotesSize() {
        this.size = new SizeF(0f, 0f);
    }

    /**
     * Creates a NotesSize with the given dimensions.
     *
     * @param size the notes slide size
     */
    public NotesSize(SizeF size) {
        this.size = size != null ? size : new SizeF(0f, 0f);
    }

    /**
     * Internal initialization that binds this instance to a {@link PresentationPart}.
     *
     * <p>Once bound, {@link #getSize()} and {@link #setSize(SizeF)} delegate to
     * the {@code notesSz} element in the presentation XML, converting between
     * points and EMUs automatically.</p>
     *
     * @param presentationPart the presentation part providing access to {@code notesSz} in XML
     */
    public void initInternal(PresentationPart presentationPart) {
        this.presentationPart = presentationPart;
    }

    @Override
    public SizeF getSize() {
        if (presentationPart != null) {
            int[] emu = presentationPart.getNotesSize();
            return new SizeF(emu[0] / EMU_PER_POINT, emu[1] / EMU_PER_POINT);
        }
        return size;
    }

    /**
     * Sets the size of the notes slide in points.
     *
     * @param size the new size
     */
    @Override
    public void setSize(SizeF size) {
        SizeF safe = size != null ? size : new SizeF(0f, 0f);
        if (presentationPart != null) {
            presentationPart.setNotesSize(
                    Math.round(safe.getWidth() * EMU_PER_POINT),
                    Math.round(safe.getHeight() * EMU_PER_POINT));
        } else {
            this.size = safe;
        }
    }
}
