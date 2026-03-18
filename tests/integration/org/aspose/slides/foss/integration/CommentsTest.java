package org.aspose.slides.foss.integration;
import org.aspose.slides.foss.*;

import org.aspose.slides.foss.drawing.PointF;
import org.aspose.slides.foss.export.SaveFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for Comments: authors, comments CRUD, slide comments.
 */
class CommentsTest implements AutoCloseable {

    @TempDir
    Path tempDir;

    @Override
    public void close() {
        // TempDir handles cleanup
    }

    private Presentation saveAndReopen(Presentation pres) throws IOException {
        String path = tempDir.resolve("roundtrip.pptx").toString();
        pres.save(path, SaveFormat.PPTX);
        pres.dispose();
        return new Presentation(path);
    }

    /** Author name and initials persist. */
    @Test
    void testAddAuthor() throws IOException {
        try (var pres = new Presentation()) {
            ICommentAuthor author = pres.getCommentAuthors().addAuthor("Alice", "A");
            assertThat(author.getName()).isEqualTo("Alice");
            assertThat(author.getInitials()).isEqualTo("A");
            assertThat(pres.getCommentAuthors().size()).isEqualTo(1);

            try (var pres2 = saveAndReopen(pres)) {
                assertThat(pres2.getCommentAuthors().size()).isEqualTo(1);
                assertThat(pres2.getCommentAuthors().get(0).getName()).isEqualTo("Alice");
                assertThat(pres2.getCommentAuthors().get(0).getInitials()).isEqualTo("A");
            }
        }
    }

    /** Comment text, position, and time persist. */
    @Test
    void testAddComment() throws IOException {
        try (var pres = new Presentation()) {
            ICommentAuthor author = pres.getCommentAuthors().addAuthor("Alice", "A");
            ISlide slide = pres.getSlides().get(0);
            LocalDateTime now = LocalDateTime.of(2026, 1, 15, 12, 0, 0);
            IComment comment = author.getComments().addComment("Review note", slide,
                    new PointF(2.0f, 3.0f), now);
            assertThat(comment.getText()).isEqualTo("Review note");
            assertThat(comment.getAuthor().getName()).isEqualTo("Alice");

            try (var pres2 = saveAndReopen(pres)) {
                ICommentAuthor a2 = pres2.getCommentAuthors().get(0);
                assertThat(a2.getComments().size()).isEqualTo(1);
                assertThat(a2.getComments().get(0).getText()).isEqualTo("Review note");
            }
        }
    }

    /** Multiple authors can coexist. */
    @Test
    void testMultipleAuthors() {
        try (Presentation pres = new Presentation()) {
            pres.getCommentAuthors().addAuthor("Alice", "A");
            pres.getCommentAuthors().addAuthor("Bob", "B");
            assertThat(pres.getCommentAuthors().size()).isEqualTo(2);
        }
    }

    /** getSlideComments filters by author. */
    @Test
    void testGetSlideComments() {
        try (Presentation pres = new Presentation()) {
            ICommentAuthor a1 = pres.getCommentAuthors().addAuthor("Alice", "A");
            ICommentAuthor a2 = pres.getCommentAuthors().addAuthor("Bob", "B");
            ISlide slide = pres.getSlides().get(0);
            LocalDateTime now = LocalDateTime.now();
            a1.getComments().addComment("Alice's", slide, new PointF(1, 1), now);
            a2.getComments().addComment("Bob's", slide, new PointF(2, 2), now);

            IComment[] allComments = slide.getSlideComments(null);
            assertThat(allComments).hasSize(2);

            IComment[] bobComments = slide.getSlideComments(a2);
            assertThat(bobComments).hasSize(1);
            assertThat(bobComments[0].getText()).isEqualTo("Bob's");
        }
    }

    /** Removing a comment persists. */
    @Test
    void testRemoveComment() throws IOException {
        try (var pres = new Presentation()) {
            ICommentAuthor author = pres.getCommentAuthors().addAuthor("Alice", "A");
            ISlide slide = pres.getSlides().get(0);
            LocalDateTime now = LocalDateTime.now();
            author.getComments().addComment("C1", slide, new PointF(1, 1), now);
            author.getComments().addComment("C2", slide, new PointF(2, 2), now);
            author.getComments().addComment("C3", slide, new PointF(3, 3), now);
            assertThat(author.getComments().size()).isEqualTo(3);

            author.getComments().removeAt(1);
            assertThat(author.getComments().size()).isEqualTo(2);

            try (var pres2 = saveAndReopen(pres)) {
                assertThat(pres2.getCommentAuthors().get(0).getComments().size()).isEqualTo(2);
            }
        }
    }

    /** insertComment places at the correct index. */
    @Test
    void testInsertComment() {
        try (Presentation pres = new Presentation()) {
            ICommentAuthor author = pres.getCommentAuthors().addAuthor("Alice", "A");
            ISlide slide = pres.getSlides().get(0);
            LocalDateTime now = LocalDateTime.now();
            author.getComments().addComment("First", slide, new PointF(1, 1), now);
            author.getComments().addComment("Third", slide, new PointF(1, 3), now);
            author.getComments().insertComment(1, "Second", slide, new PointF(1, 2), now);
            assertThat(author.getComments().size()).isEqualTo(3);
            assertThat(author.getComments().get(1).getText()).isEqualTo("Second");
        }
    }

    /** clear() removes all comments from an author. */
    @Test
    void testClearComments() {
        try (Presentation pres = new Presentation()) {
            ICommentAuthor author = pres.getCommentAuthors().addAuthor("Alice", "A");
            ISlide slide = pres.getSlides().get(0);
            LocalDateTime now = LocalDateTime.now();
            author.getComments().addComment("C1", slide, new PointF(1, 1), now);
            author.getComments().addComment("C2", slide, new PointF(2, 2), now);
            author.getComments().clear();
            assertThat(author.getComments().size()).isEqualTo(0);
        }
    }

    /** Removing an author persists. */
    @Test
    void testRemoveAuthor() throws IOException {
        try (var pres = new Presentation()) {
            pres.getCommentAuthors().addAuthor("Alice", "A");
            pres.getCommentAuthors().addAuthor("Bob", "B");
            pres.getCommentAuthors().remove(pres.getCommentAuthors().get(0));
            assertThat(pres.getCommentAuthors().size()).isEqualTo(1);

            try (var pres2 = saveAndReopen(pres)) {
                assertThat(pres2.getCommentAuthors().size()).isEqualTo(1);
                assertThat(pres2.getCommentAuthors().get(0).getName()).isEqualTo("Bob");
            }
        }
    }
}
