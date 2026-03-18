package org.aspose.slides.foss.internal.pptx;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

/**
 * Reads and writes core properties from {@code docProps/core.xml} in an OPC package.
 *
 * <p>Handles: title, subject, creator (author), keywords, description (comments),
 * category, contentStatus, contentType, lastModifiedBy, revision, created, modified, lastPrinted.</p>
 */
public final class CorePropertiesPart {

    /** The part name inside the OPC package. */
    public static final String partName = "docProps/core.xml";

    private static final String NS_CP = "http://schemas.openxmlformats.org/package/2006/metadata/core-properties";
    private static final String NS_DC = "http://purl.org/dc/elements/1.1/";
    private static final String NS_DCTERMS = "http://purl.org/dc/terms/";
    private static final String NS_XSI = "http://www.w3.org/2001/XMLSchema-instance";
    private static final String NS_DCMITYPE = "http://purl.org/dc/dcmitype/";

    private static final DateTimeFormatter W3CDTF_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private final OpcPackage pkg;
    private Element root;
    private boolean parsed;
    private boolean dirty;

    // String properties
    private String title;
    private String subject;
    private String creator;
    private String keywords;
    private String description;
    private String category;
    private String contentStatus;
    private String contentType;
    private String lastModifiedBy;
    private String revision;

    // Date properties
    private OffsetDateTime created;
    private OffsetDateTime modified;
    private OffsetDateTime lastPrinted;

    /**
     * Creates a part backed by the given package and immediately parses it.
     *
     * @param pkg the OPC package
     */
    public CorePropertiesPart(OpcPackage pkg) {
        this.pkg = pkg;
        parse();
    }

