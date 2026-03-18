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
 * Tests for {@link Reflection}: all properties, interface contracts, and save callback.
 */
class ReflectionTest {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";

    private Document doc;
    private Element reflectionElement;

    @BeforeEach
    void setUp() throws Exception {
        doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .getDOMImplementation()
                .createDocument(NS_A, "a:reflection", null);
        reflectionElement = doc.getDocumentElement();
    }

    // --- start pos alpha (stPos attribute, default 0.0, scale 1000) ---

    @Test
    void startPosAlpha_defaultsToZero() {
        var reflection = new Reflection(reflectionElement, null);
        assertThat(reflection.getStartPosAlpha()).isEqualTo(0.0);
    }

    @Test
    void startPosAlpha_setAndGet() {
        var reflection = new Reflection(reflectionElement, null);
        reflection.setStartPosAlpha(50.0);
        assertThat(reflection.getStartPosAlpha()).isCloseTo(50.0, offset(0.01));
    }

    @Test
    void startPosAlpha_roundTrip() {
        var reflection = new Reflection(reflectionElement, null);
        reflection.setStartPosAlpha(75.5);
        var reflection2 = new Reflection(reflectionElement, null);
        assertThat(reflection2.getStartPosAlpha()).isCloseTo(75.5, offset(0.01));
    }

    @Test
    void startPosAlpha_setsRawAttribute() {
        var reflection = new Reflection(reflectionElement, null);
        reflection.setStartPosAlpha(50.0);
        assertThat(reflectionElement.getAttribute("stPos")).isEqualTo("50000");
    }

    // --- end pos alpha (endPos attribute, default 100.0, scale 1000) ---

    @Test
    void endPosAlpha_defaultsTo100() {
        var reflection = new Reflection(reflectionElement, null);
        assertThat(reflection.getEndPosAlpha()).isEqualTo(100.0);
    }

    @Test
    void endPosAlpha_setAndGet() {
        var reflection = new Reflection(reflectionElement, null);
        reflection.setEndPosAlpha(100.0);
        assertThat(reflection.getEndPosAlpha()).isCloseTo(100.0, offset(0.01));
    }

    @Test
    void endPosAlpha_setsRawAttribute() {
        var reflection = new Reflection(reflectionElement, null);
        reflection.setEndPosAlpha(50.0);
        assertThat(reflectionElement.getAttribute("endPos")).isEqualTo("50000");
    }

    // --- fade direction (fadeDir attribute, default 90.0, scale 60000) ---

    @Test
    void fadeDirection_defaultsTo90() {
        var reflection = new Reflection(reflectionElement, null);
        assertThat(reflection.getFadeDirection()).isEqualTo(90.0);
    }

    @Test
    void fadeDirection_setAndGet() {
        var reflection = new Reflection(reflectionElement, null);
        reflection.setFadeDirection(90.0);
        assertThat(reflection.getFadeDirection()).isCloseTo(90.0, offset(0.01));
    }

    @Test
    void fadeDirection_setsRawAngleAttribute() {
        var reflection = new Reflection(reflectionElement, null);
        reflection.setFadeDirection(1.0);
        assertThat(reflectionElement.getAttribute("fadeDir")).isEqualTo("60000");
    }

    // --- start reflection opacity (stA attribute, default 100.0, scale 1000) ---

    @Test
    void startReflectionOpacity_defaultsTo100() {
        var reflection = new Reflection(reflectionElement, null);
        assertThat(reflection.getStartReflectionOpacity()).isEqualTo(100.0);
    }

    @Test
    void startReflectionOpacity_setAndGet() {
        var reflection = new Reflection(reflectionElement, null);
        reflection.setStartReflectionOpacity(50.0);
        assertThat(reflection.getStartReflectionOpacity()).isCloseTo(50.0, offset(0.01));
    }

    @Test
    void startReflectionOpacity_setsRawAttribute() {
        var reflection = new Reflection(reflectionElement, null);
        reflection.setStartReflectionOpacity(50.0);
        assertThat(reflectionElement.getAttribute("stA")).isEqualTo("50000");
    }

    // --- end reflection opacity (endA attribute, default 0.0, scale 1000) ---

    @Test
    void endReflectionOpacity_defaultsToZero() {
        var reflection = new Reflection(reflectionElement, null);
        assertThat(reflection.getEndReflectionOpacity()).isEqualTo(0.0);
    }

    @Test
    void endReflectionOpacity_setAndGet() {
        var reflection = new Reflection(reflectionElement, null);
        reflection.setEndReflectionOpacity(80.0);
        assertThat(reflection.getEndReflectionOpacity()).isCloseTo(80.0, offset(0.01));
    }

