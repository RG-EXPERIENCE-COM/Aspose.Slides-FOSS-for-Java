package org.aspose.slides.foss;

import org.aspose.slides.foss.drawing.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link LineFormat}: width, dash style, cap style, join style,
 * alignment, arrowheads, miter limit, and custom dash patterns.
 *
 * <p>Covers line formatting properties including connector and fill format integration.</p>
 */
class LineFormatTest {

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

    private LineFormat createLineFormat() {
        return new LineFormat(spPr, null);
    }

    private LineFormat createLineFormatWithCallback(Runnable callback) {
        return new LineFormat(spPr, callback);
    }

    // --- isFormatNotDefined ---

    @Test
    void isFormatNotDefined_trueWhenNoLnElement() {
        var lf = createLineFormat();
        assertThat(lf.isFormatNotDefined()).isTrue();
    }

    @Test
    void isFormatNotDefined_trueWhenEmptyLnElement() {
        doc.getDocumentElement().appendChild(doc.createElementNS(NS_A, "a:ln"));
        var lf = createLineFormat();
        assertThat(lf.isFormatNotDefined()).isTrue();
    }

    @Test
    void isFormatNotDefined_falseWhenLnHasAttributes() {
        Element ln = doc.createElementNS(NS_A, "a:ln");
        ln.setAttribute("w", "12700");
        spPr.appendChild(ln);
        var lf = createLineFormat();
        assertThat(lf.isFormatNotDefined()).isFalse();
    }

    @Test
    void isFormatNotDefined_falseWhenLnHasChildren() {
        Element ln = doc.createElementNS(NS_A, "a:ln");
        ln.appendChild(doc.createElementNS(NS_A, "a:solidFill"));
        spPr.appendChild(ln);
        var lf = createLineFormat();
        assertThat(lf.isFormatNotDefined()).isFalse();
    }

    // --- width ---

    @Test
    void width_defaultIs075Points() {
        var lf = createLineFormat();
        assertThat(lf.getWidth()).isEqualTo(0.75);
    }

    @Test
    void width_readsFromEmu() {
        Element ln = doc.createElementNS(NS_A, "a:ln");
        ln.setAttribute("w", "25400"); // 2.0 points
        spPr.appendChild(ln);
        var lf = createLineFormat();
        assertThat(lf.getWidth()).isCloseTo(2.0, org.assertj.core.data.Offset.offset(0.001));
    }

    @Test
    void width_setConvertsToEmu() {
        var lf = createLineFormat();
        lf.setWidth(3.0);
        assertThat(lf.getWidth()).isCloseTo(3.0, org.assertj.core.data.Offset.offset(0.001));
    }

    @Test
    void width_roundTrip() {
        var lf = createLineFormat();
        lf.setWidth(1.5);
        assertThat(lf.getWidth()).isCloseTo(1.5, org.assertj.core.data.Offset.offset(0.001));
        assertThat(lf.isFormatNotDefined()).isFalse();
    }

    // --- dash style ---

    @Test
    void dashStyle_defaultIsNotDefined() {
        var lf = createLineFormat();
        assertThat(lf.getDashStyle()).isEqualTo(LineDashStyle.NOT_DEFINED);
    }

    @Test
    void dashStyle_readsSolidPreset() {
        Element ln = doc.createElementNS(NS_A, "a:ln");
        Element prstDash = doc.createElementNS(NS_A, "a:prstDash");
        prstDash.setAttribute("val", "dash");
        ln.appendChild(prstDash);
        spPr.appendChild(ln);
        var lf = createLineFormat();
        assertThat(lf.getDashStyle()).isEqualTo(LineDashStyle.DASH);
    }

    @Test
    void dashStyle_setAndGet() {
        var lf = createLineFormat();
        lf.setDashStyle(LineDashStyle.DOT);
        assertThat(lf.getDashStyle()).isEqualTo(LineDashStyle.DOT);
    }

    @Test
    void dashStyle_setCustomCreatesElement() {
        var lf = createLineFormat();
        lf.setDashStyle(LineDashStyle.CUSTOM);
        assertThat(lf.getDashStyle()).isEqualTo(LineDashStyle.CUSTOM);
    }

    @Test
    void dashStyle_setNotDefinedRemovesElement() {
        var lf = createLineFormat();
        lf.setDashStyle(LineDashStyle.DASH);
        lf.setDashStyle(LineDashStyle.NOT_DEFINED);
        assertThat(lf.getDashStyle()).isEqualTo(LineDashStyle.NOT_DEFINED);
    }

