package org.aspose.slides.foss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for {@link AdjustValueCollection}.
 */
class AdjustValueCollectionTest {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";

    private Document doc;

    @BeforeEach
    void setUp() throws Exception {
        doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .newDocument();
    }

    private Element createAvLstWithGds(int count) {
        Element avLst = doc.createElementNS(NS_A, "a:avLst");
        doc.appendChild(avLst);
        for (int i = 0; i < count; i++) {
            Element gd = doc.createElementNS(NS_A, "a:gd");
            gd.setAttribute("name", "adj" + (i + 1));
            gd.setAttribute("fmla", "val " + ((i + 1) * 10000));
            avLst.appendChild(gd);
        }
        return avLst;
    }

    // --- size ---

    @Test
    void size_returnsZeroWhenNoBackingElement() {
        var collection = new AdjustValueCollection();
        assertThat(collection.size()).isEqualTo(0);
    }

    @Test
    void size_returnsZeroForEmptyAvLst() {
        Element avLst = doc.createElementNS(NS_A, "a:avLst");
        doc.appendChild(avLst);
        var collection = new AdjustValueCollection();
        collection.initInternal(avLst, null);
        assertThat(collection.size()).isEqualTo(0);
    }

    @Test
    void size_returnsCorrectCount() {
        Element avLst = createAvLstWithGds(3);
        var collection = new AdjustValueCollection();
        collection.initInternal(avLst, null);
        assertThat(collection.size()).isEqualTo(3);
    }

    // --- get ---

    @Test
    void get_returnsCorrectAdjustValue() {
        Element avLst = createAvLstWithGds(2);
        var collection = new AdjustValueCollection();
        collection.initInternal(avLst, null);

        IAdjustValue first = collection.get(0);
        assertThat(first.getName()).isEqualTo("adj1");
        assertThat(first.getRawValue()).isEqualTo(10000);

        IAdjustValue second = collection.get(1);
        assertThat(second.getName()).isEqualTo("adj2");
        assertThat(second.getRawValue()).isEqualTo(20000);
    }

    @Test
    void get_throwsOnNegativeIndex() {
        Element avLst = createAvLstWithGds(1);
        var collection = new AdjustValueCollection();
        collection.initInternal(avLst, null);

        assertThatThrownBy(() -> collection.get(-1))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void get_throwsOnOutOfRangeIndex() {
        Element avLst = createAvLstWithGds(2);
        var collection = new AdjustValueCollection();
        collection.initInternal(avLst, null);

        assertThatThrownBy(() -> collection.get(2))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    // --- getGdElements ---

    @Test
    void getGdElements_returnsEmptyListWhenNoBackingElement() {
        var collection = new AdjustValueCollection();
        assertThat(collection.getGdElements()).isEmpty();
    }

    @Test
    void getGdElements_returnsElements() {
        Element avLst = createAvLstWithGds(3);
        var collection = new AdjustValueCollection();
        collection.initInternal(avLst, null);
        assertThat(collection.getGdElements()).hasSize(3);
    }

    // --- initInternal ---

    @Test
    void initInternal_returnsSelf() {
        var collection = new AdjustValueCollection();
        Element avLst = createAvLstWithGds(1);
        AdjustValueCollection result = collection.initInternal(avLst, null);
        assertThat(result).isSameAs(collection);
    }

    // --- asICollection ---

    @Test
    void asICollection_returnsAllValues() {
        Element avLst = createAvLstWithGds(3);
        var collection = new AdjustValueCollection();
        collection.initInternal(avLst, null);

        List<IAdjustValue> list = collection.asICollection();
        assertThat(list).hasSize(3);
        assertThat(list.get(0).getName()).isEqualTo("adj1");
        assertThat(list.get(2).getName()).isEqualTo("adj3");
    }

    @Test
    void asICollection_returnsUnmodifiableList() {
        Element avLst = createAvLstWithGds(1);
        var collection = new AdjustValueCollection();
        collection.initInternal(avLst, null);

        List<IAdjustValue> list = collection.asICollection();
        assertThatThrownBy(() -> list.add(new AdjustValue()))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    // --- asIEnumerable ---

    @Test
    void asIEnumerable_returnsIterable() {
        Element avLst = createAvLstWithGds(2);
        var collection = new AdjustValueCollection();
        collection.initInternal(avLst, null);

        int count = 0;
        for (IAdjustValue av : collection.asIEnumerable()) {
            count++;
        }
        assertThat(count).isEqualTo(2);
    }

    // --- iterator ---

    @Test
    void iterator_worksInForEach() {
        Element avLst = createAvLstWithGds(2);
        var collection = new AdjustValueCollection();
        collection.initInternal(avLst, null);

        int count = 0;
        for (IAdjustValue av : collection) {
            assertThat(av.getName()).startsWith("adj");
            count++;
        }
        assertThat(count).isEqualTo(2);
    }
}
