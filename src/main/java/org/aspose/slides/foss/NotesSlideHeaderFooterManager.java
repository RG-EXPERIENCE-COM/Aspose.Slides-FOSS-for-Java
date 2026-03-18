package org.aspose.slides.foss;

import org.aspose.slides.foss.internal.pptx.NotesSlidePart;

import java.util.Objects;

/**
 * Represents manager which holds behavior of the notes slide placeholders,
 * including header placeholder.
 */
public final class NotesSlideHeaderFooterManager
        extends BaseHandoutNotesSlideHeaderFooterManager
        implements INotesSlideHeaderFooterManager {

    private NotesSlidePart notesPart;

    /**
     * Creates a NotesSlideHeaderFooterManager backed by the given notes slide part.
     *
     * @param notesPart the NotesSlidePart providing placeholder access
     */
    NotesSlideHeaderFooterManager(NotesSlidePart notesPart) {
        this.notesPart = Objects.requireNonNull(notesPart);
    }

    /**
     * Creates an uninitialized NotesSlideHeaderFooterManager.
     * Must call {@link #initInternal(NotesSlidePart)} before use.
     */
    NotesSlideHeaderFooterManager() {
    }

    /**
     * Internal initialization.
     *
     * @param notesPart the NotesSlidePart providing placeholder access
     */
    public void initInternal(NotesSlidePart notesPart) {
        this.notesPart = Objects.requireNonNull(notesPart);
    }

    // --- Visibility properties ---

    @Override
    public boolean isFooterVisible() {
        return notesPart.hasPlaceholder("ftr");
    }

    @Override
    public boolean isSlideNumberVisible() {
        return notesPart.hasPlaceholder("sldNum");
    }

    @Override
    public boolean isDateTimeVisible() {
        return notesPart.hasPlaceholder("dt");
    }

    @Override
    public boolean isHeaderVisible() {
        return notesPart.hasPlaceholder("hdr");
    }

    // --- Interface cast methods ---

    @Override
    public IBaseHandoutNotesSlideHeaderFooterManager asIBaseHandoutNotesSlideHeaderFooterManag() {
        return this;
    }

    @Override
    public IBaseSlideHeaderFooterManager asIBaseSlideHeaderFooterManager() {
        return this;
    }

    @Override
    public IBaseHeaderFooterManager asIBaseHeaderFooterManager() {
        return this;
    }

    // --- Visibility setters ---

    @Override
    public void setFooterVisibility(boolean isVisible) {
        if (isVisible) {
            notesPart.addPlaceholder("ftr");
        } else {
            notesPart.removePlaceholder("ftr");
        }
    }

    @Override
    public void setSlideNumberVisibility(boolean isVisible) {
        if (isVisible) {
            notesPart.addPlaceholder("sldNum");
        } else {
            notesPart.removePlaceholder("sldNum");
        }
    }

    @Override
    public void setDateTimeVisibility(boolean isVisible) {
        if (isVisible) {
            notesPart.addPlaceholder("dt");
        } else {
            notesPart.removePlaceholder("dt");
        }
    }

    @Override
    public void setHeaderVisibility(boolean isVisible) {
        if (isVisible) {
            notesPart.addPlaceholder("hdr");
        } else {
            notesPart.removePlaceholder("hdr");
        }
    }

    // --- Text setters ---

    @Override
    public void setFooterText(String text) {
        notesPart.setPlaceholderText("ftr", text);
    }

    @Override
    public void setDateTimeText(String text) {
        notesPart.setPlaceholderText("dt", text);
    }

    @Override
    public void setHeaderText(String text) {
        notesPart.setPlaceholderText("hdr", text);
    }
}
