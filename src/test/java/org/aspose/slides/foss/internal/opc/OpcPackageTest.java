package org.aspose.slides.foss.internal.opc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.assertj.core.api.Assertions.*;

class OpcPackageTest {

    @TempDir
    Path tempDir;

    @Test
    void createNewReturnsEmptyPackage() {
        try (var pkg = OpcPackage.createNew()) {
            assertThat(pkg.getPartNames()).isEmpty();
            assertThat(pkg.getSourcePath()).isEmpty();
        }
    }

    @Test
    void setAndGetPartWithBytes() {
        try (var pkg = OpcPackage.createNew()) {
            byte[] data = {1, 2, 3};
            pkg.setPart("test.bin", data);

            assertThat(pkg.getPart("test.bin")).isPresent().hasValue(data);
            assertThat(pkg.hasPart("test.bin")).isTrue();
        }
    }

    @Test
    void setAndGetPartWithString() {
        try (var pkg = OpcPackage.createNew()) {
            pkg.setPart("doc.xml", "<root/>");

            assertThat(pkg.getPart("doc.xml")).isPresent()
                    .hasValue("<root/>".getBytes(StandardCharsets.UTF_8));
        }
    }

    @Test
    void getPartReturnsEmptyForMissing() {
        try (var pkg = OpcPackage.createNew()) {
            assertThat(pkg.getPart("nonexistent")).isEmpty();
        }
    }

    @Test
    void hasPartReturnsFalseForMissing() {
        try (var pkg = OpcPackage.createNew()) {
            assertThat(pkg.hasPart("nonexistent")).isFalse();
        }
    }

    @Test
    void deletePartReturnsTrueWhenExists() {
        try (var pkg = OpcPackage.createNew()) {
            pkg.setPart("a.xml", new byte[]{1});
            assertThat(pkg.deletePart("a.xml")).isTrue();
            assertThat(pkg.hasPart("a.xml")).isFalse();
        }
    }

    @Test
    void deletePartReturnsFalseWhenMissing() {
        try (var pkg = OpcPackage.createNew()) {
            assertThat(pkg.deletePart("missing")).isFalse();
        }
    }

    @Test
    void getPartNamesReturnsAllParts() {
        try (var pkg = OpcPackage.createNew()) {
            pkg.setPart("a.xml", "a");
            pkg.setPart("b.xml", "b");

            assertThat(pkg.getPartNames()).containsExactly("a.xml", "b.xml");
        }
    }

    @Test
    void roundTripThroughFile() throws IOException {
        Path file = tempDir.resolve("test.zip");

        try (var pkg = OpcPackage.createNew()) {
            pkg.setPart("ppt/presentation.xml", "<presentation/>");
            pkg.setPart("data.bin", new byte[]{10, 20, 30});
            pkg.save(file.toString());
        }

        try (var pkg = OpcPackage.open(file.toString())) {
            assertThat(pkg.getSourcePath()).hasValue(file.toString());
            assertThat(pkg.getPartNames()).containsExactlyInAnyOrder(
                    "ppt/presentation.xml", "data.bin");
            assertThat(pkg.getPart("ppt/presentation.xml")).isPresent()
                    .hasValue("<presentation/>".getBytes(StandardCharsets.UTF_8));
            assertThat(pkg.getPart("data.bin")).isPresent()
                    .hasValue(new byte[]{10, 20, 30});
        }
    }

    @Test
    void roundTripThroughStream() throws IOException {
        var baos = new ByteArrayOutputStream();

        try (var pkg = OpcPackage.createNew()) {
            pkg.setPart("content.xml", "hello");
            pkg.save(baos);
        }

        try (var pkg = OpcPackage.open(new ByteArrayInputStream(baos.toByteArray()))) {
            assertThat(pkg.getSourcePath()).isEmpty();
            assertThat(pkg.getPart("content.xml")).isPresent()
                    .hasValue("hello".getBytes(StandardCharsets.UTF_8));
        }
    }

    @Test
    void openNonexistentFileThrowsFileNotFound() {
        assertThatThrownBy(() -> OpcPackage.open("/no/such/file.zip"))
                .isInstanceOf(FileNotFoundException.class)
                .hasMessageContaining("/no/such/file.zip");
    }

    @Test
    void closeClearsState() {
        var pkg = OpcPackage.createNew();
        pkg.setPart("a.xml", "data");
        pkg.close();

        assertThat(pkg.getPartNames()).isEmpty();
        assertThat(pkg.getSourcePath()).isEmpty();
    }

    @Test
    void setPartOverwritesExisting() {
        try (var pkg = OpcPackage.createNew()) {
            pkg.setPart("x.xml", "old");
            pkg.setPart("x.xml", "new");

            assertThat(pkg.getPart("x.xml")).isPresent()
                    .hasValue("new".getBytes(StandardCharsets.UTF_8));
            assertThat(pkg.getPartNames()).hasSize(1);
        }
    }
}
