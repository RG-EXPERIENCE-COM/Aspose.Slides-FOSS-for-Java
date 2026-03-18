package org.aspose.slides.foss.integration;
import org.aspose.slides.foss.*;

import org.aspose.slides.foss.export.SaveFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for Connector shapes, adjustments, and connections.
 */
class ConnectorTest implements AutoCloseable {

    @TempDir
    Path tempDir;

    @Override
    public void close() {
        // TempDir handles cleanup
    }

    /**
     * Saves a Presentation to a
     * temporary file, disposes the original, and reopens from that file.
     */
    private Presentation saveAndReopen(Presentation pres) throws IOException {
        String path = tempDir.resolve("roundtrip.pptx").toString();
        pres.save(path, SaveFormat.PPTX);
        pres.dispose();
        return new Presentation(path);
    }

    // --- test_add_straight_connector ---

    @Test
    void testAddStraightConnector() {
        try (var pres = new Presentation()) {
            var slide = pres.getSlides().get(0);
            var conn = slide.getShapes().addConnector(ShapeType.STRAIGHT_CONNECTOR1, 100, 100, 300, 200);
            assertThat(conn.getShapeType()).isEqualTo(ShapeType.STRAIGHT_CONNECTOR1);
        }
    }

    // --- test_add_straight_connector_persists ---

    @Test
    void testAddStraightConnectorPersists() throws IOException {
        try (var pres = new Presentation()) {
            pres.getSlides().get(0).getShapes().addConnector(ShapeType.STRAIGHT_CONNECTOR1, 100, 100, 300, 200);

            try (var pres2 = saveAndReopen(pres)) {
                assertThat(pres2.getSlides().get(0).getShapes().size()).isGreaterThanOrEqualTo(1);
            }
        }
    }

    // --- test_bent_connector_adjustments ---

    @Test
    void testBentConnectorAdjustments() throws IOException {
        try (var pres = new Presentation()) {
            pres.getSlides().get(0).getShapes().clear();
            var conn = pres.getSlides().get(0).getShapes().addConnector(ShapeType.BENT_CONNECTOR3, 50, 50, 300, 200);
            if (conn.getAdjustments().size() > 0) {
                conn.getAdjustments().get(0).setRawValue(30000);
            }

            try (var pres2 = saveAndReopen(pres)) {
                // Find the connector shape
                Connector conn2 = null;
                var shapes2 = pres2.getSlides().get(0).getShapes();
                for (int i = 0; i < shapes2.size(); i++) {
                    IShape sh = shapes2.get(i);
                    if (sh instanceof Connector c) {
                        conn2 = c;
                        break;
                    }
                }
                assertThat(conn2).as("Connector not found after reload").isNotNull();
                if (conn2.getAdjustments().size() > 0) {
                    assertThat(conn2.getAdjustments().get(0).getRawValue()).isEqualTo(30000);
                }
            }
        }
    }

    // --- test_connect_shapes ---

    @Test
    void testConnectShapes() throws IOException {
        try (var pres = new Presentation()) {
            var slide = pres.getSlides().get(0);
            slide.getShapes().clear();
            var s1 = slide.getShapes().addAutoShape(ShapeType.RECTANGLE, 50, 50, 100, 60);
            var s2 = slide.getShapes().addAutoShape(ShapeType.RECTANGLE, 350, 200, 100, 60);
            var conn = slide.getShapes().addConnector(ShapeType.BENT_CONNECTOR3, 0, 0, 1, 1);

            conn.setStartShapeConnectedTo(s1);
            conn.setStartShapeConnectionSiteIndex(3);
            conn.setEndShapeConnectedTo(s2);
            conn.setEndShapeConnectionSiteIndex(1);

            assertThat(conn.getStartShapeConnectedTo()).isNotNull();
            assertThat(conn.getEndShapeConnectedTo()).isNotNull();

            try (var pres2 = saveAndReopen(pres)) {
                IConnector conn2 = null;
                var shapes2 = pres2.getSlides().get(0).getShapes();
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
    }

    // --- test_reroute ---

    @Test
    void testReroute() {
        try (var pres = new Presentation()) {
            var slide = pres.getSlides().get(0);
            var s1 = slide.getShapes().addAutoShape(ShapeType.ELLIPSE, 50, 100, 80, 80);
            var s2 = slide.getShapes().addAutoShape(ShapeType.ELLIPSE, 400, 100, 80, 80);
            var conn = slide.getShapes().addConnector(ShapeType.BENT_CONNECTOR3, 0, 0, 1, 1);
            conn.setStartShapeConnectedTo(s1);
            conn.setStartShapeConnectionSiteIndex(3);
            conn.setEndShapeConnectedTo(s2);
            conn.setEndShapeConnectionSiteIndex(1);
            conn.reroute();
            // After reroute the connector should span between the shapes
            assertThat(conn.getWidth() > 0 || conn.getHeight() > 0).isTrue();
        }
    }

    // --- test_adjustment_properties ---

    @Test
    void testAdjustmentProperties() {
        try (var pres = new Presentation()) {
            var conn = pres.getSlides().get(0).getShapes().addConnector(ShapeType.BENT_CONNECTOR3, 50, 50, 300, 200);
            if (conn.getAdjustments().size() > 0) {
                var adj = conn.getAdjustments().get(0);
                assertThat(adj.getName()).isNotNull();
                assertThat(adj.getRawValue()).isInstanceOf(Long.class);
                assertThat(adj.getAngleValue()).isInstanceOf(Double.class);
            }
        }
    }
}
