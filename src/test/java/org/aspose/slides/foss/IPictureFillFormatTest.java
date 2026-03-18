package org.aspose.slides.foss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

/**
 * Tests for {@link IPictureFillFormat} contract exercised through {@link PictureFillFormat}.
 *
 * <p>Verifies picture fill format behavior including stretch mode and image persistence.</p>
 */
class IPictureFillFormatTest {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";

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

    private IPictureFillFormat createPictureFillFormat() {
        return new PictureFillFormat(blipFill, null);
    }

    // --- interface hierarchy ---

    @Test
    void pictureFillFormat_implementsIFillParamSource() {
        IPictureFillFormat pff = createPictureFillFormat();
        assertThat(pff).isInstanceOf(IFillParamSource.class);
    }

    // --- picture fill mode ---

    @Test
    void pictureFillMode_defaultIsStretch() {
        IPictureFillFormat pff = createPictureFillFormat();
        assertThat(pff.getPictureFillMode()).isEqualTo(PictureFillMode.STRETCH);
    }

    @Test
    void pictureFillMode_setTileAndReRead() {
        IPictureFillFormat pff = createPictureFillFormat();
        pff.setPictureFillMode(PictureFillMode.TILE);

        // Re-read from same XML element
        IPictureFillFormat pff2 = new PictureFillFormat(blipFill, null);
        assertThat(pff2.getPictureFillMode()).isEqualTo(PictureFillMode.TILE);
    }

    @Test
    void pictureFillMode_setStretchAndReRead() {
        IPictureFillFormat pff = createPictureFillFormat();
        pff.setPictureFillMode(PictureFillMode.TILE);
        pff.setPictureFillMode(PictureFillMode.STRETCH);

        IPictureFillFormat pff2 = new PictureFillFormat(blipFill, null);
        assertThat(pff2.getPictureFillMode()).isEqualTo(PictureFillMode.STRETCH);
    }

    @Test
    void pictureFillMode_switchToTileRemovesStretch() {
        // Add a stretch element first
        Element stretch = doc.createElementNS(NS_A, "a:stretch");
        blipFill.appendChild(stretch);

        IPictureFillFormat pff = createPictureFillFormat();
        assertThat(pff.getPictureFillMode()).isEqualTo(PictureFillMode.STRETCH);

        pff.setPictureFillMode(PictureFillMode.TILE);
        assertThat(pff.getPictureFillMode()).isEqualTo(PictureFillMode.TILE);
    }

    // --- picture ---

    @Test
    void picture_returnsNullWhenNoBlip() {
        IPictureFillFormat pff = createPictureFillFormat();
        assertThat(pff.getPicture()).isNull();
    }

    @Test
    void picture_returnsPictureWhenBlipExists() {
        Element blip = doc.createElementNS(NS_A, "a:blip");
        blipFill.appendChild(blip);

        IPictureFillFormat pff = createPictureFillFormat();
        assertThat(pff.getPicture()).isNotNull();
    }

    // --- dpi ---

    @Test
    void dpi_defaultIsNegativeOne() {
        IPictureFillFormat pff = createPictureFillFormat();
        assertThat(pff.getDpi()).isEqualTo(-1);
    }

    @Test
    void dpi_setAndGet() {
        IPictureFillFormat pff = createPictureFillFormat();
        pff.setDpi(150);
        assertThat(pff.getDpi()).isEqualTo(150);
    }

    // --- crop properties (XML-backed via a:srcRect) ---

    @Test
    void cropLeft_defaultIsZero() {
        IPictureFillFormat pff = createPictureFillFormat();
        assertThat(pff.getCropLeft()).isEqualTo(0f);
    }

    @Test
    void cropLeft_setAndGet() {
        IPictureFillFormat pff = createPictureFillFormat();
        pff.setCropLeft(10.5f);
        assertThat(pff.getCropLeft()).isCloseTo(10.5f, offset(0.01f));
    }

    @Test
    void cropLeft_persistsInXml() {
        IPictureFillFormat pff = createPictureFillFormat();
        pff.setCropLeft(10.0f);

        // Re-read from same XML
        IPictureFillFormat pff2 = new PictureFillFormat(blipFill, null);
        assertThat(pff2.getCropLeft()).isCloseTo(10.0f, offset(0.01f));
    }

    @Test
    void cropTop_setAndGet() {
        IPictureFillFormat pff = createPictureFillFormat();
        pff.setCropTop(20.0f);
        assertThat(pff.getCropTop()).isCloseTo(20.0f, offset(0.01f));
    }

    @Test
    void cropRight_setAndGet() {
        IPictureFillFormat pff = createPictureFillFormat();
        pff.setCropRight(15.0f);
        assertThat(pff.getCropRight()).isCloseTo(15.0f, offset(0.01f));
    }

    @Test
    void cropBottom_setAndGet() {
        IPictureFillFormat pff = createPictureFillFormat();
        pff.setCropBottom(5.0f);
        assertThat(pff.getCropBottom()).isCloseTo(5.0f, offset(0.01f));
    }

