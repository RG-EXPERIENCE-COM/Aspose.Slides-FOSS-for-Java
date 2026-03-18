package org.aspose.slides.foss.drawing;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for Size: construction, dimension access, mutability, equality.
 */
class SizeTest {

    @Test
    void constructor_setsDimensions() {
        var size = new Size(300, 250);

        assertThat(size.getWidth()).isEqualTo(300);
        assertThat(size.getHeight()).isEqualTo(250);
    }

    @Test
    void constructor_defaults_areZero() {
        var size = new Size();

        assertThat(size.getWidth()).isEqualTo(0);
        assertThat(size.getHeight()).isEqualTo(0);
    }

    @Test
    void dimensions_areMutable() {
        var size = new Size(100, 200);

        size.setWidth(400);
        size.setHeight(500);

        assertThat(size.getWidth()).isEqualTo(400);
        assertThat(size.getHeight()).isEqualTo(500);
    }

    @Test
    void positiveDimensions() {
        var size = new Size(720, 540);

        assertThat(size.getWidth()).isPositive();
        assertThat(size.getHeight()).isPositive();
    }

    @Test
    void equals_sameDimensions_returnsTrue() {
        var a = new Size(200, 100);
        var b = new Size(200, 100);

        assertThat(a).isEqualTo(b);
    }

    @Test
    void equals_differentDimensions_returnsFalse() {
        var a = new Size(200, 100);
        var b = new Size(200, 101);

        assertThat(a).isNotEqualTo(b);
    }

    @Test
    void getHashCode_equalSizes_sameHash() {
        var a = new Size(300, 250);
        var b = new Size(300, 250);

        assertThat(a.hashCode()).isEqualTo(b.hashCode());
    }

    @ParameterizedTest
    @CsvSource({
            "100, 60",
            "200, 100",
            "300, 250"
    })
    void constructor_roundTrips(int width, int height) {
        var size = new Size(width, height);

        assertThat(size.getWidth()).isEqualTo(width);
        assertThat(size.getHeight()).isEqualTo(height);
    }

    @Test
    void empty_hasZeroDimensions() {
        assertThat(Size.EMPTY.getWidth()).isEqualTo(0);
        assertThat(Size.EMPTY.getHeight()).isEqualTo(0);
    }

    @Test
    void isEmpty_zeroDimensions_returnsTrue() {
        assertThat(new Size(0, 0).isEmpty()).isTrue();
        assertThat(Size.EMPTY.isEmpty()).isTrue();
    }

    @Test
    void isEmpty_nonZeroDimensions_returnsFalse() {
        assertThat(new Size(1, 0).isEmpty()).isFalse();
        assertThat(new Size(0, 1).isEmpty()).isFalse();
    }

    @Test
    void add_returnsSumOfDimensions() {
        var a = new Size(10, 20);
        var b = new Size(30, 40);

        var result = Size.add(a, b);

        assertThat(result.getWidth()).isEqualTo(40);
        assertThat(result.getHeight()).isEqualTo(60);
    }

    @Test
    void subtract_returnsDifferenceOfDimensions() {
        var a = new Size(30, 40);
        var b = new Size(10, 15);

        var result = Size.subtract(a, b);

        assertThat(result.getWidth()).isEqualTo(20);
        assertThat(result.getHeight()).isEqualTo(25);
    }
}
