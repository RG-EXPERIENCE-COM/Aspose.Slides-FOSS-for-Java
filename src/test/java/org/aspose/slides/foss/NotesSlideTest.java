package org.aspose.slides.foss;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link NotesSlide}.
 *
 * <p>Verifies notes slide creation, text access, and round-trip persistence.</p>
 */
class NotesSlideTest {

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
    void notesTextPersistsAfterRoundTrip() throws IOException {
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
    void parentSlideReferencesOriginalSlide() {
        try (var pres = new Presentation()) {
            var slide = pres.getSlides().get(0);
            var notes = ((Slide) slide).getNotesSlideManager().addNotesSlide();
            assertThat(notes.getParentSlide()).isSameAs(slide);
        }
    }

    @Test
    void asIBaseSlideReturnsSelf() {
        try (var pres = new Presentation()) {
            var slide = (Slide) pres.getSlides().get(0);
            var notes = slide.getNotesSlideManager().addNotesSlide();
            assertThat(notes.asIBaseSlide()).isSameAs(notes);
        }
    }

    @Test
    void removeNotes_persistsAcrossRoundTrip() throws IOException {
        var pres = new Presentation();
        var mgr = pres.getSlides().get(0).getNotesSlideManager();
        mgr.addNotesSlide();
        assertThat(mgr.getNotesSlide()).isNotNull();

        mgr.removeNotesSlide();
        assertThat(mgr.getNotesSlide()).isNull();

        try (var pres2 = roundTrip(pres)) {
            assertThat(pres2.getSlides().get(0).getNotesSlideManager().getNotesSlide()).isNull();
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
}
