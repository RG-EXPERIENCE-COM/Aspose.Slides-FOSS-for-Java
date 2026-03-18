package org.aspose.slides.foss.effects;

import org.aspose.slides.foss.IPresentationComponent;
import org.aspose.slides.foss.RectangleAlignment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

/**
 * Tests for {@link OuterShadow}: all properties, interface contracts, and save callback.
 */
class OuterShadowTest {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";

    private Document doc;
    private Element shadowElement;

    @BeforeEach
    void setUp() throws Exception {
        doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .getDOMImplementation()
                .createDocument(NS_A, "a:outerShdw", null);
        shadowElement = doc.getDocumentElement();
    }

    // --- blur radius ---

    @Test
    void blurRadius_defaultsToZeroWhenNotSet() {
        var shadow = new OuterShadow(shadowElement, null);
        assertThat(shadow.getBlurRadius()).isEqualTo(0.0);
    }

    @Test
    void blurRadius_setAndGet() {
        var shadow = new OuterShadow(shadowElement, null);
        shadow.setBlurRadius(10.0);
        assertThat(shadow.getBlurRadius()).isCloseTo(10.0, offset(0.01));
    }

    @Test
    void blurRadius_roundTrip() {
        var shadow = new OuterShadow(shadowElement, null);
        shadow.setBlurRadius(12.5);

        var shadow2 = new OuterShadow(shadowElement, null);
        assertThat(shadow2.getBlurRadius()).isCloseTo(12.5, offset(0.01));
    }

    @Test
    void blurRadius_setsRawEmuAttribute() {
        var shadow = new OuterShadow(shadowElement, null);
        shadow.setBlurRadius(1.0);
        assertThat(shadowElement.getAttribute("blurRad")).isEqualTo("12700");
    }

    // --- direction ---

    @Test
    void direction_defaultsToZeroWhenNotSet() {
        var shadow = new OuterShadow(shadowElement, null);
        assertThat(shadow.getDirection()).isEqualTo(0.0);
    }

    @Test
    void direction_setAndGet() {
        var shadow = new OuterShadow(shadowElement, null);
        shadow.setDirection(315.0);
        assertThat(shadow.getDirection()).isCloseTo(315.0, offset(0.01));
    }

    @Test
    void direction_roundTrip() {
        var shadow = new OuterShadow(shadowElement, null);
        shadow.setDirection(270.0);

        var shadow2 = new OuterShadow(shadowElement, null);
        assertThat(shadow2.getDirection()).isCloseTo(270.0, offset(0.01));
    }

    @Test
    void direction_setsRawAngleAttribute() {
        var shadow = new OuterShadow(shadowElement, null);
        shadow.setDirection(1.0);
        assertThat(shadowElement.getAttribute("dir")).isEqualTo("60000");
    }

    // --- distance ---

    @Test
    void distance_defaultsToZeroWhenNotSet() {
        var shadow = new OuterShadow(shadowElement, null);
        assertThat(shadow.getDistance()).isEqualTo(0.0);
    }

    @Test
    void distance_setAndGet() {
        var shadow = new OuterShadow(shadowElement, null);
        shadow.setDistance(8.0);
        assertThat(shadow.getDistance()).isCloseTo(8.0, offset(0.01));
    }

    @Test
    void distance_roundTrip() {
        var shadow = new OuterShadow(shadowElement, null);
        shadow.setDistance(5.5);

        var shadow2 = new OuterShadow(shadowElement, null);
        assertThat(shadow2.getDistance()).isCloseTo(5.5, offset(0.01));
    }

    @Test
    void distance_setsRawEmuAttribute() {
        var shadow = new OuterShadow(shadowElement, null);
        shadow.setDistance(1.0);
        assertThat(shadowElement.getAttribute("dist")).isEqualTo("12700");
    }

    // --- shadow color ---

    @Test
    void shadowColor_isNotNull() {
        var shadow = new OuterShadow(shadowElement, null);
        assertThat(shadow.getShadowColor()).isNotNull();
    }

    // --- rectangle align ---

    @Test
    void rectangleAlign_defaultsToBottom() {
        var shadow = new OuterShadow(shadowElement, null);
        assertThat(shadow.getRectangleAlign()).isEqualTo(RectangleAlignment.BOTTOM);
    }

    @Test
    void rectangleAlign_setAndGet() {
        var shadow = new OuterShadow(shadowElement, null);
        shadow.setRectangleAlign(RectangleAlignment.CENTER);
        assertThat(shadow.getRectangleAlign()).isEqualTo(RectangleAlignment.CENTER);
    }

