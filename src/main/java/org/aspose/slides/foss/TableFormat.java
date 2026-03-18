package org.aspose.slides.foss;

import org.w3c.dom.Element;

/**
 * Represents format of a table.
 */
public final class TableFormat implements ITableFormat {

    private Element tblPrElement;
    private Runnable saveCallback;
    private IBaseSlide parentSlide;

    /**
     * Creates an uninitialized TableFormat. Call {@link #initInternal} before use.
     */
    public TableFormat() {
    }

    /**
     * Internal initialization.
     *
     * @param tblPrElement the {@code <a:tblPr>} XML element
     * @param saveCallback callback invoked after mutations to persist changes; may be {@code null}
     * @param parentSlide  the parent slide object; may be {@code null}
     * @return this instance for fluent chaining
     */
    public TableFormat initInternal(Element tblPrElement, Runnable saveCallback, IBaseSlide parentSlide) {
        this.tblPrElement = tblPrElement;
        this.saveCallback = saveCallback;
        this.parentSlide = parentSlide;
        return this;
    }

    @Override
    public IFillFormat getFillFormat() {
        if (tblPrElement == null) {
            return null;
        }
        FillFormat fillFormat = new FillFormat();
        fillFormat.initInternal(tblPrElement, saveCallback, parentSlide);
        return fillFormat;
    }
}
