package org.aspose.slides.foss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ParagraphCollection} covering the full public contract:
 * add, insert, remove, removeAt, clear, contains, indexOf, count, isReadOnly,
 * asISlideComponent, asIPresentationComponent, asIEnumerable, slide, presentation, iterator.
 *
 * <p>Covers collection CRUD patterns including add, clear, insert, remove, and type creation.</p>
 */
class ParagraphCollectionTest {

    private ParagraphCollection collection;

    @BeforeEach
    void setUp() {
        collection = new ParagraphCollection();
    }

    // --- add ---

    @Test
    void add_appendsParagraphAndIncrementsCount() {
        var p1 = new Paragraph();
        p1.setText("First paragraph");
        collection.add(p1);
        assertThat(collection.count()).isEqualTo(1);
        assertThat(collection.get(0).getText()).isEqualTo("First paragraph");

        var p2 = new Paragraph();
        p2.setText("Second paragraph");
        collection.add(p2);
        assertThat(collection.count()).isEqualTo(2);
        assertThat(collection.get(1).getText()).isEqualTo("Second paragraph");
    }

    @Test
    void add_multipleParagraphsPreservesOrder() {
        var p1 = new Paragraph();
        p1.setText("A");
        var p2 = new Paragraph();
        p2.setText("B");
        var p3 = new Paragraph();
        p3.setText("C");
        collection.add(p1);
        collection.add(p2);
        collection.add(p3);

        assertThat(collection.size()).isEqualTo(3);
        assertThat(collection.get(0).getText()).isEqualTo("A");
        assertThat(collection.get(1).getText()).isEqualTo("B");
        assertThat(collection.get(2).getText()).isEqualTo("C");
    }

    // --- clear ---

    @Test
    void clear_removesAllParagraphs() {
        collection.add(new Paragraph());
        collection.add(new Paragraph());
        assertThat(collection.count()).isEqualTo(2);

        collection.clear();
        assertThat(collection.count()).isEqualTo(0);
        assertThat(collection.size()).isEqualTo(0);
    }

    @Test
    void clear_onEmptyCollection_doesNothing() {
        collection.clear();
        assertThat(collection.count()).isEqualTo(0);
    }

    // --- insert ---

    @Test
    void insert_placesAtCorrectIndex() {
        var first = new Paragraph();
        first.setText("First");
        var third = new Paragraph();
        third.setText("Third");
        collection.add(first);
        collection.add(third);

        var second = new Paragraph();
        second.setText("Second");
        collection.insert(1, second);

        assertThat(collection.size()).isEqualTo(3);
        assertThat(collection.get(0).getText()).isEqualTo("First");
        assertThat(collection.get(1).getText()).isEqualTo("Second");
        assertThat(collection.get(2).getText()).isEqualTo("Third");
    }

    @Test
    void insert_atEnd_appendsParagraph() {
        var p1 = new Paragraph();
        p1.setText("A");
        collection.add(p1);

        var p2 = new Paragraph();
        p2.setText("B");
        collection.insert(10, p2); // index beyond size

        assertThat(collection.size()).isEqualTo(2);
        assertThat(collection.get(1).getText()).isEqualTo("B");
    }

    @Test
    void insert_atZero_prependsParagraph() {
        var p1 = new Paragraph();
        p1.setText("Original");
        collection.add(p1);

        var p0 = new Paragraph();
        p0.setText("Prepended");
        collection.insert(0, p0);

        assertThat(collection.size()).isEqualTo(2);
        assertThat(collection.get(0).getText()).isEqualTo("Prepended");
        assertThat(collection.get(1).getText()).isEqualTo("Original");
    }

    // --- remove ---

    @Test
    void remove_removesParagraphByReference() {
        var p1 = new Paragraph();
        p1.setText("C1");
        var p2 = new Paragraph();
        p2.setText("C2");
        var p3 = new Paragraph();
        p3.setText("C3");
        collection.add(p1);
        collection.add(p2);
        collection.add(p3);
        assertThat(collection.size()).isEqualTo(3);

        boolean removed = collection.remove(p2);
        assertThat(removed).isTrue();
        assertThat(collection.size()).isEqualTo(2);
        assertThat(collection.get(0).getText()).isEqualTo("C1");
        assertThat(collection.get(1).getText()).isEqualTo("C3");
    }

    @Test
    void remove_nonExistentItem_returnsFalse() {
        var p1 = new Paragraph();
        collection.add(p1);
        var other = new Paragraph();

        assertThat(collection.remove(other)).isFalse();
        assertThat(collection.size()).isEqualTo(1);
    }

