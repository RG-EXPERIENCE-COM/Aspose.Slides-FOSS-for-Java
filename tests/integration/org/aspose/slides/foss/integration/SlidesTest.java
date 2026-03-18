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
 * Integration tests for SlideCollection operations and Slide properties.
 */
class SlidesTest implements AutoCloseable {

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

    /** add_empty_slide increases slide count. */
    @Test
    void testAddEmptySlide() {
        try (var pres = new Presentation()) {
            ILayoutSlide layout = pres.getLayoutSlides().get(0);
            pres.getSlides().addEmptySlide(layout);
            assertThat(pres.getSlides().size()).isEqualTo(2);
        }
    }

    /** insert_empty_slide places a slide at the given index. */
    @Test
    void testInsertEmptySlide() {
        try (var pres = new Presentation()) {
            ILayoutSlide layout = pres.getLayoutSlides().get(0);
            pres.getSlides().addEmptySlide(layout);
            pres.getSlides().insertEmptySlide(1, layout);
            assertThat(pres.getSlides().size()).isEqualTo(3);
        }
    }

    /** Removing a slide by reference decreases count. */
    @Test
    void testRemoveSlideByRef() {
        try (var pres = new Presentation()) {
            ILayoutSlide layout = pres.getLayoutSlides().get(0);
            pres.getSlides().addEmptySlide(layout);
            assertThat(pres.getSlides().size()).isEqualTo(2);
            pres.getSlides().remove(pres.getSlides().get(1));
            assertThat(pres.getSlides().size()).isEqualTo(1);
        }
    }

    /** remove_at removes by index. */
    @Test
    void testRemoveSlideAt() {
        try (var pres = new Presentation()) {
            ILayoutSlide layout = pres.getLayoutSlides().get(0);
            pres.getSlides().addEmptySlide(layout);
            pres.getSlides().removeAt(1);
            assertThat(pres.getSlides().size()).isEqualTo(1);
        }
    }

    /** Setting hidden persists across save/reload. */
    @Test
    void testSlideHidden() throws IOException {
        try (var pres = new Presentation()) {
            pres.getSlides().get(0).setHidden(true);
            assertThat(pres.getSlides().get(0).isHidden()).isTrue();

            try (var pres2 = saveAndReopen(pres)) {
                assertThat(pres2.getSlides().get(0).isHidden()).isTrue();
            }
        }
    }

    /** add_clone duplicates a slide with its shapes. */
    @Test
    void testCloneSlide() {
        try (var pres = new Presentation()) {
            ISlide slide = pres.getSlides().get(0);
            slide.getShapes().addAutoShape(ShapeType.RECTANGLE, 50, 50, 200, 100);
            pres.getSlides().addClone(slide);
            assertThat(pres.getSlides().size()).isEqualTo(2);
            assertThat(pres.getSlides().get(1).getShapes().size()).isGreaterThanOrEqualTo(1);
        }
    }

    /** Each slide exposes its layout_slide. */
    @Test
    void testSlideLayoutAccess() {
        try (var pres = new Presentation()) {
            assertThat(pres.getSlides().get(0).getLayoutSlide()).isNotNull();
        }
    }

    /** Slide name persists after save/reload. */
    @Test
    void testSlideName() throws IOException {
        try (var pres = new Presentation()) {
            pres.getSlides().get(0).setName("MySlide");
            assertThat(pres.getSlides().get(0).getName()).isEqualTo("MySlide");

            try (var pres2 = saveAndReopen(pres)) {
                assertThat(pres2.getSlides().get(0).getName()).isEqualTo("MySlide");
            }
        }
    }

    /** Slides are iterable. */
    @Test
    void testIterateSlides() {
        try (var pres = new Presentation()) {
            ILayoutSlide layout = pres.getLayoutSlides().get(0);
            pres.getSlides().addEmptySlide(layout);
            List<ISlide> slides = new ArrayList<>();
            for (ISlide slide : pres.getSlides()) {
                slides.add(slide);
            }
            assertThat(slides).hasSize(2);
        }
    }

    /** index_of returns the correct position. */
    @Test
    void testIndexOf() {
        try (var pres = new Presentation()) {
            ILayoutSlide layout = pres.getLayoutSlides().get(0);
            pres.getSlides().addEmptySlide(layout);
            assertThat(pres.getSlides().indexOf(pres.getSlides().get(0))).isEqualTo(0);
            assertThat(pres.getSlides().indexOf(pres.getSlides().get(1))).isEqualTo(1);
        }
    }
}
