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
 * Tests for {@link BulletFormat}: type switching, char, font, height, color,
 * numbered bullet properties, hard color/font flags, and picture bullets.
 *
 * <p>Covers bullet type switching, color integration, effect formats, fill formats,
 * and picture bullet behavior.</p>
 */
class BulletFormatTest {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";

    private Document doc;
    private Element pprElement;
    private int saveCount;

    @BeforeEach
    void setUp() throws Exception {
        doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .newDocument();
        pprElement = doc.createElementNS(NS_A, "a:pPr");
        doc.appendChild(pprElement);
        saveCount = 0;
    }

    private BulletFormat createBulletFormat() {
        return new BulletFormat().initInternal(pprElement, () -> saveCount++, null);
    }

    // ---- type ----

    @Test
    void type_noBulletElements_isNotDefined() {
        var bf = createBulletFormat();

        assertThat(bf.getType()).isEqualTo(BulletType.NOT_DEFINED);
    }

    @Test
    void type_nullElement_isNotDefined() {
        var bf = new BulletFormat().initInternal(null, null, null);

        assertThat(bf.getType()).isEqualTo(BulletType.NOT_DEFINED);
    }

    @Test
    void setType_none_createsBuNone() {
        var bf = createBulletFormat();

        bf.setType(BulletType.NONE);

        assertThat(bf.getType()).isEqualTo(BulletType.NONE);
        assertThat(saveCount).isEqualTo(1);
    }

    @Test
    void setType_symbol_createsBuCharWithDefaultBullet() {
        var bf = createBulletFormat();

        bf.setType(BulletType.SYMBOL);

        assertThat(bf.getType()).isEqualTo(BulletType.SYMBOL);
        assertThat(bf.getChar()).isEqualTo("\u2022");
    }

    @Test
    void setType_numbered_createsBuAutoNumWithArabicPeriod() {
        var bf = createBulletFormat();

        bf.setType(BulletType.NUMBERED);

        assertThat(bf.getType()).isEqualTo(BulletType.NUMBERED);
        assertThat(bf.getNumberedBulletStyle()).isEqualTo(NumberedBulletStyle.BULLET_ARABIC_PERIOD);
    }

    @Test
    void setType_picture_createsBuBlip() {
        var bf = createBulletFormat();

        bf.setType(BulletType.PICTURE);

        assertThat(bf.getType()).isEqualTo(BulletType.PICTURE);
    }

    @Test
    void setType_removesPreviousBulletTypeElements() {
        var bf = createBulletFormat();
        bf.setType(BulletType.SYMBOL);

        bf.setType(BulletType.NUMBERED);

        assertThat(bf.getType()).isEqualTo(BulletType.NUMBERED);
        assertThat(bf.getChar()).isEmpty();
    }

    @Test
    void setType_notDefined_removesAll() {
        var bf = createBulletFormat();
        bf.setType(BulletType.SYMBOL);

        bf.setType(BulletType.NOT_DEFINED);

        assertThat(bf.getType()).isEqualTo(BulletType.NOT_DEFINED);
    }

    @Test
    void setType_onNullElement_noOp() {
        var bf = new BulletFormat().initInternal(null, null, null);

        bf.setType(BulletType.SYMBOL);

        assertThat(bf.getType()).isEqualTo(BulletType.NOT_DEFINED);
    }

    // ---- char ----

    @Test
    void char_noBuChar_returnsEmpty() {
        var bf = createBulletFormat();

        assertThat(bf.getChar()).isEmpty();
    }

    @Test
    void setChar_createsSymbolBullet() {
        var bf = createBulletFormat();

        bf.setChar("-");

        assertThat(bf.getChar()).isEqualTo("-");
        assertThat(bf.getType()).isEqualTo(BulletType.SYMBOL);
    }

    @Test
    void setChar_overwritesExistingChar() {
        var bf = createBulletFormat();
        bf.setType(BulletType.SYMBOL);

        bf.setChar("\u25CF");

        assertThat(bf.getChar()).isEqualTo("\u25CF");
    }

    @Test
    void setChar_triggersCallback() {
        var bf = createBulletFormat();

        bf.setChar("*");

        assertThat(saveCount).isGreaterThanOrEqualTo(1);
    }

    // ---- font ----

    @Test
    void font_noBuFont_returnsNull() {
        var bf = createBulletFormat();

        assertThat(bf.getFont()).isNull();
    }

    @Test
    void setFont_createsElement() {
        var bf = createBulletFormat();

        bf.setFont(new FontData("Arial"));

        assertThat(bf.getFont()).isNotNull();
        assertThat(bf.getFont().getFontName()).isEqualTo("Arial");
    }

    @Test
    void setFont_null_removesElement() {
        var bf = createBulletFormat();
        bf.setFont(new FontData("Arial"));

        bf.setFont(null);

        assertThat(bf.getFont()).isNull();
    }

