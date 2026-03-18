package org.aspose.slides.foss;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.Map;

/**
 * Represents a cell of a table.
 *
 * <p>Wraps an OOXML {@code <a:tc>} element.</p>
 */
public final class Cell implements ICell, ISlideComponent, IPresentationComponent {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";
    private static final double EMU_PER_POINT = 12700.0;
    private static final double DEFAULT_LR_MARGIN_EMU = 91440;
    private static final double DEFAULT_TB_MARGIN_EMU = 45720;

    private static final Map<String, TextVerticalType> VERT_MAP = Map.of(
            "horz", TextVerticalType.HORIZONTAL,
            "vert", TextVerticalType.VERTICAL,
            "vert270", TextVerticalType.VERTICAL270,
            "wordArtVert", TextVerticalType.WORD_ART_VERTICAL,
            "eaVert", TextVerticalType.EAST_ASIAN_VERTICAL,
            "mongolianVert", TextVerticalType.MONGOLIAN_VERTICAL,
            "wordArtVertRtl", TextVerticalType.WORD_ART_VERTICAL_RIGHT_TO_LEFT
    );

    private static final Map<TextVerticalType, String> VERT_REVERSE = Map.of(
            TextVerticalType.HORIZONTAL, "horz",
            TextVerticalType.VERTICAL, "vert",
            TextVerticalType.VERTICAL270, "vert270",
            TextVerticalType.WORD_ART_VERTICAL, "wordArtVert",
            TextVerticalType.EAST_ASIAN_VERTICAL, "eaVert",
            TextVerticalType.MONGOLIAN_VERTICAL, "mongolianVert",
            TextVerticalType.WORD_ART_VERTICAL_RIGHT_TO_LEFT, "wordArtVertRtl"
    );

    private static final Map<String, TextAnchorType> ANCHOR_MAP = Map.of(
            "t", TextAnchorType.TOP,
            "ctr", TextAnchorType.CENTER,
            "b", TextAnchorType.BOTTOM,
            "just", TextAnchorType.JUSTIFIED,
            "dist", TextAnchorType.DISTRIBUTED
    );

    private static final Map<TextAnchorType, String> ANCHOR_REVERSE = Map.of(
            TextAnchorType.TOP, "t",
            TextAnchorType.CENTER, "ctr",
            TextAnchorType.BOTTOM, "b",
            TextAnchorType.JUSTIFIED, "just",
            TextAnchorType.DISTRIBUTED, "dist"
    );

    private Element tcElement;
    private int rowIndex;
    private int colIndex;
    private Runnable saveCallback;
    private IBaseSlide parentSlide;
    private ITable table;

    /**
     * Creates an empty {@code Cell} with no backing element.
     *
     * <p>Call {@link #initInternal} to bind to an XML element.</p>
     */
    public Cell() {
        // no-op; call initInternal to bind to an XML element
    }

    /**
     * Creates a new Cell backed by the given {@code <a:tc>} element.
     *
     * @param tcElement    the tc XML element
     * @param rowIndex     zero-based row index of this cell in the table
     * @param colIndex     zero-based column index of this cell in the table
     * @param saveCallback callback invoked after mutations; may be {@code null}
     * @param parentSlide  the slide containing the table; may be {@code null}
     * @param table        the parent table; may be {@code null}
     */
    public Cell(Element tcElement, int rowIndex, int colIndex,
                Runnable saveCallback, IBaseSlide parentSlide, ITable table) {
        this.tcElement = tcElement;
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
        this.saveCallback = saveCallback;
        this.parentSlide = parentSlide;
        this.table = table;
    }

    /**
     * Creates a new Cell backed by the given {@code <a:tc>} element.
     *
     * <p>Convenience constructor without table context (row/col index default to 0,
     * table and slide default to {@code null}).</p>
     *
     * @param tcElement    the tc XML element
     * @param saveCallback callback invoked after mutations; may be {@code null}
     */
    public Cell(Element tcElement, Runnable saveCallback) {
        this(tcElement, 0, 0, saveCallback, null, null);
    }

    /**
     * Initializes this cell from the given {@code <a:tc>} element and table context.
     *
     * @param tcElement   the tc XML element
     * @param rowIndex    zero-based row index of this cell in the table
     * @param colIndex    zero-based column index of this cell in the table
     * @param saveCallback callback invoked after mutations; may be {@code null}
     * @param parentSlide the slide containing the table; may be {@code null}
     * @param table       the parent table; may be {@code null}
     * @return this cell, for fluent chaining
     */
    public Cell initInternal(Element tcElement, int rowIndex, int colIndex,
                             Runnable saveCallback, IBaseSlide parentSlide, ITable table) {
        this.tcElement = tcElement;
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
        this.saveCallback = saveCallback;
        this.parentSlide = parentSlide;
        this.table = table;
        return this;
    }

    private void save() {
        if (saveCallback != null) saveCallback.run();
    }

