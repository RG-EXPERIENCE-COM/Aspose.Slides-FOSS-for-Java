package org.aspose.slides.foss;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for line-related enums.
 * Ports the behavioral intent of test_line_format.py by verifying that
 * the enum constants used in line cap, join, arrowhead, alignment,
 * and style scenarios exist and carry the correct string values.
 */
class LineFormatEnumsTest {

    // ---- LineCapStyle (4 constants) ----

    @Test
    void lineCapStyleHasFourConstants() {
        assertThat(LineCapStyle.values()).hasSize(4);
    }

    @Test
    void lineCapStyleGetValueReturnsCorrectStrings() {
        assertThat(LineCapStyle.NOT_DEFINED.getValue()).isEqualTo("NotDefined");
        assertThat(LineCapStyle.ROUND.getValue()).isEqualTo("Round");
        assertThat(LineCapStyle.SQUARE.getValue()).isEqualTo("Square");
        assertThat(LineCapStyle.FLAT.getValue()).isEqualTo("Flat");
    }

    @ParameterizedTest
    @EnumSource(LineCapStyle.class)
    void allLineCapStyleValuesAreNonNullAndNonEmpty(LineCapStyle lcs) {
        assertThat(lcs.getValue()).isNotNull().isNotEmpty();
    }

    // ---- LineJoinStyle (4 constants) ----

    @Test
    void lineJoinStyleHasFourConstants() {
        assertThat(LineJoinStyle.values()).hasSize(4);
    }

    @Test
    void lineJoinStyleGetValueReturnsCorrectStrings() {
        assertThat(LineJoinStyle.NOT_DEFINED.getValue()).isEqualTo("NotDefined");
        assertThat(LineJoinStyle.ROUND.getValue()).isEqualTo("Round");
        assertThat(LineJoinStyle.BEVEL.getValue()).isEqualTo("Bevel");
        assertThat(LineJoinStyle.MITER.getValue()).isEqualTo("Miter");
    }

    @ParameterizedTest
    @EnumSource(LineJoinStyle.class)
    void allLineJoinStyleValuesAreNonNullAndNonEmpty(LineJoinStyle ljs) {
        assertThat(ljs.getValue()).isNotNull().isNotEmpty();
    }

    // ---- LineArrowheadStyle (7 constants) ----

    @Test
    void lineArrowheadStyleHasSevenConstants() {
        assertThat(LineArrowheadStyle.values()).hasSize(7);
    }

    @Test
    void lineArrowheadStyleGetValueReturnsCorrectStrings() {
        assertThat(LineArrowheadStyle.NOT_DEFINED.getValue()).isEqualTo("NotDefined");
        assertThat(LineArrowheadStyle.NONE.getValue()).isEqualTo("None");
        assertThat(LineArrowheadStyle.TRIANGLE.getValue()).isEqualTo("Triangle");
        assertThat(LineArrowheadStyle.STEALTH.getValue()).isEqualTo("Stealth");
        assertThat(LineArrowheadStyle.DIAMOND.getValue()).isEqualTo("Diamond");
        assertThat(LineArrowheadStyle.OVAL.getValue()).isEqualTo("Oval");
        assertThat(LineArrowheadStyle.OPEN.getValue()).isEqualTo("Open");
    }

    @ParameterizedTest
    @EnumSource(LineArrowheadStyle.class)
    void allLineArrowheadStyleValuesAreNonNullAndNonEmpty(LineArrowheadStyle las) {
        assertThat(las.getValue()).isNotNull().isNotEmpty();
    }

    // ---- LineArrowheadLength (4 constants) ----

    @Test
    void lineArrowheadLengthHasFourConstants() {
        assertThat(LineArrowheadLength.values()).hasSize(4);
    }

    @Test
    void lineArrowheadLengthGetValueReturnsCorrectStrings() {
        assertThat(LineArrowheadLength.NOT_DEFINED.getValue()).isEqualTo("NotDefined");
        assertThat(LineArrowheadLength.SHORT.getValue()).isEqualTo("Short");
        assertThat(LineArrowheadLength.MEDIUM.getValue()).isEqualTo("Medium");
        assertThat(LineArrowheadLength.LONG.getValue()).isEqualTo("Long");
    }

    @ParameterizedTest
    @EnumSource(LineArrowheadLength.class)
    void allLineArrowheadLengthValuesAreNonNullAndNonEmpty(LineArrowheadLength lal) {
        assertThat(lal.getValue()).isNotNull().isNotEmpty();
    }

    // ---- LineArrowheadWidth (4 constants) ----

    @Test
    void lineArrowheadWidthHasFourConstants() {
        assertThat(LineArrowheadWidth.values()).hasSize(4);
    }

    @Test
    void lineArrowheadWidthGetValueReturnsCorrectStrings() {
        assertThat(LineArrowheadWidth.NOT_DEFINED.getValue()).isEqualTo("NotDefined");
        assertThat(LineArrowheadWidth.NARROW.getValue()).isEqualTo("Narrow");
        assertThat(LineArrowheadWidth.MEDIUM.getValue()).isEqualTo("Medium");
        assertThat(LineArrowheadWidth.WIDE.getValue()).isEqualTo("Wide");
    }

    @ParameterizedTest
    @EnumSource(LineArrowheadWidth.class)
    void allLineArrowheadWidthValuesAreNonNullAndNonEmpty(LineArrowheadWidth law) {
        assertThat(law.getValue()).isNotNull().isNotEmpty();
    }

    // ---- LineAlignment (3 constants) ----

    @Test
    void lineAlignmentHasThreeConstants() {
        assertThat(LineAlignment.values()).hasSize(3);
    }

    @Test
    void lineAlignmentGetValueReturnsCorrectStrings() {
        assertThat(LineAlignment.NOT_DEFINED.getValue()).isEqualTo("NotDefined");
        assertThat(LineAlignment.CENTER.getValue()).isEqualTo("Center");
        assertThat(LineAlignment.INSET.getValue()).isEqualTo("Inset");
    }

    @ParameterizedTest
    @EnumSource(LineAlignment.class)
    void allLineAlignmentValuesAreNonNullAndNonEmpty(LineAlignment la) {
        assertThat(la.getValue()).isNotNull().isNotEmpty();
    }

    // ---- LineStyle (6 constants) ----

    @Test
    void lineStyleHasSixConstants() {
        assertThat(LineStyle.values()).hasSize(6);
    }

    @Test
    void lineStyleGetValueReturnsCorrectStrings() {
        assertThat(LineStyle.NOT_DEFINED.getValue()).isEqualTo("NotDefined");
        assertThat(LineStyle.SINGLE.getValue()).isEqualTo("Single");
        assertThat(LineStyle.THIN_THIN.getValue()).isEqualTo("ThinThin");
        assertThat(LineStyle.THICK_THIN.getValue()).isEqualTo("ThickThin");
        assertThat(LineStyle.THIN_THICK.getValue()).isEqualTo("ThinThick");
        assertThat(LineStyle.THICK_BETWEEN_THIN.getValue()).isEqualTo("ThickBetweenThin");
    }

    @ParameterizedTest
    @EnumSource(LineStyle.class)
    void allLineStyleValuesAreNonNullAndNonEmpty(LineStyle ls) {
        assertThat(ls.getValue()).isNotNull().isNotEmpty();
    }
}
