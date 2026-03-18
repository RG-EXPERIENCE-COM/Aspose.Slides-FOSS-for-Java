package org.aspose.slides.foss;

import org.aspose.slides.foss.drawing.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link FillFormat}: solid, gradient, pattern, picture, no-fill.
 *
 * <p>Covers solid fill, gradient fill, pattern fill, picture fill, and no-fill modes.</p>
 */
class FillFormatTest {

    private static final String NS_P = "http://schemas.openxmlformats.org/presentationml/2006/main";
    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";

    private Document doc;

    @BeforeEach
    void setUp() throws Exception {
        doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .newDocument();
    }

    /**
     * Creates an {@code <p:spPr>} element to use as the parent for FillFormat.
     */
    private Element createSpPr() {
        Element spPr = doc.createElementNS(NS_P, "p:spPr");
        doc.appendChild(spPr);
        return spPr;
    }

    // --- test_solid_fill ---

    @Test
    void solidFillColor_persistsAfterSetAndReRead() {
        Element spPr = createSpPr();
        var ff = new FillFormat(spPr, null);

        ff.setFillType(FillType.SOLID);
        ff.getSolidFillColor().setColor(Color.fromArgb(255, 0, 128, 255));

        // Re-read from same XML (simulates save/reload)
        var ff2 = new FillFormat(spPr, null);
        assertThat(ff2.getFillType()).isEqualTo(FillType.SOLID);
        Color c = ff2.getSolidFillColor().getColor();
        assertThat(c.getR()).isEqualTo(0);
        assertThat(c.getG()).isEqualTo(128);
        assertThat(c.getB()).isEqualTo(255);
    }

    @Test
    void solidFill_setTypeDoesNotClobberExistingColor() {
        Element spPr = createSpPr();
        var ff = new FillFormat(spPr, null);

        ff.setFillType(FillType.SOLID);
        ff.getSolidFillColor().setColor(new Color(255, 255, 0, 0));

        // Setting the same fill type again should preserve the color
        ff.setFillType(FillType.SOLID);
        Color c = ff.getSolidFillColor().getColor();
        assertThat(c.getR()).isEqualTo(255);
        assertThat(c.getG()).isEqualTo(0);
        assertThat(c.getB()).isEqualTo(0);
    }

    // --- test_gradient_fill ---

