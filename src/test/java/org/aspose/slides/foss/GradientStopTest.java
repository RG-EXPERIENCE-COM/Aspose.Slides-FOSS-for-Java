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
 * Tests for {@link GradientStop}: position get/set, color access, save callback.
 *
 * <p>Verifies gradient stop position persistence (0..1 mapped to 0..100000 in OOXML),
 * color access via ColorFormat, and save callback invocation on mutation.</p>
 */
class GradientStopTest {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";

    private Document doc;
    private Element gsElement;
    private int saveCount;

    @BeforeEach
    void setUp() throws Exception {
        doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .newDocument();
        gsElement = doc.createElementNS(NS_A, "a:gs");
        doc.appendChild(gsElement);
        saveCount = 0;
    }

    private GradientStop createStop() {
        return new GradientStop(gsElement, () -> saveCount++);
    }

    // --- position ---

    @Test
    void getPosition_defaultsToZero() {
        var stop = createStop();

        assertThat(stop.getPosition()).isCloseTo(0.0, offset(0.001));
    }

    @Test
    void getPosition_readsFromPosAttribute() {
        gsElement.setAttribute("pos", "50000");
        var stop = createStop();

        assertThat(stop.getPosition()).isCloseTo(0.5, offset(0.001));
    }

    @Test
    void getPosition_fullRange() {
        gsElement.setAttribute("pos", "100000");
        var stop = createStop();

        assertThat(stop.getPosition()).isCloseTo(1.0, offset(0.001));
    }

    @Test
    void setPosition_writesScaledValue() {
        var stop = createStop();

        stop.setPosition(0.75);

        assertThat(gsElement.getAttribute("pos")).isEqualTo("75000");
    }

    @Test
    void setPosition_roundTrips() {
        var stop = createStop();

        stop.setPosition(0.33);

        assertThat(stop.getPosition()).isCloseTo(0.33, offset(0.001));
    }

    @Test
    void setPosition_zeroAndOne() {
        var stop = createStop();

        stop.setPosition(0.0);
        assertThat(gsElement.getAttribute("pos")).isEqualTo("0");

        stop.setPosition(1.0);
        assertThat(gsElement.getAttribute("pos")).isEqualTo("100000");
    }

    @Test
    void setPosition_invokesSaveCallback() {
        var stop = createStop();

        stop.setPosition(0.5);

        assertThat(saveCount).isEqualTo(1);
    }

    // --- color ---

    @Test
    void getColor_returnsColorFormat() {
        var stop = createStop();

        IColorFormat color = stop.getColor();

        assertThat(color).isNotNull();
    }

    @Test
    void getColor_canSetAndReadBack() {
        var stop = createStop();

        stop.getColor().setColor(Color.BLUE);

        Color c = stop.getColor().getColor();
        assertThat(c.getR()).isEqualTo(0);
        assertThat(c.getG()).isEqualTo(0);
        assertThat(c.getB()).isEqualTo(255);
    }

    @Test
    void getColor_setColorInvokesSaveCallback() {
        var stop = createStop();

        stop.getColor().setColor(Color.RED);

        assertThat(saveCount).isGreaterThan(0);
    }

    // --- save callback null safety ---

    @Test
    void setPosition_withNullCallback_doesNotThrow() {
        var stop = new GradientStop(gsElement, null);

        stop.setPosition(0.5);

        assertThat(stop.getPosition()).isCloseTo(0.5, offset(0.001));
    }
}