    @Test
    void endReflectionOpacity_setsRawAttribute() {
        var reflection = new Reflection(reflectionElement, null);
        reflection.setEndReflectionOpacity(80.0);
        assertThat(reflectionElement.getAttribute("endA")).isEqualTo("80000");
    }

    // --- blur radius (blurRad attribute, default 0.0, EMU scale) ---

    @Test
    void blurRadius_defaultsToZero() {
        var reflection = new Reflection(reflectionElement, null);
        assertThat(reflection.getBlurRadius()).isEqualTo(0.0);
    }

    @Test
    void blurRadius_setAndGet() {
        var reflection = new Reflection(reflectionElement, null);
        reflection.setBlurRadius(10.0);
        assertThat(reflection.getBlurRadius()).isCloseTo(10.0, offset(0.01));
    }

    @Test
    void blurRadius_roundTrip() {
        var reflection = new Reflection(reflectionElement, null);
        reflection.setBlurRadius(12.5);
        var reflection2 = new Reflection(reflectionElement, null);
        assertThat(reflection2.getBlurRadius()).isCloseTo(12.5, offset(0.01));
    }

    @Test
    void blurRadius_setsRawEmuAttribute() {
        var reflection = new Reflection(reflectionElement, null);
        reflection.setBlurRadius(1.0);
        assertThat(reflectionElement.getAttribute("blurRad")).isEqualTo("12700");
    }

    // --- direction (dir attribute, default 0.0, angle scale) ---

    @Test
    void direction_defaultsToZero() {
        var reflection = new Reflection(reflectionElement, null);
        assertThat(reflection.getDirection()).isEqualTo(0.0);
    }

    @Test
    void direction_setAndGet() {
        var reflection = new Reflection(reflectionElement, null);
        reflection.setDirection(315.0);
        assertThat(reflection.getDirection()).isCloseTo(315.0, offset(0.01));
    }

    @Test
    void direction_roundTrip() {
        var reflection = new Reflection(reflectionElement, null);
        reflection.setDirection(270.0);
        var reflection2 = new Reflection(reflectionElement, null);
        assertThat(reflection2.getDirection()).isCloseTo(270.0, offset(0.01));
    }

    @Test
    void direction_setsRawAngleAttribute() {
        var reflection = new Reflection(reflectionElement, null);
        reflection.setDirection(1.0);
        assertThat(reflectionElement.getAttribute("dir")).isEqualTo("60000");
    }

    // --- distance (dist attribute, default 0.0, EMU scale) ---

    @Test
    void distance_defaultsToZero() {
        var reflection = new Reflection(reflectionElement, null);
        assertThat(reflection.getDistance()).isEqualTo(0.0);
    }

    @Test
    void distance_setAndGet() {
        var reflection = new Reflection(reflectionElement, null);
        reflection.setDistance(8.0);
        assertThat(reflection.getDistance()).isCloseTo(8.0, offset(0.01));
    }

    @Test
    void distance_roundTrip() {
        var reflection = new Reflection(reflectionElement, null);
        reflection.setDistance(5.5);
        var reflection2 = new Reflection(reflectionElement, null);
        assertThat(reflection2.getDistance()).isCloseTo(5.5, offset(0.01));
    }

    @Test
    void distance_setsRawEmuAttribute() {
        var reflection = new Reflection(reflectionElement, null);
        reflection.setDistance(1.0);
        assertThat(reflectionElement.getAttribute("dist")).isEqualTo("12700");
    }

    // --- rectangle align (algn attribute, default BOTTOM, abbreviation map) ---

    @Test
    void rectangleAlign_defaultsToBottom() {
        var reflection = new Reflection(reflectionElement, null);
        assertThat(reflection.getRectangleAlign()).isEqualTo(RectangleAlignment.BOTTOM);
    }

    @Test
    void rectangleAlign_setAndGet() {
        var reflection = new Reflection(reflectionElement, null);
        reflection.setRectangleAlign(RectangleAlignment.CENTER);
        assertThat(reflection.getRectangleAlign()).isEqualTo(RectangleAlignment.CENTER);
    }

    @Test
    void rectangleAlign_setNotDefinedRemovesAttribute() {
        var reflection = new Reflection(reflectionElement, null);
        reflection.setRectangleAlign(RectangleAlignment.TOP_LEFT);
        reflection.setRectangleAlign(RectangleAlignment.NOT_DEFINED);
        assertThat(reflectionElement.getAttribute("algn")).isEmpty();
    }

