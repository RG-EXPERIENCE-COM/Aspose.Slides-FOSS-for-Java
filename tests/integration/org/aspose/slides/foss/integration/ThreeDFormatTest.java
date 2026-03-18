package org.aspose.slides.foss.integration;
import org.aspose.slides.foss.*;

import org.aspose.slides.foss.export.SaveFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for ThreeDFormat: bevel, camera, light rig, depth.
 */
class ThreeDFormatTest implements AutoCloseable {

    @TempDir
    Path tempDir;

    @Override
    public void close() {
        // TempDir handles cleanup
    }

    /**
     * Saves a Presentation to a
     * temporary file, disposes the original, and reopens from that file.
     */
    private Presentation saveAndReopen(Presentation pres) throws IOException {
        String path = tempDir.resolve("roundtrip.pptx").toString();
        pres.save(path, SaveFormat.PPTX);
        pres.dispose();
        return new Presentation(path);
    }

    /**
     * Clears all shapes from slide 0 and returns that slide.
     * Clears all shapes from slide 0.
     */
    private ISlide clear(Presentation pres) {
        ISlide slide = pres.getSlides().get(0);
        slide.getShapes().clear();
        return slide;
    }

    // --- test_bevel_top ---

    @Test
    void testBevelTop() throws IOException {
        try (var pres = new Presentation()) {
            var slide = clear(pres);
            var shape = slide.getShapes().addAutoShape(ShapeType.RECTANGLE, 100, 100, 200, 100);
            var tdf = shape.getThreeDFormat();
            tdf.getBevelTop().setBevelType(BevelPresetType.CIRCLE);
            tdf.getBevelTop().setWidth(10);
            tdf.getBevelTop().setHeight(5);

            try (var pres2 = saveAndReopen(pres)) {
                var bt = pres2.getSlides().get(0).getShapes().get(0).getThreeDFormat().getBevelTop();
                assertThat(bt.getBevelType()).isEqualTo(BevelPresetType.CIRCLE);
                assertThat(bt.getWidth()).isEqualTo(10);
                assertThat(bt.getHeight()).isEqualTo(5);
            }
        }
    }

    // --- test_camera ---

    @Test
    void testCamera() throws IOException {
        try (var pres = new Presentation()) {
            var slide = clear(pres);
            var shape = slide.getShapes().addAutoShape(ShapeType.RECTANGLE, 100, 100, 200, 100);
            shape.getThreeDFormat().getCamera().setCameraType(CameraPresetType.PERSPECTIVE_ABOVE);

            try (var pres2 = saveAndReopen(pres)) {
                var cam = pres2.getSlides().get(0).getShapes().get(0).getThreeDFormat().getCamera();
                assertThat(cam.getCameraType()).isEqualTo(CameraPresetType.PERSPECTIVE_ABOVE);
            }
        }
    }

    // --- test_light_rig ---

    @Test
    void testLightRig() throws IOException {
        try (var pres = new Presentation()) {
            var slide = clear(pres);
            var shape = slide.getShapes().addAutoShape(ShapeType.RECTANGLE, 100, 100, 200, 100);
            var lr = shape.getThreeDFormat().getLightRig();
            lr.setLightType(LightRigPresetType.BALANCED);
            lr.setDirection(LightingDirection.TOP);

            try (var pres2 = saveAndReopen(pres)) {
                var lr2 = pres2.getSlides().get(0).getShapes().get(0).getThreeDFormat().getLightRig();
                assertThat(lr2.getLightType()).isEqualTo(LightRigPresetType.BALANCED);
                assertThat(lr2.getDirection()).isEqualTo(LightingDirection.TOP);
            }
        }
    }

    // --- test_depth_and_material ---

    @Test
    void testDepthAndMaterial() throws IOException {
        try (var pres = new Presentation()) {
            var slide = clear(pres);
            var shape = slide.getShapes().addAutoShape(ShapeType.RECTANGLE, 100, 100, 200, 100);
            var tdf = shape.getThreeDFormat();
            tdf.setDepth(20);
            tdf.setMaterial(MaterialPresetType.METAL);

            try (var pres2 = saveAndReopen(pres)) {
                var tdf2 = pres2.getSlides().get(0).getShapes().get(0).getThreeDFormat();
                assertThat(tdf2.getDepth()).isEqualTo(20);
                assertThat(tdf2.getMaterial()).isEqualTo(MaterialPresetType.METAL);
            }
        }
    }
}
