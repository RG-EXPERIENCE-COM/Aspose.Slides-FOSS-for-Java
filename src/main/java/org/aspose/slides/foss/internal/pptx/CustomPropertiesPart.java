package org.aspose.slides.foss.internal.pptx;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Reads and writes custom properties from {@code docProps/custom.xml} in an OPC package.
 *
 * <p>Each custom property is stored as:</p>
 * <pre>{@code
 * <property fmtid="{D5CDD505-2E9C-101B-9397-08002B2CF9AE}" pid="N" name="...">
 *     <vt:type>value</vt:type>
 * </property>
 * }</pre>
 *
 * <p>Type mapping: {@code String→lpwstr}, {@code Integer→i4}, {@code Double→r8},
 * {@code Boolean→bool}, {@code OffsetDateTime→filetime}.</p>
 *
 * <p>PIDs start at 2. The file is created on demand only when custom properties are added.</p>
 */
public final class CustomPropertiesPart {

    /** The part name inside the OPC package. */
    public static final String partName = "docProps/custom.xml";

    private static final String NS_CUSTOM = "http://schemas.openxmlformats.org/officeDocument/2006/custom-properties";
    private static final String NS_VT = "http://schemas.openxmlformats.org/officeDocument/2006/docPropsVTypes";
    private static final String NS_CONTENT_TYPES = "http://schemas.openxmlformats.org/package/2006/content-types";
    private static final String NS_RELATIONSHIPS = "http://schemas.openxmlformats.org/package/2006/relationships";
    private static final String FMTID = "{D5CDD505-2E9C-101B-9397-08002B2CF9AE}";
    private static final String REL_TYPE_CUSTOM_PROPERTIES =
            "http://schemas.openxmlformats.org/officeDocument/2006/relationships/custom-properties";
    private static final String CUSTOM_PROPERTIES_CONTENT_TYPE =
            "application/vnd.openxmlformats-officedocument.custom-properties+xml";

    private static final DateTimeFormatter FILETIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private final OpcPackage pkg;
    private final Map<String, Object> properties = new LinkedHashMap<>();
    private boolean dirty;

    /**
     * Creates a part backed by the given package and immediately parses it.
     *
     * @param pkg the OPC package
     */
    public CustomPropertiesPart(OpcPackage pkg) {
        this.pkg = pkg;
        parse();
    }

    /**
     * Parses {@code docProps/custom.xml} from the package, populating the property map.
     */
    public void parse() {
        Document doc = pkg.parseXml(partName);
        if (doc == null) return;
        Element root = doc.getDocumentElement();
        NodeList propList = root.getElementsByTagNameNS(NS_CUSTOM, "property");
        for (int i = 0; i < propList.getLength(); i++) {
            Element prop = (Element) propList.item(i);
            String name = prop.getAttribute("name");
            if (name == null || name.isEmpty()) {
                continue;
            }
            Object value = readValue(prop);
            if (value != null) {
                properties.put(name, value);
            }
        }
    }

