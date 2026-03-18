package org.aspose.slides.foss.effects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

/**
 * Tests for {@link Glow}: radius, color, interface contracts, and save callback.
 */
class GlowTest {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";

    private Document doc;
    private Element glowElement;

    @BeforeEach
    void setUp() throws Exception {
        doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .getDOMImplementation()
                .createDocument(NS_A, "a:glow", null);
        glowElement = doc.getDocumentElement();
    }

    // --- radius ---

    @Test
    void radius_defaultsToZeroWhenNotSet() {
        var glow = new Glow(glowElement, null);
        assertThat(glow.getRadius()).isEqualTo(0.0);
    }

    @Test
    void radius_setAndGet() {
        var glow = new Glow(glowElement, null);
        glow.setRadius(15.0);
        assertThat(glow.getRadius()).isCloseTo(15.0, offset(0.01));
    }

    @Test
    void radius_roundTrip() {
        var glow = new Glow(glowElement, null);
        glow.setRadius(12.5);

        // Re-read from the same element (simulates save/reload)
        var glow2 = new Glow(glowElement, null);
        assertThat(glow2.getRadius()).isCloseTo(12.5, offset(0.01));
    }

    @Test
    void radius_setsRawEmuAttribute() {
        var glow = new Glow(glowElement, null);
        glow.setRadius(1.0);
        // 1 point = 12700 EMU
        assertThat(glowElement.getAttribute("rad")).isEqualTo("12700");
    }

    // --- color ---

    @Test
    void color_returnsNonNull() {
        var glow = new Glow(glowElement, null);
        assertThat(glow.getColor()).isNotNull();
    }

    // --- save callback ---

    @Test
    void setRadius_invokesSaveCallback() {
        var callCount = new AtomicInteger(0);
        var glow = new Glow(glowElement, callCount::incrementAndGet);
        glow.setRadius(5.0);
        assertThat(callCount.get()).isEqualTo(1);
    }

    @Test
    void noCallbackDoesNotThrow() {
        var glow = new Glow(glowElement, null);
        glow.setRadius(5.0);
        // No exception expected
    }

    // --- interface contracts ---

    @Test
    void asIImageTransformOperation_returnsSelf() {
        var glow = new Glow(glowElement, null);
        IImageTransformOperation result = glow.asIImageTransformOperation();
        assertThat(result).isSameAs(glow);
    }

    @Test
    void implementsIGlow() {
        var glow = new Glow(glowElement, null);
        assertThat(glow).isInstanceOf(IGlow.class);
    }

    @Test
    void implementsIImageTransformOperation() {
        var glow = new Glow(glowElement, null);
        assertThat(glow).isInstanceOf(IImageTransformOperation.class);
    }
}
