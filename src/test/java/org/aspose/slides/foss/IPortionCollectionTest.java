package org.aspose.slides.foss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for {@link IPortionCollection}: add, insert, remove, removeAt, clear,
 * contains, indexOf, count, asIEnumerable, and index access.
 */
class IPortionCollectionTest {

    private PortionCollection collection;

    @BeforeEach
    void setUp() {
        collection = new PortionCollection();
    }

    // --- add ---

    @Test
    void add_appendsPortion() {
        var p1 = new Portion("Hello");
        collection.add(p1);
        assertThat(collection.size()).isEqualTo(1);
        assertThat(collection.get(0).getText()).isEqualTo("Hello");
    }

    @Test
    void add_multiplePortionsAppendsInOrder() {
        collection.add(new Portion("Hello "));
        collection.add(new Portion("World!"));
        assertThat(collection.size()).isEqualTo(2);
        assertThat(collection.get(0).getText()).isEqualTo("Hello ");
        assertThat(collection.get(1).getText()).isEqualTo("World!");
    }

    // --- indexOf ---

    @Test
    void indexOf_returnsCorrectPosition() {
        var p0 = new Portion("A");
        var p1 = new Portion("B");
        collection.add(p0);
        collection.add(p1);
        assertThat(collection.indexOf(p0)).isEqualTo(0);
        assertThat(collection.indexOf(p1)).isEqualTo(1);
    }

    @Test
    void indexOf_returnsNegativeOneWhenNotFound() {
        collection.add(new Portion("A"));
        var missing = new Portion("B");
        assertThat(collection.indexOf(missing)).isEqualTo(-1);
    }

    // --- removeAt ---

    @Test
    void removeAt_removesByIndex() {
        collection.add(new Portion("C1"));
        collection.add(new Portion("C2"));
        collection.add(new Portion("C3"));
        assertThat(collection.size()).isEqualTo(3);

        collection.removeAt(1);
        assertThat(collection.size()).isEqualTo(2);
        assertThat(collection.get(0).getText()).isEqualTo("C1");
        assertThat(collection.get(1).getText()).isEqualTo("C3");
    }

    @Test
    void removeAt_firstElement() {
        collection.add(new Portion("First"));
        collection.add(new Portion("Second"));
        collection.removeAt(0);
        assertThat(collection.size()).isEqualTo(1);
        assertThat(collection.get(0).getText()).isEqualTo("Second");
    }

    // --- remove ---

    @Test
    void remove_removesPortionByReference() {
        var p1 = new Portion("A");
        var p2 = new Portion("B");
        collection.add(p1);
        collection.add(p2);

        boolean removed = collection.remove(p1);
        assertThat(removed).isTrue();
        assertThat(collection.size()).isEqualTo(1);
        assertThat(collection.get(0).getText()).isEqualTo("B");
    }

    @Test
    void remove_returnsFalseWhenNotFound() {
        collection.add(new Portion("A"));
        var missing = new Portion("X");
        assertThat(collection.remove(missing)).isFalse();
        assertThat(collection.size()).isEqualTo(1);
    }

    // --- insert ---

    @Test
    void insert_placesAtCorrectIndex() {
        collection.add(new Portion("First"));
        collection.add(new Portion("Third"));

        collection.insert(1, new Portion("Second"));

        assertThat(collection.size()).isEqualTo(3);
        assertThat(collection.get(0).getText()).isEqualTo("First");
        assertThat(collection.get(1).getText()).isEqualTo("Second");
        assertThat(collection.get(2).getText()).isEqualTo("Third");
    }

    // --- clear ---

    @Test
    void clear_removesAllPortions() {
        collection.add(new Portion("A"));
        collection.add(new Portion("B"));
        assertThat(collection.size()).isEqualTo(2);

        collection.clear();
        assertThat(collection.size()).isEqualTo(0);
    }

    // --- contains ---

    @Test
    void contains_returnsTrueForExistingPortion() {
        var p = new Portion("X");
        collection.add(p);
        assertThat(collection.contains(p)).isTrue();
    }

    @Test
    void contains_returnsFalseForMissingPortion() {
        collection.add(new Portion("X"));
        assertThat(collection.contains(new Portion("Y"))).isFalse();
    }

    // --- count ---

    @Test
    void count_returnsSameAsSize() {
        assertThat(collection.count()).isEqualTo(0);

        collection.add(new Portion("A"));
        collection.add(new Portion("B"));
        assertThat(collection.count()).isEqualTo(2);
        assertThat(collection.count()).isEqualTo(collection.size());
    }

    // --- asIEnumerable ---

    @Test
    void asIEnumerable_isIterable() {
        collection.add(new Portion("A"));
        collection.add(new Portion("B"));

        int count = 0;
        for (var portion : collection.asIEnumerable()) {
            count++;
        }
        assertThat(count).isEqualTo(2);
    }

    @Test
    void asIEnumerable_emptyCollection() {
        int count = 0;
        for (var ignored : collection.asIEnumerable()) {
            count++;
        }
        assertThat(count).isEqualTo(0);
    }

    // --- get ---

    @Test
    void get_returnsCorrectPortion() {
        collection.add(new Portion("Alpha"));
        collection.add(new Portion("Beta"));

        assertThat(collection.get(0).getText()).isEqualTo("Alpha");
        assertThat(collection.get(1).getText()).isEqualTo("Beta");
    }

    @Test
    void get_throwsOnInvalidIndex() {
        assertThatThrownBy(() -> collection.get(0))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }
}
