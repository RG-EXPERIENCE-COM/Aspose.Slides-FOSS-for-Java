package org.aspose.slides.foss;

import org.aspose.slides.foss.internal.pptx.NotesSlidePart;

import java.util.Objects;

/**
 * A text frame backed by the notes body placeholder in a {@link NotesSlidePart}.
 */
final class NotesTextFrame implements ITextFrame {

    private final NotesSlidePart notesPart;

    /**
     * Creates a NotesTextFrame backed by the given notes slide part.
     *
     * @param notesPart the notes slide part
     */
    NotesTextFrame(NotesSlidePart notesPart) {
        this.notesPart = Objects.requireNonNull(notesPart);
    }

    @Override
    public String getText() {
        return notesPart.getNotesText();
    }

    @Override
    public void setText(String text) {
        notesPart.setNotesText(text);
    }

    @Override
    public IParagraphCollection getParagraphs() {
        return null;
    }

    @Override
    public ITextFrameFormat getTextFrameFormat() {
        return null;
    }

    @Override
    public IShape getParentShape() {
        return null;
    }

    @Override
    public ICell getParentCell() {
        return null;
    }

    @Override
    public ISlideComponent asISlideComponent() {
        return this;
    }

    @Override
    public IBaseSlide getSlide() {
        return null;
    }

    @Override
    public IPresentation getPresentation() {
        return null;
    }

    @Override
    public IPresentationComponent asIPresentationComponent() {
        return this;
    }
}
