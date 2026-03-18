package org.aspose.slides.foss;

import org.aspose.slides.foss.drawing.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link PatternFormat}.
 *
 * <p>Verifies pattern fill format properties and persistence.</p>
 */
class PatternFormatTest {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";

    private Document doc;

    @BeforeEach
    void setUp() throws Exception {
        doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .newDocument();
    }

    private Element createPattFill() {
        Element pattFill = doc.createElementNS(NS_A, "a:pattFill");
        doc.appendChild(pattFill);
        return pattFill;
    }

    @Test
    void patternStyleAndColorsPersist() {
        Element pattFill = createPattFill();
        var pf = new PatternFormat(pattFill, null);

        pf.setPatternStyle(PatternStyle.PERCENT50);
        pf.getForeColor().setColor(new Color(255, 0, 0, 139));   // dark blue
        pf.getBackColor().setColor(new Color(255, 255, 255, 224)); // light yellow

        // Re-read from same XML element (simulates save/reload)
        var pf2 = new PatternFormat(pattFill, null);
        assertThat(pf2.getPatternStyle()).isEqualTo(PatternStyle.PERCENT50);

        Color fg = pf2.getForeColor().getColor();
        assertThat(fg.getR()).isEqualTo(0);
        assertThat(fg.getG()).isEqualTo(0);
        assertThat(fg.getB()).isEqualTo(139);

        Color bg = pf2.getBackColor().getColor();
        assertThat(bg.getR()).isEqualTo(255);
        assertThat(bg.getG()).isEqualTo(255);
        assertThat(bg.getB()).isEqualTo(224);
    }

    @Test
    void defaultPatternStyleIsNotDefined() {
        Element pattFill = createPattFill();
        var pf = new PatternFormat(pattFill, null);
        assertThat(pf.getPatternStyle()).isEqualTo(PatternStyle.NOT_DEFINED);
    }

    @Test
    void setPatternStyleNotDefined_removesAttribute() {
        Element pattFill = createPattFill();
        var pf = new PatternFormat(pattFill, null);

        pf.setPatternStyle(PatternStyle.PERCENT50);
        assertThat(pf.getPatternStyle()).isEqualTo(PatternStyle.PERCENT50);

        pf.setPatternStyle(PatternStyle.NOT_DEFINED);
        assertThat(pf.getPatternStyle()).isEqualTo(PatternStyle.NOT_DEFINED);
    }

    @Test
    void foreColorPersists() {
        Element pattFill = createPattFill();
        var pf = new PatternFormat(pattFill, null);
        pf.getForeColor().setColor(new Color(255, 128, 64, 32));

        var pf2 = new PatternFormat(pattFill, null);
        Color c = pf2.getForeColor().getColor();
        assertThat(c.getR()).isEqualTo(128);
        assertThat(c.getG()).isEqualTo(64);
        assertThat(c.getB()).isEqualTo(32);
    }

    @Test
    void backColorPersists() {
        Element pattFill = createPattFill();
        var pf = new PatternFormat(pattFill, null);
        pf.getBackColor().setColor(new Color(255, 10, 20, 30));

        var pf2 = new PatternFormat(pattFill, null);
        Color c = pf2.getBackColor().getColor();
        assertThat(c.getR()).isEqualTo(10);
        assertThat(c.getG()).isEqualTo(20);
        assertThat(c.getB()).isEqualTo(30);
    }

    @Test
    void saveCallbackInvokedOnPatternStyleChange() {
        Element pattFill = createPattFill();
        int[] callCount = {0};
        var pf = new PatternFormat(pattFill, () -> callCount[0]++);

        pf.setPatternStyle(PatternStyle.DIAGONAL_CROSS);
        assertThat(callCount[0]).isGreaterThan(0);
    }
}
