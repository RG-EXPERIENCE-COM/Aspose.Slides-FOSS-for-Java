package org.aspose.slides.foss;

import org.aspose.slides.foss.drawing.Color;
import org.aspose.slides.foss.drawing.PointF;
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
 * Tests for {@link ColorFormat}: color type switching, RGB read/write, scheme colors,
 * preset colors, alpha handling, float components, and drawing primitive integration.
 *
 * <p>Covers color usage in effects, PointF integration, and direct ColorFormat behavior.</p>
 */
class ColorFormatTest {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";

    private Document doc;
    private Element parentElement;

    @BeforeEach
    void setUp() throws Exception {
        doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .newDocument();
        parentElement = doc.createElementNS(NS_A, "a:solidFill");
        doc.appendChild(parentElement);
    }

    private ColorFormat createColorFormat() {
        return new ColorFormat(parentElement);
    }

    // ---- color type ----

    @Test
    void colorType_noChildren_isNotDefined() {
        var cf = createColorFormat();

        assertThat(cf.getColorType()).isEqualTo(ColorType.NOT_DEFINED);
    }

    @Test
    void setColorType_rgb_createsElement() {
        var cf = createColorFormat();

        cf.setColorType(ColorType.RGB);

        assertThat(cf.getColorType()).isEqualTo(ColorType.RGB);
    }

    @Test
    void setColorType_scheme_createsElement() {
        var cf = createColorFormat();

        cf.setColorType(ColorType.SCHEME);

        assertThat(cf.getColorType()).isEqualTo(ColorType.SCHEME);
    }

    @Test
    void setColorType_preset_createsElement() {
        var cf = createColorFormat();

        cf.setColorType(ColorType.PRESET);

        assertThat(cf.getColorType()).isEqualTo(ColorType.PRESET);
    }

    @Test
    void setColorType_system_createsElement() {
        var cf = createColorFormat();

        cf.setColorType(ColorType.SYSTEM);

        assertThat(cf.getColorType()).isEqualTo(ColorType.SYSTEM);
    }

    @Test
    void setColorType_hsl_createsElement() {
        var cf = createColorFormat();

        cf.setColorType(ColorType.HSL);

        assertThat(cf.getColorType()).isEqualTo(ColorType.HSL);
    }

    @Test
    void setColorType_rgbPercentage_createsElement() {
        var cf = createColorFormat();

        cf.setColorType(ColorType.RGB_PERCENTAGE);

        assertThat(cf.getColorType()).isEqualTo(ColorType.RGB_PERCENTAGE);
    }

    @Test
    void setColorType_notDefined_clearsExisting() {
        var cf = createColorFormat();
        cf.setColorType(ColorType.RGB);

        cf.setColorType(ColorType.NOT_DEFINED);

        assertThat(cf.getColorType()).isEqualTo(ColorType.NOT_DEFINED);
    }

    @Test
    void setColorType_sameTwice_noOp() {
        var cf = createColorFormat();
        cf.setColor(new Color(100, 200, 50));

        cf.setColorType(ColorType.RGB);

        // Should not reset the color value since type is already RGB
        assertThat(cf.getColor().getR()).isEqualTo(100);
        assertThat(cf.getColor().getG()).isEqualTo(200);
        assertThat(cf.getColor().getB()).isEqualTo(50);
    }

    @Test
    void setColorType_switchingClearsPrevious() {
        var cf = createColorFormat();
        cf.setColorType(ColorType.RGB);

        cf.setColorType(ColorType.SCHEME);

        assertThat(cf.getColorType()).isEqualTo(ColorType.SCHEME);
    }

    // ---- color (RGB) ----

    @Test
    void getColor_noElement_returnsBlack() {
        var cf = createColorFormat();

        Color c = cf.getColor();

        assertThat(c).isEqualTo(new Color(255, 0, 0, 0));
    }

    @Test
    void setColor_rgb_persists() {
        var cf = createColorFormat();

        cf.setColor(new Color(255, 0, 128));

        Color c = cf.getColor();
        assertThat(c.getR()).isEqualTo(255);
        assertThat(c.getG()).isEqualTo(0);
        assertThat(c.getB()).isEqualTo(128);
        assertThat(c.getA()).isEqualTo(255);
    }

    @Test
    void setColor_withAlpha_persists() {
        var cf = createColorFormat();

        cf.setColor(new Color(128, 255, 0, 0));

        Color c = cf.getColor();
        assertThat(c.getR()).isEqualTo(255);
        assertThat(c.getA()).isEqualTo(128);
    }

    @Test
    void setColor_setsColorTypeToRgb() {
        var cf = createColorFormat();
        cf.setColorType(ColorType.SCHEME);

        cf.setColor(new Color(10, 20, 30));

        assertThat(cf.getColorType()).isEqualTo(ColorType.RGB);
    }

    // ---- glow effect color integration ----

    @Test
    void setColor_goldConstant_persists() {
        var cf = createColorFormat();

        cf.setColor(Color.GOLD);

        Color c = cf.getColor();
        assertThat(c.getR()).isEqualTo(255);
        assertThat(c.getG()).isEqualTo(215);
        assertThat(c.getB()).isEqualTo(0);
    }

