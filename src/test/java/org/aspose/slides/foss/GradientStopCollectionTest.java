package org.aspose.slides.foss;

import org.aspose.slides.foss.drawing.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for {@link GradientStopCollection}: add, insert, removeAt, clear,
 * asICollection, asIEnumerable, and index access.
 *
 * <p>Covers gradient stop persistence, removal by index, collection append,
 * and indexed collection operations.</p>
 */
class GradientStopCollectionTest {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";

    private Document doc;
    private Element gsLst;
    private int saveCount;

    @BeforeEach
    void setUp() throws Exception {
        doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .newDocument();
        gsLst = doc.createElementNS(NS_A, "a:gsLst");
        doc.appendChild(gsLst);
        saveCount = 0;
    }

    private GradientStopCollection createCollection() {
        return new GradientStopCollection(gsLst, () -> saveCount++);
    }

    // --- add ---

    @Test
    void add_withColor_increasesSize() {
        var coll = createCollection();

        coll.add(0.0, Color.BLUE);
        coll.add(1.0, Color.RED);

        assertThat(coll.size()).isEqualTo(2);
    }

    @Test
    void add_positionPersists() {
        var coll = createCollection();

        coll.add(0.0, Color.BLUE);
        coll.add(1.0, Color.RED);

        assertThat(coll.get(0).getPosition()).isCloseTo(0.0, org.assertj.core.data.Offset.offset(0.001));
        assertThat(coll.get(1).getPosition()).isCloseTo(1.0, org.assertj.core.data.Offset.offset(0.001));
    }

    @Test
    void add_returnsNewStop() {
        var coll = createCollection();

        IGradientStop stop = coll.add(0.5, Color.GREEN);

        assertThat(stop).isNotNull();
        assertThat(stop.getPosition()).isCloseTo(0.5, org.assertj.core.data.Offset.offset(0.001));
    }

    @Test
    void add_withPresetColor_increasesSize() {
        var coll = createCollection();

        coll.add(0.0, PresetColor.ALICE_BLUE);

        assertThat(coll.size()).isEqualTo(1);
    }

    @Test
    void add_withSchemeColor_increasesSize() {
        var coll = createCollection();

        coll.add(0.0, SchemeColor.ACCENT1);

        assertThat(coll.size()).isEqualTo(1);
    }

    // --- add invokes save callback ---

    @Test
    void add_invokesSaveCallback() {
        var coll = createCollection();

        coll.add(0.0, Color.BLUE);

        assertThat(saveCount).isGreaterThan(0);
    }

    // --- insert ---

    @Test
    void insert_placesAtCorrectIndex() {
        var coll = createCollection();
        coll.add(0.0, Color.BLUE);
        coll.add(1.0, Color.RED);

        coll.insert(1, 0.5, Color.GREEN);

        assertThat(coll.size()).isEqualTo(3);
        assertThat(coll.get(1).getPosition()).isCloseTo(0.5, org.assertj.core.data.Offset.offset(0.001));
    }

    @Test
    void insert_atBeginning() {
        var coll = createCollection();
        coll.add(0.5, Color.GREEN);

        coll.insert(0, 0.0, Color.BLUE);

        assertThat(coll.size()).isEqualTo(2);
        assertThat(coll.get(0).getPosition()).isCloseTo(0.0, org.assertj.core.data.Offset.offset(0.001));
        assertThat(coll.get(1).getPosition()).isCloseTo(0.5, org.assertj.core.data.Offset.offset(0.001));
    }

    @Test
    void insert_beyondSize_appends() {
        var coll = createCollection();
        coll.add(0.0, Color.BLUE);

        coll.insert(10, 1.0, Color.RED);

        assertThat(coll.size()).isEqualTo(2);
        assertThat(coll.get(1).getPosition()).isCloseTo(1.0, org.assertj.core.data.Offset.offset(0.001));
    }

    @Test
    void insert_withPresetColor() {
        var coll = createCollection();
        coll.add(0.0, Color.BLUE);

        coll.insert(0, 0.25, PresetColor.GOLD);

        assertThat(coll.size()).isEqualTo(2);
    }

    @Test
    void insert_withSchemeColor() {
        var coll = createCollection();
        coll.add(0.0, Color.BLUE);

        coll.insert(0, 0.25, SchemeColor.ACCENT2);

        assertThat(coll.size()).isEqualTo(2);
    }

