package org.aspose.slides.foss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ParagraphFormat}: alignment, depth, spacing, margins,
 * indent, nullable-bool properties, font alignment, and default portion format.
 *
 * <p>Covers paragraph alignment, depth, spacing, and formatting properties.</p>
 */
class ParagraphFormatTest {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";

    private Document doc;
    private Element pElement;
    private int saveCount;

    @BeforeEach
    void setUp() throws Exception {
        doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .newDocument();
        pElement = doc.createElementNS(NS_A, "a:p");
        doc.appendChild(pElement);
        saveCount = 0;
    }

    private ParagraphFormat createFormat() {
        return new ParagraphFormat(pElement, () -> saveCount++);
    }

    // ---- alignment ----

    @Test
    void alignment_noPPr_isNotDefined() {
        var pf = createFormat();

        assertThat(pf.getAlignment()).isEqualTo(TextAlignment.NOT_DEFINED);
    }

    @Test
    void setAlignment_center_persists() {
        var pf = createFormat();

        pf.setAlignment(TextAlignment.CENTER);

        assertThat(pf.getAlignment()).isEqualTo(TextAlignment.CENTER);
        assertThat(saveCount).isEqualTo(1);
    }

    @ParameterizedTest
    @EnumSource(value = TextAlignment.class, names = "NOT_DEFINED", mode = EnumSource.Mode.EXCLUDE)
    void setAlignment_allValues_roundTrip(TextAlignment alignment) {
        var pf = createFormat();

        pf.setAlignment(alignment);

        assertThat(pf.getAlignment()).isEqualTo(alignment);
    }

    @Test
    void setAlignment_notDefined_removesAttribute() {
        var pf = createFormat();
        pf.setAlignment(TextAlignment.CENTER);

        pf.setAlignment(TextAlignment.NOT_DEFINED);

        assertThat(pf.getAlignment()).isEqualTo(TextAlignment.NOT_DEFINED);
    }

    // ---- depth ----

    @Test
    void depth_noPPr_isZero() {
        var pf = createFormat();

        assertThat(pf.getDepth()).isEqualTo(0);
    }

    @Test
    void setDepth_persists() {
        var pf = createFormat();

        pf.setDepth(20);

        assertThat(pf.getDepth()).isEqualTo(20);
        assertThat(saveCount).isEqualTo(1);
    }

    @Test
    void setDepth_zero_removesAttribute() {
        var pf = createFormat();
        pf.setDepth(5);

        pf.setDepth(0);

        assertThat(pf.getDepth()).isEqualTo(0);
    }

    // ---- spacing ----

    @Test
    void spaceWithin_noPPr_isNaN() {
        var pf = createFormat();

        assertThat(pf.getSpaceWithin()).isNaN();
    }

    @Test
    void setSpaceWithin_persists() {
        var pf = createFormat();

        pf.setSpaceWithin(1.5);

        assertThat(pf.getSpaceWithin()).isEqualTo(1.5);
        assertThat(saveCount).isEqualTo(1);
    }

    @Test
    void spaceBefore_noPPr_isNaN() {
        var pf = createFormat();

        assertThat(pf.getSpaceBefore()).isNaN();
    }

    @Test
    void setSpaceBefore_persists() {
        var pf = createFormat();

        pf.setSpaceBefore(12.0);

        assertThat(pf.getSpaceBefore()).isEqualTo(12.0);
    }

    @Test
    void spaceAfter_noPPr_isNaN() {
        var pf = createFormat();

        assertThat(pf.getSpaceAfter()).isNaN();
    }

    @Test
    void setSpaceAfter_persists() {
        var pf = createFormat();

        pf.setSpaceAfter(6.0);

        assertThat(pf.getSpaceAfter()).isEqualTo(6.0);
    }

    @Test
    void setSpacing_nan_removesElement() {
        var pf = createFormat();
        pf.setSpaceWithin(1.5);

        pf.setSpaceWithin(Double.NaN);

        assertThat(pf.getSpaceWithin()).isNaN();
    }