    static Stream<LineDashStyle> lineDashStyles() {
        return Stream.of(
                LineDashStyle.SOLID, LineDashStyle.DOT, LineDashStyle.DASH,
                LineDashStyle.LARGE_DASH, LineDashStyle.DASH_DOT,
                LineDashStyle.LARGE_DASH_DOT, LineDashStyle.LARGE_DASH_DOT_DOT,
                LineDashStyle.SYSTEM_DASH, LineDashStyle.SYSTEM_DOT,
                LineDashStyle.SYSTEM_DASH_DOT, LineDashStyle.SYSTEM_DASH_DOT_DOT);
    }

    @ParameterizedTest
    @MethodSource("lineDashStyles")
    void dashStyle_allPresetsRoundTrip(LineDashStyle style) {
        var lf = createLineFormat();
        lf.setDashStyle(style);
        assertThat(lf.getDashStyle()).isEqualTo(style);
    }

    // --- custom dash pattern ---

    @Test
    void customDashPattern_emptyByDefault() {
        var lf = createLineFormat();
        assertThat(lf.getCustomDashPattern()).isEmpty();
    }

    @Test
    void customDashPattern_setAndGet() {
        var lf = createLineFormat();
        lf.setCustomDashPattern(List.of(2.0, 1.0, 0.5, 0.5));
        List<Double> pattern = lf.getCustomDashPattern();
        assertThat(pattern).hasSize(4);
        assertThat(pattern.get(0)).isCloseTo(2.0, org.assertj.core.data.Offset.offset(0.001));
        assertThat(pattern.get(1)).isCloseTo(1.0, org.assertj.core.data.Offset.offset(0.001));
        assertThat(pattern.get(2)).isCloseTo(0.5, org.assertj.core.data.Offset.offset(0.001));
        assertThat(pattern.get(3)).isCloseTo(0.5, org.assertj.core.data.Offset.offset(0.001));
    }

    // --- cap style ---

    @Test
    void capStyle_defaultIsNotDefined() {
        var lf = createLineFormat();
        assertThat(lf.getCapStyle()).isEqualTo(LineCapStyle.NOT_DEFINED);
    }

    @Test
    void capStyle_roundTrip() {
        var lf = createLineFormat();
        lf.setCapStyle(LineCapStyle.ROUND);
        assertThat(lf.getCapStyle()).isEqualTo(LineCapStyle.ROUND);
        lf.setCapStyle(LineCapStyle.SQUARE);
        assertThat(lf.getCapStyle()).isEqualTo(LineCapStyle.SQUARE);
        lf.setCapStyle(LineCapStyle.FLAT);
        assertThat(lf.getCapStyle()).isEqualTo(LineCapStyle.FLAT);
    }

    @Test
    void capStyle_setNotDefinedRemovesAttribute() {
        var lf = createLineFormat();
        lf.setCapStyle(LineCapStyle.ROUND);
        lf.setCapStyle(LineCapStyle.NOT_DEFINED);
        assertThat(lf.getCapStyle()).isEqualTo(LineCapStyle.NOT_DEFINED);
    }

    // --- line style ---

    @Test
    void style_defaultIsNotDefined() {
        var lf = createLineFormat();
        assertThat(lf.getStyle()).isEqualTo(LineStyle.NOT_DEFINED);
    }

    @Test
    void style_roundTrip() {
        var lf = createLineFormat();
        lf.setStyle(LineStyle.SINGLE);
        assertThat(lf.getStyle()).isEqualTo(LineStyle.SINGLE);
        lf.setStyle(LineStyle.THIN_THIN);
        assertThat(lf.getStyle()).isEqualTo(LineStyle.THIN_THIN);
        lf.setStyle(LineStyle.THICK_THIN);
        assertThat(lf.getStyle()).isEqualTo(LineStyle.THICK_THIN);
    }

    // --- alignment ---

    @Test
    void alignment_defaultIsNotDefined() {
        var lf = createLineFormat();
        assertThat(lf.getAlignment()).isEqualTo(LineAlignment.NOT_DEFINED);
    }

    @Test
    void alignment_roundTrip() {
        var lf = createLineFormat();
        lf.setAlignment(LineAlignment.CENTER);
        assertThat(lf.getAlignment()).isEqualTo(LineAlignment.CENTER);
        lf.setAlignment(LineAlignment.INSET);
        assertThat(lf.getAlignment()).isEqualTo(LineAlignment.INSET);
    }

