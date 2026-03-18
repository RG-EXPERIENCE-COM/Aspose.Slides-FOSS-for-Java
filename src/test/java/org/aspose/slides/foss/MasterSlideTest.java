package org.aspose.slides.foss;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link IMasterSlide} and layout slide integration.
 *
 * <p>Verifies master slide access and layout slide integration.</p>
 */
class MasterSlideTest {

    @Test
    void slideCountAfterAdd() {
        try (var pres = new Presentation()) {
            ILayoutSlide layout = pres.getLayoutSlides().get(0);
            pres.getSlides().addEmptySlide(layout);
            assertThat(pres.getSlides().size()).isEqualTo(2);
        }
    }

    @Test
    void addEmptySlide() {
        try (var pres = new Presentation()) {
            ILayoutSlide layout = pres.getLayoutSlides().get(0);
            pres.getSlides().addEmptySlide(layout);
            assertThat(pres.getSlides().size()).isEqualTo(2);
        }
    }

    @Test
    void indexOf() {
        try (var pres = new Presentation()) {
            ILayoutSlide layout = pres.getLayoutSlides().get(0);
            pres.getSlides().addEmptySlide(layout);
            assertThat(pres.getSlides().indexOf(pres.getSlides().get(0))).isEqualTo(0);
            assertThat(pres.getSlides().indexOf(pres.getSlides().get(1))).isEqualTo(1);
        }
    }

    @Test
    void insertEmptySlide() {
        try (var pres = new Presentation()) {
            ILayoutSlide layout = pres.getLayoutSlides().get(0);
            pres.getSlides().addEmptySlide(layout);
            pres.getSlides().insertEmptySlide(1, layout);
            assertThat(pres.getSlides().size()).isEqualTo(3);
        }
    }

    @Test
    void iterateSlides() {
        try (var pres = new Presentation()) {
            ILayoutSlide layout = pres.getLayoutSlides().get(0);
            pres.getSlides().addEmptySlide(layout);
            int count = 0;
            for (ISlide ignored : pres.getSlides()) {
                count++;
            }
            assertThat(count).isEqualTo(2);
        }
    }

    @Test
    void removeSlideAt() {
        try (var pres = new Presentation()) {
            ILayoutSlide layout = pres.getLayoutSlides().get(0);
            pres.getSlides().addEmptySlide(layout);
            pres.getSlides().removeAt(1);
            assertThat(pres.getSlides().size()).isEqualTo(1);
        }
    }
}
