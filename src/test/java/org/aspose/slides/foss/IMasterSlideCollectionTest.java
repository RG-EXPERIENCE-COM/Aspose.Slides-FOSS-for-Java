package org.aspose.slides.foss;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link IMasterSlideCollection}.
 *
 * <p>Verifies master slide collection and slide cloning behavior.</p>
 */
class IMasterSlideCollectionTest {

    @Test
    void addCloneDuplicatesSlideWithShapes() {
        try (var pres = new Presentation()) {
            ISlide slide = pres.getSlides().get(0);
            slide.getShapes().addAutoShape(ShapeType.RECTANGLE, 50, 50, 200, 100);
            pres.getSlides().addClone(slide);
            assertThat(pres.getSlides().size()).isEqualTo(2);
            assertThat(pres.getSlides().get(1).getShapes().size()).isGreaterThanOrEqualTo(1);
        }
    }
}
