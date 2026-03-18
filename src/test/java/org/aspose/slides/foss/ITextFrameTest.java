package org.aspose.slides.foss;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

import org.aspose.slides.foss.drawing.PointF;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ITextFrame} contract exercised through comments, notes, and table scenarios.
 *
 * <p>Covers text frame behavior in comments, notes, and table scenarios.</p>
 */
class ITextFrameTest {

    private Presentation roundTrip(Presentation pres) throws IOException {
        var baos = new ByteArrayOutputStream();
        pres.save(baos);
        pres.dispose();
        return new Presentation(new ByteArrayInputStream(baos.toByteArray()));
    }

    // --- from test_comments.py ---

    @Test
    void addComment_textPositionAndTimePersist() throws IOException {
        var pres = new Presentation();
        var author = pres.getCommentAuthors().addAuthor("Alice", "A");
        var slide = pres.getSlides().get(0);
        var now = LocalDateTime.of(2026, 1, 15, 12, 0, 0);
        var comment = author.getComments().addComment("Review note", slide, new PointF(2.0f, 3.0f), now);
        assertThat(comment.getText()).isEqualTo("Review note");
        assertThat(comment.getAuthor().getName()).isEqualTo("Alice");

        try (var pres2 = roundTrip(pres)) {
            var a2 = pres2.getCommentAuthors().get(0);
            assertThat(a2.getComments().size()).isEqualTo(1);
            assertThat(a2.getComments().get(0).getText()).isEqualTo("Review note");
        }
    }

    @Test
    void getSlideComments_filtersByAuthor() {
        try (var pres = new Presentation()) {
            var a1 = pres.getCommentAuthors().addAuthor("Alice", "A");
            var a2 = pres.getCommentAuthors().addAuthor("Bob", "B");
            var slide = pres.getSlides().get(0);
            var now = LocalDateTime.now();
            a1.getComments().addComment("Alice's", slide, new PointF(1, 1), now);
            a2.getComments().addComment("Bob's", slide, new PointF(2, 2), now);

            var allComments = slide.getSlideComments(null);
            assertThat(allComments).hasSize(2);

            var bobComments = slide.getSlideComments(a2);
            assertThat(bobComments).hasSize(1);
            assertThat(bobComments[0].getText()).isEqualTo("Bob's");
        }
    }

    @Test
    void insertComment_placesAtCorrectIndex() {
        try (var pres = new Presentation()) {
            var author = pres.getCommentAuthors().addAuthor("Alice", "A");
            var slide = pres.getSlides().get(0);
            var now = LocalDateTime.now();
            author.getComments().addComment("First", slide, new PointF(1, 1), now);
            author.getComments().addComment("Third", slide, new PointF(1, 3), now);
            author.getComments().insertComment(1, "Second", slide, new PointF(1, 2), now);
            assertThat(author.getComments().size()).isEqualTo(3);
            assertThat(author.getComments().get(1).getText()).isEqualTo("Second");
        }
    }

    // --- from test_notes_slide.py ---

    @Test
    void notesTextFrame_textPersistsAfterRoundTrip() throws IOException {
        var pres = new Presentation();
        var slide = (Slide) pres.getSlides().get(0);
        var notes = slide.getNotesSlideManager().addNotesSlide();
        notes.getNotesTextFrame().setText("Speaker notes");

        try (var pres2 = roundTrip(pres)) {
            var ns2 = pres2.getSlides().get(0).getNotesSlideManager().getNotesSlide();
            assertThat(ns2).isNotNull();
            assertThat(ns2.getNotesTextFrame().getText()).isEqualTo("Speaker notes");
        }
    }

    @Test
    void notesHeaderFooter_persistsAcrossRoundTrip() throws IOException {
        var pres = new Presentation();
        var notes = pres.getSlides().get(0).getNotesSlideManager().addNotesSlide();
        notes.getNotesTextFrame().setText("Notes");
        var hfm = notes.getHeaderFooterManager();
        hfm.setFooterVisibility(true);
        hfm.setFooterText("Confidential");
        hfm.setSlideNumberVisibility(true);

        assertThat(hfm.isFooterVisible()).isTrue();
        assertThat(hfm.isSlideNumberVisible()).isTrue();

        try (var pres2 = roundTrip(pres)) {
            var ns2 = pres2.getSlides().get(0).getNotesSlideManager().getNotesSlide();
            var hfm2 = ns2.getHeaderFooterManager();
            assertThat(hfm2.isFooterVisible()).isTrue();
            assertThat(hfm2.isSlideNumberVisible()).isTrue();
        }
    }

    // --- from test_table.py ---

    private ISlide blankSlide(Presentation pres) {
        ISlide slide = pres.getSlides().get(0);
        slide.getShapes().clear();
        return slide;
    }

    private ITable findTable(ISlide slide) {
        for (int i = 0; i < slide.getShapes().size(); i++) {
            IShape shape = slide.getShapes().get(i);
            if (shape instanceof Table) {
                return (ITable) shape;
            }
        }
        return null;
    }

    @Test
    void cellBorders_persistAfterReload() throws IOException {
        var pres = new Presentation();
        ISlide slide = blankSlide(pres);
        ITable table = slide.getShapes().addTable(50, 50,
                new double[]{150}, new double[]{50});
        ICell cell = table.getRows().get(0).get(0);
        cell.getTextFrame().setText("Bordered");
        ICellFormat fmt = cell.getCellFormat();
        for (ILineFormat border : new ILineFormat[]{
                fmt.getBorderTop(), fmt.getBorderBottom(),
                fmt.getBorderLeft(), fmt.getBorderRight()}) {
            border.getFillFormat().setFillType(FillType.SOLID);
            border.setWidth(3);
        }

        try (Presentation pres2 = roundTrip(pres)) {
            ITable t2 = findTable(pres2.getSlides().get(0));
            assertThat(t2).isNotNull();
            ICellFormat fmt2 = t2.getRows().get(0).get(0).getCellFormat();
            assertThat(fmt2.getBorderTop().getWidth()).isEqualTo(3);
        }
    }
}