    private Element findChild(Element parent, String localName) {
        NodeList children = parent.getChildNodes();
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
     * Returns the {@code <a:tcPr>} child element of the backing {@code <a:tc>} element.
     *
     * @return the tcPr element, or {@code null} if the tc element is null or has no tcPr child
     */
    public Element getTcPr() {
        if (tcElement == null) return null;
        return findChild(tcElement, "tcPr");
    }

    /**
     * Returns the {@code <a:tcPr>} child element, creating it if absent.
     *
     * @return the tcPr element, never {@code null}
     */
    public Element ensureTcPr() {
        Element tcPr = getTcPr();
        if (tcPr == null) {
            Document doc = tcElement.getOwnerDocument();
            tcPr = doc.createElementNS(NS_A, "a:tcPr");
            tcElement.appendChild(tcPr);
        }
        return tcPr;
    }

    @Override
    public double getOffsetX() {
        if (table == null) return 0;
        IColumnCollection cols = table.getColumns();
        double total = 0.0;
        for (int i = 0; i < colIndex; i++) {
            total += cols.get(i).getWidth();
        }
        return total;
    }

    @Override
    public double getOffsetY() {
        if (table == null) return 0;
        IRowCollection rows = table.getRows();
        double total = 0.0;
        for (int i = 0; i < rowIndex; i++) {
            total += rows.get(i).getHeight();
        }
        return total;
    }

    @Override
    public int getFirstRowIndex() {
        return rowIndex;
    }

    @Override
    public int getFirstColumnIndex() {
        return colIndex;
    }

    @Override
    public double getWidth() {
        if (table == null) return 0;
        IColumnCollection cols = table.getColumns();
        int span = getColSpan();
        double total = 0.0;
        for (int i = colIndex; i < Math.min(colIndex + span, cols.size()); i++) {
            total += cols.get(i).getWidth();
        }
        return total;
    }

    @Override
    public double getHeight() {
        if (table == null) return 0;
        IRowCollection rows = table.getRows();
        int span = getRowSpan();
        double total = 0.0;
        for (int i = rowIndex; i < Math.min(rowIndex + span, rows.size()); i++) {
            total += rows.get(i).getHeight();
        }
        return total;
    }

    @Override
    public double getMinimalHeight() {
        if (table == null) return 0;
        IRowCollection rows = table.getRows();
        int span = getRowSpan();
        double total = 0.0;
        for (int i = rowIndex; i < Math.min(rowIndex + span, rows.size()); i++) {
            total += rows.get(i).getMinimalHeight();
        }
        return total;
    }

    @Override
    public double getMarginLeft() {
        Element tcPr = getTcPr();
        if (tcPr != null) {
            String val = tcPr.getAttribute("marL");
            if (val != null && !val.isEmpty()) {
                return Long.parseLong(val) / EMU_PER_POINT;
            }
        }
        return DEFAULT_LR_MARGIN_EMU / EMU_PER_POINT;
    }

    @Override
    public void setMarginLeft(double value) {
        Element tcPr = ensureTcPr();
        tcPr.setAttribute("marL", String.valueOf(Math.round(value * EMU_PER_POINT)));
        save();
    }

    @Override
    public double getMarginRight() {
        Element tcPr = getTcPr();
        if (tcPr != null) {
            String val = tcPr.getAttribute("marR");
            if (val != null && !val.isEmpty()) {
                return Long.parseLong(val) / EMU_PER_POINT;
            }
        }
        return DEFAULT_LR_MARGIN_EMU / EMU_PER_POINT;
    }

    @Override
    public void setMarginRight(double value) {
        Element tcPr = ensureTcPr();
        tcPr.setAttribute("marR", String.valueOf(Math.round(value * EMU_PER_POINT)));
        save();
    }

    @Override
    public double getMarginTop() {
        Element tcPr = getTcPr();
        if (tcPr != null) {
            String val = tcPr.getAttribute("marT");
            if (val != null && !val.isEmpty()) {
                return Long.parseLong(val) / EMU_PER_POINT;
            }
        }
        return DEFAULT_TB_MARGIN_EMU / EMU_PER_POINT;
    }

    @Override
    public void setMarginTop(double value) {
        Element tcPr = ensureTcPr();
        tcPr.setAttribute("marT", String.valueOf(Math.round(value * EMU_PER_POINT)));
        save();
    }

    @Override
    public double getMarginBottom() {
        Element tcPr = getTcPr();
        if (tcPr != null) {
            String val = tcPr.getAttribute("marB");
            if (val != null && !val.isEmpty()) {
                return Long.parseLong(val) / EMU_PER_POINT;
            }
        }
        return DEFAULT_TB_MARGIN_EMU / EMU_PER_POINT;
    }

    @Override
    public void setMarginBottom(double value) {
        Element tcPr = ensureTcPr();
        tcPr.setAttribute("marB", String.valueOf(Math.round(value * EMU_PER_POINT)));
        save();
    }

    @Override
    public TextVerticalType getTextVerticalType() {
        Element tcPr = getTcPr();
        if (tcPr != null) {
            String val = tcPr.getAttribute("vert");
            if (val != null && !val.isEmpty()) {
                return VERT_MAP.getOrDefault(val, TextVerticalType.NOT_DEFINED);
            }
        }
        return TextVerticalType.NOT_DEFINED;
    }

    @Override
    public void setTextVerticalType(TextVerticalType value) {
        Element tcPr = ensureTcPr();
        String ooxmlVal = VERT_REVERSE.get(value);
        if (ooxmlVal != null) {
            tcPr.setAttribute("vert", ooxmlVal);
        } else if (tcPr.hasAttribute("vert")) {
            tcPr.removeAttribute("vert");
        }
        save();
    }

    @Override
    public TextAnchorType getTextAnchorType() {
        Element tcPr = getTcPr();
        if (tcPr != null) {
            String val = tcPr.getAttribute("anchor");
            if (val != null && !val.isEmpty()) {
                return ANCHOR_MAP.getOrDefault(val, TextAnchorType.NOT_DEFINED);
            }
        }
        return TextAnchorType.NOT_DEFINED;
    }

    @Override
    public void setTextAnchorType(TextAnchorType value) {
        Element tcPr = ensureTcPr();
        String ooxmlVal = ANCHOR_REVERSE.get(value);
        if (ooxmlVal != null) {
            tcPr.setAttribute("anchor", ooxmlVal);
        } else if (tcPr.hasAttribute("anchor")) {
            tcPr.removeAttribute("anchor");
        }
        save();
    }

    @Override
    public boolean isAnchorCenter() {
        Element tcPr = getTcPr();
        if (tcPr != null) {
            String val = tcPr.getAttribute("anchorCtr");
            return "1".equals(val);
        }
        return false;
    }

    @Override
    public void setAnchorCenter(boolean value) {
        Element tcPr = ensureTcPr();
        if (value) {
            tcPr.setAttribute("anchorCtr", "1");
        } else if (tcPr.hasAttribute("anchorCtr")) {
            tcPr.removeAttribute("anchorCtr");
        }
        save();
    }

    @Override
    public IRow getFirstRow() {
        if (table == null) return null;
        return table.getRows().get(rowIndex);
    }

    @Override
    public IColumn getFirstColumn() {
        if (table == null) return null;
        return table.getColumns().get(colIndex);
    }

    @Override
    public int getColSpan() {
        if (tcElement != null) {
            String val = tcElement.getAttribute("gridSpan");
            if (val != null && !val.isEmpty()) {
                return Integer.parseInt(val);
            }
        }
        return 1;
    }

    @Override
    public int getRowSpan() {
        if (tcElement != null) {
            String val = tcElement.getAttribute("rowSpan");
            if (val != null && !val.isEmpty()) {
                return Integer.parseInt(val);
            }
        }
        return 1;
    }

    @Override
    public ITextFrame getTextFrame() {
        if (tcElement == null) return null;
        Element txBody = findChild(tcElement, "txBody");
        if (txBody == null) return null;
        return new TextFrame(txBody, saveCallback);
    }

    @Override
    public ITable getTable() {
        return table;
    }

    @Override
    public boolean isMergedCell() {
        if (tcElement == null) return false;
        String gridSpan = tcElement.getAttribute("gridSpan");
        if (gridSpan != null && !gridSpan.isEmpty() && Integer.parseInt(gridSpan) > 1) return true;
        String rowSpan = tcElement.getAttribute("rowSpan");
        if (rowSpan != null && !rowSpan.isEmpty() && Integer.parseInt(rowSpan) > 1) return true;
        if ("1".equals(tcElement.getAttribute("hMerge"))) return true;
        if ("1".equals(tcElement.getAttribute("vMerge"))) return true;
        return false;
    }

    @Override
    public ICellFormat getCellFormat() {
        Element tcPr = ensureTcPr();
        return new CellFormat(tcPr, saveCallback);
    }

    @Override
    public IBaseSlide getSlide() {
        return parentSlide;
    }

    @Override
    public IPresentation getPresentation() {
        if (parentSlide instanceof IPresentationComponent pc) {
            return pc.getPresentation();
        }
        return null;
    }

    /**
     * Returns the underlying {@code <a:tc>} XML element.
     *
     * <p>Package-visible for internal bulk-formatting operations.</p>
     *
     * @return the tc element, or {@code null} if not set
     */
    public Element getTcElement() {
        return tcElement;
    }

    @Override
    public ISlideComponent getAsISlideComponent() {
        return this;
    }

    @Override
    public IPresentationComponent getAsIPresentationComponent() {
        return this;
    }

    @Override
    public IPresentationComponent asIPresentationComponent() {
        return this;
    }
}
