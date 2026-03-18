package org.aspose.slides.foss;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link NotesSlideHeaderFooterManager}.
 *
 * <p>Verifies notes slide header and footer visibility and text properties.</p>
 */
class NotesSlideHeaderFooterManagerTest {

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
    void headerFooterVisibility_persistsAfterRoundTrip() throws IOException {
        var pres = new Presentation();
        var slide = (Slide) pres.getSlides().get(0);
        var notes = slide.getNotesSlideManager().addNotesSlide();
        notes.getNotesTextFrame().setText("Notes");

        var hfm = notes.getHeaderFooterManager();
        hfm.setFooterVisibility(true);
        hfm.setFooterText("Confidential");
        hfm.setSlideNumberVisibility(true);

        assertThat(hfm.isFooterVisible()).isTrue();
        assertThat(hfm.isSlideNumberVisible()).isTrue();

        try (var pres2 = roundTrip(pres)) {
            var ns2 = pres2.getSlides().get(0).getNotesSlideManager().getNotesSlide();
            assertThat(ns2).isNotNull();

            var hfm2 = ns2.getHeaderFooterManager();
            assertThat(hfm2.isFooterVisible()).isTrue();
            assertThat(hfm2.isSlideNumberVisible()).isTrue();
        }
    }

    @Test
    void setFooterVisibility_addsAndRemovesPlaceholder() {
        try (var pres = new Presentation()) {
            var slide = (Slide) pres.getSlides().get(0);
            var notes = slide.getNotesSlideManager().addNotesSlide();

            var hfm = notes.getHeaderFooterManager();
            assertThat(hfm.isFooterVisible()).isFalse();

            hfm.setFooterVisibility(true);
            assertThat(hfm.isFooterVisible()).isTrue();

            hfm.setFooterVisibility(false);
            assertThat(hfm.isFooterVisible()).isFalse();
        }
    }

    @Test
    void setHeaderVisibility_addsAndRemovesPlaceholder() {
        try (var pres = new Presentation()) {
            var slide = (Slide) pres.getSlides().get(0);
            var notes = slide.getNotesSlideManager().addNotesSlide();

            var hfm = notes.getHeaderFooterManager();
            assertThat(hfm.isHeaderVisible()).isFalse();

            hfm.setHeaderVisibility(true);
            assertThat(hfm.isHeaderVisible()).isTrue();

            hfm.setHeaderVisibility(false);
            assertThat(hfm.isHeaderVisible()).isFalse();
        }
    }

    @Test
    void setDateTimeVisibility_addsAndRemovesPlaceholder() {
        try (var pres = new Presentation()) {
            var slide = (Slide) pres.getSlides().get(0);
            var notes = slide.getNotesSlideManager().addNotesSlide();

            var hfm = notes.getHeaderFooterManager();
            assertThat(hfm.isDateTimeVisible()).isFalse();

            hfm.setDateTimeVisibility(true);
            assertThat(hfm.isDateTimeVisible()).isTrue();

            hfm.setDateTimeVisibility(false);
            assertThat(hfm.isDateTimeVisible()).isFalse();
        }
    }

    @Test
    void setSlideNumberVisibility_addsAndRemovesPlaceholder() {
        try (var pres = new Presentation()) {
            var slide = (Slide) pres.getSlides().get(0);
            var notes = slide.getNotesSlideManager().addNotesSlide();

            var hfm = notes.getHeaderFooterManager();
            assertThat(hfm.isSlideNumberVisible()).isFalse();

            hfm.setSlideNumberVisibility(true);
            assertThat(hfm.isSlideNumberVisible()).isTrue();

            hfm.setSlideNumberVisibility(false);
            assertThat(hfm.isSlideNumberVisible()).isFalse();
        }
    }

    @Test
    void setFooterText_setsTextOnPlaceholder() {
        try (var pres = new Presentation()) {
            var slide = (Slide) pres.getSlides().get(0);
            var notes = slide.getNotesSlideManager().addNotesSlide();

            var hfm = notes.getHeaderFooterManager();
            hfm.setFooterText("My Footer");
            // Setting text should also make footer visible
            assertThat(hfm.isFooterVisible()).isTrue();
        }
    }

    @Test
    void setHeaderText_setsTextOnPlaceholder() {
        try (var pres = new Presentation()) {
            var slide = (Slide) pres.getSlides().get(0);
            var notes = slide.getNotesSlideManager().addNotesSlide();

            var hfm = notes.getHeaderFooterManager();
            hfm.setHeaderText("My Header");
            assertThat(hfm.isHeaderVisible()).isTrue();
        }
    }

    @Test
    void setDateTimeText_setsTextOnPlaceholder() {
        try (var pres = new Presentation()) {
            var slide = (Slide) pres.getSlides().get(0);
            var notes = slide.getNotesSlideManager().addNotesSlide();

            var hfm = notes.getHeaderFooterManager();
            hfm.setDateTimeText("2026-03-16");
            assertThat(hfm.isDateTimeVisible()).isTrue();
        }
    }

    @Test
    void interfaceCastMethods_returnSameInstance() {
        try (var pres = new Presentation()) {
            var slide = (Slide) pres.getSlides().get(0);
            var notes = slide.getNotesSlideManager().addNotesSlide();

            var hfm = notes.getHeaderFooterManager();
            assertThat(hfm.asIBaseHandoutNotesSlideHeaderFooterManag()).isSameAs(hfm);
            assertThat(hfm.asIBaseSlideHeaderFooterManager()).isSameAs(hfm);
            assertThat(hfm.asIBaseHeaderFooterManager()).isSameAs(hfm);
        }
    }
}