    // --- removeAt ---

    @Test
    void removeAt_removesMiddleElement() {
        var p1 = new Paragraph();
        p1.setText("C1");
        var p2 = new Paragraph();
        p2.setText("C2");
        var p3 = new Paragraph();
        p3.setText("C3");
        collection.add(p1);
        collection.add(p2);
        collection.add(p3);

        collection.removeAt(1);
        assertThat(collection.size()).isEqualTo(2);
        assertThat(collection.get(0).getText()).isEqualTo("C1");
        assertThat(collection.get(1).getText()).isEqualTo("C3");
    }

    @Test
    void removeAt_outOfBounds_doesNothing() {
        collection.add(new Paragraph());
        collection.removeAt(-1);
        collection.removeAt(5);
        assertThat(collection.size()).isEqualTo(1);
    }

    // --- contains and indexOf ---

    @Test
    void contains_returnsTrueForExistingItem() {
        var p = new Paragraph();
        collection.add(p);
        assertThat(collection.contains(p)).isTrue();
    }

    @Test
    void contains_returnsFalseForMissingItem() {
        collection.add(new Paragraph());
        assertThat(collection.contains(new Paragraph())).isFalse();
    }

    @Test
    void indexOf_returnsCorrectIndex() {
        var p1 = new Paragraph();
        var p2 = new Paragraph();
        var p3 = new Paragraph();
        collection.add(p1);
        collection.add(p2);
        collection.add(p3);

        assertThat(collection.indexOf(p1)).isEqualTo(0);
        assertThat(collection.indexOf(p2)).isEqualTo(1);
        assertThat(collection.indexOf(p3)).isEqualTo(2);
    }

    @Test
    void indexOf_returnsNegativeOneForMissing() {
        collection.add(new Paragraph());
        assertThat(collection.indexOf(new Paragraph())).isEqualTo(-1);
    }

    // --- isReadOnly ---

    @Test
    void isReadOnly_returnsFalse() {
        assertThat(collection.isReadOnly()).isFalse();
    }

    // --- asISlideComponent / asIPresentationComponent ---

    @Test
    void asISlideComponent_returnsSelf() {
        assertThat(collection.asISlideComponent()).isSameAs(collection);
    }

    @Test
    void asIPresentationComponent_returnsSelf() {
        assertThat(collection.asIPresentationComponent()).isSameAs(collection);
    }

    // --- slide / presentation for standalone collection ---

    @Test
    void slide_returnsNullForStandalone() {
        assertThat(collection.getSlide()).isNull();
    }

    @Test
    void presentation_returnsNullForStandalone() {
        assertThat(collection.getPresentation()).isNull();
    }

    // --- asIEnumerable / iterator ---

    @Test
    void asIEnumerable_isIterable() {
        var p1 = new Paragraph();
        p1.setText("X");
        var p2 = new Paragraph();
        p2.setText("Y");
        collection.add(p1);
        collection.add(p2);

        var iterable = collection.asIEnumerable();
        var texts = new ArrayList<String>();
        for (var p : iterable) {
            texts.add(p.getText());
        }
        assertThat(texts).containsExactly("X", "Y");
    }

    @Test
    void iterator_supportsForEach() {
        var p1 = new Paragraph();
        p1.setText("A");
        var p2 = new Paragraph();
        p2.setText("B");
        collection.add(p1);
        collection.add(p2);

        var texts = new ArrayList<String>();
        for (var p : collection) {
            texts.add(p.getText());
        }
        assertThat(texts).containsExactly("A", "B");
    }

    // --- count / size consistency ---

    @Test
    void count_matchesSize() {
        assertThat(collection.count()).isEqualTo(collection.size());
        collection.add(new Paragraph());
        collection.add(new Paragraph());
        assertThat(collection.count()).isEqualTo(collection.size());
        assertThat(collection.count()).isEqualTo(2);
    }

    // --- constructor with initial list ---

    @Test
    void constructorWithList_populatesCollection() {
        var p1 = new Paragraph();
        p1.setText("Alpha");
        var p2 = new Paragraph();
        p2.setText("Beta");
        var initial = List.of((IParagraph) p1, (IParagraph) p2);

        var coll = new ParagraphCollection(initial);
        assertThat(coll.size()).isEqualTo(2);
        assertThat(coll.get(0).getText()).isEqualTo("Alpha");
        assertThat(coll.get(1).getText()).isEqualTo("Beta");
    }

    @Test
    void constructorWithNull_createsEmptyCollection() {
        var coll = new ParagraphCollection(null);
        assertThat(coll.size()).isEqualTo(0);
    }
}
