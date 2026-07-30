package org.aspose.slides.foss.internal.pptx;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Element;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class SlidePartTest {

    private static final String SLIDE_XML = """
            <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
            <p:sld xmlns:p="http://schemas.openxmlformats.org/presentationml/2006/main"
                   xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main"
                   xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships">
                <p:cSld>
                    <p:spTree>
                        <p:pic>
                            <p:nvPicPr><p:cNvPr id="6" name="Picture 6"/></p:nvPicPr>
                            <p:blipFill><a:blip r:embed="rId4"/></p:blipFill>
                        </p:pic>
                        <p:pic>
                            <p:nvPicPr><p:cNvPr id="8" name="Picture 8"/></p:nvPicPr>
                            <p:blipFill><a:blip r:embed="rId5"/></p:blipFill>
                        </p:pic>
                    </p:spTree>
                </p:cSld>
            </p:sld>
            """;

    private static Element parseSlide() {
        return SlidePart.parseXml(SLIDE_XML.getBytes(StandardCharsets.UTF_8)).getDocumentElement();
    }

    private static String embedOf(Element root, int picIndex) {
        var pics = root.getElementsByTagNameNS(
                "http://schemas.openxmlformats.org/presentationml/2006/main", "pic");
        var blips = ((Element) pics.item(picIndex)).getElementsByTagNameNS(
                "http://schemas.openxmlformats.org/drawingml/2006/main", "blip");
        return ((Element) blips.item(0)).getAttributeNS(
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships", "embed");
    }

    /**
     * Source {@code .rels} entries are frequently stored out of numeric order, so renumbering
     * during a clone produces a swap cycle. Each attribute must be remapped exactly once.
     */
    @Test
    void appliesSwapCycleExactlyOnce() {
        Element root = parseSlide();

        SlidePart.updateRidReferences(root, Map.of("rId4", "rId5", "rId5", "rId4"));

        assertThat(embedOf(root, 0)).isEqualTo("rId5");
        assertThat(embedOf(root, 1)).isEqualTo("rId4");
    }

    @Test
    void doesNotChainThroughMappedValues() {
        Element root = parseSlide();

        SlidePart.updateRidReferences(root, Map.of("rId4", "rId5", "rId5", "rId6"));

        assertThat(embedOf(root, 0)).isEqualTo("rId5");
        assertThat(embedOf(root, 1)).isEqualTo("rId6");
    }

    @Test
    void leavesUnmappedIdsUntouched() {
        Element root = parseSlide();

        SlidePart.updateRidReferences(root, Map.of("rId9", "rId1"));

        assertThat(embedOf(root, 0)).isEqualTo("rId4");
        assertThat(embedOf(root, 1)).isEqualTo("rId5");
    }
}
