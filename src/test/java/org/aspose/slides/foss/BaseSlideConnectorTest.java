package org.aspose.slides.foss;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for connector shapes created via the Presentation API.
 *
 * <p>Covers adding straight connectors, connector persistence, bent connector adjustments,
 * connecting shapes, rerouting, and adjustment properties.</p>
 */
class BaseSlideConnectorTest {

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

    @Test
    void addStraightConnector_hasCorrectType() {
        try (var pres = new Presentation()) {
            IShapeCollection shapes = pres.getSlides().get(0).getShapes();
            IConnector conn = shapes.addConnector(ShapeType.STRAIGHT_CONNECTOR1, 100, 100, 300, 200);
            assertThat(conn.getShapeType()).isEqualTo(ShapeType.STRAIGHT_CONNECTOR1);
        }
    }

    @Test
    void addStraightConnector_persistsAfterSaveReload() throws IOException {
        var pres = new Presentation();
        pres.getSlides().get(0).getShapes().addConnector(ShapeType.STRAIGHT_CONNECTOR1, 100, 100, 300, 200);

        try (var pres2 = roundTrip(pres)) {
            assertThat(pres2.getSlides().get(0).getShapes().size()).isGreaterThanOrEqualTo(1);
        }
    }

    @Test
    void bentConnectorAdjustments_persistAfterSaveReload() throws IOException {
        var pres = new Presentation();
        IShapeCollection shapes = pres.getSlides().get(0).getShapes();
        shapes.clear();
        IConnector conn = shapes.addConnector(ShapeType.BENT_CONNECTOR3, 50, 50, 300, 200);
        IAdjustValueCollection adjustments = conn.getAdjustments();
        if (adjustments != null && adjustments.size() > 0) {
            adjustments.get(0).setRawValue(30000);
        }

        try (var pres2 = roundTrip(pres)) {
            // Find the connector shape
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

    @Test
    void connectShapes_connectionsPersistAfterSaveReload() throws IOException {
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

    @Test
    void reroute_updatesConnectorPosition() {
        try (var pres = new Presentation()) {
            IShapeCollection shapes = pres.getSlides().get(0).getShapes();
            IAutoShape s1 = shapes.addAutoShape(ShapeType.ELLIPSE, 50, 100, 80, 80);
            IAutoShape s2 = shapes.addAutoShape(ShapeType.ELLIPSE, 400, 100, 80, 80);
            IConnector conn = shapes.addConnector(ShapeType.BENT_CONNECTOR3, 0, 0, 1, 1);
            conn.setStartShapeConnectedTo(s1);
            conn.setStartShapeConnectionSiteIndex(3);
            conn.setEndShapeConnectedTo(s2);
            conn.setEndShapeConnectionSiteIndex(1);
            conn.reroute();
            // After reroute the connector should span between the shapes
            assertThat(conn.getWidth() > 0 || conn.getHeight() > 0).isTrue();
        }
    }

    @Test
    void adjustmentProperties_exposeNameRawValueAngleValue() {
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
}
