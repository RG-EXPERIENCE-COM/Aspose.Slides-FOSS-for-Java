package org.aspose.slides.foss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

/**
 * Tests for {@link ShapeBevel} implementing {@link IShapeBevel}.
 *
 * <p>Verifies width, height, and bevelType properties for both
 * top and bottom bevels, including defaults, round-trips, and
 * save callback invocation.</p>
 */
class IShapeBevelTest {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";

    private Document doc;
    private Element sp3d;

    @BeforeEach
    void setUp() throws Exception {
        doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .getDOMImplementation()
                .createDocument(NS_A, "a:sp3d", null);
        sp3d = doc.getDocumentElement();
    }

    private ShapeBevel createTopBevel() {
        return new ShapeBevel(sp3d, null, true);
    }

    private ShapeBevel createBottomBevel() {
        return new ShapeBevel(sp3d, null, false);
    }

    // --- width ---

    @Test
    void width_defaultIsZero() {
        var bevel = createTopBevel();
        assertThat(bevel.getWidth()).isEqualTo(0.0);
    }

    @Test
    void width_setAndGet() {
        var bevel = createTopBevel();
        bevel.setWidth(10.0);
        assertThat(bevel.getWidth()).isCloseTo(10.0, within(0.01));
    }

    @Test
    void width_persistsAcrossReRead() {
        var bevel = createTopBevel();
        bevel.setWidth(7.5);

        var bevel2 = new ShapeBevel(sp3d, null, true);
        assertThat(bevel2.getWidth()).isCloseTo(7.5, within(0.01));
    }

    // --- height ---

    @Test
    void height_defaultIsZero() {
        var bevel = createTopBevel();
        assertThat(bevel.getHeight()).isEqualTo(0.0);
    }

    @Test
    void height_setAndGet() {
        var bevel = createTopBevel();
        bevel.setHeight(15.0);
        assertThat(bevel.getHeight()).isCloseTo(15.0, within(0.01));
    }

    @Test
    void height_persistsAcrossReRead() {
        var bevel = createTopBevel();
        bevel.setHeight(3.25);

        var bevel2 = new ShapeBevel(sp3d, null, true);
        assertThat(bevel2.getHeight()).isCloseTo(3.25, within(0.01));
    }

    // --- bevelType ---

    @Test
    void bevelType_defaultIsNotDefined() {
        var bevel = createTopBevel();
        assertThat(bevel.getBevelType()).isEqualTo(BevelPresetType.NOT_DEFINED);
    }

    @Test
    void bevelType_setAndGet() {
        var bevel = createTopBevel();
        bevel.setBevelType(BevelPresetType.CIRCLE);
        assertThat(bevel.getBevelType()).isEqualTo(BevelPresetType.CIRCLE);
    }

    @Test
    void bevelType_persistsAcrossReRead() {
        var bevel = createTopBevel();
        bevel.setBevelType(BevelPresetType.SOFT_ROUND);

        var bevel2 = new ShapeBevel(sp3d, null, true);
        assertThat(bevel2.getBevelType()).isEqualTo(BevelPresetType.SOFT_ROUND);
    }

    @Test
    void bevelType_notDefined_removesAttribute() {
        var bevel = createTopBevel();
        bevel.setBevelType(BevelPresetType.ANGLE);
        bevel.setBevelType(BevelPresetType.NOT_DEFINED);
        assertThat(bevel.getBevelType()).isEqualTo(BevelPresetType.NOT_DEFINED);
    }

    @ParameterizedTest
    @EnumSource(BevelPresetType.class)
    void bevelType_allPresetsRoundTrip(BevelPresetType type) {
        var bevel = createTopBevel();
        bevel.setBevelType(type);
        assertThat(bevel.getBevelType()).isEqualTo(type);
    }

    // --- bottom bevel ---

    @Test
    void bottomBevel_widthIndependentFromTop() {
        var top = createTopBevel();
        var bottom = createBottomBevel();
        top.setWidth(10.0);
        bottom.setWidth(20.0);
        assertThat(top.getWidth()).isCloseTo(10.0, within(0.01));
        assertThat(bottom.getWidth()).isCloseTo(20.0, within(0.01));
    }

    @Test
    void bottomBevel_bevelTypeIndependentFromTop() {
        var top = createTopBevel();
        var bottom = createBottomBevel();
        top.setBevelType(BevelPresetType.CIRCLE);
        bottom.setBevelType(BevelPresetType.CROSS);
        assertThat(top.getBevelType()).isEqualTo(BevelPresetType.CIRCLE);
        assertThat(bottom.getBevelType()).isEqualTo(BevelPresetType.CROSS);
    }

    // --- save callback ---

    @Test
    void saveCallback_invokedOnSetWidth() {
        var called = new boolean[]{false};
        var bevel = new ShapeBevel(sp3d, () -> called[0] = true, true);
        bevel.setWidth(5.0);
        assertThat(called[0]).isTrue();
    }

    @Test
    void saveCallback_invokedOnSetHeight() {
        var called = new boolean[]{false};
        var bevel = new ShapeBevel(sp3d, () -> called[0] = true, true);
        bevel.setHeight(5.0);
        assertThat(called[0]).isTrue();
    }

    @Test
    void saveCallback_invokedOnSetBevelType() {
        var called = new boolean[]{false};
        var bevel = new ShapeBevel(sp3d, () -> called[0] = true, true);
        bevel.setBevelType(BevelPresetType.CONVEX);
        assertThat(called[0]).isTrue();
    }
}
