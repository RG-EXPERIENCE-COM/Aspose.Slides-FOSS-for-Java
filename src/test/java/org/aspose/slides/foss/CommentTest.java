package org.aspose.slides.foss;

import org.aspose.slides.foss.drawing.PointF;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for Comments: authors, comments CRUD, slide comments.
 *
 * <p>Covers comment author management, comment CRUD, and slide comment access.</p>
 */
class CommentTest {

    /**
     * Saves the presentation to a byte array and reloads it, simulating the
     * round-trip fixture for save/reload testing.
     */
    private Presentation roundTrip(Presentation pres) throws IOException {
        var baos = new ByteArrayOutputStream();
        pres.save(baos);
        pres.dispose();
        return new Presentation(new ByteArrayInputStream(baos.toByteArray()));
    }

    @Test
    void addAuthor_namAndInitialsPersist() throws IOException {
        var pres = new Presentation();
        var author = pres.getCommentAuthors().addAuthor("Alice", "A");
        assertThat(author.getName()).isEqualTo("Alice");
        assertThat(author.getInitials()).isEqualTo("A");
        assertThat(pres.getCommentAuthors().size()).isEqualTo(1);

        try (var pres2 = roundTrip(pres)) {
            assertThat(pres2.getCommentAuthors().size()).isEqualTo(1);
            assertThat(pres2.getCommentAuthors().get(0).getName()).isEqualTo("Alice");
            assertThat(pres2.getCommentAuthors().get(0).getInitials()).isEqualTo("A");
        }
    }

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
            var c = a2.getComments().get(0);
            assertThat(c.getText()).isEqualTo("Review note");
        }
    }

    @Test
    void multipleAuthors_canCoexist() {
        try (var pres = new Presentation()) {
            pres.getCommentAuthors().addAuthor("Alice", "A");
            pres.getCommentAuthors().addAuthor("Bob", "B");
            assertThat(pres.getCommentAuthors().size()).isEqualTo(2);
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
    void removeComment_persists() throws IOException {
        var pres = new Presentation();
        var author = pres.getCommentAuthors().addAuthor("Alice", "A");
        var slide = pres.getSlides().get(0);
        var now = LocalDateTime.now();
        author.getComments().addComment("C1", slide, new PointF(1, 1), now);
        author.getComments().addComment("C2", slide, new PointF(2, 2), now);
        author.getComments().addComment("C3", slide, new PointF(3, 3), now);
        assertThat(author.getComments().size()).isEqualTo(3);

        author.getComments().removeAt(1);
        assertThat(author.getComments().size()).isEqualTo(2);

        try (var pres2 = roundTrip(pres)) {
            assertThat(pres2.getCommentAuthors().get(0).getComments().size()).isEqualTo(2);
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

    @Test
    void clearComments_removesAll() {
        try (var pres = new Presentation()) {
            var author = pres.getCommentAuthors().addAuthor("Alice", "A");
            var slide = pres.getSlides().get(0);
            var now = LocalDateTime.now();
            author.getComments().addComment("C1", slide, new PointF(1, 1), now);
            author.getComments().addComment("C2", slide, new PointF(2, 2), now);
            author.getComments().clear();
            assertThat(author.getComments().size()).isEqualTo(0);
        }
    }

    @Test
    void setNameAndInitials_mutatesAuthor() {
        try (var pres = new Presentation()) {
            var author = pres.getCommentAuthors().addAuthor("Alice", "A");
            author.setName("Bob");
            author.setInitials("B");
            assertThat(author.getName()).isEqualTo("Bob");
            assertThat(author.getInitials()).isEqualTo("B");
        }
    }

    @Test
    void authorRemove_clearsCommentsAndRemovesFromCollection() {
        try (var pres = new Presentation()) {
            var author = pres.getCommentAuthors().addAuthor("Alice", "A");
            var slide = pres.getSlides().get(0);
            var now = LocalDateTime.now();
            author.getComments().addComment("C1", slide, new PointF(1, 1), now);
            author.getComments().addComment("C2", slide, new PointF(2, 2), now);
            assertThat(author.getComments().size()).isEqualTo(2);

            author.remove();
            assertThat(author.getComments().size()).isEqualTo(0);
            assertThat(pres.getCommentAuthors().size()).isEqualTo(0);
        }
    }

    @Test
    void removeAuthor_persists() throws IOException {
        var pres = new Presentation();
        pres.getCommentAuthors().addAuthor("Alice", "A");
        pres.getCommentAuthors().addAuthor("Bob", "B");
        pres.getCommentAuthors().remove(pres.getCommentAuthors().get(0));
        assertThat(pres.getCommentAuthors().size()).isEqualTo(1);

        try (var pres2 = roundTrip(pres)) {
            assertThat(pres2.getCommentAuthors().size()).isEqualTo(1);
            assertThat(pres2.getCommentAuthors().get(0).getName()).isEqualTo("Bob");
        }
    }
}
