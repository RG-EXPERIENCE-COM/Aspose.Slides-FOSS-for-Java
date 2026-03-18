package org.aspose.slides.foss;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for text-related enums — alignment, anchor, autofit, cap,
 * strikethrough, underline, vertical, and shape types.
 */
class TextEnumsTest {

    @Test
    void textAlignmentHasAllConstants() {
        assertThat(TextAlignment.values()).hasSize(7);
        assertThat(TextAlignment.LEFT.getValue()).isEqualTo("Left");
        assertThat(TextAlignment.JUSTIFY.getValue()).isEqualTo("Justify");
    }

    @Test
    void textAnchorTypeHasAllConstants() {
        assertThat(TextAnchorType.values()).hasSize(6);
        assertThat(TextAnchorType.TOP.getValue()).isEqualTo("Top");
        assertThat(TextAnchorType.DISTRIBUTED.getValue()).isEqualTo("Distributed");
    }

    @Test
    void textAutofitTypeHasAllConstants() {
        assertThat(TextAutofitType.values()).hasSize(4);
        assertThat(TextAutofitType.NORMAL.getValue()).isEqualTo("Normal");
        assertThat(TextAutofitType.SHAPE.getValue()).isEqualTo("Shape");
    }

    @Test
    void textCapTypeHasAllConstants() {
        assertThat(TextCapType.values()).hasSize(4);
        assertThat(TextCapType.SMALL.getValue()).isEqualTo("Small");
        assertThat(TextCapType.ALL.getValue()).isEqualTo("All");
    }

    @Test
    void textStrikethroughTypeHasAllConstants() {
        assertThat(TextStrikethroughType.values()).hasSize(4);
        assertThat(TextStrikethroughType.SINGLE.getValue()).isEqualTo("Single");
        assertThat(TextStrikethroughType.DOUBLE.getValue()).isEqualTo("Double");
    }

    @Test
    void textUnderlineTypeHasAllConstants() {
        assertThat(TextUnderlineType.values()).hasSize(19);
        assertThat(TextUnderlineType.WAVY.getValue()).isEqualTo("Wavy");
        assertThat(TextUnderlineType.DOUBLE_WAVY.getValue()).isEqualTo("DoubleWavy");
    }

    @Test
    void textVerticalTypeHasAllConstants() {
        assertThat(TextVerticalType.values()).hasSize(8);
        assertThat(TextVerticalType.HORIZONTAL.getValue()).isEqualTo("Horizontal");
        assertThat(TextVerticalType.MONGOLIAN_VERTICAL.getValue()).isEqualTo("MongolianVertical");
    }

    @Test
    void textShapeTypeHasAllConstants() {
        assertThat(TextShapeType.values()).hasSize(43);
        assertThat(TextShapeType.PLAIN.getValue()).isEqualTo("Plain");
        assertThat(TextShapeType.CASCADE_DOWN.getValue()).isEqualTo("CascadeDown");
    }
}
