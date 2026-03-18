package org.aspose.slides.foss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.data.Offset.offset;

/**
 * Tests for {@link Row}: height, minimalHeight, rowFormat, cell collection,
 * bulk text formatting, XML-backed behaviour.
 *
 * <p>Covers row height, format, cell collection, and bulk text formatting.</p>
 */
class RowTest {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";
    private static final double EMU_PER_POINT = 12700.0;

    private Document doc;

    @BeforeEach
    void setUp() throws Exception {
        doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .newDocument();
    }

    private Element createTr(double heightPts) {
        Element tr = doc.createElementNS(NS_A, "a:tr");
        tr.setAttribute("h", String.valueOf(Math.round(heightPts * EMU_PER_POINT)));
        doc.appendChild(tr);
        return tr;
    }

    // --- height (plain constructor) ---

    @Test
    void height_returnsConstructorValue() {
        var row = new Row(List.of(), 50.0);
        assertThat(row.getHeight()).isEqualTo(50.0);
    }

    @Test
    void height_returnsZeroForDefaultRow() {
        var row = new Row();
        assertThat(row.getHeight()).isEqualTo(0.0);
    }

    // --- height (XML-backed) ---

    @Test
    void height_readsFromTrElement() {
        Element tr = createTr(75.0);
        var row = new Row(tr, List.of(), null);
        assertThat(row.getHeight()).isCloseTo(75.0, offset(0.001));
    }

    @Test
    void height_returnsZeroWhenAttributeMissing() {
        Element tr = doc.createElementNS(NS_A, "a:tr");
        doc.appendChild(tr);
        var row = new Row(tr, List.of(), null);
        assertThat(row.getHeight()).isEqualTo(0.0);
    }

    // --- minimalHeight (plain constructor) ---

    @Test
    void minimalHeight_defaultsToHeight() {
        var row = new Row(List.of(), 40.0);
        assertThat(row.getMinimalHeight()).isEqualTo(40.0);
    }

    @Test
    void minimalHeight_canBeSet() {
        var row = new Row(List.of(), 40.0);
        row.setMinimalHeight(20.0);
        assertThat(row.getMinimalHeight()).isEqualTo(20.0);
    }

    // --- minimalHeight (XML-backed) ---

    @Test
    void minimalHeight_readsFromTrElement() {
        Element tr = createTr(60.0);
        var row = new Row(tr, List.of(), null);
        assertThat(row.getMinimalHeight()).isCloseTo(60.0, offset(0.001));
    }

    @Test
    void minimalHeight_setUpdatesElement() {
        Element tr = createTr(50.0);
        var row = new Row(tr, List.of(), null);
        row.setMinimalHeight(80.0);
        assertThat(row.getMinimalHeight()).isCloseTo(80.0, offset(0.001));
        // height should also reflect the change since they share the h attribute
        assertThat(row.getHeight()).isCloseTo(80.0, offset(0.001));
    }

    @Test
    void minimalHeight_setInvokesSaveCallback() {
        Element tr = createTr(50.0);
        AtomicBoolean saved = new AtomicBoolean(false);
        var row = new Row(tr, List.of(), () -> saved.set(true));
        row.setMinimalHeight(100.0);
        assertThat(saved).isTrue();
    }

    // --- rowFormat ---

    @Test
    void rowFormat_returnsNonNull() {
        var row = new Row();
        assertThat(row.getRowFormat()).isNotNull();
    }

    @Test
    void rowFormat_returnsRowFormatInstance() {
        var row = new Row();
        assertThat(row.getRowFormat()).isInstanceOf(RowFormat.class);
    }

    // --- asICellCollection ---

    @Test
    void asICellCollection_returnsSelf() {
        var row = new Row();
        assertThat(row.getAsICellCollection()).isSameAs(row);
    }

    // --- asIBulkTextFormattable ---

    @Test
    void asIBulkTextFormattable_returnsSelf() {
        var row = new Row();
        assertThat(row.getAsIBulkTextFormattable()).isSameAs(row);
    }

    // --- setTextFormat ---

    @Test
    void setTextFormat_throwsOnNullSource() {
        var row = new Row();
        assertThatThrownBy(() -> row.setTextFormat(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void setTextFormat_acceptsNonNullSource() {
        var row = new Row();
        row.setTextFormat(new Object());
    }

    // --- cell access ---

    @Test
    void size_returnsNumberOfCells() {
        var stubCell = new CellStub();
        var row = new Row(List.of(stubCell), 30.0);
        assertThat(row.size()).isEqualTo(1);
    }

    @Test
    void get_returnsCellByIndex() {
        var stubCell = new CellStub();
        var row = new Row(List.of(stubCell), 30.0);
        assertThat(row.get(0)).isSameAs(stubCell);
    }

    // --- row heights match constructor arguments (from test_table.test_row_height) ---

    @Test
    void rowHeights_matchConstructorArguments() {
        var row1 = new Row(List.of(), 30.0);
        var row2 = new Row(List.of(), 50.0);
        var row3 = new Row(List.of(), 70.0);

        assertThat(row1.getHeight()).isEqualTo(30.0);
        assertThat(row2.getHeight()).isEqualTo(50.0);
        assertThat(row3.getHeight()).isEqualTo(70.0);
    }

    @Test
    void rowHeights_matchXmlBackedArguments() {
        Element tr1 = createTr(30.0);
        doc.removeChild(tr1);
        Element tr2 = createTr(50.0);
        doc.removeChild(tr2);
        Element tr3 = createTr(70.0);

        var row1 = new Row(tr1, List.of(), null);
        var row2 = new Row(tr2, List.of(), null);
        var row3 = new Row(tr3, List.of(), null);

        assertThat(row1.getHeight()).isCloseTo(30.0, offset(0.001));
        assertThat(row2.getHeight()).isCloseTo(50.0, offset(0.001));
        assertThat(row3.getHeight()).isCloseTo(70.0, offset(0.001));
    }

    /**
     * Minimal ICell stub for testing Row cell collection behavior.
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
