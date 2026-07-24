package org.aspose.slides.foss;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Represents a text frame containing paragraphs.
 *
 * <p>Wraps an OOXML {@code <a:txBody>} element and provides access
 * to its paragraph collection and text frame format.</p>
 */
public final class TextFrame implements ITextFrame {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";

    private Element txBodyElement;
    private Runnable saveCallback;
    private ParagraphCollection paragraphs;
    private TextFrameFormat textFrameFormat;
    private IBaseSlide parentSlide;
    private IShape parentShape;
    private ICell parentCell;

    /**
     * Creates a TextFrame backed by the given text body element.
     *
     * @param txBodyElement the {@code <a:txBody>} XML element
     * @param saveCallback  callback invoked after mutations; may be {@code null}
     */
    public TextFrame(Element txBodyElement, Runnable saveCallback) {
        this.txBodyElement = txBodyElement;
        this.saveCallback = saveCallback;
        loadParagraphs();
    }

    /**
     * Creates a TextFrame backed by the given text body element with parent context.
     *
     * @param txBodyElement the {@code <a:txBody>} XML element
     * @param saveCallback  callback invoked after mutations; may be {@code null}
     * @param parentSlide   the parent slide; may be {@code null}
     * @param parentShape   the parent shape; may be {@code null}
     */
    public TextFrame(Element txBodyElement, Runnable saveCallback,
                     IBaseSlide parentSlide, IShape parentShape) {
        this.txBodyElement = txBodyElement;
        this.saveCallback = saveCallback;
        this.parentSlide = parentSlide;
        this.parentShape = parentShape;
        loadParagraphs();
    }

    /**
     * Initializes this text frame with the given backing element and parent context.
     *
     * <p>This is a post-construction initializer that sets (or resets) the backing
     * XML element, save callback, parent slide, and optional parent shape, then
     * reloads the paragraph collection from the element. Returns {@code this}
     * for method chaining.</p>
     *
     * @param txBodyElement the {@code <a:txBody>} XML element
     * @param saveCallback  callback invoked after mutations; may be {@code null}
     * @param parentSlide   the parent slide
     * @param parentShape   the parent shape; may be {@code null}
     * @return this text frame instance
     */
    TextFrame initInternal(Element txBodyElement, Runnable saveCallback,
                           IBaseSlide parentSlide, IShape parentShape) {
        this.txBodyElement = txBodyElement;
        this.saveCallback = saveCallback;
        this.parentSlide = parentSlide;
        this.parentShape = parentShape;
        this.textFrameFormat = null;
        loadParagraphs();
        return this;
    }

    /**
     * Sets the parent slide for this text frame.
     *
     * @param parentSlide the parent slide
     */
    void setParentSlide(IBaseSlide parentSlide) {
        this.parentSlide = parentSlide;
    }

    /**
     * Sets the parent shape for this text frame.
     *
     * @param parentShape the parent shape
     */
    void setParentShape(IShape parentShape) {
        this.parentShape = parentShape;
    }

    /**
     * Sets the parent cell for this text frame.
     *
     * @param parentCell the parent cell
     */
    void setParentCell(ICell parentCell) {
        this.parentCell = parentCell;
    }

    private void loadParagraphs() {
        paragraphs = new ParagraphCollection();
        paragraphs.bind(txBodyElement, this::save, parentSlide);
        if (txBodyElement == null) {
            return;
        }
        NodeList children = txBodyElement.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i) instanceof Element el
                    && NS_A.equals(el.getNamespaceURI())
                    && "p".equals(el.getLocalName())) {
                // Already a DOM child — add() will rebind without re-inserting.
                paragraphs.add(new Paragraph(el, this::save));
            }
        }
    }

    private void save() {
        if (saveCallback != null) saveCallback.run();
    }

    /**
     * Returns the paragraph collection.
     *
     * @return the paragraphs
     */
    public IParagraphCollection getParagraphs() {
        return paragraphs;
    }

    /**
     * Returns the text frame format.
     *
     * @return the text frame format
     */
    public ITextFrameFormat getTextFrameFormat() {
        if (textFrameFormat == null) {
            textFrameFormat = new TextFrameFormat(txBodyElement, this::save);
        }
        return textFrameFormat;
    }

    @Override
    public String getText() {
        var sb = new StringBuilder();
        for (int i = 0; i < paragraphs.size(); i++) {
            if (i > 0) sb.append('\n');
            sb.append(paragraphs.get(i).getText());
        }
        return sb.toString();
    }

    @Override
    public void setText(String text) {
        paragraphs.clear();
        // Remove existing <a:p> elements from the DOM
        if (txBodyElement != null) {
            var toRemove = new java.util.ArrayList<Element>();
            NodeList children = txBodyElement.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                if (children.item(i) instanceof Element el
                        && NS_A.equals(el.getNamespaceURI())
                        && "p".equals(el.getLocalName())) {
                    toRemove.add(el);
                }
            }
            for (Element el : toRemove) {
                txBodyElement.removeChild(el);
            }
        }
        if (text != null) {
            String[] lines = text.split("\n", -1);
            for (String line : lines) {
                if (txBodyElement != null) {
                    // Create paragraph element in the same DOM
                    var doc = txBodyElement.getOwnerDocument();
                    Element pEl = doc.createElementNS(NS_A, "a:p");
                    if (!line.isEmpty()) {
                        Element rEl = doc.createElementNS(NS_A, "a:r");
                        Element tEl = doc.createElementNS(NS_A, "a:t");
                        tEl.setTextContent(line);
                        rEl.appendChild(tEl);
                        pEl.appendChild(rEl);
                    }
                    txBodyElement.appendChild(pEl);
                    var p = new Paragraph(pEl, this::save);
                    paragraphs.add(p);
                } else {
                    var p = new Paragraph();
                    p.setText(line);
                    paragraphs.add(p);
                }
            }
        }
        save();
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
    public IPresentationComponent asIPresentationComponent() {
        return this;
    }

    @Override
    public IShape getParentShape() {
        return parentShape;
    }

    @Override
    public ICell getParentCell() {
        return parentCell;
    }

    @Override
    public ISlideComponent asISlideComponent() {
        return this;
    }
}
