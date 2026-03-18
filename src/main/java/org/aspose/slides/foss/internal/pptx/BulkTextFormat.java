package org.aspose.slides.foss.internal.pptx;

import org.aspose.slides.foss.BasePortionFormat;
import org.aspose.slides.foss.Cell;
import org.aspose.slides.foss.ParagraphFormat;
import org.aspose.slides.foss.PortionFormat;
import org.aspose.slides.foss.TextFrameFormat;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Helper for applying bulk text formatting to collections of table cells.
 *
 * <p>Implements the logic behind {@code IBulkTextFormattable.setTextFormat} for
 * Table, Row, and Column classes. The three overloads accept:
 * <ul>
 *   <li>{@link PortionFormat} / {@link BasePortionFormat} &rarr; applied to every run
 *       ({@code <a:rPr>}) in every cell</li>
 *   <li>{@link ParagraphFormat} &rarr; applied to every paragraph ({@code <a:pPr>})
 *       in every cell</li>
 *   <li>{@link TextFrameFormat} &rarr; applied to every text body ({@code <a:bodyPr>})
 *       in every cell</li>
 * </ul>
 */
public final class BulkTextFormat {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";

    private BulkTextFormat() {
        // utility class
    }

    /**
     * Copies all attributes from {@code src} to {@code dst}, overwriting existing ones.
     *
     * @param src the source element whose attributes are copied
     * @param dst the destination element to receive the attributes
     */
    public static void copyXmlAttrs(Element src, Element dst) {
        NamedNodeMap attrs = src.getAttributes();
        for (int i = 0; i < attrs.getLength(); i++) {
            Node attr = attrs.item(i);
            dst.setAttribute(attr.getNodeName(), attr.getNodeValue());
        }
    }

    /**
     * Replaces the first child of {@code parent} with the same tag as {@code srcChild},
     * or appends a deep copy if no such child exists.
     *
     * @param parent   the parent element
     * @param srcChild the source child element to clone into the parent
     */
    public static void replaceOrAddChild(Element parent, Element srcChild) {
        Element existing = findChildByTag(parent, srcChild.getNamespaceURI(), srcChild.getLocalName());
        Node clone = parent.getOwnerDocument().importNode(srcChild, true);
        if (existing != null) {
            parent.replaceChild(clone, existing);
        } else {
            parent.appendChild(clone);
        }
    }

