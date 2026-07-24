package org.aspose.slides.foss;

import org.aspose.slides.foss.internal.BaseCollection;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a collection of paragraphs.
 *
 * <p>When bound to an OOXML {@code <a:txBody>} element, mutations
 * ({@link #add}, {@link #insert}, {@link #remove}, {@link #removeAt},
 * {@link #clear}) are synchronised to the DOM — same contract as
 * {@link PortionCollection} for runs.</p>
 */
public final class ParagraphCollection extends BaseCollection<IParagraph>
        implements IParagraphCollection, ISlideComponent, IPresentationComponent {

    private static final String NS_A =
            "http://schemas.openxmlformats.org/drawingml/2006/main";

    private final List<IParagraph> paragraphs = new ArrayList<>();

    private Element txbodyElement;
    private Object slidePart;
    private IBaseSlide parentSlide;
    private Runnable saveCallback;
    private boolean dynamic;

    /**
     * Creates an empty ParagraphCollection.
     */
    public ParagraphCollection() {
    }

    /**
     * Creates a ParagraphCollection with the given initial paragraphs.
     *
     * @param paragraphs the initial paragraphs
     */
    public ParagraphCollection(List<IParagraph> paragraphs) {
        if (paragraphs != null) {
            this.paragraphs.addAll(paragraphs);
        }
    }

    /**
     * Initialises this collection from an OOXML text-body element.
     *
     * <p>After this call, the collection operates in <em>dynamic</em> mode:
     * reads resolve {@code <a:p>} children from the supplied element on each
     * access.</p>
     *
     * @param txbodyElement the {@code <a:txBody>} XML element
     * @param slidePart     the OPC slide part that owns the paragraphs, or {@code null}
     * @param parentSlide   the parent slide, or {@code null}
     * @return this collection, for method chaining
     */
    public ParagraphCollection initInternal(Element txbodyElement,
                                            Object slidePart,
                                            IBaseSlide parentSlide) {
        this.txbodyElement = txbodyElement;
        this.slidePart = slidePart;
        this.parentSlide = parentSlide;
        this.dynamic = true;
        return this;
    }

    /**
     * Binds this collection to a text-body element for mutation sync.
     *
     * <p>Unlike {@link #initInternal}, this keeps an in-memory list as the
     * working set and writes add/remove/clear through to the DOM (used by
     * {@link TextFrame}).</p>
     *
     * @param txbodyElement the {@code <a:txBody>} XML element; may be {@code null}
     * @param saveCallback  callback invoked after DOM mutations; may be {@code null}
     * @param parentSlide   the parent slide; may be {@code null}
     * @return this collection
     */
    ParagraphCollection bind(Element txbodyElement, Runnable saveCallback, IBaseSlide parentSlide) {
        this.txbodyElement = txbodyElement;
        this.saveCallback = saveCallback;
        this.parentSlide = parentSlide;
        this.dynamic = false;
        return this;
    }

    /**
     * Builds and returns the list of paragraphs.
     *
     * <p>When the collection is in dynamic mode, paragraphs are resolved from
     * the {@code <a:p>} children of the text-body element on every call.
     * Otherwise returns the in-memory list.</p>
     *
     * @return a list of paragraphs
     */
    public List<IParagraph> getParagraphs() {
        if (dynamic && txbodyElement != null) {
            List<IParagraph> result = new ArrayList<>();
            NodeList children = txbodyElement.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                if (children.item(i) instanceof Element el
                        && NS_A.equals(el.getNamespaceURI())
                        && "p".equals(el.getLocalName())) {
                    Paragraph para = new Paragraph();
                    para.initInternal(el, txbodyElement, slidePart, parentSlide);
                    if (saveCallback != null) {
                        para.attachSaveCallback(saveCallback);
                    }
                    result.add(para);
                }
            }
            return result;
        }
        return List.copyOf(paragraphs);
    }

    /**
     * Sets the parent slide for this collection.
     *
     * @param parentSlide the parent slide
     */
    void setParentSlide(IBaseSlide parentSlide) {
        this.parentSlide = parentSlide;
    }

    private void save() {
        if (saveCallback != null) {
            saveCallback.run();
        }
    }

    private Element adoptElement(Element elem) {
        if (txbodyElement != null && elem.getOwnerDocument() != txbodyElement.getOwnerDocument()) {
            return (Element) txbodyElement.getOwnerDocument().importNode(elem, true);
        }
        return elem;
    }

    private List<Element> findParagraphElements() {
        List<Element> result = new ArrayList<>();
        if (txbodyElement == null) {
            return result;
        }
        NodeList children = txbodyElement.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i) instanceof Element el
                    && NS_A.equals(el.getNamespaceURI())
                    && "p".equals(el.getLocalName())) {
                result.add(el);
            }
        }
        return result;
    }

    /**
     * Attach a paragraph element to {@code txbodyElement}, rebinding the
     * {@link Paragraph} wrapper to the adopted node.
     */
    private void syncParagraphIntoDom(Paragraph p, int insertBeforeIndex) {
        Element pElem = p.getPElement();
        if (pElem == null || txbodyElement == null) {
            return;
        }
        if (pElem.getParentNode() == txbodyElement) {
            p.attachToTextBody(pElem, txbodyElement, saveCallback, slidePart, parentSlide);
            return;
        }
        pElem = adoptElement(pElem);
        List<Element> existing = findParagraphElements();
        if (insertBeforeIndex < 0 || insertBeforeIndex >= existing.size()) {
            txbodyElement.appendChild(pElem);
        } else {
            txbodyElement.insertBefore(pElem, existing.get(insertBeforeIndex));
        }
        p.attachToTextBody(pElem, txbodyElement, saveCallback, slidePart, parentSlide);
    }

    @Override
    public IParagraph get(int index) {
        return getParagraphs().get(index);
    }

    @Override
    public int size() {
        return getParagraphs().size();
    }

    @Override
    public int count() {
        return getParagraphs().size();
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Override
    public ISlideComponent asISlideComponent() {
        return this;
    }

    @Override
    public Iterable<IParagraph> asIEnumerable() {
        return getParagraphs();
    }

    @Override
    public IPresentationComponent asIPresentationComponent() {
        return this;
    }

    @Override
    public IBaseSlide getSlide() {
        return parentSlide;
    }

    @Override
    public IPresentation getPresentation() {
        if (parentSlide != null) {
            return parentSlide.getPresentation();
        }
        return null;
    }

    @Override
    public void add(IParagraph value) {
        if (txbodyElement != null && value instanceof Paragraph p) {
            syncParagraphIntoDom(p, -1);
            save();
        }
        paragraphs.add(value);
    }

    @Override
    public void insert(int index, IParagraph value) {
        if (txbodyElement != null && value instanceof Paragraph p) {
            syncParagraphIntoDom(p, index);
            save();
        }
        if (index >= paragraphs.size()) {
            paragraphs.add(value);
        } else {
            paragraphs.add(index, value);
        }
    }

    @Override
    public int indexOf(IParagraph item) {
        return getParagraphs().indexOf(item);
    }

    @Override
    public boolean contains(IParagraph item) {
        return getParagraphs().contains(item);
    }

    @Override
    public void clear() {
        if (txbodyElement != null) {
            for (Element el : findParagraphElements()) {
                txbodyElement.removeChild(el);
            }
            save();
        }
        paragraphs.clear();
    }

    @Override
    public void removeAt(int index) {
        List<IParagraph> view = getParagraphs();
        if (index < 0 || index >= view.size()) {
            return;
        }
        if (txbodyElement != null) {
            List<Element> elements = findParagraphElements();
            if (index < elements.size()) {
                txbodyElement.removeChild(elements.get(index));
                save();
            }
        }
        if (!dynamic && index < paragraphs.size()) {
            paragraphs.remove(index);
        }
    }

    @Override
    public boolean remove(IParagraph item) {
        int idx = paragraphs.indexOf(item);
        if (idx < 0 && !dynamic) {
            return false;
        }
        if (dynamic) {
            idx = getParagraphs().indexOf(item);
            if (idx < 0) {
                return false;
            }
            removeAt(idx);
            return true;
        }
        if (txbodyElement != null) {
            List<Element> elements = findParagraphElements();
            if (idx < elements.size()) {
                txbodyElement.removeChild(elements.get(idx));
                save();
            } else if (item instanceof Paragraph p && p.getPElement() != null
                    && p.getPElement().getParentNode() == txbodyElement) {
                txbodyElement.removeChild(p.getPElement());
                save();
            }
        }
        paragraphs.remove(idx);
        return true;
    }

    @Override
    public Iterator<IParagraph> iterator() {
        return getParagraphs().iterator();
    }

    /**
     * Returns the internal list (for framework use).
     *
     * @return the internal list
     */
    List<IParagraph> getInternalList() {
        return paragraphs;
    }
}
