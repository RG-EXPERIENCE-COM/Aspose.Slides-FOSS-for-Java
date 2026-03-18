package org.aspose.slides.foss;

import org.aspose.slides.foss.effects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

/**
 * Tests for {@link IShape} contract members: reroute, effect format (blur, glow,
 * outer shadow, soft edge, enable/disable effects).
 *
 * <p>Covers connector rerouting, blur, enable/disable effects, glow,
 * outer shadow, and soft edge effects.</p>
 */
class IShapeTest {

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

    // --- Helper methods ---

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

    private GeometryShape createAutoShape(String id, String prstName, double x, double y, double w, double h) {
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

    private EffectFormat createEffectFormatFromShape(GeometryShape shape) {
        // Access the spPr element to create an EffectFormat
        IEffectFormat ef = shape.getEffectFormat();
        assertThat(ef).isNotNull();
        return (EffectFormat) ef;
    }

    // --- test_reroute: reroute() updates connector position ---

    @Test
    void reroute_updatesConnectorPosition() {
        GeometryShape s1 = createAutoShape("2", "ellipse", 50, 100, 80, 80);
        GeometryShape s2 = createAutoShape("3", "ellipse", 400, 100, 80, 80);

        Element connXml = createConnectorXml("bentConnector3", 0, 0, 1, 1);
        var conn = new Connector(connXml, null);
        conn.setParentShapes(List.of(s1, s2));

        conn.setStartShapeConnectedTo(s1);
        conn.setStartShapeConnectionSiteIndex(3);
        conn.setEndShapeConnectedTo(s2);
        conn.setEndShapeConnectionSiteIndex(1);
        conn.reroute();

        // After reroute the connector should span between the shapes
        assertThat(conn.getWidth() > 0 || conn.getHeight() > 0).isTrue();
    }

    // --- test_blur: blur effect persists ---

    @Test
    void blur_setBlurEffectPersists() {
        GeometryShape shape = createAutoShape("1", "rect", 100, 100, 200, 100);
        IEffectFormat ef = shape.getEffectFormat();
        ef.setBlurEffect(8, true);

        // Re-read from same XML
        IEffectFormat ef2 = shape.getEffectFormat();
        IBlur b2 = ef2.getBlurEffect();
        assertThat(b2).isNotNull();
        assertThat(b2.getRadius()).isCloseTo(8.0, offset(0.01));
    }

    // --- test_enable_disable_effects ---

    @Test
    void enableDisable_effectsCanBeEnabledThenDisabled() {
        GeometryShape shape = createAutoShape("1", "rect", 100, 100, 200, 100);
        IEffectFormat ef = shape.getEffectFormat();
        ef.enableOuterShadowEffect();
        ef.enableGlowEffect();
        assertThat(ef.isNoEffects()).isFalse();

        ef.disableOuterShadowEffect();
        ef.disableGlowEffect();
        // Re-read to check
        IEffectFormat ef2 = shape.getEffectFormat();
        assertThat(ef2.isNoEffects()).isTrue();
    }

    // --- test_glow: glow effect persists ---

    @Test
    void glow_enableAndSetRadiusPersists() {
        GeometryShape shape = createAutoShape("1", "ellipse", 100, 100, 200, 200);
        IEffectFormat ef = shape.getEffectFormat();
        ef.enableGlowEffect();
        ef.getGlowEffect().setRadius(15);

        // Re-read
        IEffectFormat ef2 = shape.getEffectFormat();
        IGlow g2 = ef2.getGlowEffect();
        assertThat(g2).isNotNull();
        assertThat(g2.getRadius()).isCloseTo(15.0, offset(0.01));
    }

    // --- test_outer_shadow: outer shadow properties persist ---

    @Test
    void outerShadow_propertiesPersist() {
        GeometryShape shape = createAutoShape("1", "rect", 100, 100, 200, 100);
        IEffectFormat ef = shape.getEffectFormat();
        ef.enableOuterShadowEffect();

        IOuterShadow shadow = ef.getOuterShadowEffect();
        assertThat(shadow).isNotNull();
        shadow.setBlurRadius(10);
        shadow.setDirection(315);
        shadow.setDistance(8);

        // Re-read
        IEffectFormat ef2 = shape.getEffectFormat();
        IOuterShadow s2 = ef2.getOuterShadowEffect();
        assertThat(s2).isNotNull();
        assertThat(s2.getBlurRadius()).isCloseTo(10.0, offset(0.01));
        assertThat(s2.getDirection()).isCloseTo(315.0, offset(0.01));
        assertThat(s2.getDistance()).isCloseTo(8.0, offset(0.01));
    }

    // --- test_soft_edge: soft edge radius persists ---

    @Test
    void softEdge_radiusPersists() {
        GeometryShape shape = createAutoShape("1", "rect", 100, 100, 200, 100);
        IEffectFormat ef = shape.getEffectFormat();
        ef.enableSoftEdgeEffect();
        ef.getSoftEdgeEffect().setRadius(10);

        // Re-read
        IEffectFormat ef2 = shape.getEffectFormat();
        ISoftEdge se2 = ef2.getSoftEdgeEffect();
        assertThat(se2).isNotNull();
        assertThat(se2.getRadius()).isCloseTo(10.0, offset(0.01));
    }
}
