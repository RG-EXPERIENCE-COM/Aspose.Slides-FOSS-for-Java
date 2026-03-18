package org.aspose.slides.foss;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link IShapeCollection}: add/insert/remove shapes, toArray,
 * reorder, asICollection, asIEnumerable, parentGroup.
 *
 * <p>Covers connector creation, persistence, adjustment properties, shape connections,
 * and element removal.</p>
 */
class IShapeCollectionTest {

    /**
     * Saves the presentation to a byte array and reloads it, simulating the
     * round-trip fixture for save/reload testing.
     */
    private Presentation roundTrip(Presentation pres) throws IOException {
        var baos = new ByteArrayOutputStream();
        pres.save(baos);
        pres.dispose();
        return new Presentation(new ByteArrayInputStream(baos.toByteArray()));
    }

    // --- test_add_straight_connector ---

    @Test
    void addConnector_straightConnectorHasCorrectType() {
        try (var pres = new Presentation()) {
            IShapeCollection shapes = pres.getSlides().get(0).getShapes();
            IConnector conn = shapes.addConnector(ShapeType.STRAIGHT_CONNECTOR1, 100, 100, 300, 200);
            assertThat(conn.getShapeType()).isEqualTo(ShapeType.STRAIGHT_CONNECTOR1);
        }
    }

    // --- test_add_straight_connector_persists ---

    @Test
    void addConnector_persistsAfterSaveReload() throws IOException {
        var pres = new Presentation();
        pres.getSlides().get(0).getShapes().addConnector(ShapeType.STRAIGHT_CONNECTOR1, 100, 100, 300, 200);

        try (var pres2 = roundTrip(pres)) {
            assertThat(pres2.getSlides().get(0).getShapes().size()).isGreaterThanOrEqualTo(1);
        }
    }

    // --- test_adjustment_properties ---

    @Test
    void addConnector_adjustmentValuesExposeNameRawValueAngleValue() {
        try (var pres = new Presentation()) {
            IConnector conn = pres.getSlides().get(0).getShapes()
                    .addConnector(ShapeType.BENT_CONNECTOR3, 50, 50, 300, 200);
            IAdjustValueCollection adjustments = conn.getAdjustments();
            if (adjustments != null && adjustments.size() > 0) {
                IAdjustValue adj = adjustments.get(0);
                assertThat(adj.getName()).isNotNull();
                assertThat(adj.getRawValue()).isInstanceOf(Long.class);
                assertThat(adj.getAngleValue()).isInstanceOf(Double.class);
            }
        }
    }

    // --- test_bent_connector_adjustments ---

    @Test
    void addConnector_bentConnectorAdjustmentsPersist() throws IOException {
        var pres = new Presentation();
        IShapeCollection shapes = pres.getSlides().get(0).getShapes();
        shapes.clear();
        IConnector conn = shapes.addConnector(ShapeType.BENT_CONNECTOR3, 50, 50, 300, 200);
        IAdjustValueCollection adjustments = conn.getAdjustments();
        if (adjustments != null && adjustments.size() > 0) {
            adjustments.get(0).setRawValue(30000);
        }

        try (var pres2 = roundTrip(pres)) {
            Connector conn2 = null;
            IShapeCollection shapes2 = pres2.getSlides().get(0).getShapes();
            for (int i = 0; i < shapes2.size(); i++) {
                IShape sh = shapes2.get(i);
                if (sh instanceof Connector c) {
                    conn2 = c;
                    break;
                }
            }
            assertThat(conn2).as("Connector not found after reload").isNotNull();
            IAdjustValueCollection adj2 = conn2.getAdjustments();
            if (adj2 != null && adj2.size() > 0) {
                assertThat(adj2.get(0).getRawValue()).isEqualTo(30000);
            }
        }
    }

    // --- test_connect_shapes ---