    @Test
    void setColor_fromArgbSemiTransparent_persists() {
        var cf = createColorFormat();

        cf.setColor(Color.fromArgb(128, 0, 0, 0));

        Color c = cf.getColor();
        assertThat(c.getA()).isEqualTo(128);
        assertThat(c.getR()).isEqualTo(0);
        assertThat(c.getG()).isEqualTo(0);
        assertThat(c.getB()).isEqualTo(0);
    }

    // ---- scheme color ----

    @Test
    void getSchemeColor_noElement_isNotDefined() {
        var cf = createColorFormat();

        assertThat(cf.getSchemeColor()).isEqualTo(SchemeColor.NOT_DEFINED);
    }

    @Test
    void setSchemeColor_accent1_persists() {
        var cf = createColorFormat();

        cf.setSchemeColor(SchemeColor.ACCENT1);

        assertThat(cf.getSchemeColor()).isEqualTo(SchemeColor.ACCENT1);
        assertThat(cf.getColorType()).isEqualTo(ColorType.SCHEME);
    }

    @Test
    void setSchemeColor_notDefined_ignored() {
        var cf = createColorFormat();
        cf.setSchemeColor(SchemeColor.ACCENT1);

        cf.setSchemeColor(SchemeColor.NOT_DEFINED);

        // NOT_DEFINED is a no-op, original stays
        assertThat(cf.getSchemeColor()).isEqualTo(SchemeColor.ACCENT1);
    }

    static Stream<SchemeColor> schemeColors() {
        return Stream.of(
                SchemeColor.BACKGROUND1, SchemeColor.TEXT1,
                SchemeColor.ACCENT1, SchemeColor.ACCENT2, SchemeColor.ACCENT3,
                SchemeColor.ACCENT4, SchemeColor.ACCENT5, SchemeColor.ACCENT6,
                SchemeColor.HYPERLINK, SchemeColor.FOLLOWED_HYPERLINK,
                SchemeColor.DARK1, SchemeColor.LIGHT1, SchemeColor.DARK2, SchemeColor.LIGHT2);
    }

    @ParameterizedTest
    @MethodSource("schemeColors")
    void setSchemeColor_allMappedValues_roundTrip(SchemeColor sc) {
        var cf = createColorFormat();
        cf.setSchemeColor(sc);
        assertThat(cf.getSchemeColor()).isEqualTo(sc);
    }

    // ---- preset color ----

    @Test
    void getPresetColor_noElement_isNotDefined() {
        var cf = createColorFormat();

        assertThat(cf.getPresetColor()).isEqualTo(PresetColor.NOT_DEFINED);
    }

    @Test
    void setPresetColor_aliceBlue_persists() {
        var cf = createColorFormat();

        cf.setPresetColor(PresetColor.ALICE_BLUE);

        assertThat(cf.getPresetColor()).isEqualTo(PresetColor.ALICE_BLUE);
        assertThat(cf.getColorType()).isEqualTo(ColorType.PRESET);
    }

    @Test
    void setPresetColor_notDefined_ignored() {
        var cf = createColorFormat();
        cf.setPresetColor(PresetColor.BLACK);

        cf.setPresetColor(PresetColor.NOT_DEFINED);

        assertThat(cf.getPresetColor()).isEqualTo(PresetColor.BLACK);
    }

    // ---- component access (r, g, b) ----

    @Test
    void setR_updatesRedOnly() {
        var cf = createColorFormat();
        cf.setColor(new Color(10, 20, 30));

        cf.setR(99);

        assertThat(cf.getR()).isEqualTo(99);
        assertThat(cf.getG()).isEqualTo(20);
        assertThat(cf.getB()).isEqualTo(30);
    }

    @Test
    void setG_updatesGreenOnly() {
        var cf = createColorFormat();
        cf.setColor(new Color(10, 20, 30));

        cf.setG(99);

        assertThat(cf.getR()).isEqualTo(10);
        assertThat(cf.getG()).isEqualTo(99);
        assertThat(cf.getB()).isEqualTo(30);
    }

    @Test
    void setB_updatesBlueOnly() {
        var cf = createColorFormat();
        cf.setColor(new Color(10, 20, 30));

        cf.setB(99);

        assertThat(cf.getR()).isEqualTo(10);
        assertThat(cf.getG()).isEqualTo(20);
        assertThat(cf.getB()).isEqualTo(99);
    }

    // ---- float components ----

    @Test
    void floatR_roundTrips() {
        var cf = createColorFormat();
        cf.setColor(new Color(0, 0, 0));

        cf.setFloatR(0.5f);

        assertThat(cf.getR()).isEqualTo(128);
        assertThat(cf.getFloatR()).isCloseTo(128 / 255.0f, org.assertj.core.data.Offset.offset(0.01f));
    }

    @Test
    void floatG_roundTrips() {
        var cf = createColorFormat();
        cf.setColor(new Color(0, 0, 0));

        cf.setFloatG(1.0f);

        assertThat(cf.getG()).isEqualTo(255);
        assertThat(cf.getFloatG()).isEqualTo(1.0f);
    }

