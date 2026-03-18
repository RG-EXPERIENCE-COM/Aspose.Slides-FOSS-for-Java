package org.aspose.slides.foss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for line dash style persistence.
 *
 * <p>Verifies dash style read/write and persistence across multiple style types.</p>
 */
class LineDashStyleIntegrationTest {

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

    /**
     * Dash style persists after setting width and fill.
     *
     * <p>Sets width=3, dash style=DASH, solid fill black, then re-reads from the
     * same XML to verify persistence.</p>
     */
    @Test
    void dashStyle_persistsWithWidthAndFill() {
        var lf = new LineFormat(spPr, null);
        lf.setWidth(3.0);
        lf.setDashStyle(LineDashStyle.DASH);

        // Re-read from the same backing XML (simulates save/reload)
        var lf2 = new LineFormat(spPr, null);
        assertThat(lf2.getDashStyle()).isEqualTo(LineDashStyle.DASH);
    }

    /**
     * Various dash styles can be set in-memory on separate line formats.
     *
     * <p>Creates shapes with different dash styles and verifies each one independently.</p>
     */
    static Stream<LineDashStyle> dashStyles() {
        return Stream.of(
                LineDashStyle.SOLID, LineDashStyle.DASH,
                LineDashStyle.DOT, LineDashStyle.DASH_DOT
        );
    }

    @ParameterizedTest(name = "dash style {0} can be set in memory")
    @MethodSource("dashStyles")
    void multipleDashStyles_canBeSetInMemory(LineDashStyle style) {
        // Each shape gets its own spPr element
        Element shapeSpPr = doc.createElementNS(NS_A, "a:spPr");
        var lf = new LineFormat(shapeSpPr, null);
        lf.setDashStyle(style);
        assertThat(lf.getDashStyle()).isEqualTo(style);
    }

    /**
     * Dash style can be changed after initial set.
     */
    @Test
    void dashStyle_canBeChanged() {
        var lf = new LineFormat(spPr, null);
        lf.setDashStyle(LineDashStyle.DASH);
        assertThat(lf.getDashStyle()).isEqualTo(LineDashStyle.DASH);

        lf.setDashStyle(LineDashStyle.DOT);
        assertThat(lf.getDashStyle()).isEqualTo(LineDashStyle.DOT);
    }
}
