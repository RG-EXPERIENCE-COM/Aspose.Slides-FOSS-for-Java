package org.aspose.slides.foss.effects;

import org.aspose.slides.foss.FillBlendMode;
import org.aspose.slides.foss.FillType;
import org.aspose.slides.foss.IPresentationComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link FillOverlay}: blend mode, fill format, interface contracts, and save callback.
 *
 * <p>Covers blend mode, fill format integration, and save callback behavior.</p>
 */
class FillOverlayTest {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";

    private Document doc;
    private Element fillOverlayElement;

    @BeforeEach
    void setUp() throws Exception {
        doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .getDOMImplementation()
                .createDocument(NS_A, "a:fillOverlay", null);
        fillOverlayElement = doc.getDocumentElement();
    }

    // --- blend ---

    @Test
    void blend_defaultsToOverlayWhenNotSet() {
        var fo = new FillOverlay(fillOverlayElement, null);
        assertThat(fo.getBlend()).isEqualTo(FillBlendMode.OVERLAY);
    }

    @Test
    void blend_setAndGet_overlay() {
        var fo = new FillOverlay(fillOverlayElement, null);
        fo.setBlend(FillBlendMode.OVERLAY);
        assertThat(fo.getBlend()).isEqualTo(FillBlendMode.OVERLAY);
        assertThat(fillOverlayElement.getAttribute("blend")).isEqualTo("over");
    }

    @Test
    void blend_setAndGet_multiply() {
        var fo = new FillOverlay(fillOverlayElement, null);
        fo.setBlend(FillBlendMode.MULTIPLY);
        assertThat(fo.getBlend()).isEqualTo(FillBlendMode.MULTIPLY);
        assertThat(fillOverlayElement.getAttribute("blend")).isEqualTo("mult");
    }

    @Test
    void blend_setAndGet_screen() {
        var fo = new FillOverlay(fillOverlayElement, null);
        fo.setBlend(FillBlendMode.SCREEN);
        assertThat(fo.getBlend()).isEqualTo(FillBlendMode.SCREEN);
        assertThat(fillOverlayElement.getAttribute("blend")).isEqualTo("screen");
    }

    @Test
    void blend_setAndGet_darken() {
        var fo = new FillOverlay(fillOverlayElement, null);
        fo.setBlend(FillBlendMode.DARKEN);
        assertThat(fo.getBlend()).isEqualTo(FillBlendMode.DARKEN);
        assertThat(fillOverlayElement.getAttribute("blend")).isEqualTo("darken");
    }

    @Test
    void blend_setAndGet_lighten() {
        var fo = new FillOverlay(fillOverlayElement, null);
        fo.setBlend(FillBlendMode.LIGHTEN);
        assertThat(fo.getBlend()).isEqualTo(FillBlendMode.LIGHTEN);
        assertThat(fillOverlayElement.getAttribute("blend")).isEqualTo("lighten");
    }

    @Test
    void blend_roundTrip() {
        var fo = new FillOverlay(fillOverlayElement, null);
        fo.setBlend(FillBlendMode.SCREEN);

        // Re-read from the same element (simulates save/reload)
        var fo2 = new FillOverlay(fillOverlayElement, null);
        assertThat(fo2.getBlend()).isEqualTo(FillBlendMode.SCREEN);
    }

    @Test
    void blend_unknownAttributeDefaultsToOverlay() {
        fillOverlayElement.setAttribute("blend", "unknownValue");
        var fo = new FillOverlay(fillOverlayElement, null);
        assertThat(fo.getBlend()).isEqualTo(FillBlendMode.OVERLAY);
    }

    // --- fill format ---

    @Test
    void fillFormat_returnsNonNull() {
        var fo = new FillOverlay(fillOverlayElement, null);
        assertThat(fo.getFillFormat()).isNotNull();
    }

    @Test
    void fillFormat_defaultsToNotDefined() {
        var fo = new FillOverlay(fillOverlayElement, null);
        assertThat(fo.getFillFormat().getFillType()).isEqualTo(FillType.NOT_DEFINED);
    }

    // --- save callback ---

    @Test
    void setBlend_invokesSaveCallback() {
        AtomicInteger callCount = new AtomicInteger(0);
        var fo = new FillOverlay(fillOverlayElement, callCount::incrementAndGet);
        fo.setBlend(FillBlendMode.DARKEN);
        assertThat(callCount.get()).isEqualTo(1);
    }

    @Test
    void noCallbackDoesNotThrow() {
        var fo = new FillOverlay(fillOverlayElement, null);
        fo.setBlend(FillBlendMode.MULTIPLY);
        // No exception expected
    }

    // --- interface contracts ---

    @Test
    void asIPresentationComponent_returnsSelf() {
        var fo = new FillOverlay(fillOverlayElement, null);
        IPresentationComponent result = fo.asIPresentationComponent();
        assertThat(result).isSameAs(fo);
    }

    @Test
    void asIImageTransformOperation_returnsSelf() {
        var fo = new FillOverlay(fillOverlayElement, null);
        IImageTransformOperation result = fo.asIImageTransformOperation();
        assertThat(result).isSameAs(fo);
    }

    @Test
    void slide_nullWhenNoParentSlide() {
        var fo = new FillOverlay(fillOverlayElement, null);
        assertThat(fo.getSlide()).isNull();
    }

    @Test
    void presentation_nullWhenNoParentSlide() {
        var fo = new FillOverlay(fillOverlayElement, null);
        assertThat(fo.getPresentation()).isNull();
    }
}