    @Test
    void rectangleAlign_setsOoxmlAbbreviation() {
        var reflection = new Reflection(reflectionElement, null);
        reflection.setRectangleAlign(RectangleAlignment.TOP_LEFT);
        assertThat(reflectionElement.getAttribute("algn")).isEqualTo("tl");
        reflection.setRectangleAlign(RectangleAlignment.CENTER);
        assertThat(reflectionElement.getAttribute("algn")).isEqualTo("ctr");
        reflection.setRectangleAlign(RectangleAlignment.BOTTOM_RIGHT);
        assertThat(reflectionElement.getAttribute("algn")).isEqualTo("br");
    }

    @Test
    void rectangleAlign_unknownValueReturnsNotDefined() {
        reflectionElement.setAttribute("algn", "xyz");
        var reflection = new Reflection(reflectionElement, null);
        assertThat(reflection.getRectangleAlign()).isEqualTo(RectangleAlignment.NOT_DEFINED);
    }

    // --- skew horizontal (kx attribute, default 0.0, angle scale) ---

    @Test
    void skewHorizontal_defaultsToZero() {
        var reflection = new Reflection(reflectionElement, null);
        assertThat(reflection.getSkewHorizontal()).isEqualTo(0.0);
    }

    @Test
    void skewHorizontal_setAndGet() {
        var reflection = new Reflection(reflectionElement, null);
        reflection.setSkewHorizontal(45.0);
        assertThat(reflection.getSkewHorizontal()).isCloseTo(45.0, offset(0.01));
    }

    @Test
    void skewHorizontal_setsRawAttribute() {
        var reflection = new Reflection(reflectionElement, null);
        reflection.setSkewHorizontal(1.0);
        assertThat(reflectionElement.getAttribute("kx")).isEqualTo("60000");
    }

    // --- skew vertical (ky attribute, default 0.0, angle scale) ---

    @Test
    void skewVertical_defaultsToZero() {
        var reflection = new Reflection(reflectionElement, null);
        assertThat(reflection.getSkewVertical()).isEqualTo(0.0);
    }

    @Test
    void skewVertical_setAndGet() {
        var reflection = new Reflection(reflectionElement, null);
        reflection.setSkewVertical(30.0);
        assertThat(reflection.getSkewVertical()).isCloseTo(30.0, offset(0.01));
    }

    @Test
    void skewVertical_setsRawAttribute() {
        var reflection = new Reflection(reflectionElement, null);
        reflection.setSkewVertical(1.0);
        assertThat(reflectionElement.getAttribute("ky")).isEqualTo("60000");
    }

    // --- rotate shadow with shape (rotWithShape attribute, default true) ---

    @Test
    void rotateShadowWithShape_defaultsToTrue() {
        var reflection = new Reflection(reflectionElement, null);
        assertThat(reflection.getRotateShadowWithShape()).isTrue();
    }

    @Test
    void rotateShadowWithShape_setFalseAndGet() {
        var reflection = new Reflection(reflectionElement, null);
        reflection.setRotateShadowWithShape(false);
        assertThat(reflection.getRotateShadowWithShape()).isFalse();
    }

    @Test
    void rotateShadowWithShape_setTrueAndGet() {
        var reflection = new Reflection(reflectionElement, null);
        reflection.setRotateShadowWithShape(false);
        reflection.setRotateShadowWithShape(true);
        assertThat(reflection.getRotateShadowWithShape()).isTrue();
    }

    // --- scale horizontal (sx attribute, default 100.0, scale 1000) ---

    @Test
    void scaleHorizontal_defaultsTo100() {
        var reflection = new Reflection(reflectionElement, null);
        assertThat(reflection.getScaleHorizontal()).isEqualTo(100.0);
    }

    @Test
    void scaleHorizontal_setAndGet() {
        var reflection = new Reflection(reflectionElement, null);
        reflection.setScaleHorizontal(50.0);
        assertThat(reflection.getScaleHorizontal()).isCloseTo(50.0, offset(0.01));
    }

    @Test
    void scaleHorizontal_negativeFlip() {
        var reflection = new Reflection(reflectionElement, null);
        reflection.setScaleHorizontal(-100.0);
        assertThat(reflection.getScaleHorizontal()).isCloseTo(-100.0, offset(0.01));
    }

    @Test
    void scaleHorizontal_setsRawAttribute() {
        var reflection = new Reflection(reflectionElement, null);
        reflection.setScaleHorizontal(50.0);
        assertThat(reflectionElement.getAttribute("sx")).isEqualTo("50000");
    }

    // --- scale vertical (sy attribute, default 100.0, scale 1000) ---

    @Test
    void scaleVertical_defaultsTo100() {
        var reflection = new Reflection(reflectionElement, null);
        assertThat(reflection.getScaleVertical()).isEqualTo(100.0);
    }

    @Test
    void scaleVertical_setAndGet() {
        var reflection = new Reflection(reflectionElement, null);
        reflection.setScaleVertical(75.0);
        assertThat(reflection.getScaleVertical()).isCloseTo(75.0, offset(0.01));
    }

