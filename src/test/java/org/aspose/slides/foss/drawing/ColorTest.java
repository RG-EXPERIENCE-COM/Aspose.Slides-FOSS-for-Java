package org.aspose.slides.foss.drawing;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for Color: construction, fromArgb, component access, named constants, equality.
 */
class ColorTest {

    @Test
    void constructor_defaultAlpha_is255() {
        var color = new Color(0, 128, 255);

        assertThat(color.getA()).isEqualTo(255);
        assertThat(color.getR()).isEqualTo(0);
        assertThat(color.getG()).isEqualTo(128);
        assertThat(color.getB()).isEqualTo(255);
    }

    @Test
    void constructor_allDefaults_isBlackOpaque() {
        var color = new Color();

        assertThat(color.getA()).isEqualTo(255);
        assertThat(color.getR()).isEqualTo(0);
        assertThat(color.getG()).isEqualTo(0);
        assertThat(color.getB()).isEqualTo(0);
    }

    @Test
    void constructor_explicitAlpha_isPreserved() {
        var color = new Color(128, 0, 0, 0);

        assertThat(color.getA()).isEqualTo(128);
        assertThat(color.getR()).isEqualTo(0);
        assertThat(color.getG()).isEqualTo(0);
        assertThat(color.getB()).isEqualTo(0);
    }

    @Test
    void fromArgb_returnsCorrectComponents() {
        var color = Color.fromArgb(255, 0, 128, 255);

        assertThat(color.getA()).isEqualTo(255);
        assertThat(color.getR()).isEqualTo(0);
        assertThat(color.getG()).isEqualTo(128);
        assertThat(color.getB()).isEqualTo(255);
    }

    @Test
    void fromArgb_semiTransparent_preservesAlpha() {
        var color = Color.fromArgb(128, 0, 0, 0);

        assertThat(color.getA()).isEqualTo(128);
    }

    @Test
    void red_componentsMatchExpected() {
        assertThat(Color.RED.getR()).isEqualTo(255);
        assertThat(Color.RED.getG()).isEqualTo(0);
        assertThat(Color.RED.getB()).isEqualTo(0);
        assertThat(Color.RED.getA()).isEqualTo(255);
    }

    @Test
    void darkRed_componentsMatchExpected() {
        assertThat(Color.DARK_RED.getR()).isEqualTo(139);
        assertThat(Color.DARK_RED.getG()).isEqualTo(0);
        assertThat(Color.DARK_RED.getB()).isEqualTo(0);
    }

    @Test
    void blue_componentsMatchExpected() {
        assertThat(Color.BLUE.getR()).isEqualTo(0);
        assertThat(Color.BLUE.getG()).isEqualTo(0);
        assertThat(Color.BLUE.getB()).isEqualTo(255);
    }

    @Test
    void gold_componentsMatchExpected() {
        assertThat(Color.GOLD.getR()).isEqualTo(255);
        assertThat(Color.GOLD.getG()).isEqualTo(215);
        assertThat(Color.GOLD.getB()).isEqualTo(0);
    }

    @Test
    void darkBlue_componentsMatchExpected() {
        assertThat(Color.DARK_BLUE.getR()).isEqualTo(0);
        assertThat(Color.DARK_BLUE.getG()).isEqualTo(0);
        assertThat(Color.DARK_BLUE.getB()).isEqualTo(139);
    }

    @Test
    void lightYellow_componentsMatchExpected() {
        assertThat(Color.LIGHT_YELLOW.getR()).isEqualTo(255);
        assertThat(Color.LIGHT_YELLOW.getG()).isEqualTo(255);
        assertThat(Color.LIGHT_YELLOW.getB()).isEqualTo(224);
    }

    @Test
    void black_componentsMatchExpected() {
        assertThat(Color.BLACK.getR()).isEqualTo(0);
        assertThat(Color.BLACK.getG()).isEqualTo(0);
        assertThat(Color.BLACK.getB()).isEqualTo(0);
        assertThat(Color.BLACK.getA()).isEqualTo(255);
    }

