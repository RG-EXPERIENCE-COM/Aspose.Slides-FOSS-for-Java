package org.aspose.slides.foss.internal.opc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Manages an Open Packaging Conventions (OPC) package.
 *
 * <p>An OPC package is a ZIP archive containing parts (files) organized
 * according to Office Open XML conventions. This class provides:
 * <ul>
 *   <li>Loading from file path or stream</li>
 *   <li>Saving to file path or stream</li>
 *   <li>Part access (get/set/delete)</li>
 *   <li>Preservation of unknown parts for round-trip fidelity</li>
 * </ul>
 */
public final class OpcPackage implements AutoCloseable {

    private final Map<String, byte[]> parts = new LinkedHashMap<>();
    private String sourcePath;

    /**
     * Initialize an empty OPC package.
     * Use {@link #open} or {@link #createNew} factory methods instead.
     */
    private OpcPackage() {
    }

    /**
     * Open an OPC package from a file path.
     *
     * @param path file path to the OPC package (ZIP archive)
     * @return loaded {@code OpcPackage} instance
     * @throws FileNotFoundException if the file does not exist
     * @throws IOException           if an I/O error occurs while reading
     */
    public static OpcPackage open(String path) throws IOException {
        OpcPackage pkg = new OpcPackage();
        Path p = Path.of(path);
        if (!Files.exists(p)) {
            throw new FileNotFoundException("Package file not found: " + path);
        }
        pkg.sourcePath = path;
        pkg.loadFromPath(path);
        return pkg;
    }

    /**
     * Open an OPC package from a binary input stream.
     *
     * @param stream input stream containing a ZIP archive
     * @return loaded {@code OpcPackage} instance
     * @throws IOException if an I/O error occurs while reading
     */
    public static OpcPackage open(InputStream stream) throws IOException {
        OpcPackage pkg = new OpcPackage();
        pkg.loadFromStream(stream);
        return pkg;
    }

    /**
     * Create a new empty OPC package.
     *
     * @return new empty {@code OpcPackage} instance
     */
    public static OpcPackage createNew() {
        return new OpcPackage();
    }

    /**
     * Load all parts from a ZIP file at the given path.
     *
     * @param path file path to the ZIP archive
     * @throws IOException if an I/O error occurs
     */
    void loadFromPath(String path) throws IOException {
        try (InputStream is = Files.newInputStream(Path.of(path));
             ZipInputStream zis = new ZipInputStream(is)) {
            loadFromZipfile(zis);
        }
    }

    /**
     * Load all parts from a ZIP input stream.
     *
     * @param stream the input stream containing a ZIP archive
     * @throws IOException if an I/O error occurs
     */
    void loadFromStream(InputStream stream) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(stream)) {
            loadFromZipfile(zis);
        }
    }

    /**
     * Load all parts from an open {@link ZipInputStream}.
     *
     * @param zis the ZIP input stream to read from
     * @throws IOException if an I/O error occurs
     */
    void loadFromZipfile(ZipInputStream zis) throws IOException {
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            if (!entry.isDirectory()) {
                parts.put(entry.getName(), zis.readAllBytes());
            }
            zis.closeEntry();
        }
    }

    /**
     * Save the OPC package to a file path.
     *
     * @param path file path to write to
     * @throws IOException if an I/O error occurs
     */
    public void save(String path) throws IOException {
        saveToPath(path);
    }

    /**
     * Save the OPC package to an output stream.
     *
     * @param stream output stream to write to
     * @throws IOException if an I/O error occurs
     */
    public void save(OutputStream stream) throws IOException {
        saveToStream(stream);
    }

    /**
     * Save all parts to a ZIP file at the given path.
     *
     * @param path file path to write to
     * @throws IOException if an I/O error occurs
     */
    void saveToPath(String path) throws IOException {
        try (OutputStream os = Files.newOutputStream(Path.of(path));
             ZipOutputStream zos = new ZipOutputStream(os)) {
            saveToZipfile(zos);
        }
    }

    /**
     * Save all parts to a ZIP output stream.
     *
     * @param stream the output stream to write to
     * @throws IOException if an I/O error occurs
     */
    void saveToStream(OutputStream stream) throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(stream)) {
            saveToZipfile(zos);
        }
    }

    /**
     * Save all parts to an open {@link ZipOutputStream}.
     *
     * @param zos the ZIP output stream to write to
     * @throws IOException if an I/O error occurs
     */
    void saveToZipfile(ZipOutputStream zos) throws IOException {
        for (var entry : parts.entrySet()) {
            zos.putNextEntry(new ZipEntry(entry.getKey()));
            zos.write(entry.getValue());
            zos.closeEntry();
        }
    }

    /**
     * Get the content of a part by name.
     *
     * @param partName the part path within the package (e.g., "ppt/presentation.xml")
     * @return part content as bytes, or empty if the part does not exist
     */
    public Optional<byte[]> getPart(String partName) {
        return Optional.ofNullable(parts.get(partName));
    }

    /**
     * Set or update the content of a part with byte array content.
     *
     * @param partName the part path within the package
     * @param content  part content as bytes
     */
    public void setPart(String partName, byte[] content) {
        parts.put(partName, content);
    }

    /**
     * Set or update the content of a part with string content (encoded as UTF-8).
     *
     * @param partName the part path within the package
     * @param content  part content as a string (will be encoded as UTF-8)
     */
    public void setPart(String partName, String content) {
        parts.put(partName, content.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Check if a part exists in the package.
     *
     * @param partName the part path to check
     * @return {@code true} if the part exists, {@code false} otherwise
     */
    public boolean hasPart(String partName) {
        return parts.containsKey(partName);
    }

    /**
     * Delete a part from the package.
     *
     * @param partName the part path to delete
     * @return {@code true} if the part was deleted, {@code false} if it did not exist
     */
    public boolean deletePart(String partName) {
        return parts.remove(partName) != null;
    }

    /**
     * Get a list of all part names in the package.
     *
     * @return list of part paths
     */
    public List<String> getPartNames() {
        return List.copyOf(parts.keySet());
    }

    /**
     * Get the original file path if the package was loaded from a file.
     *
     * @return file path, or empty if loaded from a stream or created new
     */
    public Optional<String> getSourcePath() {
        return Optional.ofNullable(sourcePath);
    }

    /**
     * Close the package and release resources.
     *
     * <p>Clears all in-memory part data. The package should not be used after closing.
     */
    @Override
    public void close() {
        parts.clear();
        sourcePath = null;
    }
}