    /**
     * Reads a typed value from a property element.
     *
     * <p>Inspects the first child element's local name to determine the type:
     * {@code lpwstr} → String, {@code i4} → Integer, {@code r8} → Double,
     * {@code bool} → Boolean, {@code filetime} → OffsetDateTime.</p>
     *
     * @param prop the property element
     * @return the parsed value, or {@code null} if no recognized child element is found
     */
    public Object readValue(Element prop) {
        NodeList children = prop.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i) instanceof Element el) {
                String localName = el.getLocalName();
                String text = el.getTextContent();
                if (text == null) text = "";
                return switch (localName) {
                    case "lpwstr" -> text;
                    case "i4" -> {
                        try {
                            yield Integer.parseInt(text.strip());
                        } catch (NumberFormatException e) {
                            yield 0;
                        }
                    }
                    case "r8" -> {
                        try {
                            yield Double.parseDouble(text.strip());
                        } catch (NumberFormatException e) {
                            yield 0.0;
                        }
                    }
                    case "bool" -> {
                        String lower = text.strip().toLowerCase(java.util.Locale.ROOT);
                        yield "true".equals(lower) || "1".equals(lower);
                    }
                    case "filetime" -> parseFiletime(text);
                    default -> text;
                };
            }
        }
        return null;
    }

    /**
     * Parses a filetime string (ISO 8601 format in OOXML) to an {@link OffsetDateTime}.
     *
     * @param text the filetime string, may be {@code null} or empty
     * @return the parsed datetime, or {@code null} if the text is null, empty, or unparseable
     */
    public OffsetDateTime parseFiletime(String text) {
        if (text == null || text.isBlank()) {
            return null;
        }
        try {
            String normalized = text.strip();
            if (normalized.endsWith("Z")) {
                normalized = normalized.substring(0, normalized.length() - 1) + "+00:00";
            }
            return OffsetDateTime.parse(normalized);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Serializes custom properties back to the package.
     *
     * <p>Only writes if the part has been modified. If no properties remain,
     * the part is removed from the package. Otherwise, the part is written and
     * content type and relationship entries are ensured.</p>
     */
    public void save() {
        if (!dirty) return;

        if (properties.isEmpty()) {
            pkg.removePart(partName);
            dirty = false;
            return;
        }

        Document doc = OpcPackage.newDocument();
        Element root = doc.createElementNS(NS_CUSTOM, "Properties");
        root.setAttribute("xmlns:vt", NS_VT);
        doc.appendChild(root);

        int pid = 2;
        for (var entry : properties.entrySet()) {
            Element prop = doc.createElementNS(NS_CUSTOM, "property");
            prop.setAttribute("fmtid", FMTID);
            prop.setAttribute("pid", String.valueOf(pid++));
            prop.setAttribute("name", entry.getKey());
            writeValue(doc, prop, entry.getValue());
            root.appendChild(prop);
        }

        pkg.serializeXml(partName, doc);
        ensureContentType();
        ensureRelationship();
        dirty = false;
    }

    /**
     * Writes a typed value element as a child of the given property element.
     *
     * <p>Type mapping: {@code Boolean→vt:bool}, {@code Integer→vt:i4},
     * {@code Double→vt:r8}, {@code OffsetDateTime→vt:filetime},
     * everything else → {@code vt:lpwstr}.</p>
     *
     * @param doc   the owning document
     * @param prop  the property element to append to
     * @param value the value to write
     */
    public void writeValue(Document doc, Element prop, Object value) {
        Element vtEl;
        if (value instanceof Boolean boolVal) {
            vtEl = doc.createElementNS(NS_VT, "vt:bool");
            vtEl.setTextContent(boolVal ? "true" : "false");
        } else if (value instanceof Integer intVal) {
            vtEl = doc.createElementNS(NS_VT, "vt:i4");
            vtEl.setTextContent(intVal.toString());
        } else if (value instanceof Double dblVal) {
            vtEl = doc.createElementNS(NS_VT, "vt:r8");
            vtEl.setTextContent(dblVal.toString());
        } else if (value instanceof OffsetDateTime dtVal) {
            vtEl = doc.createElementNS(NS_VT, "vt:filetime");
            var utc = dtVal.withOffsetSameInstant(ZoneOffset.UTC);
            vtEl.setTextContent(utc.format(FILETIME_FORMATTER));
        } else {
            vtEl = doc.createElementNS(NS_VT, "vt:lpwstr");
            vtEl.setTextContent(value != null ? value.toString() : "");
        }
        prop.appendChild(vtEl);
    }

    /**
     * Ensures {@code [Content_Types].xml} has an override entry for {@code /docProps/custom.xml}.
     *
     * <p>If the content types part does not exist or already contains the override, this is a no-op.</p>
     */
    public void ensureContentType() {
        Document ctDoc = pkg.parseXml("[Content_Types].xml");
        if (ctDoc == null) return;

        Element ctRoot = ctDoc.getDocumentElement();
        NodeList overrides = ctRoot.getElementsByTagNameNS(NS_CONTENT_TYPES, "Override");
        for (int i = 0; i < overrides.getLength(); i++) {
            Element override = (Element) overrides.item(i);
            if ("/docProps/custom.xml".equals(override.getAttribute("PartName"))) {
                return;
            }
        }

        Element newOverride = ctDoc.createElementNS(NS_CONTENT_TYPES, "Override");
        newOverride.setAttribute("PartName", "/docProps/custom.xml");
        newOverride.setAttribute("ContentType", CUSTOM_PROPERTIES_CONTENT_TYPE);
        ctRoot.appendChild(newOverride);
        pkg.serializeXml("[Content_Types].xml", ctDoc);
    }

    /**
     * Ensures {@code _rels/.rels} has a relationship entry for {@code docProps/custom.xml}.
     *
     * <p>If the relationships part does not exist or already contains the relationship, this is a no-op.
     * A new relationship ID is generated as {@code rId(max+1)}.</p>
     */
    public void ensureRelationship() {
        Document relsDoc = pkg.parseXml("_rels/.rels");
        if (relsDoc == null) return;

        Element relsRoot = relsDoc.getDocumentElement();
        NodeList rels = relsRoot.getChildNodes();

        int maxId = 0;
        for (int i = 0; i < rels.getLength(); i++) {
            if (rels.item(i) instanceof Element rel) {
                if (REL_TYPE_CUSTOM_PROPERTIES.equals(rel.getAttribute("Type"))) {
                    return;
                }
                String rid = rel.getAttribute("Id");
                if (rid != null && rid.startsWith("rId")) {
                    try {
                        maxId = Math.max(maxId, Integer.parseInt(rid.substring(3)));
                    } catch (NumberFormatException ignored) {
                        // Non-numeric value; use default
                    }
                }
            }
        }

        Element newRel = relsDoc.createElementNS(NS_RELATIONSHIPS, "Relationship");
        newRel.setAttribute("Id", "rId" + (maxId + 1));
        newRel.setAttribute("Type", REL_TYPE_CUSTOM_PROPERTIES);
        newRel.setAttribute("Target", "docProps/custom.xml");
        relsRoot.appendChild(newRel);
        pkg.serializeXml("_rels/.rels", relsDoc);
    }

    /** Returns the number of custom properties. */
    public int getCount() {
        return properties.size();
    }

    /**
     * Gets a property value by name.
     *
     * @param name the property name
     * @return the value, or {@code null} if not found
     */
    public Object getValue(String name) {
        return properties.get(name);
    }

    /**
     * Sets a property value by name.
     *
     * @param name  the property name
     * @param value the value to set
     */
    public void setValue(String name, Object value) {
        properties.put(name, value);
        dirty = true;
    }

    /**
     * Returns the property name at the given index.
     *
     * @param index the zero-based index
     * @return the property name
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public String getName(int index) {
        var names = new ArrayList<>(properties.keySet());
        if (index < 0 || index >= names.size()) {
            throw new IndexOutOfBoundsException("Index " + index + " out of range");
        }
        return names.get(index);
    }

    /**
     * Removes a property by name.
     *
     * @param name the property name
     * @return {@code true} if the property was removed
     */
    public boolean remove(String name) {
        if (properties.containsKey(name)) {
            properties.remove(name);
            dirty = true;
            return true;
        }
        return false;
    }

    /**
     * Returns whether a property with the given name exists.
     *
     * @param name the property name
     * @return {@code true} if the property exists
     */
    public boolean contains(String name) {
        return properties.containsKey(name);
    }

    /** Removes all custom properties. */
    public void clear() {
        if (!properties.isEmpty()) {
            properties.clear();
            dirty = true;
        }
    }
}
