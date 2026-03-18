package org.aspose.slides.foss.integration;
import org.aspose.slides.foss.*;

import org.aspose.slides.foss.drawing.Color;
import org.aspose.slides.foss.export.SaveFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for LineFormat: colour, width, dash style.
 */
class LineFormatTest implements AutoCloseable {

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

    // --- test_line_color_and_width ---

    @Test
    void testLineColorAndWidth() throws IOException {
        try (var pres = new Presentation()) {
            var slide = clear(pres);
            var shape = slide.getShapes().addAutoShape(ShapeType.RECTANGLE, 50, 50, 200, 100);
            var lf = shape.getLineFormat();
            lf.setWidth(5);
            lf.getFillFormat().setFillType(FillType.SOLID);
            lf.getFillFormat().getSolidFillColor().setColor(Color.DARK_RED);

            try (var pres2 = saveAndReopen(pres)) {
                var lf2 = pres2.getSlides().get(0).getShapes().get(0).getLineFormat();
                assertThat(lf2.getWidth()).isEqualTo(5);
                assertThat(lf2.getFillFormat().getFillType()).isEqualTo(FillType.SOLID);
                var c = lf2.getFillFormat().getSolidFillColor().getColor();
                assertThat(c.getR()).isEqualTo(Color.DARK_RED.getR());
            }
        }
    }

    // --- test_line_dash_style ---

    @Test
    void testLineDashStyle() throws IOException {
        try (var pres = new Presentation()) {
            var slide = clear(pres);
            var shape = slide.getShapes().addAutoShape(ShapeType.RECTANGLE, 50, 50, 200, 100);
            var lf = shape.getLineFormat();
            lf.setWidth(3);
            lf.setDashStyle(LineDashStyle.DASH);
            lf.getFillFormat().setFillType(FillType.SOLID);
            lf.getFillFormat().getSolidFillColor().setColor(Color.BLACK);

            try (var pres2 = saveAndReopen(pres)) {
                var lf2 = pres2.getSlides().get(0).getShapes().get(0).getLineFormat();
                assertThat(lf2.getDashStyle()).isEqualTo(LineDashStyle.DASH);
            }
        }
    }

    // --- test_multiple_dash_styles ---

    static Stream<LineDashStyle> dashStyles() {
        return Stream.of(
                LineDashStyle.SOLID, LineDashStyle.DASH,
                LineDashStyle.DOT, LineDashStyle.DASH_DOT
        );
    }

    @ParameterizedTest(name = "dash style {0} can be set on shape line format")
    @MethodSource("dashStyles")
    void testMultipleDashStyles(LineDashStyle style) {
        try (var pres = new Presentation()) {
            var slide = pres.getSlides().get(0);
            var shape = slide.getShapes().addAutoShape(ShapeType.RECTANGLE, 50, 50, 200, 50);
            shape.getLineFormat().setDashStyle(style);
            assertThat(shape.getLineFormat().getDashStyle()).isEqualTo(style);
        }
    }
}
