package org.aspose.slides.foss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

/**
 * Tests for {@link Column}: width, columnFormat, cell collection, bulk text formatting.
 *
 * <p>Covers column width, format, cell collection, and bulk text formatting.</p>
 */
class ColumnTest {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";
    private static final double EMU_PER_POINT = 12700.0;

    private Document doc;

    @BeforeEach
    void setUp() throws Exception {
        doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .newDocument();
    }

    private Element createGridCol(double widthPts) {
        Element gridCol = doc.createElementNS(NS_A, "a:gridCol");
        gridCol.setAttribute("w", String.valueOf(Math.round(widthPts * EMU_PER_POINT)));
        doc.appendChild(gridCol);
        return gridCol;
    }

    // --- width ---

    @Test
    void width_readsFromGridColElement() {
        Element gridCol = createGridCol(150.0);
        var col = new Column(gridCol, null, List.of());
        assertThat(col.getWidth()).isCloseTo(150.0, offset(0.001));
    }

    @Test
    void width_returnsZeroWhenNoElement() {
        var col = new Column();
        assertThat(col.getWidth()).isEqualTo(0.0);
    }

    @Test
    void width_setUpdatesElement() {
        Element gridCol = createGridCol(100.0);
        var col = new Column(gridCol, null, List.of());
        col.setWidth(200.0);
        assertThat(col.getWidth()).isCloseTo(200.0, offset(0.001));
    }

    @Test
    void width_setInvokesSaveCallback() {
        Element gridCol = createGridCol(100.0);
        AtomicBoolean saved = new AtomicBoolean(false);
        var col = new Column(gridCol, () -> saved.set(true), List.of());
        col.setWidth(200.0);
        assertThat(saved).isTrue();
    }

    @Test
    void width_setNoOpWhenNoElement() {
        var col = new Column();
        col.setWidth(200.0);
        assertThat(col.getWidth()).isEqualTo(0.0);
    }

    @Test
    void width_returnsZeroWhenAttributeMissing() {
        Element gridCol = doc.createElementNS(NS_A, "a:gridCol");
        doc.appendChild(gridCol);
        var col = new Column(gridCol, null, List.of());
        assertThat(col.getWidth()).isEqualTo(0.0);
    }

    // --- columnFormat ---

    @Test
    void columnFormat_returnsNonNull() {
        var col = new Column();
        assertThat(col.getColumnFormat()).isNotNull();
    }

    @Test
    void columnFormat_returnsColumnFormatInstance() {
        var col = new Column();
        assertThat(col.getColumnFormat()).isInstanceOf(ColumnFormat.class);
    }

    // --- asICellCollection ---

    @Test
    void asICellCollection_returnsSelf() {
        var col = new Column();
        assertThat(col.getAsICellCollection()).isSameAs(col);
    }

    // --- asIBulkTextFormattable ---

    @Test
    void asIBulkTextFormattable_returnsSelf() {
        var col = new Column();
        assertThat(col.getAsIBulkTextFormattable()).isSameAs(col);
    }

    // --- cell access ---

    @Test
    void size_returnsNumberOfCells() {
        Element gridCol = createGridCol(100.0);
        var stubCell = new CellStub();
        var col = new Column(gridCol, null, List.of(stubCell));
        assertThat(col.size()).isEqualTo(1);
    }

    @Test
    void get_returnsCellByIndex() {
        Element gridCol = createGridCol(100.0);
        var stubCell = new CellStub();
        var col = new Column(gridCol, null, List.of(stubCell));
        assertThat(col.get(0)).isSameAs(stubCell);
    }

    // --- column widths match constructor arguments (from test_table.test_column_width) ---

    @Test
    void columnWidths_matchConstructorArguments() {
        Element gc1 = doc.createElementNS(NS_A, "a:gridCol");
        gc1.setAttribute("w", String.valueOf(Math.round(100.0 * EMU_PER_POINT)));

        // Need separate docs for additional elements
        Document doc2, doc3;
        try {
            doc2 = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            doc3 = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Element gc2 = doc2.createElementNS(NS_A, "a:gridCol");
        gc2.setAttribute("w", String.valueOf(Math.round(200.0 * EMU_PER_POINT)));
        Element gc3 = doc3.createElementNS(NS_A, "a:gridCol");
        gc3.setAttribute("w", String.valueOf(Math.round(300.0 * EMU_PER_POINT)));

        var col1 = new Column(gc1, null, List.of());
        var col2 = new Column(gc2, null, List.of());
        var col3 = new Column(gc3, null, List.of());

        assertThat(col1.getWidth()).isCloseTo(100.0, offset(0.001));
        assertThat(col2.getWidth()).isCloseTo(200.0, offset(0.001));
        assertThat(col3.getWidth()).isCloseTo(300.0, offset(0.001));
    }

    /**
     * Minimal ICell stub for testing Column cell collection behavior.
     */
    private static class CellStub implements ICell, IPresentationComponent, ISlideComponent {
        @Override public double getOffsetX() { return 0; }
        @Override public double getOffsetY() { return 0; }
        @Override public int getFirstRowIndex() { return 0; }
        @Override public int getFirstColumnIndex() { return 0; }
        @Override public double getWidth() { return 0; }
        @Override public double getHeight() { return 0; }
        @Override public double getMinimalHeight() { return 0; }
        @Override public double getMarginLeft() { return 0; }
        @Override public void setMarginLeft(double v) {}
        @Override public double getMarginRight() { return 0; }
        @Override public void setMarginRight(double v) {}
        @Override public double getMarginTop() { return 0; }
        @Override public void setMarginTop(double v) {}
        @Override public double getMarginBottom() { return 0; }
        @Override public void setMarginBottom(double v) {}
        @Override public TextVerticalType getTextVerticalType() { return TextVerticalType.HORIZONTAL; }
        @Override public void setTextVerticalType(TextVerticalType v) {}
        @Override public TextAnchorType getTextAnchorType() { return TextAnchorType.NOT_DEFINED; }
        @Override public void setTextAnchorType(TextAnchorType v) {}
        @Override public boolean isAnchorCenter() { return false; }
        @Override public void setAnchorCenter(boolean v) {}
        @Override public IRow getFirstRow() { return null; }
        @Override public IColumn getFirstColumn() { return null; }
        @Override public int getColSpan() { return 1; }
        @Override public int getRowSpan() { return 1; }
        @Override public ITextFrame getTextFrame() { return null; }
        @Override public ITable getTable() { return null; }
        @Override public boolean isMergedCell() { return false; }
        @Override public ICellFormat getCellFormat() { return null; }
        @Override public IBaseSlide getSlide() { return null; }
        @Override public IPresentation getPresentation() { return null; }
        @Override public ISlideComponent getAsISlideComponent() { return null; }
        @Override public IPresentationComponent getAsIPresentationComponent() { return this; }
        @Override public IPresentationComponent asIPresentationComponent() { return this; }
    }
}
