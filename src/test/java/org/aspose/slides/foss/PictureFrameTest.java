package org.aspose.slides.foss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link PictureFrame}: shape type, picture format, locks, relative scale,
 * and cameo properties.
 *
 * <p>Covers shape type correctness, type preservation, shape identity, persistence,
 * and property round-trip behavior.</p>
 */
class PictureFrameTest {

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
     * Creates a picture frame XML element ({@code <p:pic>}) with the given preset geometry
     * and position/size in points, including nvPicPr and blipFill.
     */
    private Element createPicXml(String prstName, double x, double y, double w, double h) {
        Element pic = doc.createElementNS(NS_P, "p:pic");

        // nvPicPr with cNvPr and cNvPicPr
        Element nvPicPr = doc.createElementNS(NS_P, "p:nvPicPr");
        Element cNvPr = doc.createElementNS(NS_P, "p:cNvPr");
        cNvPr.setAttribute("id", "10");
        cNvPr.setAttribute("name", "Picture 1");
        nvPicPr.appendChild(cNvPr);
        Element cNvPicPr = doc.createElementNS(NS_P, "p:cNvPicPr");
        nvPicPr.appendChild(cNvPicPr);
        pic.appendChild(nvPicPr);

        // blipFill with a:blip and a:stretch
        Element blipFill = doc.createElementNS(NS_P, "p:blipFill");
        Element blip = doc.createElementNS(NS_A, "a:blip");
        blipFill.appendChild(blip);
        Element stretch = doc.createElementNS(NS_A, "a:stretch");
        blipFill.appendChild(stretch);
        pic.appendChild(blipFill);

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

        if (prstName != null) {
            Element prstGeom = doc.createElementNS(NS_A, "a:prstGeom");
            prstGeom.setAttribute("prst", prstName);
            spPr.appendChild(prstGeom);
        }

        pic.appendChild(spPr);
        doc.appendChild(pic);
        return pic;
    }

    /**
     * Creates a minimal picture frame XML without prstGeom.
     */
    private Element createPicXmlNoPrst(double x, double y, double w, double h) {
        return createPicXml(null, x, y, w, h);
    }

    // --- Shape type tests ---

    @Test
    void shapeType_defaultsToRectangle() {
        Element xml = createPicXml("rect", 50, 50, 200, 100);
        var pf = new PictureFrame(xml, null);
        assertThat(pf.getShapeType()).isEqualTo(ShapeType.RECTANGLE);
    }

    @Test
    void shapeType_defaultsToRectangleWhenNoPrstGeom() {
        Element xml = createPicXmlNoPrst(50, 50, 200, 100);
        var pf = new PictureFrame(xml, null);
        assertThat(pf.getShapeType()).isEqualTo(ShapeType.RECTANGLE);
    }

    @Test
    void shapeType_defaultsToRectangleWithNullElement() {
        var pf = new PictureFrame();
        assertThat(pf.getShapeType()).isEqualTo(ShapeType.RECTANGLE);
    }

    @Test
    void shapeType_preservesEllipse() {
        Element xml = createPicXml("ellipse", 10, 10, 100, 100);
        var pf = new PictureFrame(xml, null);
        assertThat(pf.getShapeType()).isEqualTo(ShapeType.ELLIPSE);
    }

    @Test
    void shapeType_preservesRoundRect() {
        Element xml = createPicXml("roundRect", 10, 10, 100, 100);
        var pf = new PictureFrame(xml, null);
        assertThat(pf.getShapeType()).isEqualTo(ShapeType.ROUND_CORNER_RECTANGLE);
    }

    // --- Shape type setter ---

    @Test
    void shapeType_setterUpdatesPresetGeometry() {
        Element xml = createPicXml("rect", 100, 100, 200, 150);
        var pf = new PictureFrame(xml, null);
        assertThat(pf.getShapeType()).isEqualTo(ShapeType.RECTANGLE);

        pf.setShapeType(ShapeType.ELLIPSE);
        assertThat(pf.getShapeType()).isEqualTo(ShapeType.ELLIPSE);
    }

