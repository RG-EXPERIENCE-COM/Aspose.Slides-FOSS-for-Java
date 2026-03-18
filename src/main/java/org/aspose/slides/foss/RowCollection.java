package org.aspose.slides.foss;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a collection of rows in a table.
 *
 * <p>When backed by an OOXML {@code <a:tbl>} element, row mutations
 * (add, insert, remove) are reflected in the underlying XML and trigger
 * a save callback.</p>
 */
public final class RowCollection implements IRowCollection, Iterable<IRow> {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";

    private Element tblElement;
    private Runnable saveCallback;
    private List<IRow> rows;

    /**
     * Creates an empty RowCollection with no XML backing.
     */
    public RowCollection() {
        this.tblElement = null;
        this.saveCallback = null;
        this.rows = new ArrayList<>();
    }

    /**
     * Creates a RowCollection with the given initial rows (no XML backing).
     *
     * @param rows the initial rows
     */
    public RowCollection(List<IRow> rows) {
        this.tblElement = null;
        this.saveCallback = null;
        this.rows = rows != null ? new ArrayList<>(rows) : new ArrayList<>();
    }

    /**
     * Creates a new RowCollection backed by an XML {@code <a:tbl>} element.
     *
     * @param tblElement   the {@code <a:tbl>} element containing row elements
     * @param saveCallback callback invoked after mutations; may be {@code null}
     */
    public RowCollection(Element tblElement, Runnable saveCallback) {
        this.tblElement = tblElement;
        this.saveCallback = saveCallback;
        this.rows = new ArrayList<>();
        rebuild();
    }

    /**
     * Initializes this collection with the given XML element and save callback.
     *
     * <p>This method supports deferred initialization — call it on a default-constructed
     * instance to bind it to the underlying XML structure.</p>
     *
     * @param tblElement   the {@code <a:tbl>} element containing row elements
     * @param saveCallback callback invoked after mutations; may be {@code null}
     * @return this collection, for method chaining
     */
    public RowCollection initInternal(Element tblElement, Runnable saveCallback) {
        this.tblElement = tblElement;
        this.saveCallback = saveCallback;
        this.rows = new ArrayList<>();
        rebuild();
        return this;
    }

    /**
     * Rebuilds the row list from the XML {@code <a:tr>} child elements.
     */
    public void rebuild() {
        rows = new ArrayList<>();
        if (tblElement == null) return;
        List<Element> trElements = getChildElements(tblElement, "tr");
        for (Element tr : trElements) {
            List<ICell> cells = collectCellsForRow(tr);
            rows.add(new Row(tr, cells, saveCallback));
        }
    }

    /**
     * Collects cells from the {@code <a:tc>} children of a row element.
     */
    private List<ICell> collectCellsForRow(Element trElement) {
        List<ICell> cells = new ArrayList<>();
        List<Element> tcs = getChildElements(trElement, "tc");
        for (Element tc : tcs) {
            cells.add(new Cell(tc, saveCallback));
        }
        return cells;
    }

    @Override
    public IRow get(int index) {
        return rows.get(index);
    }

    @Override
    public int size() {
        return rows.size();
    }

    @Override
    public IRow[] addClone(IRow templ, boolean withAttachedRows) {
        if (tblElement != null) {
            Element srcTr = getTemplateTrElement(templ);
            Element newTr = (Element) srcTr.cloneNode(true);
            tblElement.appendChild(newTr);
            rebuild();
            save();
            return new IRow[]{rows.get(rows.size() - 1)};
        }
        Row clone = new Row();
        rows.add(clone);
        return new IRow[]{clone};
    }

    @Override
    public IRow[] insertClone(int index, IRow templ, boolean withAttachedRows) {
        if (tblElement != null) {
            Element srcTr = getTemplateTrElement(templ);
            Element newTr = (Element) srcTr.cloneNode(true);
            List<Element> trs = getChildElements(tblElement, "tr");
            if (index < trs.size()) {
                tblElement.insertBefore(newTr, trs.get(index));
            } else {
                tblElement.appendChild(newTr);
            }
            rebuild();
            save();
            return new IRow[]{rows.get(index)};
        }
        Row clone = new Row();
        rows.add(index, clone);
        return new IRow[]{clone};
    }

    @Override
    public void removeAt(int firstRowIndex, boolean withAttachedRows) {
        if (tblElement != null) {
            List<Element> trs = getChildElements(tblElement, "tr");
            if (firstRowIndex < 0 || firstRowIndex >= trs.size()) {
                throw new IndexOutOfBoundsException(
                        "Row index " + firstRowIndex + " out of range");
            }
            tblElement.removeChild(trs.get(firstRowIndex));
            rebuild();
            save();
        } else {
            if (firstRowIndex >= 0 && firstRowIndex < rows.size()) {
                rows.remove(firstRowIndex);
            }
        }
    }

    @Override
    public List<IRow> asICollection() {
        return new ArrayList<>(rows);
    }

    @Override
    public Iterable<IRow> asIEnumerable() {
        return Collections.unmodifiableList(rows);
    }

    @Override
    public Iterator<IRow> iterator() {
        return Collections.unmodifiableList(rows).iterator();
    }

    /**
     * Returns the internal list (for framework use).
     *
     * @return the internal list
     */
    List<IRow> getInternalList() {
        return rows;
    }

    /**
     * Extracts the {@code <a:tr>} element from the given row template.
     * Falls back to finding the row in the current collection if no element is available.
     */
    private Element getTemplateTrElement(IRow templ) {
        if (templ instanceof Row row && row.getTrElement() != null) {
            return row.getTrElement();
        }
        // For non-XML-backed rows used as templates, find in the collection
        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i) == templ && rows.get(i) instanceof Row row) {
                if (row.getTrElement() != null) {
                    return row.getTrElement();
                }
            }
        }
        throw new IllegalArgumentException("Template row has no XML element to clone");
    }

    /**
     * Returns direct child elements with the given local name in the DrawingML namespace.
     */
    private static List<Element> getChildElements(Element parent, String localName) {
        List<Element> result = new ArrayList<>();
        NodeList children = parent.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child instanceof Element el
                    && localName.equals(el.getLocalName())
                    && NS_A.equals(el.getNamespaceURI())) {
                result.add(el);
            }
        }
        return result;
    }

    private void save() {
        if (saveCallback != null) {
            saveCallback.run();
        }
    }
}
