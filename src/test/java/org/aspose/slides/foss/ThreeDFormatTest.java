package org.aspose.slides.foss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ThreeDFormat}: bevel, camera, light rig, depth, and material.
 *
 * <p>Verifies 3D format behavior for bevel, camera, light rig, depth, and material.</p>
 */
class ThreeDFormatTest {

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

    private ThreeDFormat createThreeDFormat() {
        return new ThreeDFormat(spPr, null);
    }

    // --- bevel top ---

    @Test
    void bevelTop_typeWidthHeight_persist() {
        var tdf = createThreeDFormat();
        tdf.getBevelTop().setBevelType(BevelPresetType.CIRCLE);
        tdf.getBevelTop().setWidth(10);
        tdf.getBevelTop().setHeight(5);

        // Re-read from same XML to verify persistence
        var tdf2 = createThreeDFormat();
        IShapeBevel bt = tdf2.getBevelTop();
        assertThat(bt.getBevelType()).isEqualTo(BevelPresetType.CIRCLE);
        assertThat(bt.getWidth()).isEqualTo(10);
        assertThat(bt.getHeight()).isEqualTo(5);
    }

    // --- camera ---

    @Test
    void camera_presetPersists() {
        var tdf = createThreeDFormat();
        tdf.getCamera().setCameraType(CameraPresetType.PERSPECTIVE_ABOVE);

        var tdf2 = createThreeDFormat();
        assertThat(tdf2.getCamera().getCameraType()).isEqualTo(CameraPresetType.PERSPECTIVE_ABOVE);
    }

    // --- light rig ---

    @Test
    void lightRig_presetAndDirectionPersist() {
        var tdf = createThreeDFormat();
        ILightRig lr = tdf.getLightRig();
        lr.setLightType(LightRigPresetType.BALANCED);
        lr.setDirection(LightingDirection.TOP);

        var tdf2 = createThreeDFormat();
        ILightRig lr2 = tdf2.getLightRig();
        assertThat(lr2.getLightType()).isEqualTo(LightRigPresetType.BALANCED);
        assertThat(lr2.getDirection()).isEqualTo(LightingDirection.TOP);
    }

    // --- depth and material ---

    @Test
    void depthAndMaterial_persist() {
        var tdf = createThreeDFormat();
        tdf.setDepth(20);
        tdf.setMaterial(MaterialPresetType.METAL);

        var tdf2 = createThreeDFormat();
        assertThat(tdf2.getDepth()).isEqualTo(20);
        assertThat(tdf2.getMaterial()).isEqualTo(MaterialPresetType.METAL);
    }

    // --- interface hierarchy ---

    @Test
    void implementsIThreeDParamSource() {
        var tdf = createThreeDFormat();
        assertThat(tdf).isInstanceOf(IThreeDParamSource.class);
        assertThat(tdf).isInstanceOf(IThreeDFormat.class);
    }
}
