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
 * Tests for {@link BasePortionFormat}: NullableBool properties, enum properties,
 * float properties, font properties, string properties, format object accessors,
 * underline hard/soft, and spell check.
 *
 * <p>Covers format object access patterns and fill/gradient format property persistence.</p>
 */
class BasePortionFormatTest {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";

    private Document doc;
    private Element rprElement;

    @BeforeEach
    void setUp() throws Exception {
        doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .newDocument();
        rprElement = doc.createElementNS(NS_A, "a:rPr");
        doc.appendChild(rprElement);
    }

    private BasePortionFormat createFormat() {
        return new BasePortionFormat(rprElement, null);
    }

    private BasePortionFormat createFormatWithCallback(Runnable callback) {
        return new BasePortionFormat(rprElement, callback);
    }

    // ---- default constructor ----

    @Test
    void defaultConstructor_createsDetachedElement() {
        var fmt = new BasePortionFormat();
        assertThat(fmt.getFontBold()).isEqualTo(NullableBool.NOT_DEFINED);
        assertThat(fmt.getFontHeight()).isNaN();
        assertThat(fmt.getLatinFont()).isNull();
    }

    // ---- NullableBool properties ----

    @Test
    void fontBold_defaultIsNotDefined() {
        assertThat(createFormat().getFontBold()).isEqualTo(NullableBool.NOT_DEFINED);
    }

    @Test
    void fontBold_setTrue_roundTrips() {
        var fmt = createFormat();
        fmt.setFontBold(NullableBool.TRUE);
        assertThat(fmt.getFontBold()).isEqualTo(NullableBool.TRUE);
        assertThat(rprElement.getAttribute("b")).isEqualTo("1");
    }

    @Test
    void fontBold_setFalse_roundTrips() {
        var fmt = createFormat();
        fmt.setFontBold(NullableBool.FALSE);
        assertThat(fmt.getFontBold()).isEqualTo(NullableBool.FALSE);
        assertThat(rprElement.getAttribute("b")).isEqualTo("0");
    }

    @Test
    void fontBold_setNotDefined_removesAttribute() {
        var fmt = createFormat();
        fmt.setFontBold(NullableBool.TRUE);
        fmt.setFontBold(NullableBool.NOT_DEFINED);
        assertThat(fmt.getFontBold()).isEqualTo(NullableBool.NOT_DEFINED);
        assertThat(rprElement.hasAttribute("b")).isFalse();
    }

    @Test
    void fontItalic_setTrue_roundTrips() {
        var fmt = createFormat();
        fmt.setFontItalic(NullableBool.TRUE);
        assertThat(fmt.getFontItalic()).isEqualTo(NullableBool.TRUE);
    }

    @Test
    void kumimoji_setTrue_roundTrips() {
        var fmt = createFormat();
        fmt.setKumimoji(NullableBool.TRUE);
        assertThat(fmt.getKumimoji()).isEqualTo(NullableBool.TRUE);
    }

    @Test
    void normaliseHeight_setTrue_roundTrips() {
        var fmt = createFormat();
        fmt.setNormaliseHeight(NullableBool.TRUE);
        assertThat(fmt.getNormaliseHeight()).isEqualTo(NullableBool.TRUE);
    }

    @Test
    void proofDisabled_setTrue_roundTrips() {
        var fmt = createFormat();
        fmt.setProofDisabled(NullableBool.TRUE);
        assertThat(fmt.getProofDisabled()).isEqualTo(NullableBool.TRUE);
    }

    // ---- enum properties: underline ----

    @Test
    void fontUnderline_defaultIsNotDefined() {
        assertThat(createFormat().getFontUnderline()).isEqualTo(TextUnderlineType.NOT_DEFINED);
    }

    @Test
    void fontUnderline_setSingle_roundTrips() {
        var fmt = createFormat();
        fmt.setFontUnderline(TextUnderlineType.SINGLE);
        assertThat(fmt.getFontUnderline()).isEqualTo(TextUnderlineType.SINGLE);
        assertThat(rprElement.getAttribute("u")).isEqualTo("sng");
    }

    @Test
    void fontUnderline_setDouble_roundTrips() {
        var fmt = createFormat();
        fmt.setFontUnderline(TextUnderlineType.DOUBLE);
        assertThat(fmt.getFontUnderline()).isEqualTo(TextUnderlineType.DOUBLE);
        assertThat(rprElement.getAttribute("u")).isEqualTo("dbl");
    }

