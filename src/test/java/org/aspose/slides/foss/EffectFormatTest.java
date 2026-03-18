package org.aspose.slides.foss;

import org.aspose.slides.foss.effects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

/**
 * Tests for {@link EffectFormat}: blur, glow, outer shadow, soft edge,
 * enable/disable effects, and save callback invocation.
 *
 * <p>Verifies effect format behavior for blur, glow, shadows, and soft edges.</p>
 */
class EffectFormatTest {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";

    private Document doc;
    private Element spPr;

    @BeforeEach
    void setUp() throws Exception {
        doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .getDOMImplementation()
                .createDocument(NS_A, "a:spPr", null);
        spPr = doc.getDocumentElement();
    }

    private EffectFormat createEffectFormat() {
        return new EffectFormat(spPr, null);
    }

    private EffectFormat createEffectFormatWithCallback(Runnable callback) {
        return new EffectFormat(spPr, callback);
    }

    // --- isNoEffects ---

    @Test
    void isNoEffects_trueWhenNoEffectLst() {
        var ef = createEffectFormat();
        assertThat(ef.isNoEffects()).isTrue();
    }

    @Test
    void isNoEffects_trueWhenEmptyEffectLst() {
        spPr.appendChild(doc.createElementNS(NS_A, "a:effectLst"));
        var ef = createEffectFormat();
        assertThat(ef.isNoEffects()).isTrue();
    }

    @Test
    void isNoEffects_falseWhenEffectsPresent() {
        var ef = createEffectFormat();
        ef.enableOuterShadowEffect();
        assertThat(ef.isNoEffects()).isFalse();
    }

    // --- blur ---

    @Test
    void blur_nullWhenNotPresent() {
        var ef = createEffectFormat();
        assertThat(ef.getBlurEffect()).isNull();
    }

    @Test
    void blur_setBlurEffectCreatesAndPersists() {
        var ef = createEffectFormat();
        ef.setBlurEffect(8, true);

        IBlur blur = ef.getBlurEffect();
        assertThat(blur).isNotNull();
        assertThat(blur.getRadius()).isCloseTo(8.0, offset(0.01));
        assertThat(blur.isGrow()).isTrue();
    }

    @Test
    void blur_roundTripThroughXml() {
        var ef = createEffectFormat();
        ef.setBlurEffect(8, true);

        // Re-read from the same XML (simulates save/reload)
        var ef2 = createEffectFormat();
        IBlur b2 = ef2.getBlurEffect();
        assertThat(b2).isNotNull();
        assertThat(b2.getRadius()).isCloseTo(8.0, offset(0.01));
        assertThat(b2.isGrow()).isTrue();
    }

    // --- glow ---

    @Test
    void glow_nullWhenNotPresent() {
        var ef = createEffectFormat();
        assertThat(ef.getGlowEffect()).isNull();
    }

    @Test
    void glow_enableAndSetRadius() {
        var ef = createEffectFormat();
        ef.enableGlowEffect();
        IGlow glow = ef.getGlowEffect();
        assertThat(glow).isNotNull();

        glow.setRadius(15);

        // Re-read
        var ef2 = createEffectFormat();
        IGlow g2 = ef2.getGlowEffect();
        assertThat(g2).isNotNull();
        assertThat(g2.getRadius()).isCloseTo(15.0, offset(0.01));
    }

    // --- outer shadow ---

    @Test
    void outerShadow_nullWhenNotPresent() {
        var ef = createEffectFormat();
        assertThat(ef.getOuterShadowEffect()).isNull();
    }

    @Test
    void outerShadow_enableAndSetProperties() {
        var ef = createEffectFormat();
        ef.enableOuterShadowEffect();

        IOuterShadow shadow = ef.getOuterShadowEffect();
        assertThat(shadow).isNotNull();

        shadow.setBlurRadius(10);
        shadow.setDirection(315);
        shadow.setDistance(8);

        // Re-read
        var ef2 = createEffectFormat();
        IOuterShadow s2 = ef2.getOuterShadowEffect();
        assertThat(s2).isNotNull();
        assertThat(s2.getBlurRadius()).isCloseTo(10.0, offset(0.01));
        assertThat(s2.getDirection()).isCloseTo(315.0, offset(0.01));
        assertThat(s2.getDistance()).isCloseTo(8.0, offset(0.01));
    }

    // --- soft edge ---

    @Test
    void softEdge_nullWhenNotPresent() {
        var ef = createEffectFormat();
        assertThat(ef.getSoftEdgeEffect()).isNull();
    }

    @Test
    void softEdge_enableAndSetRadius() {
        var ef = createEffectFormat();
        ef.enableSoftEdgeEffect();

        ISoftEdge se = ef.getSoftEdgeEffect();
        assertThat(se).isNotNull();
        se.setRadius(10);

        // Re-read
        var ef2 = createEffectFormat();
        ISoftEdge se2 = ef2.getSoftEdgeEffect();
        assertThat(se2).isNotNull();
        assertThat(se2.getRadius()).isCloseTo(10.0, offset(0.01));
    }

    // --- enable / disable ---

    @Test
    void enableDisable_effectsCanBeEnabledThenDisabled() {
        var ef = createEffectFormat();
        ef.enableOuterShadowEffect();
        ef.enableGlowEffect();
        assertThat(ef.isNoEffects()).isFalse();

        ef.disableOuterShadowEffect();
        ef.disableGlowEffect();
        assertThat(ef.isNoEffects()).isTrue();
    }

    @Test
    void enableDisable_disablingAlreadyDisabledIsNoOp() {
        var ef = createEffectFormat();
        ef.disableBlurEffect(); // should not throw
        assertThat(ef.isNoEffects()).isTrue();
    }

    @Test
    void enableDisable_allEffectTypes() {
        var ef = createEffectFormat();

        ef.enableFillOverlayEffect();
        assertThat(ef.getFillOverlayEffect()).isNotNull();
        ef.disableFillOverlayEffect();
        assertThat(ef.getFillOverlayEffect()).isNull();

        ef.enableInnerShadowEffect();
        assertThat(ef.getInnerShadowEffect()).isNotNull();
        ef.disableInnerShadowEffect();
        assertThat(ef.getInnerShadowEffect()).isNull();

        ef.enablePresetShadowEffect();
        assertThat(ef.getPresetShadowEffect()).isNotNull();
        ef.disablePresetShadowEffect();
        assertThat(ef.getPresetShadowEffect()).isNull();

        ef.enableReflectionEffect();
        assertThat(ef.getReflectionEffect()).isNotNull();
        ef.disableReflectionEffect();
        assertThat(ef.getReflectionEffect()).isNull();
    }

    // --- save callback ---

    @Test
    void setBlurEffect_invokesSaveCallback() {
        AtomicInteger callCount = new AtomicInteger(0);
        var ef = createEffectFormatWithCallback(callCount::incrementAndGet);
        ef.setBlurEffect(5, false);
        assertThat(callCount.get()).isEqualTo(1);
    }

    @Test
    void enableDisable_invokesSaveCallback() {
        AtomicInteger callCount = new AtomicInteger(0);
        var ef = createEffectFormatWithCallback(callCount::incrementAndGet);
        ef.enableOuterShadowEffect();
        assertThat(callCount.get()).isEqualTo(1);
        ef.disableOuterShadowEffect();
        assertThat(callCount.get()).isEqualTo(2);
    }

    // --- asIEffectParamSource ---

    @Test
    void asIEffectParamSource_returnsSelf() {
        var ef = createEffectFormat();
        assertThat(ef.asIEffectParamSource()).isSameAs(ef);
    }
}
