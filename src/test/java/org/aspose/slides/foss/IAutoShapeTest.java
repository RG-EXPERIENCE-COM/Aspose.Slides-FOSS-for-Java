package org.aspose.slides.foss;

import org.aspose.slides.foss.drawing.Color;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link IAutoShape} exercised through the Presentation API.
 *
 * <p>Covers text frame creation, text overwriting, portion addition, cell text, and cell fill.</p>
 */
class IAutoShapeTest {

    private Presentation roundTrip(Presentation pres) throws IOException {
        var baos = new ByteArrayOutputStream();
        pres.save(baos);
        pres.dispose();
        return new Presentation(new ByteArrayInputStream(baos.toByteArray()));
    }

    private ISlide blankSlide(Presentation pres) {
        ISlide slide = pres.getSlides().get(0);
        slide.getShapes().clear();
        return slide;
    }

    private ITable findTable(ISlide slide) {
        for (int i = 0; i < slide.getShapes().size(); i++) {
            IShape shape = slide.getShapes().get(i);
            if (shape instanceof Table) {
                return (ITable) shape;
            }
        }
        return null;
    }

    // --- from test_text.py: test_overwrite_text ---

    @Test
    void overwriteText_replacesPreviousValue() {
        try (var pres = new Presentation()) {
            IAutoShape shape = pres.getSlides().get(0).getShapes()
                    .addAutoShape(ShapeType.RECTANGLE, 50, 50, 300, 100);
            shape.getTextFrame().setText("First");
            shape.getTextFrame().setText("Second");
            assertThat(shape.getTextFrame().getText()).isEqualTo("Second");
        }
    }

    // --- from test_text.py: test_add_portion ---

    @Test
    void addPortion_appendsText() {
        try (var pres = new Presentation()) {
            IAutoShape shape = pres.getSlides().get(0).getShapes()
                    .addAutoShape(ShapeType.RECTANGLE, 50, 50, 400, 100);
            shape.getTextFrame().setText("Hello ");
            Portion newPortion = new Portion("World!");
            shape.getTextFrame().getParagraphs().get(0).getPortions().add(newPortion);
            assertThat(shape.getTextFrame().getText()).contains("World!");
        }
    }

    // --- from test_text.py: test_add_text_frame ---

    @Test
    void addTextFrame_onShapeCreatedWithoutText() {
        try (var pres = new Presentation()) {
            IAutoShape shape = pres.getSlides().get(0).getShapes()
                    .addAutoShape(ShapeType.RECTANGLE, 50, 50, 300, 100, false);
            shape.addTextFrame("via add_text_frame");
            assertThat(shape.getTextFrame().getText()).isEqualTo("via add_text_frame");
        }
    }

    // --- from test_table.py: test_cell_text ---

    @Test
    void cellText_roundTrips() throws IOException {
        var pres = new Presentation();
        ISlide slide = blankSlide(pres);
        ITable table = slide.getShapes().addTable(50, 50,
                new double[]{100, 100}, new double[]{40, 40});
        table.getRows().get(0).get(0).getTextFrame().setText("A");
        table.getRows().get(0).get(1).getTextFrame().setText("B");
        table.getRows().get(1).get(0).getTextFrame().setText("C");
        table.getRows().get(1).get(1).getTextFrame().setText("D");

        try (Presentation pres2 = roundTrip(pres)) {
            ITable t2 = findTable(pres2.getSlides().get(0));
            assertThat(t2).isNotNull();
            assertThat(t2.getRows().get(0).get(0).getTextFrame().getText()).isEqualTo("A");
            assertThat(t2.getRows().get(0).get(1).getTextFrame().getText()).isEqualTo("B");
            assertThat(t2.getRows().get(1).get(0).getTextFrame().getText()).isEqualTo("C");
            assertThat(t2.getRows().get(1).get(1).getTextFrame().getText()).isEqualTo("D");
        }
    }

    // --- from test_table.py: test_cell_fill ---

    @Test
    void cellFill_persistsAfterReload() throws IOException {
        var pres = new Presentation();
        ISlide slide = blankSlide(pres);
        ITable table = slide.getShapes().addTable(50, 50,
                new double[]{200}, new double[]{60});
        ICell cell = table.getRows().get(0).get(0);
        cell.getCellFormat().getFillFormat().setFillType(FillType.SOLID);
        cell.getCellFormat().getFillFormat().getSolidFillColor().setColor(Color.LIGHT_BLUE);
        cell.getTextFrame().setText("Blue");

        try (Presentation pres2 = roundTrip(pres)) {
            ITable t2 = findTable(pres2.getSlides().get(0));
            assertThat(t2).isNotNull();
            ICellFormat cf2 = t2.getRows().get(0).get(0).getCellFormat();
            assertThat(cf2.getFillFormat().getFillType()).isEqualTo(FillType.SOLID);
        }
    }
}