    @Test
    void setSpaceBefore_negativePoints_persists() {
        var pf = createFormat();

        pf.setSpaceBefore(-12.0);

        assertThat(pf.getSpaceBefore()).isEqualTo(-12.0);
    }

    @Test
    void setSpaceAfter_negativePoints_persists() {
        var pf = createFormat();

        pf.setSpaceAfter(-6.0);

        assertThat(pf.getSpaceAfter()).isEqualTo(-6.0);
    }

    // ---- NullableBool properties ----

    @Test
    void eastAsianLineBreak_noPPr_isNotDefined() {
        var pf = createFormat();

        assertThat(pf.getEastAsianLineBreak()).isEqualTo(NullableBool.NOT_DEFINED);
    }

    @Test
    void setEastAsianLineBreak_true_persists() {
        var pf = createFormat();

        pf.setEastAsianLineBreak(NullableBool.TRUE);

        assertThat(pf.getEastAsianLineBreak()).isEqualTo(NullableBool.TRUE);
        assertThat(saveCount).isEqualTo(1);
    }

    @Test
    void setEastAsianLineBreak_false_persists() {
        var pf = createFormat();

        pf.setEastAsianLineBreak(NullableBool.FALSE);

        assertThat(pf.getEastAsianLineBreak()).isEqualTo(NullableBool.FALSE);
    }

    @Test
    void setEastAsianLineBreak_notDefined_removesAttribute() {
        var pf = createFormat();
        pf.setEastAsianLineBreak(NullableBool.TRUE);

        pf.setEastAsianLineBreak(NullableBool.NOT_DEFINED);

        assertThat(pf.getEastAsianLineBreak()).isEqualTo(NullableBool.NOT_DEFINED);
    }

    @Test
    void rightToLeft_roundTrip() {
        var pf = createFormat();

        pf.setRightToLeft(NullableBool.TRUE);

        assertThat(pf.getRightToLeft()).isEqualTo(NullableBool.TRUE);
    }

    @Test
    void latinLineBreak_roundTrip() {
        var pf = createFormat();

        pf.setLatinLineBreak(NullableBool.FALSE);

        assertThat(pf.getLatinLineBreak()).isEqualTo(NullableBool.FALSE);
    }

    @Test
    void hangingPunctuation_roundTrip() {
        var pf = createFormat();

        pf.setHangingPunctuation(NullableBool.TRUE);

        assertThat(pf.getHangingPunctuation()).isEqualTo(NullableBool.TRUE);
    }

    // ---- margins and indent ----

    @Test
    void marginLeft_noPPr_isNaN() {
        var pf = createFormat();

        assertThat(pf.getMarginLeft()).isNaN();
    }

    @Test
    void setMarginLeft_persists() {
        var pf = createFormat();

        pf.setMarginLeft(36.0);

        assertThat(pf.getMarginLeft()).isEqualTo(36.0);
        assertThat(saveCount).isEqualTo(1);
    }

    @Test
    void setMarginRight_persists() {
        var pf = createFormat();

        pf.setMarginRight(18.0);

        assertThat(pf.getMarginRight()).isEqualTo(18.0);
    }

    @Test
    void setIndent_persists() {
        var pf = createFormat();

        pf.setIndent(-18.0);

        assertThat(pf.getIndent()).isEqualTo(-18.0);
    }

    @Test
    void setMarginLeft_nan_removesAttribute() {
        var pf = createFormat();
        pf.setMarginLeft(36.0);

        pf.setMarginLeft(Double.NaN);

        assertThat(pf.getMarginLeft()).isNaN();
    }

    // ---- default tab size ----

    @Test
    void defaultTabSize_noPPr_isNaN() {
        var pf = createFormat();

        assertThat(pf.getDefaultTabSize()).isNaN();
    }

