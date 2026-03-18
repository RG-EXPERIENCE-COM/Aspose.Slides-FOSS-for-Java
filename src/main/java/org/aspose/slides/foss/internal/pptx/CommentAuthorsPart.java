package org.aspose.slides.foss.internal.pptx;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Manages the comment authors XML part ({@code ppt/commentAuthors.xml}).
 *
 * <p>This part holds all author definitions used across the presentation's comments.</p>
 */
public final class CommentAuthorsPart {

    /** The part URI inside the OPC package. */
    public static final String PART_NAME = "ppt/commentAuthors.xml";

    private static final String NS_P = "http://schemas.openxmlformats.org/presentationml/2006/main";
    private static final String REL_TYPE_COMMENT_AUTHORS =
            "http://schemas.openxmlformats.org/officeDocument/2006/relationships/commentAuthors";
    private static final String CONTENT_TYPE_COMMENT_AUTHORS =
            "application/vnd.openxmlformats-officedocument.presentationml.commentAuthors+xml";
    private static final String REL_NS = "http://schemas.openxmlformats.org/package/2006/relationships";
    private static final String CT_NS = "http://schemas.openxmlformats.org/package/2006/content-types";

    private final OpcPackage pkg;
    private Document doc;
    private Element root;

    /**
     * Creates a CommentAuthorsPart for the given package and loads its content.
     *
     * @param pkg the OPC package
     */
    public CommentAuthorsPart(OpcPackage pkg) {
        this.pkg = Objects.requireNonNull(pkg);
        load();
    }

    /**
     * Loads the comment authors XML from the package. If the part does not exist,
     * creates an empty {@code <p:cmAuthorLst>} root element.
     */
    private void load() {
        doc = pkg.parseXml(PART_NAME);
        if (doc != null) {
            root = doc.getDocumentElement();
        } else {
            doc = OpcPackage.newDocument();
            root = doc.createElementNS(NS_P, "p:cmAuthorLst");
            doc.appendChild(root);
        }
    }

    /**
     * Returns all comment authors.
     *
     * @return list of author data wrappers
     */
    public List<AuthorData> getAuthors() {
        var result = new ArrayList<AuthorData>();
        NodeList nodes = root.getElementsByTagNameNS(NS_P, "cmAuthor");
        for (int i = 0; i < nodes.getLength(); i++) {
            result.add(new AuthorData((Element) nodes.item(i)));
        }
        return result;
    }

