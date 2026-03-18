package org.aspose.slides.foss.integration;
import org.aspose.slides.foss.*;

import org.aspose.slides.foss.export.SaveFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for TextFrame, Paragraph, and Portion CRUD.
 */
class TextTest implements AutoCloseable {

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

    // --- test_text_frame_text ---

    @Test
    void testTextFrameText() {
        try (var pres = new Presentation()) {
            var shape = pres.getSlides().get(0).getShapes()
                    .addAutoShape(ShapeType.RECTANGLE, 50, 50, 300, 100);
            shape.getTextFrame().setText("Hello, World!");
            assertThat(shape.getTextFrame().getText()).isEqualTo("Hello, World!");
        }
    }

    // --- test_overwrite_text ---

    @Test
    void testOverwriteText() {
        try (var pres = new Presentation()) {
            var shape = pres.getSlides().get(0).getShapes()
                    .addAutoShape(ShapeType.RECTANGLE, 50, 50, 300, 100);
            shape.getTextFrame().setText("First");
            shape.getTextFrame().setText("Second");
            assertThat(shape.getTextFrame().getText()).isEqualTo("Second");
        }
    }

    // --- test_paragraphs_count ---

    @Test
    void testParagraphsCount() {
        try (var pres = new Presentation()) {
            var shape = pres.getSlides().get(0).getShapes()
                    .addAutoShape(ShapeType.RECTANGLE, 50, 50, 300, 100);
            shape.getTextFrame().setText("Line");
            assertThat(shape.getTextFrame().getParagraphs().count()).isGreaterThanOrEqualTo(1);
        }
    }

    // --- test_paragraph_text ---

    @Test
    void testParagraphText() {
        try (var pres = new Presentation()) {
            var shape = pres.getSlides().get(0).getShapes()
                    .addAutoShape(ShapeType.RECTANGLE, 50, 50, 300, 100);
            shape.getTextFrame().setText("Original");
            var para = shape.getTextFrame().getParagraphs().get(0);
            assertThat(para.getText()).isEqualTo("Original");
            para.setText("Modified");
            assertThat(para.getText()).isEqualTo("Modified");
        }
    }

    // --- test_portions_count ---

    @Test
    void testPortionsCount() {
        try (var pres = new Presentation()) {
            var shape = pres.getSlides().get(0).getShapes()
                    .addAutoShape(ShapeType.RECTANGLE, 50, 50, 300, 100);
            shape.getTextFrame().setText("Hello");
            assertThat(shape.getTextFrame().getParagraphs().get(0).getPortions().count())
                    .isGreaterThanOrEqualTo(1);
        }
    }

    // --- test_add_portion ---

    @Test
    void testAddPortion() {
        try (var pres = new Presentation()) {
            var shape = pres.getSlides().get(0).getShapes()
                    .addAutoShape(ShapeType.RECTANGLE, 50, 50, 400, 100);
            shape.getTextFrame().setText("Hello ");
            var newPortion = new Portion("World!");
            shape.getTextFrame().getParagraphs().get(0).getPortions().add(newPortion);
            assertThat(shape.getTextFrame().getText()).contains("World!");
        }
    }

    // --- test_text_persists ---

    @Test
    void testTextPersists() throws IOException {
        try (var pres = new Presentation()) {
            pres.getSlides().get(0).getShapes().clear();
            var shape = pres.getSlides().get(0).getShapes()
                    .addAutoShape(ShapeType.RECTANGLE, 50, 50, 300, 100);
            shape.getTextFrame().setText("Persistent text");

            try (var pres2 = saveAndReopen(pres)) {
                var reloadedShape = (IAutoShape) pres2.getSlides().get(0).getShapes().get(0);
                assertThat(reloadedShape.getTextFrame().getText())
                        .isEqualTo("Persistent text");
            }
        }
    }

    // --- test_add_text_frame ---

    @Test
    void testAddTextFrame() {
        try (var pres = new Presentation()) {
            var shape = pres.getSlides().get(0).getShapes()
                    .addAutoShape(ShapeType.RECTANGLE, 50, 50, 300, 100, false);
            shape.addTextFrame("via add_text_frame");
            assertThat(shape.getTextFrame().getText()).isEqualTo("via add_text_frame");
        }
    }
}
