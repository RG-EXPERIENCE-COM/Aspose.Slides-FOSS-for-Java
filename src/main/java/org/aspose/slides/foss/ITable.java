package org.aspose.slides.foss;

/**
 * Represents a table on a slide.
 */
public interface ITable extends IGraphicalObject, IBulkTextFormattable {

    /**
     * Returns the collection of rows. Read-only {@link IRowCollection}.
     *
     * @return the row collection
     */
    IRowCollection getRows();

    /**
     * Returns the collection of columns. Read-only {@link IColumnCollection}.
     *
     * @return the column collection
     */
    IColumnCollection getColumns();

    /**
     * Returns the table formatting. Read-only {@link ITableFormat}.
     *
     * @return the table format
     */
    ITableFormat getTableFormat();

    /**
     * Returns the table style preset. Read/write.
     *
     * @return the table style preset
     */
    TableStylePreset getStylePreset();

    /**
     * Sets the table style preset.
     *
     * @param value the table style preset
     */
    void setStylePreset(TableStylePreset value);

    /**
     * Determines whether the table has right-to-left reading order. Read/write.
     *
     * @return {@code true} if right-to-left
     */
    boolean isRightToLeft();

    /**
     * Sets the right-to-left reading order.
     *
     * @param value {@code true} for right-to-left
     */
    void setRightToLeft(boolean value);

    /**
     * Determines whether the first row has special formatting. Read/write.
     *
     * @return {@code true} if first row has special formatting
     */
    boolean isFirstRow();

    /**
     * Sets whether the first row has special formatting.
     *
     * @param value {@code true} for special first row formatting
     */
    void setFirstRow(boolean value);

    /**
     * Determines whether the first column has special formatting. Read/write.
     *
     * @return {@code true} if first column has special formatting
     */
    boolean isFirstCol();

    /**
     * Sets whether the first column has special formatting.
     *
     * @param value {@code true} for special first column formatting
     */
    void setFirstCol(boolean value);

    /**
     * Determines whether the last row has special formatting. Read/write.
     *
     * @return {@code true} if last row has special formatting
     */
    boolean isLastRow();

    /**
     * Sets whether the last row has special formatting.
     *
     * @param value {@code true} for special last row formatting
     */
    void setLastRow(boolean value);

    /**
     * Determines whether the last column has special formatting. Read/write.
     *
     * @return {@code true} if last column has special formatting
     */
    boolean isLastCol();

    /**
     * Sets whether the last column has special formatting.
     *
     * @param value {@code true} for special last column formatting
     */
    void setLastCol(boolean value);

    /**
     * Determines whether the table has horizontal banding. Read/write.
     *
     * @return {@code true} if horizontal banding is enabled
     */
    boolean isHorizontalBanding();

    /**
     * Sets whether the table has horizontal banding.
     *
     * @param value {@code true} for horizontal banding
     */
    void setHorizontalBanding(boolean value);

    /**
     * Determines whether the table has vertical banding. Read/write.
     *
     * @return {@code true} if vertical banding is enabled
     */
    boolean isVerticalBanding();

    /**
     * Sets whether the table has vertical banding.
     *
     * @param value {@code true} for vertical banding
     */
    void setVerticalBanding(boolean value);

    /**
     * Merges neighbouring cells.
     *
     * @param cell1          the first cell to merge
     * @param cell2          the second cell to merge
     * @param allowSplitting if {@code true}, allow splitting previously merged cells
     * @return the merged cell
     */
    ICell mergeCells(ICell cell1, ICell cell2, boolean allowSplitting);

    /**
     * Returns this table as an {@link IGraphicalObject}. Read-only.
     *
     * @return this instance as {@link IGraphicalObject}
     */
    IGraphicalObject asIGraphicalObject();

    /**
     * Returns this table as an {@link IBulkTextFormattable}. Read-only.
     *
     * @return this instance as {@link IBulkTextFormattable}
     */
    IBulkTextFormattable asIBulkTextFormattable();
}
