package org.aspose.slides.foss.effects;

import org.aspose.slides.foss.IPresentationComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

/**
 * Tests for {@link Blur}: radius, grow, interface contracts, and save callback.
 */
class BlurTest {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";

    private Document doc;
    private Element blurElement;

    @BeforeEach
    void setUp() throws Exception {
        doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .getDOMImplementation()
                .createDocument(NS_A, "a:blur", null);
        blurElement = doc.getDocumentElement();
    }

    // --- radius ---

    @Test
    void radius_defaultsToZeroWhenNotSet() {
        var blur = new Blur(blurElement, null);
        assertThat(blur.getRadius()).isEqualTo(0.0);
    }

    @Test
    void radius_setAndGet() {
        var blur = new Blur(blurElement, null);
        blur.setRadius(8.0);
        assertThat(blur.getRadius()).isCloseTo(8.0, offset(0.01));
    }

    @Test
    void radius_roundTrip() {
        var blur = new Blur(blurElement, null);
        blur.setRadius(12.5);

        // Re-read from the same element (simulates save/reload)
        var blur2 = new Blur(blurElement, null);
        assertThat(blur2.getRadius()).isCloseTo(12.5, offset(0.01));
    }

    @Test
    void radius_setsRawEmuAttribute() {
        var blur = new Blur(blurElement, null);
        blur.setRadius(1.0);
        // 1 point = 12700 EMU
        assertThat(blurElement.getAttribute("rad")).isEqualTo("12700");
    }

    // --- grow ---

    @Test
    void grow_defaultsToTrueWhenNotSet() {
        var blur = new Blur(blurElement, null);
        assertThat(blur.isGrow()).isTrue();
    }

    @Test
    void grow_setTrueAndGet() {
        var blur = new Blur(blurElement, null);
        blur.setGrow(true);
        assertThat(blur.isGrow()).isTrue();
        assertThat(blurElement.getAttribute("grow")).isEqualTo("1");
    }

    @Test
    void grow_setFalseAndGet() {
        var blur = new Blur(blurElement, null);
        blur.setGrow(false);
        assertThat(blur.isGrow()).isFalse();
        assertThat(blurElement.getAttribute("grow")).isEqualTo("0");
    }

    @Test
    void grow_roundTrip() {
        var blur = new Blur(blurElement, null);
        blur.setGrow(false);

        var blur2 = new Blur(blurElement, null);
        assertThat(blur2.isGrow()).isFalse();
    }

    // --- save callback ---

    @Test
    void setRadius_invokesSaveCallback() {
        AtomicInteger callCount = new AtomicInteger(0);
        var blur = new Blur(blurElement, callCount::incrementAndGet);
        blur.setRadius(5.0);
        assertThat(callCount.get()).isEqualTo(1);
    }

    @Test
    void setGrow_invokesSaveCallback() {
        AtomicInteger callCount = new AtomicInteger(0);
        var blur = new Blur(blurElement, callCount::incrementAndGet);
        blur.setGrow(true);
        assertThat(callCount.get()).isEqualTo(1);
    }

    @Test
    void noCallbackDoesNotThrow() {
        var blur = new Blur(blurElement, null);
        blur.setRadius(5.0);
        blur.setGrow(false);
        // No exception expected
    }

    // --- interface contracts ---

    @Test
    void asIPresentationComponent_returnsSelf() {
        var blur = new Blur(blurElement, null);
        IPresentationComponent result = blur.asIPresentationComponent();
        assertThat(result).isSameAs(blur);
    }

    @Test
    void asIImageTransformOperation_returnsSelf() {
        var blur = new Blur(blurElement, null);
        IImageTransformOperation result = blur.asIImageTransformOperation();
        assertThat(result).isSameAs(blur);
    }

    @Test
    void slide_nullWhenNoParentSlide() {
        var blur = new Blur(blurElement, null);
        assertThat(blur.getSlide()).isNull();
    }

    @Test
    void presentation_nullWhenNoParentSlide() {
        var blur = new Blur(blurElement, null);
        assertThat(blur.getPresentation()).isNull();
    }
}
