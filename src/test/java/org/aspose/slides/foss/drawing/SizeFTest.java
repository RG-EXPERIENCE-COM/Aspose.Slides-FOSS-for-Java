package org.aspose.slides.foss.drawing;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for SizeF: construction, dimension access, mutability, equality.
 */
class SizeFTest {

    @Test
    void constructor_setsDimensions() {
        var size = new SizeF(300.5f, 250.5f);

        assertThat(size.getWidth()).isEqualTo(300.5f);
        assertThat(size.getHeight()).isEqualTo(250.5f);
    }

    @Test
    void constructor_defaults_areZero() {
        var size = new SizeF();

        assertThat(size.getWidth()).isEqualTo(0f);
        assertThat(size.getHeight()).isEqualTo(0f);
    }

    @Test
    void dimensions_areMutable() {
        var size = new SizeF(1.0f, 2.0f);

        size.setWidth(10.5f);
        size.setHeight(20.5f);

        assertThat(size.getWidth()).isEqualTo(10.5f);
        assertThat(size.getHeight()).isEqualTo(20.5f);
    }

    @Test
    void equals_sameDimensions_returnsTrue() {
        var a = new SizeF(100.5f, 200.5f);
        var b = new SizeF(100.5f, 200.5f);

        assertThat(a).isEqualTo(b);
    }

    @Test
    void equals_differentDimensions_returnsFalse() {
        var a = new SizeF(100.5f, 200.5f);
        var b = new SizeF(100.5f, 200.6f);

        assertThat(a).isNotEqualTo(b);
    }

    @Test
    void getHashCode_equalSizes_sameHash() {
        var a = new SizeF(10.5f, 20.5f);
        var b = new SizeF(10.5f, 20.5f);

        assertThat(a.hashCode()).isEqualTo(b.hashCode());
    }

    @Test
    void empty_hasZeroDimensions() {
        assertThat(SizeF.EMPTY.getWidth()).isEqualTo(0f);
        assertThat(SizeF.EMPTY.getHeight()).isEqualTo(0f);
    }

    @Test
    void isEmpty_zeroDimensions_returnsTrue() {
        assertThat(new SizeF(0f, 0f).isEmpty()).isTrue();
        assertThat(SizeF.EMPTY.isEmpty()).isTrue();
    }

    @Test
    void isEmpty_nonZeroDimensions_returnsFalse() {
        assertThat(new SizeF(1.0f, 0f).isEmpty()).isFalse();
        assertThat(new SizeF(0f, 1.0f).isEmpty()).isFalse();
    }

    @Test
    void add_returnsSumOfDimensions() {
        var a = new SizeF(10.5f, 20.5f);
        var b = new SizeF(30.5f, 40.5f);

        var result = SizeF.add(a, b);

        assertThat(result.getWidth()).isEqualTo(41.0f);
        assertThat(result.getHeight()).isEqualTo(61.0f);
    }

    @Test
    void toSize_truncatesDimensions() {
        var sizeF = new SizeF(10.9f, 20.7f);

        var size = sizeF.toSize();

        assertThat(size.getWidth()).isEqualTo(10);
        assertThat(size.getHeight()).isEqualTo(20);
    }
}
