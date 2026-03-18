package org.aspose.slides.foss;

import org.junit.jupiter.api.Test;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link PortionCollection} exercising add, insert,
 * remove, removeAt, clear, indexOf, and contains through the Presentation API.
 *
 * <p>Covers portion addition, removal, index lookup, and round-trip persistence.</p>
 */
class PortionCollectionTest {

    private Presentation roundTrip(Presentation pres) throws IOException {
        var baos = new ByteArrayOutputStream();
        pres.save(baos);
        pres.dispose();
        return new Presentation(new ByteArrayInputStream(baos.toByteArray()));
    }

    /**
     * Returns the {@link TextFrame} from the given shape.
     */
    private static TextFrame textFrame(IAutoShape shape) {
        return (TextFrame) shape.getTextFrame();
    }

    // ---

    @Test
    void addPortion_appendsText() {
        try (var pres = new Presentation()) {
            var shape = pres.getSlides().get(0).getShapes()
                    .addAutoShape(ShapeType.RECTANGLE, 50, 50, 400, 100);
            var tf = textFrame(shape);
            tf.setText("Hello ");

            var newPortion = new Portion("World!");
            tf.getParagraphs().get(0).getPortions().add(newPortion);

            assertThat(tf.getText()).contains("World!");
        }
    }

    // --- removeAt: middle element ---

    @Test
    void removeAt_removesMiddlePortion() {
        try (var pres = new Presentation()) {
            var shape = pres.getSlides().get(0).getShapes()
                    .addAutoShape(ShapeType.RECTANGLE, 50, 50, 400, 100);
            var tf = textFrame(shape);
            tf.setText("A");
            var portions = tf.getParagraphs().get(0).getPortions();

            portions.add(new Portion("B"));
            portions.add(new Portion("C"));
            assertThat(portions.count()).isEqualTo(3);

            portions.removeAt(1);
            assertThat(portions.count()).isEqualTo(2);
            assertThat(portions.get(0).getText()).isEqualTo("A");
            assertThat(portions.get(1).getText()).isEqualTo("C");
        }
    }

    // --- removeAt: by index ---

    @Test
    void removeAt_removesByIndex() {
        try (var pres = new Presentation()) {
            var shape = pres.getSlides().get(0).getShapes()
                    .addAutoShape(ShapeType.RECTANGLE, 50, 50, 400, 100);
            var tf = textFrame(shape);
            tf.setText("First");
            var portions = tf.getParagraphs().get(0).getPortions();

            portions.add(new Portion("Second"));
            assertThat(portions.count()).isEqualTo(2);

            portions.removeAt(0);
            assertThat(portions.count()).isEqualTo(1);
        }
    }

    // --- indexOf ---

    @Test
    void indexOf_returnsCorrectPosition() {
        try (var pres = new Presentation()) {
            var shape = pres.getSlides().get(0).getShapes()
                    .addAutoShape(ShapeType.RECTANGLE, 50, 50, 400, 100);
            var tf = textFrame(shape);
            tf.setText("First");
            var portions = tf.getParagraphs().get(0).getPortions();

            var second = new Portion("Second");
            portions.add(second);

            assertThat(portions.indexOf(portions.get(0))).isEqualTo(0);
            assertThat(portions.indexOf(second)).isEqualTo(1);
        }
    }

    // --- removeAt: last element ---

    @Test
    void removeAt_lastElement() {
        try (var pres = new Presentation()) {
            var shape = pres.getSlides().get(0).getShapes()
                    .addAutoShape(ShapeType.RECTANGLE, 50, 50, 400, 100);
            var tf = textFrame(shape);
            tf.setText("Keep");
            var portions = tf.getParagraphs().get(0).getPortions();

            portions.add(new Portion("Remove"));
            assertThat(portions.count()).isEqualTo(2);

            portions.removeAt(1);
            assertThat(portions.count()).isEqualTo(1);
        }
    }

    // --- add + round-trip ---

