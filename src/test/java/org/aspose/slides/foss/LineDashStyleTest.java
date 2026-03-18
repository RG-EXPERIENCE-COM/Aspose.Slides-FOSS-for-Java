package org.aspose.slides.foss;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link LineDashStyle} enum.
 */
class LineDashStyleTest {

    @Test
    void hasThirteenConstants() {
        assertThat(LineDashStyle.values()).hasSize(13);
    }

    @Test
    void getValueReturnsCorrectStrings() {
        assertThat(LineDashStyle.NOT_DEFINED.getValue()).isEqualTo("NotDefined");
        assertThat(LineDashStyle.SOLID.getValue()).isEqualTo("Solid");
        assertThat(LineDashStyle.DOT.getValue()).isEqualTo("Dot");
        assertThat(LineDashStyle.DASH.getValue()).isEqualTo("Dash");
        assertThat(LineDashStyle.LARGE_DASH.getValue()).isEqualTo("LargeDash");
        assertThat(LineDashStyle.DASH_DOT.getValue()).isEqualTo("DashDot");
        assertThat(LineDashStyle.LARGE_DASH_DOT.getValue()).isEqualTo("LargeDashDot");
        assertThat(LineDashStyle.LARGE_DASH_DOT_DOT.getValue()).isEqualTo("LargeDashDotDot");
        assertThat(LineDashStyle.SYSTEM_DASH.getValue()).isEqualTo("SystemDash");
        assertThat(LineDashStyle.SYSTEM_DOT.getValue()).isEqualTo("SystemDot");
        assertThat(LineDashStyle.SYSTEM_DASH_DOT.getValue()).isEqualTo("SystemDashDot");
        assertThat(LineDashStyle.SYSTEM_DASH_DOT_DOT.getValue()).isEqualTo("SystemDashDotDot");
        assertThat(LineDashStyle.CUSTOM.getValue()).isEqualTo("Custom");
    }

    @ParameterizedTest
    @EnumSource(LineDashStyle.class)
    void allValuesAreNonNullAndNonEmpty(LineDashStyle lds) {
        assertThat(lds.getValue()).isNotNull().isNotEmpty();
    }
}
