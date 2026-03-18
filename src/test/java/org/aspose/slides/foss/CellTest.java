package org.aspose.slides.foss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

/**
 * Unit tests for {@link Cell}.
 *
 * <p>Verifies the cell behavioral contract.</p>
 */
class CellTest {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";
    private static final double EMU_PER_POINT = 12700.0;

    private Document doc;

    @BeforeEach
    void setUp() throws Exception {
        doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .newDocument();
    }

    private Element createTcElement() {
        Element tc = doc.createElementNS(NS_A, "a:tc");
        doc.appendChild(tc);
        return tc;
    }

    private Element createTcWithTcPr() {
        Element tc = doc.createElementNS(NS_A, "a:tc");
        doc.appendChild(tc);
        Element tcPr = doc.createElementNS(NS_A, "a:tcPr");
        tc.appendChild(tcPr);
        return tc;
    }

    // --- margins: defaults ---

    @Test
    void marginLeft_defaultsWhenNoTcPr() {
        var cell = new Cell(createTcElement(), null);
        assertThat(cell.getMarginLeft()).isCloseTo(91440 / EMU_PER_POINT, offset(0.001));
    }

    @Test
    void marginRight_defaultsWhenNoTcPr() {
        var cell = new Cell(createTcElement(), null);
        assertThat(cell.getMarginRight()).isCloseTo(91440 / EMU_PER_POINT, offset(0.001));
    }

    @Test
    void marginTop_defaultsWhenNoTcPr() {
        var cell = new Cell(createTcElement(), null);
        assertThat(cell.getMarginTop()).isCloseTo(45720 / EMU_PER_POINT, offset(0.001));
    }

    @Test
    void marginBottom_defaultsWhenNoTcPr() {
        var cell = new Cell(createTcElement(), null);
        assertThat(cell.getMarginBottom()).isCloseTo(45720 / EMU_PER_POINT, offset(0.001));
    }

    // --- margins: read from XML ---

    @Test
    void marginLeft_readsFromTcPr() {
        Element tc = createTcWithTcPr();
        Element tcPr = (Element) tc.getFirstChild();
        tcPr.setAttribute("marL", "127000");
        var cell = new Cell(tc, null);
        assertThat(cell.getMarginLeft()).isCloseTo(127000 / EMU_PER_POINT, offset(0.001));
    }

    @Test
    void marginRight_readsFromTcPr() {
        Element tc = createTcWithTcPr();
        Element tcPr = (Element) tc.getFirstChild();
        tcPr.setAttribute("marR", "254000");
        var cell = new Cell(tc, null);
        assertThat(cell.getMarginRight()).isCloseTo(254000 / EMU_PER_POINT, offset(0.001));
    }

    // --- margins: set ---

    @Test
    void setMarginLeft_writesToXml() {
        Element tc = createTcElement();
        var cell = new Cell(tc, null);
        cell.setMarginLeft(10.0);
        assertThat(cell.getMarginLeft()).isCloseTo(10.0, offset(0.001));
    }

    @Test
    void setMarginTop_writesToXml() {
        Element tc = createTcElement();
        var cell = new Cell(tc, null);
        cell.setMarginTop(5.0);
        assertThat(cell.getMarginTop()).isCloseTo(5.0, offset(0.001));
    }

    @Test
    void setMarginBottom_writesToXml() {
        Element tc = createTcElement();
        var cell = new Cell(tc, null);
        cell.setMarginBottom(3.0);
        assertThat(cell.getMarginBottom()).isCloseTo(3.0, offset(0.001));
    }

    @Test
    void setMarginRight_writesToXml() {
        Element tc = createTcElement();
        var cell = new Cell(tc, null);
        cell.setMarginRight(7.5);
        assertThat(cell.getMarginRight()).isCloseTo(7.5, offset(0.001));
    }

    @Test
    void setMargin_invokesSaveCallback() {
        Element tc = createTcElement();
        AtomicBoolean saved = new AtomicBoolean(false);
        var cell = new Cell(tc, () -> saved.set(true));
        cell.setMarginLeft(10.0);
        assertThat(saved).isTrue();
    }

    // --- colSpan / rowSpan ---

    @Test
    void colSpan_defaultsToOne() {
        var cell = new Cell(createTcElement(), null);
        assertThat(cell.getColSpan()).isEqualTo(1);
    }