    /**
     * Parses a W3CDTF datetime string to an {@link OffsetDateTime}.
     *
     * @param text the W3CDTF string, may be {@code null}
     * @return the parsed datetime, or empty if the text is null, blank, or unparseable
     */
    public static Optional<OffsetDateTime> parseW3cdtf(String text) {
        if (text == null || text.isBlank()) {
            return Optional.empty();
        }
        text = text.strip();
        try {
            if (text.endsWith("Z")) {
                text = text.substring(0, text.length() - 1) + "+00:00";
            }
            return Optional.of(OffsetDateTime.parse(text));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    /**
     * Formats an {@link OffsetDateTime} as a W3CDTF string.
     *
     * @param dt the datetime, may be {@code null}
     * @return the formatted string, or empty if the datetime is {@code null}
     */
    public static Optional<String> formatW3cdtf(OffsetDateTime dt) {
        if (dt == null) {
            return Optional.empty();
        }
        var utc = dt.withOffsetSameInstant(ZoneOffset.UTC);
        return Optional.of(utc.format(W3CDTF_FORMATTER));
    }

    /**
     * Parses {@code docProps/core.xml} from the package, populating all property fields.
     */
    public void parse() {
        byte[] data = pkg.getPartBytes(partName);
        if (data == null) {
            root = null;
            parsed = true;
            return;
        }

        Document doc = pkg.parseXml(partName);
        if (doc != null) {
            root = doc.getDocumentElement();
        }
        parsed = true;

        title = getText(NS_DC, "title");
        subject = getText(NS_DC, "subject");
        creator = getText(NS_DC, "creator");
        keywords = getText(NS_CP, "keywords");
        description = getText(NS_DC, "description");
        category = getText(NS_CP, "category");
        contentStatus = getText(NS_CP, "contentStatus");
        contentType = getText(NS_CP, "contentType");
        lastModifiedBy = getText(NS_CP, "lastModifiedBy");
        revision = getText(NS_CP, "revision");

        created = parseW3cdtf(getText(NS_DCTERMS, "created")).orElse(null);
        modified = parseW3cdtf(getText(NS_DCTERMS, "modified")).orElse(null);
        lastPrinted = parseW3cdtf(getText(NS_CP, "lastPrinted")).orElse(null);
    }

    /**
     * Returns the text content of a child element with the given namespace and local name.
     *
     * @param ns        the namespace URI
     * @param localName the local element name
     * @return the text content, or {@code null} if not found or empty
     */
    public String getText(String ns, String localName) {
        if (root == null) {
            return null;
        }
        NodeList list = root.getElementsByTagNameNS(ns, localName);
        if (list.getLength() > 0) {
            String text = list.item(0).getTextContent();
            if (text != null && !text.isEmpty()) {
                return text;
            }
        }
        return null;
    }

    /** Marks this part as dirty so it will be saved on the next {@link #save()} call. */
    public void markDirty() {
        dirty = true;
    }

    /**
     * Serializes core properties back to the package.
     *
     * <p>Only writes if the part is dirty or was never loaded from existing data.
     * Rebuilds the XML tree from the current property values.</p>
     */
    public void save() {
        if (!dirty && root != null) {
            return;
        }

        Document doc = OpcPackage.newDocument();
        Element newRoot = doc.createElementNS(NS_CP, "cp:coreProperties");
        newRoot.setAttribute("xmlns:dc", NS_DC);
        newRoot.setAttribute("xmlns:dcterms", NS_DCTERMS);
        newRoot.setAttribute("xmlns:dcmitype", NS_DCMITYPE);
        newRoot.setAttribute("xmlns:xsi", NS_XSI);
        doc.appendChild(newRoot);

        setDc(newRoot, "title", title);
        setDc(newRoot, "subject", subject);
        setDc(newRoot, "creator", creator);
        setCp(newRoot, "keywords", keywords);
        setDc(newRoot, "description", description);
        setCp(newRoot, "category", category);
        setCp(newRoot, "contentStatus", contentStatus);
        setCp(newRoot, "contentType", contentType);
        setCp(newRoot, "lastModifiedBy", lastModifiedBy);
        setCp(newRoot, "revision", revision);

        setDctermsDate(newRoot, "created", created);
        setDctermsDate(newRoot, "modified", modified);
        if (lastPrinted != null) {
            setCp(newRoot, "lastPrinted", formatW3cdtf(lastPrinted).orElse(null));
        }

        pkg.serializeXml(partName, doc);
        dirty = false;
    }

    /**
     * Appends a Dublin Core element to the given root if the value is non-null.
     *
     * @param root      the parent element
     * @param localName the DC local element name
     * @param value     the text value, or {@code null} to skip
     */
    public void setDc(Element root, String localName, String value) {
        if (value != null) {
            Element el = root.getOwnerDocument().createElementNS(NS_DC, "dc:" + localName);
            el.setTextContent(value);
            root.appendChild(el);
        }
    }

    /**
     * Appends a Core Properties element to the given root if the value is non-null.
     *
     * @param root      the parent element
     * @param localName the CP local element name
     * @param value     the text value, or {@code null} to skip
     */
    public void setCp(Element root, String localName, String value) {
        if (value != null) {
            Element el = root.getOwnerDocument().createElementNS(NS_CP, "cp:" + localName);
            el.setTextContent(value);
            root.appendChild(el);
        }
    }

    /**
     * Appends a DC Terms date element to the given root if the datetime is non-null.
     *
     * <p>Sets the {@code xsi:type} attribute to {@code dcterms:W3CDTF}.</p>
     *
     * @param root      the parent element
     * @param localName the dcterms local element name
     * @param dt        the datetime value, or {@code null} to skip
     */
    public void setDctermsDate(Element root, String localName, OffsetDateTime dt) {
        if (dt != null) {
            Element el = root.getOwnerDocument().createElementNS(NS_DCTERMS, "dcterms:" + localName);
            el.setAttributeNS(NS_XSI, "xsi:type", "dcterms:W3CDTF");
            el.setTextContent(formatW3cdtf(dt).orElse(""));
            root.appendChild(el);
        }
    }

    /** Resets all properties to {@code null} and marks the part as dirty. */
    public void clear() {
        title = null;
        subject = null;
        creator = null;
        keywords = null;
        description = null;
        category = null;
        contentStatus = null;
        contentType = null;
        lastModifiedBy = null;
        revision = null;
        created = null;
        modified = null;
        lastPrinted = null;
        dirty = true;
    }

    // ---- Property accessors ----

    /** Returns the document title. */
    public String getTitle() { return title; }
    /** Sets the document title. */
    public void setTitle(String title) { this.title = title; }

    /** Returns the document subject. */
    public String getSubject() { return subject; }
    /** Sets the document subject. */
    public void setSubject(String subject) { this.subject = subject; }

    /** Returns the document creator (author). */
    public String getCreator() { return creator; }
    /** Sets the document creator (author). */
    public void setCreator(String creator) { this.creator = creator; }

    /** Returns the document keywords. */
    public String getKeywords() { return keywords; }
    /** Sets the document keywords. */
    public void setKeywords(String keywords) { this.keywords = keywords; }

    /** Returns the document description (comments). */
    public String getDescription() { return description; }
    /** Sets the document description (comments). */
    public void setDescription(String description) { this.description = description; }

    /** Returns the document category. */
    public String getCategory() { return category; }
    /** Sets the document category. */
    public void setCategory(String category) { this.category = category; }

    /** Returns the content status. */
    public String getContentStatus() { return contentStatus; }
    /** Sets the content status. */
    public void setContentStatus(String contentStatus) { this.contentStatus = contentStatus; }

    /** Returns the content type. */
    public String getContentType() { return contentType; }
    /** Sets the content type. */
    public void setContentType(String contentType) { this.contentType = contentType; }

    /** Returns the last modified by value. */
    public String getLastModifiedBy() { return lastModifiedBy; }
    /** Sets the last modified by value. */
    public void setLastModifiedBy(String lastModifiedBy) { this.lastModifiedBy = lastModifiedBy; }

    /** Returns the revision number as a string. */
    public String getRevision() { return revision; }
    /** Sets the revision number as a string. */
    public void setRevision(String revision) { this.revision = revision; }

    /** Returns the creation datetime. */
    public OffsetDateTime getCreated() { return created; }
    /** Sets the creation datetime. */
    public void setCreated(OffsetDateTime created) { this.created = created; }

    /** Returns the last modification datetime. */
    public OffsetDateTime getModified() { return modified; }
    /** Sets the last modification datetime. */
    public void setModified(OffsetDateTime modified) { this.modified = modified; }

    /** Returns the last printed datetime. */
    public OffsetDateTime getLastPrinted() { return lastPrinted; }
    /** Sets the last printed datetime. */
    public void setLastPrinted(OffsetDateTime lastPrinted) { this.lastPrinted = lastPrinted; }
}