    @Test
    void fontUnderline_setWavy_roundTrips() {
        var fmt = createFormat();
        fmt.setFontUnderline(TextUnderlineType.WAVY);
        assertThat(fmt.getFontUnderline()).isEqualTo(TextUnderlineType.WAVY);
    }

    @Test
    void fontUnderline_setNotDefined_removesAttribute() {
        var fmt = createFormat();
        fmt.setFontUnderline(TextUnderlineType.SINGLE);
        fmt.setFontUnderline(TextUnderlineType.NOT_DEFINED);
        assertThat(fmt.getFontUnderline()).isEqualTo(TextUnderlineType.NOT_DEFINED);
        assertThat(rprElement.hasAttribute("u")).isFalse();
    }

    @ParameterizedTest
    @EnumSource(value = TextUnderlineType.class, mode = EnumSource.Mode.EXCLUDE, names = "NOT_DEFINED")
    void fontUnderline_allTypes_roundTrip(TextUnderlineType type) {
        var fmt = createFormat();
        fmt.setFontUnderline(type);
        assertThat(fmt.getFontUnderline()).isEqualTo(type);
    }

    // ---- enum properties: cap type ----

    @Test
    void textCapType_defaultIsNotDefined() {
        assertThat(createFormat().getTextCapType()).isEqualTo(TextCapType.NOT_DEFINED);
    }

    @Test
    void textCapType_setSmall_roundTrips() {
        var fmt = createFormat();
        fmt.setTextCapType(TextCapType.SMALL);
        assertThat(fmt.getTextCapType()).isEqualTo(TextCapType.SMALL);
        assertThat(rprElement.getAttribute("cap")).isEqualTo("small");
    }

    @Test
    void textCapType_setAll_roundTrips() {
        var fmt = createFormat();
        fmt.setTextCapType(TextCapType.ALL);
        assertThat(fmt.getTextCapType()).isEqualTo(TextCapType.ALL);
    }

    @Test
    void textCapType_setNotDefined_removesAttribute() {
        var fmt = createFormat();
        fmt.setTextCapType(TextCapType.ALL);
        fmt.setTextCapType(TextCapType.NOT_DEFINED);
        assertThat(fmt.getTextCapType()).isEqualTo(TextCapType.NOT_DEFINED);
    }

    // ---- enum properties: strikethrough ----

    @Test
    void strikethroughType_defaultIsNotDefined() {
        assertThat(createFormat().getStrikethroughType()).isEqualTo(TextStrikethroughType.NOT_DEFINED);
    }

    @Test
    void strikethroughType_setSingle_roundTrips() {
        var fmt = createFormat();
        fmt.setStrikethroughType(TextStrikethroughType.SINGLE);
        assertThat(fmt.getStrikethroughType()).isEqualTo(TextStrikethroughType.SINGLE);
        assertThat(rprElement.getAttribute("strike")).isEqualTo("sngStrike");
    }

    @Test
    void strikethroughType_setDouble_roundTrips() {
        var fmt = createFormat();
        fmt.setStrikethroughType(TextStrikethroughType.DOUBLE);
        assertThat(fmt.getStrikethroughType()).isEqualTo(TextStrikethroughType.DOUBLE);
    }

    @Test
    void strikethroughType_setNotDefined_removesAttribute() {
        var fmt = createFormat();
        fmt.setStrikethroughType(TextStrikethroughType.SINGLE);
        fmt.setStrikethroughType(TextStrikethroughType.NOT_DEFINED);
        assertThat(fmt.getStrikethroughType()).isEqualTo(TextStrikethroughType.NOT_DEFINED);
    }

    // ---- float properties ----

    @Test
    void fontHeight_defaultIsNaN() {
        assertThat(createFormat().getFontHeight()).isNaN();
    }

    @Test
    void fontHeight_set12_roundTrips() {
        var fmt = createFormat();
        fmt.setFontHeight(12.0f);
        assertThat(fmt.getFontHeight()).isEqualTo(12.0f);
        // OOXML stores as hundredths: 12pt = 1200
        assertThat(rprElement.getAttribute("sz")).isEqualTo("1200");
    }

    @Test
    void fontHeight_set24_5_roundTrips() {
        var fmt = createFormat();
        fmt.setFontHeight(24.5f);
        assertThat(fmt.getFontHeight()).isEqualTo(24.5f);
        assertThat(rprElement.getAttribute("sz")).isEqualTo("2450");
    }

