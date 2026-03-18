package org.aspose.slides.foss;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a collection of shape's adjustments backed by an OOXML {@code <a:avLst>} element.
 *
 * <p>This collection lazily resolves {@code <a:gd>} child elements on each access.</p>
 */
public final class AdjustValueCollection implements IAdjustValueCollection {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";

    private Element avLstElement;
    private Runnable saveCallback;

    /**
     * Creates an empty {@code AdjustValueCollection} with no backing element.
     */
    public AdjustValueCollection() {
        // no-op; call initInternal to bind to an XML element
    }

    /**
     * Initializes this collection from the given {@code <a:avLst>} element.
     *
     * @param avLstElement the OOXML avLst element; may be {@code null}
     * @param saveCallback callback invoked after mutations; may be {@code null}
     * @return this collection, for fluent chaining
     */
    public AdjustValueCollection initInternal(Element avLstElement, Runnable saveCallback) {
        this.avLstElement = avLstElement;
        this.saveCallback = saveCallback;
        return this;
    }

    /**
     * Returns the {@code <a:gd>} child elements of the backing {@code <a:avLst>} element.
     *
     * @return an unmodifiable list of guide-definition elements; empty if no backing element is set
     */
    public List<Element> getGdElements() {
        if (avLstElement == null) {
            return List.of();
        }
        NodeList children = avLstElement.getElementsByTagNameNS(NS_A, "gd");
        List<Element> result = new ArrayList<>(children.getLength());
        for (int i = 0; i < children.getLength(); i++) {
            result.add((Element) children.item(i));
        }
        return result;
    }

    @Override
    public int size() {
        return getGdElements().size();
    }

    @Override
    public IAdjustValue get(int index) {
        List<Element> gdElements = getGdElements();
        if (index < 0 || index >= gdElements.size()) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of range");
        }
        return new AdjustValue(gdElements.get(index), saveCallback);
    }

    @Override
    public List<IAdjustValue> asICollection() {
        List<Element> gdElements = getGdElements();
        List<IAdjustValue> result = new ArrayList<>(gdElements.size());
        for (Element gd : gdElements) {
            result.add(new AdjustValue(gd, saveCallback));
        }
        return Collections.unmodifiableList(result);
    }

    @Override
    public Iterable<IAdjustValue> asIEnumerable() {
        return asICollection();
    }

    @Override
    public Iterator<IAdjustValue> iterator() {
        return asICollection().iterator();
    }
}
