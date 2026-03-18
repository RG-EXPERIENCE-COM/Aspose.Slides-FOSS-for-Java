package org.aspose.slides.foss.integration;
import org.aspose.slides.foss.*;

import org.aspose.slides.foss.export.SaveFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for DocumentProperties: core + custom properties.
 */
class DocumentPropertiesTest implements AutoCloseable {

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

    // --- test_core_properties ---

    @Test
    void testCoreProperties() throws IOException {
        try (var pres = new Presentation()) {
            IDocumentProperties props = pres.getDocumentProperties();
            props.setTitle("My Presentation");
            props.setSubject("Demo Subject");
            props.setAuthor("John Doe");
            props.setKeywords("demo, test");
            props.setCategory("Examples");

            try (var pres2 = saveAndReopen(pres)) {
                IDocumentProperties p2 = pres2.getDocumentProperties();
                assertThat(p2.getTitle()).isEqualTo("My Presentation");
                assertThat(p2.getSubject()).isEqualTo("Demo Subject");
                assertThat(p2.getAuthor()).isEqualTo("John Doe");
                assertThat(p2.getKeywords()).isEqualTo("demo, test");
                assertThat(p2.getCategory()).isEqualTo("Examples");
            }
        }
    }

    // --- test_custom_string_property ---

    @Test
    void testCustomStringProperty() throws IOException {
        try (var pres = new Presentation()) {
            pres.getDocumentProperties().setCustomPropertyValue("MyProp", "hello");

            try (var pres2 = saveAndReopen(pres)) {
                List<Object> out = new ArrayList<>();
                out.add(null);
                pres2.getDocumentProperties().getCustomPropertyValue("MyProp", out);
                assertThat(out.get(0)).isEqualTo("hello");
            }
        }
    }

    // --- test_custom_int_property ---

    @Test
    void testCustomIntProperty() throws IOException {
        try (var pres = new Presentation()) {
            pres.getDocumentProperties().setCustomPropertyValue("Count", 42);

            try (var pres2 = saveAndReopen(pres)) {
                List<Object> out = new ArrayList<>();
                out.add(null);
                pres2.getDocumentProperties().getCustomPropertyValue("Count", out);
                assertThat(out.get(0)).isEqualTo(42);
            }
        }
    }

    // --- test_remove_custom_property ---

    @Test
    void testRemoveCustomProperty() {
        try (Presentation pres = new Presentation()) {
            IDocumentProperties props = pres.getDocumentProperties();
            props.setCustomPropertyValue("A", "val");
            props.setCustomPropertyValue("B", "val");
            assertThat(props.getCountOfCustomProperties()).isEqualTo(2);

            props.removeCustomProperty("A");
            assertThat(props.getCountOfCustomProperties()).isEqualTo(1);
            assertThat(props.containsCustomProperty("A")).isFalse();
            assertThat(props.containsCustomProperty("B")).isTrue();
        }
    }
}