    @Test
    void fontHeight_setNaN_removesAttribute() {
        var fmt = createFormat();
        fmt.setFontHeight(12.0f);
        fmt.setFontHeight(Float.NaN);
        assertThat(fmt.getFontHeight()).isNaN();
        assertThat(rprElement.hasAttribute("sz")).isFalse();
    }

    @Test
    void escapement_defaultIsNaN() {
        assertThat(createFormat().getEscapement()).isNaN();
    }

    @Test
    void escapement_set30_roundTrips() {
        var fmt = createFormat();
        fmt.setEscapement(30.0f);
        assertThat(fmt.getEscapement()).isEqualTo(30.0f);
        // OOXML stores as thousandths: 30% = 30000
        assertThat(rprElement.getAttribute("baseline")).isEqualTo("30000");
    }

    @Test
    void escapement_setNegative_roundTrips() {
        var fmt = createFormat();
        fmt.setEscapement(-25.0f);
        assertThat(fmt.getEscapement()).isEqualTo(-25.0f);
    }

    @Test
    void kerningMinimalSize_set18_roundTrips() {
        var fmt = createFormat();
        fmt.setKerningMinimalSize(18.0f);
        assertThat(fmt.getKerningMinimalSize()).isEqualTo(18.0f);
        assertThat(rprElement.getAttribute("kern")).isEqualTo("1800");
    }

    @Test
    void spacing_set3_roundTrips() {
        var fmt = createFormat();
        fmt.setSpacing(3.0f);
        assertThat(fmt.getSpacing()).isEqualTo(3.0f);
        assertThat(rprElement.getAttribute("spc")).isEqualTo("300");
    }

    @Test
    void spacing_setNaN_removesAttribute() {
        var fmt = createFormat();
        fmt.setSpacing(5.0f);
        fmt.setSpacing(Float.NaN);
        assertThat(fmt.getSpacing()).isNaN();
    }

    // ---- font properties ----

    @Test
    void latinFont_defaultIsNull() {
        assertThat(createFormat().getLatinFont()).isNull();
    }

    @Test
    void latinFont_set_roundTrips() {
        var fmt = createFormat();
        fmt.setLatinFont(new FontData("Arial"));
        assertThat(fmt.getLatinFont()).isNotNull();
        assertThat(fmt.getLatinFont().getFontName()).isEqualTo("Arial");
    }

    @Test
    void latinFont_setNull_removesElement() {
        var fmt = createFormat();
        fmt.setLatinFont(new FontData("Arial"));
        fmt.setLatinFont(null);
        assertThat(fmt.getLatinFont()).isNull();
    }

    @Test
    void eastAsianFont_set_roundTrips() {
        var fmt = createFormat();
        fmt.setEastAsianFont(new FontData("MS Gothic"));
        assertThat(fmt.getEastAsianFont().getFontName()).isEqualTo("MS Gothic");
    }

    @Test
    void complexScriptFont_set_roundTrips() {
        var fmt = createFormat();
        fmt.setComplexScriptFont(new FontData("Arial Unicode MS"));
        assertThat(fmt.getComplexScriptFont().getFontName()).isEqualTo("Arial Unicode MS");
    }

    @Test
    void symbolFont_set_roundTrips() {
        var fmt = createFormat();
        fmt.setSymbolFont(new FontData("Symbol"));
        assertThat(fmt.getSymbolFont().getFontName()).isEqualTo("Symbol");
    }

    @Test
    void multipleFonts_independent() {
        var fmt = createFormat();
        fmt.setLatinFont(new FontData("Arial"));
        fmt.setEastAsianFont(new FontData("MS Gothic"));
        fmt.setComplexScriptFont(new FontData("Tahoma"));
        fmt.setSymbolFont(new FontData("Wingdings"));

        assertThat(fmt.getLatinFont().getFontName()).isEqualTo("Arial");
        assertThat(fmt.getEastAsianFont().getFontName()).isEqualTo("MS Gothic");
        assertThat(fmt.getComplexScriptFont().getFontName()).isEqualTo("Tahoma");
        assertThat(fmt.getSymbolFont().getFontName()).isEqualTo("Wingdings");
    }

    // ---- string properties ----

    @Test
    void languageId_defaultIsNull() {
        assertThat(createFormat().getLanguageId()).isNull();
    }

