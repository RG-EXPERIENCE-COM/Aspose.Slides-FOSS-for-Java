package org.aspose.slides.foss;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link Portion} covering the {@code slide} property.
 *
 * <p>Verifies the slide property returns the containing slide.</p>
 */
class PortionTest {

    @Test
    void slide_returnsNullByDefault() {
        var portion = new Portion();
        assertThat(portion.getSlide()).isNull();
    }

    @Test
    void slide_returnsParentSlideWhenSet() {
        var portion = new Portion();
        var slide = new BaseSlide("Slide1", 1);
        portion.setSlide(slide);
        assertThat(portion.getSlide()).isSameAs(slide);
    }

    @Test
    void slide_returnsNullAfterClearing() {
        var portion = new Portion();
        var slide = new BaseSlide("Slide1", 1);
        portion.setSlide(slide);
        portion.setSlide(null);
        assertThat(portion.getSlide()).isNull();
    }

    @Test
    void slide_canBeReassigned() {
        var portion = new Portion();
        var slide1 = new BaseSlide("Slide1", 1);
        var slide2 = new BaseSlide("Slide2", 2);
        portion.setSlide(slide1);
        assertThat(portion.getSlide()).isSameAs(slide1);
        portion.setSlide(slide2);
        assertThat(portion.getSlide()).isSameAs(slide2);
    }
}
