package org.aspose.slides.foss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for {@link ColumnCollection}.
 *
 * <p>Covers collection CRUD operations including add, remove, clone, and index access.</p>
 */
class ColumnCollectionTest {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";
    private static final double EMU_PER_POINT = 12700.0;

    private Document doc;

    @BeforeEach
    void setUp() throws Exception {
        doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .getDOMImplementation()
                .createDocument(null, "root", null);
    }

    /**
     * Creates a minimal table structure: {@code <a:tbl>} containing
     * {@code <a:tblGrid>} with the given number of columns and rows,
     * each row having one {@code <a:tc>} per column.
     */
    private record TableElements(Element tbl, Element tblGrid) {}

    private TableElements createTable(int numCols, int numRows) {
        Element tbl = doc.createElementNS(NS_A, "a:tbl");
        doc.getDocumentElement().appendChild(tbl);

        Element tblGrid = doc.createElementNS(NS_A, "a:tblGrid");
        tbl.appendChild(tblGrid);

        // Create gridCol elements
        for (int c = 0; c < numCols; c++) {
            Element gridCol = doc.createElementNS(NS_A, "a:gridCol");
            gridCol.setAttribute("w", String.valueOf(Math.round((100.0 + c * 50) * EMU_PER_POINT)));
            tblGrid.appendChild(gridCol);
        }

        // Create rows with tc elements
        for (int r = 0; r < numRows; r++) {
            Element tr = doc.createElementNS(NS_A, "a:tr");
            tbl.appendChild(tr);
            for (int c = 0; c < numCols; c++) {
                Element tc = doc.createElementNS(NS_A, "a:tc");
                // Add minimal content to identify cells
                Element txBody = doc.createElementNS(NS_A, "a:txBody");
                tc.appendChild(txBody);
                Element tcPr = doc.createElementNS(NS_A, "a:tcPr");
                tc.appendChild(tcPr);
                tr.appendChild(tc);
            }
        }

        return new TableElements(tbl, tblGrid);
    }

    // --- basic construction ---

    @Test
    void emptyCollection_hasSizeZero() {
        var collection = new ColumnCollection();
        assertThat(collection.size()).isEqualTo(0);
    }

    @Test
    void constructorWithColumns_reportsCorrectSize() {
        var table = createTable(3, 2);
        var collection = new ColumnCollection(table.tbl(), table.tblGrid(), null);
        assertThat(collection.size()).isEqualTo(3);
    }

    @Test
    void get_returnsColumnAtIndex() {
        var table = createTable(2, 1);
        var collection = new ColumnCollection(table.tbl(), table.tblGrid(), null);
        assertThat(collection.get(0)).isNotNull();
        assertThat(collection.get(1)).isNotNull();
    }

