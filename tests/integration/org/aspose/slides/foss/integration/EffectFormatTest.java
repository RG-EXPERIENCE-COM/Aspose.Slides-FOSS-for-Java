package org.aspose.slides.foss.integration;
import org.aspose.slides.foss.*;

import org.aspose.slides.foss.drawing.Color;
import org.aspose.slides.foss.export.SaveFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for EffectFormat: shadow, glow, soft edge, blur, reflection.
 */
class EffectFormatTest implements AutoCloseable {

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

    /**
     * Clears all shapes from slide 0 and returns that slide.
     * Clears all shapes from slide 0.
     */
    private ISlide clear(Presentation pres) {
        ISlide slide = pres.getSlides().get(0);
        slide.getShapes().clear();
        return slide;
    }

    // --- test_outer_shadow ---

    @Test
    void testOuterShadow() throws IOException {
        try (var pres = new Presentation()) {
            var slide = clear(pres);
            var shape = slide.getShapes().addAutoShape(ShapeType.RECTANGLE, 100, 100, 200, 100);
            var ef = shape.getEffectFormat();
            ef.enableOuterShadowEffect();
            var shadow = ef.getOuterShadowEffect();
            shadow.setBlurRadius(10);
            shadow.setDirection(315);
            shadow.setDistance(8);
            shadow.getShadowColor().setColor(Color.fromArgb(128, 0, 0, 0));

            try (var pres2 = saveAndReopen(pres)) {
                var ef2 = pres2.getSlides().get(0).getShapes().get(0).getEffectFormat();
                var s2 = ef2.getOuterShadowEffect();
                assertThat(s2).as("outer_shadow_effect should not be None after reload").isNotNull();
                assertThat(s2.getBlurRadius()).isEqualTo(10);
                assertThat(s2.getDirection()).isEqualTo(315);
                assertThat(s2.getDistance()).isEqualTo(8);
            }
        }
    }

    // --- test_glow ---

    @Test
    void testGlow() throws IOException {
        try (var pres = new Presentation()) {
            var slide = clear(pres);
            var shape = slide.getShapes().addAutoShape(ShapeType.ELLIPSE, 100, 100, 200, 200);
            var ef = shape.getEffectFormat();
            ef.enableGlowEffect();
            ef.getGlowEffect().setRadius(15);
            ef.getGlowEffect().getColor().setColor(Color.GOLD);

            try (var pres2 = saveAndReopen(pres)) {
                var g2 = pres2.getSlides().get(0).getShapes().get(0).getEffectFormat().getGlowEffect();
                assertThat(g2).as("glow_effect should not be None after reload").isNotNull();
                assertThat(g2.getRadius()).isEqualTo(15);
            }
        }
    }

    // --- test_soft_edge ---

    @Test
    void testSoftEdge() throws IOException {
        try (var pres = new Presentation()) {
            var slide = clear(pres);
            var shape = slide.getShapes().addAutoShape(ShapeType.RECTANGLE, 100, 100, 200, 100);
            var ef = shape.getEffectFormat();
            ef.enableSoftEdgeEffect();
            ef.getSoftEdgeEffect().setRadius(10);

            try (var pres2 = saveAndReopen(pres)) {
                var se2 = pres2.getSlides().get(0).getShapes().get(0).getEffectFormat().getSoftEdgeEffect();
                assertThat(se2).as("soft_edge_effect should not be None after reload").isNotNull();
                assertThat(se2.getRadius()).isEqualTo(10);
            }
        }
    }

    // --- test_blur ---

    @Test
    void testBlur() throws IOException {
        try (var pres = new Presentation()) {
            var slide = clear(pres);
            var shape = slide.getShapes().addAutoShape(ShapeType.RECTANGLE, 100, 100, 200, 100);
            var ef = shape.getEffectFormat();
            ef.setBlurEffect(8, true);

            try (var pres2 = saveAndReopen(pres)) {
                var b2 = pres2.getSlides().get(0).getShapes().get(0).getEffectFormat().getBlurEffect();
                assertThat(b2).as("blur_effect should not be None after reload").isNotNull();
                assertThat(b2.getRadius()).isEqualTo(8);
            }
        }
    }

    // --- test_enable_disable_effects ---

    @Test
    void testEnableDisableEffects() {
        try (var pres = new Presentation()) {
            var shape = pres.getSlides().get(0).getShapes().addAutoShape(ShapeType.RECTANGLE, 100, 100, 200, 100);
            var ef = shape.getEffectFormat();
            ef.enableOuterShadowEffect();
            ef.enableGlowEffect();
            assertThat(ef.isNoEffects()).isFalse();

            ef.disableOuterShadowEffect();
            ef.disableGlowEffect();
            assertThat(ef.isNoEffects()).isTrue();
        }
    }
}
