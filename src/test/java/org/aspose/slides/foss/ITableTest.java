package org.aspose.slides.foss;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ITable} contract members.
 *
 * <p>Verifies table creation, row/column access, and cell formatting.</p>
 */
class ITableTest {

    /**
     * Saves and reloads a presentation, returning the reloaded copy.
     */
    private Presentation roundTrip(Presentation pres) throws IOException {
        var baos = new ByteArrayOutputStream();
        pres.save(baos);
        pres.dispose();
        return new Presentation(new ByteArrayInputStream(baos.toByteArray()));
    }

    /**
     * Returns the first slide with shapes cleared.
     */
    private ISlide blankSlide(Presentation pres) {
        ISlide slide = pres.getSlides().get(0);
        slide.getShapes().clear();
        return slide;
    }

    /**
     * Finds the first Table shape on a slide.
     */
    private ITable findTable(ISlide slide) {
        for (int i = 0; i < slide.getShapes().size(); i++) {
            IShape shape = slide.getShapes().get(i);
            if (shape instanceof Table) {
                return (ITable) shape;
            }
        }
        return null;
    }

    // --- test_create_table ---

    @Test
    void createTable_verifiesRowAndColumnCounts() {
        try (var pres = new Presentation()) {
            ISlide slide = blankSlide(pres);
            ITable table = slide.getShapes().addTable(50, 50,
                    new double[]{100, 150, 200}, new double[]{40, 40, 40});
            assertThat(table.getRows().size()).isEqualTo(3);
            assertThat(table.getColumns().size()).isEqualTo(3);
        }
    }

    // --- test_column_width ---

    @Test
    void columnWidths_matchConstructorArguments() {
        try (var pres = new Presentation()) {
            ISlide slide = blankSlide(pres);
            ITable table = slide.getShapes().addTable(50, 50,
                    new double[]{100, 200, 300}, new double[]{40});
            assertThat(table.getColumns().get(0).getWidth()).isEqualTo(100);
            assertThat(table.getColumns().get(1).getWidth()).isEqualTo(200);
            assertThat(table.getColumns().get(2).getWidth()).isEqualTo(300);
        }
    }

    // --- test_cell_text ---

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

    // --- test_merge_cells ---

    @Test
    void mergeCells_preservesColSpanAfterReload() throws IOException {
        var pres = new Presentation();
        ISlide slide = blankSlide(pres);
        ITable table = slide.getShapes().addTable(50, 50,
                new double[]{100, 100, 100}, new double[]{40, 40});
        ICell cell1 = table.getRows().get(0).get(0);
        ICell cell2 = table.getRows().get(0).get(1);
        table.mergeCells(cell1, cell2, false);

        // Re-fetch after merge (cells may have been rebuilt)
        cell1 = table.getRows().get(0).get(0);
        assertThat(cell1.isMergedCell()).isTrue();
        assertThat(cell1.getColSpan()).isGreaterThanOrEqualTo(2);

        try (Presentation pres2 = roundTrip(pres)) {
            ITable t2 = findTable(pres2.getSlides().get(0));
            assertThat(t2).isNotNull();
            assertThat(t2.getRows().get(0).get(0).isMergedCell()).isTrue();
        }
    }

    // --- test_cell_borders ---

    @Test
    void cellBorders_persistAfterReload() throws IOException {
        var pres = new Presentation();
        ISlide slide = blankSlide(pres);
        ITable table = slide.getShapes().addTable(50, 50,
                new double[]{150}, new double[]{50});
        ICell cell = table.getRows().get(0).get(0);
        cell.getTextFrame().setText("Bordered");
        ICellFormat fmt = cell.getCellFormat();
        for (ILineFormat border : new ILineFormat[]{
                fmt.getBorderTop(), fmt.getBorderBottom(),
                fmt.getBorderLeft(), fmt.getBorderRight()}) {
            border.getFillFormat().setFillType(FillType.SOLID);
            border.setWidth(3);
        }

        try (Presentation pres2 = roundTrip(pres)) {
            ITable t2 = findTable(pres2.getSlides().get(0));
            assertThat(t2).isNotNull();
            ICellFormat fmt2 = t2.getRows().get(0).get(0).getCellFormat();
            assertThat(fmt2.getBorderTop().getWidth()).isEqualTo(3);
        }
    }

    // --- test_table_style_options ---

    @Test
    void tableStyleFlags_persistAfterReload() throws IOException {
        var pres = new Presentation();
        ISlide slide = blankSlide(pres);
        ITable table = slide.getShapes().addTable(50, 50,
                new double[]{120, 120}, new double[]{40, 40, 40});
        table.setFirstRow(true);
        table.setHorizontalBanding(true);
        table.setVerticalBanding(false);

        try (Presentation pres2 = roundTrip(pres)) {
            ITable t2 = findTable(pres2.getSlides().get(0));
            assertThat(t2).isNotNull();
            assertThat(t2.isFirstRow()).isTrue();
            assertThat(t2.isHorizontalBanding()).isTrue();
        }
    }

    // --- test_cell_fill ---

    @Test
    void cellFill_persistsAfterReload() throws IOException {
        var pres = new Presentation();
        ISlide slide = blankSlide(pres);
        ITable table = slide.getShapes().addTable(50, 50,
                new double[]{200}, new double[]{60});
        ICell cell = table.getRows().get(0).get(0);
        cell.getCellFormat().getFillFormat().setFillType(FillType.SOLID);
        cell.getTextFrame().setText("Blue");

        try (Presentation pres2 = roundTrip(pres)) {
            ITable t2 = findTable(pres2.getSlides().get(0));
            assertThat(t2).isNotNull();
            ICellFormat cf2 = t2.getRows().get(0).get(0).getCellFormat();
            assertThat(cf2.getFillFormat().getFillType()).isEqualTo(FillType.SOLID);
        }
    }
}
