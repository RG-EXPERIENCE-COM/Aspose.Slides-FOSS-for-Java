package org.aspose.slides.foss.internal.opc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.*;

class ContentTypesManagerTest {

    private OpcPackage pkg;

    @BeforeEach
    void setUp() {
        pkg = OpcPackage.createNew();
    }

    @Test
    void newPackageCreatesDefaultExtensions() {
        var ct = new ContentTypesManager(pkg);

        assertThat(ct.getContentType("/some/file.rels"))
                .hasValue("application/vnd.openxmlformats-package.relationships+xml");
        assertThat(ct.getContentType("/some/file.xml"))
                .hasValue("application/xml");
    }

    @Test
    void addOverrideAndRetrieve() {
        var ct = new ContentTypesManager(pkg);
        ct.addOverride("/ppt/slides/slide1.xml",
                ContentTypesManager.CONTENT_TYPES.get("slide"));

        assertThat(ct.getContentType("/ppt/slides/slide1.xml"))
                .hasValue("application/vnd.openxmlformats-officedocument.presentationml.slide+xml");
    }

    @Test
    void overrideTakesPrecedenceOverDefault() {
        var ct = new ContentTypesManager(pkg);
        ct.addOverride("/ppt/presentation.xml", "custom/type");

        // Override should win over .xml default
        assertThat(ct.getContentType("/ppt/presentation.xml"))
                .hasValue("custom/type");
    }

    @Test
    void addOverrideUpdatesExisting() {
        var ct = new ContentTypesManager(pkg);
        ct.addOverride("/ppt/slides/slide1.xml", "type/first");
        ct.addOverride("/ppt/slides/slide1.xml", "type/second");

        assertThat(ct.getContentType("/ppt/slides/slide1.xml"))
                .hasValue("type/second");
    }

    @Test
    void removeOverrideReturnsTrue() {
        var ct = new ContentTypesManager(pkg);
        ct.addOverride("/ppt/slides/slide1.xml", "some/type");

        assertThat(ct.removeOverride("/ppt/slides/slide1.xml")).isTrue();
        assertThat(ct.getContentType("/ppt/slides/slide1.xml"))
                .hasValue("application/xml"); // falls back to default
    }

    @Test
    void removeOverrideReturnsFalseWhenNotPresent() {
        var ct = new ContentTypesManager(pkg);

        assertThat(ct.removeOverride("/nonexistent/part.xml")).isFalse();
    }

    @Test
    void getContentTypeReturnsEmptyForUnknownExtension() {
        var ct = new ContentTypesManager(pkg);

        assertThat(ct.getContentType("/some/file.unknown")).isEmpty();
    }

    @Test
    void partNameNormalizationAddsLeadingSlash() {
        var ct = new ContentTypesManager(pkg);
        ct.addOverride("ppt/slides/slide1.xml", "some/type");

        // Should be retrievable with or without leading slash
        assertThat(ct.getContentType("/ppt/slides/slide1.xml"))
                .hasValue("some/type");
        assertThat(ct.getContentType("ppt/slides/slide1.xml"))
                .hasValue("some/type");
    }

    @Test
    void addDefaultExtensionAndRetrieve() {
        var ct = new ContentTypesManager(pkg);
        ct.addDefaultExtension("png", "image/png");

        assertThat(ct.getContentType("/ppt/media/image1.png"))
                .hasValue("image/png");
    }

    @Test
    void saveAndReload() {
        var ct = new ContentTypesManager(pkg);
        ct.addOverride("/ppt/slides/slide1.xml",
                ContentTypesManager.CONTENT_TYPES.get("slide"));
        ct.addDefaultExtension("png", "image/png");
        ct.save();

        // Reload from same package
        var ct2 = new ContentTypesManager(pkg);
        assertThat(ct2.getContentType("/ppt/slides/slide1.xml"))
                .hasValue("application/vnd.openxmlformats-officedocument.presentationml.slide+xml");
        assertThat(ct2.getContentType("/some/image.png"))
                .hasValue("image/png");
    }

    @Test
    void loadFromExistingContentTypesXml() {
        String xml = """
                <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
                <Types xmlns="http://schemas.openxmlformats.org/package/2006/content-types">
                  <Default Extension="rels" ContentType="application/vnd.openxmlformats-package.relationships+xml"/>
                  <Default Extension="xml" ContentType="application/xml"/>
                  <Override PartName="/ppt/presentation.xml" ContentType="application/vnd.openxmlformats-officedocument.presentationml.presentation.main+xml"/>
                </Types>
                """;
        pkg.setPart("[Content_Types].xml", xml.getBytes(StandardCharsets.UTF_8));

        var ct = new ContentTypesManager(pkg);
        assertThat(ct.getContentType("/ppt/presentation.xml"))
                .hasValue("application/vnd.openxmlformats-officedocument.presentationml.presentation.main+xml");
        assertThat(ct.getContentType("/some/file.rels"))
                .hasValue("application/vnd.openxmlformats-package.relationships+xml");
    }

    @Test
    void contentTypesMapContainsExpectedEntries() {
        assertThat(ContentTypesManager.CONTENT_TYPES)
                .containsKey("presentation")
                .containsKey("slide")
                .containsKey("theme")
                .containsKey("commentAuthors")
                .hasSize(15);
    }

    @Test
    void ctNamespaceConstant() {
        assertThat(ContentTypesManager.CT_NAMESPACE)
                .isEqualTo("http://schemas.openxmlformats.org/package/2006/content-types");
        assertThat(ContentTypesManager.CT_NS)
                .isEqualTo("{http://schemas.openxmlformats.org/package/2006/content-types}");
    }
}
