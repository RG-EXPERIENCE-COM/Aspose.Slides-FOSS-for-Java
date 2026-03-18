package org.aspose.slides.foss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

/**
 * Tests for shape frame properties: x, y, width, height, rotation.
 *
 * <p>Verifies shape frame property read/write and persistence.</p>
 */
class GeometryShapeFrameTest {

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

    private GeometryShape createAutoShape(double x, double y, double w, double h) {
        Element sp = doc.createElementNS(NS_P, "p:sp");

        Element nvSpPr = doc.createElementNS(NS_P, "p:nvSpPr");
        Element cNvPr = doc.createElementNS(NS_P, "p:cNvPr");
        cNvPr.setAttribute("id", "1");
        cNvPr.setAttribute("name", "Shape");
        nvSpPr.appendChild(cNvPr);
        Element cNvSpPr = doc.createElementNS(NS_P, "p:cNvSpPr");
        nvSpPr.appendChild(cNvSpPr);
        sp.appendChild(nvSpPr);

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
        prstGeom.setAttribute("prst", "rect");
        spPr.appendChild(prstGeom);

        sp.appendChild(spPr);
        doc.appendChild(sp);

        return new GeometryShape(sp, null);
    }

    @Test
    void frameProperties_persistAfterSetAndReRead() {
        var shape = createAutoShape(200, 200, 300, 250);
        shape.setRotation(45);

        // Re-read from the same XML (simulates save/reload)
        var shape2 = new GeometryShape(shape.xmlElement, null);
        assertThat(shape2.getX()).isCloseTo(200, offset(0.1));
        assertThat(shape2.getY()).isCloseTo(200, offset(0.1));
        assertThat(shape2.getWidth()).isCloseTo(300, offset(0.1));
        assertThat(shape2.getHeight()).isCloseTo(250, offset(0.1));
        assertThat(shape2.getRotation()).isCloseTo(45, offset(0.1));
    }

    @Test
    void frameProperties_initialValues() {
        var shape = createAutoShape(200, 200, 300, 250);
        assertThat(shape.getX()).isCloseTo(200, offset(0.1));
        assertThat(shape.getY()).isCloseTo(200, offset(0.1));
        assertThat(shape.getWidth()).isCloseTo(300, offset(0.1));
        assertThat(shape.getHeight()).isCloseTo(250, offset(0.1));
        assertThat(shape.getRotation()).isCloseTo(0, offset(0.1));
    }

    @Test
    void rotation_setAndGet() {
        var shape = createAutoShape(50, 50, 100, 100);
        shape.setRotation(90);
        assertThat(shape.getRotation()).isCloseTo(90, offset(0.1));
    }
}
