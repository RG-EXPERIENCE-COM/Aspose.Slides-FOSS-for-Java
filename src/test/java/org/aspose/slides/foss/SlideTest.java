package org.aspose.slides.foss;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link Slide} properties.
 *
 * <p>Covers slide creation, cloning, removal, and property access.</p>
 */
class SlideTest {

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
    void slideHidden_persistsAcrossRoundTrip() throws IOException {
        var pres = new Presentation();
        pres.getSlides().get(0).setHidden(true);
        assertThat(pres.getSlides().get(0).isHidden()).isTrue();

        try (var pres2 = roundTrip(pres)) {
            assertThat(pres2.getSlides().get(0).isHidden()).isTrue();
        }
    }
}