    @Test
    void colSpan_readsGridSpanAttribute() {
        Element tc = createTcElement();
        tc.setAttribute("gridSpan", "3");
        var cell = new Cell(tc, null);
        assertThat(cell.getColSpan()).isEqualTo(3);
    }

    @Test
    void rowSpan_defaultsToOne() {
        var cell = new Cell(createTcElement(), null);
        assertThat(cell.getRowSpan()).isEqualTo(1);
    }

    @Test
    void rowSpan_readsRowSpanAttribute() {
        Element tc = createTcElement();
        tc.setAttribute("rowSpan", "2");
        var cell = new Cell(tc, null);
        assertThat(cell.getRowSpan()).isEqualTo(2);
    }

    // --- isMergedCell ---

    @Test
    void isMergedCell_falseByDefault() {
        var cell = new Cell(createTcElement(), null);
        assertThat(cell.isMergedCell()).isFalse();
    }

    @Test
    void isMergedCell_trueWhenGridSpanGreaterThanOne() {
        Element tc = createTcElement();
        tc.setAttribute("gridSpan", "2");
        var cell = new Cell(tc, null);
        assertThat(cell.isMergedCell()).isTrue();
    }

    @Test
    void isMergedCell_trueWhenRowSpanGreaterThanOne() {
        Element tc = createTcElement();
        tc.setAttribute("rowSpan", "3");
        var cell = new Cell(tc, null);
        assertThat(cell.isMergedCell()).isTrue();
    }

    @Test
    void isMergedCell_trueWhenHMerge() {
        Element tc = createTcElement();
        tc.setAttribute("hMerge", "1");
        var cell = new Cell(tc, null);
        assertThat(cell.isMergedCell()).isTrue();
    }

    @Test
    void isMergedCell_trueWhenVMerge() {
        Element tc = createTcElement();
        tc.setAttribute("vMerge", "1");
        var cell = new Cell(tc, null);
        assertThat(cell.isMergedCell()).isTrue();
    }

    // --- textVerticalType ---

    @Test
    void textVerticalType_defaultsToNotDefined() {
        var cell = new Cell(createTcElement(), null);
        assertThat(cell.getTextVerticalType()).isEqualTo(TextVerticalType.NOT_DEFINED);
    }

    @Test
    void textVerticalType_readsFromTcPr() {
        Element tc = createTcWithTcPr();
        Element tcPr = (Element) tc.getFirstChild();
        tcPr.setAttribute("vert", "vert");
        var cell = new Cell(tc, null);
        assertThat(cell.getTextVerticalType()).isEqualTo(TextVerticalType.VERTICAL);
    }

    @Test
    void textVerticalType_mapsAllKnownValues() {
        Element tc = createTcWithTcPr();
        Element tcPr = (Element) tc.getFirstChild();

        tcPr.setAttribute("vert", "horz");
        assertThat(new Cell(tc, null).getTextVerticalType()).isEqualTo(TextVerticalType.HORIZONTAL);

        tcPr.setAttribute("vert", "vert270");
        assertThat(new Cell(tc, null).getTextVerticalType()).isEqualTo(TextVerticalType.VERTICAL270);

        tcPr.setAttribute("vert", "wordArtVert");
        assertThat(new Cell(tc, null).getTextVerticalType()).isEqualTo(TextVerticalType.WORD_ART_VERTICAL);

        tcPr.setAttribute("vert", "eaVert");
        assertThat(new Cell(tc, null).getTextVerticalType()).isEqualTo(TextVerticalType.EAST_ASIAN_VERTICAL);

        tcPr.setAttribute("vert", "mongolianVert");
        assertThat(new Cell(tc, null).getTextVerticalType()).isEqualTo(TextVerticalType.MONGOLIAN_VERTICAL);

        tcPr.setAttribute("vert", "wordArtVertRtl");
        assertThat(new Cell(tc, null).getTextVerticalType()).isEqualTo(TextVerticalType.WORD_ART_VERTICAL_RIGHT_TO_LEFT);
    }

    @Test
    void textVerticalType_unknownValueMapsToNotDefined() {
        Element tc = createTcWithTcPr();
        Element tcPr = (Element) tc.getFirstChild();
        tcPr.setAttribute("vert", "unknownValue");
        var cell = new Cell(tc, null);
        assertThat(cell.getTextVerticalType()).isEqualTo(TextVerticalType.NOT_DEFINED);
    }

