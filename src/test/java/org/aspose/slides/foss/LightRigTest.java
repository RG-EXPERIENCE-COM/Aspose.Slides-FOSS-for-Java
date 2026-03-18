package org.aspose.slides.foss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link LightRig}: light type, direction, and rotation.
 *
 * <p>Verifies light type, direction, and rotation behavior.</p>
 */
class LightRigTest {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";

    private Document doc;
    private Element scene3d;

    @BeforeEach
    void setUp() throws Exception {
        doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .getDOMImplementation()
                .createDocument(NS_A, "a:scene3d", null);
        scene3d = doc.getDocumentElement();
    }

    private LightRig createLightRig() {
        return new LightRig(scene3d, null);
    }

    // --- light type ---

    @Test
    void lightType_defaultIsNotDefined() {
        var lr = createLightRig();
        assertThat(lr.getLightType()).isEqualTo(LightRigPresetType.NOT_DEFINED);
    }

    @Test
    void lightType_balanced_persists() {
        var lr = createLightRig();
        lr.setLightType(LightRigPresetType.BALANCED);
        assertThat(lr.getLightType()).isEqualTo(LightRigPresetType.BALANCED);
    }

    static Stream<LightRigPresetType> lightRigPresetTypes() {
        return Stream.of(
                LightRigPresetType.BALANCED, LightRigPresetType.BRIGHT_ROOM,
                LightRigPresetType.CHILLY, LightRigPresetType.CONTRASTING,
                LightRigPresetType.FLAT, LightRigPresetType.FLOOD,
                LightRigPresetType.FREEZING, LightRigPresetType.GLOW,
                LightRigPresetType.HARSH, LightRigPresetType.MORNING,
                LightRigPresetType.SOFT, LightRigPresetType.SUNRISE,
                LightRigPresetType.SUNSET, LightRigPresetType.THREE_PT,
                LightRigPresetType.TWO_PT);
    }

    @ParameterizedTest
    @MethodSource("lightRigPresetTypes")
    void lightType_allPresetsRoundTrip(LightRigPresetType type) {
        var lr = createLightRig();
        lr.setLightType(type);
        assertThat(lr.getLightType()).isEqualTo(type);
    }

    // --- direction ---

    @Test
    void direction_defaultIsNotDefined() {
        var lr = createLightRig();
        assertThat(lr.getDirection()).isEqualTo(LightingDirection.NOT_DEFINED);
    }

    @Test
    void direction_top_persists() {
        var lr = createLightRig();
        lr.setDirection(LightingDirection.TOP);
        assertThat(lr.getDirection()).isEqualTo(LightingDirection.TOP);
    }

    static Stream<LightingDirection> lightingDirections() {
        return Stream.of(
                LightingDirection.TOP, LightingDirection.TOP_LEFT,
                LightingDirection.TOP_RIGHT, LightingDirection.BOTTOM,
                LightingDirection.BOTTOM_LEFT, LightingDirection.BOTTOM_RIGHT,
                LightingDirection.LEFT, LightingDirection.RIGHT);
    }

    @ParameterizedTest
    @MethodSource("lightingDirections")
    void direction_allValuesRoundTrip(LightingDirection dir) {
        var lr = createLightRig();
        lr.setDirection(dir);
        assertThat(lr.getDirection()).isEqualTo(dir);
    }

    // --- combined: light rig preset and direction persist (test_light_rig) ---

    @Test
    void lightTypeAndDirection_persistTogether() {
        var lr = createLightRig();
        lr.setLightType(LightRigPresetType.BALANCED);
        lr.setDirection(LightingDirection.TOP);

        // Simulate re-reading from the same XML (as save/reload would)
        var lr2 = new LightRig(scene3d, null);
        assertThat(lr2.getLightType()).isEqualTo(LightRigPresetType.BALANCED);
        assertThat(lr2.getDirection()).isEqualTo(LightingDirection.TOP);
    }

    // --- rotation ---

    @Test
    void rotation_defaultIsZeros() {
        var lr = createLightRig();
        double[] rot = lr.getRotation();
        assertThat(rot).containsExactly(0.0, 0.0, 0.0);
    }

    @Test
    void rotation_setAndGet() {
        var lr = createLightRig();
        lr.setRotation(45.0, 90.0, 180.0);
        double[] rot = lr.getRotation();
        assertThat(rot[0]).isCloseTo(45.0, org.assertj.core.data.Offset.offset(0.001));
        assertThat(rot[1]).isCloseTo(90.0, org.assertj.core.data.Offset.offset(0.001));
        assertThat(rot[2]).isCloseTo(180.0, org.assertj.core.data.Offset.offset(0.001));
    }

    // --- save callback ---

    @Test
    void setLightType_invokesSaveCallback() {
        int[] callCount = {0};
        var lr = new LightRig(scene3d, () -> callCount[0]++);
        lr.setLightType(LightRigPresetType.BALANCED);
        assertThat(callCount[0]).isEqualTo(1);
    }

    @Test
    void setDirection_invokesSaveCallback() {
        int[] callCount = {0};
        var lr = new LightRig(scene3d, () -> callCount[0]++);
        lr.setDirection(LightingDirection.TOP);
        assertThat(callCount[0]).isEqualTo(1);
    }

    // --- reads from existing XML ---

    @Test
    void readsExistingLightRigElement() {
        Element lightRig = doc.createElementNS(NS_A, "a:lightRig");
        lightRig.setAttribute("rig", "balanced");
        lightRig.setAttribute("dir", "t");
        scene3d.appendChild(lightRig);

        var lr = createLightRig();
        assertThat(lr.getLightType()).isEqualTo(LightRigPresetType.BALANCED);
        assertThat(lr.getDirection()).isEqualTo(LightingDirection.TOP);
    }
}
