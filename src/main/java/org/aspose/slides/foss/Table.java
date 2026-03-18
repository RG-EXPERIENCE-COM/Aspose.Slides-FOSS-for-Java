package org.aspose.slides.foss;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a table shape on a slide.
 *
 * <p>Extends {@link GeometryShape} and provides access to rows, columns,
 * formatting, and style properties.</p>
 */
public final class Table extends GeometryShape implements ITable {

    private RowCollection rows;
    private ColumnCollection columns;
    private Element tblElement;
    private Element tblPr;
    private Element tblGrid;
    private TableFormat tableFormat;
    private TableStylePreset stylePreset = TableStylePreset.NONE;
    private boolean rightToLeft;
    private boolean firstRow;
    private boolean firstCol;
    private boolean lastRow;
    private boolean lastCol;
    private boolean horizontalBanding;
    private boolean verticalBanding;
    private IBaseSlide parentSlide;

    /**
     * Creates an empty Table.
     */
    public Table() {
        super();
        this.rows = new RowCollection();
        this.columns = new ColumnCollection();
    }

    /**
     * Creates a Table backed by the given XML element (a {@code <p:graphicFrame>}).
     *
     * @param xmlElement   the graphicFrame XML element
     * @param saveCallback callback invoked after mutations; may be {@code null}
     */
    public Table(Element xmlElement, Runnable saveCallback) {
        super(xmlElement, saveCallback);
        this.tblElement = findTblElement(xmlElement);
        if (tblElement != null) {
            this.tblPr = findChildElement(tblElement, NS_A, "tblPr");
            this.tblGrid = findChildElement(tblElement, NS_A, "tblGrid");
            parseTableProperties();
            buildRowsAndColumns(saveCallback);
        } else {
            this.rows = new RowCollection();
            this.columns = new ColumnCollection();
        }
    }

    /**
     * Locates the {@code <a:tbl>} element within a graphicFrame.
     */
    private static Element findTblElement(Element graphicFrame) {
        if (graphicFrame == null) return null;
        // Navigate: graphicFrame -> a:graphic -> a:graphicData -> a:tbl
        Element graphic = findChildElement(graphicFrame, NS_A, "graphic");
        if (graphic == null) return null;
        Element graphicData = findChildElement(graphic, NS_A, "graphicData");
        if (graphicData == null) return null;
        return findChildElement(graphicData, NS_A, "tbl");
    }