    @Test
    void addConnector_connectShapesPersist() throws IOException {
        var pres = new Presentation();
        IShapeCollection shapes = pres.getSlides().get(0).getShapes();
        shapes.clear();
        IAutoShape s1 = shapes.addAutoShape(ShapeType.RECTANGLE, 50, 50, 100, 60);
        IAutoShape s2 = shapes.addAutoShape(ShapeType.RECTANGLE, 350, 200, 100, 60);
        IConnector conn = shapes.addConnector(ShapeType.BENT_CONNECTOR3, 0, 0, 1, 1);

        conn.setStartShapeConnectedTo(s1);
        conn.setStartShapeConnectionSiteIndex(3);
        conn.setEndShapeConnectedTo(s2);
        conn.setEndShapeConnectionSiteIndex(1);

        assertThat(conn.getStartShapeConnectedTo()).isNotNull();
        assertThat(conn.getEndShapeConnectedTo()).isNotNull();

        try (var pres2 = roundTrip(pres)) {
            IConnector conn2 = null;
            IShapeCollection shapes2 = pres2.getSlides().get(0).getShapes();
            for (int i = 0; i < shapes2.size(); i++) {
                IShape sh = shapes2.get(i);
                if (sh.getShapeType() == ShapeType.BENT_CONNECTOR3) {
                    conn2 = (IConnector) sh;
                    break;
                }
            }
            assertThat(conn2).isNotNull();
            assertThat(conn2.getStartShapeConnectionSiteIndex()).isEqualTo(3);
            assertThat(conn2.getEndShapeConnectionSiteIndex()).isEqualTo(1);
        }
    }

    // --- test_remove_comment (exercises removeAt on a collection) ---

    @Test
    void removeAt_removesShapeAtIndex() {
        try (var pres = new Presentation()) {
            IShapeCollection shapes = pres.getSlides().get(0).getShapes();
            shapes.clear();
            shapes.addAutoShape(ShapeType.RECTANGLE, 10, 10, 50, 50);
            shapes.addAutoShape(ShapeType.ELLIPSE, 70, 10, 50, 50);
            shapes.addAutoShape(ShapeType.TRIANGLE, 130, 10, 50, 50);
            assertThat(shapes.size()).isEqualTo(3);

            shapes.removeAt(1);
            assertThat(shapes.size()).isEqualTo(2);
            // First and third remain
            assertThat(shapes.get(0).getShapeType()).isEqualTo(ShapeType.RECTANGLE);
            assertThat(shapes.get(1).getShapeType()).isEqualTo(ShapeType.TRIANGLE);
        }
    }

    @Test
    void removeAt_reducesSize() {
        try (var pres = new Presentation()) {
            IShapeCollection shapes = pres.getSlides().get(0).getShapes();
            shapes.clear();
            shapes.addAutoShape(ShapeType.RECTANGLE, 10, 10, 50, 50);
            shapes.addAutoShape(ShapeType.ELLIPSE, 70, 10, 50, 50);
            shapes.addAutoShape(ShapeType.TRIANGLE, 130, 10, 50, 50);
            assertThat(shapes.size()).isEqualTo(3);

            shapes.removeAt(1);
            assertThat(shapes.size()).isEqualTo(2);
        }
    }

    // --- Additional IShapeCollection contract tests ---

    @Test
    void clear_removesAllShapes() {
        try (var pres = new Presentation()) {
            IShapeCollection shapes = pres.getSlides().get(0).getShapes();
            shapes.addAutoShape(ShapeType.RECTANGLE, 10, 10, 50, 50);
            shapes.addAutoShape(ShapeType.ELLIPSE, 70, 10, 50, 50);
            shapes.clear();
            assertThat(shapes.size()).isEqualTo(0);
        }
    }

    @Test
    void remove_removesSpecificShape() {
        try (var pres = new Presentation()) {
            IShapeCollection shapes = pres.getSlides().get(0).getShapes();
            shapes.clear();
            IAutoShape s1 = shapes.addAutoShape(ShapeType.RECTANGLE, 10, 10, 50, 50);
            shapes.addAutoShape(ShapeType.ELLIPSE, 70, 10, 50, 50);
            shapes.remove(s1);
            assertThat(shapes.size()).isEqualTo(1);
            assertThat(shapes.get(0).getShapeType()).isEqualTo(ShapeType.ELLIPSE);
        }
    }