    @Test
    void scaleVertical_setsRawAttribute() {
        var reflection = new Reflection(reflectionElement, null);
        reflection.setScaleVertical(75.0);
        assertThat(reflectionElement.getAttribute("sy")).isEqualTo("75000");
    }

    // --- save callback ---

    @Test
    void setBlurRadius_invokesSaveCallback() {
        AtomicInteger callCount = new AtomicInteger(0);
        var reflection = new Reflection(reflectionElement, callCount::incrementAndGet);
        reflection.setBlurRadius(5.0);
        assertThat(callCount.get()).isEqualTo(1);
    }

    @Test
    void setDirection_invokesSaveCallback() {
        AtomicInteger callCount = new AtomicInteger(0);
        var reflection = new Reflection(reflectionElement, callCount::incrementAndGet);
        reflection.setDirection(90.0);
        assertThat(callCount.get()).isEqualTo(1);
    }

    @Test
    void setDistance_invokesSaveCallback() {
        AtomicInteger callCount = new AtomicInteger(0);
        var reflection = new Reflection(reflectionElement, callCount::incrementAndGet);
        reflection.setDistance(4.0);
        assertThat(callCount.get()).isEqualTo(1);
    }

    @Test
    void setStartPosAlpha_invokesSaveCallback() {
        AtomicInteger callCount = new AtomicInteger(0);
        var reflection = new Reflection(reflectionElement, callCount::incrementAndGet);
        reflection.setStartPosAlpha(50.0);
        assertThat(callCount.get()).isEqualTo(1);
    }

    @Test
    void setEndPosAlpha_invokesSaveCallback() {
        AtomicInteger callCount = new AtomicInteger(0);
        var reflection = new Reflection(reflectionElement, callCount::incrementAndGet);
        reflection.setEndPosAlpha(100.0);
        assertThat(callCount.get()).isEqualTo(1);
    }

    @Test
    void setFadeDirection_invokesSaveCallback() {
        AtomicInteger callCount = new AtomicInteger(0);
        var reflection = new Reflection(reflectionElement, callCount::incrementAndGet);
        reflection.setFadeDirection(90.0);
        assertThat(callCount.get()).isEqualTo(1);
    }

    @Test
    void setRectangleAlign_invokesSaveCallback() {
        AtomicInteger callCount = new AtomicInteger(0);
        var reflection = new Reflection(reflectionElement, callCount::incrementAndGet);
        reflection.setRectangleAlign(RectangleAlignment.CENTER);
        assertThat(callCount.get()).isEqualTo(1);
    }

    @Test
    void setRotateShadowWithShape_invokesSaveCallback() {
        AtomicInteger callCount = new AtomicInteger(0);
        var reflection = new Reflection(reflectionElement, callCount::incrementAndGet);
        reflection.setRotateShadowWithShape(false);
        assertThat(callCount.get()).isEqualTo(1);
    }

    @Test
    void noCallbackDoesNotThrow() {
        var reflection = new Reflection(reflectionElement, null);
        reflection.setStartPosAlpha(50.0);
        reflection.setEndPosAlpha(100.0);
        reflection.setFadeDirection(90.0);
        reflection.setStartReflectionOpacity(50.0);
        reflection.setEndReflectionOpacity(80.0);
        reflection.setBlurRadius(5.0);
        reflection.setDirection(180.0);
        reflection.setDistance(3.0);
        reflection.setRectangleAlign(RectangleAlignment.TOP);
        reflection.setSkewHorizontal(10.0);
        reflection.setSkewVertical(10.0);
        reflection.setRotateShadowWithShape(false);
        reflection.setScaleHorizontal(50.0);
        reflection.setScaleVertical(50.0);
    }

    // --- interface contracts ---

    @Test
    void asIPresentationComponent_returnsSelf() {
        var reflection = new Reflection(reflectionElement, null);
        IPresentationComponent result = reflection.asIPresentationComponent();
        assertThat(result).isSameAs(reflection);
    }

    @Test
    void asIImageTransformOperation_returnsSelf() {
        var reflection = new Reflection(reflectionElement, null);
        IImageTransformOperation result = reflection.asIImageTransformOperation();
        assertThat(result).isSameAs(reflection);
    }

    @Test
    void slide_nullWhenNoParentSlide() {
        var reflection = new Reflection(reflectionElement, null);
        assertThat(reflection.getSlide()).isNull();
    }

    @Test
    void presentation_nullWhenNoParentSlide() {
        var reflection = new Reflection(reflectionElement, null);
        assertThat(reflection.getPresentation()).isNull();
    }
}
