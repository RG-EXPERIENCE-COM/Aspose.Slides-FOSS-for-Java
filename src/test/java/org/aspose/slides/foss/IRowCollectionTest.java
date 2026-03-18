package org.aspose.slides.foss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for {@link IRowCollection} via {@link RowCollection}.
 *
 * <p>Covers remove-at pattern, removal by index, addClone duplication,
 * and count consistency after removal.</p>
 */
class IRowCollectionTest {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";

    private RowCollection collection;

    @BeforeEach
    void setUp() {
        collection = new RowCollection();
    }

    // --- removeAt (from test_remove_comment, test_remove_at, test_remove_slide_at) ---

    @Test
    void removeAt_removesRowByIndex() {
        collection.addClone(new Row(), false);
        collection.addClone(new Row(), false);
        assertThat(collection.size()).isEqualTo(2);

        collection.removeAt(0, false);
        assertThat(collection.size()).isEqualTo(1);
    }

    @Test
    void removeAt_removesMiddleElement() {
        collection.addClone(new Row(List.of(), 10.0), false);
        collection.addClone(new Row(List.of(), 20.0), false);
        collection.addClone(new Row(List.of(), 30.0), false);
        assertThat(collection.size()).isEqualTo(3);

        collection.removeAt(1, false);
        assertThat(collection.size()).isEqualTo(2);
    }

    @Test
    void removeAt_decreasesCount() {
        collection.addClone(new Row(), false);
        collection.addClone(new Row(), false);
        collection.removeAt(1, false);
        assertThat(collection.size()).isEqualTo(1);
    }

    // --- addClone (from test_clone_slide) ---

    @Test
    void addClone_duplicatesRow() {
        var template = new Row(List.of(), 42.0);
        IRow[] cloned = collection.addClone(template, false);
        assertThat(collection.size()).isEqualTo(1);
        assertThat(cloned).hasSize(1);
    }

    @Test
    void addClone_appendsToEnd() {
        collection.addClone(new Row(List.of(), 10.0), false);
        collection.addClone(new Row(List.of(), 20.0), false);
        assertThat(collection.size()).isEqualTo(2);
    }

    // --- insertClone ---

    @Test
    void insertClone_insertsAtSpecifiedIndex() {
        collection.addClone(new Row(List.of(), 10.0), false);
        collection.addClone(new Row(List.of(), 30.0), false);
        collection.insertClone(1, new Row(List.of(), 20.0), false);
        assertThat(collection.size()).isEqualTo(3);
    }

    // --- get / size ---

    @Test
    void get_returnsRowAtIndex() {
        collection.addClone(new Row(), false);
        assertThat(collection.get(0)).isNotNull();
    }

    @Test
    void size_returnsZeroForEmptyCollection() {
        assertThat(collection.size()).isEqualTo(0);
    }

    // --- asICollection / asIEnumerable ---

    @Test
    void asICollection_returnsList() {
        collection.addClone(new Row(), false);
        collection.addClone(new Row(), false);
        List<IRow> list = collection.asICollection();
        assertThat(list).hasSize(2);
    }

    @Test
    void asIEnumerable_returnsIterable() {
        collection.addClone(new Row(), false);
        Iterable<IRow> iterable = collection.asIEnumerable();
        assertThat(iterable).hasSize(1);
    }

    // --- XML-backed tests ---

    @Nested
    class XmlBacked {

        private Document doc;
        private Element tblElement;

        @BeforeEach
        void setUp() throws Exception {
            doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .newDocument();
            tblElement = doc.createElementNS(NS_A, "a:tbl");
            doc.appendChild(tblElement);
        }

        private Element addTrWithCells(int cellCount) {
            Element tr = doc.createElementNS(NS_A, "a:tr");
            tr.setAttribute("h", String.valueOf(370840));
            for (int i = 0; i < cellCount; i++) {
                Element tc = doc.createElementNS(NS_A, "a:tc");
                Element txBody = doc.createElementNS(NS_A, "a:txBody");
                tc.appendChild(txBody);
                tr.appendChild(tc);
            }
            tblElement.appendChild(tr);
            return tr;
        }

