package org.aspose.slides.foss;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Represents a pattern fill format.
 *
 * <p>Wraps an OOXML {@code <a:pattFill>} element.</p>
 */
public final class PatternFormat extends PVIObject implements IPatternFormat {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";

    private Element pattFill;
    private Runnable saveCallback;

    /**
     * Creates a new PatternFormat backed by the given {@code <a:pattFill>} element.
     *
     * @param pattFill     the pattFill XML element
     * @param saveCallback callback invoked after mutations; may be {@code null}
     */
    public PatternFormat(Element pattFill, Runnable saveCallback) {
        this.pattFill = pattFill;
        this.saveCallback = saveCallback;
    }

    /**
     * Creates an uninitialized PatternFormat. Call {@link #initInternal} before use.
     */
    public PatternFormat() {
    }

    /**
     * Internal initialization.
     *
     * @param pattFillElement the {@code <a:pattFill>} XML element
     * @param saveCallback    callback invoked after mutations to persist changes; may be {@code null}
     * @param parentSlide     the parent slide object
     */
    public void initInternal(Element pattFillElement, Runnable saveCallback, IBaseSlide parentSlide) {
        this.pattFill = pattFillElement;
        this.saveCallback = saveCallback;
        this.parentSlide = parentSlide;
    }

    private void save() {
        if (saveCallback != null) saveCallback.run();
    }

    private Element findChild(String localName) {
        NodeList children = pattFill.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i) instanceof Element el
                    && NS_A.equals(el.getNamespaceURI())
                    && localName.equals(el.getLocalName())) {
                return el;
            }
        }
        return null;
    }

    private Element ensureChild(String localName) {
        Element el = findChild(localName);
        if (el == null) {
            Document doc = pattFill.getOwnerDocument();
            el = doc.createElementNS(NS_A, "a:" + localName);
            pattFill.appendChild(el);
        }
        return el;
    }

    @Override
    public PatternStyle getPatternStyle() {
        String prst = pattFill.getAttribute("prst");
        if (prst == null || prst.isEmpty()) return PatternStyle.NOT_DEFINED;
        String enumName = ColorFormat.camelToUpperSnake(prst);
        try {
            return PatternStyle.valueOf(enumName);
        } catch (IllegalArgumentException e) {
            return PatternStyle.NOT_DEFINED;
        }
    }

    @Override
    public void setPatternStyle(PatternStyle value) {
        if (value == PatternStyle.NOT_DEFINED) {
            pattFill.removeAttribute("prst");
        } else {
            String ooxmlVal = ColorFormat.upperSnakeToCamel(value.name());
            pattFill.setAttribute("prst", ooxmlVal);
        }
        save();
    }

    @Override
    public IColorFormat getForeColor() {
        Element fgClr = ensureChild("fgClr");
        return new ColorFormat(fgClr, saveCallback);
    }

    @Override
    public IColorFormat getBackColor() {
        Element bgClr = ensureChild("bgClr");
        return new ColorFormat(bgClr, saveCallback);
    }
}
