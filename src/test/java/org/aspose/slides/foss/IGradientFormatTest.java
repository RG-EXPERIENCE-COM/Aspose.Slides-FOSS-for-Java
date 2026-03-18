package org.aspose.slides.foss;

import org.aspose.slides.foss.drawing.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

/**
 * Tests for {@link IGradientFormat} / {@link GradientFormat}.
 *
 * <p>Verifies gradient stop and angle persistence, gradient shape setting,
 * and the {@link IFillParamSource} contract.</p>
 */
class IGradientFormatTest {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";

    private Document doc;
    private Element gradFill;
    private int saveCount;

    @BeforeEach
    void setUp() throws Exception {
        doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .newDocument();
        gradFill = doc.createElementNS(NS_A, "a:gradFill");
        doc.appendChild(gradFill);
        saveCount = 0;
    }

    private GradientFormat createFormat() {
        return new GradientFormat(gradFill, () -> saveCount++);
    }

    // --- gradient shape, angle, and stops persist ---

    @Test
    void gradientShape_setLinear_persistsAsLinear() {
        var gf = createFormat();

        gf.setGradientShape(GradientShape.LINEAR);

        assertThat(gf.getGradientShape()).isEqualTo(GradientShape.LINEAR);
    }

    @Test
    void linearGradientAngle_set45_persistsAs45() {
        var gf = createFormat();
        gf.setGradientShape(GradientShape.LINEAR);

        gf.setLinearGradientAngle(45);

        assertThat(gf.getLinearGradientAngle()).isCloseTo(45.0, offset(0.01));
    }

    @Test
    void gradientStops_addTwoStops_sizeIsAtLeastTwo() {
        var gf = createFormat();
        gf.setGradientShape(GradientShape.LINEAR);
        gf.setLinearGradientAngle(45);

        gf.getGradientStops().add(0.0, Color.BLUE);
        gf.getGradientStops().add(1.0, Color.RED);

        assertThat(gf.getGradientStops().size()).isGreaterThanOrEqualTo(2);
    }

    // --- Interface contract: IGradientFormat extends IFillParamSource ---

    @Test
    void implementsIFillParamSource() {
        var gf = createFormat();

        assertThat(gf).isInstanceOf(IFillParamSource.class);
        assertThat(gf).isInstanceOf(IGradientFormat.class);
    }

    // --- Additional property tests ---

    @Test
    void tileFlip_defaultIsNotDefined() {
        var gf = createFormat();

        assertThat(gf.getTileFlip()).isEqualTo(TileFlip.NOT_DEFINED);
    }

    @Test
    void tileFlip_setAndGet() {
        var gf = createFormat();

        gf.setTileFlip(TileFlip.FLIP_X);

        assertThat(gf.getTileFlip()).isEqualTo(TileFlip.FLIP_X);
    }

    @Test
    void gradientDirection_defaultIsNotDefined() {
        var gf = createFormat();

        assertThat(gf.getGradientDirection()).isEqualTo(GradientDirection.NOT_DEFINED);
    }

    @Test
    void gradientDirection_setFromCenter() {
        var gf = createFormat();

        gf.setGradientDirection(GradientDirection.FROM_CENTER);

        assertThat(gf.getGradientDirection()).isEqualTo(GradientDirection.FROM_CENTER);
    }

    @Test
    void linearGradientScaled_defaultIsNotDefined() {
        var gf = createFormat();

        assertThat(gf.getLinearGradientScaled()).isEqualTo(NullableBool.NOT_DEFINED);
    }

    @Test
    void linearGradientScaled_setTrue() {
        var gf = createFormat();
        gf.setGradientShape(GradientShape.LINEAR);

        gf.setLinearGradientScaled(NullableBool.TRUE);

        assertThat(gf.getLinearGradientScaled()).isEqualTo(NullableBool.TRUE);
    }

    @Test
    void gradientShape_defaultIsNotDefined() {
        var gf = createFormat();

        assertThat(gf.getGradientShape()).isEqualTo(GradientShape.NOT_DEFINED);
    }

    @Test
    void gradientStops_returnsNonNull() {
        var gf = createFormat();

        assertThat(gf.getGradientStops()).isNotNull();
    }
}