    @Test
    void setTextVerticalType_writesToXml() {
        Element tc = createTcElement();
        var cell = new Cell(tc, null);
        cell.setTextVerticalType(TextVerticalType.VERTICAL270);
        assertThat(cell.getTextVerticalType()).isEqualTo(TextVerticalType.VERTICAL270);
    }

    @Test
    void setTextVerticalType_notDefinedRemovesAttribute() {
        Element tc = createTcWithTcPr();
        Element tcPr = (Element) tc.getFirstChild();
        tcPr.setAttribute("vert", "vert");
        var cell = new Cell(tc, null);
        cell.setTextVerticalType(TextVerticalType.NOT_DEFINED);
        assertThat(tcPr.hasAttribute("vert")).isFalse();
    }

    @Test
    void setTextVerticalType_invokesSaveCallback() {
        Element tc = createTcElement();
        AtomicBoolean saved = new AtomicBoolean(false);
        var cell = new Cell(tc, () -> saved.set(true));
        cell.setTextVerticalType(TextVerticalType.HORIZONTAL);
        assertThat(saved).isTrue();
    }

    // --- textAnchorType ---

    @Test
    void textAnchorType_defaultsToNotDefined() {
        var cell = new Cell(createTcElement(), null);
        assertThat(cell.getTextAnchorType()).isEqualTo(TextAnchorType.NOT_DEFINED);
    }

    @Test
    void textAnchorType_readsFromTcPr() {
        Element tc = createTcWithTcPr();
        Element tcPr = (Element) tc.getFirstChild();
        tcPr.setAttribute("anchor", "ctr");
        var cell = new Cell(tc, null);
        assertThat(cell.getTextAnchorType()).isEqualTo(TextAnchorType.CENTER);
    }

    @Test
    void textAnchorType_mapsAllKnownValues() {
        Element tc = createTcWithTcPr();
        Element tcPr = (Element) tc.getFirstChild();

        tcPr.setAttribute("anchor", "t");
        assertThat(new Cell(tc, null).getTextAnchorType()).isEqualTo(TextAnchorType.TOP);

        tcPr.setAttribute("anchor", "b");
        assertThat(new Cell(tc, null).getTextAnchorType()).isEqualTo(TextAnchorType.BOTTOM);

        tcPr.setAttribute("anchor", "just");
        assertThat(new Cell(tc, null).getTextAnchorType()).isEqualTo(TextAnchorType.JUSTIFIED);

        tcPr.setAttribute("anchor", "dist");
        assertThat(new Cell(tc, null).getTextAnchorType()).isEqualTo(TextAnchorType.DISTRIBUTED);
    }

    @Test
    void setTextAnchorType_writesToXml() {
        Element tc = createTcElement();
        var cell = new Cell(tc, null);
        cell.setTextAnchorType(TextAnchorType.BOTTOM);
        assertThat(cell.getTextAnchorType()).isEqualTo(TextAnchorType.BOTTOM);
    }

    @Test
    void setTextAnchorType_notDefinedRemovesAttribute() {
        Element tc = createTcWithTcPr();
        Element tcPr = (Element) tc.getFirstChild();
        tcPr.setAttribute("anchor", "t");
        var cell = new Cell(tc, null);
        cell.setTextAnchorType(TextAnchorType.NOT_DEFINED);
        assertThat(tcPr.hasAttribute("anchor")).isFalse();
    }

    // --- anchorCenter ---

    @Test
    void anchorCenter_defaultsFalse() {
        var cell = new Cell(createTcElement(), null);
        assertThat(cell.isAnchorCenter()).isFalse();
    }

    @Test
    void anchorCenter_readsFromTcPr() {
        Element tc = createTcWithTcPr();
        Element tcPr = (Element) tc.getFirstChild();
        tcPr.setAttribute("anchorCtr", "1");
        var cell = new Cell(tc, null);
        assertThat(cell.isAnchorCenter()).isTrue();
    }

    @Test
    void setAnchorCenter_true_writesToXml() {
        Element tc = createTcElement();
        var cell = new Cell(tc, null);
        cell.setAnchorCenter(true);
        assertThat(cell.isAnchorCenter()).isTrue();
    }

    @Test
    void setAnchorCenter_false_removesAttribute() {
        Element tc = createTcWithTcPr();
        Element tcPr = (Element) tc.getFirstChild();
        tcPr.setAttribute("anchorCtr", "1");
        var cell = new Cell(tc, null);
        cell.setAnchorCenter(false);
        assertThat(tcPr.hasAttribute("anchorCtr")).isFalse();
    }

