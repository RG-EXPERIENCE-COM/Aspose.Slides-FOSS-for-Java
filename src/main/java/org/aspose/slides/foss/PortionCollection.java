package org.aspose.slides.foss;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a collection of text portions within a paragraph.
 *
 * <p>When backed by an XML {@code <a:p>} element, mutations are
 * synchronised to the DOM and a save callback is invoked.</p>
 */
public final class PortionCollection implements IPortionCollection {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";

    private final List<IPortion> portions = new ArrayList<>();
    private Element pElement;
    private Element txBodyElement;
    private Object slidePart;
    private IBaseSlide parentSlide;
    private Runnable saveCallback;
    private boolean dynamic;

    /**
     * Creates an empty PortionCollection.
     */
    public PortionCollection() {
    }

    /**
     * Creates a PortionCollection with the given initial portions.
     *
     * @param portions the initial portions
     */
    public PortionCollection(List<IPortion> portions) {
        if (portions != null) {
            this.portions.addAll(portions);
        }
    }

    /**
     * Creates an XML-backed PortionCollection.
     *
     * @param pElement     the {@code <a:p>} XML element; may be {@code null}
     * @param saveCallback callback invoked after mutations; may be {@code null}
     */
    public PortionCollection(Element pElement, Runnable saveCallback) {
        this.pElement = pElement;
        this.saveCallback = saveCallback;
    }

    /**
     * Initialises this collection with full XML context.
     *
     * <p>After calling this method the collection operates in <em>dynamic</em>
     * mode: {@link #get(int)}, {@link #size()}, {@link #count()} and
     * {@link #iterator()} read portions directly from the underlying
     * {@code <a:p>} element on every call.</p>
     *
     * @param pElement      the {@code <a:p>} paragraph element; may be {@code null}
     * @param txBodyElement the {@code <a:txBody>} element containing the paragraph
     * @param slidePart     the slide part that owns this portion's XML
     * @param parentSlide   the parent slide
     * @return this collection, for fluent chaining
     */
    PortionCollection initInternal(Element pElement, Element txBodyElement,
                                   Object slidePart, IBaseSlide parentSlide) {
        this.pElement = pElement;
        this.txBodyElement = txBodyElement;
        this.slidePart = slidePart;
        this.parentSlide = parentSlide;
        this.dynamic = true;
        return this;
    }

    /**
     * Builds a list of {@link Portion} objects from the {@code <a:r>} children
     * of the backing {@code <a:p>} element.
     *
     * <p>Each call re-reads the DOM, so the returned list always reflects the
     * current XML state.</p>
     *
     * @return an unmodifiable snapshot of portions; empty list if
     *         {@code pElement} is {@code null}
     */
    List<IPortion> getPortions() {
        if (pElement == null) {
            return List.of();
        }
        List<IPortion> result = new ArrayList<>();
        for (Element rElem : findRunElements()) {
            Portion p = new Portion();
            p.initInternal(rElem, pElement, txBodyElement, slidePart, parentSlide);
            result.add(p);
        }
        return Collections.unmodifiableList(result);
    }

    private void save() {
        if (saveCallback != null) {
            saveCallback.run();
        }
    }

    /**
     * Imports the given element into the same document as {@code pElement},
     * if it belongs to a different document.
     */
    private Element adoptElement(Element elem) {
        if (pElement != null && elem.getOwnerDocument() != pElement.getOwnerDocument()) {
            return (Element) pElement.getOwnerDocument().importNode(elem, true);
        }
        return elem;
    }

    /**
     * Finds the first child element of {@code pElement} with the given local name
     * in the DrawingML namespace.
     */
    private Element findChild(String localName) {
        if (pElement == null) return null;
        NodeList children = pElement.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i) instanceof Element el
                    && NS_A.equals(el.getNamespaceURI())
                    && localName.equals(el.getLocalName())) {
                return el;
            }
        }
        return null;
    }

    /**
     * Returns all {@code <a:r>} child elements of {@code pElement}.
     */
    private List<Element> findRunElements() {
        List<Element> runs = new ArrayList<>();
        if (pElement == null) return runs;
        NodeList children = pElement.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i) instanceof Element el
                    && NS_A.equals(el.getNamespaceURI())
                    && "r".equals(el.getLocalName())) {
                runs.add(el);
            }
        }
        return runs;
    }

    /**
     * Returns the effective list of portions — dynamic from XML when
     * {@link #initInternal} was used, otherwise the internal cached list.
     */
    private List<IPortion> effectivePortions() {
        return dynamic ? getPortions() : portions;
    }

    @Override
    public IPortion get(int index) {
        return effectivePortions().get(index);
    }

    @Override
    public int size() {
        return effectivePortions().size();
    }

    @Override
    public int count() {
        return effectivePortions().size();
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Override
    public Iterable<IPortion> asIEnumerable() {
        return dynamic ? getPortions() : Collections.unmodifiableList(portions);
    }

    @Override
    public void add(IPortion value) {
        portions.add(value);
        if (pElement != null && value instanceof Portion p) {
            Element rElem = p.getRunElement();
            // Only insert into XML if not already a child of pElement
            if (rElem.getParentNode() != pElement) {
                rElem = adoptElement(rElem);
                Element endPara = findChild("endParaRPr");
                if (endPara != null) {
                    pElement.insertBefore(rElem, endPara);
                } else {
                    pElement.appendChild(rElem);
                }
                p.initInternal(rElem, saveCallback);
            }
            save();
        }
    }

    @Override
    public int indexOf(IPortion item) {
        return portions.indexOf(item);
    }

    @Override
    public void insert(int index, IPortion value) {
        if (pElement != null && value instanceof Portion p) {
            Element rElem = adoptElement(p.getRunElement());
            List<Element> runElements = findRunElements();
            if (index >= runElements.size()) {
                Element endPara = findChild("endParaRPr");
                if (endPara != null) {
                    pElement.insertBefore(rElem, endPara);
                } else {
                    pElement.appendChild(rElem);
                }
            } else {
                pElement.insertBefore(rElem, runElements.get(index));
            }
            p.initInternal(rElem, saveCallback);
            save();
        }
        if (index >= portions.size()) {
            portions.add(value);
        } else {
            portions.add(index, value);
        }
    }

    @Override
    public void clear() {
        if (pElement != null) {
            for (Element rElem : findRunElements()) {
                pElement.removeChild(rElem);
            }
            save();
        }
        portions.clear();
    }

    @Override
    public boolean contains(IPortion item) {
        return portions.contains(item);
    }

    @Override
    public boolean remove(IPortion item) {
        int idx = portions.indexOf(item);
        if (idx < 0) return false;
        if (pElement != null) {
            List<Element> runElements = findRunElements();
            if (idx < runElements.size()) {
                pElement.removeChild(runElements.get(idx));
                save();
            }
        }
        portions.remove(idx);
        return true;
    }

    @Override
    public void removeAt(int index) {
        if (index < 0 || index >= portions.size()) return;
        if (pElement != null) {
            List<Element> runElements = findRunElements();
            if (index < runElements.size()) {
                pElement.removeChild(runElements.get(index));
                save();
            }
        }
        portions.remove(index);
    }

    @Override
    public Iterator<IPortion> iterator() {
        return dynamic ? getPortions().iterator() : List.copyOf(portions).iterator();
    }

    /**
     * Returns the internal list (for framework use).
     *
     * @return the internal list
     */
    List<IPortion> getInternalList() {
        return portions;
    }
}