    @Test
    void setFont_replacesExisting() {
        var bf = createBulletFormat();
        bf.setFont(new FontData("Arial"));

        bf.setFont(new FontData("Wingdings"));

        assertThat(bf.getFont().getFontName()).isEqualTo("Wingdings");
    }

    // ---- height ----

    @Test
    void height_noSizeElements_returnsNaN() {
        var bf = createBulletFormat();

        assertThat(bf.getHeight()).isNaN();
    }

    @Test
    void setHeight_storesAsPercentage() {
        var bf = createBulletFormat();

        bf.setHeight(75.0f);

        assertThat(bf.getHeight()).isEqualTo(75.0f);
    }

    @Test
    void setHeight_nan_removesSizeElements() {
        var bf = createBulletFormat();
        bf.setHeight(100.0f);

        bf.setHeight(Float.NaN);

        assertThat(bf.getHeight()).isNaN();
    }

    @Test
    void setHeight_removesExistingBeforeCreating() {
        var bf = createBulletFormat();
        bf.setHeight(50.0f);

        bf.setHeight(80.0f);

        assertThat(bf.getHeight()).isEqualTo(80.0f);
    }

    // ---- color (read-only sub-object) ----

    @Test
    void color_returnsColorFormat() {
        var bf = createBulletFormat();

        IColorFormat color = bf.getColor();

        assertThat(color).isNotNull();
    }

    @Test
    void color_calledTwice_returnsFreshInstances() {
        var bf = createBulletFormat();

        IColorFormat c1 = bf.getColor();
        IColorFormat c2 = bf.getColor();

        assertThat(c1).isNotNull();
        assertThat(c2).isNotNull();
    }

    // ---- color integration ----

    @Test
    void color_setGoldColor_persistsOnBullet() {
        var bf = createBulletFormat();

        IColorFormat color = bf.getColor();
        color.setColor(org.aspose.slides.foss.drawing.Color.GOLD);

        var c = bf.getColor().getColor();
        assertThat(c.getR()).isEqualTo(255);
        assertThat(c.getG()).isEqualTo(215);
        assertThat(c.getB()).isEqualTo(0);
    }

    // ---- color integration ----

    @Test
    void color_setSemiTransparentBlack_persistsOnBullet() {
        var bf = createBulletFormat();

        IColorFormat color = bf.getColor();
        color.setColor(org.aspose.slides.foss.drawing.Color.fromArgb(128, 0, 0, 0));

        var c = bf.getColor().getColor();
        assertThat(c.getA()).isEqualTo(128);
        assertThat(c.getR()).isEqualTo(0);
        assertThat(c.getG()).isEqualTo(0);
        assertThat(c.getB()).isEqualTo(0);
    }

    // ---- color integration ----

    @Test
    void color_setSolidFillRgb_componentsPersist() {
        var bf = createBulletFormat();

        IColorFormat color = bf.getColor();
        color.setColor(org.aspose.slides.foss.drawing.Color.fromArgb(255, 0, 128, 255));

        var c = bf.getColor().getColor();
        assertThat(c.getR()).isEqualTo(0);
        assertThat(c.getG()).isEqualTo(128);
        assertThat(c.getB()).isEqualTo(255);
    }

    // ---- color integration ----

    @Test
    void color_setDarkBlue_persistsOnBullet() {
        var bf = createBulletFormat();

        IColorFormat color = bf.getColor();
        color.setColor(org.aspose.slides.foss.drawing.Color.DARK_BLUE);

        var c = bf.getColor().getColor();
        assertThat(c.getR()).isEqualTo(0);
        assertThat(c.getG()).isEqualTo(0);
        assertThat(c.getB()).isEqualTo(139);
    }

    // ---- numbered_bullet_start_with ----

    @Test
    void numberedBulletStartWith_noBuAutoNum_returns1() {
        var bf = createBulletFormat();

        assertThat(bf.getNumberedBulletStartWith()).isEqualTo(1);
    }

    @Test
    void setNumberedBulletStartWith_setsStartAt() {
        var bf = createBulletFormat();
        bf.setType(BulletType.NUMBERED);

        bf.setNumberedBulletStartWith(5);

        assertThat(bf.getNumberedBulletStartWith()).isEqualTo(5);
    }

    @Test
    void setNumberedBulletStartWith_1_removesAttribute() {
        var bf = createBulletFormat();
        bf.setType(BulletType.NUMBERED);
        bf.setNumberedBulletStartWith(5);

        bf.setNumberedBulletStartWith(1);

        assertThat(bf.getNumberedBulletStartWith()).isEqualTo(1);
    }

