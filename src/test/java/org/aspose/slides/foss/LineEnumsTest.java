package org.aspose.slides.foss;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for line-related enums used in line format operations.
 * Ports the behavioral intent of test_line_format.py — verifying that
 * line style, cap, join, arrowhead, and alignment enum constants exist
 * with correct values.
 */
class LineEnumsTest {

    @Test
    void lineStyleHasAllConstants() {
        assertThat(LineStyle.values()).hasSize(6);
        assertThat(LineStyle.SINGLE.getValue()).isEqualTo("Single");
        assertThat(LineStyle.THICK_BETWEEN_THIN.getValue()).isEqualTo("ThickBetweenThin");
    }

    @Test
    void lineCapStyleHasAllConstants() {
        assertThat(LineCapStyle.values()).hasSize(4);
        assertThat(LineCapStyle.ROUND.getValue()).isEqualTo("Round");
        assertThat(LineCapStyle.FLAT.getValue()).isEqualTo("Flat");
    }

    @Test
    void lineJoinStyleHasAllConstants() {
        assertThat(LineJoinStyle.values()).hasSize(4);
        assertThat(LineJoinStyle.MITER.getValue()).isEqualTo("Miter");
        assertThat(LineJoinStyle.BEVEL.getValue()).isEqualTo("Bevel");
    }

    @Test
    void lineAlignmentHasAllConstants() {
        assertThat(LineAlignment.values()).hasSize(3);
        assertThat(LineAlignment.CENTER.getValue()).isEqualTo("Center");
        assertThat(LineAlignment.INSET.getValue()).isEqualTo("Inset");
    }

    @Test
    void lineArrowheadStyleHasAllConstants() {
        assertThat(LineArrowheadStyle.values()).hasSize(7);
        assertThat(LineArrowheadStyle.TRIANGLE.getValue()).isEqualTo("Triangle");
        assertThat(LineArrowheadStyle.OPEN.getValue()).isEqualTo("Open");
    }

    @Test
    void lineArrowheadLengthHasAllConstants() {
        assertThat(LineArrowheadLength.values()).hasSize(4);
        assertThat(LineArrowheadLength.SHORT.getValue()).isEqualTo("Short");
        assertThat(LineArrowheadLength.LONG.getValue()).isEqualTo("Long");
    }

    @Test
    void lineArrowheadWidthHasAllConstants() {
        assertThat(LineArrowheadWidth.values()).hasSize(4);
        assertThat(LineArrowheadWidth.NARROW.getValue()).isEqualTo("Narrow");
        assertThat(LineArrowheadWidth.WIDE.getValue()).isEqualTo("Wide");
    }

    @ParameterizedTest
    @EnumSource(LineStyle.class)
    void allLineStylesHaveNonNullValues(LineStyle style) {
        assertThat(style.getValue()).isNotNull().isNotEmpty();
    }

    @ParameterizedTest
    @EnumSource(LineArrowheadStyle.class)
    void allArrowheadStylesHaveNonNullValues(LineArrowheadStyle style) {
        assertThat(style.getValue()).isNotNull().isNotEmpty();
    }
}
