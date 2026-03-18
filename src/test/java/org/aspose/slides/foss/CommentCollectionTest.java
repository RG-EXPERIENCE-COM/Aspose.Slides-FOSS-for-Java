package org.aspose.slides.foss;

import org.aspose.slides.foss.drawing.PointF;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link CommentCollection} methods: toArray, remove, findCommentByIdx,
 * asICollection, asIEnumerable.
 */
class CommentCollectionTest {

    @Test
    void toArray_returnsAllComments() {
        try (var pres = new Presentation()) {
            var author = pres.getCommentAuthors().addAuthor("Alice", "A");
            var slide = pres.getSlides().get(0);
            var now = LocalDateTime.now();
            author.getComments().addComment("C1", slide, new PointF(1, 1), now);
            author.getComments().addComment("C2", slide, new PointF(2, 2), now);

            var arr = author.getComments().toArray();
            assertThat(arr).hasSize(2);
            assertThat(arr[0].getText()).isEqualTo("C1");
            assertThat(arr[1].getText()).isEqualTo("C2");
        }
    }

    @Test
    void toArray_withRange_returnsSubset() {
        try (var pres = new Presentation()) {
            var author = pres.getCommentAuthors().addAuthor("Alice", "A");
            var slide = pres.getSlides().get(0);
            var now = LocalDateTime.now();
            author.getComments().addComment("C1", slide, new PointF(1, 1), now);
            author.getComments().addComment("C2", slide, new PointF(2, 2), now);
            author.getComments().addComment("C3", slide, new PointF(3, 3), now);

            var arr = author.getComments().toArray(1, 2);
            assertThat(arr).hasSize(2);
            assertThat(arr[0].getText()).isEqualTo("C2");
            assertThat(arr[1].getText()).isEqualTo("C3");
        }
    }

    @Test
    void remove_removesComment() {
        try (var pres = new Presentation()) {
            var author = pres.getCommentAuthors().addAuthor("Alice", "A");
            var slide = pres.getSlides().get(0);
            var now = LocalDateTime.now();
            var c1 = author.getComments().addComment("C1", slide, new PointF(1, 1), now);
            author.getComments().addComment("C2", slide, new PointF(2, 2), now);
            assertThat(author.getComments().size()).isEqualTo(2);

            author.getComments().remove(c1);
            assertThat(author.getComments().size()).isEqualTo(1);
            assertThat(author.getComments().get(0).getText()).isEqualTo("C2");
        }
    }

    @Test
    void findCommentByIdx_returnsMatchingComment() {
        try (var pres = new Presentation()) {
            var author = pres.getCommentAuthors().addAuthor("Alice", "A");
            var slide = pres.getSlides().get(0);
            var now = LocalDateTime.now();
            author.getComments().addComment("C1", slide, new PointF(1, 1), now);
            author.getComments().addComment("C2", slide, new PointF(2, 2), now);

            // idx values are 1-based and auto-incremented
            var found = author.getComments().findCommentByIdx(2);
            assertThat(found).isPresent();
            assertThat(found.get().getText()).isEqualTo("C2");
        }
    }

    @Test
    void findCommentByIdx_returnsEmptyWhenNotFound() {
        try (var pres = new Presentation()) {
            var author = pres.getCommentAuthors().addAuthor("Alice", "A");
            var slide = pres.getSlides().get(0);
            var now = LocalDateTime.now();
            author.getComments().addComment("C1", slide, new PointF(1, 1), now);

            var found = author.getComments().findCommentByIdx(99);
            assertThat(found).isEmpty();
        }
    }

    @Test
    void asICollection_returnsList() {
        try (var pres = new Presentation()) {
            var author = pres.getCommentAuthors().addAuthor("Alice", "A");
            var slide = pres.getSlides().get(0);
            var now = LocalDateTime.now();
            author.getComments().addComment("C1", slide, new PointF(1, 1), now);
            author.getComments().addComment("C2", slide, new PointF(2, 2), now);

            var list = author.getComments().asICollection();
            assertThat(list).hasSize(2);
            assertThat(list.get(0).getText()).isEqualTo("C1");
        }
    }

    @Test
    void asIEnumerable_isIterable() {
        try (var pres = new Presentation()) {
            var author = pres.getCommentAuthors().addAuthor("Alice", "A");
            var slide = pres.getSlides().get(0);
            var now = LocalDateTime.now();
            author.getComments().addComment("C1", slide, new PointF(1, 1), now);
            author.getComments().addComment("C2", slide, new PointF(2, 2), now);

            var iterable = author.getComments().asIEnumerable();
            int count = 0;
            for (var comment : iterable) {
                count++;
            }
            assertThat(count).isEqualTo(2);
        }
    }
}