    // --- stretch offset properties (XML-backed via a:fillRect under a:stretch) ---

    @Test
    void stretchOffsetLeft_defaultIsZero() {
        IPictureFillFormat pff = createPictureFillFormat();
        assertThat(pff.getStretchOffsetLeft()).isEqualTo(0f);
    }

    @Test
    void stretchOffsetLeft_setAndGet() {
        IPictureFillFormat pff = createPictureFillFormat();
        pff.setStretchOffsetLeft(5.0f);
        assertThat(pff.getStretchOffsetLeft()).isCloseTo(5.0f, offset(0.01f));
    }

    @Test
    void stretchOffsetLeft_persistsInXml() {
        IPictureFillFormat pff = createPictureFillFormat();
        pff.setStretchOffsetLeft(5.0f);

        IPictureFillFormat pff2 = new PictureFillFormat(blipFill, null);
        assertThat(pff2.getStretchOffsetLeft()).isCloseTo(5.0f, offset(0.01f));
    }

    @Test
    void stretchOffsetTop_setAndGet() {
        IPictureFillFormat pff = createPictureFillFormat();
        pff.setStretchOffsetTop(10.0f);
        assertThat(pff.getStretchOffsetTop()).isCloseTo(10.0f, offset(0.01f));
    }

    @Test
    void stretchOffsetRight_setAndGet() {
        IPictureFillFormat pff = createPictureFillFormat();
        pff.setStretchOffsetRight(-3.5f);
        assertThat(pff.getStretchOffsetRight()).isCloseTo(-3.5f, offset(0.01f));
    }

    @Test
    void stretchOffsetBottom_setAndGet() {
        IPictureFillFormat pff = createPictureFillFormat();
        pff.setStretchOffsetBottom(7.0f);
        assertThat(pff.getStretchOffsetBottom()).isCloseTo(7.0f, offset(0.01f));
    }

    // --- tile offset/scale properties (XML-backed via a:tile) ---

    @Test
    void tileOffsetX_defaultIsZero() {
        IPictureFillFormat pff = createPictureFillFormat();
        assertThat(pff.getTileOffsetX()).isEqualTo(0f);
    }

    @Test
    void tileOffsetX_setAndGet() {
        IPictureFillFormat pff = createPictureFillFormat();
        pff.setTileOffsetX(50.0f);
        assertThat(pff.getTileOffsetX()).isCloseTo(50.0f, offset(0.01f));
    }

    @Test
    void tileOffsetX_persistsInXml() {
        IPictureFillFormat pff = createPictureFillFormat();
        pff.setTileOffsetX(50.0f);

        IPictureFillFormat pff2 = new PictureFillFormat(blipFill, null);
        assertThat(pff2.getTileOffsetX()).isCloseTo(50.0f, offset(0.01f));
    }

    @Test
    void tileOffsetY_setAndGet() {
        IPictureFillFormat pff = createPictureFillFormat();
        pff.setTileOffsetY(-25.0f);
        assertThat(pff.getTileOffsetY()).isCloseTo(-25.0f, offset(0.01f));
    }

    @Test
    void tileScaleX_defaultIs100() {
        IPictureFillFormat pff = createPictureFillFormat();
        assertThat(pff.getTileScaleX()).isCloseTo(100.0f, offset(0.01f));
    }

    @Test
    void tileScaleX_setAndGet() {
        IPictureFillFormat pff = createPictureFillFormat();
        pff.setTileScaleX(200.0f);
        assertThat(pff.getTileScaleX()).isCloseTo(200.0f, offset(0.01f));
    }

    @Test
    void tileScaleY_setAndGet() {
        IPictureFillFormat pff = createPictureFillFormat();
        pff.setTileScaleY(150.0f);
        assertThat(pff.getTileScaleY()).isCloseTo(150.0f, offset(0.01f));
    }

    // --- tile alignment and flip ---

    @Test
    void tileAlignment_defaultIsNotDefined() {
        IPictureFillFormat pff = createPictureFillFormat();
        assertThat(pff.getTileAlignment()).isEqualTo(RectangleAlignment.NOT_DEFINED);
    }

    @Test
    void tileAlignment_setAndGet() {
        IPictureFillFormat pff = createPictureFillFormat();
        pff.setTileAlignment(RectangleAlignment.CENTER);
        assertThat(pff.getTileAlignment()).isEqualTo(RectangleAlignment.CENTER);
    }

    @Test
    void tileAlignment_persistsInXml() {
        IPictureFillFormat pff = createPictureFillFormat();
        pff.setTileAlignment(RectangleAlignment.BOTTOM_RIGHT);

        IPictureFillFormat pff2 = new PictureFillFormat(blipFill, null);
        assertThat(pff2.getTileAlignment()).isEqualTo(RectangleAlignment.BOTTOM_RIGHT);
    }

