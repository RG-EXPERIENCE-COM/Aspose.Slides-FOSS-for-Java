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
 * Tests for {@link SoftEdge}: radius, interface contracts, and save callback.
 */
class SoftEdgeTest {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";

    private Document doc;
    private Element softEdgeElement;

    @BeforeEach
    void setUp() throws Exception {
        doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .getDOMImplementation()
                .createDocument(NS_A, "a:softEdge", null);
        softEdgeElement = doc.getDocumentElement();
    }

    // --- radius ---

    @Test
    void radius_defaultsToZeroWhenNotSet() {
        var softEdge = new SoftEdge(softEdgeElement, null);
        assertThat(softEdge.getRadius()).isEqualTo(0.0);
    }

    @Test
    void radius_setAndGet() {
        var softEdge = new SoftEdge(softEdgeElement, null);
        softEdge.setRadius(10.0);
        assertThat(softEdge.getRadius()).isCloseTo(10.0, offset(0.01));
    }

    @Test
    void radius_roundTrip() {
        var softEdge = new SoftEdge(softEdgeElement, null);
        softEdge.setRadius(12.5);

        // Re-read from the same element (simulates save/reload)
        var softEdge2 = new SoftEdge(softEdgeElement, null);
        assertThat(softEdge2.getRadius()).isCloseTo(12.5, offset(0.01));
    }

    @Test
    void radius_setsRawEmuAttribute() {
        var softEdge = new SoftEdge(softEdgeElement, null);
        softEdge.setRadius(1.0);
        // 1 point = 12700 EMU
        assertThat(softEdgeElement.getAttribute("rad")).isEqualTo("12700");
    }

    // --- save callback ---

    @Test
    void setRadius_invokesSaveCallback() {
        var callCount = new AtomicInteger(0);
        var softEdge = new SoftEdge(softEdgeElement, callCount::incrementAndGet);
        softEdge.setRadius(5.0);
        assertThat(callCount.get()).isEqualTo(1);
    }

    @Test
    void noCallbackDoesNotThrow() {
        var softEdge = new SoftEdge(softEdgeElement, null);
        softEdge.setRadius(5.0);
        // No exception expected
    }

    // --- interface contracts ---

    @Test
    void asIImageTransformOperation_returnsSelf() {
        var softEdge = new SoftEdge(softEdgeElement, null);
        IImageTransformOperation result = softEdge.asIImageTransformOperation();
        assertThat(result).isSameAs(softEdge);
    }

    @Test
    void implementsISoftEdge() {
        var softEdge = new SoftEdge(softEdgeElement, null);
        assertThat(softEdge).isInstanceOf(ISoftEdge.class);
    }

    @Test
    void implementsIImageTransformOperation() {
        var softEdge = new SoftEdge(softEdgeElement, null);
        assertThat(softEdge).isInstanceOf(IImageTransformOperation.class);
    }
}