    private static Element findChildElement(Element parent, String nsUri, String localName) {
        NodeList children = parent.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child instanceof Element el
                    && localName.equals(el.getLocalName())
                    && nsUri.equals(el.getNamespaceURI())) {
                return el;
            }
        }
        return null;
    }

    /**
     * Two-phase initialization for this table shape.
     *
     * <p>Binds this table to its XML element and locates the {@code <a:tbl>},
     * {@code <a:tblPr>}, and {@code <a:tblGrid>} elements within the graphic frame.</p>
     *
     * @param xmlElement   the XML element representing this table shape
     * @param saveCallback callback invoked after mutations; may be {@code null}
     * @param parentSlide  the parent slide object; may be {@code null}
     */
    public void initInternal(Element xmlElement, Runnable saveCallback,
                             IBaseSlide parentSlide) {
        this.xmlElement = xmlElement;
        this.saveCallback = saveCallback;
        this.parentSlide = parentSlide;
        this.tblElement = findTblElement(xmlElement);
        if (this.tblElement != null) {
            this.tblPr = findChildElement(tblElement, NS_A, "tblPr");
            this.tblGrid = findChildElement(tblElement, NS_A, "tblGrid");
        }
    }

    /**
     * Returns the cached {@code <a:tblPr>} element, or {@code null} if not present.
     *
     * @return the table properties element, or {@code null}
     */
    Element getTblPr() {
        return tblPr;
    }

    /**
     * Returns the {@code <a:tblPr>} element, creating it as the first child
     * of {@code <a:tbl>} if it does not already exist.
     *
     * @return the table properties element; never {@code null}
     * @throws IllegalStateException if no backing {@code <a:tbl>} element exists
     */
    Element ensureTblPr() {
        if (tblPr != null) {
            return tblPr;
        }
        if (tblElement == null) {
            throw new IllegalStateException("ensureTblPr requires a backing <a:tbl> element");
        }
        Document doc = tblElement.getOwnerDocument();
        tblPr = doc.createElementNS(NS_A, "a:tblPr");
        tblElement.insertBefore(tblPr, tblElement.getFirstChild());
        return tblPr;
    }

    /**
     * Reads a boolean attribute from the {@code <a:tblPr>} element.
     *
     * <p>Returns {@code true} if the attribute value is {@code "1"},
     * {@code false} otherwise (including when tblPr is absent).</p>
     *
     * @param attrName the attribute name to read
     * @return {@code true} if the attribute is {@code "1"}
     */
    boolean getBoolAttr(String attrName) {
        if (tblPr == null) {
            return false;
        }
        String val = tblPr.getAttribute(attrName);
        return "1".equals(val);
    }

    /**
     * Sets or removes a boolean attribute on the {@code <a:tblPr>} element.
     *
     * <p>If {@code value} is {@code true}, sets the attribute to {@code "1"}.
     * If {@code false}, removes the attribute. Creates the tblPr element if
     * necessary. Invokes the save callback after mutation.</p>
     *
     * @param attrName the attribute name to set or remove
     * @param value    the boolean value
     */
    void setBoolAttr(String attrName, boolean value) {
        Element pr = ensureTblPr();
        if (value) {
            pr.setAttribute(attrName, "1");
        } else {
            pr.removeAttribute(attrName);
        }
        if (saveCallback != null) {
            saveCallback.run();
        }
    }

    /**
     * Finds the table style element within the given {@code <a:tblPr>}.
     *
     * <p>Checks for {@code <a:tableStyleId>} first, then falls back to
     * the legacy {@code <a:tblStyle>} element.</p>
     *
     * @param tblPrElement the table properties element to search
     * @return the style element, or {@code null} if neither is present
     */
    Element findStyleElement(Element tblPrElement) {
        if (tblPrElement == null) {
            return null;
        }
        Element el = findChildElement(tblPrElement, NS_A, "tableStyleId");
        if (el != null) {
            return el;
        }
        return findChildElement(tblPrElement, NS_A, "tblStyle");
    }

    /**
     * Reads a GUID string from a table style element.
     *
     * <p>Handles two formats:
     * <ul>
     *   <li>{@code <a:tableStyleId>} — GUID stored as text content</li>
     *   <li>{@code <a:tblStyle>} — GUID stored in the {@code val} attribute</li>
     * </ul>
     *
     * @param styleEl the style element to read from; may be {@code null}
     * @return the GUID string, or an empty string if not found
     */
    String readStyleGuid(Element styleEl) {
        if (styleEl == null) {
            return "";
        }
        // <a:tableStyleId> stores GUID as text content
        String text = styleEl.getTextContent();
        if (text != null && !text.isBlank()) {
            return text.strip();
        }
        // Legacy <a:tblStyle> stores GUID as val attribute
        String val = styleEl.getAttribute("val");
        return val != null ? val : "";
    }

    /**
     * Reads tblPr attributes into Java fields.
     */
    private void parseTableProperties() {
        Element tblPr = findChildElement(tblElement, NS_A, "tblPr");
        if (tblPr == null) return;
        firstRow = "1".equals(tblPr.getAttribute("firstRow"));
        firstCol = "1".equals(tblPr.getAttribute("firstCol"));
        lastRow = "1".equals(tblPr.getAttribute("lastRow"));
        lastCol = "1".equals(tblPr.getAttribute("lastCol"));
        horizontalBanding = "1".equals(tblPr.getAttribute("bandRow"));
        verticalBanding = "1".equals(tblPr.getAttribute("bandCol"));
        rightToLeft = "1".equals(tblPr.getAttribute("rtl"));
    }

    /**
     * Builds row and column collections from the XML table element.
     */
    private void buildRowsAndColumns(Runnable saveCallback) {
        // Build rows from <a:tr> elements
        List<Element> trElements = getChildElements(tblElement, "tr");
        List<IRow> rowList = new ArrayList<>();
        for (int r = 0; r < trElements.size(); r++) {
            Element tr = trElements.get(r);
            List<Element> tcElements = getChildElements(tr, "tc");
            List<ICell> cells = new ArrayList<>();
            for (int c = 0; c < tcElements.size(); c++) {
                cells.add(new Cell(tcElements.get(c), r, c, saveCallback, null, this));
            }
            rowList.add(new Row(tr, cells, saveCallback));
        }
        this.rows = new RowCollection(rowList);

        // Build columns from <a:tblGrid>/<a:gridCol> elements
        Element tblGrid = findChildElement(tblElement, NS_A, "tblGrid");
        if (tblGrid != null) {
            this.columns = new ColumnCollection(tblElement, tblGrid, saveCallback);
        } else {
            this.columns = new ColumnCollection();
        }
    }

    private double parseEmuAttr(Element el, String attr) {
        String val = el.getAttribute(attr);
        if (val == null || val.isEmpty()) return 0;
        try {
            return Long.parseLong(val) / EMU_PER_POINT;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static List<Element> getChildElements(Element parent, String localName) {
        List<Element> result = new ArrayList<>();
        NodeList children = parent.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child instanceof Element el
                    && localName.equals(el.getLocalName())
                    && "http://schemas.openxmlformats.org/drawingml/2006/main".equals(el.getNamespaceURI())) {
                result.add(el);
            }
        }
        return result;
    }

    /**
     * Returns the backing {@code <a:tbl>} element, or {@code null}.
     *
     * @return the tbl element
     */
    Element getTblElement() {
        return tblElement;
    }

    /**
     * Sets the backing {@code <a:tbl>} element.
     *
     * @param tblElement the tbl element
     */
    void setTblElement(Element tblElement) {
        this.tblElement = tblElement;
    }

    @Override
    public IRowCollection getRows() {
        return rows;
    }

    @Override
    public IColumnCollection getColumns() {
        return columns;
    }

    @Override
    public ITableFormat getTableFormat() {
        if (tableFormat == null) {
            tableFormat = new TableFormat();
            if (tblElement != null) {
                Element pr = ensureTblPr();
                tableFormat.initInternal(pr, saveCallback, parentSlide);
            }
        }
        return tableFormat;
    }

    @Override
    public TableStylePreset getStylePreset() {
        return stylePreset;
    }

    @Override
    public void setStylePreset(TableStylePreset value) {
        this.stylePreset = value;
        persistTblPr();
    }

    @Override
    public boolean isRightToLeft() {
        return rightToLeft;
    }

    @Override
    public void setRightToLeft(boolean value) {
        this.rightToLeft = value;
        persistTblPr();
    }

    @Override
    public boolean isFirstRow() {
        return firstRow;
    }

    @Override
    public void setFirstRow(boolean value) {
        this.firstRow = value;
        persistTblPr();
    }

    @Override
    public boolean isFirstCol() {
        return firstCol;
    }

    @Override
    public void setFirstCol(boolean value) {
        this.firstCol = value;
        persistTblPr();
    }

    @Override
    public boolean isLastRow() {
        return lastRow;
    }

    @Override
    public void setLastRow(boolean value) {
        this.lastRow = value;
        persistTblPr();
    }

    @Override
    public boolean isLastCol() {
        return lastCol;
    }

    @Override
    public void setLastCol(boolean value) {
        this.lastCol = value;
        persistTblPr();
    }

    @Override
    public boolean isHorizontalBanding() {
        return horizontalBanding;
    }

    @Override
    public void setHorizontalBanding(boolean value) {
        this.horizontalBanding = value;
        persistTblPr();
    }

    @Override
    public boolean isVerticalBanding() {
        return verticalBanding;
    }

    @Override
    public void setVerticalBanding(boolean value) {
        this.verticalBanding = value;
        persistTblPr();
    }

    /**
     * Persists table property flags back to the {@code <a:tblPr>} element.
     */
    private void persistTblPr() {
        if (tblElement == null) return;
        Element tblPr = findChildElement(tblElement, NS_A, "tblPr");
        if (tblPr == null) {
            tblPr = tblElement.getOwnerDocument().createElementNS(NS_A, "a:tblPr");
            tblElement.insertBefore(tblPr, tblElement.getFirstChild());
        }
        setFlag(tblPr, "firstRow", firstRow);
        setFlag(tblPr, "firstCol", firstCol);
        setFlag(tblPr, "lastRow", lastRow);
        setFlag(tblPr, "lastCol", lastCol);
        setFlag(tblPr, "bandRow", horizontalBanding);
        setFlag(tblPr, "bandCol", verticalBanding);
        setFlag(tblPr, "rtl", rightToLeft);
        if (saveCallback != null) saveCallback.run();
    }

    private static void setFlag(Element el, String attr, boolean value) {
        if (value) {
            el.setAttribute(attr, "1");
        } else {
            el.removeAttribute(attr);
        }
    }

    @Override
    public ICell mergeCells(ICell cell1, ICell cell2, boolean allowSplitting) {
        int r1 = cell1.getFirstRowIndex();
        int c1 = cell1.getFirstColumnIndex();
        int r2 = cell2.getFirstRowIndex();
        int c2 = cell2.getFirstColumnIndex();

        int minRow = Math.min(r1, r2);
        int maxRow = Math.max(r1, r2);
        int minCol = Math.min(c1, c2);
        int maxCol = Math.max(c1, c2);

        if (tblElement == null) {
            throw new UnsupportedOperationException("mergeCells requires XML-backed table");
        }

        List<Element> trElements = getChildElements(tblElement, "tr");

        // Set gridSpan (horizontal merge) and rowSpan (vertical merge)
        for (int r = minRow; r <= maxRow; r++) {
            if (r >= trElements.size()) break;
            List<Element> tcElements = getChildElements(trElements.get(r), "tc");
            for (int c = minCol; c <= maxCol; c++) {
                if (c >= tcElements.size()) break;
                Element tc = tcElements.get(c);
                if (r == minRow && c == minCol) {
                    // Primary cell: set spans
                    int colSpan = maxCol - minCol + 1;
                    int rowSpan = maxRow - minRow + 1;
                    if (colSpan > 1) tc.setAttribute("gridSpan", String.valueOf(colSpan));
                    if (rowSpan > 1) tc.setAttribute("rowSpan", String.valueOf(rowSpan));
                } else {
                    // Merged-away cells: mark with hMerge/vMerge
                    if (c > minCol) tc.setAttribute("hMerge", "1");
                    if (r > minRow) tc.setAttribute("vMerge", "1");
                }
            }
        }

        if (saveCallback != null) saveCallback.run();

        // Rebuild rows to pick up changed attributes
        buildRowsAndColumns(saveCallback);

        // Return the primary cell
        return rows.get(minRow).get(minCol);
    }

    @Override
    public IGraphicalObject asIGraphicalObject() {
        return this;
    }

    @Override
    public IBulkTextFormattable asIBulkTextFormattable() {
        return this;
    }

    @Override
    public IGraphicalObjectLock getGraphicalObjectLock() {
        return null;
    }

    @Override
    public void setTextFormat(Object source) {
        // Not yet implemented
    }
}