    @Test
    void lightBlue_componentsMatchExpected() {
        assertThat(Color.LIGHT_BLUE.getR()).isEqualTo(173);
        assertThat(Color.LIGHT_BLUE.getG()).isEqualTo(216);
        assertThat(Color.LIGHT_BLUE.getB()).isEqualTo(230);
    }

    @Test
    void equals_sameComponents_returnsTrue() {
        var a = new Color(100, 200, 50);
        var b = new Color(100, 200, 50);

        assertThat(a).isEqualTo(b);
    }

    @Test
    void equals_differentComponents_returnsFalse() {
        var a = new Color(100, 200, 50);
        var b = new Color(100, 200, 51);

        assertThat(a).isNotEqualTo(b);
    }

    @Test
    void equals_differentAlpha_returnsFalse() {
        var a = Color.fromArgb(255, 0, 0, 0);
        var b = Color.fromArgb(128, 0, 0, 0);

        assertThat(a).isNotEqualTo(b);
    }

    @Test
    void getHashCode_equalColors_sameHash() {
        var a = Color.fromArgb(128, 10, 20, 30);
        var b = Color.fromArgb(128, 10, 20, 30);

        assertThat(a.hashCode()).isEqualTo(b.hashCode());
    }

    @Test
    void fromArgb_equalsConstructor() {
        var fromFactory = Color.fromArgb(128, 0, 0, 0);
        var fromCtor = new Color(128, 0, 0, 0);

        assertThat(fromFactory).isEqualTo(fromCtor);
    }

    @Test
    void transparent_hasZeroAlpha() {
        assertThat(Color.TRANSPARENT.getA()).isEqualTo(0);
    }

    @Test
    void empty_hasAllZeros() {
        assertThat(Color.EMPTY.getA()).isEqualTo(0);
        assertThat(Color.EMPTY.getR()).isEqualTo(0);
        assertThat(Color.EMPTY.getG()).isEqualTo(0);
        assertThat(Color.EMPTY.getB()).isEqualTo(0);
    }

    @ParameterizedTest
    @CsvSource({
            "255, 0, 128, 255",
            "128, 0, 0, 0",
            "0, 255, 255, 255"
    })
    void fromArgb_roundTrips_allComponents(int a, int r, int g, int b) {
        var color = Color.fromArgb(a, r, g, b);

        assertThat(color.getA()).isEqualTo(a);
        assertThat(color.getR()).isEqualTo(r);
        assertThat(color.getG()).isEqualTo(g);
        assertThat(color.getB()).isEqualTo(b);
    }

    @Test
    void fromArgb_threeArgs_defaultsAlphaTo255() {
        var color = Color.fromArgb(10, 20, 30);

        assertThat(color.getA()).isEqualTo(255);
        assertThat(color.getR()).isEqualTo(10);
        assertThat(color.getG()).isEqualTo(20);
        assertThat(color.getB()).isEqualTo(30);
    }

    @Test
    void fromArgb_packedArgb_extractsComponents() {
        int packed = (128 << 24) | (10 << 16) | (20 << 8) | 30;
        var color = Color.fromArgb(packed);

        assertThat(color.getA()).isEqualTo(128);
        assertThat(color.getR()).isEqualTo(10);
        assertThat(color.getG()).isEqualTo(20);
        assertThat(color.getB()).isEqualTo(30);
    }

    @Test
    void toArgb_packsComponents() {
        var color = new Color(128, 10, 20, 30);
        int expected = (128 << 24) | (10 << 16) | (20 << 8) | 30;

        assertThat(color.toArgb()).isEqualTo(expected);
    }

    @Test
    void isEmpty_allZeros_returnsTrue() {
        assertThat(Color.EMPTY.isEmpty()).isTrue();
        assertThat(new Color(0, 0, 0, 0).isEmpty()).isTrue();
    }

    @Test
    void isEmpty_nonZero_returnsFalse() {
        assertThat(Color.RED.isEmpty()).isFalse();
        assertThat(Color.BLACK.isEmpty()).isFalse();
    }
}