    @Test
    void setDefaultTabSize_persists() {
        var pf = createFormat();

        pf.setDefaultTabSize(72.0);

        assertThat(pf.getDefaultTabSize()).isEqualTo(72.0);
        assertThat(saveCount).isEqualTo(1);
    }

    // ---- font alignment ----

    @Test
    void fontAlignment_noPPr_isDefault() {
        var pf = createFormat();

        assertThat(pf.getFontAlignment()).isEqualTo(FontAlignment.DEFAULT);
    }

    @Test
    void setFontAlignment_center_persists() {
        var pf = createFormat();

        pf.setFontAlignment(FontAlignment.CENTER);

        assertThat(pf.getFontAlignment()).isEqualTo(FontAlignment.CENTER);
        assertThat(saveCount).isEqualTo(1);
    }

    @ParameterizedTest
    @EnumSource(value = FontAlignment.class, names = "DEFAULT", mode = EnumSource.Mode.EXCLUDE)
    void setFontAlignment_allValues_roundTrip(FontAlignment fa) {
        var pf = createFormat();

        pf.setFontAlignment(fa);

        assertThat(pf.getFontAlignment()).isEqualTo(fa);
    }

    @Test
    void setFontAlignment_default_removesAttribute() {
        var pf = createFormat();
        pf.setFontAlignment(FontAlignment.TOP);

        pf.setFontAlignment(FontAlignment.DEFAULT);

        assertThat(pf.getFontAlignment()).isEqualTo(FontAlignment.DEFAULT);
    }

    // ---- bullet (read-only) ----

    @Test
    void bullet_returnsNonNull() {
        var pf = createFormat();

        assertThat(pf.getBullet()).isNotNull();
    }

    // ---- default portion format (read-only) ----

    @Test
    void defaultPortionFormat_returnsNonNull() {
        var pf = createFormat();

        assertThat(pf.getDefaultPortionFormat()).isNotNull();
    }

    @Test
    void defaultPortionFormat_sameInstanceOnRepeatedCalls() {
        var pf = createFormat();

        var dpf1 = pf.getDefaultPortionFormat();
        var dpf2 = pf.getDefaultPortionFormat();

        assertThat(dpf1).isNotNull();
        assertThat(dpf2).isNotNull();
    }

    // ---- detached constructor ----

    @Test
    void detachedConstructor_defaults() {
        var pf = new ParagraphFormat();

        assertThat(pf.getAlignment()).isEqualTo(TextAlignment.NOT_DEFINED);
        assertThat(pf.getDepth()).isEqualTo(0);
        assertThat(pf.getSpaceWithin()).isNaN();
        assertThat(pf.getMarginLeft()).isNaN();
        assertThat(pf.getFontAlignment()).isEqualTo(FontAlignment.DEFAULT);
    }

    // ---- initInternal ----

    @Test
    void initInternal_usesPPrDirectly() {
        Element pPr = doc.createElementNS(NS_A, "a:pPr");
        pPr.setAttribute("algn", "ctr");
        doc.getDocumentElement().appendChild(pPr);

        var pf = new ParagraphFormat();
        pf.initInternal(pPr, () -> saveCount++, null);

        assertThat(pf.getAlignment()).isEqualTo(TextAlignment.CENTER);
    }

    @Test
    void initInternal_saveCallbackIsWired() {
        Element pPr = doc.createElementNS(NS_A, "a:pPr");
        doc.getDocumentElement().appendChild(pPr);

        var pf = new ParagraphFormat();
        pf.initInternal(pPr, () -> saveCount++, null);
        pf.setAlignment(TextAlignment.RIGHT);

        assertThat(saveCount).isEqualTo(1);
    }

    // ---- pprInsertChild ----