    @Test
    void shapeType_setterPersistsInXml() {
        Element xml = createPicXml("rect", 100, 100, 200, 150);
        var pf = new PictureFrame(xml, null);
        pf.setShapeType(ShapeType.ROUND_CORNER_RECTANGLE);

        // Re-read from the same XML (simulates save/reload)
        var pf2 = new PictureFrame(xml, null);
        assertThat(pf2.getShapeType()).isEqualTo(ShapeType.ROUND_CORNER_RECTANGLE);
    }

    @Test
    void shapeType_setterDefaultsToRectForUnmapped() {
        Element xml = createPicXml("rect", 100, 100, 200, 150);
        var pf = new PictureFrame(xml, null);
        // NOT_DEFINED has no OOXML mapping, so should fall back to "rect"
        pf.setShapeType(ShapeType.NOT_DEFINED);
        assertThat(pf.getShapeType()).isEqualTo(ShapeType.RECTANGLE);
    }

    @Test
    void shapeType_setterNoOpWithNullElement() {
        var pf = new PictureFrame();
        pf.setShapeType(ShapeType.ELLIPSE); // should not throw
        assertThat(pf.getShapeType()).isEqualTo(ShapeType.RECTANGLE);
    }

    // --- Position/size persistence ---

    @Test
    void positionAndSize_readFromXfrm() {
        Element xml = createPicXml("rect", 200, 150, 300, 250);
        var pf = new PictureFrame(xml, null);
        assertThat(pf.getX()).isCloseTo(200, org.assertj.core.data.Offset.offset(0.1));
        assertThat(pf.getY()).isCloseTo(150, org.assertj.core.data.Offset.offset(0.1));
        assertThat(pf.getWidth()).isCloseTo(300, org.assertj.core.data.Offset.offset(0.1));
        assertThat(pf.getHeight()).isCloseTo(250, org.assertj.core.data.Offset.offset(0.1));
    }

    @Test
    void positionAndSize_persistAfterMutation() {
        Element xml = createPicXml("rect", 50, 50, 200, 100);
        var pf = new PictureFrame(xml, null);
        pf.setX(200);
        pf.setY(200);
        pf.setWidth(300);
        pf.setHeight(250);
        pf.setRotation(45);

        // Re-read from the same XML
        var pf2 = new PictureFrame(xml, null);
        assertThat(pf2.getX()).isCloseTo(200, org.assertj.core.data.Offset.offset(0.1));
        assertThat(pf2.getY()).isCloseTo(200, org.assertj.core.data.Offset.offset(0.1));
        assertThat(pf2.getWidth()).isCloseTo(300, org.assertj.core.data.Offset.offset(0.1));
        assertThat(pf2.getHeight()).isCloseTo(250, org.assertj.core.data.Offset.offset(0.1));
        assertThat(pf2.getRotation()).isCloseTo(45, org.assertj.core.data.Offset.offset(0.1));
    }

    // --- Picture frame lock ---

    @Test
    void pictureFrameLock_returnsNonNullWhenXmlPresent() {
        Element xml = createPicXml("rect", 50, 50, 200, 100);
        var pf = new PictureFrame(xml, null);
        assertThat(pf.getPictureFrameLock()).isNotNull();
    }

    @Test
    void pictureFrameLock_returnsNullWhenNoXml() {
        var pf = new PictureFrame();
        assertThat(pf.getPictureFrameLock()).isNull();
    }

    @Test
    void pictureFrameLock_createsPicLocksIfMissing() {
        Element xml = createPicXml("rect", 50, 50, 200, 100);
        var pf = new PictureFrame(xml, null);
        // First call should create the <a:picLocks> element
        IPictureFrameLock lock = pf.getPictureFrameLock();
        assertThat(lock).isNotNull();

        // Verify the element was actually created in the XML
        Element nvPicPr = findChild(xml, NS_P, "nvPicPr");
        assertThat(nvPicPr).isNotNull();
        Element cNvPicPr = findChild(nvPicPr, NS_P, "cNvPicPr");
        assertThat(cNvPicPr).isNotNull();
        Element picLocks = findChild(cNvPicPr, NS_A, "picLocks");
        assertThat(picLocks).isNotNull();
    }

