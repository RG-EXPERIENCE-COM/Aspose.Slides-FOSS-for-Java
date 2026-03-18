package org.aspose.slides.foss.internal.pptx;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.*;

class LayoutSlidePartTest {

    private static final String LAYOUT_XML = """
            <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
            <p:sldLayout xmlns:p="http://schemas.openxmlformats.org/presentationml/2006/main"
                         xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main"
                         xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"
                         type="titleOnly">
                <p:cSld name="Title Only">
                    <p:spTree/>
                </p:cSld>
            </p:sldLayout>
            """;

    private static final String LAYOUT_XML_NO_TYPE = """
            <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
            <p:sldLayout xmlns:p="http://schemas.openxmlformats.org/presentationml/2006/main">
                <p:cSld name="Custom Layout">
                    <p:spTree/>
                </p:cSld>
            </p:sldLayout>
            """;

    private static final String RELS_XML = """
            <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
            <Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
                <Relationship Id="rId1"
                    Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/slideMaster"
                    Target="../slideMasters/slideMaster1.xml"/>
            </Relationships>
            """;

    private OpcPackage createPackage(String partName, String xml, String relsXml) {
        var pkg = new OpcPackage();
        pkg.setPartBytes(partName, xml.getBytes(StandardCharsets.UTF_8));
        if (relsXml != null) {
            String relsUri = computeRelsUri(partName);
            pkg.setPartBytes(relsUri, relsXml.getBytes(StandardCharsets.UTF_8));
        }
        return pkg;
    }

    private static String computeRelsUri(String uri) {
        int lastSlash = uri.lastIndexOf('/');
        if (lastSlash < 0) {
            return "_rels/" + uri + ".rels";
        }
        String dir = uri.substring(0, lastSlash);
        String fileName = uri.substring(lastSlash + 1);
        return dir + "/_rels/" + fileName + ".rels";
    }

    @Test
    void getPartNameReturnsConstructorValue() {
        var pkg = createPackage("ppt/slideLayouts/slideLayout1.xml", LAYOUT_XML, null);
        var part = new LayoutSlidePart(pkg, "ppt/slideLayouts/slideLayout1.xml");
        assertThat(part.getPartName()).isEqualTo("ppt/slideLayouts/slideLayout1.xml");
    }

    @Test
    void getNameReturnsLayoutName() {
        var pkg = createPackage("ppt/slideLayouts/slideLayout1.xml", LAYOUT_XML, null);
        var part = new LayoutSlidePart(pkg, "ppt/slideLayouts/slideLayout1.xml");
        assertThat(part.getName()).isEqualTo("Title Only");
    }

    @Test
    void setNameUpdatesLayoutName() {
        var pkg = createPackage("ppt/slideLayouts/slideLayout1.xml", LAYOUT_XML, null);
        var part = new LayoutSlidePart(pkg, "ppt/slideLayouts/slideLayout1.xml");
        part.setName("New Name");
        assertThat(part.getName()).isEqualTo("New Name");
    }

    @Test
    void getLayoutTypeRawReturnsTypeAttribute() {
        var pkg = createPackage("ppt/slideLayouts/slideLayout1.xml", LAYOUT_XML, null);
        var part = new LayoutSlidePart(pkg, "ppt/slideLayouts/slideLayout1.xml");
        assertThat(part.getLayoutTypeRaw()).isEqualTo("titleOnly");
    }

    @Test
    void getLayoutTypeRawDefaultsToCust() {
        var pkg = createPackage("ppt/slideLayouts/slideLayout1.xml", LAYOUT_XML_NO_TYPE, null);
        var part = new LayoutSlidePart(pkg, "ppt/slideLayouts/slideLayout1.xml");
        assertThat(part.getLayoutTypeRaw()).isEqualTo("cust");
    }

    @Test
    void getLayoutTypeValueMapsTitleOnly() {
        var pkg = createPackage("ppt/slideLayouts/slideLayout1.xml", LAYOUT_XML, null);
        var part = new LayoutSlidePart(pkg, "ppt/slideLayouts/slideLayout1.xml");
        assertThat(part.getLayoutTypeValue()).isEqualTo("TitleOnly");
    }

    @Test
    void getLayoutTypeValueDefaultsToCustom() {
        var pkg = createPackage("ppt/slideLayouts/slideLayout1.xml", LAYOUT_XML_NO_TYPE, null);
        var part = new LayoutSlidePart(pkg, "ppt/slideLayouts/slideLayout1.xml");
        assertThat(part.getLayoutTypeValue()).isEqualTo("Custom");
    }

    @Test
    void getMasterPartNameResolvesRelativePath() {
        var pkg = createPackage("ppt/slideLayouts/slideLayout1.xml", LAYOUT_XML, RELS_XML);
        var part = new LayoutSlidePart(pkg, "ppt/slideLayouts/slideLayout1.xml");
        assertThat(part.getMasterPartName())
                .isEqualTo("ppt/slideMasters/slideMaster1.xml");
    }

    @Test
    void getMasterPartNameReturnsEmptyWhenNoRels() {
        var pkg = createPackage("ppt/slideLayouts/slideLayout1.xml", LAYOUT_XML, null);
        var part = new LayoutSlidePart(pkg, "ppt/slideLayouts/slideLayout1.xml");
        assertThat(part.getMasterPartName()).isNull();
    }

    @Test
    void constructorThrowsForMissingPart() {
        var pkg = new OpcPackage();
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new LayoutSlidePart(pkg, "missing.xml"))
                .withMessageContaining("missing.xml");
    }

    @Test
    void resolveTargetHandlesAbsolutePath() {
        var pkg = createPackage("ppt/slideLayouts/slideLayout1.xml", LAYOUT_XML, null);
        var part = new LayoutSlidePart(pkg, "ppt/slideLayouts/slideLayout1.xml");
        assertThat(part.resolveTarget("/ppt/slideMasters/slideMaster1.xml"))
                .isEqualTo("ppt/slideMasters/slideMaster1.xml");
    }

    @Test
    void resolveTargetHandlesRelativePathWithDotDot() {
        var pkg = createPackage("ppt/slideLayouts/slideLayout1.xml", LAYOUT_XML, null);
        var part = new LayoutSlidePart(pkg, "ppt/slideLayouts/slideLayout1.xml");
        assertThat(part.resolveTarget("../slideMasters/slideMaster1.xml"))
                .isEqualTo("ppt/slideMasters/slideMaster1.xml");
    }

    @Test
    void saveSerializesBackToPackage() {
        var pkg = createPackage("ppt/slideLayouts/slideLayout1.xml", LAYOUT_XML, null);
        var part = new LayoutSlidePart(pkg, "ppt/slideLayouts/slideLayout1.xml");
        part.setName("Modified");
        part.save();
        // Reload and verify
        var reloaded = new LayoutSlidePart(pkg, "ppt/slideLayouts/slideLayout1.xml");
        assertThat(reloaded.getName()).isEqualTo("Modified");
    }
}