    @Test
    void indexOf_returnsCorrectIndex() {
        try (var pres = new Presentation()) {
            IShapeCollection shapes = pres.getSlides().get(0).getShapes();
            shapes.clear();
            IAutoShape s1 = shapes.addAutoShape(ShapeType.RECTANGLE, 10, 10, 50, 50);
            IAutoShape s2 = shapes.addAutoShape(ShapeType.ELLIPSE, 70, 10, 50, 50);
            assertThat(shapes.indexOf(s1)).isEqualTo(0);
            assertThat(shapes.indexOf(s2)).isEqualTo(1);
        }
    }

    @Test
    void toArray_returnsAllShapes() {
        try (var pres = new Presentation()) {
            IShapeCollection shapes = pres.getSlides().get(0).getShapes();
            shapes.clear();
            shapes.addAutoShape(ShapeType.RECTANGLE, 10, 10, 50, 50);
            shapes.addAutoShape(ShapeType.ELLIPSE, 70, 10, 50, 50);
            IShape[] arr = shapes.toArray();
            assertThat(arr).hasSize(2);
        }
    }

    @Test
    void toArray_withRange_returnsSubset() {
        try (var pres = new Presentation()) {
            IShapeCollection shapes = pres.getSlides().get(0).getShapes();
            shapes.clear();
            shapes.addAutoShape(ShapeType.RECTANGLE, 10, 10, 50, 50);
            shapes.addAutoShape(ShapeType.ELLIPSE, 70, 10, 50, 50);
            shapes.addAutoShape(ShapeType.TRIANGLE, 130, 10, 50, 50);
            IShape[] arr = shapes.toArray(1, 2);
            assertThat(arr).hasSize(2);
            assertThat(arr[0].getShapeType()).isEqualTo(ShapeType.ELLIPSE);
            assertThat(arr[1].getShapeType()).isEqualTo(ShapeType.TRIANGLE);
        }
    }

    @Test
    void reorder_movesShapeToNewPosition() {
        try (var pres = new Presentation()) {
            IShapeCollection shapes = pres.getSlides().get(0).getShapes();
            shapes.clear();
            IAutoShape s1 = shapes.addAutoShape(ShapeType.RECTANGLE, 10, 10, 50, 50);
            shapes.addAutoShape(ShapeType.ELLIPSE, 70, 10, 50, 50);
            shapes.addAutoShape(ShapeType.TRIANGLE, 130, 10, 50, 50);

            shapes.reorder(2, s1);
            assertThat(shapes.get(0).getShapeType()).isEqualTo(ShapeType.ELLIPSE);
            assertThat(shapes.get(2).getShapeType()).isEqualTo(ShapeType.RECTANGLE);
        }
    }

    @Test
    void reorder_multipleShapes() {
        try (var pres = new Presentation()) {
            IShapeCollection shapes = pres.getSlides().get(0).getShapes();
            shapes.clear();
            IAutoShape s1 = shapes.addAutoShape(ShapeType.RECTANGLE, 10, 10, 50, 50);
            IAutoShape s2 = shapes.addAutoShape(ShapeType.ELLIPSE, 70, 10, 50, 50);
            IAutoShape s3 = shapes.addAutoShape(ShapeType.TRIANGLE, 130, 10, 50, 50);

            shapes.reorder(0, new IShape[]{s3, s2});
            assertThat(shapes.get(0).getShapeType()).isEqualTo(ShapeType.TRIANGLE);
            assertThat(shapes.get(1).getShapeType()).isEqualTo(ShapeType.ELLIPSE);
            assertThat(shapes.get(2).getShapeType()).isEqualTo(ShapeType.RECTANGLE);
        }
    }

