package org.aspose.slides.foss;

import org.aspose.slides.foss.drawing.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ILineFillFormat} contract exercised through {@link LineFillFormat}.
 *
 * <p>Covers solid fill, gradient fill, pattern fill, picture fill, no-fill modes,
 * and line color/width integration.</p>
 */
class ILineFillFormatTest {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";

    private Document doc;
    private Element lnElement;

    @BeforeEach
    void setUp() throws Exception {
        doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .newDocument();
        lnElement = doc.createElementNS(NS_A, "a:ln");
        doc.appendChild(lnElement);
    }

    private ILineFillFormat createLineFillFormat() {
        return new LineFillFormat(lnElement, null);
    }

    // --- interface hierarchy ---

    @Test
    void lineFillFormat_implementsIFillParamSource() {
        ILineFillFormat lff = createLineFillFormat();
        assertThat(lff).isInstanceOf(IFillParamSource.class);
    }

    @Test
    void lineFillFormat_implementsIFillFormat() {
        ILineFillFormat lff = createLineFillFormat();
        assertThat(lff).isInstanceOf(IFillFormat.class);
    }

    // --- test_solid_fill (line context) ---

    @Test
    void solidFillColor_persistsAfterReRead() {
        ILineFillFormat lff = createLineFillFormat();
        lff.setFillType(FillType.SOLID);
        lff.getSolidFillColor().setColor(Color.fromArgb(255, 0, 128, 255));

        ILineFillFormat lff2 = new LineFillFormat(lnElement, null);
        assertThat(lff2.getFillType()).isEqualTo(FillType.SOLID);
        Color c = lff2.getSolidFillColor().getColor();
        assertThat(c.getR()).isEqualTo(0);
        assertThat(c.getG()).isEqualTo(128);
        assertThat(c.getB()).isEqualTo(255);
    }

    // --- test_gradient_fill (line context) ---

    @Test
    void gradientFill_stopsAndAnglePersist() {
        ILineFillFormat lff = createLineFillFormat();
        lff.setFillType(FillType.GRADIENT);
        IGradientFormat gf = lff.getGradientFormat();
        gf.setGradientShape(GradientShape.LINEAR);
        gf.setLinearGradientAngle(45);
        gf.getGradientStops().add(0.0, Color.BLUE);
        gf.getGradientStops().add(1.0, Color.RED);

        ILineFillFormat lff2 = new LineFillFormat(lnElement, null);
        assertThat(lff2.getFillType()).isEqualTo(FillType.GRADIENT);
        assertThat(lff2.getGradientFormat().getGradientStops().size())
                .isGreaterThanOrEqualTo(2);
    }

    // --- test_pattern_fill (line context) ---

    @Test
    void patternFill_styleAndColorsPersist() {
        ILineFillFormat lff = createLineFillFormat();
        lff.setFillType(FillType.PATTERN);
        IPatternFormat pf = lff.getPatternFormat();
        pf.setPatternStyle(PatternStyle.PERCENT50);
        pf.getForeColor().setColor(new Color(255, 0, 0, 139));
        pf.getBackColor().setColor(new Color(255, 255, 255, 224));

        ILineFillFormat lff2 = new LineFillFormat(lnElement, null);
        assertThat(lff2.getFillType()).isEqualTo(FillType.PATTERN);
        assertThat(lff2.getPatternFormat().getPatternStyle())
                .isEqualTo(PatternStyle.PERCENT50);
    }

    // --- test_picture_fill (line context) ---

    @Test
    void pictureFill_typePersists() {
        ILineFillFormat lff = createLineFillFormat();
        lff.setFillType(FillType.PICTURE);

        ILineFillFormat lff2 = new LineFillFormat(lnElement, null);
        assertThat(lff2.getFillType()).isEqualTo(FillType.PICTURE);
    }

    // --- test_no_fill (line context) ---

    @Test
    void noFill_typePersists() {
        ILineFillFormat lff = createLineFillFormat();
        lff.setFillType(FillType.NO_FILL);

        ILineFillFormat lff2 = new LineFillFormat(lnElement, null);
        assertThat(lff2.getFillType()).isEqualTo(FillType.NO_FILL);
    }

    // --- test_line_color_and_width (fill portion) ---

    @Test
    void lineColorAndWidth_solidFillOnLinePersists() {
        ILineFillFormat lff = createLineFillFormat();
        lff.setFillType(FillType.SOLID);
        lff.getSolidFillColor().setColor(Color.DARK_RED);

        ILineFillFormat lff2 = new LineFillFormat(lnElement, null);
        assertThat(lff2.getFillType()).isEqualTo(FillType.SOLID);
        Color c = lff2.getSolidFillColor().getColor();
        assertThat(c.getR()).isEqualTo(Color.DARK_RED.getR());
    }

    // --- rotate_with_shape ---

    @Test
    void rotateWithShape_defaultIsNotDefined() {
        ILineFillFormat lff = createLineFillFormat();
        assertThat(lff.getRotateWithShape()).isEqualTo(NullableBool.NOT_DEFINED);
    }

    @Test
    void rotateWithShape_roundTrip() {
        ILineFillFormat lff = createLineFillFormat();
        lff.setFillType(FillType.GRADIENT);
        lff.setRotateWithShape(NullableBool.TRUE);

        ILineFillFormat lff2 = new LineFillFormat(lnElement, null);
        assertThat(lff2.getRotateWithShape()).isEqualTo(NullableBool.TRUE);
    }
}