    @Test
    void tileAlignment_setNotDefinedRemovesAttribute() {
        IPictureFillFormat pff = createPictureFillFormat();
        pff.setTileAlignment(RectangleAlignment.TOP_LEFT);
        assertThat(pff.getTileAlignment()).isEqualTo(RectangleAlignment.TOP_LEFT);

        pff.setTileAlignment(RectangleAlignment.NOT_DEFINED);
        assertThat(pff.getTileAlignment()).isEqualTo(RectangleAlignment.NOT_DEFINED);
    }

    @Test
    void tileFlip_defaultIsNotDefined() {
        IPictureFillFormat pff = createPictureFillFormat();
        assertThat(pff.getTileFlip()).isEqualTo(TileFlip.NOT_DEFINED);
    }

    @Test
    void tileFlip_setAndGet() {
        IPictureFillFormat pff = createPictureFillFormat();
        pff.setTileFlip(TileFlip.FLIP_BOTH);
        assertThat(pff.getTileFlip()).isEqualTo(TileFlip.FLIP_BOTH);
    }

    @Test
    void tileFlip_persistsInXml() {
        IPictureFillFormat pff = createPictureFillFormat();
        pff.setTileFlip(TileFlip.FLIP_X);

        IPictureFillFormat pff2 = new PictureFillFormat(blipFill, null);
        assertThat(pff2.getTileFlip()).isEqualTo(TileFlip.FLIP_X);
    }

    @Test
    void tileFlip_setNotDefinedRemovesAttribute() {
        IPictureFillFormat pff = createPictureFillFormat();
        pff.setTileFlip(TileFlip.FLIP_Y);
        assertThat(pff.getTileFlip()).isEqualTo(TileFlip.FLIP_Y);

        pff.setTileFlip(TileFlip.NOT_DEFINED);
        assertThat(pff.getTileFlip()).isEqualTo(TileFlip.NOT_DEFINED);
    }

    // --- save callback ---

    @Test
    void saveCallback_invokedOnPropertyChange() {
        int[] callCount = {0};
        var pff = new PictureFillFormat(blipFill, () -> callCount[0]++);

        pff.setDpi(96);
        assertThat(callCount[0]).isEqualTo(1);

        pff.setCropLeft(10.0f);
        assertThat(callCount[0]).isEqualTo(2);

        pff.setTileFlip(TileFlip.FLIP_X);
        assertThat(callCount[0]).isEqualTo(3);
    }

    // --- tile setters implicitly switch to tile mode ---

    @Test
    void setTileOffsetX_switchesToTileMode() {
        // Start with stretch
        Element stretch = doc.createElementNS(NS_A, "a:stretch");
        blipFill.appendChild(stretch);

        IPictureFillFormat pff = createPictureFillFormat();
        assertThat(pff.getPictureFillMode()).isEqualTo(PictureFillMode.STRETCH);

        pff.setTileOffsetX(10.0f);
        assertThat(pff.getPictureFillMode()).isEqualTo(PictureFillMode.TILE);
    }

    // --- pre-populated XML reading ---

    @Test
    void cropValues_readFromExistingXml() {
        Element srcRect = doc.createElementNS(NS_A, "a:srcRect");
        srcRect.setAttribute("l", "15000");
        srcRect.setAttribute("t", "20000");
        srcRect.setAttribute("r", "10000");
        srcRect.setAttribute("b", "5000");
        blipFill.appendChild(srcRect);

        IPictureFillFormat pff = createPictureFillFormat();
        assertThat(pff.getCropLeft()).isCloseTo(15.0f, offset(0.01f));
        assertThat(pff.getCropTop()).isCloseTo(20.0f, offset(0.01f));
        assertThat(pff.getCropRight()).isCloseTo(10.0f, offset(0.01f));
        assertThat(pff.getCropBottom()).isCloseTo(5.0f, offset(0.01f));
    }

    @Test
    void tileProperties_readFromExistingXml() {
        Element tile = doc.createElementNS(NS_A, "a:tile");
        tile.setAttribute("tx", "635000");  // 50 points * 12700
        tile.setAttribute("ty", "-317500"); // -25 points * 12700
        tile.setAttribute("sx", "200000");  // 200%
        tile.setAttribute("sy", "150000");  // 150%
        tile.setAttribute("algn", "ctr");
        tile.setAttribute("flip", "xy");
        blipFill.appendChild(tile);

        IPictureFillFormat pff = createPictureFillFormat();
        assertThat(pff.getPictureFillMode()).isEqualTo(PictureFillMode.TILE);
        assertThat(pff.getTileOffsetX()).isCloseTo(50.0f, offset(0.01f));
        assertThat(pff.getTileOffsetY()).isCloseTo(-25.0f, offset(0.01f));
        assertThat(pff.getTileScaleX()).isCloseTo(200.0f, offset(0.01f));
        assertThat(pff.getTileScaleY()).isCloseTo(150.0f, offset(0.01f));
        assertThat(pff.getTileAlignment()).isEqualTo(RectangleAlignment.CENTER);
        assertThat(pff.getTileFlip()).isEqualTo(TileFlip.FLIP_BOTH);
    }
}