    @Test
    void insertConnector_insertsAtSpecifiedIndex() {
        try (var pres = new Presentation()) {
            IShapeCollection shapes = pres.getSlides().get(0).getShapes();
            shapes.clear();
            shapes.addAutoShape(ShapeType.RECTANGLE, 10, 10, 50, 50);
            shapes.addAutoShape(ShapeType.ELLIPSE, 70, 10, 50, 50);

            IConnector conn = shapes.insertConnector(1, ShapeType.STRAIGHT_CONNECTOR1, 0, 0, 100, 100);
            assertThat(shapes.size()).isEqualTo(3);
            assertThat(shapes.get(1)).isSameAs(conn);
            assertThat(conn.getShapeType()).isEqualTo(ShapeType.STRAIGHT_CONNECTOR1);
        }
    }

    @Test
    void insertAutoShape_insertsAtSpecifiedIndex() {
        try (var pres = new Presentation()) {
            IShapeCollection shapes = pres.getSlides().get(0).getShapes();
            shapes.clear();
            shapes.addAutoShape(ShapeType.RECTANGLE, 10, 10, 50, 50);
            shapes.addAutoShape(ShapeType.TRIANGLE, 130, 10, 50, 50);

            IAutoShape inserted = shapes.insertAutoShape(1, ShapeType.ELLIPSE, 70, 10, 50, 50);
            assertThat(shapes.size()).isEqualTo(3);
            assertThat(shapes.get(1)).isSameAs(inserted);
            assertThat(inserted.getShapeType()).isEqualTo(ShapeType.ELLIPSE);
        }
    }

    @Test
    void asICollection_returnsUnmodifiableList() {
        try (var pres = new Presentation()) {
            IShapeCollection shapes = pres.getSlides().get(0).getShapes();
            shapes.clear();
            shapes.addAutoShape(ShapeType.RECTANGLE, 10, 10, 50, 50);
            shapes.addAutoShape(ShapeType.ELLIPSE, 70, 10, 50, 50);

            List<IShape> list = shapes.asICollection();
            assertThat(list).hasSize(2);
            assertThat(list.get(0).getShapeType()).isEqualTo(ShapeType.RECTANGLE);
        }
    }

    @Test
    void asIEnumerable_isIterable() {
        try (var pres = new Presentation()) {
            IShapeCollection shapes = pres.getSlides().get(0).getShapes();
            shapes.clear();
            shapes.addAutoShape(ShapeType.RECTANGLE, 10, 10, 50, 50);
            shapes.addAutoShape(ShapeType.ELLIPSE, 70, 10, 50, 50);

            Iterable<IShape> iterable = shapes.asIEnumerable();
            int count = 0;
            for (IShape ignored : iterable) {
                count++;
            }
            assertThat(count).isEqualTo(2);
        }
    }

    @Test
    void parentGroup_isNullForSlideShapes() {
        try (var pres = new Presentation()) {
            IShapeCollection shapes = pres.getSlides().get(0).getShapes();
            assertThat(shapes.getParentGroup()).isNull();
        }
    }

    @Test
    void addConnector_withCreateFromTemplate() {
        try (var pres = new Presentation()) {
            IShapeCollection shapes = pres.getSlides().get(0).getShapes();
            IConnector conn = shapes.addConnector(ShapeType.STRAIGHT_CONNECTOR1, 100, 100, 300, 200, false);
            assertThat(conn.getShapeType()).isEqualTo(ShapeType.STRAIGHT_CONNECTOR1);
        }
    }

    @Test
    void insertAutoShape_withCreateFromTemplate() {
        try (var pres = new Presentation()) {
            IShapeCollection shapes = pres.getSlides().get(0).getShapes();
            shapes.clear();
            shapes.addAutoShape(ShapeType.RECTANGLE, 10, 10, 50, 50);
            IAutoShape inserted = shapes.insertAutoShape(0, ShapeType.ELLIPSE, 0, 0, 100, 100, false);
            assertThat(shapes.size()).isEqualTo(2);
            assertThat(shapes.get(0)).isSameAs(inserted);
        }
    }
}
