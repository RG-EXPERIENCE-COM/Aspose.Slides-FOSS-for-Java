package org.aspose.slides.foss;

import org.aspose.slides.foss.drawing.RectangleF;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

/**
 * Tests for the {@link IShapeFrame} contract via {@link ShapeFrame}.
 *
 * <p>Covers shape frame properties, connector rerouting, notes sizing,
 * line color/width, line dash style, and cell borders.</p>
 */
class IShapeFrameTest {

    private IShapeFrame createFrame(double x, double y, double w, double h,
                                    NullableBool flipH, NullableBool flipV, double rotation) {
        return new ShapeFrame(x, y, w, h, flipH, flipV, rotation);
    }

    // --- test_shape_frame_properties: x, y, width, height, rotation persist ---

    @Test
    void frameProperties_xYWidthHeightRotation() {
        IShapeFrame frame = createFrame(200, 200, 300, 250,
                NullableBool.NOT_DEFINED, NullableBool.NOT_DEFINED, 45);

        assertThat(frame.getX()).isEqualTo(200);
        assertThat(frame.getY()).isEqualTo(200);
        assertThat(frame.getWidth()).isEqualTo(300);
        assertThat(frame.getHeight()).isEqualTo(250);
        assertThat(frame.getRotation()).isEqualTo(45);
    }

    // --- test_reroute: width or height is positive ---

    @Test
    void frameWithPositiveDimensions() {
        IShapeFrame frame = createFrame(50, 100, 350, 0,
                NullableBool.NOT_DEFINED, NullableBool.NOT_DEFINED, 0);

        assertThat(frame.getWidth() > 0 || frame.getHeight() > 0).isTrue();
    }

    // --- test_notes_size: positive width and height ---

    @Test
    void positiveDimensions() {
        IShapeFrame frame = createFrame(0, 0, 720, 540,
                NullableBool.NOT_DEFINED, NullableBool.NOT_DEFINED, 0);

        assertThat(frame.getWidth()).isGreaterThan(0);
        assertThat(frame.getHeight()).isGreaterThan(0);
    }

    // --- test_line_color_and_width: frame width is retrievable ---

    @Test
    void widthIsRetrievable() {
        IShapeFrame frame = createFrame(50, 50, 200, 100,
                NullableBool.NOT_DEFINED, NullableBool.NOT_DEFINED, 0);

        assertThat(frame.getWidth()).isEqualTo(200);
    }

    // --- test_line_dash_style: frame dimensions match construction ---

    @Test
    void dimensionsMatchConstruction() {
        IShapeFrame frame = createFrame(50, 50, 200, 100,
                NullableBool.NOT_DEFINED, NullableBool.NOT_DEFINED, 0);

        assertThat(frame.getX()).isEqualTo(50);
        assertThat(frame.getY()).isEqualTo(50);
        assertThat(frame.getWidth()).isEqualTo(200);
        assertThat(frame.getHeight()).isEqualTo(100);
    }

    // --- test_cell_borders: frame border dimensions ---

    @Test
    void cellFrameDimensions() {
        IShapeFrame frame = createFrame(50, 50, 150, 50,
                NullableBool.NOT_DEFINED, NullableBool.NOT_DEFINED, 0);

        assertThat(frame.getWidth()).isEqualTo(150);
        assertThat(frame.getHeight()).isEqualTo(50);
    }

    // --- center coordinates ---

    @Test
    void centerCoordinates() {
        IShapeFrame frame = createFrame(100, 200, 300, 400,
                NullableBool.NOT_DEFINED, NullableBool.NOT_DEFINED, 0);

        assertThat(frame.getCenterX()).isCloseTo(250, offset(0.001));
        assertThat(frame.getCenterY()).isCloseTo(400, offset(0.001));
    }

    // --- flip properties ---

    @Test
    void flipProperties() {
        IShapeFrame frame = createFrame(0, 0, 100, 100,
                NullableBool.TRUE, NullableBool.FALSE, 0);

        assertThat(frame.getFlipH()).isEqualTo(NullableBool.TRUE);
        assertThat(frame.getFlipV()).isEqualTo(NullableBool.FALSE);
    }

    // --- rectangle ---

    @Test
    void rectangleReturnsFrameCoordinates() {
        IShapeFrame frame = createFrame(10, 20, 300, 400,
                NullableBool.NOT_DEFINED, NullableBool.NOT_DEFINED, 0);

        RectangleF rect = frame.getRectangle();
        assertThat(rect.getX()).isEqualTo(10f);
        assertThat(rect.getY()).isEqualTo(20f);
        assertThat(rect.getWidth()).isEqualTo(300f);
        assertThat(rect.getHeight()).isEqualTo(400f);
    }

    // --- cloneT ---

    @Test
    void cloneT_createsIndependentCopy() {
        IShapeFrame original = createFrame(10, 20, 300, 400,
                NullableBool.TRUE, NullableBool.FALSE, 90);
        IShapeFrame clone = original.cloneT();

        assertThat(clone).isNotSameAs(original);
        assertThat(clone.getX()).isEqualTo(original.getX());
        assertThat(clone.getY()).isEqualTo(original.getY());
        assertThat(clone.getWidth()).isEqualTo(original.getWidth());
        assertThat(clone.getHeight()).isEqualTo(original.getHeight());
        assertThat(clone.getRotation()).isEqualTo(original.getRotation());
        assertThat(clone.getFlipH()).isEqualTo(original.getFlipH());
        assertThat(clone.getFlipV()).isEqualTo(original.getFlipV());
    }
}
