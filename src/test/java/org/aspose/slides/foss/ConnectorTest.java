package org.aspose.slides.foss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link Connector}: shape type, adjustments, connections, and rerouting.
 *
 * <p>Covers straight connector creation, adjustment properties, bent connector adjustments,
 * shape connections, rerouting, and auto shape integration.</p>
 */
class ConnectorTest {

    private static final String NS_P = "http://schemas.openxmlformats.org/presentationml/2006/main";
    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";
    private static final double EMU_PER_POINT = 12700.0;

    private Document doc;

    @BeforeEach
    void setUp() throws Exception {
        doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .newDocument();
    }

    /**
     * Creates a connector XML element ({@code <p:cxnSp>}) with the given preset geometry
     * and position/size in points.
     */
    private Element createConnectorXml(String prstName, double x, double y, double w, double h) {
        Element cxnSp = doc.createElementNS(NS_P, "p:cxnSp");

        // nvCxnSpPr with cNvPr and cNvCxnSpPr
        Element nvCxnSpPr = doc.createElementNS(NS_P, "p:nvCxnSpPr");
        Element cNvPr = doc.createElementNS(NS_P, "p:cNvPr");
        cNvPr.setAttribute("id", "100");
        cNvPr.setAttribute("name", "Connector");
        nvCxnSpPr.appendChild(cNvPr);
        Element cNvCxnSpPr = doc.createElementNS(NS_P, "p:cNvCxnSpPr");
        nvCxnSpPr.appendChild(cNvCxnSpPr);
        cxnSp.appendChild(nvCxnSpPr);

        // spPr with xfrm and prstGeom
        Element spPr = doc.createElementNS(NS_P, "p:spPr");
        Element xfrm = doc.createElementNS(NS_A, "a:xfrm");
        Element off = doc.createElementNS(NS_A, "a:off");
        off.setAttribute("x", String.valueOf(Math.round(x * EMU_PER_POINT)));
        off.setAttribute("y", String.valueOf(Math.round(y * EMU_PER_POINT)));
        Element ext = doc.createElementNS(NS_A, "a:ext");
        ext.setAttribute("cx", String.valueOf(Math.round(w * EMU_PER_POINT)));
        ext.setAttribute("cy", String.valueOf(Math.round(h * EMU_PER_POINT)));
        xfrm.appendChild(off);
        xfrm.appendChild(ext);
        spPr.appendChild(xfrm);

        Element prstGeom = doc.createElementNS(NS_A, "a:prstGeom");
        prstGeom.setAttribute("prst", prstName);
        // Add avLst for connectors that have adjustments
        Element avLst = doc.createElementNS(NS_A, "a:avLst");
        if ("bentConnector3".equals(prstName)) {
            Element gd = doc.createElementNS(NS_A, "a:gd");
            gd.setAttribute("name", "adj1");
            gd.setAttribute("fmla", "val 50000");
            avLst.appendChild(gd);
        }
        prstGeom.appendChild(avLst);
        spPr.appendChild(prstGeom);

        cxnSp.appendChild(spPr);
        doc.appendChild(cxnSp);
        return cxnSp;
    }

