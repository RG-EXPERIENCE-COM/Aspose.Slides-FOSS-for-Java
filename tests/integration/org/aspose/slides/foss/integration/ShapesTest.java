package org.aspose.slides.foss.integration;
import org.aspose.slides.foss.*;

import org.aspose.slides.foss.export.SaveFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for ShapeCollection operations and shape frame properties.
 */
class ShapesTest implements AutoCloseable {

    @TempDir
    Path tempDir;

    @Override
    public void close() {
        // TempDir handles cleanup
    }

    /**
     * Saves a Presentation to a
     * temporary file, disposes the original, and reopens from that file.
     */
    private Presentation saveAndReopen(Presentation pres) throws IOException {
        String path = tempDir.resolve("roundtrip.pptx").toString();
        pres.save(path, SaveFormat.PPTX);
        pres.dispose();
        return new Presentation(path);
    }

    /**
     * Returns a slide with all placeholder shapes removed.
     */
    private static ISlide blankSlide(Presentation pres) {
        ISlide slide = pres.getSlides().get(0);
        slide.getShapes().clear();
        return slide;
    }

    // --- test_add_auto_shape ---

    @Test
    void testAddAutoShape() {
        try (var pres = new Presentation()) {
            var slide = blankSlide(pres);
            var shape = slide.getShapes().addAutoShape(ShapeType.RECTANGLE, 50, 50, 200, 100);
            assertThat(slide.getShapes().size()).isEqualTo(1);
            assertThat(shape.getShapeType()).isEqualTo(ShapeType.RECTANGLE);
        }
    }

    // --- test_multiple_shape_types ---

    @Test
    void testMultipleShapeTypes() {
        ShapeType[] types = {ShapeType.RECTANGLE, ShapeType.ELLIPSE, ShapeType.TRIANGLE};
        try (var pres = new Presentation()) {
            var slide = blankSlide(pres);
            for (ShapeType st : types) {
                var s = slide.getShapes().addAutoShape(st, 10, 10, 100, 100);
                assertThat(s.getShapeType()).isEqualTo(st);
            }
            assertThat(slide.getShapes().size()).isEqualTo(3);
        }
    }

    // --- test_insert_auto_shape ---

    @Test
    void testInsertAutoShape() {
        try (var pres = new Presentation()) {
            var slide = blankSlide(pres);
            slide.getShapes().addAutoShape(ShapeType.RECTANGLE, 50, 50, 200, 100);
            slide.getShapes().addAutoShape(ShapeType.ELLIPSE, 300, 50, 150, 150);
            slide.getShapes().insertAutoShape(1, ShapeType.TRIANGLE, 150, 200, 100, 100);
            assertThat(slide.getShapes().size()).isEqualTo(3);
        }
    }

    // --- test_remove_shape ---

    @Test
    void testRemoveShape() {
        try (var pres = new Presentation()) {
            var slide = blankSlide(pres);
            var s = slide.getShapes().addAutoShape(ShapeType.RECTANGLE, 50, 50, 200, 100);
            slide.getShapes().addAutoShape(ShapeType.ELLIPSE, 300, 50, 150, 150);
            assertThat(slide.getShapes().size()).isEqualTo(2);
            slide.getShapes().remove(s);
            assertThat(slide.getShapes().size()).isEqualTo(1);
        }
    }

    // --- test_remove_at ---

    @Test
    void testRemoveAt() {
        try (var pres = new Presentation()) {
            var slide = blankSlide(pres);
            slide.getShapes().addAutoShape(ShapeType.RECTANGLE, 50, 50, 200, 100);
            slide.getShapes().addAutoShape(ShapeType.ELLIPSE, 300, 50, 150, 150);
            slide.getShapes().removeAt(0);
            assertThat(slide.getShapes().size()).isEqualTo(1);
        }
    }

    // --- test_clear_shapes ---

    @Test
    void testClearShapes() {
        try (var pres = new Presentation()) {
            var slide = pres.getSlides().get(0);
            slide.getShapes().addAutoShape(ShapeType.RECTANGLE, 50, 50, 200, 100);
            slide.getShapes().clear();
            assertThat(slide.getShapes().size()).isEqualTo(0);
        }
    }

    // --- test_shape_frame_properties ---

    @Test
    void testShapeFrameProperties() throws IOException {
        try (var pres = new Presentation()) {
            var slide = blankSlide(pres);
            var shape = slide.getShapes().addAutoShape(ShapeType.RECTANGLE, 200, 200, 300, 250);
            shape.setRotation(45);

            try (var pres2 = saveAndReopen(pres)) {
                var s2 = pres2.getSlides().get(0).getShapes().get(0);
                assertThat(s2.getX()).isEqualTo(200);
                assertThat(s2.getY()).isEqualTo(200);
                assertThat(s2.getWidth()).isEqualTo(300);
                assertThat(s2.getHeight()).isEqualTo(250);
                assertThat(s2.getRotation()).isEqualTo(45);
            }
        }
    }

    // --- test_reorder_shapes ---

    @Test
    void testReorderShapes() {
        try (var pres = new Presentation()) {
            var slide = blankSlide(pres);
            slide.getShapes().addAutoShape(ShapeType.RECTANGLE, 50, 50, 200, 100);
            var ellipse = slide.getShapes().addAutoShape(ShapeType.ELLIPSE, 300, 50, 150, 150);
            slide.getShapes().reorder(0, ellipse);
            assertThat(slide.getShapes().get(0).getShapeType()).isEqualTo(ShapeType.ELLIPSE);
        }
    }

    // --- test_iterate_shapes ---

    @Test
    void testIterateShapes() {
        try (var pres = new Presentation()) {
            var slide = blankSlide(pres);
            slide.getShapes().addAutoShape(ShapeType.RECTANGLE, 50, 50, 200, 100);
            slide.getShapes().addAutoShape(ShapeType.ELLIPSE, 300, 50, 150, 150);
            List<IShape> shapes = new ArrayList<>();
            var shapeCollection = slide.getShapes();
            for (int i = 0; i < shapeCollection.size(); i++) {
                shapes.add(shapeCollection.get(i));
            }
            assertThat(shapes).hasSize(2);
        }
    }

    // --- test_shape_persists_after_reload ---

    @Test
    void testShapePersistsAfterReload() throws IOException {
        try (var pres = new Presentation()) {
            var slide = blankSlide(pres);
            slide.getShapes().addAutoShape(ShapeType.RECTANGLE, 50, 50, 200, 100);

            try (var pres2 = saveAndReopen(pres)) {
                assertThat(pres2.getSlides().get(0).getShapes().size()).isGreaterThanOrEqualTo(1);
                assertThat(pres2.getSlides().get(0).getShapes().get(0).getShapeType()).isEqualTo(ShapeType.RECTANGLE);
            }
        }
    }
}
