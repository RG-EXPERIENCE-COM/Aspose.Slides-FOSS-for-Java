package org.aspose.slides.foss;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link Paragraph} covering the {@code slide} property.
 *
 * <p>Verifies the slide property returns the containing slide.</p>
 */
class ParagraphTest {

    @Test
    void slide_returnsNullByDefault() {
        var paragraph = new Paragraph();
        assertThat(paragraph.getSlide()).isNull();
    }

    @Test
    void slide_returnsParentSlideWhenSet() {
        var paragraph = new Paragraph();
        var slide = new BaseSlide("Slide1", 1);
        paragraph.setSlide(slide);
        assertThat(paragraph.getSlide()).isSameAs(slide);
    }

    @Test
    void slide_returnsNullAfterClearing() {
        var paragraph = new Paragraph();
        var slide = new BaseSlide("Slide1", 1);
        paragraph.setSlide(slide);
        paragraph.setSlide(null);
        assertThat(paragraph.getSlide()).isNull();
    }

    @Test
    void slide_canBeReassigned() {
        var paragraph = new Paragraph();
        var slide1 = new BaseSlide("Slide1", 1);
        var slide2 = new BaseSlide("Slide2", 2);
        paragraph.setSlide(slide1);
        assertThat(paragraph.getSlide()).isSameAs(slide1);
        paragraph.setSlide(slide2);
        assertThat(paragraph.getSlide()).isSameAs(slide2);
    }
}
