package org.aspose.slides.foss;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link NotesSize} via {@link Presentation}.
 *
 * <p>Verifies notes size properties have positive width and height.</p>
 */
class NotesSizeTest {

    @Test
    void notesSize_hasPositiveWidthAndHeight() {
        try (var pres = new Presentation()) {
            var ns = pres.getNotesSize();
            assertThat(ns).isNotNull();
            assertThat(ns.getSize().getWidth()).isGreaterThan(0);
            assertThat(ns.getSize().getHeight()).isGreaterThan(0);
        }
    }
}
