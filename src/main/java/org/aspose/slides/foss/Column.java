package org.aspose.slides.foss;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a column in a table.
 *
 * <p>Wraps an OOXML {@code <a:gridCol>} element.</p>
 */
public final class Column implements IColumn {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";
    private static final double EMU_PER_POINT = 12700.0;

    private Element gridColElement;
    private Runnable saveCallback;
    private List<ICell> cells;
    private int colIndex;
    private IBaseSlide parentSlide;
    private ITable table;

    /**
     * Creates a new Column backed by the given {@code <a:gridCol>} element.
     *
     * @param gridColElement the gridCol XML element
     * @param saveCallback   callback invoked after mutations; may be {@code null}
     * @param cells          the cells in this column
     */
    public Column(Element gridColElement, Runnable saveCallback, List<ICell> cells) {
        this.gridColElement = gridColElement;
        this.saveCallback = saveCallback;
        this.cells = cells != null ? new ArrayList<>(cells) : new ArrayList<>();
    }

    /**
     * Creates a Column with no backing element.
     */
    public Column() {
        this(null, null, null);
    }

    /**
     * Initializes this column from the given {@code <a:gridCol>} element and table context.
     *
     * <p>Parses cells by iterating over {@code <a:tr>} rows in the table element and
     * extracting the {@code <a:tc>} at this column's index. A {@link Cell} is created
     * for each row that has a cell at the specified column index.</p>
     *
     * @param gridColElement the {@code <a:gridCol>} XML element backing this column
     * @param colIndex       zero-based column index within the table grid
     * @param tblElement     the {@code <a:tbl>} XML element containing the table rows
     * @param saveCallback   callback invoked after mutations; may be {@code null}
     * @param parentSlide    the slide containing the table; may be {@code null}
     * @param table          the parent table; may be {@code null}
     * @return this column, for fluent chaining
     */
    public Column initInternal(Element gridColElement, int colIndex, Element tblElement,
                               Runnable saveCallback, IBaseSlide parentSlide, ITable table) {
        this.gridColElement = gridColElement;
        this.colIndex = colIndex;
        this.saveCallback = saveCallback;
        this.parentSlide = parentSlide;
        this.table = table;

        List<ICell> parsedCells = new ArrayList<>();
        int rowIdx = 0;
        NodeList children = tblElement.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i) instanceof Element tr
                    && NS_A.equals(tr.getNamespaceURI())
                    && "tr".equals(tr.getLocalName())) {
                List<Element> tcs = findChildren(tr, "tc");
                if (colIndex < tcs.size()) {
                    Cell cell = new Cell();
                    cell.initInternal(tcs.get(colIndex), rowIdx, colIndex,
                            saveCallback, parentSlide, table);
                    parsedCells.add(cell);
                }
                rowIdx++;
            }
        }
        this.cells = parsedCells;
        return this;
    }

    private static List<Element> findChildren(Element parent, String localName) {
        List<Element> result = new ArrayList<>();
        NodeList children = parent.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i) instanceof Element el
                    && NS_A.equals(el.getNamespaceURI())
                    && localName.equals(el.getLocalName())) {
                result.add(el);
            }
        }
        return result;
    }

    private void save() {
        if (saveCallback != null) saveCallback.run();
    }

    @Override
    public double getWidth() {
        if (gridColElement == null) return 0.0;
        String w = gridColElement.getAttribute("w");
        if (w == null || w.isEmpty()) return 0.0;
        return Long.parseLong(w) / EMU_PER_POINT;
    }

    @Override
    public void setWidth(double value) {
        if (gridColElement == null) return;
        gridColElement.setAttribute("w", String.valueOf(Math.round(value * EMU_PER_POINT)));
        save();
    }

    @Override
    public ICell get(int index) {
        return cells.get(index);
    }

    @Override
    public int size() {
        return cells.size();
    }

    @Override
    public ICellCollection getAsICellCollection() {
        return this;
    }

    @Override
    public IBulkTextFormattable getAsIBulkTextFormattable() {
        return this;
    }

    @Override
    public IColumnFormat getColumnFormat() {
        return new ColumnFormat();
    }

    @Override
    public void setTextFormat(Object source) {
        // Simplified implementation
    }

    @Override
    public List<ICell> asICollection() {
        return new ArrayList<>(cells);
    }

    @Override
    public Iterable<ICell> asIEnumerable() {
        return Collections.unmodifiableList(cells);
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
    public ISlideComponent asISlideComponent() {
        return this;
    }

    @Override
    public IPresentationComponent asIPresentationComponent() {
        return this;
    }
}