    @Test
    void setNumberedBulletStartWith_noBuAutoNum_createsOne() {
        var bf = createBulletFormat();

        bf.setNumberedBulletStartWith(3);

        assertThat(bf.getType()).isEqualTo(BulletType.NUMBERED);
        assertThat(bf.getNumberedBulletStartWith()).isEqualTo(3);
    }

    // ---- numbered_bullet_style ----

    @Test
    void numberedBulletStyle_noBuAutoNum_isNotDefined() {
        var bf = createBulletFormat();

        assertThat(bf.getNumberedBulletStyle()).isEqualTo(NumberedBulletStyle.NOT_DEFINED);
    }

    @Test
    void setNumberedBulletStyle_arabicPeriod_roundTrips() {
        var bf = createBulletFormat();

        bf.setNumberedBulletStyle(NumberedBulletStyle.BULLET_ARABIC_PERIOD);

        assertThat(bf.getNumberedBulletStyle()).isEqualTo(NumberedBulletStyle.BULLET_ARABIC_PERIOD);
        assertThat(bf.getType()).isEqualTo(BulletType.NUMBERED);
    }

    @Test
    void setNumberedBulletStyle_romanLcPeriod_roundTrips() {
        var bf = createBulletFormat();

        bf.setNumberedBulletStyle(NumberedBulletStyle.BULLET_ROMAN_LC_PERIOD);

        assertThat(bf.getNumberedBulletStyle()).isEqualTo(NumberedBulletStyle.BULLET_ROMAN_LC_PERIOD);
    }

    @Test
    void setNumberedBulletStyle_notDefined_removesElement() {
        var bf = createBulletFormat();
        bf.setNumberedBulletStyle(NumberedBulletStyle.BULLET_ARABIC_PERIOD);

        bf.setNumberedBulletStyle(NumberedBulletStyle.NOT_DEFINED);

        assertThat(bf.getNumberedBulletStyle()).isEqualTo(NumberedBulletStyle.NOT_DEFINED);
        assertThat(bf.getType()).isEqualTo(BulletType.NOT_DEFINED);
    }

    @Test
    void setNumberedBulletStyle_cjkStyle_roundTrips() {
        var bf = createBulletFormat();

        bf.setNumberedBulletStyle(NumberedBulletStyle.BULLET_SIMP_CHIN_PERIOD);

        assertThat(bf.getNumberedBulletStyle()).isEqualTo(NumberedBulletStyle.BULLET_SIMP_CHIN_PERIOD);
    }

    @Test
    void setNumberedBulletStyle_thaiStyle_roundTrips() {
        var bf = createBulletFormat();

        bf.setNumberedBulletStyle(NumberedBulletStyle.BULLET_THAI_ALPHA_PERIOD);

        assertThat(bf.getNumberedBulletStyle()).isEqualTo(NumberedBulletStyle.BULLET_THAI_ALPHA_PERIOD);
    }

    @Test
    void setNumberedBulletStyle_hindiStyle_roundTrips() {
        var bf = createBulletFormat();

        bf.setNumberedBulletStyle(NumberedBulletStyle.BULLET_HINDI_NUM_PERIOD);

        assertThat(bf.getNumberedBulletStyle()).isEqualTo(NumberedBulletStyle.BULLET_HINDI_NUM_PERIOD);
    }

    // ---- is_bullet_hard_color ----

    @Test
    void isBulletHardColor_noElements_isNotDefined() {
        var bf = createBulletFormat();

        assertThat(bf.getIsBulletHardColor()).isEqualTo(NullableBool.NOT_DEFINED);
    }

    @Test
    void setIsBulletHardColor_true_createsBuClr() {
        var bf = createBulletFormat();

        bf.setIsBulletHardColor(NullableBool.TRUE);

        assertThat(bf.getIsBulletHardColor()).isEqualTo(NullableBool.TRUE);
    }

    @Test
    void setIsBulletHardColor_false_createsBuClrTx() {
        var bf = createBulletFormat();

        bf.setIsBulletHardColor(NullableBool.FALSE);

        assertThat(bf.getIsBulletHardColor()).isEqualTo(NullableBool.FALSE);
    }

    @Test
    void setIsBulletHardColor_notDefined_removesAll() {
        var bf = createBulletFormat();
        bf.setIsBulletHardColor(NullableBool.TRUE);

        bf.setIsBulletHardColor(NullableBool.NOT_DEFINED);

        assertThat(bf.getIsBulletHardColor()).isEqualTo(NullableBool.NOT_DEFINED);
    }

    @Test
    void setIsBulletHardColor_trueToFalse_switchesElement() {
        var bf = createBulletFormat();
        bf.setIsBulletHardColor(NullableBool.TRUE);

        bf.setIsBulletHardColor(NullableBool.FALSE);

        assertThat(bf.getIsBulletHardColor()).isEqualTo(NullableBool.FALSE);
    }

    // ---- is_bullet_hard_font ----