    @Test
    void languageId_set_roundTrips() {
        var fmt = createFormat();
        fmt.setLanguageId("en-US");
        assertThat(fmt.getLanguageId()).isEqualTo("en-US");
    }

    @Test
    void languageId_setNull_removesAttribute() {
        var fmt = createFormat();
        fmt.setLanguageId("en-US");
        fmt.setLanguageId(null);
        assertThat(fmt.getLanguageId()).isNull();
    }

    @Test
    void alternativeLanguageId_set_roundTrips() {
        var fmt = createFormat();
        fmt.setAlternativeLanguageId("ja-JP");
        assertThat(fmt.getAlternativeLanguageId()).isEqualTo("ja-JP");
    }

    @Test
    void alternativeLanguageId_setNull_removesAttribute() {
        var fmt = createFormat();
        fmt.setAlternativeLanguageId("ja-JP");
        fmt.setAlternativeLanguageId(null);
        assertThat(fmt.getAlternativeLanguageId()).isNull();
    }

    // ---- isHardUnderlineLine ----

    @Test
    void isHardUnderlineLine_default_isNotDefined() {
        assertThat(createFormat().getIsHardUnderlineLine()).isEqualTo(NullableBool.NOT_DEFINED);
    }

    @Test
    void isHardUnderlineLine_setTrue_createsULnElement() {
        var fmt = createFormat();
        fmt.setIsHardUnderlineLine(NullableBool.TRUE);
        assertThat(fmt.getIsHardUnderlineLine()).isEqualTo(NullableBool.TRUE);
    }

    @Test
    void isHardUnderlineLine_setFalse_createsULnTxElement() {
        var fmt = createFormat();
        fmt.setIsHardUnderlineLine(NullableBool.FALSE);
        assertThat(fmt.getIsHardUnderlineLine()).isEqualTo(NullableBool.FALSE);
    }

    @Test
    void isHardUnderlineLine_setNotDefined_removesBothElements() {
        var fmt = createFormat();
        fmt.setIsHardUnderlineLine(NullableBool.TRUE);
        fmt.setIsHardUnderlineLine(NullableBool.NOT_DEFINED);
        assertThat(fmt.getIsHardUnderlineLine()).isEqualTo(NullableBool.NOT_DEFINED);
    }

    @Test
    void isHardUnderlineLine_switchFromTrueToFalse() {
        var fmt = createFormat();
        fmt.setIsHardUnderlineLine(NullableBool.TRUE);
        fmt.setIsHardUnderlineLine(NullableBool.FALSE);
        assertThat(fmt.getIsHardUnderlineLine()).isEqualTo(NullableBool.FALSE);
    }

    // ---- isHardUnderlineFill ----

    @Test
    void isHardUnderlineFill_default_isNotDefined() {
        assertThat(createFormat().getIsHardUnderlineFill()).isEqualTo(NullableBool.NOT_DEFINED);
    }

    @Test
    void isHardUnderlineFill_setTrue_roundTrips() {
        var fmt = createFormat();
        fmt.setIsHardUnderlineFill(NullableBool.TRUE);
        assertThat(fmt.getIsHardUnderlineFill()).isEqualTo(NullableBool.TRUE);
    }

    @Test
    void isHardUnderlineFill_setFalse_roundTrips() {
        var fmt = createFormat();
        fmt.setIsHardUnderlineFill(NullableBool.FALSE);
        assertThat(fmt.getIsHardUnderlineFill()).isEqualTo(NullableBool.FALSE);
    }

    // ---- spell check ----

    @Test
    void spellCheck_defaultIsFalse() {
        assertThat(createFormat().getSpellCheck()).isFalse();
    }

    @Test
    void spellCheck_setTrue_removesNoProof() {
        var fmt = createFormat();
        fmt.setSpellCheck(true);
        assertThat(rprElement.hasAttribute("noProof")).isFalse();
    }

    @Test
    void spellCheck_setFalse_setsNoProof() {
        var fmt = createFormat();
        fmt.setSpellCheck(false);
        assertThat(rprElement.getAttribute("noProof")).isEqualTo("1");
        assertThat(fmt.getSpellCheck()).isFalse();
    }

    @Test
    void spellCheck_withNoProofZero_isTrue() {
        rprElement.setAttribute("noProof", "0");
        assertThat(createFormat().getSpellCheck()).isTrue();
    }

    @Test
    void spellCheck_withErrAttribute_isTrue() {
        rprElement.setAttribute("err", "1");
        assertThat(createFormat().getSpellCheck()).isTrue();
    }

