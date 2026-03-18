package org.aspose.slides.foss;

import org.aspose.slides.foss.drawing.Color;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a collection of gradient stops.
 *
 * <p>Wraps an OOXML {@code <a:gsLst>} element.</p>
 */
public final class GradientStopCollection implements IGradientStopCollection, Iterable<IGradientStop> {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";

    private final Element gsLst;
    private final Runnable saveCallback;

    /**
     * Creates a new GradientStopCollection backed by the given {@code <a:gsLst>} element.
     *
     * @param gsLst        the gsLst XML element
     * @param saveCallback callback invoked after mutations; may be {@code null}
     */
    public GradientStopCollection(Element gsLst, Runnable saveCallback) {
        this.gsLst = gsLst;
        this.saveCallback = saveCallback;
    }

    private void save() {
        if (saveCallback != null) saveCallback.run();
    }

    private List<Element> getGsElements() {
        List<Element> result = new ArrayList<>();
        NodeList children = gsLst.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i) instanceof Element el
                    && NS_A.equals(el.getNamespaceURI())
                    && "gs".equals(el.getLocalName())) {
                result.add(el);
            }
        }
        return result;
    }

    /**
     * Creates a new {@code <a:gs>} element with the given position.
     */
    private Element createGsElement(double position) {
        Document doc = gsLst.getOwnerDocument();
        Element gsElem = doc.createElementNS(NS_A, "a:gs");
        gsElem.setAttribute("pos", String.valueOf(Math.round(position * 100000)));
        return gsElem;
    }

    /**
     * Sets the color on the given {@code <a:gs>} element from the provided color argument.
     */
    private void setColorOnElement(Element gsElem, Color color) {
        new ColorFormat(gsElem, saveCallback).setColor(color);
    }

    private void setColorOnElement(Element gsElem, PresetColor presetColor) {
        new ColorFormat(gsElem, saveCallback).setPresetColor(presetColor);
    }

    private void setColorOnElement(Element gsElem, SchemeColor schemeColor) {
        new ColorFormat(gsElem, saveCallback).setSchemeColor(schemeColor);
    }

    @Override
    public IGradientStop get(int index) {
        List<Element> elements = getGsElements();
        if (index < 0 || index >= elements.size()) {
            throw new IndexOutOfBoundsException("Index " + index + " out of range");
        }
        return new GradientStop(elements.get(index), saveCallback);
    }

    @Override
    public int size() {
        return getGsElements().size();
    }

    @Override
    public IGradientStop add(double position, Color color) {
        Element gsElem = createGsElement(position);
        gsLst.appendChild(gsElem);
        setColorOnElement(gsElem, color);
        save();
        return new GradientStop(gsElem, saveCallback);
    }

    @Override
    public IGradientStop add(double position, PresetColor presetColor) {
        Element gsElem = createGsElement(position);
        gsLst.appendChild(gsElem);
        setColorOnElement(gsElem, presetColor);
        save();
        return new GradientStop(gsElem, saveCallback);
    }

    @Override
    public IGradientStop add(double position, SchemeColor schemeColor) {
        Element gsElem = createGsElement(position);
        gsLst.appendChild(gsElem);
        setColorOnElement(gsElem, schemeColor);
        save();
        return new GradientStop(gsElem, saveCallback);
    }

    @Override
    public void insert(int index, double position, Color color) {
        Element gsElem = createGsElement(position);
        setColorOnElement(gsElem, color);
        insertElement(index, gsElem);
        save();
    }

    @Override
    public void insert(int index, double position, PresetColor presetColor) {
        Element gsElem = createGsElement(position);
        setColorOnElement(gsElem, presetColor);
        insertElement(index, gsElem);
        save();
    }

    @Override
    public void insert(int index, double position, SchemeColor schemeColor) {
        Element gsElem = createGsElement(position);
        setColorOnElement(gsElem, schemeColor);
        insertElement(index, gsElem);
        save();
    }

    /**
     * Inserts the given element at the specified index among existing {@code <a:gs>} children.
     * If the index is beyond the current size, the element is appended.
     */
    private void insertElement(int index, Element gsElem) {
        List<Element> elements = getGsElements();
        if (index >= elements.size()) {
            gsLst.appendChild(gsElem);
        } else {
            gsLst.insertBefore(gsElem, elements.get(index));
        }
    }

    @Override
    public void removeAt(int index) {
        List<Element> elements = getGsElements();
        if (index >= 0 && index < elements.size()) {
            gsLst.removeChild(elements.get(index));
            save();
        }
    }

    @Override
    public void clear() {
        for (Element el : getGsElements()) {
            gsLst.removeChild(el);
        }
        save();
    }

    @Override
    public List<IGradientStop> asICollection() {
        List<Element> elements = getGsElements();
        List<IGradientStop> result = new ArrayList<>(elements.size());
        for (Element el : elements) {
            result.add(new GradientStop(el, saveCallback));
        }
        return result;
    }

    @Override
    public Iterable<IGradientStop> asIEnumerable() {
        return asICollection();
    }

    /**
     * Returns an iterator over the gradient stops in this collection.
     *
     * @return an iterator
     */
    @Override
    public Iterator<IGradientStop> iterator() {
        return asICollection().iterator();
    }

    /**
     * Sets color on a {@link ColorFormat} from various argument types.
     *
     * <p>Accepts {@link Color}, {@link PresetColor}, or {@link SchemeColor}.</p>
     *
     * @param cf       the color format to modify
     * @param colorArg the color argument
     */
    void setColorFromArg(IColorFormat cf, Object colorArg) {
        if (colorArg instanceof Color color) {
            cf.setColor(color);
        } else if (colorArg instanceof PresetColor presetColor) {
            cf.setPresetColor(presetColor);
        } else if (colorArg instanceof SchemeColor schemeColor) {
            cf.setSchemeColor(schemeColor);
        }
    }
}
