package org.aspose.slides.foss.drawing;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for PointF: construction, coordinate access, mutability, equality.
 */
class PointFTest {

    @Test
    void constructor_setsCoordinates() {
        var point = new PointF(2.0f, 3.0f);

        assertThat(point.getX()).isEqualTo(2.0f);
        assertThat(point.getY()).isEqualTo(3.0f);
    }

    @Test
    void constructor_defaults_areZero() {
        var point = new PointF();

        assertThat(point.getX()).isEqualTo(0f);
        assertThat(point.getY()).isEqualTo(0f);
    }

    @Test
    void constructor_variousPositions() {
        var p1 = new PointF(1, 1);
        var p2 = new PointF(2, 2);

        assertThat(p1.getX()).isEqualTo(1f);
        assertThat(p1.getY()).isEqualTo(1f);
        assertThat(p2.getX()).isEqualTo(2f);
        assertThat(p2.getY()).isEqualTo(2f);
    }

    @Test
    void coordinates_areMutable() {
        var point = new PointF(1.0f, 1.0f);

        point.setX(5.0f);
        point.setY(7.0f);

        assertThat(point.getX()).isEqualTo(5.0f);
        assertThat(point.getY()).isEqualTo(7.0f);
    }

    @Test
    void equals_sameCoordinates_returnsTrue() {
        var a = new PointF(2.0f, 3.0f);
        var b = new PointF(2.0f, 3.0f);

        assertThat(a).isEqualTo(b);
    }

    @Test
    void equals_differentCoordinates_returnsFalse() {
        var a = new PointF(1.0f, 1.0f);
        var b = new PointF(2.0f, 2.0f);

        assertThat(a).isNotEqualTo(b);
    }

    @Test
    void getHashCode_equalPoints_sameHash() {
        var a = new PointF(3.5f, 7.5f);
        var b = new PointF(3.5f, 7.5f);

        assertThat(a.hashCode()).isEqualTo(b.hashCode());
    }

    @ParameterizedTest
    @CsvSource({
            "1.0, 1.0",
            "2.0, 3.0",
            "0.0, 0.0",
            "100.5, 200.5"
    })
    void constructor_roundTrips_allValues(float x, float y) {
        var point = new PointF(x, y);

        assertThat(point.getX()).isEqualTo(x);
        assertThat(point.getY()).isEqualTo(y);
    }

    @Test
    void empty_hasZeroCoordinates() {
        assertThat(PointF.EMPTY.getX()).isEqualTo(0f);
        assertThat(PointF.EMPTY.getY()).isEqualTo(0f);
    }

    @Test
    void isEmpty_zeroCoordinates_returnsTrue() {
        assertThat(new PointF(0f, 0f).isEmpty()).isTrue();
        assertThat(PointF.EMPTY.isEmpty()).isTrue();
    }

    @Test
    void isEmpty_nonZeroCoordinates_returnsFalse() {
        assertThat(new PointF(1.0f, 0f).isEmpty()).isFalse();
        assertThat(new PointF(0f, 1.0f).isEmpty()).isFalse();
    }
}