    @Test
    void rectangleAlign_setNotDefinedRemovesAttribute() {
        var shadow = new OuterShadow(shadowElement, null);
        shadow.setRectangleAlign(RectangleAlignment.TOP_LEFT);
        shadow.setRectangleAlign(RectangleAlignment.NOT_DEFINED);
        assertThat(shadowElement.getAttribute("algn")).isEmpty();
    }

    @Test
    void rectangleAlign_setsOoxmlAbbreviation() {
        var shadow = new OuterShadow(shadowElement, null);
        shadow.setRectangleAlign(RectangleAlignment.CENTER);
        assertThat(shadowElement.getAttribute("algn")).isEqualTo("ctr");

        shadow.setRectangleAlign(RectangleAlignment.TOP_LEFT);
        assertThat(shadowElement.getAttribute("algn")).isEqualTo("tl");

        shadow.setRectangleAlign(RectangleAlignment.BOTTOM_RIGHT);
        assertThat(shadowElement.getAttribute("algn")).isEqualTo("br");
    }

    @Test
    void rectangleAlign_readsOoxmlAbbreviation() {
        shadowElement.setAttribute("algn", "ctr");
        var shadow = new OuterShadow(shadowElement, null);
        assertThat(shadow.getRectangleAlign()).isEqualTo(RectangleAlignment.CENTER);
    }

    @Test
    void rectangleAlign_unknownValueReturnsNotDefined() {
        shadowElement.setAttribute("algn", "unknown");
        var shadow = new OuterShadow(shadowElement, null);
        assertThat(shadow.getRectangleAlign()).isEqualTo(RectangleAlignment.NOT_DEFINED);
    }

    // --- skew horizontal ---

    @Test
    void skewHorizontal_defaultsToZero() {
        var shadow = new OuterShadow(shadowElement, null);
        assertThat(shadow.getSkewHorizontal()).isEqualTo(0.0);
    }

    @Test
    void skewHorizontal_setAndGet() {
        var shadow = new OuterShadow(shadowElement, null);
        shadow.setSkewHorizontal(45.0);
        assertThat(shadow.getSkewHorizontal()).isCloseTo(45.0, offset(0.01));
    }

    @Test
    void skewHorizontal_setsRawAngleAttribute() {
        var shadow = new OuterShadow(shadowElement, null);
        shadow.setSkewHorizontal(1.0);
        assertThat(shadowElement.getAttribute("kx")).isEqualTo("60000");
    }

    // --- skew vertical ---

    @Test
    void skewVertical_defaultsToZero() {
        var shadow = new OuterShadow(shadowElement, null);
        assertThat(shadow.getSkewVertical()).isEqualTo(0.0);
    }

    @Test
    void skewVertical_setAndGet() {
        var shadow = new OuterShadow(shadowElement, null);
        shadow.setSkewVertical(30.0);
        assertThat(shadow.getSkewVertical()).isCloseTo(30.0, offset(0.01));
    }

    @Test
    void skewVertical_setsRawAngleAttribute() {
        var shadow = new OuterShadow(shadowElement, null);
        shadow.setSkewVertical(1.0);
        assertThat(shadowElement.getAttribute("ky")).isEqualTo("60000");
    }

    // --- rotate shadow with shape ---

    @Test
    void rotateShadowWithShape_defaultsToTrue() {
        var shadow = new OuterShadow(shadowElement, null);
        assertThat(shadow.getRotateShadowWithShape()).isTrue();
    }

    @Test
    void rotateShadowWithShape_setFalseAndGet() {
        var shadow = new OuterShadow(shadowElement, null);
        shadow.setRotateShadowWithShape(false);
        assertThat(shadow.getRotateShadowWithShape()).isFalse();
    }

    @Test
    void rotateShadowWithShape_setTrueAndGet() {
        var shadow = new OuterShadow(shadowElement, null);
        shadow.setRotateShadowWithShape(false);
        shadow.setRotateShadowWithShape(true);
        assertThat(shadow.getRotateShadowWithShape()).isTrue();
    }

    // --- scale horizontal ---

    @Test
    void scaleHorizontal_defaultsTo100() {
        var shadow = new OuterShadow(shadowElement, null);
        assertThat(shadow.getScaleHorizontal()).isEqualTo(100.0);
    }

    @Test
    void scaleHorizontal_setAndGet() {
        var shadow = new OuterShadow(shadowElement, null);
        shadow.setScaleHorizontal(50.0);
        assertThat(shadow.getScaleHorizontal()).isCloseTo(50.0, offset(0.01));
    }

    @Test
    void scaleHorizontal_negativeFlip() {
        var shadow = new OuterShadow(shadowElement, null);
        shadow.setScaleHorizontal(-100.0);
        assertThat(shadow.getScaleHorizontal()).isCloseTo(-100.0, offset(0.01));
    }

