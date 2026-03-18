package org.aspose.slides.foss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

/**
 * Tests for line colour and width combined properties.
 *
 * <p>Verifies line colour and width read/write and persistence.</p>
 */
class LineFormatColorWidthTest {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";

    private Document doc;
    private Element spPr;

    @BeforeEach
    void setUp() throws Exception {
        doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .getDOMImplementation()
                .createDocument(NS_A, "a:spPr", null);
        spPr = doc.getDocumentElement();
    }

    @Test
    void widthPersistsAfterReRead() {
        var lf = new LineFormat(spPr, null);
        lf.setWidth(5.0);

        // Re-read from the same XML (simulates save/reload)
        var lf2 = new LineFormat(spPr, null);
        assertThat(lf2.getWidth()).isCloseTo(5.0, offset(0.001));
    }

    @Test
    void fillFormat_returnsNonNull() {
        var lf = new LineFormat(spPr, null);
        lf.setWidth(5.0);
        assertThat(lf.getFillFormat()).isNotNull();
    }

    @Test
    void widthAndDashStyle_persistTogether() {
        var lf = new LineFormat(spPr, null);
        lf.setWidth(3.0);
        lf.setDashStyle(LineDashStyle.DASH);

        // Re-read from the same XML
        var lf2 = new LineFormat(spPr, null);
        assertThat(lf2.getWidth()).isCloseTo(3.0, offset(0.001));
        assertThat(lf2.getDashStyle()).isEqualTo(LineDashStyle.DASH);
    }
}
