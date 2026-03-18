package org.aspose.slides.foss.integration;
import org.aspose.slides.foss.*;

import org.aspose.slides.foss.export.SaveFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for NotesSlide, NotesSlideManager, header/footer, and NotesSize.
 */
class NotesSlideTest implements AutoCloseable {

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

    // --- test_add_notes ---

    @Test
    void testAddNotes() throws IOException {
        try (var pres = new Presentation()) {
            var slide = pres.getSlides().get(0);
            var notes = slide.getNotesSlideManager().addNotesSlide();
            notes.getNotesTextFrame().setText("Speaker notes");

            try (var pres2 = saveAndReopen(pres)) {
                var ns2 = pres2.getSlides().get(0).getNotesSlideManager().getNotesSlide();
                assertThat(ns2).isNotNull();
                assertThat(ns2.getNotesTextFrame().getText()).isEqualTo("Speaker notes");
            }
        }
    }

    // --- test_remove_notes ---

    @Test
    void testRemoveNotes() throws IOException {
        try (var pres = new Presentation()) {
            var mgr = pres.getSlides().get(0).getNotesSlideManager();
            mgr.addNotesSlide();
            assertThat(mgr.getNotesSlide()).isNotNull();

            mgr.removeNotesSlide();
            assertThat(mgr.getNotesSlide()).isNull();

            try (var pres2 = saveAndReopen(pres)) {
                assertThat(pres2.getSlides().get(0).getNotesSlideManager().getNotesSlide()).isNull();
            }
        }
    }

    // --- test_notes_header_footer ---

    @Test
    void testNotesHeaderFooter() throws IOException {
        try (var pres = new Presentation()) {
            var notes = pres.getSlides().get(0).getNotesSlideManager().addNotesSlide();
            notes.getNotesTextFrame().setText("Notes");
            var hfm = notes.getHeaderFooterManager();
            hfm.setFooterVisibility(true);
            hfm.setFooterText("Confidential");
            hfm.setSlideNumberVisibility(true);

            assertThat(hfm.isFooterVisible()).isTrue();
            assertThat(hfm.isSlideNumberVisible()).isTrue();

            try (var pres2 = saveAndReopen(pres)) {
                var ns2 = pres2.getSlides().get(0).getNotesSlideManager().getNotesSlide();
                var hfm2 = ns2.getHeaderFooterManager();
                assertThat(hfm2.isFooterVisible()).isTrue();
                assertThat(hfm2.isSlideNumberVisible()).isTrue();
            }
        }
    }

    // --- test_notes_parent_slide ---

    @Test
    void testNotesParentSlide() {
        try (var pres = new Presentation()) {
            var slide = pres.getSlides().get(0);
            var notes = slide.getNotesSlideManager().addNotesSlide();
            assertThat(notes.getParentSlide()).isSameAs(slide);
        }
    }

    // --- test_notes_size ---

    @Test
    void testNotesSize() {
        try (var pres = new Presentation()) {
            var ns = pres.getNotesSize();
            assertThat(ns.getSize().getWidth()).isGreaterThan(0);
            assertThat(ns.getSize().getHeight()).isGreaterThan(0);
        }
    }
}