    // --- join style ---

    @Test
    void joinStyle_defaultIsNotDefined() {
        var lf = createLineFormat();
        assertThat(lf.getJoinStyle()).isEqualTo(LineJoinStyle.NOT_DEFINED);
    }

    @Test
    void joinStyle_roundTrip() {
        var lf = createLineFormat();
        lf.setJoinStyle(LineJoinStyle.ROUND);
        assertThat(lf.getJoinStyle()).isEqualTo(LineJoinStyle.ROUND);
        lf.setJoinStyle(LineJoinStyle.BEVEL);
        assertThat(lf.getJoinStyle()).isEqualTo(LineJoinStyle.BEVEL);
        lf.setJoinStyle(LineJoinStyle.MITER);
        assertThat(lf.getJoinStyle()).isEqualTo(LineJoinStyle.MITER);
    }

    @Test
    void joinStyle_setRemovesPreviousJoin() {
        var lf = createLineFormat();
        lf.setJoinStyle(LineJoinStyle.ROUND);
        lf.setJoinStyle(LineJoinStyle.BEVEL);
        assertThat(lf.getJoinStyle()).isEqualTo(LineJoinStyle.BEVEL);
    }

    // --- miter limit ---

    @Test
    void miterLimit_defaultIsZero() {
        var lf = createLineFormat();
        assertThat(lf.getMiterLimit()).isEqualTo(0.0);
    }

    @Test
    void miterLimit_setAndGet() {
        var lf = createLineFormat();
        lf.setMiterLimit(8.0);
        assertThat(lf.getMiterLimit()).isCloseTo(8.0, org.assertj.core.data.Offset.offset(0.001));
    }

    @Test
    void miterLimit_setCreatesMiterJoin() {
        var lf = createLineFormat();
        lf.setJoinStyle(LineJoinStyle.ROUND);
        lf.setMiterLimit(5.0);
        // Setting miter limit should replace round join with miter
        assertThat(lf.getJoinStyle()).isEqualTo(LineJoinStyle.MITER);
        assertThat(lf.getMiterLimit()).isCloseTo(5.0, org.assertj.core.data.Offset.offset(0.001));
    }

    // --- arrowhead style ---

    @Test
    void beginArrowheadStyle_defaultIsNotDefined() {
        var lf = createLineFormat();
        assertThat(lf.getBeginArrowheadStyle()).isEqualTo(LineArrowheadStyle.NOT_DEFINED);
    }

    @Test
    void beginArrowheadStyle_roundTrip() {
        var lf = createLineFormat();
        lf.setBeginArrowheadStyle(LineArrowheadStyle.TRIANGLE);
        assertThat(lf.getBeginArrowheadStyle()).isEqualTo(LineArrowheadStyle.TRIANGLE);
    }

    @Test
    void endArrowheadStyle_roundTrip() {
        var lf = createLineFormat();
        lf.setEndArrowheadStyle(LineArrowheadStyle.STEALTH);
        assertThat(lf.getEndArrowheadStyle()).isEqualTo(LineArrowheadStyle.STEALTH);
    }

    @Test
    void arrowheadStyle_beginAndEndIndependent() {
        var lf = createLineFormat();
        lf.setBeginArrowheadStyle(LineArrowheadStyle.DIAMOND);
        lf.setEndArrowheadStyle(LineArrowheadStyle.OVAL);
        assertThat(lf.getBeginArrowheadStyle()).isEqualTo(LineArrowheadStyle.DIAMOND);
        assertThat(lf.getEndArrowheadStyle()).isEqualTo(LineArrowheadStyle.OVAL);
    }

    // --- arrowhead width ---

    @Test
    void beginArrowheadWidth_roundTrip() {
        var lf = createLineFormat();
        lf.setBeginArrowheadWidth(LineArrowheadWidth.WIDE);
        assertThat(lf.getBeginArrowheadWidth()).isEqualTo(LineArrowheadWidth.WIDE);
    }

    @Test
    void endArrowheadWidth_roundTrip() {
        var lf = createLineFormat();
        lf.setEndArrowheadWidth(LineArrowheadWidth.NARROW);
        assertThat(lf.getEndArrowheadWidth()).isEqualTo(LineArrowheadWidth.NARROW);
    }

    // --- arrowhead length ---

