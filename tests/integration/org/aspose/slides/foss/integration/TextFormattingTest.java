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
 * Integration tests for text formatting: bold, italic, underline, font, colour, alignment.
 */
class TextFormattingTest implements AutoCloseable {

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
     * Clears slide, adds a rectangle with text "Sample", and returns the portion format.
     * text "Sample", and returns the portion format.
     */
    private IPortionFormat shaped(Presentation pres) {
        pres.getSlides().get(0).getShapes().clear();
        var shape = pres.getSlides().get(0).getShapes()
                .addAutoShape(ShapeType.RECTANGLE, 50, 50, 400, 60);
        shape.getTextFrame().setText("Sample");
        return shape.getTextFrame().getParagraphs().get(0)
                .getPortions().get(0).getPortionFormat();
    }

    // --- test_bold_italic ---

    @Test
    void testBoldItalic() throws IOException {
        try (var pres = new Presentation()) {
            var fmt = shaped(pres);
            fmt.setFontBold(NullableBool.TRUE);
            fmt.setFontItalic(NullableBool.TRUE);

            try (var pres2 = saveAndReopen(pres)) {
                var fmt2 = ((IAutoShape) pres2.getSlides().get(0).getShapes().get(0))
                        .getTextFrame().getParagraphs().get(0)
                        .getPortions().get(0).getPortionFormat();
                assertThat(fmt2.getFontBold()).isEqualTo(NullableBool.TRUE);
                assertThat(fmt2.getFontItalic()).isEqualTo(NullableBool.TRUE);
            }
        }
    }

    // --- test_underline ---

    @Test
    void testUnderline() throws IOException {
        try (var pres = new Presentation()) {
            var fmt = shaped(pres);
            fmt.setFontUnderline(TextUnderlineType.SINGLE);

            try (var pres2 = saveAndReopen(pres)) {
                var fmt2 = ((IAutoShape) pres2.getSlides().get(0).getShapes().get(0))
                        .getTextFrame().getParagraphs().get(0)
                        .getPortions().get(0).getPortionFormat();
                assertThat(fmt2.getFontUnderline()).isEqualTo(TextUnderlineType.SINGLE);
            }
        }
    }

    // --- test_strikethrough ---

    @Test
    void testStrikethrough() throws IOException {
        try (var pres = new Presentation()) {
            var fmt = shaped(pres);
            fmt.setStrikethroughType(TextStrikethroughType.SINGLE);

            try (var pres2 = saveAndReopen(pres)) {
                var fmt2 = ((IAutoShape) pres2.getSlides().get(0).getShapes().get(0))
                        .getTextFrame().getParagraphs().get(0)
                        .getPortions().get(0).getPortionFormat();
                assertThat(fmt2.getStrikethroughType()).isEqualTo(TextStrikethroughType.SINGLE);
            }
        }
    }

    // --- test_font_size ---

    @Test
    void testFontSize() throws IOException {
        try (var pres = new Presentation()) {
            var fmt = shaped(pres);
            fmt.setFontHeight(28);

            try (var pres2 = saveAndReopen(pres)) {
                var fmt2 = ((IAutoShape) pres2.getSlides().get(0).getShapes().get(0))
                        .getTextFrame().getParagraphs().get(0)
                        .getPortions().get(0).getPortionFormat();
                assertThat(fmt2.getFontHeight()).isEqualTo(28);
            }
        }
    }

    // --- test_font_color ---

    @Test
    void testFontColor() throws IOException {
        try (var pres = new Presentation()) {
            var fmt = shaped(pres);
            fmt.getFillFormat().setFillType(FillType.SOLID);
            fmt.getFillFormat().getSolidFillColor().setColor(Color.RED);

            try (var pres2 = saveAndReopen(pres)) {
                var fmt2 = ((IAutoShape) pres2.getSlides().get(0).getShapes().get(0))
                        .getTextFrame().getParagraphs().get(0)
                        .getPortions().get(0).getPortionFormat();
                assertThat(fmt2.getFillFormat().getFillType()).isEqualTo(FillType.SOLID);
                var c = fmt2.getFillFormat().getSolidFillColor().getColor();
                assertThat(c.getR()).isEqualTo(255);
                assertThat(c.getG()).isEqualTo(0);
                assertThat(c.getB()).isEqualTo(0);
            }
        }
    }

    // --- test_latin_font ---

    @Test
    void testLatinFont() throws IOException {
        try (var pres = new Presentation()) {
            var fmt = shaped(pres);
            fmt.setLatinFont(new FontData("Courier New"));

            try (var pres2 = saveAndReopen(pres)) {
                var fmt2 = ((IAutoShape) pres2.getSlides().get(0).getShapes().get(0))
                        .getTextFrame().getParagraphs().get(0)
                        .getPortions().get(0).getPortionFormat();
                assertThat(fmt2.getLatinFont().getFontName()).isEqualTo("Courier New");
            }
        }
    }

    // --- test_paragraph_alignment ---

    @Test
    void testParagraphAlignment() throws IOException {
        try (var pres = new Presentation()) {
            pres.getSlides().get(0).getShapes().clear();
            var shape = pres.getSlides().get(0).getShapes()
                    .addAutoShape(ShapeType.RECTANGLE, 50, 50, 400, 200);
            shape.getTextFrame().setText("Centered");
            shape.getTextFrame().getParagraphs().get(0)
                    .getParagraphFormat().setAlignment(TextAlignment.CENTER);

            try (var pres2 = saveAndReopen(pres)) {
                var pf = ((IAutoShape) pres2.getSlides().get(0).getShapes().get(0))
                        .getTextFrame().getParagraphs().get(0).getParagraphFormat();
                assertThat(pf.getAlignment()).isEqualTo(TextAlignment.CENTER);
            }
        }
    }
}
