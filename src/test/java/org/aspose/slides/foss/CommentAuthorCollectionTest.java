package org.aspose.slides.foss;

import org.aspose.slides.foss.drawing.PointF;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link CommentAuthorCollection} methods.
 *
 * <p>Covers toArray, findByName, findByNameAndInitials, removeAt, clear,
 * asICollection, and asIEnumerable.</p>
 */
class CommentAuthorCollectionTest {

    @Test
    void toArray_returnsAllAuthors() {
        try (var pres = new Presentation()) {
            pres.getCommentAuthors().addAuthor("Alice", "A");
            pres.getCommentAuthors().addAuthor("Bob", "B");

            ICommentAuthor[] arr = pres.getCommentAuthors().toArray();
            assertThat(arr).hasSize(2);
            assertThat(arr[0].getName()).isEqualTo("Alice");
            assertThat(arr[1].getName()).isEqualTo("Bob");
        }
    }

    @Test
    void toArray_emptyCollection() {
        try (var pres = new Presentation()) {
            assertThat(pres.getCommentAuthors().toArray()).isEmpty();
        }
    }

    @Test
    void findByName_matchesCorrectAuthors() {
        try (var pres = new Presentation()) {
            pres.getCommentAuthors().addAuthor("Alice", "A");
            pres.getCommentAuthors().addAuthor("Bob", "B");
            pres.getCommentAuthors().addAuthor("Alice", "AW");

            List<ICommentAuthor> found = pres.getCommentAuthors().findByName("Alice");
            assertThat(found).hasSize(2);
            assertThat(found).allMatch(a -> a.getName().equals("Alice"));
        }
    }

    @Test
    void findByName_noMatch_returnsEmpty() {
        try (var pres = new Presentation()) {
            pres.getCommentAuthors().addAuthor("Alice", "A");

            assertThat(pres.getCommentAuthors().findByName("Nobody")).isEmpty();
        }
    }

    @Test
    void findByNameAndInitials_matchesExact() {
        try (var pres = new Presentation()) {
            pres.getCommentAuthors().addAuthor("Alice", "A");
            pres.getCommentAuthors().addAuthor("Alice", "AW");
            pres.getCommentAuthors().addAuthor("Bob", "B");

            List<ICommentAuthor> found = pres.getCommentAuthors().findByNameAndInitials("Alice", "AW");
            assertThat(found).hasSize(1);
            assertThat(found.get(0).getInitials()).isEqualTo("AW");
        }
    }

    @Test
    void findByNameAndInitials_noMatch_returnsEmpty() {
        try (var pres = new Presentation()) {
            pres.getCommentAuthors().addAuthor("Alice", "A");

            assertThat(pres.getCommentAuthors().findByNameAndInitials("Alice", "X")).isEmpty();
        }
    }

    @Test
    void removeAt_removesAuthorAndClearsComments() {
        try (var pres = new Presentation()) {
            var alice = pres.getCommentAuthors().addAuthor("Alice", "A");
            pres.getCommentAuthors().addAuthor("Bob", "B");
            var slide = pres.getSlides().get(0);
            var now = LocalDateTime.now();
            alice.getComments().addComment("C1", slide, new PointF(1, 1), now);

            pres.getCommentAuthors().removeAt(0);
            assertThat(pres.getCommentAuthors().size()).isEqualTo(1);
            assertThat(pres.getCommentAuthors().get(0).getName()).isEqualTo("Bob");
        }
    }

    @Test
    void removeAt_outOfBounds_doesNothing() {
        try (var pres = new Presentation()) {
            pres.getCommentAuthors().addAuthor("Alice", "A");

            pres.getCommentAuthors().removeAt(-1);
            pres.getCommentAuthors().removeAt(5);
            assertThat(pres.getCommentAuthors().size()).isEqualTo(1);
        }
    }

    @Test
    void clear_removesAllAuthorsAndComments() {
        try (var pres = new Presentation()) {
            var alice = pres.getCommentAuthors().addAuthor("Alice", "A");
            var bob = pres.getCommentAuthors().addAuthor("Bob", "B");
            var slide = pres.getSlides().get(0);
            var now = LocalDateTime.now();
            alice.getComments().addComment("C1", slide, new PointF(1, 1), now);
            bob.getComments().addComment("C2", slide, new PointF(2, 2), now);

            pres.getCommentAuthors().clear();
            assertThat(pres.getCommentAuthors().size()).isEqualTo(0);
        }
    }

    @Test
    void asICollection_returnsListOfAuthors() {
        try (var pres = new Presentation()) {
            pres.getCommentAuthors().addAuthor("Alice", "A");
            pres.getCommentAuthors().addAuthor("Bob", "B");

            List<ICommentAuthor> list = pres.getCommentAuthors().asICollection();
            assertThat(list).hasSize(2);
            assertThat(list.get(0).getName()).isEqualTo("Alice");
            assertThat(list.get(1).getName()).isEqualTo("Bob");
        }
    }

    @Test
    void asIEnumerable_isIterable() {
        try (var pres = new Presentation()) {
            pres.getCommentAuthors().addAuthor("Alice", "A");
            pres.getCommentAuthors().addAuthor("Bob", "B");

            int count = 0;
            for (var author : pres.getCommentAuthors().asIEnumerable()) {
                assertThat(author.getName()).isNotNull();
                count++;
            }
            assertThat(count).isEqualTo(2);
        }
    }
}
