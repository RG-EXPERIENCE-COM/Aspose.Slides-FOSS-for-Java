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
 * Integration tests for Table: create, cell text, merge, borders, style options.
 */
class TableTest implements AutoCloseable {

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
     * Finds the Table shape on a slide (skip placeholders).
     */
    private static ITable findTable(ISlide slide) {
        for (int i = 0; i < slide.getShapes().size(); i++) {
            IShape shape = slide.getShapes().get(i);
            if (shape instanceof ITable table) {
                return table;
            }
        }
        return null;
    }

    /**
     * Returns the first slide with placeholders removed.
     */
    private static ISlide blankSlide(Presentation pres) {
        ISlide slide = pres.getSlides().get(0);
        slide.getShapes().clear();
        return slide;
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

    // --- test_cell_text ---

    @Test
    void cellText_roundTrips() throws IOException {
        try (var pres = new Presentation()) {
            ISlide slide = blankSlide(pres);
            ITable table = slide.getShapes().addTable(50, 50,
                    new double[]{100, 100}, new double[]{40, 40});
            table.getRows().get(0).get(0).getTextFrame().setText("A");
            table.getRows().get(0).get(1).getTextFrame().setText("B");
            table.getRows().get(1).get(0).getTextFrame().setText("C");
            table.getRows().get(1).get(1).getTextFrame().setText("D");

            try (var pres2 = saveAndReopen(pres)) {
                ITable t2 = findTable(pres2.getSlides().get(0));
                assertThat(t2).isNotNull();
                assertThat(t2.getRows().get(0).get(0).getTextFrame().getText()).isEqualTo("A");
                assertThat(t2.getRows().get(0).get(1).getTextFrame().getText()).isEqualTo("B");
                assertThat(t2.getRows().get(1).get(0).getTextFrame().getText()).isEqualTo("C");
                assertThat(t2.getRows().get(1).get(1).getTextFrame().getText()).isEqualTo("D");
            }
        }
    }

    // --- test_merge_cells ---

    @Test
    void mergeCells_preservesColSpanAfterReload() throws IOException {
        try (var pres = new Presentation()) {
            ISlide slide = blankSlide(pres);
            ITable table = slide.getShapes().addTable(50, 50,
                    new double[]{100, 100, 100}, new double[]{40, 40});
            ICell cell1 = table.getRows().get(0).get(0);
            ICell cell2 = table.getRows().get(0).get(1);
            table.mergeCells(cell1, cell2, false);
            assertThat(cell1.isMergedCell()).isTrue();
            assertThat(cell1.getColSpan()).isGreaterThanOrEqualTo(2);

            try (var pres2 = saveAndReopen(pres)) {
                ITable t2 = findTable(pres2.getSlides().get(0));
                assertThat(t2).isNotNull();
                assertThat(t2.getRows().get(0).get(0).isMergedCell()).isTrue();
            }
        }
    }

    // --- test_cell_borders ---

    @Test
    void cellBorders_persistAfterReload() throws IOException {
        try (var pres = new Presentation()) {
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
                border.getFillFormat().getSolidFillColor().setColor(Color.RED);
                border.setWidth(3);
            }

            try (var pres2 = saveAndReopen(pres)) {
                ITable t2 = findTable(pres2.getSlides().get(0));
                assertThat(t2).isNotNull();
                ICellFormat fmt2 = t2.getRows().get(0).get(0).getCellFormat();
                assertThat(fmt2.getBorderTop().getWidth()).isEqualTo(3);
            }
        }
    }

    // --- test_table_style_options ---

    @Test
    void tableStyleFlags_persistAfterReload() throws IOException {
        try (var pres = new Presentation()) {
            ISlide slide = blankSlide(pres);
            ITable table = slide.getShapes().addTable(50, 50,
                    new double[]{120, 120}, new double[]{40, 40, 40});
            table.setFirstRow(true);
            table.setHorizontalBanding(true);
            table.setVerticalBanding(false);

            try (var pres2 = saveAndReopen(pres)) {
                ITable t2 = findTable(pres2.getSlides().get(0));
                assertThat(t2).isNotNull();
                assertThat(t2.isFirstRow()).isTrue();
                assertThat(t2.isHorizontalBanding()).isTrue();
            }
        }
    }

    // --- test_row_height ---

    @Test
    void rowHeights_matchConstructorArguments() {
        try (var pres = new Presentation()) {
            ISlide slide = blankSlide(pres);
            ITable table = slide.getShapes().addTable(50, 50,
                    new double[]{200}, new double[]{30, 50, 70});
            assertThat(table.getRows().get(0).getHeight()).isEqualTo(30);
            assertThat(table.getRows().get(1).getHeight()).isEqualTo(50);
            assertThat(table.getRows().get(2).getHeight()).isEqualTo(70);
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

    // --- test_cell_fill ---

    @Test
    void cellFill_persistsAfterReload() throws IOException {
        try (var pres = new Presentation()) {
            ISlide slide = blankSlide(pres);
            ITable table = slide.getShapes().addTable(50, 50,
                    new double[]{200}, new double[]{60});
            ICell cell = table.getRows().get(0).get(0);
            cell.getCellFormat().getFillFormat().setFillType(FillType.SOLID);
            cell.getCellFormat().getFillFormat().getSolidFillColor().setColor(Color.LIGHT_BLUE);
            cell.getTextFrame().setText("Blue");

            try (var pres2 = saveAndReopen(pres)) {
                ITable t2 = findTable(pres2.getSlides().get(0));
                assertThat(t2).isNotNull();
                ICellFormat cf2 = t2.getRows().get(0).get(0).getCellFormat();
                assertThat(cf2.getFillFormat().getFillType()).isEqualTo(FillType.SOLID);
            }
        }
    }
}
