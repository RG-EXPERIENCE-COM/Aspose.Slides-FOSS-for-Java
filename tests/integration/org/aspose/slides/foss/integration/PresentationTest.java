package org.aspose.slides.foss.integration;
import org.aspose.slides.foss.*;

import org.aspose.slides.foss.export.SaveFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

/**
 * Integration tests for Presentation create / load / save / properties.
 */
class PresentationTest implements AutoCloseable {

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

    /** A brand-new presentation has exactly 1 slide. */
    @Test
    void testCreateEmpty() {
        try (var pres = new Presentation()) {
            assertThat(pres.getSlides().size()).isEqualTo(1);
        }
    }

    /** Round-trip: create -> save -> reload preserves slide count. */
    @Test
    void testSaveAndReload() throws IOException {
        try (var pres = new Presentation()) {
            try (var pres2 = saveAndReopen(pres)) {
                assertThat(pres2.getSlides().size()).isEqualTo(1);
            }
        }
    }

    /** Saving to a ByteArrayOutputStream produces a non-empty buffer. */
    @Test
    void testSaveToStream() throws IOException {
        try (var pres = new Presentation()) {
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            pres.save(buf, SaveFormat.PPTX);
            assertThat(buf.size()).isGreaterThan(0);
        }
    }

    /** Presentation can be used as a try-with-resources (context manager). */
    @Test
    void testContextManager() {
        try (Presentation pres = new Presentation()) {
            assertThat(pres.getSlides().size()).isGreaterThanOrEqualTo(1);
        }
    }

    /** first_slide_number persists across save/reload. */
    @Test
    void testFirstSlideNumber() throws IOException {
        try (var pres = new Presentation()) {
            pres.setFirstSlideNumber(5);
            assertThat(pres.getFirstSlideNumber()).isEqualTo(5);

            try (var pres2 = saveAndReopen(pres)) {
                assertThat(pres2.getFirstSlideNumber()).isEqualTo(5);
            }
        }
    }

    /** Load a known .pptx from test_data and verify it opens. */
    @Test
    void testLoadExisting() throws IOException {
        Path path = Path.of("src", "test", "resources", "aspose", "slidesfoss",
                "test_data", "Presentation.pptx");
        assumeThat(Files.exists(path))
                .as("Presentation.pptx not found in test_data")
                .isTrue();
        try (var pres = new Presentation(path.toString())) {
            assertThat(pres.getSlides().size()).isGreaterThanOrEqualTo(1);
        }
    }

    /** Calling dispose() twice must not raise. */
    @Test
    void testDisposeIsIdempotent() {
        Presentation pres = new Presentation();
        pres.dispose();
        pres.dispose(); // second call should be harmless
    }

    /** Adding a slide increases slide count to 2. */
    @Test
    void testSlideCountAfterAdd() throws IOException {
        try (var pres = new Presentation()) {
            ILayoutSlide layout = pres.getLayoutSlides().get(0);
            pres.getSlides().addEmptySlide(layout);
            assertThat(pres.getSlides().size()).isEqualTo(2);
        }
    }
}