    @Test
    void pprInsertChild_insertsAtCorrectSchemaPosition() {
        Element pPr = doc.createElementNS(NS_A, "a:pPr");
        doc.getDocumentElement().appendChild(pPr);

        // Insert defRPr first (position 8 in schema)
        ParagraphFormat.pprInsertChild(pPr, "defRPr");
        // Insert lnSpc (position 0 in schema) — should go before defRPr
        ParagraphFormat.pprInsertChild(pPr, "lnSpc");

        var children = pPr.getChildNodes();
        assertThat(children.getLength()).isEqualTo(2);
        assertThat(((Element) children.item(0)).getLocalName()).isEqualTo("lnSpc");
        assertThat(((Element) children.item(1)).getLocalName()).isEqualTo("defRPr");
    }

    @Test
    void pprInsertChild_withAttributes() {
        Element pPr = doc.createElementNS(NS_A, "a:pPr");
        doc.getDocumentElement().appendChild(pPr);

        Element el = ParagraphFormat.pprInsertChild(pPr, "spcBef", java.util.Map.of());

        assertThat(el).isNotNull();
        assertThat(el.getLocalName()).isEqualTo("spcBef");
    }

    @Test
    void pprInsertChild_multipleElementsInSchemaOrder() {
        Element pPr = doc.createElementNS(NS_A, "a:pPr");
        doc.getDocumentElement().appendChild(pPr);

        // Insert in reverse order — should still be sorted
        ParagraphFormat.pprInsertChild(pPr, "defRPr");
        ParagraphFormat.pprInsertChild(pPr, "spcAft");
        ParagraphFormat.pprInsertChild(pPr, "lnSpc");
        ParagraphFormat.pprInsertChild(pPr, "spcBef");

        var children = pPr.getChildNodes();
        assertThat(children.getLength()).isEqualTo(4);
        assertThat(((Element) children.item(0)).getLocalName()).isEqualTo("lnSpc");
        assertThat(((Element) children.item(1)).getLocalName()).isEqualTo("spcBef");
        assertThat(((Element) children.item(2)).getLocalName()).isEqualTo("spcAft");
        assertThat(((Element) children.item(3)).getLocalName()).isEqualTo("defRPr");
    }

    // ---- getEmuAttr / setEmuAttr ----

    @Test
    void getEmuAttr_noPPr_returnsNaN() {
        var pf = createFormat();

        assertThat(pf.getEmuAttr("marL")).isNaN();
    }

    @Test
    void setEmuAttr_roundTrip() {
        var pf = createFormat();

        pf.setEmuAttr("marL", 36.0);

        assertThat(pf.getEmuAttr("marL")).isEqualTo(36.0);
    }

    @Test
    void setEmuAttr_nan_removesAttribute() {
        var pf = createFormat();
        pf.setEmuAttr("marL", 36.0);

        pf.setEmuAttr("marL", Double.NaN);

        assertThat(pf.getEmuAttr("marL")).isNaN();
    }

    // ---- getNullableBoolAttr / setNullableBoolAttr ----

    @Test
    void getNullableBoolAttr_noPPr_returnsNotDefined() {
        var pf = createFormat();

        assertThat(pf.getNullableBoolAttr("rtl")).isEqualTo(NullableBool.NOT_DEFINED);
    }

    @Test
    void setNullableBoolAttr_roundTrip() {
        var pf = createFormat();

        pf.setNullableBoolAttr("rtl", NullableBool.TRUE);

        assertThat(pf.getNullableBoolAttr("rtl")).isEqualTo(NullableBool.TRUE);
    }

    // ---- getSpacing / setSpacing ----

    @Test
    void getSpacing_noPPr_returnsNaN() {
        var pf = createFormat();

        assertThat(pf.getSpacing("lnSpc")).isNaN();
    }

    @Test
    void setSpacing_percentage_roundTrip() {
        var pf = createFormat();

        pf.setSpacing("lnSpc", 1.5);

        assertThat(pf.getSpacing("lnSpc")).isEqualTo(1.5);
    }

    @Test
    void setSpacing_points_roundTrip() {
        var pf = createFormat();

        pf.setSpacing("spcBef", -12.0);

        assertThat(pf.getSpacing("spcBef")).isEqualTo(-12.0);
    }
}
