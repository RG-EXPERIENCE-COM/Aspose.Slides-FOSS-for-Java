package org.aspose.slides.foss.internal.pptx;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.Map;
import java.util.Optional;

/**
 * Manages the {@code [Content_Types].xml} file in an OPC package.
 *
 * <p>This class provides methods to:
 * <ul>
 *   <li>Parse existing content types</li>
 *   <li>Add overrides for specific parts</li>
 *   <li>Add default extensions</li>
 *   <li>Serialize back to XML</li>
 * </ul>
 */
public final class ContentTypesManager {

    /** Content Types XML namespace. */
    public static final String CT_NAMESPACE =
            "http://schemas.openxmlformats.org/package/2006/content-types";

    /** Content Types namespace wrapped in braces for qualified element names. */
    public static final String CT_NS = "{" + CT_NAMESPACE + "}";

    /** Common PPTX content types keyed by logical name. */
    public static final Map<String, String> CONTENT_TYPES = Map.ofEntries(
            Map.entry("presentation",
                    "application/vnd.openxmlformats-officedocument.presentationml.presentation.main+xml"),
            Map.entry("presentation_macro",
                    "application/vnd.ms-powerpoint.presentation.macroEnabled.main+xml"),
            Map.entry("slide",
                    "application/vnd.openxmlformats-officedocument.presentationml.slide+xml"),
            Map.entry("slide_layout",
                    "application/vnd.openxmlformats-officedocument.presentationml.slideLayout+xml"),
            Map.entry("slide_master",
                    "application/vnd.openxmlformats-officedocument.presentationml.slideMaster+xml"),
            Map.entry("notes_slide",
                    "application/vnd.openxmlformats-officedocument.presentationml.notesSlide+xml"),
            Map.entry("notes_master",
                    "application/vnd.openxmlformats-officedocument.presentationml.notesMaster+xml"),
            Map.entry("handout_master",
                    "application/vnd.openxmlformats-officedocument.presentationml.handoutMaster+xml"),
            Map.entry("theme",
                    "application/vnd.openxmlformats-officedocument.theme+xml"),
            Map.entry("core_properties",
                    "application/vnd.openxmlformats-package.core-properties+xml"),
            Map.entry("extended_properties",
                    "application/vnd.openxmlformats-officedocument.extended-properties+xml"),
            Map.entry("chart",
                    "application/vnd.openxmlformats-officedocument.drawingml.chart+xml"),
            Map.entry("chartsheet",
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.chartsheet+xml"),
            Map.entry("comments",
                    "application/vnd.openxmlformats-officedocument.presentationml.comments+xml"),
            Map.entry("commentAuthors",
                    "application/vnd.openxmlformats-officedocument.presentationml.commentAuthors+xml")
    );

    private static final String PART_NAME = "[Content_Types].xml";

    private final OpcPackage pkg;
    private Document doc;
    private Element root;

    /**
     * Creates a content types manager for the given OPC package.
     *
     * @param pkg the OPC package to manage content types for
     */
    public ContentTypesManager(OpcPackage pkg) {
        this.pkg = pkg;
        load();
    }

    /**
     * Loads and parses the {@code [Content_Types].xml} from the package.
     */
    private void load() {
        doc = pkg.parseXml(PART_NAME);
        if (doc != null) {
            root = doc.getDocumentElement();
        } else {
            doc = OpcPackage.newDocument();
            root = doc.createElementNS(CT_NAMESPACE, "Types");
            doc.appendChild(root);
            addDefaultExtension("rels", "application/vnd.openxmlformats-package.relationships+xml");
            addDefaultExtension("xml", "application/xml");
        }
    }

    /**
     * Adds a default content type mapping for a file extension.
     *
     * @param extension   the file extension (without leading dot)
     * @param contentType the MIME content type
     */
    public void addDefaultExtension(String extension, String contentType) {
        Element def = doc.createElementNS(CT_NAMESPACE, "Default");
        def.setAttribute("Extension", extension);
        def.setAttribute("ContentType", contentType);
        root.appendChild(def);
    }

    /**
     * Adds or updates a content type override for a specific part.
     *
     * @param partName    the part path (e.g., {@code /ppt/slides/slide1.xml})
     * @param contentType the MIME content type
     */
    public void addOverride(String partName, String contentType) {
        String normalized = normalizePartName(partName);

        // Check if override already exists
        NodeList overrides = root.getElementsByTagNameNS(CT_NAMESPACE, "Override");
        for (int i = 0; i < overrides.getLength(); i++) {
            Element el = (Element) overrides.item(i);
            if (normalized.equals(el.getAttribute("PartName"))) {
                el.setAttribute("ContentType", contentType);
                return;
            }
        }

        // Add new override
        Element override = doc.createElementNS(CT_NAMESPACE, "Override");
        override.setAttribute("PartName", normalized);
        override.setAttribute("ContentType", contentType);
        root.appendChild(override);
    }

    /**
     * Removes a content type override for a specific part.
     *
     * @param partName the part path
     * @return {@code true} if the override was removed, {@code false} if it did not exist
     */
    public boolean removeOverride(String partName) {
        String normalized = normalizePartName(partName);

        NodeList overrides = root.getElementsByTagNameNS(CT_NAMESPACE, "Override");
        for (int i = 0; i < overrides.getLength(); i++) {
            Element el = (Element) overrides.item(i);
            if (normalized.equals(el.getAttribute("PartName"))) {
                root.removeChild(el);
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the content type for a specific part.
     *
     * <p>Checks overrides first, then falls back to default extension mappings.</p>
     *
     * @param partName the part path
     * @return the content type, or empty if not found
     */
    public Optional<String> getContentType(String partName) {
        String normalized = normalizePartName(partName);

        // Check overrides first
        NodeList overrides = root.getElementsByTagNameNS(CT_NAMESPACE, "Override");
        for (int i = 0; i < overrides.getLength(); i++) {
            Element el = (Element) overrides.item(i);
            if (normalized.equals(el.getAttribute("PartName"))) {
                return Optional.of(el.getAttribute("ContentType"));
            }
        }

        // Fall back to defaults based on extension
        String ext = "";
        int dotIdx = normalized.lastIndexOf('.');
        if (dotIdx >= 0) {
            ext = normalized.substring(dotIdx + 1);
        }

        NodeList defaults = root.getElementsByTagNameNS(CT_NAMESPACE, "Default");
        for (int i = 0; i < defaults.getLength(); i++) {
            Element el = (Element) defaults.item(i);
            if (ext.equals(el.getAttribute("Extension"))) {
                return Optional.of(el.getAttribute("ContentType"));
            }
        }

        return Optional.empty();
    }

    /**
     * Saves the content types back to the package.
     */
    public void save() {
        pkg.serializeXml(PART_NAME, doc);
    }

    private static String normalizePartName(String partName) {
        return partName.startsWith("/") ? partName : "/" + partName;
    }
}
