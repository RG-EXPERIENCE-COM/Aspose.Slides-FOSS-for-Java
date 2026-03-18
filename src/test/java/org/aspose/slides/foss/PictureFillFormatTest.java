package org.aspose.slides.foss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

/**
 * Tests for {@link PictureFillFormat}.
 *
 * <p>Verifies picture fill with stretch mode and image assignment persists correctly.
 * Exercises the behavioral contract through XML element manipulation.</p>
 */
class PictureFillFormatTest {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";
    private static final String NS_R = "http://schemas.openxmlformats.org/officeDocument/2006/relationships";

    private Document doc;
    private Element blipFill;

    @BeforeEach
    void setUp() throws Exception {
        doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .newDocument();
        blipFill = doc.createElementNS(NS_A, "a:blipFill");
        doc.appendChild(blipFill);
    }

    /**
     * Picture fill with stretch mode and image persists.
     *
     * <p>Sets up the XML structure equivalent to what the Presentation save
     * would produce, then re-reads from the same XML to verify persistence.</p>
     */
    @Test
    void pictureFill_stretchModeWithImagePersists() {
        var pff = new PictureFillFormat(blipFill, null);

        // Set stretch mode (equivalent to pff.picture_fill_mode = PictureFillMode.STRETCH)
        pff.setPictureFillMode(PictureFillMode.STRETCH);
        assertThat(pff.getPictureFillMode()).isEqualTo(PictureFillMode.STRETCH);

        // Add a blip element to represent the image (equivalent to pff.picture.image = img)
        Element blip = doc.createElementNS(NS_A, "a:blip");
        blip.setAttributeNS(NS_R, "r:embed", "rId1");
        blipFill.insertBefore(blip, blipFill.getFirstChild());

        // Re-read from same XML (simulates save/reload)
        var pff2 = new PictureFillFormat(blipFill, null);
        assertThat(pff2.getPictureFillMode()).isEqualTo(PictureFillMode.STRETCH);
        assertThat(pff2.getPicture()).isNotNull();
    }

    /**
     * Tile mode with full tile configuration persists in XML.
     */
    @Test
    void pictureFill_tileModeWithPropertiesPersists() {
        var pff = new PictureFillFormat(blipFill, null);

        pff.setPictureFillMode(PictureFillMode.TILE);
        pff.setTileOffsetX(10.0f);
        pff.setTileOffsetY(-5.0f);
        pff.setTileScaleX(150.0f);
        pff.setTileScaleY(75.0f);
        pff.setTileAlignment(RectangleAlignment.CENTER);
        pff.setTileFlip(TileFlip.FLIP_BOTH);

        // Re-read from same XML
        var pff2 = new PictureFillFormat(blipFill, null);
        assertThat(pff2.getPictureFillMode()).isEqualTo(PictureFillMode.TILE);
        assertThat(pff2.getTileOffsetX()).isCloseTo(10.0f, offset(0.01f));
        assertThat(pff2.getTileOffsetY()).isCloseTo(-5.0f, offset(0.01f));
        assertThat(pff2.getTileScaleX()).isCloseTo(150.0f, offset(0.01f));
        assertThat(pff2.getTileScaleY()).isCloseTo(75.0f, offset(0.01f));
        assertThat(pff2.getTileAlignment()).isEqualTo(RectangleAlignment.CENTER);
        assertThat(pff2.getTileFlip()).isEqualTo(TileFlip.FLIP_BOTH);
    }

    /**
     * Crop values persist in XML and are correctly scaled (percentage * 1000).
     */
    @Test
    void pictureFill_cropValuesPersistInXml() {
        var pff = new PictureFillFormat(blipFill, null);
        pff.setCropLeft(10.0f);
        pff.setCropTop(20.0f);
        pff.setCropRight(5.0f);
        pff.setCropBottom(15.0f);

        var pff2 = new PictureFillFormat(blipFill, null);
        assertThat(pff2.getCropLeft()).isCloseTo(10.0f, offset(0.01f));
        assertThat(pff2.getCropTop()).isCloseTo(20.0f, offset(0.01f));
        assertThat(pff2.getCropRight()).isCloseTo(5.0f, offset(0.01f));
        assertThat(pff2.getCropBottom()).isCloseTo(15.0f, offset(0.01f));
    }