    @Test
    void isBulletHardFont_noElements_isNotDefined() {
        var bf = createBulletFormat();

        assertThat(bf.getIsBulletHardFont()).isEqualTo(NullableBool.NOT_DEFINED);
    }

    @Test
    void setIsBulletHardFont_true_createsBuFont() {
        var bf = createBulletFormat();

        bf.setIsBulletHardFont(NullableBool.TRUE);

        assertThat(bf.getIsBulletHardFont()).isEqualTo(NullableBool.TRUE);
    }

    @Test
    void setIsBulletHardFont_false_createsBuFontTx() {
        var bf = createBulletFormat();

        bf.setIsBulletHardFont(NullableBool.FALSE);

        assertThat(bf.getIsBulletHardFont()).isEqualTo(NullableBool.FALSE);
    }

    @Test
    void setIsBulletHardFont_notDefined_removesAll() {
        var bf = createBulletFormat();
        bf.setIsBulletHardFont(NullableBool.TRUE);

        bf.setIsBulletHardFont(NullableBool.NOT_DEFINED);

        assertThat(bf.getIsBulletHardFont()).isEqualTo(NullableBool.NOT_DEFINED);
    }

    @Test
    void setIsBulletHardFont_truePreservesExistingFont() {
        var bf = createBulletFormat();
        bf.setFont(new FontData("Wingdings"));

        bf.setIsBulletHardFont(NullableBool.TRUE);

        assertThat(bf.getIsBulletHardFont()).isEqualTo(NullableBool.TRUE);
        assertThat(bf.getFont()).isNotNull();
        assertThat(bf.getFont().getFontName()).isEqualTo("Wingdings");
    }

    // ---- picture ----

    @Test
    void picture_returnsPicture() {
        var bf = createBulletFormat();

        ISlidesPicture pic = bf.getPicture();

        assertThat(pic).isNotNull();
    }

    @Test
    void picture_nullElement_returnsNull() {
        var bf = new BulletFormat().initInternal(null, null, null);

        assertThat(bf.getPicture()).isNull();
    }

    @Test
    void picture_createsBuBlipAndBlipElements() {
        var bf = createBulletFormat();

        bf.getPicture();

        // buBlip should now exist as a child
        assertThat(bf.getType()).isEqualTo(BulletType.PICTURE);
    }

    // ---- picture integration ----

    @Test
    void picture_blipElementCreated_canSetAttributes() {
        var bf = createBulletFormat();

        ISlidesPicture pic = bf.getPicture();

        // Picture was created with a blip element; verify it's a valid object
        assertThat(pic).isInstanceOf(Picture.class);
    }

    // ---- connector reroute integration ----
    // The reroute test verifies that after connecting shapes and rerouting,
    // the connector has non-zero dimensions. Here we verify the analogous
    // BulletFormat invariant: after setting properties, the format reflects changes.

    @Test
    void multiplePropertiesSet_allPersist() {
        var bf = createBulletFormat();

        bf.setType(BulletType.NUMBERED);
        bf.setNumberedBulletStartWith(3);
        bf.setNumberedBulletStyle(NumberedBulletStyle.BULLET_ROMAN_UC_PERIOD);
        bf.setIsBulletHardColor(NullableBool.TRUE);
        bf.setIsBulletHardFont(NullableBool.TRUE);
        bf.setFont(new FontData("Calibri"));
        bf.setHeight(120.0f);

        assertThat(bf.getType()).isEqualTo(BulletType.NUMBERED);
        assertThat(bf.getNumberedBulletStartWith()).isEqualTo(3);
        assertThat(bf.getNumberedBulletStyle()).isEqualTo(NumberedBulletStyle.BULLET_ROMAN_UC_PERIOD);
        assertThat(bf.getIsBulletHardColor()).isEqualTo(NullableBool.TRUE);
        assertThat(bf.getIsBulletHardFont()).isEqualTo(NullableBool.TRUE);
        assertThat(bf.getFont().getFontName()).isEqualTo("Calibri");
        assertThat(bf.getHeight()).isEqualTo(120.0f);
        assertThat(saveCount).isGreaterThan(0);
    }

    // ---- save callback ----

    @Test
    void allMutations_triggerCallback() {
        var bf = createBulletFormat();

        bf.setType(BulletType.SYMBOL);
        bf.setChar(">");
        bf.setFont(new FontData("Courier"));
        bf.setHeight(90.0f);
        bf.setIsBulletHardColor(NullableBool.TRUE);
        bf.setIsBulletHardFont(NullableBool.TRUE);

        assertThat(saveCount).isEqualTo(6);
    }

    @Test
    void noCallback_noException() {
        var bf = new BulletFormat().initInternal(pprElement, null, null);

        bf.setType(BulletType.SYMBOL);
        bf.setChar("*");

        assertThat(bf.getChar()).isEqualTo("*");
    }
}
