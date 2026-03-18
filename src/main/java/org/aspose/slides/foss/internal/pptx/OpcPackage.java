package org.aspose.slides.foss.internal.pptx;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Minimal OPC (Open Packaging Conventions) package backed by a ZIP archive.
 *
 * <p>Stores part contents as byte arrays keyed by their URI path (without leading slash).</p>
 */
public final class OpcPackage {

    private final Map<String, byte[]> parts = new LinkedHashMap<>();

    /** Creates an empty package. */
    public OpcPackage() {
    }

    /**
     * Loads a package from a ZIP input stream.
     *
     * @param in the input stream
     * @throws IOException if an I/O error occurs
     */
    public void load(InputStream in) throws IOException {
        parts.clear();
        try (var zis = new ZipInputStream(in)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (!entry.isDirectory()) {
                    parts.put(entry.getName(), zis.readAllBytes());
                }
            }
        }
    }

    /**
     * Saves the package as a ZIP archive to the given output stream.
     *
     * @param out the output stream
     * @throws IOException if an I/O error occurs
     */
    public void save(OutputStream out) throws IOException {
        try (var zos = new ZipOutputStream(out)) {
            for (var e : parts.entrySet()) {
                zos.putNextEntry(new ZipEntry(e.getKey()));
                zos.write(e.getValue());
                zos.closeEntry();
            }
        }
    }

    /** Returns whether a part with the given URI exists. */
    public boolean hasPart(String uri) {
        return parts.containsKey(uri);
    }

    /** Returns the raw bytes of the given part, or {@code null} if not found. */
    public byte[] getPartBytes(String uri) {
        return parts.get(uri);
    }

    /** Sets the raw bytes for the given part URI. */
    public void setPartBytes(String uri, byte[] data) {
        parts.put(uri, data);
    }

    /** Removes a part by URI. */
    public void removePart(String uri) {
        parts.remove(uri);
    }

    /** Returns all part URIs as an unmodifiable set. */
    public Set<String> getPartNames() {
        return java.util.Collections.unmodifiableSet(parts.keySet());
    }

    /** Clears all parts, releasing memory held by byte arrays. */
    public void clear() {
        parts.clear();
    }

    /**
     * Parses the given part as an XML document.
     *
     * @param uri the part URI
     * @return the parsed document, or {@code null} if the part does not exist
     */
    public Document parseXml(String uri) {
        byte[] data = parts.get(uri);
        if (data == null) return null;
        try {
            var factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            return factory.newDocumentBuilder()
                    .parse(new ByteArrayInputStream(data));
        } catch (Exception e) {
            throw new IllegalStateException("Failed to parse XML part: " + uri, e);
        }
    }

    /**
     * Serializes the given XML document and stores it as a part.
     *
     * @param uri the part URI
     * @param doc the XML document
     */
    public void serializeXml(String uri, Document doc) {
        try {
            var transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            var sw = new ByteArrayOutputStream();
            transformer.transform(new DOMSource(doc), new StreamResult(sw));
            parts.put(uri, sw.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Failed to serialize XML part: " + uri, e);
        }
    }

    /**
     * Creates a new empty XML document.
     *
     * @return a new DOM Document
     */
    public static Document newDocument() {
        try {
            return DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .newDocument();
        } catch (javax.xml.parsers.ParserConfigurationException e) {
            throw new IllegalStateException("Failed to create XML document", e);
        }
    }
}
