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
 * Tests for {@link InnerShadow}: blur radius, direction, distance, shadow color,
 * interface contracts, and save callback.
 */
class InnerShadowTest {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";

    private Document doc;
    private Element shadowElement;

    @BeforeEach
    void setUp() throws Exception {
        doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .getDOMImplementation()
                .createDocument(NS_A, "a:innerShdw", null);
        shadowElement = doc.getDocumentElement();
    }

    // --- blur radius ---

    @Test
    void blurRadius_defaultsToZeroWhenNotSet() {
        var shadow = new InnerShadow(shadowElement, null);
        assertThat(shadow.getBlurRadius()).isEqualTo(0.0);
    }

    @Test
    void blurRadius_setAndGet() {
        var shadow = new InnerShadow(shadowElement, null);
        shadow.setBlurRadius(10.0);
        assertThat(shadow.getBlurRadius()).isCloseTo(10.0, offset(0.01));
    }

    @Test
    void blurRadius_roundTrip() {
        var shadow = new InnerShadow(shadowElement, null);
        shadow.setBlurRadius(12.5);

        var shadow2 = new InnerShadow(shadowElement, null);
        assertThat(shadow2.getBlurRadius()).isCloseTo(12.5, offset(0.01));
    }

    @Test
    void blurRadius_setsRawEmuAttribute() {
        var shadow = new InnerShadow(shadowElement, null);
        shadow.setBlurRadius(1.0);
        // 1 point = 12700 EMU
        assertThat(shadowElement.getAttribute("blurRad")).isEqualTo("12700");
    }

    // --- direction ---

    @Test
    void direction_defaultsToZeroWhenNotSet() {
        var shadow = new InnerShadow(shadowElement, null);
        assertThat(shadow.getDirection()).isEqualTo(0.0);
    }

    @Test
    void direction_setAndGet() {
        var shadow = new InnerShadow(shadowElement, null);
        shadow.setDirection(315.0);
        assertThat(shadow.getDirection()).isCloseTo(315.0, offset(0.01));
    }

    @Test
    void direction_roundTrip() {
        var shadow = new InnerShadow(shadowElement, null);
        shadow.setDirection(270.0);

        var shadow2 = new InnerShadow(shadowElement, null);
        assertThat(shadow2.getDirection()).isCloseTo(270.0, offset(0.01));
    }

    @Test
    void direction_setsRawAngleAttribute() {
        var shadow = new InnerShadow(shadowElement, null);
        shadow.setDirection(1.0);
        // 1 degree = 60000 angle units
        assertThat(shadowElement.getAttribute("dir")).isEqualTo("60000");
    }

    // --- distance ---

    @Test
    void distance_defaultsToZeroWhenNotSet() {
        var shadow = new InnerShadow(shadowElement, null);
        assertThat(shadow.getDistance()).isEqualTo(0.0);
    }

    @Test
    void distance_setAndGet() {
        var shadow = new InnerShadow(shadowElement, null);
        shadow.setDistance(8.0);
        assertThat(shadow.getDistance()).isCloseTo(8.0, offset(0.01));
    }

    @Test
    void distance_roundTrip() {
        var shadow = new InnerShadow(shadowElement, null);
        shadow.setDistance(5.5);

        var shadow2 = new InnerShadow(shadowElement, null);
        assertThat(shadow2.getDistance()).isCloseTo(5.5, offset(0.01));
    }

    @Test
    void distance_setsRawEmuAttribute() {
        var shadow = new InnerShadow(shadowElement, null);
        shadow.setDistance(1.0);
        assertThat(shadowElement.getAttribute("dist")).isEqualTo("12700");
    }

    // --- shadow color ---

    @Test
    void shadowColor_isNotNull() {
        var shadow = new InnerShadow(shadowElement, null);
        assertThat(shadow.getShadowColor()).isNotNull();
    }

    // --- save callback ---

    @Test
    void setBlurRadius_invokesSaveCallback() {
        AtomicInteger callCount = new AtomicInteger(0);
        var shadow = new InnerShadow(shadowElement, callCount::incrementAndGet);
        shadow.setBlurRadius(5.0);
        assertThat(callCount.get()).isEqualTo(1);
    }

    @Test
    void setDirection_invokesSaveCallback() {
        AtomicInteger callCount = new AtomicInteger(0);
        var shadow = new InnerShadow(shadowElement, callCount::incrementAndGet);
        shadow.setDirection(90.0);
        assertThat(callCount.get()).isEqualTo(1);
    }

    @Test
    void setDistance_invokesSaveCallback() {
        AtomicInteger callCount = new AtomicInteger(0);
        var shadow = new InnerShadow(shadowElement, callCount::incrementAndGet);
        shadow.setDistance(4.0);
        assertThat(callCount.get()).isEqualTo(1);
    }

    @Test
    void noCallbackDoesNotThrow() {
        var shadow = new InnerShadow(shadowElement, null);
        shadow.setBlurRadius(5.0);
        shadow.setDirection(180.0);
        shadow.setDistance(3.0);
        // No exception expected
    }

    // --- interface contracts ---

    @Test
    void asIPresentationComponent_returnsSelf() {
        var shadow = new InnerShadow(shadowElement, null);
        IPresentationComponent result = shadow.asIPresentationComponent();
        assertThat(result).isSameAs(shadow);
    }

    @Test
    void asIImageTransformOperation_returnsSelf() {
        var shadow = new InnerShadow(shadowElement, null);
        IImageTransformOperation result = shadow.asIImageTransformOperation();
        assertThat(result).isSameAs(shadow);
    }

    @Test
    void slide_nullWhenNoParentSlide() {
        var shadow = new InnerShadow(shadowElement, null);
        assertThat(shadow.getSlide()).isNull();
    }

    @Test
    void presentation_nullWhenNoParentSlide() {
        var shadow = new InnerShadow(shadowElement, null);
        assertThat(shadow.getPresentation()).isNull();
    }
}
