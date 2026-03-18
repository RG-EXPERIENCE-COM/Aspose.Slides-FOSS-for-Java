package org.aspose.slides.foss;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Set;

/**
 * Represents the fill format of a line.
 *
 * <p>Wraps an {@code <a:ln>} element for reading and writing line fill properties.
 * Overrides fill element insertion to maintain the correct OOXML child ordering
 * within {@code <a:ln>}: fill elements appear before dash, join, arrow-end,
 * and extension elements.</p>
 */
public final class LineFillFormat extends FillFormat implements ILineFillFormat {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";
    private static final String NS_P = "http://schemas.openxmlformats.org/presentationml/2006/main";

    /** Local names that must appear after fill elements within {@code <a:ln>}. */
    private static final Set<String> AFTER_FILL_LOCAL_NAMES = Set.of(
            "prstDash", "custDash", "round", "bevel", "miter",
            "headEnd", "tailEnd", "extLst"
    );

    /**
     * Creates a new LineFillFormat backed by the given {@code <a:ln>} element.
     *
     * @param lnElement    the line XML element
     * @param saveCallback callback invoked after mutations; may be {@code null}
     */
    public LineFillFormat(Element lnElement, Runnable saveCallback) {
        super(lnElement, saveCallback);
    }

    /**
     * Creates an uninitialized LineFillFormat. Call {@link #initInternal} before use.
     */
    public LineFillFormat() {
    }

    /**
     * Internal initialization for deferred construction.
     *
     * @param lnElement    the {@code <a:ln>} XML element
     * @param saveCallback callback invoked after mutations to persist changes; may be {@code null}
     * @param parentSlide  the parent slide object
     */
    @Override
    public void initInternal(Element lnElement, Runnable saveCallback, IBaseSlide parentSlide) {
        super.initInternal(lnElement, saveCallback, parentSlide);
    }

    /**
     * Inserts a fill element at the correct position within {@code <a:ln>}.
     *
     * <p>Fill elements are inserted before dash, join, arrow-end, and extension
     * elements to maintain OOXML element ordering.</p>
     *
     * @param localName the local name of the fill element (e.g. {@code "solidFill"})
     * @return the newly created element
     */
    @Override
    protected Element insertFillElement(String localName) {
        Document doc = parentElement.getOwnerDocument();
        Element el = doc.createElementNS(NS_A, "a:" + localName);
        Node insertBefore = null;
        NodeList children = parentElement.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i) instanceof Element child
                    && NS_A.equals(child.getNamespaceURI())
                    && AFTER_FILL_LOCAL_NAMES.contains(child.getLocalName())) {
                insertBefore = child;
                break;
            }
        }
        if (insertBefore != null) {
            parentElement.insertBefore(el, insertBefore);
        } else {
            parentElement.appendChild(el);
        }
        return el;
    }

    /**
     * Resets the {@code <a:lnRef idx>} attribute to {@code "0"} in the parent
     * shape's {@code <p:style>} element so that the explicit line fill takes
     * priority over the theme reference.
     *
     * <p>Navigates from the {@code <a:ln>} element up through {@code <p:spPr>}
     * to the shape element, then finds {@code <p:style>/<a:lnRef>} and sets
     * its {@code idx} attribute to {@code "0"}.</p>
     */
    public void resetStyleLnRef() {
        Element ln = parentElement;
        // <a:ln> is a child of <p:spPr> (or similar)
        Node spPrNode = ln.getParentNode();
        if (!(spPrNode instanceof Element)) {
            return;
        }
        // <p:spPr> is a child of the shape element (e.g. <p:sp>)
        Node shapeNode = spPrNode.getParentNode();
        if (!(shapeNode instanceof Element shapeEl)) {
            return;
        }
        // Find <p:style> in the shape element
        Element styleEl = findChild(shapeEl, NS_P, "style");
        if (styleEl == null) {
            return;
        }
        // Find <a:lnRef> in the style element
        Element lnRef = findChild(styleEl, NS_A, "lnRef");
        if (lnRef != null) {
            lnRef.setAttribute("idx", "0");
        }
    }

    private static Element findChild(Element parent, String nsUri, String localName) {
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
}