    @Test
    void addPortion_persistsAfterRoundTrip() throws IOException {
        var pres = new Presentation();
        pres.getSlides().get(0).getShapes().clear();
        var shape = pres.getSlides().get(0).getShapes()
                .addAutoShape(ShapeType.RECTANGLE, 50, 50, 300, 150);
        var tf = textFrame(shape);
        tf.setText("Base");

        var extra = new Portion(" Extra");
        tf.getParagraphs().get(0).getPortions().add(extra);

        try (var pres2 = roundTrip(pres)) {
            var shape2 = (IAutoShape) pres2.getSlides().get(0).getShapes().get(0);
            var tf2 = textFrame(shape2);
            var text = tf2.getText();
            assertThat(text).contains("Base");
            assertThat(text).contains("Extra");
            assertThat(tf2.getParagraphs().get(0).getPortions().count()).isGreaterThanOrEqualTo(2);
        }
    }

    // --- Additional contract tests ---

    @Test
    void clear_removesAllPortions() {
        try (var pres = new Presentation()) {
            var shape = pres.getSlides().get(0).getShapes()
                    .addAutoShape(ShapeType.RECTANGLE, 50, 50, 400, 100);
            var tf = textFrame(shape);
            tf.setText("Hello");
            var portions = tf.getParagraphs().get(0).getPortions();

            portions.add(new Portion("World"));
            assertThat(portions.count()).isGreaterThanOrEqualTo(2);

            portions.clear();
            assertThat(portions.count()).isEqualTo(0);
        }
    }

    @Test
    void contains_returnsTrueForExistingPortion() {
        try (var pres = new Presentation()) {
            var shape = pres.getSlides().get(0).getShapes()
                    .addAutoShape(ShapeType.RECTANGLE, 50, 50, 400, 100);
            var tf = textFrame(shape);
            tf.setText("Hello");
            var portions = tf.getParagraphs().get(0).getPortions();

            var extra = new Portion("Extra");
            portions.add(extra);

            assertThat(portions.contains(extra)).isTrue();
            assertThat(portions.contains(new Portion("Missing"))).isFalse();
        }
    }

    @Test
    void isReadOnly_returnsFalse() {
        try (var pres = new Presentation()) {
            var shape = pres.getSlides().get(0).getShapes()
                    .addAutoShape(ShapeType.RECTANGLE, 50, 50, 400, 100);
            var tf = textFrame(shape);
            tf.setText("Hello");
            assertThat(tf.getParagraphs().get(0).getPortions().isReadOnly()).isFalse();
        }
    }

    @Test
    void insert_placesAtCorrectIndex() {
        try (var pres = new Presentation()) {
            var shape = pres.getSlides().get(0).getShapes()
                    .addAutoShape(ShapeType.RECTANGLE, 50, 50, 400, 100);
            var tf = textFrame(shape);
            tf.setText("First");
            var portions = tf.getParagraphs().get(0).getPortions();

            portions.add(new Portion("Third"));
            portions.insert(1, new Portion("Second"));

            assertThat(portions.count()).isEqualTo(3);
            assertThat(portions.get(1).getText()).isEqualTo("Second");
        }
    }

    @Test
    void remove_removesPortionByReference() {
        try (var pres = new Presentation()) {
            var shape = pres.getSlides().get(0).getShapes()
                    .addAutoShape(ShapeType.RECTANGLE, 50, 50, 400, 100);
            var tf = textFrame(shape);
            tf.setText("A");
            var portions = tf.getParagraphs().get(0).getPortions();

            var b = new Portion("B");
            portions.add(b);
            assertThat(portions.count()).isEqualTo(2);

            boolean removed = portions.remove(b);
            assertThat(removed).isTrue();
            assertThat(portions.count()).isEqualTo(1);
            assertThat(portions.get(0).getText()).isEqualTo("A");
        }
    }

    @Test
    void asIEnumerable_iteratesAllPortions() {
        try (var pres = new Presentation()) {
            var shape = pres.getSlides().get(0).getShapes()
                    .addAutoShape(ShapeType.RECTANGLE, 50, 50, 400, 100);
            var tf = textFrame(shape);
            tf.setText("A");
            var portions = tf.getParagraphs().get(0).getPortions();

            portions.add(new Portion("B"));

            int count = 0;
            for (var p : portions.asIEnumerable()) {
                count++;
            }
            assertThat(count).isEqualTo(2);
        }
    }
}
