package org.aspose.slides.foss.internal.pptx;

import org.w3c.dom.Element;

import java.util.Objects;

/**
 * Raw data for a comment author parsed from XML.
 *
 * <p>Wraps a {@code <p:cmAuthor>} DOM element and provides typed access
 * to its attributes.</p>
 */
public final class AuthorData {

    private final Element elem;

    /**
     * Creates an AuthorData wrapper around the given {@code <p:cmAuthor>} element.
     *
     * @param elem the {@code <p:cmAuthor>} DOM element
     */
    public AuthorData(Element elem) {
        this.elem = Objects.requireNonNull(elem);
    }

    /**
     * Returns the underlying DOM element.
     *
     * @return the {@code <p:cmAuthor>} element
     */
    public Element getElement() {
        return elem;
    }

    /**
     * Returns the author ID.
     *
     * @return the author ID
     */
    public int getId() {
        String val = elem.getAttribute("id");
        return val.isEmpty() ? 0 : Integer.parseInt(val);
    }

    /**
     * Returns the author name.
     *
     * @return the author name
     */
    public String getName() {
        return elem.getAttribute("name");
    }

    /**
     * Sets the author name.
     *
     * @param value the author name
     */
    public void setName(String value) {
        elem.setAttribute("name", value);
    }

    /**
     * Returns the author initials.
     *
     * @return the author initials
     */
    public String getInitials() {
        return elem.getAttribute("initials");
    }

    /**
     * Sets the author initials.
     *
     * @param value the author initials
     */
    public void setInitials(String value) {
        elem.setAttribute("initials", value);
    }

    /**
     * Returns the last comment index used by this author.
     *
     * @return the last index
     */
    public int getLastIdx() {
        String val = elem.getAttribute("lastIdx");
        return val.isEmpty() ? 0 : Integer.parseInt(val);
    }

    /**
     * Sets the last comment index used by this author.
     *
     * @param value the last index
     */
    public void setLastIdx(int value) {
        elem.setAttribute("lastIdx", String.valueOf(value));
    }

    /**
     * Returns the color index for this author.
     *
     * @return the color index
     */
    public int getClrIdx() {
        String val = elem.getAttribute("clrIdx");
        return val.isEmpty() ? 0 : Integer.parseInt(val);
    }
}
