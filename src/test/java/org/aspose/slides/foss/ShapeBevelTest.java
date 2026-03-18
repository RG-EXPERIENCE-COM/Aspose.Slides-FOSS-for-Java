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
import static org.assertj.core.data.Offset.offset;

/**
 * Tests for {@link ShapeBevel}: width, height, bevel type, and save callback.
 *
 * <p>Verifies bevel width, height, type, and save callback behavior.</p>
 */
class ShapeBevelTest {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";
    private static final double EMU_PER_POINT = 12700.0;

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
        bevel.setWidth(6.0);
        assertThat(bevel.getWidth()).isCloseTo(6.0, offset(0.01));
    }

    @Test
    void width_persistsAcrossReRead() {
        var bevel = createTopBevel();
        bevel.setWidth(10.0);

        var bevel2 = new ShapeBevel(sp3d, null, true);
        assertThat(bevel2.getWidth()).isCloseTo(10.0, offset(0.01));
    }

    @Test
    void width_storedAsEmu() {
        var bevel = createTopBevel();
        bevel.setWidth(5.0);

        Element bevelEl = (Element) sp3d.getElementsByTagNameNS(NS_A, "bevelT").item(0);
        assertThat(bevelEl).isNotNull();
        assertThat(Long.parseLong(bevelEl.getAttribute("w"))).isEqualTo(Math.round(5.0 * EMU_PER_POINT));
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
        bevel.setHeight(8.0);
        assertThat(bevel.getHeight()).isCloseTo(8.0, offset(0.01));
    }

    @Test
    void height_persistsAcrossReRead() {
        var bevel = createTopBevel();
        bevel.setHeight(12.5);

        var bevel2 = new ShapeBevel(sp3d, null, true);
        assertThat(bevel2.getHeight()).isCloseTo(12.5, offset(0.01));
    }

    // --- bevel type ---

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
    void bevelType_notDefined_removesAttribute() {
        var bevel = createTopBevel();
        bevel.setBevelType(BevelPresetType.CONVEX);
        bevel.setBevelType(BevelPresetType.NOT_DEFINED);
        assertThat(bevel.getBevelType()).isEqualTo(BevelPresetType.NOT_DEFINED);
    }

    static Stream<BevelPresetType> bevelPresetTypes() {
        return Stream.of(
                BevelPresetType.ANGLE, BevelPresetType.ART_DECO, BevelPresetType.CIRCLE,
                BevelPresetType.CONVEX, BevelPresetType.COOL_SLANT, BevelPresetType.CROSS,
                BevelPresetType.DIVOT, BevelPresetType.HARD_EDGE, BevelPresetType.RELAXED_INSET,
                BevelPresetType.RIBLET, BevelPresetType.SLOPE, BevelPresetType.SOFT_ROUND);
    }

    @ParameterizedTest
    @MethodSource("bevelPresetTypes")
    void bevelType_allPresetsRoundTrip(BevelPresetType type) {
        var bevel = createTopBevel();
        bevel.setBevelType(type);
        assertThat(bevel.getBevelType()).isEqualTo(type);
    }

    @Test
    void bevelType_persistsAcrossReRead() {
        var bevel = createTopBevel();
        bevel.setBevelType(BevelPresetType.SLOPE);

        var bevel2 = new ShapeBevel(sp3d, null, true);
        assertThat(bevel2.getBevelType()).isEqualTo(BevelPresetType.SLOPE);
    }

    // --- bottom bevel ---

    @Test
    void bottomBevel_usesCorrectElement() {
        var bevel = createBottomBevel();
        bevel.setWidth(4.0);
        bevel.setBevelType(BevelPresetType.DIVOT);

        assertThat(sp3d.getElementsByTagNameNS(NS_A, "bevelB").getLength()).isEqualTo(1);
        assertThat(sp3d.getElementsByTagNameNS(NS_A, "bevelT").getLength()).isEqualTo(0);
    }

    @Test
    void topAndBottomBevel_independent() {
        var top = createTopBevel();
        var bottom = createBottomBevel();

        top.setWidth(5.0);
        top.setBevelType(BevelPresetType.CIRCLE);
        bottom.setWidth(3.0);
        bottom.setBevelType(BevelPresetType.ANGLE);

        assertThat(top.getWidth()).isCloseTo(5.0, offset(0.01));
        assertThat(top.getBevelType()).isEqualTo(BevelPresetType.CIRCLE);
        assertThat(bottom.getWidth()).isCloseTo(3.0, offset(0.01));
        assertThat(bottom.getBevelType()).isEqualTo(BevelPresetType.ANGLE);
    }

    // --- save callback ---

    @Test
    void setWidth_invokesSaveCallback() {
        int[] callCount = {0};
        var bevel = new ShapeBevel(sp3d, () -> callCount[0]++, true);
        bevel.setWidth(5.0);
        assertThat(callCount[0]).isEqualTo(1);
    }

    @Test
    void setHeight_invokesSaveCallback() {
        int[] callCount = {0};
        var bevel = new ShapeBevel(sp3d, () -> callCount[0]++, true);
        bevel.setHeight(5.0);
        assertThat(callCount[0]).isEqualTo(1);
    }

    @Test
    void setBevelType_invokesSaveCallback() {
        int[] callCount = {0};
        var bevel = new ShapeBevel(sp3d, () -> callCount[0]++, true);
        bevel.setBevelType(BevelPresetType.CIRCLE);
        assertThat(callCount[0]).isEqualTo(1);
    }

    // --- reads from existing XML ---

    @Test
    void readsExistingBevelElement() {
        Element bevelT = doc.createElementNS(NS_A, "a:bevelT");
        bevelT.setAttribute("w", String.valueOf(Math.round(6.0 * EMU_PER_POINT)));
        bevelT.setAttribute("h", String.valueOf(Math.round(4.0 * EMU_PER_POINT)));
        bevelT.setAttribute("prst", "circle");
        sp3d.appendChild(bevelT);

        var bevel = createTopBevel();
        assertThat(bevel.getWidth()).isCloseTo(6.0, offset(0.01));
        assertThat(bevel.getHeight()).isCloseTo(4.0, offset(0.01));
        assertThat(bevel.getBevelType()).isEqualTo(BevelPresetType.CIRCLE);
    }
}
