package org.aspose.slides.foss.effects;

import org.aspose.slides.foss.PresetShadowType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

/**
 * Tests for {@link PresetShadow}: all properties, interface contracts, and save callback.
 */
class PresetShadowTest {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";

    private Document doc;
    private Element shadowElement;

    @BeforeEach
    void setUp() throws Exception {
        doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .getDOMImplementation()
                .createDocument(NS_A, "a:prstShdw", null);
        shadowElement = doc.getDocumentElement();
    }

    // --- direction ---

    @Test
    void direction_defaultsToZeroWhenNotSet() {
        var shadow = new PresetShadow(shadowElement, null);
        assertThat(shadow.getDirection()).isEqualTo(0.0);
    }

    @Test
    void direction_setAndGet() {
        var shadow = new PresetShadow(shadowElement, null);
        shadow.setDirection(315.0);
        assertThat(shadow.getDirection()).isCloseTo(315.0, offset(0.01));
    }

    @Test
    void direction_roundTrip() {
        var shadow = new PresetShadow(shadowElement, null);
        shadow.setDirection(270.0);

        var shadow2 = new PresetShadow(shadowElement, null);
        assertThat(shadow2.getDirection()).isCloseTo(270.0, offset(0.01));
    }

    @Test
    void direction_setsRawAngleAttribute() {
        var shadow = new PresetShadow(shadowElement, null);
        shadow.setDirection(1.0);
        assertThat(shadowElement.getAttribute("dir")).isEqualTo("60000");
    }

    // --- distance ---

    @Test
    void distance_defaultsToZeroWhenNotSet() {
        var shadow = new PresetShadow(shadowElement, null);
        assertThat(shadow.getDistance()).isEqualTo(0.0);
    }

    @Test
    void distance_setAndGet() {
        var shadow = new PresetShadow(shadowElement, null);
        shadow.setDistance(8.0);
        assertThat(shadow.getDistance()).isCloseTo(8.0, offset(0.01));
    }

    @Test
    void distance_roundTrip() {
        var shadow = new PresetShadow(shadowElement, null);
        shadow.setDistance(5.5);

        var shadow2 = new PresetShadow(shadowElement, null);
        assertThat(shadow2.getDistance()).isCloseTo(5.5, offset(0.01));
    }

    @Test
    void distance_setsRawEmuAttribute() {
        var shadow = new PresetShadow(shadowElement, null);
        shadow.setDistance(1.0);
        assertThat(shadowElement.getAttribute("dist")).isEqualTo("12700");
    }

    // --- shadow color ---

    @Test
    void shadowColor_isNotNull() {
        var shadow = new PresetShadow(shadowElement, null);
        assertThat(shadow.getShadowColor()).isNotNull();
    }

    // --- preset ---

    @Test
    void preset_defaultsToNullWhenNotSet() {
        var shadow = new PresetShadow(shadowElement, null);
        assertThat(shadow.getPreset()).isNull();
    }

    @Test
    void preset_setAndGet() {
        var shadow = new PresetShadow(shadowElement, null);
        shadow.setPreset(PresetShadowType.TOP_LEFT_DROP_SHADOW);
        assertThat(shadow.getPreset()).isEqualTo(PresetShadowType.TOP_LEFT_DROP_SHADOW);
    }

    @Test
    void preset_roundTrip() {
        var shadow = new PresetShadow(shadowElement, null);
        shadow.setPreset(PresetShadowType.BOTTOM_RIGHT_DROP_SHADOW);

        var shadow2 = new PresetShadow(shadowElement, null);
        assertThat(shadow2.getPreset()).isEqualTo(PresetShadowType.BOTTOM_RIGHT_DROP_SHADOW);
    }

    @Test
    void preset_setsRawAttribute() {
        var shadow = new PresetShadow(shadowElement, null);
        shadow.setPreset(PresetShadowType.OUTER_BOX_SHADOW_3D);
        assertThat(shadowElement.getAttribute("prst")).isEqualTo("shdw9");
    }

    @Test
    void preset_setNullRemovesAttribute() {
        var shadow = new PresetShadow(shadowElement, null);
        shadow.setPreset(PresetShadowType.TOP_LEFT_DROP_SHADOW);
        shadow.setPreset(null);
        assertThat(shadowElement.getAttribute("prst")).isEmpty();
    }

    // --- save callback ---

    @Test
    void setDirection_invokesSaveCallback() {
        AtomicInteger callCount = new AtomicInteger(0);
        var shadow = new PresetShadow(shadowElement, callCount::incrementAndGet);
        shadow.setDirection(90.0);
        assertThat(callCount.get()).isEqualTo(1);
    }

    @Test
    void setDistance_invokesSaveCallback() {
        AtomicInteger callCount = new AtomicInteger(0);
        var shadow = new PresetShadow(shadowElement, callCount::incrementAndGet);
        shadow.setDistance(4.0);
        assertThat(callCount.get()).isEqualTo(1);
    }

    @Test
    void setPreset_invokesSaveCallback() {
        AtomicInteger callCount = new AtomicInteger(0);
        var shadow = new PresetShadow(shadowElement, callCount::incrementAndGet);
        shadow.setPreset(PresetShadowType.FRONT_BOTTOM_SHADOW);
        assertThat(callCount.get()).isEqualTo(1);
    }

    @Test
    void noCallbackDoesNotThrow() {
        var shadow = new PresetShadow(shadowElement, null);
        shadow.setDirection(180.0);
        shadow.setDistance(3.0);
        shadow.setPreset(PresetShadowType.TOP_LEFT_DROP_SHADOW);
    }

    // --- interface contracts ---

    @Test
    void asIImageTransformOperation_returnsSelf() {
        var shadow = new PresetShadow(shadowElement, null);
        IImageTransformOperation result = shadow.asIImageTransformOperation();
        assertThat(result).isSameAs(shadow);
    }

    @Test
    void implementsIPresetShadow() {
        var shadow = new PresetShadow(shadowElement, null);
        assertThat(shadow).isInstanceOf(IPresetShadow.class);
    }

    @Test
    void implementsIImageTransformOperation() {
        var shadow = new PresetShadow(shadowElement, null);
        assertThat(shadow).isInstanceOf(IImageTransformOperation.class);
    }
}
