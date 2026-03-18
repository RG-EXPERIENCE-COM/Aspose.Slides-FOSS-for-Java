package org.aspose.slides.foss;

/**
 * Represents a row in a table.
 */
public interface IRow extends ICellCollection, IBulkTextFormattable {

    /**
     * Returns the height of the row. Read-only.
     *
     * @return the height
     */
    double getHeight();

    /**
     * Returns the minimal height of the row. Read/write.
     *
     * @return the minimal height
     */
    double getMinimalHeight();

    /**
     * Sets the minimal height of the row.
     *
     * @param value the minimal height
     */
    void setMinimalHeight(double value);

    /**
     * Returns the RowFormat object that contains formatting properties for this row. Read-only.
     *
     * @return the row format
     */
    IRowFormat getRowFormat();

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
}
