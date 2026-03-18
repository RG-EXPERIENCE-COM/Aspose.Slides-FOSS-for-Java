package org.aspose.slides.foss;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for enums used in fill format operations.
 * Ports the behavioral intent of test_fill_format.py by verifying that
 * the enum constants referenced in solid, gradient, pattern, picture,
 * and no-fill scenarios exist and carry the correct string values.
 */
class FillFormatEnumsTest {

    // ---- FillType ----

    @Test
    void fillTypeHasAllSevenConstants() {
        assertThat(FillType.values()).hasSize(7);
    }

    @Test
    void fillTypeConstantsExist() {
        assertThat(FillType.NOT_DEFINED).isNotNull();
        assertThat(FillType.NO_FILL).isNotNull();
        assertThat(FillType.SOLID).isNotNull();
        assertThat(FillType.GRADIENT).isNotNull();
        assertThat(FillType.PATTERN).isNotNull();
        assertThat(FillType.PICTURE).isNotNull();
        assertThat(FillType.GROUP).isNotNull();
    }

    @Test
    void fillTypeGetValueReturnsCorrectStrings() {
        assertThat(FillType.NOT_DEFINED.getValue()).isEqualTo("NotDefined");
        assertThat(FillType.NO_FILL.getValue()).isEqualTo("NoFill");
        assertThat(FillType.SOLID.getValue()).isEqualTo("Solid");
        assertThat(FillType.GRADIENT.getValue()).isEqualTo("Gradient");
        assertThat(FillType.PATTERN.getValue()).isEqualTo("Pattern");
        assertThat(FillType.PICTURE.getValue()).isEqualTo("Picture");
        assertThat(FillType.GROUP.getValue()).isEqualTo("Group");
    }

    @ParameterizedTest
    @EnumSource(FillType.class)
    void allFillTypeValuesAreNonNullAndNonEmpty(FillType ft) {
        assertThat(ft.getValue()).isNotNull().isNotEmpty();
    }

    // ---- GradientShape ----

    @Test
    void gradientShapeHasAllFiveConstants() {
        assertThat(GradientShape.values()).hasSize(5);
    }

    @Test
    void gradientShapeLinearValue() {
        assertThat(GradientShape.LINEAR.getValue()).isEqualTo("Linear");
    }

    @ParameterizedTest
    @EnumSource(GradientShape.class)
    void allGradientShapeValuesAreNonNullAndNonEmpty(GradientShape gs) {
        assertThat(gs.getValue()).isNotNull().isNotEmpty();
    }

    // ---- PatternStyle ----

    @Test
    void patternStyleHas56Constants() {
        assertThat(PatternStyle.values()).hasSize(56);
    }

    @Test
    void patternStylePercent50Value() {
        assertThat(PatternStyle.PERCENT50.getValue()).isEqualTo("Percent50");
    }

    @ParameterizedTest
    @EnumSource(PatternStyle.class)
    void allPatternStyleValuesAreNonNullAndNonEmpty(PatternStyle ps) {
        assertThat(ps.getValue()).isNotNull().isNotEmpty();
    }

    // ---- PictureFillMode ----

    @Test
    void pictureFillModeHasTwoConstants() {
        assertThat(PictureFillMode.values()).hasSize(2);
    }

    @Test
    void pictureFillModeTileAndStretchExist() {
        assertThat(PictureFillMode.TILE.getValue()).isEqualTo("Tile");
        assertThat(PictureFillMode.STRETCH.getValue()).isEqualTo("Stretch");
    }

    @ParameterizedTest
    @EnumSource(PictureFillMode.class)
    void allPictureFillModeValuesAreNonNullAndNonEmpty(PictureFillMode pfm) {
        assertThat(pfm.getValue()).isNotNull().isNotEmpty();
    }
}