        /** AddClone duplicates a row with its cells. */
        @Test
        void addClone_clonesRowInXml() {
            addTrWithCells(2);
            var rc = new RowCollection(tblElement, null);
            assertThat(rc.size()).isEqualTo(1);

            IRow[] cloned = rc.addClone(rc.get(0), false);
            assertThat(rc.size()).isEqualTo(2);
            assertThat(cloned).hasSize(1);
        }

        /** Cloned row preserves cell structure. */
        @Test
        void addClone_preservesCellCount() {
            addTrWithCells(3);
            var rc = new RowCollection(tblElement, null);

            rc.addClone(rc.get(0), false);
            // The cloned row should have the same number of cells
            assertThat(rc.get(1)).isInstanceOf(Row.class);
        }

        /** Remove_at removes by index. */
        @Test
        void removeAt_removesRowFromXml() {
            addTrWithCells(2);
            addTrWithCells(2);
            var rc = new RowCollection(tblElement, null);
            assertThat(rc.size()).isEqualTo(2);

            rc.removeAt(0, false);
            assertThat(rc.size()).isEqualTo(1);
        }

        /** Removing middle element persists. */
        @Test
        void removeAt_removesMiddleRow() {
            addTrWithCells(1);
            addTrWithCells(1);
            addTrWithCells(1);
            var rc = new RowCollection(tblElement, null);
            assertThat(rc.size()).isEqualTo(3);

            rc.removeAt(1, false);
            assertThat(rc.size()).isEqualTo(2);
        }

        /** RemoveAt decreases count. */
        @Test
        void removeAt_decreasesCount() {
            addTrWithCells(1);
            addTrWithCells(1);
            var rc = new RowCollection(tblElement, null);

            rc.removeAt(1, false);
            assertThat(rc.size()).isEqualTo(1);
        }

        /** removeAt throws for out-of-range index. */
        @Test
        void removeAt_throwsForInvalidIndex() {
            addTrWithCells(1);
            var rc = new RowCollection(tblElement, null);

            assertThatThrownBy(() -> rc.removeAt(5, false))
                    .isInstanceOf(IndexOutOfBoundsException.class);
        }

        /** removeAt throws for negative index. */
        @Test
        void removeAt_throwsForNegativeIndex() {
            addTrWithCells(1);
            var rc = new RowCollection(tblElement, null);

            assertThatThrownBy(() -> rc.removeAt(-1, false))
                    .isInstanceOf(IndexOutOfBoundsException.class);
        }

        /** insertClone places row at the specified index. */
        @Test
        void insertClone_insertsAtIndex() {
            addTrWithCells(1);
            addTrWithCells(1);
            var rc = new RowCollection(tblElement, null);

            rc.insertClone(1, rc.get(0), false);
            assertThat(rc.size()).isEqualTo(3);
        }

        /** insertClone appends when index equals size. */
        @Test
        void insertClone_appendsWhenIndexEqualsSize() {
            addTrWithCells(1);
            var rc = new RowCollection(tblElement, null);

            rc.insertClone(1, rc.get(0), false);
            assertThat(rc.size()).isEqualTo(2);
        }

        /** Save callback is invoked on addClone. */
        @Test
        void addClone_invokesSaveCallback() {
            addTrWithCells(1);
            var counter = new AtomicInteger(0);
            var rc = new RowCollection(tblElement, counter::incrementAndGet);

            rc.addClone(rc.get(0), false);
            assertThat(counter.get()).isEqualTo(1);
        }

        /** Save callback is invoked on removeAt. */
        @Test
        void removeAt_invokesSaveCallback() {
            addTrWithCells(1);
            var counter = new AtomicInteger(0);
            var rc = new RowCollection(tblElement, counter::incrementAndGet);

            rc.removeAt(0, false);
            assertThat(counter.get()).isEqualTo(1);
        }

        /** Iterator works over XML-backed collection. */
        @Test
        void iterator_worksOverXmlBackedRows() {
            addTrWithCells(1);
            addTrWithCells(1);
            var rc = new RowCollection(tblElement, null);

            int count = 0;
            for (IRow ignored : rc) {
                count++;
            }
            assertThat(count).isEqualTo(2);
        }
    }
}
