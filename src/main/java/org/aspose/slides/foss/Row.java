package org.aspose.slides.foss;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a row in a table.
 *
 * <p>Extends {@link CellCollection} and adds height and formatting properties.
 * When backed by an OOXML {@code <a:tr>} element, height values are read from
 * and written to the element's {@code h} attribute.</p>
 */
public final class Row extends CellCollection implements IRow {

    private static final double EMU_PER_POINT = 12700.0;
    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";

    private Element trElement;
    private Runnable saveCallback;
    private int rowIndex;
    private ITable tableRef;
    private double height;
    private double minimalHeight;

    /**
     * Creates an empty Row with no XML backing.
     */
    public Row() {
        super();
        this.trElement = null;
        this.saveCallback = null;
    }

    /**
     * Creates a Row with the given cells and height, without XML backing.
     *
     * @param cells  the cells in this row
     * @param height the row height in points
     */
    public Row(List<ICell> cells, double height) {
        super(cells);
        this.trElement = null;
        this.saveCallback = null;
        this.height = height;
        this.minimalHeight = height;
    }

    /**
     * Creates an XML-backed Row from the given {@code <a:tr>} element.
     *
     * @param trElement    the tr XML element
     * @param cells        the cells in this row
     * @param saveCallback callback invoked after mutations; may be {@code null}
     */
    public Row(Element trElement, List<ICell> cells, Runnable saveCallback) {
        super(cells);
        this.trElement = trElement;
        this.saveCallback = saveCallback;
    }

    /**
     * Initializes this row from an {@code <a:tr>} XML element, parsing child
     * {@code <a:tc>} elements into {@link Cell} instances.
     *
     * @param trElement   the {@code <a:tr>} XML element
     * @param rowIndex    zero-based row index of this row in the table
     * @param slidePart   the slide part associated with this row
     * @param parentSlide the slide containing the table; may be {@code null}
     * @param table       the parent table; may be {@code null}
     * @return this row, for fluent chaining
     */
    public Row initInternal(Element trElement, int rowIndex, Object slidePart,
                            IBaseSlide parentSlide, ITable table) {
        this.trElement = trElement;
        this.rowIndex = rowIndex;
        this.tableRef = table;

        // Parse cells from <a:tc> children
        List<ICell> cells = new ArrayList<>();
        int colIdx = 0;
        NodeList children = trElement.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child instanceof Element el
                    && "tc".equals(el.getLocalName())
                    && NS_A.equals(el.getNamespaceURI())) {
                Cell cell = new Cell();
                cell.initInternal(el, rowIndex, colIdx, null, parentSlide, table);
                cells.add(cell);
                colIdx++;
            }
        }

        // Delegate cell storage and slide context to the parent CellCollection
        super.initInternal(cells, slidePart, parentSlide);
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * <p>When XML-backed, reads the {@code h} attribute from the {@code <a:tr>} element
     * and converts from EMU to points. Otherwise returns the value passed at construction.</p>
     */
    @Override
    public double getHeight() {
        if (trElement != null) {
            return readHeightFromElement();
        }
        return height;
    }

    /**
     * {@inheritDoc}
     *
     * <p>When XML-backed, reads the {@code h} attribute from the {@code <a:tr>} element
     * and converts from EMU to points. Otherwise returns the stored minimal height.</p>
     */
    @Override
    public double getMinimalHeight() {
        if (trElement != null) {
            return readHeightFromElement();
        }
        return minimalHeight;
    }

    /**
     * {@inheritDoc}
     *
     * <p>When XML-backed, writes the value back to the {@code h} attribute (converting
     * from points to EMU) and triggers the save callback.</p>
     */
    @Override
    public void setMinimalHeight(double value) {
        if (trElement != null) {
            trElement.setAttribute("h", String.valueOf(Math.round(value * EMU_PER_POINT)));
            if (saveCallback != null) {
                saveCallback.run();
            }
        } else {
            this.minimalHeight = value;
        }
    }

    /** {@inheritDoc} */
    @Override
    public IRowFormat getRowFormat() {
        return new RowFormat();
    }

    /** {@inheritDoc} */
    @Override
    public ICellCollection getAsICellCollection() {
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public IBulkTextFormattable getAsIBulkTextFormattable() {
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException if {@code source} is {@code null}
     */
    @Override
    public void setTextFormat(Object source) {
        if (source == null) {
            throw new IllegalArgumentException("setTextFormat requires a non-null source");
        }
        // Bulk text formatting applied to child cells
    }

    /**
     * Returns the backing {@code <a:tr>} XML element, or {@code null} if not XML-backed.
     *
     * @return the tr element
     */
    Element getTrElement() {
        return trElement;
    }

    private double readHeightFromElement() {
        String h = trElement.getAttribute("h");
        if (h != null && !h.isEmpty()) {
            try {
                return Long.parseLong(h) / EMU_PER_POINT;
            } catch (NumberFormatException e) {
                return 0.0;
            }
        }
        return 0.0;
    }
}
