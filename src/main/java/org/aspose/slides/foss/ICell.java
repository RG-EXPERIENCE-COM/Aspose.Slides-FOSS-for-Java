package org.aspose.slides.foss;

/**
 * Represents a cell in a table.
 */
public interface ICell {

    /**
     * Returns a distance from left side of a table to left side of a cell. Read-only.
     *
     * @return the x offset
     */
    double getOffsetX();

    /**
     * Returns a distance from top side of a table to top side of a cell. Read-only.
     *
     * @return the y offset
     */
    double getOffsetY();

    /**
     * Returns an index of first row, covered by the cell. Read-only.
     *
     * @return the first row index
     */
    int getFirstRowIndex();

    /**
     * Returns an index of first column, covered by the cell. Read-only.
     *
     * @return the first column index
     */
    int getFirstColumnIndex();

    /**
     * Returns the width of the cell. Read-only.
     *
     * @return the width
     */
    double getWidth();

    /**
     * Returns the height of the cell. Read-only.
     *
     * @return the height
     */
    double getHeight();

    /**
     * Returns the minimum height of a cell. This is a sum of minimal heights of all rows
     * covered by the cell. Read-only.
     *
     * @return the minimal height
     */
    double getMinimalHeight();

    /**
     * Returns or sets the left margin in a TextFrame. Read/write.
     *
     * @return the left margin
     */
    double getMarginLeft();

    /**
     * Sets the left margin.
     *
     * @param value the left margin
     */
    void setMarginLeft(double value);

    /**
     * Returns or sets the right margin in a TextFrame. Read/write.
     *
     * @return the right margin
     */
    double getMarginRight();

    /**
     * Sets the right margin.
     *
     * @param value the right margin
     */
    void setMarginRight(double value);

    /**
     * Returns or sets the top margin in a TextFrame. Read/write.
     *
     * @return the top margin
     */
    double getMarginTop();

    /**
     * Sets the top margin.
     *
     * @param value the top margin
     */
    void setMarginTop(double value);

    /**
     * Returns or sets the bottom margin in a TextFrame. Read/write.
     *
     * @return the bottom margin
     */
    double getMarginBottom();

    /**
     * Sets the bottom margin.
     *
     * @param value the bottom margin
     */
    void setMarginBottom(double value);

    /**
     * Returns or sets the type of vertical text. Read/write.
     *
     * @return the text vertical type
     */
    TextVerticalType getTextVerticalType();

    /**
     * Sets the type of vertical text.
     *
     * @param value the text vertical type
     */
    void setTextVerticalType(TextVerticalType value);

    /**
     * Returns or sets the text anchor type. Read/write.
     *
     * @return the text anchor type
     */
    TextAnchorType getTextAnchorType();

    /**
     * Sets the text anchor type.
     *
     * @param value the text anchor type
     */
    void setTextAnchorType(TextAnchorType value);

    /**
     * Determines whether or not text box centered inside a cell. Read/write.
     *
     * @return {@code true} if anchor center is enabled
     */
    boolean isAnchorCenter();

    /**
     * Sets whether text box is centered inside a cell.
     *
     * @param value {@code true} to center the text box
     */
    void setAnchorCenter(boolean value);

    /**
     * Gets first row of cell. Read-only.
     *
     * @return the first row
     */
    IRow getFirstRow();

    /**
     * Gets first column of cell. Read-only.
     *
     * @return the first column
     */
    IColumn getFirstColumn();

    /**
     * Returns the number of grid columns spanned by the current cell. Read-only.
     *
     * @return the column span
     */
    int getColSpan();

    /**
     * Returns the number of rows that a merged cell spans. Read-only.
     *
     * @return the row span
     */
    int getRowSpan();

    /**
     * Returns the text frame of a cell. Read-only.
     *
     * @return the text frame, or {@code null} if none
     */
    ITextFrame getTextFrame();

    /**
     * Returns the parent Table object for a cell. Read-only.
     *
     * @return the parent table
     */
    ITable getTable();

    /**
     * Returns true if the cell is merged with any adjusted cell. Read-only.
     *
     * @return whether the cell is merged
     */
    boolean isMergedCell();

    /**
     * Returns the CellFormat object that contains formatting properties for this cell. Read-only.
     *
     * @return the cell format
     */
    ICellFormat getCellFormat();

    /**
     * Returns the parent slide of a cell. Read-only.
     *
     * @return the parent slide
     */
    IBaseSlide getSlide();

    /**
     * Returns the parent presentation of a cell. Read-only.
     *
     * @return the parent presentation
     */
    IPresentation getPresentation();

    /**
     * Returns this cell as {@link ISlideComponent}.
     *
     * @return this as ISlideComponent
     */
    ISlideComponent getAsISlideComponent();

    /**
     * Returns this cell as {@link IPresentationComponent}.
     *
     * @return this as IPresentationComponent
     */
    IPresentationComponent getAsIPresentationComponent();
}