    @Test
    void spellCheck_withNoProofOne_isFalse() {
        rprElement.setAttribute("noProof", "1");
        assertThat(createFormat().getSpellCheck()).isFalse();
    }

    // ---- format object accessors ----

    @Test
    void lineFormat_isNotNull() {
        assertThat(createFormat().getLineFormat()).isNotNull();
    }

    @Test
    void fillFormat_isNotNull() {
        assertThat(createFormat().getFillFormat()).isNotNull();
    }

    @Test
    void effectFormat_isNotNull() {
        assertThat(createFormat().getEffectFormat()).isNotNull();
    }

    @Test
    void highlightColor_isNotNull() {
        assertThat(createFormat().getHighlightColor()).isNotNull();
    }

    @Test
    void underlineLineFormat_isNotNull() {
        assertThat(createFormat().getUnderlineLineFormat()).isNotNull();
    }

    @Test
    void underlineFillFormat_isNotNull() {
        assertThat(createFormat().getUnderlineFillFormat()).isNotNull();
    }

    @Test
    void effectFormat_returnsEffectFormatInstance() {
        var fmt = createFormat();
        IEffectFormat ef = fmt.getEffectFormat();
        assertThat(ef).isInstanceOf(EffectFormat.class);
    }

    @Test
    void fillFormat_returnsFillFormatInstance() {
        var fmt = createFormat();
        IFillFormat ff = fmt.getFillFormat();
        assertThat(ff).isInstanceOf(FillFormat.class);
    }

    @Test
    void lineFormat_returnsLineFormatInstance() {
        var fmt = createFormat();
        ILineFormat lf = fmt.getLineFormat();
        assertThat(lf).isInstanceOf(LineFormat.class);
    }

    // ---- save callback ----

    @Test
    void saveCallback_invokedOnPropertyChange() {
        int[] callCount = {0};
        var fmt = createFormatWithCallback(() -> callCount[0]++);
        fmt.setFontBold(NullableBool.TRUE);
        assertThat(callCount[0]).isEqualTo(1);
        fmt.setFontHeight(12.0f);
        assertThat(callCount[0]).isEqualTo(2);
    }

    @Test
    void saveCallback_invokedOnFontChange() {
        int[] callCount = {0};
        var fmt = createFormatWithCallback(() -> callCount[0]++);
        fmt.setLatinFont(new FontData("Calibri"));
        assertThat(callCount[0]).isEqualTo(1);
    }

    @Test
    void saveCallback_invokedOnLanguageChange() {
        int[] callCount = {0};
        var fmt = createFormatWithCallback(() -> callCount[0]++);
        fmt.setLanguageId("en-US");
        assertThat(callCount[0]).isEqualTo(1);
    }

    // ---- pre-populated XML round-trip ----

    @Test
    void prePopulatedAttributes_readCorrectly() {
        rprElement.setAttribute("b", "1");
        rprElement.setAttribute("i", "0");
        rprElement.setAttribute("sz", "2400");
        rprElement.setAttribute("u", "sng");
        rprElement.setAttribute("lang", "fr-FR");

        var fmt = createFormat();
        assertThat(fmt.getFontBold()).isEqualTo(NullableBool.TRUE);
        assertThat(fmt.getFontItalic()).isEqualTo(NullableBool.FALSE);
        assertThat(fmt.getFontHeight()).isEqualTo(24.0f);
        assertThat(fmt.getFontUnderline()).isEqualTo(TextUnderlineType.SINGLE);
        assertThat(fmt.getLanguageId()).isEqualTo("fr-FR");
    }

    @Test
    void prePopulatedFontElement_readCorrectly() {
        Element latin = doc.createElementNS(NS_A, "a:latin");
        latin.setAttribute("typeface", "Calibri");
        rprElement.appendChild(latin);

        assertThat(createFormat().getLatinFont()).isNotNull();
        assertThat(createFormat().getLatinFont().getFontName()).isEqualTo("Calibri");
    }

    @Test
    void prePopulatedStrike_readCorrectly() {
        rprElement.setAttribute("strike", "dblStrike");
        assertThat(createFormat().getStrikethroughType()).isEqualTo(TextStrikethroughType.DOUBLE);
    }

    @Test
    void prePopulatedCap_readCorrectly() {
        rprElement.setAttribute("cap", "all");
        assertThat(createFormat().getTextCapType()).isEqualTo(TextCapType.ALL);
    }
}