    /**
     * Finds an author by their unique ID.
     *
     * @param authorId the author ID to search for
     * @return the matching author, or empty if not found
     */
    public Optional<AuthorData> findAuthorById(int authorId) {
        NodeList nodes = root.getElementsByTagNameNS(NS_P, "cmAuthor");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element el = (Element) nodes.item(i);
            String idStr = el.getAttribute("id");
            if (!idStr.isEmpty() && Integer.parseInt(idStr) == authorId) {
                return Optional.of(new AuthorData(el));
            }
        }
        return Optional.empty();
    }

    /**
     * Adds a new author and returns its data wrapper.
     *
     * @param name     the author name
     * @param initials the author initials
     * @return the created author data
     */
    public AuthorData addAuthor(String name, String initials) {
        NodeList existing = root.getElementsByTagNameNS(NS_P, "cmAuthor");
        int nextId = existing.getLength();
        int clrIdx = nextId % 10; // Colors cycle 0-9

        Element elem = doc.createElementNS(NS_P, "p:cmAuthor");
        elem.setAttribute("id", String.valueOf(nextId));
        elem.setAttribute("name", name);
        elem.setAttribute("initials", initials);
        elem.setAttribute("lastIdx", "0");
        elem.setAttribute("clrIdx", String.valueOf(clrIdx));
        root.appendChild(elem);
        return new AuthorData(elem);
    }

    /**
     * Removes an author by their unique ID.
     *
     * @param authorId the author ID to remove
     */
    public void removeAuthor(int authorId) {
        NodeList nodes = root.getElementsByTagNameNS(NS_P, "cmAuthor");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element el = (Element) nodes.item(i);
            String idStr = el.getAttribute("id");
            if (!idStr.isEmpty() && Integer.parseInt(idStr) == authorId) {
                root.removeChild(el);
                return;
            }
        }
    }

    /**
     * Removes all authors from this part.
     */
    public void clear() {
        NodeList nodes = root.getElementsByTagNameNS(NS_P, "cmAuthor");
        // Iterate backwards to safely remove nodes
        for (int i = nodes.getLength() - 1; i >= 0; i--) {
            root.removeChild(nodes.item(i));
        }
    }

    /**
     * Returns the next globally-unique comment index.
     *
     * <p>OOXML {@code parentCmId} references idx values that must be unique across
     * <strong>all</strong> authors within the presentation (not just per-author).
     * This method takes the maximum {@code lastIdx} across every author and increments
     * from there, then updates only the target author's {@code lastIdx}.</p>
     *
     * @param authorId the author ID to update
     * @return the next unique comment index
     */
    public int nextCommentIdx(int authorId) {
        int globalMax = 0;
        NodeList nodes = root.getElementsByTagNameNS(NS_P, "cmAuthor");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element el = (Element) nodes.item(i);
            String lastIdxStr = el.getAttribute("lastIdx");
            if (!lastIdxStr.isEmpty()) {
                globalMax = Math.max(globalMax, Integer.parseInt(lastIdxStr));
            }
        }
        int newIdx = globalMax + 1;
        findAuthorById(authorId).ifPresent(data -> data.setLastIdx(newIdx));
        return newIdx;
    }

    /**
     * Saves the comment authors XML back to the package.
     */
    public void save() {
        pkg.serializeXml(PART_NAME, doc);
    }

    /**
     * Ensures that {@code commentAuthors.xml} is registered in content types and
     * the presentation has a relationship to it. Call before first save.
     *
     * @param pkg the OPC package
     */
    public static void ensureRegistered(OpcPackage pkg) {
        // Add content type override if needed
        var ct = new ContentTypesManager(pkg);
        Optional<String> existingCt = ct.getContentType(PART_NAME);
        if (existingCt.isEmpty() || !CONTENT_TYPE_COMMENT_AUTHORS.equals(existingCt.get())) {
            ct.addOverride(PART_NAME, CONTENT_TYPE_COMMENT_AUTHORS);
            ct.save();
        }

        // Add relationship from presentation to commentAuthors if needed
        String relsUri = getRelsPartName(PresentationPart.PART_NAME);
        Document relsDoc = pkg.parseXml(relsUri);
        Element relsRoot;
        if (relsDoc == null) {
            relsDoc = OpcPackage.newDocument();
            relsRoot = relsDoc.createElementNS(REL_NS, "Relationships");
            relsDoc.appendChild(relsRoot);
        } else {
            relsRoot = relsDoc.getDocumentElement();
            // Check if relationship already exists
            NodeList rels = relsRoot.getElementsByTagName("Relationship");
            for (int i = 0; i < rels.getLength(); i++) {
                Element rel = (Element) rels.item(i);
                if (REL_TYPE_COMMENT_AUTHORS.equals(rel.getAttribute("Type"))) {
                    return;
                }
            }
        }

        Element rel = relsDoc.createElementNS(REL_NS, "Relationship");
        rel.setAttribute("Id", "rId_commentAuthors");
        rel.setAttribute("Type", REL_TYPE_COMMENT_AUTHORS);
        rel.setAttribute("Target", "commentAuthors.xml");
        relsRoot.appendChild(rel);
        pkg.serializeXml(relsUri, relsDoc);
    }

    private static String getRelsPartName(String partName) {
        int lastSlash = partName.lastIndexOf('/');
        if (lastSlash >= 0) {
            return partName.substring(0, lastSlash + 1) + "_rels/"
                    + partName.substring(lastSlash + 1) + ".rels";
        }
        return "_rels/" + partName + ".rels";
    }
}
