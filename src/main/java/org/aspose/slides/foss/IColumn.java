package org.aspose.slides.foss;

/**
 * Represents a column in a table.
 */
public interface IColumn extends ICellCollection, IBulkTextFormattable {

    /**
     * Returns or sets the width of a column. Read/write.
     *
     * @return the width
     */
    double getWidth();

    /**
     * Sets the width of a column.
     *
     * @param value the width
     */
    void setWidth(double value);

    /**
     * Allows to get base ICellCollection interface. Read-only.
     *
     * @return this as ICellCollection
     */
    ICellCollection getAsICellCollection();

    /**
     * Allows to get base IBulkTextFormattable interface. Read-only.
     *
     * @return this as IBulkTextFormattable
     */
    IBulkTextFormattable getAsIBulkTextFormattable();

    /**
     * Returns the ColumnFormat object that contains formatting properties for this column. Read-only.
     *
     * @return the column format
     */
    IColumnFormat getColumnFormat();
}
