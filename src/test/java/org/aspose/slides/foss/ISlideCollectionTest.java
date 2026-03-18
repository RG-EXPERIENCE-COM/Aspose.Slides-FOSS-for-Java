package org.aspose.slides.foss;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ISlideCollection} contract members.
 *
 * <p>Covers slide CRUD operations, cloning, indexing, and related shape/comment integration.</p>
 */
class ISlideCollectionTest {

    private Presentation roundTrip(Presentation pres) throws IOException {
        var baos = new ByteArrayOutputStream();
        pres.save(baos);
        pres.dispose();
        return new Presentation(new ByteArrayInputStream(baos.toByteArray()));
    }

    // --- from test_slides.py: test_add_empty_slide ---

    @Test
    void addEmptySlide_increasesSlideCount() {
        try (var pres = new Presentation()) {
            var layout = pres.getSlides().get(0).getLayoutSlide();
            pres.getSlides().addEmptySlide(layout);
            assertThat(pres.getSlides().size()).isEqualTo(2);
        }
    }

    // --- from test_slides.py: test_clone_slide ---

    @Test
    void addClone_duplicatesSlideWithShapes() {
        try (var pres = new Presentation()) {
            var slide = pres.getSlides().get(0);
            slide.getShapes().addAutoShape(ShapeType.RECTANGLE, 50, 50, 200, 100);
            pres.getSlides().addClone(slide);
            assertThat(pres.getSlides().size()).isEqualTo(2);
            assertThat(pres.getSlides().get(1).getShapes().size()).isGreaterThanOrEqualTo(1);
        }
    }

    // --- from test_slides.py: test_index_of ---

    @Test
    void indexOf_returnsCorrectPosition() {
        try (var pres = new Presentation()) {
            var layout = pres.getSlides().get(0).getLayoutSlide();
            pres.getSlides().addEmptySlide(layout);
            assertThat(pres.getSlides().indexOf(pres.getSlides().get(0))).isEqualTo(0);
            assertThat(pres.getSlides().indexOf(pres.getSlides().get(1))).isEqualTo(1);
        }
    }

    // --- from test_presentation.py: test_slide_count_after_add ---

    @Test
    void slideCountAfterAdd_isTwo() {
        try (var pres = new Presentation()) {
            var layout = pres.getSlides().get(0).getLayoutSlide();
            pres.getSlides().addEmptySlide(layout);
            assertThat(pres.getSlides().size()).isEqualTo(2);
        }
    }

    // --- from test_comments.py: test_remove_comment (exercises removeAt on a collection) ---

    @Test
    void removeComment_persists() throws IOException {
        var pres = new Presentation();
        var author = pres.getCommentAuthors().addAuthor("Alice", "A");
        var slide = pres.getSlides().get(0);
        var now = java.time.LocalDateTime.now();
        author.getComments().addComment("C1", slide, new org.aspose.slides.foss.drawing.PointF(1, 1), now);
        author.getComments().addComment("C2", slide, new org.aspose.slides.foss.drawing.PointF(2, 2), now);
        author.getComments().addComment("C3", slide, new org.aspose.slides.foss.drawing.PointF(3, 3), now);
        assertThat(author.getComments().size()).isEqualTo(3);

        author.getComments().removeAt(1);
        assertThat(author.getComments().size()).isEqualTo(2);

        try (var pres2 = roundTrip(pres)) {
            assertThat(pres2.getCommentAuthors().get(0).getComments().size()).isEqualTo(2);
        }
    }

    // --- from test_shapes.py: test_remove_at ---

    @Test
    void shapeRemoveAt_removesByIndex() {
        try (var pres = new Presentation()) {
            var slide = pres.getSlides().get(0);
            slide.getShapes().clear();
            slide.getShapes().addAutoShape(ShapeType.RECTANGLE, 50, 50, 200, 100);
            slide.getShapes().addAutoShape(ShapeType.ELLIPSE, 300, 50, 150, 150);
            slide.getShapes().removeAt(0);
            assertThat(slide.getShapes().size()).isEqualTo(1);
        }
    }
}