    /**
     * Copies attributes and children from a source {@code <a:rPr>} to a target
     * {@code <a:rPr>}-like element.
     *
     * @param srcRpr the source run properties element
     * @param target the target run properties element to update
     */
    public static void applyRprToElement(Element srcRpr, Element target) {
        copyXmlAttrs(srcRpr, target);
        NodeList children = srcRpr.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i) instanceof Element child) {
                replaceOrAddChild(target, child);
            }
        }
    }

    /**
     * Applies a {@link PortionFormat} or {@link BasePortionFormat} to every run
     * and {@code endParaRPr} in every cell.
     *
     * @param cells  iterable of {@link Cell} objects
     * @param source the portion format whose {@code <a:rPr>} element is applied
     */
    public static void applyPortionFormat(Iterable<Cell> cells, BasePortionFormat source) {
        Element srcRpr = source.getRprElement();
        if (srcRpr == null) {
            return;
        }
        for (Cell cell : cells) {
            Element tcElement = cell.getTcElement();
            if (tcElement == null) continue;
            Element txBody = findChildByTag(tcElement, NS_A, "txBody");
            if (txBody == null) continue;

            for (Element p : childElements(txBody, NS_A, "p")) {
                // Apply to runs
                for (Element r : childElements(p, NS_A, "r")) {
                    Element rpr = findChildByTag(r, NS_A, "rPr");
                    if (rpr == null) {
                        Document doc = r.getOwnerDocument();
                        rpr = doc.createElementNS(NS_A, "a:rPr");
                        r.insertBefore(rpr, r.getFirstChild());
                    }
                    applyRprToElement(srcRpr, rpr);
                }
                // Apply to endParaRPr
                Element endRpr = findChildByTag(p, NS_A, "endParaRPr");
                if (endRpr != null) {
                    applyRprToElement(srcRpr, endRpr);
                }
            }
        }
    }

    /**
     * Applies a {@link ParagraphFormat} to every paragraph in every cell.
     *
     * @param cells  iterable of {@link Cell} objects
     * @param source the paragraph format whose {@code <a:pPr>} attributes and children are applied
     */
    public static void applyParagraphFormat(Iterable<Cell> cells, ParagraphFormat source) {
        Element pElement = source.getPElement();
        if (pElement == null) {
            return;
        }
        Element srcPpr = findChildByTag(pElement, NS_A, "pPr");
        if (srcPpr == null) {
            return;
        }
        for (Cell cell : cells) {
            Element tcElement = cell.getTcElement();
            if (tcElement == null) continue;
            Element txBody = findChildByTag(tcElement, NS_A, "txBody");
            if (txBody == null) continue;

            for (Element p : childElements(txBody, NS_A, "p")) {
                Element ppr = findChildByTag(p, NS_A, "pPr");
                if (ppr == null) {
                    Document doc = p.getOwnerDocument();
                    ppr = doc.createElementNS(NS_A, "a:pPr");
                    p.insertBefore(ppr, p.getFirstChild());
                }
                copyXmlAttrs(srcPpr, ppr);
                NodeList children = srcPpr.getChildNodes();
                for (int i = 0; i < children.getLength(); i++) {
                    if (children.item(i) instanceof Element child) {
                        replaceOrAddChild(ppr, child);
                    }
                }
            }
        }
    }

    /**
     * Applies a {@link TextFrameFormat} to every text body in every cell.
     *
     * <p>Also propagates the {@code vert} attribute to {@code <a:tcPr>} to match
     * Aspose.Slides behaviour where vertical text type is mirrored on the
     * cell properties element.</p>
     *
     * @param cells  iterable of {@link Cell} objects
     * @param source the text frame format whose {@code <a:bodyPr>} is applied
     */
    public static void applyTextFrameFormat(Iterable<Cell> cells, TextFrameFormat source) {
        Element txBodyElement = source.getTxBodyElement();
        if (txBodyElement == null) {
            return;
        }
        Element srcBodyPr = findChildByTag(txBodyElement, NS_A, "bodyPr");
        if (srcBodyPr == null) {
            return;
        }
        String vertVal = srcBodyPr.hasAttribute("vert") ? srcBodyPr.getAttribute("vert") : null;

        for (Cell cell : cells) {
            Element tcElement = cell.getTcElement();
            if (tcElement == null) continue;
            Element txBody = findChildByTag(tcElement, NS_A, "txBody");
            if (txBody == null) continue;

            Element bodyPr = findChildByTag(txBody, NS_A, "bodyPr");
            if (bodyPr == null) {
                Document doc = txBody.getOwnerDocument();
                bodyPr = doc.createElementNS(NS_A, "a:bodyPr");
                txBody.insertBefore(bodyPr, txBody.getFirstChild());
            }
            copyXmlAttrs(srcBodyPr, bodyPr);
            NodeList children = srcBodyPr.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                if (children.item(i) instanceof Element child) {
                    replaceOrAddChild(bodyPr, child);
                }
            }

            // Mirror vert on <a:tcPr>
            if (vertVal != null) {
                Element tcPr = findChildByTag(tcElement, NS_A, "tcPr");
                if (tcPr != null) {
                    tcPr.setAttribute("vert", vertVal);
                }
            }
        }
    }

    /**
     * Dispatches to the correct applier based on the runtime type of {@code source}.
     *
     * @param cells        iterable of {@link Cell} objects (each must have a tc element)
     * @param source       a {@link PortionFormat}, {@link BasePortionFormat},
     *                     {@link ParagraphFormat}, or {@link TextFrameFormat} instance
     * @param saveCallback callback invoked after applying the format; may be {@code null}
     * @throws IllegalArgumentException if {@code source} is not a recognized format type
     */
    public static void applyTextFormat(Iterable<Cell> cells, Object source, Runnable saveCallback) {
        if (source instanceof BasePortionFormat bpf) {
            applyPortionFormat(cells, bpf);
        } else if (source instanceof ParagraphFormat pf) {
            applyParagraphFormat(cells, pf);
        } else if (source instanceof TextFrameFormat tff) {
            applyTextFrameFormat(cells, tff);
        } else {
            throw new IllegalArgumentException(
                    "setTextFormat expects PortionFormat, ParagraphFormat, or TextFrameFormat, got "
                            + (source == null ? "null" : source.getClass().getSimpleName())
            );
        }

        if (saveCallback != null) {
            saveCallback.run();
        }
    }

    // ---- internal helpers ----

    /**
     * Finds the first child element with the given namespace URI and local name.
     */
    private static Element findChildByTag(Element parent, String nsUri, String localName) {
        NodeList children = parent.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i) instanceof Element el
                    && nsUri.equals(el.getNamespaceURI())
                    && localName.equals(el.getLocalName())) {
                return el;
            }
        }
        return null;
    }

    /**
     * Returns all child elements matching the given namespace URI and local name.
     */
    private static java.util.List<Element> childElements(Element parent, String nsUri, String localName) {
        var result = new java.util.ArrayList<Element>();
        NodeList children = parent.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i) instanceof Element el
                    && nsUri.equals(el.getNamespaceURI())
                    && localName.equals(el.getLocalName())) {
                result.add(el);
            }
        }
        return result;
    }
}