    // --- Picture format ---

    @Test
    void pictureFormat_returnsNonNullWhenBlipFillPresent() {
        Element xml = createPicXml("rect", 50, 50, 200, 100);
        var pf = new PictureFrame(xml, null);
        assertThat(pf.getPictureFormat()).isNotNull();
    }

    @Test
    void pictureFormat_returnsNullWhenNoXml() {
        var pf = new PictureFrame();
        assertThat(pf.getPictureFormat()).isNull();
    }

    @Test
    void pictureFormat_returnsNullWhenNoBlipFill() throws Exception {
        // Create a pic element without blipFill
        Document d = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element pic = d.createElementNS(NS_P, "p:pic");
        Element spPr = d.createElementNS(NS_P, "p:spPr");
        pic.appendChild(spPr);
        d.appendChild(pic);

        var pf = new PictureFrame(pic, null);
        assertThat(pf.getPictureFormat()).isNull();
    }

    @Test
    void pictureFormat_fillModeIsStretch() {
        Element xml = createPicXml("rect", 50, 50, 200, 100);
        var pf = new PictureFrame(xml, null);
        IPictureFillFormat fmt = pf.getPictureFormat();
        assertThat(fmt).isNotNull();
        assertThat(fmt.getPictureFillMode()).isEqualTo(PictureFillMode.STRETCH);
    }

    // --- Relative scale height/width ---

    @Test
    void relativeScaleHeight_defaultsToOne() {
        var pf = new PictureFrame();
        assertThat(pf.getRelativeScaleHeight()).isEqualTo(1.0);
    }

    @Test
    void relativeScaleHeight_setAndGet() {
        var pf = new PictureFrame();
        pf.setRelativeScaleHeight(0.5);
        assertThat(pf.getRelativeScaleHeight()).isEqualTo(0.5);
    }

    @Test
    void relativeScaleWidth_defaultsToOne() {
        var pf = new PictureFrame();
        assertThat(pf.getRelativeScaleWidth()).isEqualTo(1.0);
    }

    @Test
    void relativeScaleWidth_setAndGet() {
        var pf = new PictureFrame();
        pf.setRelativeScaleWidth(2.0);
        assertThat(pf.getRelativeScaleWidth()).isEqualTo(2.0);
    }

    // --- Cameo ---

    @Test
    void isCameo_alwaysFalse() {
        var pf = new PictureFrame();
        assertThat(pf.isCameo()).isFalse();
    }

    @Test
    void isCameo_alwaysFalseWithXml() {
        Element xml = createPicXml("rect", 50, 50, 200, 100);
        var pf = new PictureFrame(xml, null);
        assertThat(pf.isCameo()).isFalse();
    }

    // --- cNvPr ---

    @Test
    void cNvPr_returnsElementFromNvPicPr() {
        Element xml = createPicXml("rect", 50, 50, 200, 100);
        var pf = new PictureFrame(xml, null);
        Element cNvPr = pf.getCNvPr();
        assertThat(cNvPr).isNotNull();
        assertThat(cNvPr.getAttribute("id")).isEqualTo("10");
        assertThat(cNvPr.getAttribute("name")).isEqualTo("Picture 1");
    }

    @Test
    void cNvPr_returnsNullWhenNoXml() {
        var pf = new PictureFrame();
        assertThat(pf.getCNvPr()).isNull();
    }

    // --- Helper ---

    private static Element findChild(Element parent, String nsUri, String localName) {
        if (parent == null) return null;
        var children = parent.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i) instanceof Element el) {
                if (nsUri.equals(el.getNamespaceURI()) && localName.equals(el.getLocalName())) {
                    return el;
                }
            }
        }
        return null;
    }
}