    /**
     * Creates a simple auto shape XML element ({@code <p:sp>}) with the given id,
     * preset geometry, and position/size in points.
     */
    private GeometryShape createAutoShape(String id, String prstName, double x, double y, double w, double h) {
        // Need a separate doc per shape to avoid "already have a root" issue
        Document shapeDoc;
        try {
            shapeDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Element sp = shapeDoc.createElementNS(NS_P, "p:sp");

        Element nvSpPr = shapeDoc.createElementNS(NS_P, "p:nvSpPr");
        Element cNvPr = shapeDoc.createElementNS(NS_P, "p:cNvPr");
        cNvPr.setAttribute("id", id);
        cNvPr.setAttribute("name", "Shape " + id);
        nvSpPr.appendChild(cNvPr);
        Element cNvSpPr = shapeDoc.createElementNS(NS_P, "p:cNvSpPr");
        nvSpPr.appendChild(cNvSpPr);
        sp.appendChild(nvSpPr);

        Element spPr = shapeDoc.createElementNS(NS_P, "p:spPr");
        Element xfrm = shapeDoc.createElementNS(NS_A, "a:xfrm");
        Element off = shapeDoc.createElementNS(NS_A, "a:off");
        off.setAttribute("x", String.valueOf(Math.round(x * EMU_PER_POINT)));
        off.setAttribute("y", String.valueOf(Math.round(y * EMU_PER_POINT)));
        Element ext = shapeDoc.createElementNS(NS_A, "a:ext");
        ext.setAttribute("cx", String.valueOf(Math.round(w * EMU_PER_POINT)));
        ext.setAttribute("cy", String.valueOf(Math.round(h * EMU_PER_POINT)));
        xfrm.appendChild(off);
        xfrm.appendChild(ext);
        spPr.appendChild(xfrm);

        Element prstGeom = shapeDoc.createElementNS(NS_A, "a:prstGeom");
        prstGeom.setAttribute("prst", prstName);
        spPr.appendChild(prstGeom);

        sp.appendChild(spPr);
        shapeDoc.appendChild(sp);

        return new GeometryShape(sp, null);
    }

    // --- test_add_straight_connector ---

    @Test
    void straightConnector_hasCorrectShapeType() {
        Element xml = createConnectorXml("straightConnector1", 100, 100, 300, 200);
        var conn = new Connector(xml, null);
        assertThat(conn.getShapeType()).isEqualTo(ShapeType.STRAIGHT_CONNECTOR1);
    }

    // --- test_add_auto_shape ---

    @Test
    void autoShape_hasCorrectShapeType() {
        GeometryShape shape = createAutoShape("1", "rect", 50, 50, 200, 100);
        assertThat(shape.getShapeType()).isEqualTo(ShapeType.RECTANGLE);
    }

    @Test
    void autoShape_variousTypes() {
        GeometryShape rect = createAutoShape("1", "rect", 10, 10, 100, 100);
        GeometryShape ellipse = createAutoShape("2", "ellipse", 10, 10, 100, 100);
        GeometryShape triangle = createAutoShape("3", "triangle", 10, 10, 100, 100);

        assertThat(rect.getShapeType()).isEqualTo(ShapeType.RECTANGLE);
        assertThat(ellipse.getShapeType()).isEqualTo(ShapeType.ELLIPSE);
        assertThat(triangle.getShapeType()).isEqualTo(ShapeType.TRIANGLE);
    }

    // --- test_adjustment_properties ---

    @Test
    void adjustmentValues_exposeNameRawValueAngleValue() {
        Element xml = createConnectorXml("bentConnector3", 50, 50, 300, 200);
        var conn = new Connector(xml, null);
        IAdjustValueCollection adjustments = conn.getAdjustments();
        assertThat(adjustments).isNotNull();
        if (adjustments.size() > 0) {
            IAdjustValue adj = adjustments.get(0);
            assertThat(adj.getName()).isNotNull();
            assertThat(adj.getRawValue()).isInstanceOf(Long.class);
            assertThat(adj.getAngleValue()).isInstanceOf(Double.class);
        }
    }

    // --- test_bent_connector_adjustments ---

    @Test
    void bentConnectorAdjustments_persistAfterModification() {
        Element xml = createConnectorXml("bentConnector3", 50, 50, 300, 200);
        var conn = new Connector(xml, null);
        IAdjustValueCollection adjustments = conn.getAdjustments();
        assertThat(adjustments).isNotNull();
        assertThat(adjustments.size()).isGreaterThan(0);

        // Set adjustment value
        adjustments.get(0).setRawValue(30000);

        // Re-read from the same XML (simulates save/reload since XML is mutated in place)
        var conn2 = new Connector(xml, null);
        IAdjustValueCollection adjustments2 = conn2.getAdjustments();
        assertThat(adjustments2).isNotNull();
        assertThat(adjustments2.size()).isGreaterThan(0);
        assertThat(adjustments2.get(0).getRawValue()).isEqualTo(30000);
    }

    // --- test_connect_shapes ---

    @Test
    void connectShapes_startAndEndConnectionsPersist() {
        GeometryShape s1 = createAutoShape("2", "rect", 50, 50, 100, 60);
        GeometryShape s2 = createAutoShape("3", "rect", 350, 200, 100, 60);

        Element connXml = createConnectorXml("bentConnector3", 0, 0, 1, 1);
        var conn = new Connector(connXml, null);
        conn.setParentShapes(List.of(s1, s2));

        conn.setStartShapeConnectedTo(s1);
        conn.setStartShapeConnectionSiteIndex(3);
        conn.setEndShapeConnectedTo(s2);
        conn.setEndShapeConnectionSiteIndex(1);

        assertThat(conn.getStartShapeConnectedTo()).isNotNull();
        assertThat(conn.getEndShapeConnectedTo()).isNotNull();
        assertThat(conn.getStartShapeConnectionSiteIndex()).isEqualTo(3);
        assertThat(conn.getEndShapeConnectionSiteIndex()).isEqualTo(1);
    }

    @Test
    void connectShapes_disconnectBySettingNull() {
        GeometryShape s1 = createAutoShape("2", "rect", 50, 50, 100, 60);

        Element connXml = createConnectorXml("bentConnector3", 0, 0, 1, 1);
        var conn = new Connector(connXml, null);
        conn.setParentShapes(List.of(s1));

        conn.setStartShapeConnectedTo(s1);
        assertThat(conn.getStartShapeConnectedTo()).isNotNull();

        conn.setStartShapeConnectedTo(null);
        assertThat(conn.getStartShapeConnectedTo()).isNull();
    }

    // --- test_reroute ---

    @Test
    void reroute_updatesConnectorPosition() {
        GeometryShape s1 = createAutoShape("2", "ellipse", 50, 100, 80, 80);
        GeometryShape s2 = createAutoShape("3", "ellipse", 400, 100, 80, 80);

        Element connXml = createConnectorXml("bentConnector3", 0, 0, 1, 1);
        var conn = new Connector(connXml, null);
        conn.setParentShapes(List.of(s1, s2));

        conn.setStartShapeConnectedTo(s1);
        conn.setStartShapeConnectionSiteIndex(3);  // right-center of s1
        conn.setEndShapeConnectedTo(s2);
        conn.setEndShapeConnectionSiteIndex(1);     // left-center of s2
        conn.reroute();

        // After reroute the connector should span between the shapes
        assertThat(conn.getWidth() > 0 || conn.getHeight() > 0).isTrue();
    }

    @Test
    void reroute_calculatesCorrectBoundingBox() {
        GeometryShape s1 = createAutoShape("2", "rect", 50, 100, 80, 80);
        GeometryShape s2 = createAutoShape("3", "rect", 400, 100, 80, 80);

        Element connXml = createConnectorXml("straightConnector1", 0, 0, 1, 1);
        var conn = new Connector(connXml, null);
        conn.setParentShapes(List.of(s1, s2));

        conn.setStartShapeConnectedTo(s1);
        conn.setStartShapeConnectionSiteIndex(3);  // right-center of s1: (130, 140)
        conn.setEndShapeConnectedTo(s2);
        conn.setEndShapeConnectionSiteIndex(1);     // left-center of s2: (400, 140)
        conn.reroute();

        // Expected: x=130, y=140, w=270, h=0
        assertThat(conn.getX()).isCloseTo(130, org.assertj.core.data.Offset.offset(1.0));
        assertThat(conn.getY()).isCloseTo(140, org.assertj.core.data.Offset.offset(1.0));
        assertThat(conn.getWidth()).isCloseTo(270, org.assertj.core.data.Offset.offset(1.0));
    }

    @Test
    void reroute_setsFlipWhenStartIsRightOfEnd() {
        GeometryShape s1 = createAutoShape("2", "rect", 400, 100, 80, 80);
        GeometryShape s2 = createAutoShape("3", "rect", 50, 300, 80, 80);

        Element connXml = createConnectorXml("straightConnector1", 0, 0, 1, 1);
        var conn = new Connector(connXml, null);
        conn.setParentShapes(List.of(s1, s2));

        conn.setStartShapeConnectedTo(s1);
        conn.setStartShapeConnectionSiteIndex(3);  // right-center of s1: (480, 140)
        conn.setEndShapeConnectedTo(s2);
        conn.setEndShapeConnectionSiteIndex(1);     // left-center of s2: (50, 340)
        conn.reroute();

        // Start x (480) > End x (50), so flipH should be set
        Element xfrm = conn.ensureXfrm();
        assertThat(xfrm.getAttribute("flipH")).isEqualTo("1");
    }

    // --- Shape type setter ---

    @Test
    void shapeType_setterUpdatesPresetGeometry() {
        Element xml = createConnectorXml("straightConnector1", 100, 100, 300, 200);
        var conn = new Connector(xml, null);
        assertThat(conn.getShapeType()).isEqualTo(ShapeType.STRAIGHT_CONNECTOR1);

        conn.setShapeType(ShapeType.BENT_CONNECTOR3);
        assertThat(conn.getShapeType()).isEqualTo(ShapeType.BENT_CONNECTOR3);
    }

    @Test
    void shapeType_setNotDefinedIsIgnored() {
        Element xml = createConnectorXml("straightConnector1", 100, 100, 300, 200);
        var conn = new Connector(xml, null);
        conn.setShapeType(ShapeType.NOT_DEFINED);
        assertThat(conn.getShapeType()).isEqualTo(ShapeType.STRAIGHT_CONNECTOR1);
    }

    // --- Simple properties ---

    @Test
    void isTextHolder_alwaysFalse() {
        var conn = new Connector();
        assertThat(conn.isTextHolder()).isFalse();
    }

    @Test
    void placeholder_alwaysNull() {
        var conn = new Connector();
        assertThat(conn.getPlaceholder()).isNull();
    }

    @Test
    void customData_alwaysNull() {
        var conn = new Connector();
        assertThat(conn.getCustomData()).isNull();
    }

    @Test
    void shapeStyle_alwaysNull() {
        var conn = new Connector();
        assertThat(conn.getShapeStyle()).isNull();
    }

    @Test
    void connectorLock_alwaysNull() {
        var conn = new Connector();
        assertThat(conn.getConnectorLock()).isNull();
    }

    @Test
    void asIGeometryShape_returnsSelf() {
        var conn = new Connector();
        assertThat(conn.getAsIGeometryShape()).isSameAs(conn);
    }

    // --- Default connection site indices ---

    @Test
    void connectionSiteIndex_defaultsToZero() {
        Element xml = createConnectorXml("straightConnector1", 0, 0, 100, 100);
        var conn = new Connector(xml, null);
        assertThat(conn.getStartShapeConnectionSiteIndex()).isEqualTo(0);
        assertThat(conn.getEndShapeConnectionSiteIndex()).isEqualTo(0);
    }

    // --- Position/size from xfrm ---

    @Test
    void positionAndSize_readFromXfrm() {
        Element xml = createConnectorXml("straightConnector1", 200, 150, 300, 250);
        var conn = new Connector(xml, null);
        assertThat(conn.getX()).isCloseTo(200, org.assertj.core.data.Offset.offset(0.1));
        assertThat(conn.getY()).isCloseTo(150, org.assertj.core.data.Offset.offset(0.1));
        assertThat(conn.getWidth()).isCloseTo(300, org.assertj.core.data.Offset.offset(0.1));
        assertThat(conn.getHeight()).isCloseTo(250, org.assertj.core.data.Offset.offset(0.1));
    }

    // --- No-op reroute when no shapes connected ---

    @Test
    void reroute_noOpWhenNoShapesConnected() {
        Element xml = createConnectorXml("straightConnector1", 100, 100, 200, 150);
        var conn = new Connector(xml, null);
        double origX = conn.getX();
        double origY = conn.getY();
        conn.reroute();
        // Position should not change
        assertThat(conn.getX()).isEqualTo(origX);
        assertThat(conn.getY()).isEqualTo(origY);
    }
}