    @Test
    void scaleHorizontal_setsRawScaleAttribute() {
        var shadow = new OuterShadow(shadowElement, null);
        shadow.setScaleHorizontal(50.0);
        assertThat(shadowElement.getAttribute("sx")).isEqualTo("50000");
    }

    // --- scale vertical ---

    @Test
    void scaleVertical_defaultsTo100() {
        var shadow = new OuterShadow(shadowElement, null);
        assertThat(shadow.getScaleVertical()).isEqualTo(100.0);
    }

    @Test
    void scaleVertical_setAndGet() {
        var shadow = new OuterShadow(shadowElement, null);
        shadow.setScaleVertical(75.0);
        assertThat(shadow.getScaleVertical()).isCloseTo(75.0, offset(0.01));
    }

    @Test
    void scaleVertical_setsRawScaleAttribute() {
        var shadow = new OuterShadow(shadowElement, null);
        shadow.setScaleVertical(75.0);
        assertThat(shadowElement.getAttribute("sy")).isEqualTo("75000");
    }

    // --- save callback ---

    @Test
    void setBlurRadius_invokesSaveCallback() {
        AtomicInteger callCount = new AtomicInteger(0);
        var shadow = new OuterShadow(shadowElement, callCount::incrementAndGet);
        shadow.setBlurRadius(5.0);
        assertThat(callCount.get()).isEqualTo(1);
    }

    @Test
    void setDirection_invokesSaveCallback() {
        AtomicInteger callCount = new AtomicInteger(0);
        var shadow = new OuterShadow(shadowElement, callCount::incrementAndGet);
        shadow.setDirection(90.0);
        assertThat(callCount.get()).isEqualTo(1);
    }

    @Test
    void setDistance_invokesSaveCallback() {
        AtomicInteger callCount = new AtomicInteger(0);
        var shadow = new OuterShadow(shadowElement, callCount::incrementAndGet);
        shadow.setDistance(4.0);
        assertThat(callCount.get()).isEqualTo(1);
    }

    @Test
    void setRectangleAlign_invokesSaveCallback() {
        AtomicInteger callCount = new AtomicInteger(0);
        var shadow = new OuterShadow(shadowElement, callCount::incrementAndGet);
        shadow.setRectangleAlign(RectangleAlignment.CENTER);
        assertThat(callCount.get()).isEqualTo(1);
    }

    @Test
    void setSkewHorizontal_invokesSaveCallback() {
        AtomicInteger callCount = new AtomicInteger(0);
        var shadow = new OuterShadow(shadowElement, callCount::incrementAndGet);
        shadow.setSkewHorizontal(10.0);
        assertThat(callCount.get()).isEqualTo(1);
    }

    @Test
    void setRotateShadowWithShape_invokesSaveCallback() {
        AtomicInteger callCount = new AtomicInteger(0);
        var shadow = new OuterShadow(shadowElement, callCount::incrementAndGet);
        shadow.setRotateShadowWithShape(false);
        assertThat(callCount.get()).isEqualTo(1);
    }

    @Test
    void setScaleHorizontal_invokesSaveCallback() {
        AtomicInteger callCount = new AtomicInteger(0);
        var shadow = new OuterShadow(shadowElement, callCount::incrementAndGet);
        shadow.setScaleHorizontal(50.0);
        assertThat(callCount.get()).isEqualTo(1);
    }

    @Test
    void noCallbackDoesNotThrow() {
        var shadow = new OuterShadow(shadowElement, null);
        shadow.setBlurRadius(5.0);
        shadow.setDirection(180.0);
        shadow.setDistance(3.0);
        shadow.setRectangleAlign(RectangleAlignment.TOP);
        shadow.setSkewHorizontal(10.0);
        shadow.setSkewVertical(10.0);
        shadow.setRotateShadowWithShape(false);
        shadow.setScaleHorizontal(50.0);
        shadow.setScaleVertical(50.0);
    }

    // --- interface contracts ---

    @Test
    void asIPresentationComponent_returnsSelf() {
        var shadow = new OuterShadow(shadowElement, null);
        IPresentationComponent result = shadow.asIPresentationComponent();
        assertThat(result).isSameAs(shadow);
    }

    @Test
    void asIImageTransformOperation_returnsSelf() {
        var shadow = new OuterShadow(shadowElement, null);
        IImageTransformOperation result = shadow.asIImageTransformOperation();
        assertThat(result).isSameAs(shadow);
    }

    @Test
    void slide_nullWhenNoParentSlide() {
        var shadow = new OuterShadow(shadowElement, null);
        assertThat(shadow.getSlide()).isNull();
    }

    @Test
    void presentation_nullWhenNoParentSlide() {
        var shadow = new OuterShadow(shadowElement, null);
        assertThat(shadow.getPresentation()).isNull();
    }
}
