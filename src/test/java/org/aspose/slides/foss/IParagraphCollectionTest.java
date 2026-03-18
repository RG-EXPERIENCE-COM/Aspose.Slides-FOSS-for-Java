package org.aspose.slides.foss;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link IParagraphCollection}: add, insert, remove, removeAt, clear,
 * count, asISlideComponent, asIEnumerable.
 */
class IParagraphCollectionTest {

    @Test
    void add_appendsParagraph() {
        var collection = new ParagraphCollection();
        var p1 = new Paragraph();
        p1.setText("Hello");
        collection.add(p1);
        assertThat(collection.size()).isEqualTo(1);

        var p2 = new Paragraph();
        p2.setText("World");
        collection.add(p2);
        assertThat(collection.size()).isEqualTo(2);
        assertThat(collection.get(1).getText()).isEqualTo("World");
    }

    @Test
    void removeAt_removesByIndex() {
        var collection = new ParagraphCollection();
        var p1 = new Paragraph();
        p1.setText("First");
        var p2 = new Paragraph();
        p2.setText("Second");
        var p3 = new Paragraph();
        p3.setText("Third");
        collection.add(p1);
        collection.add(p2);
        collection.add(p3);
        assertThat(collection.size()).isEqualTo(3);

        collection.removeAt(1);
        assertThat(collection.size()).isEqualTo(2);
        assertThat(collection.get(0).getText()).isEqualTo("First");
        assertThat(collection.get(1).getText()).isEqualTo("Third");
    }

    @Test
    void remove_removesParagraphByReference() {
        var collection = new ParagraphCollection();
        var p1 = new Paragraph();
        p1.setText("A");
        var p2 = new Paragraph();
        p2.setText("B");
        collection.add(p1);
        collection.add(p2);

        boolean removed = collection.remove(p1);
        assertThat(removed).isTrue();
        assertThat(collection.size()).isEqualTo(1);
        assertThat(collection.get(0).getText()).isEqualTo("B");
    }

    @Test
    void clear_removesAllParagraphs() {
        var collection = new ParagraphCollection();
        collection.add(new Paragraph());
        collection.add(new Paragraph());
        assertThat(collection.size()).isEqualTo(2);

        collection.clear();
        assertThat(collection.size()).isEqualTo(0);
    }

    @Test
    void insert_placesAtCorrectIndex() {
        var collection = new ParagraphCollection();
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
        assertThat(collection.get(1).getText()).isEqualTo("Second");
    }

    @Test
    void count_returnsSameAsSize() {
        var collection = new ParagraphCollection();
        assertThat(collection.count()).isEqualTo(0);

        collection.add(new Paragraph());
        collection.add(new Paragraph());
        assertThat(collection.count()).isEqualTo(2);
        assertThat(collection.count()).isEqualTo(collection.size());
    }

    @Test
    void asIEnumerable_isIterable() {
        var collection = new ParagraphCollection();
        var p1 = new Paragraph();
        p1.setText("A");
        var p2 = new Paragraph();
        p2.setText("B");
        collection.add(p1);
        collection.add(p2);

        var iterable = collection.asIEnumerable();
        int count = 0;
        for (var paragraph : iterable) {
            count++;
        }
        assertThat(count).isEqualTo(2);
    }

    @Test
    void asISlideComponent_returnsSelf() {
        var collection = new ParagraphCollection();
        assertThat(collection.asISlideComponent()).isSameAs(collection);
    }

    @Test
    void get_returnsCorrectParagraph() {
        var collection = new ParagraphCollection();
        var p1 = new Paragraph();
        p1.setText("Alpha");
        var p2 = new Paragraph();
        p2.setText("Beta");
        collection.add(p1);
        collection.add(p2);

        assertThat(collection.get(0).getText()).isEqualTo("Alpha");
        assertThat(collection.get(1).getText()).isEqualTo("Beta");
    }
}
