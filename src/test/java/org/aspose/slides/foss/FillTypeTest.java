package org.aspose.slides.foss;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for fill-related enums used in fill format operations.
 * Ports the behavioral intent of test_fill_format.py — verifying that the enum
 * constants referenced in solid, gradient, pattern, picture, and no-fill
 * scenarios exist and carry the correct string values.
 */
class FillTypeTest {

    @Test
    void fillTypeHasAllExpectedConstants() {
        assertThat(FillType.values()).hasSize(7);
    }

    @Test
    void solidFillTypeValue() {
        assertThat(FillType.SOLID.getValue()).isEqualTo("Solid");
    }

    @Test
    void gradientFillTypeValue() {
        assertThat(FillType.GRADIENT.getValue()).isEqualTo("Gradient");
    }

    @Test
    void patternFillTypeValue() {
        assertThat(FillType.PATTERN.getValue()).isEqualTo("Pattern");
    }

    @Test
    void pictureFillTypeValue() {
        assertThat(FillType.PICTURE.getValue()).isEqualTo("Picture");
    }

    @Test
    void noFillTypeValue() {
        assertThat(FillType.NO_FILL.getValue()).isEqualTo("NoFill");
    }

    @Test
    void groupFillTypeValue() {
        assertThat(FillType.GROUP.getValue()).isEqualTo("Group");
    }

    @Test
    void notDefinedFillTypeValue() {
        assertThat(FillType.NOT_DEFINED.getValue()).isEqualTo("NotDefined");
    }

    @ParameterizedTest
    @EnumSource(FillType.class)
    void allFillTypesHaveNonNullValues(FillType fillType) {
        assertThat(fillType.getValue()).isNotNull().isNotEmpty();
    }

    @Test
    void gradientShapeLinearValue() {
        assertThat(GradientShape.LINEAR.getValue()).isEqualTo("Linear");
    }

    @Test
    void gradientShapeHasAllConstants() {
        assertThat(GradientShape.values()).hasSize(5);
    }

    @Test
    void patternStylePercent50Value() {
        assertThat(PatternStyle.PERCENT50.getValue()).isEqualTo("Percent50");
    }

    @Test
    void patternStyleHasAllConstants() {
        assertThat(PatternStyle.values()).hasSize(56);
    }

    @Test
    void pictureFillModeValues() {
        assertThat(PictureFillMode.TILE.getValue()).isEqualTo("Tile");
        assertThat(PictureFillMode.STRETCH.getValue()).isEqualTo("Stretch");
        assertThat(PictureFillMode.values()).hasSize(2);
    }

    @Test
    void fillBlendModeHasAllConstants() {
        assertThat(FillBlendMode.values()).hasSize(5);
        assertThat(FillBlendMode.DARKEN.getValue()).isEqualTo("Darken");
        assertThat(FillBlendMode.SCREEN.getValue()).isEqualTo("Screen");
    }
}
