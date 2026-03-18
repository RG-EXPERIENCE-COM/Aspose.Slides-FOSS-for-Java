package org.aspose.slides.foss.internal.pptx;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.Objects;
import java.util.OptionalInt;

/**
 * Raw data for a comment parsed from XML.
 *
 * <p>Wraps a {@code <p:cm>} DOM element and provides typed access to its attributes
 * and child elements. Positions are stored in centimetres (converted from EMU).</p>
 */
public final class CommentData {

    private static final String NS_P = "http://schemas.openxmlformats.org/presentationml/2006/main";

    /** EMU conversion factor: 1 cm = 360 000 EMU. */
    private static final int CM_TO_EMU = 360_000;

    private final Element elem;

    /**
     * Creates a CommentData wrapper around the given {@code <p:cm>} element.
     *
     * @param elem the {@code <p:cm>} DOM element
     */
    public CommentData(Element elem) {
        this.elem = Objects.requireNonNull(elem);
    }

    /**
     * Returns the underlying DOM element.
     *
     * @return the {@code <p:cm>} element
     */
    public Element getElement() {
        return elem;
    }

    /**
     * Returns the author ID of this comment.
     *
     * @return the author ID
     */
    public int getAuthorId() {
        String val = elem.getAttribute("authorId");
        return val.isEmpty() ? 0 : Integer.parseInt(val);
    }

    /**
     * Returns the index of this comment.
     *
     * @return the comment index
     */
    public int getIdx() {
        String val = elem.getAttribute("idx");
        return val.isEmpty() ? 0 : Integer.parseInt(val);
    }

    /**
     * Returns the datetime string of this comment.
     *
     * @return the OOXML datetime string, or empty string if not set
     */
    public String getDtStr() {
        return elem.getAttribute("dt");
    }

    /**
     * Sets the datetime string of this comment.
     *
     * @param value the OOXML datetime string
     */
    public void setDtStr(String value) {
        elem.setAttribute("dt", value);
    }

    /**
     * Returns the parent comment ID, if this is a reply.
     *
     * @return the parent comment ID, or empty if not a reply
     */
    public OptionalInt getParentCmId() {
        String val = elem.getAttribute("parentCmId");
        if (val == null || val.isEmpty()) {
            return OptionalInt.empty();
        }
        return OptionalInt.of(Integer.parseInt(val));
    }

    /**
     * Sets the parent comment ID. Pass {@code null} to remove.
     *
     * @param value the parent comment ID, or {@code null} to clear
     */
    public void setParentCmId(Integer value) {
        if (value == null) {
            elem.removeAttribute("parentCmId");
        } else {
            elem.setAttribute("parentCmId", String.valueOf(value));
        }
    }

    /**
     * Returns the comment text.
     *
     * @return the text content
     */
    public String getText() {
        Element textElem = getFirstChild(NS_P, "text");
        if (textElem != null && textElem.getTextContent() != null) {
            return textElem.getTextContent();
        }
        return "";
    }

    /**
     * Sets the comment text.
     *
     * @param value the text content
     */
    public void setText(String value) {
        Element textElem = getFirstChild(NS_P, "text");
        if (textElem == null) {
            Document doc = elem.getOwnerDocument();
            textElem = doc.createElementNS(NS_P, "p:text");
            elem.appendChild(textElem);
        }
        textElem.setTextContent(value);
    }

    /**
     * Returns the X position in centimetres.
     *
     * @return the X position
     */
    public double getPosX() {
        Element pos = getFirstChild(NS_P, "pos");
        if (pos != null) {
            String val = pos.getAttribute("x");
            if (!val.isEmpty()) {
                return Integer.parseInt(val) / (double) CM_TO_EMU;
            }
        }
        return 0.0;
    }

    /**
     * Sets the X position in centimetres.
     *
     * @param value the X position in cm
     */
    public void setPosX(double value) {
        Element pos = ensurePos();
        pos.setAttribute("x", String.valueOf(Math.round(value * CM_TO_EMU)));
    }

    /**
     * Returns the Y position in centimetres.
     *
     * @return the Y position
     */
    public double getPosY() {
        Element pos = getFirstChild(NS_P, "pos");
        if (pos != null) {
            String val = pos.getAttribute("y");
            if (!val.isEmpty()) {
                return Integer.parseInt(val) / (double) CM_TO_EMU;
            }
        }
        return 0.0;
    }

    /**
     * Sets the Y position in centimetres.
     *
     * @param value the Y position in cm
     */
    public void setPosY(double value) {
        Element pos = ensurePos();
        pos.setAttribute("y", String.valueOf(Math.round(value * CM_TO_EMU)));
    }

    // ---- Private helpers ----

    private Element getFirstChild(String ns, String localName) {
        NodeList list = elem.getElementsByTagNameNS(ns, localName);
        return list.getLength() > 0 ? (Element) list.item(0) : null;
    }

    private Element ensurePos() {
        Element pos = getFirstChild(NS_P, "pos");
        if (pos == null) {
            Document doc = elem.getOwnerDocument();
            pos = doc.createElementNS(NS_P, "p:pos");
            elem.appendChild(pos);
        }
        return pos;
    }
}
