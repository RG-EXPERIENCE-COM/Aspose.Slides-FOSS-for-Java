package org.aspose.slides.foss;

import org.w3c.dom.Element;

/**
 * Represents format of a table cell.
 *
 * <p>Wraps an OOXML {@code <a:tcPr>} element.</p>
 */
public final class CellFormat extends PVIObject implements ICellFormat {

    private Element tcPrElement;
    private Runnable saveCallback;

    /**
     * Creates a new CellFormat backed by the given {@code <a:tcPr>} element.
     *
     * @param tcPrElement  the tcPr XML element
     * @param saveCallback callback invoked after mutations; may be {@code null}
     */
    public CellFormat(Element tcPrElement, Runnable saveCallback) {
        this.tcPrElement = tcPrElement;
        this.saveCallback = saveCallback;
    }

    /**
     * Initializes this CellFormat with its backing element and context.
     *
     * @param tcPrElement the tcPr XML element
     * @param saveCallback callback invoked after mutations; may be {@code null}
     * @param parentSlide the slide containing the table; may be {@code null}
     * @return this instance for fluent chaining
     */
    public CellFormat initInternal(Element tcPrElement, Runnable saveCallback, IBaseSlide parentSlide) {
        this.tcPrElement = tcPrElement;
        this.saveCallback = saveCallback;
        this.parentSlide = parentSlide;
        return this;
    }

    /**
     * Gets a border {@link ILineFormat} for the given border element tag.
     *
     * @param lnTag the OOXML line tag (e.g. "lnL", "lnR", "lnT", "lnB")
     * @return the line format for the specified border
     */
    ILineFormat getBorder(String lnTag) {
        return new LineFormat(tcPrElement, saveCallback, lnTag);
    }

    @Override
    public IFillFormat getFillFormat() {
        return new FillFormat(tcPrElement, saveCallback);
    }

    @Override
    public ILineFormat getBorderLeft() {
        return getBorder("lnL");
    }

    @Override
    public ILineFormat getBorderTop() {
        return getBorder("lnT");
    }

    @Override
    public ILineFormat getBorderRight() {
        return getBorder("lnR");
    }

    @Override
    public ILineFormat getBorderBottom() {
        return getBorder("lnB");
    }

    @Override
    public ILineFormat getBorderDiagonalDown() {
        return getBorder("lnTlToBr");
    }

    @Override
    public ILineFormat getBorderDiagonalUp() {
        return getBorder("lnBlToTr");
    }
}