    @Test
    void get_outOfBounds_throwsException() {
        var collection = new ColumnCollection();
        assertThatThrownBy(() -> collection.get(0))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    // --- removeAt ---

    @Test
    void removeAt_removesByIndex() {
        var table = createTable(2, 1);
        var collection = new ColumnCollection(table.tbl(), table.tblGrid(), null);
        assertThat(collection.size()).isEqualTo(2);
        collection.removeAt(0, false);
        assertThat(collection.size()).isEqualTo(1);
    }

    @Test
    void removeAt_removesMiddleElement() {
        var table = createTable(3, 2);
        var collection = new ColumnCollection(table.tbl(), table.tblGrid(), null);
        assertThat(collection.size()).isEqualTo(3);

        collection.removeAt(1, false);
        assertThat(collection.size()).isEqualTo(2);
    }

    @Test
    void removeAt_persistsInXml() {
        var table = createTable(3, 2);
        var collection = new ColumnCollection(table.tbl(), table.tblGrid(), null);

        collection.removeAt(1, false);

        // Re-parse from the same XML to verify persistence
        var collection2 = new ColumnCollection(table.tbl(), table.tblGrid(), null);
        assertThat(collection2.size()).isEqualTo(2);
    }

    @Test
    void removeAt_negativeIndex_throwsException() {
        var table = createTable(2, 1);
        var collection = new ColumnCollection(table.tbl(), table.tblGrid(), null);
        assertThatThrownBy(() -> collection.removeAt(-1, false))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void removeAt_indexOutOfRange_throwsException() {
        var table = createTable(2, 1);
        var collection = new ColumnCollection(table.tbl(), table.tblGrid(), null);
        assertThatThrownBy(() -> collection.removeAt(5, false))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void removeAt_removesTcFromEachRow() {
        var table = createTable(3, 2);
        var collection = new ColumnCollection(table.tbl(), table.tblGrid(), null);

        collection.removeAt(0, false);

        // Verify each row now has 2 tc elements
        var rows = table.tbl().getElementsByTagNameNS(NS_A, "tr");
        for (int i = 0; i < rows.getLength(); i++) {
            Element tr = (Element) rows.item(i);
            var tcs = tr.getElementsByTagNameNS(NS_A, "tc");
            assertThat(tcs.getLength()).isEqualTo(2);
        }
    }

    @Test
    void removeAt_invokesSaveCallback() {
        var table = createTable(2, 1);
        AtomicBoolean saved = new AtomicBoolean(false);
        var collection = new ColumnCollection(table.tbl(), table.tblGrid(), () -> saved.set(true));
        collection.removeAt(0, false);
        assertThat(saved).isTrue();
    }

    // --- addClone ---

    @Test
    void addClone_duplicatesColumnWithCells() {
        var table = createTable(1, 2);
        var collection = new ColumnCollection(table.tbl(), table.tblGrid(), null);
        assertThat(collection.size()).isEqualTo(1);

        IColumn template = collection.get(0);
        List<IColumn> result = collection.addClone(template, false);

        assertThat(collection.size()).isEqualTo(2);
        assertThat(result).hasSize(1);
    }

    @Test
    void addClone_appendsToEnd() {
        var table = createTable(2, 1);
        var collection = new ColumnCollection(table.tbl(), table.tblGrid(), null);

        collection.addClone(collection.get(0), false);

        assertThat(collection.size()).isEqualTo(3);
        // The new gridCol should be last in the XML
        var gridCols = table.tblGrid().getElementsByTagNameNS(NS_A, "gridCol");
        assertThat(gridCols.getLength()).isEqualTo(3);
    }

    @Test
    void addClone_clonesCellsInEachRow() {
        var table = createTable(2, 3);
        var collection = new ColumnCollection(table.tbl(), table.tblGrid(), null);

        collection.addClone(collection.get(0), false);

        // Each row should now have 3 tc elements
        var rows = table.tbl().getElementsByTagNameNS(NS_A, "tr");
        for (int i = 0; i < rows.getLength(); i++) {
            Element tr = (Element) rows.item(i);
            var tcs = tr.getElementsByTagNameNS(NS_A, "tc");
            assertThat(tcs.getLength()).isEqualTo(3);
        }
    }

    @Test
    void addClone_invokesSaveCallback() {
        var table = createTable(1, 1);
        AtomicBoolean saved = new AtomicBoolean(false);
        var collection = new ColumnCollection(table.tbl(), table.tblGrid(), () -> saved.set(true));
        collection.addClone(collection.get(0), false);
        assertThat(saved).isTrue();
    }

    // --- insertClone ---

    @Test
    void insertClone_insertsAtIndex() {
        var table = createTable(2, 1);
        var collection = new ColumnCollection(table.tbl(), table.tblGrid(), null);

        collection.insertClone(1, collection.get(0), false);

        assertThat(collection.size()).isEqualTo(3);
    }

    @Test
    void insertClone_insertsAtBeginning() {
        var table = createTable(2, 1);
        var collection = new ColumnCollection(table.tbl(), table.tblGrid(), null);

        collection.insertClone(0, collection.get(1), false);

        assertThat(collection.size()).isEqualTo(3);
    }

    @Test
    void insertClone_clonesCellsInEachRow() {
        var table = createTable(2, 2);
        var collection = new ColumnCollection(table.tbl(), table.tblGrid(), null);

        collection.insertClone(1, collection.get(0), false);

        var rows = table.tbl().getElementsByTagNameNS(NS_A, "tr");
        for (int i = 0; i < rows.getLength(); i++) {
            Element tr = (Element) rows.item(i);
            var tcs = tr.getElementsByTagNameNS(NS_A, "tc");
            assertThat(tcs.getLength()).isEqualTo(3);
        }
    }

    // --- asICollection ---

    @Test
    void asICollection_returnsListCopy() {
        var table = createTable(2, 1);
        var collection = new ColumnCollection(table.tbl(), table.tblGrid(), null);

        List<IColumn> list = collection.asICollection();
        assertThat(list).hasSize(2);
        assertThat(list.get(0)).isSameAs(collection.get(0));
        assertThat(list.get(1)).isSameAs(collection.get(1));
    }

    @Test
    void asICollection_returnsEmptyListForEmptyCollection() {
        var collection = new ColumnCollection();
        assertThat(collection.asICollection()).isEmpty();
    }

    // --- asIEnumerable ---

    @Test
    void asIEnumerable_isIterable() {
        var table = createTable(3, 1);
        var collection = new ColumnCollection(table.tbl(), table.tblGrid(), null);

        int count = 0;
        for (var col : collection.asIEnumerable()) {
            assertThat(col).isNotNull();
            count++;
        }
        assertThat(count).isEqualTo(3);
    }

    @Test
    void asIEnumerable_emptyCollection() {
        var collection = new ColumnCollection();
        int count = 0;
        for (var ignored : collection.asIEnumerable()) {
            count++;
        }
        assertThat(count).isEqualTo(0);
    }

    // --- iterator ---

    @Test
    void iterator_iteratesAllColumns() {
        var table = createTable(2, 1);
        var collection = new ColumnCollection(table.tbl(), table.tblGrid(), null);

        int count = 0;
        for (var col : collection) {
            assertThat(col).isNotNull();
            count++;
        }
        assertThat(count).isEqualTo(2);
    }

    // --- non-XML backed collection ---

    @Test
    void nonXmlBacked_removeAt_removesColumn() {
        var stub1 = new ColumnStub();
        var stub2 = new ColumnStub();
        var collection = new ColumnCollection(List.of(stub1, stub2));
        collection.removeAt(0, false);
        assertThat(collection.size()).isEqualTo(1);
        assertThat(collection.get(0)).isSameAs(stub2);
    }

    @Test
    void nonXmlBacked_removeAt_outOfBounds_throwsException() {
        var collection = new ColumnCollection(List.of(new ColumnStub()));
        assertThatThrownBy(() -> collection.removeAt(5, false))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    /**
     * Minimal IColumn stub for testing non-XML-backed ColumnCollection.
     */
    private static class ColumnStub implements IColumn {
        @Override public double getWidth() { return 0; }
        @Override public void setWidth(double value) {}
        @Override public ICellCollection getAsICellCollection() { return this; }
        @Override public IBulkTextFormattable getAsIBulkTextFormattable() { return this; }
        @Override public IColumnFormat getColumnFormat() { return null; }
        @Override public ICell get(int index) { return null; }
        @Override public int size() { return 0; }
        @Override public List<ICell> asICollection() { return List.of(); }
        @Override public Iterable<ICell> asIEnumerable() { return List.of(); }
        @Override public void setTextFormat(Object source) {}
        @Override public IBaseSlide getSlide() { return null; }
        @Override public IPresentation getPresentation() { return null; }
        @Override public ISlideComponent asISlideComponent() { return this; }
        @Override public IPresentationComponent asIPresentationComponent() { return null; }
    }
}
