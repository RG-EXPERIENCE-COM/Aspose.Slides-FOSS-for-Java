package org.aspose.slides.foss;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Represents collection of columns in a table.
 *
 * <p>Wraps OOXML {@code <a:tblGrid>} and {@code <a:tbl>} elements to manage
 * column definitions and their corresponding cells in each row.</p>
 */
public final class ColumnCollection implements IColumnCollection, Iterable<IColumn> {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";

    private Element tblElement;
    private Element tblGridElement;
    private Runnable saveCallback;
    private List<IColumn> columns;

    /**
     * Creates a new ColumnCollection backed by XML elements.
     *
     * @param tblElement     the {@code <a:tbl>} element containing rows
     * @param tblGridElement the {@code <a:tblGrid>} element containing gridCol definitions
     * @param saveCallback   callback invoked after mutations; may be {@code null}
     */
    public ColumnCollection(Element tblElement, Element tblGridElement, Runnable saveCallback) {
        this.tblElement = tblElement;
        this.tblGridElement = tblGridElement;
        this.saveCallback = saveCallback;
        this.columns = new ArrayList<>();
        rebuild();
    }

    /**
     * Creates a new ColumnCollection with the given columns (no XML backing).
     *
     * @param columns the columns
     */
    public ColumnCollection(List<IColumn> columns) {
        this.tblElement = null;
        this.tblGridElement = null;
        this.saveCallback = null;
        this.columns = columns != null ? new ArrayList<>(columns) : new ArrayList<>();
    }

    /**
     * Creates an empty ColumnCollection (no XML backing).
     */
    public ColumnCollection() {
        this((List<IColumn>) null);
    }

    /**
     * Initializes this collection with the given XML elements and context.
     *
     * <p>This method supports deferred initialization — call it on a default-constructed
     * instance to bind it to the underlying XML structure.</p>
     *
     * @param tblElement     the {@code <a:tbl>} element containing rows
     * @param tblGridElement the {@code <a:tblGrid>} element containing gridCol definitions
     * @param saveCallback   callback invoked after mutations; may be {@code null}
     * @return this collection, for method chaining
     */
    public ColumnCollection initInternal(Element tblElement, Element tblGridElement, Runnable saveCallback) {
        this.tblElement = tblElement;
        this.tblGridElement = tblGridElement;
        this.saveCallback = saveCallback;
        this.columns = new ArrayList<>();
        rebuild();
        return this;
    }

    /**
     * Rebuilds the column list from the XML grid elements.
     */
    public void rebuild() {
        columns = new ArrayList<>();
        if (tblGridElement == null) return;
        List<Element> gridCols = getChildElements(tblGridElement, "gridCol");
        for (int i = 0; i < gridCols.size(); i++) {
            Element gridCol = gridCols.get(i);
            List<ICell> cellsForColumn = collectCellsForColumn(i);
            columns.add(new Column(gridCol, saveCallback, cellsForColumn));
        }
    }

    /**
     * Collects cells at the given column index from each row.
     */
    private List<ICell> collectCellsForColumn(int colIndex) {
        List<ICell> cells = new ArrayList<>();
        if (tblElement == null) return cells;
        List<Element> rows = getChildElements(tblElement, "tr");
        for (Element tr : rows) {
            List<Element> tcs = getChildElements(tr, "tc");
            if (colIndex < tcs.size()) {
                cells.add(new Cell(tcs.get(colIndex), saveCallback));
            }
        }
        return cells;
    }

    @Override
    public IColumn get(int index) {
        return columns.get(index);
    }

    @Override
    public int size() {
        return columns.size();
    }

    @Override
    public List<IColumn> addClone(IColumn templ, boolean withAttachedColumns) {
        if (tblGridElement == null || tblElement == null) {
            return List.of();
        }

        // Find the template column's gridCol element and index
        int srcColIdx = findColumnIndex(templ);
        Element srcGridCol = getChildElements(tblGridElement, "gridCol").get(srcColIdx);

        // Clone and append the gridCol
        Element newGridCol = (Element) srcGridCol.cloneNode(true);
        tblGridElement.appendChild(newGridCol);

        // Clone cells: for each row, clone the tc at the template column index
        List<Element> rows = getChildElements(tblElement, "tr");
        for (Element tr : rows) {
            List<Element> tcs = getChildElements(tr, "tc");
            Element newTc;
            if (srcColIdx < tcs.size()) {
                newTc = (Element) tcs.get(srcColIdx).cloneNode(true);
            } else {
                newTc = makeEmptyTc(tr.getOwnerDocument());
            }
            tr.appendChild(newTc);
        }

        rebuild();
        save();
        return List.of(columns.get(columns.size() - 1));
    }