    /**
     * Stretch offset values persist in XML.
     */
    @Test
    void pictureFill_stretchOffsetsPersistInXml() {
        var pff = new PictureFillFormat(blipFill, null);
        pff.setStretchOffsetLeft(5.0f);
        pff.setStretchOffsetTop(10.0f);
        pff.setStretchOffsetRight(-3.0f);
        pff.setStretchOffsetBottom(7.0f);

        var pff2 = new PictureFillFormat(blipFill, null);
        assertThat(pff2.getStretchOffsetLeft()).isCloseTo(5.0f, offset(0.01f));
        assertThat(pff2.getStretchOffsetTop()).isCloseTo(10.0f, offset(0.01f));
        assertThat(pff2.getStretchOffsetRight()).isCloseTo(-3.0f, offset(0.01f));
        assertThat(pff2.getStretchOffsetBottom()).isCloseTo(7.0f, offset(0.01f));
    }

    /**
     * Switching from stretch to tile mode removes the stretch element.
     */
    @Test
    void pictureFill_switchFromStretchToTile() {
        var pff = new PictureFillFormat(blipFill, null);
        pff.setPictureFillMode(PictureFillMode.STRETCH);
        assertThat(pff.getPictureFillMode()).isEqualTo(PictureFillMode.STRETCH);

        pff.setPictureFillMode(PictureFillMode.TILE);
        assertThat(pff.getPictureFillMode()).isEqualTo(PictureFillMode.TILE);

        // Stretch offsets should return 0 since stretch element was removed
        assertThat(pff.getStretchOffsetLeft()).isEqualTo(0.0f);
    }

    /**
     * Switching from tile to stretch mode removes the tile element.
     */
    @Test
    void pictureFill_switchFromTileToStretch() {
        var pff = new PictureFillFormat(blipFill, null);
        pff.setPictureFillMode(PictureFillMode.TILE);
        pff.setTileOffsetX(50.0f);

        pff.setPictureFillMode(PictureFillMode.STRETCH);
        assertThat(pff.getPictureFillMode()).isEqualTo(PictureFillMode.STRETCH);

        // Tile properties should return defaults since tile element was removed
        assertThat(pff.getTileOffsetX()).isEqualTo(0.0f);
        assertThat(pff.getTileAlignment()).isEqualTo(RectangleAlignment.NOT_DEFINED);
        assertThat(pff.getTileFlip()).isEqualTo(TileFlip.NOT_DEFINED);
    }

    /**
     * All tile alignment values round-trip correctly through XML.
     */
    @ParameterizedTest
    @EnumSource(RectangleAlignment.class)
    void tileAlignment_allValuesRoundTrip(RectangleAlignment alignment) {
        Element bf = doc.createElementNS(NS_A, "a:blipFill");
        var pff = new PictureFillFormat(bf, null);
        pff.setTileAlignment(alignment);

        var pff2 = new PictureFillFormat(bf, null);
        assertThat(pff2.getTileAlignment()).isEqualTo(alignment);
    }

    /**
     * All tile flip values round-trip correctly through XML.
     */
    @ParameterizedTest
    @EnumSource(TileFlip.class)
    void tileFlip_allValuesRoundTrip(TileFlip flip) {
        Element bf = doc.createElementNS(NS_A, "a:blipFill");
        var pff = new PictureFillFormat(bf, null);
        pff.setTileFlip(flip);

        var pff2 = new PictureFillFormat(bf, null);
        assertThat(pff2.getTileFlip()).isEqualTo(flip);
    }
}
