package org.aspose.slides.foss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for the {@link IGeometryShape} contract: shapeStyle, shapeType, and adjustments.
 *
 * <p>Covers auto shape creation, multiple shape types, straight connectors,
 * adjustment properties, and bent connector adjustments.</p>
 */
class IGeometryShapeTest {

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
     * Creates a GeometryShape backed by a {@code <p:sp>} element with preset geometry.
     */
    private GeometryShape createGeometryShape(String id, String prstName,
                                               double x, double y, double w, double h) {
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

    /**
     * Creates a connector XML element with preset geometry and optional adjustment values.
     */
    private Element createConnectorXml(String prstName, double x, double y, double w, double h) {
        Element cxnSp = doc.createElementNS(NS_P, "p:cxnSp");

        Element nvCxnSpPr = doc.createElementNS(NS_P, "p:nvCxnSpPr");
        Element cNvPr = doc.createElementNS(NS_P, "p:cNvPr");
        cNvPr.setAttribute("id", "100");
        cNvPr.setAttribute("name", "Connector");
        nvCxnSpPr.appendChild(cNvPr);
        Element cNvCxnSpPr = doc.createElementNS(NS_P, "p:cNvCxnSpPr");
        nvCxnSpPr.appendChild(cNvCxnSpPr);
        cxnSp.appendChild(nvCxnSpPr);

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

    // --- shapeStyle ---

    @Test
    void shapeStyle_returnsNullByDefault() {
        IGeometryShape shape = createGeometryShape("1", "rect", 50, 50, 200, 100);
        assertThat(shape.getShapeStyle()).isNull();
    }

    @Test
    void shapeStyle_returnsNullForConnector() {
        Element xml = createConnectorXml("straightConnector1", 100, 100, 300, 200);
        IGeometryShape conn = new Connector(xml, null);
        assertThat(conn.getShapeStyle()).isNull();
    }

    // --- shapeType (test_add_auto_shape) ---

    @Test
    void shapeType_rectangleIsPreserved() {
        IGeometryShape shape = createGeometryShape("1", "rect", 50, 50, 200, 100);
        assertThat(shape.getShapeType()).isEqualTo(ShapeType.RECTANGLE);
    }

    // --- shapeType (test_multiple_shape_types) ---

    @Test
    void shapeType_variousTypesPreserved() {
        IGeometryShape rect = createGeometryShape("1", "rect", 10, 10, 100, 100);
        IGeometryShape ellipse = createGeometryShape("2", "ellipse", 10, 10, 100, 100);
        IGeometryShape triangle = createGeometryShape("3", "triangle", 10, 10, 100, 100);

        assertThat(rect.getShapeType()).isEqualTo(ShapeType.RECTANGLE);
        assertThat(ellipse.getShapeType()).isEqualTo(ShapeType.ELLIPSE);
        assertThat(triangle.getShapeType()).isEqualTo(ShapeType.TRIANGLE);
    }

    // --- shapeType setter resets adjustments ---

    @Test
    void shapeType_setterUpdatesType() {
        IGeometryShape shape = createGeometryShape("1", "rect", 50, 50, 200, 100);
        shape.setShapeType(ShapeType.ELLIPSE);
        assertThat(shape.getShapeType()).isEqualTo(ShapeType.ELLIPSE);
    }

    @Test
    void shapeType_setNotDefinedIsIgnored() {
        IGeometryShape shape = createGeometryShape("1", "rect", 50, 50, 200, 100);
        shape.setShapeType(ShapeType.NOT_DEFINED);
        assertThat(shape.getShapeType()).isEqualTo(ShapeType.RECTANGLE);
    }

    // --- shapeType for connectors (test_add_straight_connector) ---

    @Test
    void shapeType_straightConnector() {
        Element xml = createConnectorXml("straightConnector1", 100, 100, 300, 200);
        IGeometryShape conn = new Connector(xml, null);
        assertThat(conn.getShapeType()).isEqualTo(ShapeType.STRAIGHT_CONNECTOR1);
    }

    // --- adjustments (test_adjustment_properties) ---

    @Test
    void adjustments_exposeNameRawValueAngleValue() {
        Element xml = createConnectorXml("bentConnector3", 50, 50, 300, 200);
        IGeometryShape conn = new Connector(xml, null);
        IAdjustValueCollection adjustments = conn.getAdjustments();
        assertThat(adjustments).isNotNull();
        assertThat(adjustments.size()).isGreaterThan(0);

        IAdjustValue adj = adjustments.get(0);
        assertThat(adj.getName()).isNotNull();
        assertThat(adj.getRawValue()).isInstanceOf(Long.class);
        assertThat(adj.getAngleValue()).isInstanceOf(Double.class);
    }

    // --- adjustments (test_bent_connector_adjustments) ---

    @Test
    void adjustments_persistAfterModification() {
        Element xml = createConnectorXml("bentConnector3", 50, 50, 300, 200);
        IGeometryShape conn = new Connector(xml, null);
        IAdjustValueCollection adjustments = conn.getAdjustments();
        assertThat(adjustments).isNotNull();
        assertThat(adjustments.size()).isGreaterThan(0);

        adjustments.get(0).setRawValue(30000);

        // Re-read from same XML (simulates save/reload)
        IGeometryShape conn2 = new Connector(xml, null);
        IAdjustValueCollection adjustments2 = conn2.getAdjustments();
        assertThat(adjustments2).isNotNull();
        assertThat(adjustments2.size()).isGreaterThan(0);
        assertThat(adjustments2.get(0).getRawValue()).isEqualTo(30000);
    }

    // --- adjustments for shape without avLst ---

    @Test
    void adjustments_nullWhenNoPrstGeom() {
        IGeometryShape shape = new GeometryShape();
        assertThat(shape.getAdjustments()).isNull();
    }

    @Test
    void adjustments_emptyForSimplePresetGeometry() {
        IGeometryShape shape = createGeometryShape("1", "rect", 0, 0, 100, 100);
        IAdjustValueCollection adj = shape.getAdjustments();
        assertThat(adj).isNotNull();
        assertThat(adj.size()).isEqualTo(0);
    }

    // --- shapeType returns NOT_DEFINED for null element ---

    @Test
    void shapeType_notDefinedForNullElement() {
        IGeometryShape shape = new GeometryShape();
        assertThat(shape.getShapeType()).isEqualTo(ShapeType.NOT_DEFINED);
    }
}
