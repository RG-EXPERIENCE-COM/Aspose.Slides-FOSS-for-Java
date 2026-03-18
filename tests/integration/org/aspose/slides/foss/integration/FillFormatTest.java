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
 * Integration tests for FillFormat: solid, gradient, pattern, picture, no-fill.
 */
class FillFormatTest implements AutoCloseable {

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

    // --- test_solid_fill ---

    @Test
    void testSolidFill() throws IOException {
        try (var pres = new Presentation()) {
            var slide = clear(pres);
            var shape = slide.getShapes().addAutoShape(ShapeType.RECTANGLE, 50, 50, 200, 100);
            shape.getFillFormat().setFillType(FillType.SOLID);
            shape.getFillFormat().getSolidFillColor().setColor(Color.fromArgb(255, 0, 128, 255));

            try (var pres2 = saveAndReopen(pres)) {
                var ff = pres2.getSlides().get(0).getShapes().get(0).getFillFormat();
                assertThat(ff.getFillType()).isEqualTo(FillType.SOLID);
                var c = ff.getSolidFillColor().getColor();
                assertThat(c.getR()).isEqualTo(0);
                assertThat(c.getG()).isEqualTo(128);
                assertThat(c.getB()).isEqualTo(255);
            }
        }
    }

    // --- test_gradient_fill ---

    @Test
    void testGradientFill() throws IOException {
        try (var pres = new Presentation()) {
            var slide = clear(pres);
            var shape = slide.getShapes().addAutoShape(ShapeType.RECTANGLE, 50, 50, 300, 150);
            shape.getFillFormat().setFillType(FillType.GRADIENT);
            var gf = shape.getFillFormat().getGradientFormat();
            gf.setGradientShape(GradientShape.LINEAR);
            gf.setLinearGradientAngle(45);
            gf.getGradientStops().add(0.0, Color.BLUE);
            gf.getGradientStops().add(1.0, Color.RED);

            try (var pres2 = saveAndReopen(pres)) {
                var ff2 = pres2.getSlides().get(0).getShapes().get(0).getFillFormat();
                assertThat(ff2.getFillType()).isEqualTo(FillType.GRADIENT);
                assertThat(ff2.getGradientFormat().getGradientStops().size()).isGreaterThanOrEqualTo(2);
            }
        }
    }

    // --- test_pattern_fill ---

    @Test
    void testPatternFill() throws IOException {
        try (var pres = new Presentation()) {
            var slide = clear(pres);
            var shape = slide.getShapes().addAutoShape(ShapeType.RECTANGLE, 50, 50, 200, 100);
            shape.getFillFormat().setFillType(FillType.PATTERN);
            var pf = shape.getFillFormat().getPatternFormat();
            pf.setPatternStyle(PatternStyle.PERCENT50);
            pf.getForeColor().setColor(Color.DARK_BLUE);
            pf.getBackColor().setColor(Color.LIGHT_YELLOW);

            try (var pres2 = saveAndReopen(pres)) {
                var ff2 = pres2.getSlides().get(0).getShapes().get(0).getFillFormat();
                assertThat(ff2.getFillType()).isEqualTo(FillType.PATTERN);
                assertThat(ff2.getPatternFormat().getPatternStyle()).isEqualTo(PatternStyle.PERCENT50);
            }
        }
    }

    // --- test_no_fill ---

    @Test
    void testNoFill() throws IOException {
        try (var pres = new Presentation()) {
            var slide = clear(pres);
            var shape = slide.getShapes().addAutoShape(ShapeType.RECTANGLE, 50, 50, 200, 100);
            shape.getFillFormat().setFillType(FillType.NO_FILL);

            try (var pres2 = saveAndReopen(pres)) {
                assertThat(pres2.getSlides().get(0).getShapes().get(0).getFillFormat().getFillType())
                        .isEqualTo(FillType.NO_FILL);
            }
        }
    }

    // --- test_picture_fill ---

    @Test
    void testPictureFill() throws IOException {
        try (var pres = new Presentation()) {
            var slide = clear(pres);
            var shape = slide.getShapes().addAutoShape(ShapeType.RECTANGLE, 50, 50, 200, 200);
            shape.getFillFormat().setFillType(FillType.PICTURE);
            var pff = shape.getFillFormat().getPictureFillFormat();
            pff.setPictureFillMode(PictureFillMode.STRETCH);
            var img = pres.getImages().addImage(TestHelpers.createTestPng(0, 255, 0));
            pff.getPicture().setImage(img);

            try (var pres2 = saveAndReopen(pres)) {
                var ff2 = pres2.getSlides().get(0).getShapes().get(0).getFillFormat();
                assertThat(ff2.getFillType()).isEqualTo(FillType.PICTURE);
            }
        }
    }
}