    @Test
    void gradientFill_stopsAndAnglePersist() {
        Element spPr = createSpPr();
        var ff = new FillFormat(spPr, null);

        ff.setFillType(FillType.GRADIENT);
        IGradientFormat gf = ff.getGradientFormat();
        gf.setGradientShape(GradientShape.LINEAR);
        gf.setLinearGradientAngle(45);
        gf.getGradientStops().add(0.0, Color.BLUE);
        gf.getGradientStops().add(1.0, Color.RED);

        // Re-read from same XML
        var ff2 = new FillFormat(spPr, null);
        assertThat(ff2.getFillType()).isEqualTo(FillType.GRADIENT);
        assertThat(ff2.getGradientFormat().getGradientStops().size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    void gradientFill_linearAngle() {
        Element spPr = createSpPr();
        var ff = new FillFormat(spPr, null);
        ff.setFillType(FillType.GRADIENT);
        IGradientFormat gf = ff.getGradientFormat();
        gf.setLinearGradientAngle(90);

        var ff2 = new FillFormat(spPr, null);
        assertThat(ff2.getGradientFormat().getLinearGradientAngle()).isCloseTo(90, org.assertj.core.data.Offset.offset(0.01));
    }

    @Test
    void gradientFill_shapeIsLinear() {
        Element spPr = createSpPr();
        var ff = new FillFormat(spPr, null);
        ff.setFillType(FillType.GRADIENT);
        IGradientFormat gf = ff.getGradientFormat();
        gf.setGradientShape(GradientShape.LINEAR);

        var ff2 = new FillFormat(spPr, null);
        assertThat(ff2.getGradientFormat().getGradientShape()).isEqualTo(GradientShape.LINEAR);
    }

    // --- test_pattern_fill ---

    @Test
    void patternFill_styleAndColorsPersist() {
        Element spPr = createSpPr();
        var ff = new FillFormat(spPr, null);

        ff.setFillType(FillType.PATTERN);
        IPatternFormat pf = ff.getPatternFormat();
        pf.setPatternStyle(PatternStyle.PERCENT50);
        pf.getForeColor().setColor(new Color(255, 0, 0, 139)); // dark blue
        pf.getBackColor().setColor(new Color(255, 255, 255, 224)); // light yellow

        // Re-read from same XML
        var ff2 = new FillFormat(spPr, null);
        assertThat(ff2.getFillType()).isEqualTo(FillType.PATTERN);
        assertThat(ff2.getPatternFormat().getPatternStyle()).isEqualTo(PatternStyle.PERCENT50);
    }

    @Test
    void patternFill_foreColorPersists() {
        Element spPr = createSpPr();
        var ff = new FillFormat(spPr, null);
        ff.setFillType(FillType.PATTERN);
        ff.getPatternFormat().getForeColor().setColor(new Color(255, 128, 64, 32));

        var ff2 = new FillFormat(spPr, null);
        Color c = ff2.getPatternFormat().getForeColor().getColor();
        assertThat(c.getR()).isEqualTo(128);
        assertThat(c.getG()).isEqualTo(64);
        assertThat(c.getB()).isEqualTo(32);
    }

    // --- test_no_fill ---

    @Test
    void noFill_typePersists() {
        Element spPr = createSpPr();
        var ff = new FillFormat(spPr, null);

        ff.setFillType(FillType.NO_FILL);

        var ff2 = new FillFormat(spPr, null);
        assertThat(ff2.getFillType()).isEqualTo(FillType.NO_FILL);
    }

    @Test
    void noFill_replacesExistingSolid() {
        Element spPr = createSpPr();
        var ff = new FillFormat(spPr, null);

        ff.setFillType(FillType.SOLID);
        assertThat(ff.getFillType()).isEqualTo(FillType.SOLID);

        ff.setFillType(FillType.NO_FILL);
        assertThat(ff.getFillType()).isEqualTo(FillType.NO_FILL);
    }

    // --- test_picture_fill ---

    @Test
    void pictureFill_typePersists() {
        Element spPr = createSpPr();
        var ff = new FillFormat(spPr, null);

        ff.setFillType(FillType.PICTURE);

        var ff2 = new FillFormat(spPr, null);
        assertThat(ff2.getFillType()).isEqualTo(FillType.PICTURE);
    }

    @Test
    void pictureFill_hasStretchByDefault() {
        Element spPr = createSpPr();
        var ff = new FillFormat(spPr, null);
        ff.setFillType(FillType.PICTURE);

        IPictureFillFormat pff = ff.getPictureFillFormat();
        assertThat(pff.getPictureFillMode()).isEqualTo(PictureFillMode.STRETCH);
    }

    // --- additional coverage ---

    @Test
    void fillType_notDefinedWhenEmpty() {
        Element spPr = createSpPr();
        var ff = new FillFormat(spPr, null);
        assertThat(ff.getFillType()).isEqualTo(FillType.NOT_DEFINED);
    }

    @Test
    void gradientFill_addAndReadStops() {
        Element spPr = createSpPr();
        var ff = new FillFormat(spPr, null);
        ff.setFillType(FillType.GRADIENT);
        IGradientFormat gf = ff.getGradientFormat();

        gf.getGradientStops().add(0.0, Color.BLUE);
        gf.getGradientStops().add(0.5, Color.GREEN);
        gf.getGradientStops().add(1.0, Color.RED);

        assertThat(gf.getGradientStops().size()).isEqualTo(3);
        assertThat(gf.getGradientStops().get(0).getPosition()).isCloseTo(0.0, org.assertj.core.data.Offset.offset(0.001));
        assertThat(gf.getGradientStops().get(1).getPosition()).isCloseTo(0.5, org.assertj.core.data.Offset.offset(0.001));
        assertThat(gf.getGradientStops().get(2).getPosition()).isCloseTo(1.0, org.assertj.core.data.Offset.offset(0.001));
    }

    @Test
    void gradientFill_clearStops() {
        Element spPr = createSpPr();
        var ff = new FillFormat(spPr, null);
        ff.setFillType(FillType.GRADIENT);
        IGradientFormat gf = ff.getGradientFormat();

        gf.getGradientStops().add(0.0, Color.BLUE);
        gf.getGradientStops().add(1.0, Color.RED);
        assertThat(gf.getGradientStops().size()).isEqualTo(2);

        gf.getGradientStops().clear();
        assertThat(gf.getGradientStops().size()).isEqualTo(0);
    }

    @Test
    void saveCallback_invokedOnFillTypeChange() {
        Element spPr = createSpPr();
        int[] callCount = {0};
        var ff = new FillFormat(spPr, () -> callCount[0]++);

        ff.setFillType(FillType.SOLID);
        assertThat(callCount[0]).isGreaterThan(0);
    }

    // --- rotate_with_shape ---

    @Test
    void rotateWithShape_notDefinedWhenNoFill() {
        Element spPr = createSpPr();
        var ff = new FillFormat(spPr, null);
        assertThat(ff.getRotateWithShape()).isEqualTo(NullableBool.NOT_DEFINED);
    }

    @Test
    void rotateWithShape_notDefinedWhenAttributeAbsent() {
        Element spPr = createSpPr();
        var ff = new FillFormat(spPr, null);
        ff.setFillType(FillType.GRADIENT);
        assertThat(ff.getRotateWithShape()).isEqualTo(NullableBool.NOT_DEFINED);
    }

    @Test
    void rotateWithShape_setTrueAndRead() {
        Element spPr = createSpPr();
        var ff = new FillFormat(spPr, null);
        ff.setFillType(FillType.GRADIENT);
        ff.setRotateWithShape(NullableBool.TRUE);

        var ff2 = new FillFormat(spPr, null);
        assertThat(ff2.getRotateWithShape()).isEqualTo(NullableBool.TRUE);
    }

    @Test
    void rotateWithShape_setFalseAndRead() {
        Element spPr = createSpPr();
        var ff = new FillFormat(spPr, null);
        ff.setFillType(FillType.SOLID);
        ff.setRotateWithShape(NullableBool.FALSE);

        var ff2 = new FillFormat(spPr, null);
        assertThat(ff2.getRotateWithShape()).isEqualTo(NullableBool.FALSE);
    }

    @Test
    void rotateWithShape_setNotDefinedRemovesAttribute() {
        Element spPr = createSpPr();
        var ff = new FillFormat(spPr, null);
        ff.setFillType(FillType.GRADIENT);
        ff.setRotateWithShape(NullableBool.TRUE);
        ff.setRotateWithShape(NullableBool.NOT_DEFINED);

        assertThat(ff.getRotateWithShape()).isEqualTo(NullableBool.NOT_DEFINED);
    }

    @Test
    void rotateWithShape_noOpWhenNoFillElement() {
        Element spPr = createSpPr();
        var ff = new FillFormat(spPr, null);
        // Should not throw when no fill element exists
        ff.setRotateWithShape(NullableBool.TRUE);
        assertThat(ff.getRotateWithShape()).isEqualTo(NullableBool.NOT_DEFINED);
    }

    @Test
    void rotateWithShape_saveCallbackInvoked() {
        Element spPr = createSpPr();
        int[] callCount = {0};
        var ff = new FillFormat(spPr, () -> callCount[0]++);
        ff.setFillType(FillType.SOLID);
        int before = callCount[0];
        ff.setRotateWithShape(NullableBool.TRUE);
        assertThat(callCount[0]).isGreaterThan(before);
    }
}