    @Test
    void setAnchorCenter_invokesSaveCallback() {
        Element tc = createTcElement();
        AtomicBoolean saved = new AtomicBoolean(false);
        var cell = new Cell(tc, () -> saved.set(true));
        cell.setAnchorCenter(true);
        assertThat(saved).isTrue();
    }

    // --- textFrame ---

    @Test
    void textFrame_returnsNullWhenNoTxBody() {
        var cell = new Cell(createTcElement(), null);
        assertThat(cell.getTextFrame()).isNull();
    }

    @Test
    void textFrame_returnsTextFrameWhenTxBodyPresent() {
        Element tc = createTcElement();
        Element txBody = doc.createElementNS(NS_A, "a:txBody");
        tc.appendChild(txBody);
        // Add a paragraph so the TextFrame has content
        Element p = doc.createElementNS(NS_A, "a:p");
        txBody.appendChild(p);
        var cell = new Cell(tc, null);
        assertThat(cell.getTextFrame()).isNotNull();
    }

    // --- cellFormat ---

    @Test
    void cellFormat_returnsNonNull() {
        var cell = new Cell(createTcElement(), null);
        assertThat(cell.getCellFormat()).isNotNull();
    }

    // --- firstRowIndex / firstColumnIndex ---

    @Test
    void firstRowIndex_returnsRowIndex() {
        Element tc = createTcElement();
        var cell = new Cell(tc, 3, 5, null, null, null);
        assertThat(cell.getFirstRowIndex()).isEqualTo(3);
    }

    @Test
    void firstColumnIndex_returnsColumnIndex() {
        Element tc = createTcElement();
        var cell = new Cell(tc, 3, 5, null, null, null);
        assertThat(cell.getFirstColumnIndex()).isEqualTo(5);
    }

    // --- table / slide / presentation context ---

    @Test
    void table_returnsNullWithoutContext() {
        var cell = new Cell(createTcElement(), null);
        assertThat(cell.getTable()).isNull();
    }

    @Test
    void slide_returnsNullWithoutContext() {
        var cell = new Cell(createTcElement(), null);
        assertThat(cell.getSlide()).isNull();
    }

    @Test
    void presentation_returnsNullWithoutContext() {
        var cell = new Cell(createTcElement(), null);
        assertThat(cell.getPresentation()).isNull();
    }

    // --- asISlideComponent / asIPresentationComponent ---

    @Test
    void asISlideComponent_returnsSelf() {
        var cell = new Cell(createTcElement(), null);
        assertThat(cell.getAsISlideComponent()).isSameAs(cell);
    }

    @Test
    void asIPresentationComponent_returnsSelf() {
        var cell = new Cell(createTcElement(), null);
        assertThat(cell.getAsIPresentationComponent()).isSameAs(cell);
    }

    // --- offsetX / offsetY without table context ---

    @Test
    void offsetX_returnsZeroWithoutTable() {
        var cell = new Cell(createTcElement(), null);
        assertThat(cell.getOffsetX()).isEqualTo(0.0);
    }

    @Test
    void offsetY_returnsZeroWithoutTable() {
        var cell = new Cell(createTcElement(), null);
        assertThat(cell.getOffsetY()).isEqualTo(0.0);
    }

    // --- width / height / minimalHeight without table context ---

    @Test
    void width_returnsZeroWithoutTable() {
        var cell = new Cell(createTcElement(), null);
        assertThat(cell.getWidth()).isEqualTo(0.0);
    }

    @Test
    void height_returnsZeroWithoutTable() {
        var cell = new Cell(createTcElement(), null);
        assertThat(cell.getHeight()).isEqualTo(0.0);
    }

    @Test
    void minimalHeight_returnsZeroWithoutTable() {
        var cell = new Cell(createTcElement(), null);
        assertThat(cell.getMinimalHeight()).isEqualTo(0.0);
    }

    // --- firstRow / firstColumn without table context ---

    @Test
    void firstRow_returnsNullWithoutTable() {
        var cell = new Cell(createTcElement(), null);
        assertThat(cell.getFirstRow()).isNull();
    }

    @Test
    void firstColumn_returnsNullWithoutTable() {
        var cell = new Cell(createTcElement(), null);
        assertThat(cell.getFirstColumn()).isNull();
    }
}