    @Test
    void floatB_zero_returnsZero() {
        var cf = createColorFormat();
        cf.setColor(new Color(0, 0, 0));

        assertThat(cf.getFloatB()).isEqualTo(0.0f);
    }

    // ---- save callback ----

    @Test
    void setColor_triggersCallback() {
        int[] callCount = {0};
        var cf = new ColorFormat(parentElement, () -> callCount[0]++);

        cf.setColor(new Color(255, 0, 0));

        assertThat(callCount[0]).isEqualTo(1);
    }

    // ---- camelCase / UPPER_SNAKE conversion ----

    @Test
    void camelToUpperSnake_converts() {
        assertThat(ColorFormat.camelToUpperSnake("aliceBlue")).isEqualTo("ALICE_BLUE");
        assertThat(ColorFormat.camelToUpperSnake("black")).isEqualTo("BLACK");
        assertThat(ColorFormat.camelToUpperSnake("darkSlateGray")).isEqualTo("DARK_SLATE_GRAY");
    }

    @Test
    void upperSnakeToCamel_converts() {
        assertThat(ColorFormat.upperSnakeToCamel("ALICE_BLUE")).isEqualTo("aliceBlue");
        assertThat(ColorFormat.upperSnakeToCamel("BLACK")).isEqualTo("black");
        assertThat(ColorFormat.upperSnakeToCamel("DARK_SLATE_GRAY")).isEqualTo("darkSlateGray");
    }

    // ---- solid fill color integration ----

    @Test
    void setColor_solidFillRgb_componentsPersist() {
        var cf = createColorFormat();

        cf.setColor(Color.fromArgb(255, 0, 128, 255));

        Color c = cf.getColor();
        assertThat(c.getR()).isEqualTo(0);
        assertThat(c.getG()).isEqualTo(128);
        assertThat(c.getB()).isEqualTo(255);
        assertThat(cf.getColorType()).isEqualTo(ColorType.RGB);
    }

    // ---- pattern fill color integration ----

    @Test
    void setColor_darkBlueConstant_persists() {
        var cf = createColorFormat();

        cf.setColor(Color.DARK_BLUE);

        Color c = cf.getColor();
        assertThat(c.getR()).isEqualTo(0);
        assertThat(c.getG()).isEqualTo(0);
        assertThat(c.getB()).isEqualTo(139);
    }

    @Test
    void setColor_lightYellowConstant_persists() {
        var cf = createColorFormat();

        cf.setColor(Color.LIGHT_YELLOW);

        Color c = cf.getColor();
        assertThat(c.getR()).isEqualTo(255);
        assertThat(c.getG()).isEqualTo(255);
        assertThat(c.getB()).isEqualTo(224);
    }

    // ---- line color integration ----

    @Test
    void setColor_darkRedConstant_persists() {
        var cf = createColorFormat();

        cf.setColor(Color.DARK_RED);

        Color c = cf.getColor();
        assertThat(c.getR()).isEqualTo(Color.DARK_RED.getR());
        assertThat(c.getG()).isEqualTo(0);
        assertThat(c.getB()).isEqualTo(0);
    }

    // ---- line dash style color integration ----

    @Test
    void setColor_blackConstant_persists() {
        var cf = createColorFormat();

        cf.setColor(Color.BLACK);

        Color c = cf.getColor();
        assertThat(c.getR()).isEqualTo(0);
        assertThat(c.getG()).isEqualTo(0);
        assertThat(c.getB()).isEqualTo(0);
    }

    // ---- PointF integration ----

    @Test
    void pointF_constructionAndAccess() {
        var pt = new PointF(2.0f, 3.0f);

        assertThat(pt.getX()).isEqualTo(2.0f);
        assertThat(pt.getY()).isEqualTo(3.0f);
    }

    @Test
    void pointF_equality() {
        var a = new PointF(1.0f, 1.0f);
        var b = new PointF(1.0f, 1.0f);
        var c = new PointF(2.0f, 2.0f);

        assertThat(a).isEqualTo(b);
        assertThat(a).isNotEqualTo(c);
    }

    @Test
    void pointF_multipleInstances_independent() {
        var p1 = new PointF(1.0f, 1.0f);
        var p2 = new PointF(2.0f, 2.0f);
        var p3 = new PointF(3.0f, 3.0f);

        assertThat(p1.getX()).isEqualTo(1.0f);
        assertThat(p2.getX()).isEqualTo(2.0f);
        assertThat(p3.getX()).isEqualTo(3.0f);
    }

    // ---- Color integration ----

    @Test
    void color_goldConstant_hasExpectedComponents() {
        assertThat(Color.GOLD.getR()).isEqualTo(255);
        assertThat(Color.GOLD.getG()).isEqualTo(215);
        assertThat(Color.GOLD.getB()).isEqualTo(0);
        assertThat(Color.GOLD.getA()).isEqualTo(255);
    }

    @Test
    void color_fromArgb_semiTransparentBlack() {
        var c = Color.fromArgb(128, 0, 0, 0);

        assertThat(c.getA()).isEqualTo(128);
        assertThat(c.getR()).isEqualTo(0);
    }
}