    @Test
    void beginArrowheadLength_roundTrip() {
        var lf = createLineFormat();
        lf.setBeginArrowheadLength(LineArrowheadLength.LONG);
        assertThat(lf.getBeginArrowheadLength()).isEqualTo(LineArrowheadLength.LONG);
    }

    @Test
    void endArrowheadLength_roundTrip() {
        var lf = createLineFormat();
        lf.setEndArrowheadLength(LineArrowheadLength.SHORT);
        assertThat(lf.getEndArrowheadLength()).isEqualTo(LineArrowheadLength.SHORT);
    }

    // --- fill format ---

    @Test
    void fillFormat_returnsNonNull() {
        var lf = createLineFormat();
        assertThat(lf.getFillFormat()).isNotNull();
    }

    @Test
    void fillFormat_createsLnElement() {
        var lf = createLineFormat();
        lf.getFillFormat();
        assertThat(lf.isFormatNotDefined()).isTrue(); // ln exists but is empty
    }

    @Test
    void lineColorAndWidth_persistAfterReRead() {
        var lf = createLineFormat();
        lf.setWidth(5.0);
        lf.getFillFormat().setFillType(FillType.SOLID);
        lf.getFillFormat().getSolidFillColor().setColor(Color.DARK_RED);

        // Re-read from same XML (simulates save/reload)
        var lf2 = new LineFormat(spPr, null);
        assertThat(lf2.getWidth()).isCloseTo(5.0, org.assertj.core.data.Offset.offset(0.001));
        assertThat(lf2.getFillFormat().getFillType()).isEqualTo(FillType.SOLID);
        Color c = lf2.getFillFormat().getSolidFillColor().getColor();
        assertThat(c.getR()).isEqualTo(Color.DARK_RED.getR());
    }

    // --- save callback ---

    @Test
    void setWidth_invokesSaveCallback() {
        AtomicInteger callCount = new AtomicInteger(0);
        var lf = createLineFormatWithCallback(callCount::incrementAndGet);
        lf.setWidth(2.0);
        assertThat(callCount.get()).isEqualTo(1);
    }

    @Test
    void setDashStyle_invokesSaveCallback() {
        AtomicInteger callCount = new AtomicInteger(0);
        var lf = createLineFormatWithCallback(callCount::incrementAndGet);
        lf.setDashStyle(LineDashStyle.DASH);
        assertThat(callCount.get()).isEqualTo(1);
    }

    // --- custom ln tag ---

    @Test
    void customLnTag_readsFromCorrectElement() {
        Element uLn = doc.createElementNS(NS_A, "a:uLn");
        uLn.setAttribute("w", "25400");
        spPr.appendChild(uLn);
        var lf = new LineFormat(spPr, null, "uLn");
        assertThat(lf.getWidth()).isCloseTo(2.0, org.assertj.core.data.Offset.offset(0.001));
    }

    // --- combined properties persist ---

    @Test
    void multipleProperties_persistTogether() {
        var lf = createLineFormat();
        lf.setWidth(2.5);
        lf.setDashStyle(LineDashStyle.DASH_DOT);
        lf.setCapStyle(LineCapStyle.ROUND);
        lf.setStyle(LineStyle.SINGLE);
        lf.setAlignment(LineAlignment.CENTER);
        lf.setJoinStyle(LineJoinStyle.ROUND);
        lf.setBeginArrowheadStyle(LineArrowheadStyle.TRIANGLE);
        lf.setEndArrowheadStyle(LineArrowheadStyle.OPEN);

        // All properties should be readable from the same XML
        assertThat(lf.getWidth()).isCloseTo(2.5, org.assertj.core.data.Offset.offset(0.001));
        assertThat(lf.getDashStyle()).isEqualTo(LineDashStyle.DASH_DOT);
        assertThat(lf.getCapStyle()).isEqualTo(LineCapStyle.ROUND);
        assertThat(lf.getStyle()).isEqualTo(LineStyle.SINGLE);
        assertThat(lf.getAlignment()).isEqualTo(LineAlignment.CENTER);
        assertThat(lf.getJoinStyle()).isEqualTo(LineJoinStyle.ROUND);
        assertThat(lf.getBeginArrowheadStyle()).isEqualTo(LineArrowheadStyle.TRIANGLE);
        assertThat(lf.getEndArrowheadStyle()).isEqualTo(LineArrowheadStyle.OPEN);
        assertThat(lf.isFormatNotDefined()).isFalse();
    }
}
