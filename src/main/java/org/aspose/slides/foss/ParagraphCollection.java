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
 * <p>When backed by an OOXML {@code <a:txBody>} element, paragraphs are
 * resolved dynamically from the XML on each access. Otherwise, the
 * collection operates on an in-memory list.</p>
 */
public final class ParagraphCollection extends BaseCollection<IParagraph>
        implements IParagraphCollection, ISlideComponent, IPresentationComponent {

    private static final String NS_A =
            "http://schemas.openxmlformats.org/drawingml/2006/main";

    private final List<IParagraph> paragraphs = new ArrayList<>();

    private Element txbodyElement;
    private Object slidePart;
    private IBaseSlide parentSlide;

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
     * <p>After this call, {@link #getParagraphs()} dynamically resolves
     * {@code <a:p>} children from the supplied element.</p>
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
        return this;
    }

    /**
     * Builds and returns the list of paragraphs.
     *
     * <p>When the collection is backed by an XML element, paragraphs are
     * resolved from the {@code <a:p>} children of the text-body element
     * on every call. Otherwise, returns the in-memory list.</p>
     *
     * @return a list of paragraphs
     */
    public List<IParagraph> getParagraphs() {
        if (txbodyElement == null) {
            return List.copyOf(paragraphs);
        }
        List<IParagraph> result = new ArrayList<>();
        NodeList children = txbodyElement.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i) instanceof Element el
                    && NS_A.equals(el.getNamespaceURI())
                    && "p".equals(el.getLocalName())) {
                Paragraph para = new Paragraph();
                para.initInternal(el, txbodyElement, slidePart, parentSlide);
                result.add(para);
            }
        }
        return result;
    }

    /**
     * Sets the parent slide for this collection.
     *
     * @param parentSlide the parent slide
     */
    void setParentSlide(IBaseSlide parentSlide) {
        this.parentSlide = parentSlide;
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
        paragraphs.add(value);
    }

    @Override
    public void insert(int index, IParagraph value) {
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
        paragraphs.clear();
    }

    @Override
    public void removeAt(int index) {
        if (index >= 0 && index < paragraphs.size()) {
            paragraphs.remove(index);
        }
    }

    @Override
    public boolean remove(IParagraph item) {
        return paragraphs.remove(item);
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
