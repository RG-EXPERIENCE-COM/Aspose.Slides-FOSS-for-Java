package org.aspose.slides.foss;

import org.aspose.slides.foss.internal.opc.OpcPackage;
import org.aspose.slides.foss.internal.pptx.NotesSlidePart;

import java.util.Objects;

/**
 * Represents a notes slide in a presentation.
 */
public final class NotesSlide extends BaseSlide implements INotesSlide {

    private IPresentation presentationRef;
    private OpcPackage opcPackage;
    private String partName;
    private NotesSlidePart notesPart;
    private ISlide parentSlide;
    private NotesTextFrame notesTextFrame;
    private INotesSlideHeaderFooterManager headerFooterManagerCache;

    /**
     * Creates an uninitialized NotesSlide.
     *
     * <p>Call {@link #initInternal} to complete initialization.</p>
     */
    public NotesSlide() {
        super();
    }

    /**
     * Creates a NotesSlide for the given parent slide, backed by the given notes part.
     *
     * @param parentSlide the parent slide
     * @param notesPart   the notes slide part
     */
    NotesSlide(Slide parentSlide, NotesSlidePart notesPart) {
        super();
        this.parentSlide = Objects.requireNonNull(parentSlide);
        this.notesPart = Objects.requireNonNull(notesPart);
        this.notesTextFrame = new NotesTextFrame(notesPart);
        this.headerFooterManagerCache = new NotesSlideHeaderFooterManager(notesPart);
    }

    /**
     * Internal initialization for a notes slide.
     *
     * @param presentation the parent {@link IPresentation} object
     * @param opcPackage   the OPC package
     * @param partName     the part name of this notes slide within the package
     * @param notesPart    the parsed {@link NotesSlidePart}
     * @param parentSlide  the {@link ISlide} that owns this notes slide
     */
    public void initInternal(
            IPresentation presentation,
            OpcPackage opcPackage,
            String partName,
            NotesSlidePart notesPart,
            ISlide parentSlide) {
        this.presentationRef = presentation;
        this.opcPackage = opcPackage;
        this.partName = partName;
        this.notesPart = notesPart;
        this.parentSlide = parentSlide;
        this.headerFooterManagerCache = null;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Returns the {@link NotesSlidePart} for BaseSlide shape access.</p>
     */
    @Override
    protected Object getSlidePart() {
        return notesPart;
    }

    @Override
    public ITextFrame getNotesTextFrame() {
        if (notesTextFrame == null && notesPart != null) {
            notesTextFrame = new NotesTextFrame(notesPart);
        }
        return notesTextFrame;
    }

    @Override
    public INotesSlideHeaderFooterManager getHeaderFooterManager() {
        if (headerFooterManagerCache == null && notesPart != null) {
            headerFooterManagerCache = new NotesSlideHeaderFooterManager(notesPart);
        }
        return headerFooterManagerCache;
    }

    @Override
    public ISlide getParentSlide() {
        return parentSlide;
    }

    @Override
    public IBaseSlide asIBaseSlide() {
        return this;
    }

    @Override
    public IPresentation getPresentation() {
        if (presentationRef != null) {
            return presentationRef;
        }
        if (parentSlide != null) {
            return parentSlide.getPresentation();
        }
        return null;
    }

    /**
     * Returns the underlying notes slide part (for internal use).
     *
     * @return the notes slide part
     */
    NotesSlidePart getNotesPart() {
        return notesPart;
    }
}