    @Test
    void insert_invokesSaveCallback() {
        var coll = createCollection();
        coll.add(0.0, Color.BLUE);
        saveCount = 0;

        coll.insert(0, 0.5, Color.RED);

        assertThat(saveCount).isGreaterThan(0);
    }

    // --- removeAt ---

    @Test
    void removeAt_removesByIndex() {
        var coll = createCollection();
        coll.add(0.0, Color.BLUE);
        coll.add(1.0, Color.RED);

        coll.removeAt(0);

        assertThat(coll.size()).isEqualTo(1);
    }

    @Test
    void removeAt_middleElement_preservesOthers() {
        var coll = createCollection();
        coll.add(0.0, Color.BLUE);
        coll.add(0.5, Color.GREEN);
        coll.add(1.0, Color.RED);
        assertThat(coll.size()).isEqualTo(3);

        coll.removeAt(1);

        assertThat(coll.size()).isEqualTo(2);
        assertThat(coll.get(0).getPosition()).isCloseTo(0.0, org.assertj.core.data.Offset.offset(0.001));
        assertThat(coll.get(1).getPosition()).isCloseTo(1.0, org.assertj.core.data.Offset.offset(0.001));
    }

    @Test
    void removeAt_outOfRange_noOp() {
        var coll = createCollection();
        coll.add(0.0, Color.BLUE);

        coll.removeAt(5);

        assertThat(coll.size()).isEqualTo(1);
    }

    @Test
    void removeAt_negativeIndex_noOp() {
        var coll = createCollection();
        coll.add(0.0, Color.BLUE);

        coll.removeAt(-1);

        assertThat(coll.size()).isEqualTo(1);
    }

    @Test
    void removeAt_invokesSaveCallback() {
        var coll = createCollection();
        coll.add(0.0, Color.BLUE);
        saveCount = 0;

        coll.removeAt(0);

        assertThat(saveCount).isGreaterThan(0);
    }

    // --- clear ---

    @Test
    void clear_removesAllStops() {
        var coll = createCollection();
        coll.add(0.0, Color.BLUE);
        coll.add(0.5, Color.GREEN);
        coll.add(1.0, Color.RED);

        coll.clear();

        assertThat(coll.size()).isEqualTo(0);
    }

    @Test
    void clear_emptyCollection_noOp() {
        var coll = createCollection();

        coll.clear();

        assertThat(coll.size()).isEqualTo(0);
    }

    // --- get / index access ---

    @Test
    void get_outOfBounds_throwsException() {
        var coll = createCollection();

        assertThatThrownBy(() -> coll.get(0))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void get_negativeIndex_throwsException() {
        var coll = createCollection();
        coll.add(0.0, Color.BLUE);

        assertThatThrownBy(() -> coll.get(-1))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    // --- asICollection / asIEnumerable ---

    @Test
    void asICollection_returnsAllStops() {
        var coll = createCollection();
        coll.add(0.0, Color.BLUE);
        coll.add(0.5, Color.GREEN);
        coll.add(1.0, Color.RED);

        List<IGradientStop> list = coll.asICollection();

        assertThat(list).hasSize(3);
        assertThat(list.get(0).getPosition()).isCloseTo(0.0, org.assertj.core.data.Offset.offset(0.001));
        assertThat(list.get(2).getPosition()).isCloseTo(1.0, org.assertj.core.data.Offset.offset(0.001));
    }

    @Test
    void asICollection_emptyCollection_returnsEmptyList() {
        var coll = createCollection();

        List<IGradientStop> list = coll.asICollection();

        assertThat(list).isEmpty();
    }

    @Test
    void asIEnumerable_returnsAllStops() {
        var coll = createCollection();
        coll.add(0.0, Color.BLUE);
        coll.add(1.0, Color.RED);

        int count = 0;
        for (IGradientStop stop : coll.asIEnumerable()) {
            count++;
        }

        assertThat(count).isEqualTo(2);
    }

    // --- size on empty collection ---

    @Test
    void size_emptyCollection_returnsZero() {
        var coll = createCollection();

        assertThat(coll.size()).isEqualTo(0);
    }
}