    @Override
    public List<IColumn> insertClone(int index, IColumn templ, boolean withAttachedColumns) {
        if (tblGridElement == null || tblElement == null) {
            return List.of();
        }

        int srcColIdx = findColumnIndex(templ);
        List<Element> gridCols = getChildElements(tblGridElement, "gridCol");
        Element srcGridCol = gridCols.get(srcColIdx);

        // Clone and insert gridCol at position
        Element newGridCol = (Element) srcGridCol.cloneNode(true);
        if (index < gridCols.size()) {
            tblGridElement.insertBefore(newGridCol, gridCols.get(index));
        } else {
            tblGridElement.appendChild(newGridCol);
        }

        // Clone cells in each row
        List<Element> rows = getChildElements(tblElement, "tr");
        for (Element tr : rows) {
            List<Element> tcs = getChildElements(tr, "tc");
            Element newTc;
            if (srcColIdx < tcs.size()) {
                newTc = (Element) tcs.get(srcColIdx).cloneNode(true);
            } else {
                newTc = makeEmptyTc(tr.getOwnerDocument());
            }
            if (index < tcs.size()) {
                tr.insertBefore(newTc, tcs.get(index));
            } else {
                tr.appendChild(newTc);
            }
        }

        rebuild();
        save();
        return List.of(columns.get(index));
    }

    @Override
    public void removeAt(int firstColumnIndex, boolean withAttachedRows) {
        if (tblGridElement != null && tblElement != null) {
            List<Element> gridCols = getChildElements(tblGridElement, "gridCol");
            if (firstColumnIndex < 0 || firstColumnIndex >= gridCols.size()) {
                throw new IndexOutOfBoundsException(
                        "Column index " + firstColumnIndex + " out of range");
            }
            tblGridElement.removeChild(gridCols.get(firstColumnIndex));

            // Remove corresponding tc from each row
            List<Element> rows = getChildElements(tblElement, "tr");
            for (Element tr : rows) {
                List<Element> tcs = getChildElements(tr, "tc");
                if (firstColumnIndex < tcs.size()) {
                    tr.removeChild(tcs.get(firstColumnIndex));
                }
            }

            rebuild();
            save();
        } else {
            if (firstColumnIndex < 0 || firstColumnIndex >= columns.size()) {
                throw new IndexOutOfBoundsException(
                        "Column index " + firstColumnIndex + " out of range");
            }
            columns.remove(firstColumnIndex);
        }
    }

    @Override
    public List<IColumn> asICollection() {
        return new ArrayList<>(columns);
    }

    @Override
    public Iterable<IColumn> asIEnumerable() {
        return Collections.unmodifiableList(columns);
    }

    /**
     * Returns an unmodifiable view of the internal column list.
     *
     * @return the columns as an unmodifiable list
     */
    public List<IColumn> asList() {
        return Collections.unmodifiableList(columns);
    }

    @Override
    public Iterator<IColumn> iterator() {
        return Collections.unmodifiableList(columns).iterator();
    }

    /**
     * Finds the index of the given column in the current collection.
     */
    private int findColumnIndex(IColumn col) {
        for (int i = 0; i < columns.size(); i++) {
            if (columns.get(i) == col) {
                return i;
            }
        }
        throw new IllegalArgumentException("Column not found in collection");
    }

    /**
     * Creates an empty {@code <a:tc>} element with txBody and tcPr.
     *
     * @param doc the owner document for creating elements
     * @return a new {@code <a:tc>} element with default child structure
     */
    public static Element makeEmptyTc(Document doc) {
        Element tc = doc.createElementNS(NS_A, "a:tc");
        Element txBody = doc.createElementNS(NS_A, "a:txBody");
        tc.appendChild(txBody);
        txBody.appendChild(doc.createElementNS(NS_A, "a:bodyPr"));
        txBody.appendChild(doc.createElementNS(NS_A, "a:lstStyle"));
        Element p = doc.createElementNS(NS_A, "a:p");
        txBody.appendChild(p);
        p.appendChild(doc.createElementNS(NS_A, "a:endParaRPr"));
        tc.appendChild(doc.createElementNS(NS_A, "a:tcPr"));
        return tc;
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
