package org.aspose.slides.foss;

import org.aspose.slides.foss.export.SaveFormat;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for miscellaneous enums: bevel, bullet, camera, color type,
 * font alignment, gradient direction, lighting, material, preset color/shadow,
 * rectangle alignment, scheme color, slide layout, source format,
 * table style, tile flip, nullable bool, numbered bullet style, and SaveFormat.
 */
class MiscEnumsTest {

    @Test
    void bevelPresetTypeHasAllConstants() {
        assertThat(BevelPresetType.values()).hasSize(13);
        assertThat(BevelPresetType.ANGLE.getValue()).isEqualTo("Angle");
    }

    @Test
    void bulletTypeHasAllConstants() {
        assertThat(BulletType.values()).hasSize(5);
        assertThat(BulletType.SYMBOL.getValue()).isEqualTo("Symbol");
    }

    @Test
    void cameraPresetTypeHasAllConstants() {
        assertThat(CameraPresetType.values()).hasSize(63);
        assertThat(CameraPresetType.PERSPECTIVE_FRONT.getValue()).isEqualTo("PerspectiveFront");
    }

    @Test
    void colorTypeHasAllConstants() {
        assertThat(ColorType.values()).hasSize(7);
        assertThat(ColorType.RGB.getValue()).isEqualTo("RGB");
    }

    @Test
    void fontAlignmentHasAllConstants() {
        assertThat(FontAlignment.values()).hasSize(6);
        assertThat(FontAlignment.BASELINE.getValue()).isEqualTo("Baseline");
    }

    @Test
    void gradientDirectionHasAllConstants() {
        assertThat(GradientDirection.values()).hasSize(6);
        assertThat(GradientDirection.FROM_CENTER.getValue()).isEqualTo("FromCenter");
    }

    @Test
    void lightingDirectionHasAllConstants() {
        assertThat(LightingDirection.values()).hasSize(9);
        assertThat(LightingDirection.TOP_LEFT.getValue()).isEqualTo("TopLeft");
    }

    @Test
    void lightRigPresetTypeHasAllConstants() {
        assertThat(LightRigPresetType.values()).hasSize(28);
        assertThat(LightRigPresetType.THREE_PT.getValue()).isEqualTo("ThreePt");
    }

    @Test
    void materialPresetTypeHasAllConstants() {
        assertThat(MaterialPresetType.values()).hasSize(16);
        assertThat(MaterialPresetType.METAL.getValue()).isEqualTo("Metal");
    }

    @Test
    void nullableBoolHasAllConstants() {
        assertThat(NullableBool.values()).hasSize(3);
        assertThat(NullableBool.TRUE.getValue()).isEqualTo("True");
        assertThat(NullableBool.FALSE.getValue()).isEqualTo("False");
    }

    @Test
    void numberedBulletStyleHasAllConstants() {
        assertThat(NumberedBulletStyle.values()).hasSize(42);
        assertThat(NumberedBulletStyle.BULLET_ARABIC_PERIOD.getValue()).isEqualTo("BulletArabicPeriod");
    }

    @Test
    void presetColorHasAllConstants() {
        assertThat(PresetColor.values()).hasSize(141);
        assertThat(PresetColor.ALICE_BLUE.getValue()).isEqualTo("AliceBlue");
        assertThat(PresetColor.YELLOW_GREEN.getValue()).isEqualTo("YellowGreen");
    }

    @Test
    void presetShadowTypeHasAllConstants() {
        assertThat(PresetShadowType.values()).hasSize(20);
        assertThat(PresetShadowType.TOP_LEFT_DROP_SHADOW.getValue()).isEqualTo("TopLeftDropShadow");
    }

    @Test
    void rectangleAlignmentHasAllConstants() {
        assertThat(RectangleAlignment.values()).hasSize(10);
        assertThat(RectangleAlignment.CENTER.getValue()).isEqualTo("Center");
    }

    @Test
    void schemeColorHasAllConstants() {
        assertThat(SchemeColor.values()).hasSize(18);
        assertThat(SchemeColor.ACCENT1.getValue()).isEqualTo("Accent1");
    }

    @Test
    void slideLayoutTypeHasAllConstants() {
        assertThat(SlideLayoutType.values()).hasSize(36);
        assertThat(SlideLayoutType.BLANK.getValue()).isEqualTo("Blank");
    }

    @Test
    void sourceFormatHasAllConstants() {
        assertThat(SourceFormat.values()).hasSize(3);
        assertThat(SourceFormat.PPTX.getValue()).isEqualTo("Pptx");
    }

    @Test
    void tableStylePresetHasAllConstants() {
        assertThat(TableStylePreset.values()).hasSize(76);
        assertThat(TableStylePreset.DARK_STYLE1.getValue()).isEqualTo("DarkStyle1");
    }

    @Test
    void tileFlipHasAllConstants() {
        assertThat(TileFlip.values()).hasSize(5);
        assertThat(TileFlip.FLIP_BOTH.getValue()).isEqualTo("FlipBoth");
    }

    @Test
    void saveFormatHasConstants() {
        assertThat(SaveFormat.PPTX.getValue()).isEqualTo("Pptx");
        assertThat(SaveFormat.PDF.getValue()).isEqualTo("Pdf");
    }
}
