package org.aspose.slides.foss;

/**
 * Represents format of a table cell.
 */
public interface ICellFormat {

    /**
     * Returns a cell fill properties object. Read-only.
     *
     * @return the fill format
     */
    IFillFormat getFillFormat();

    /**
     * Returns a left border line properties object. Read-only.
     *
     * @return the left border line format
     */
    ILineFormat getBorderLeft();

    /**
     * Returns a top border line properties object. Read-only.
     *
     * @return the top border line format
     */
    ILineFormat getBorderTop();

    /**
     * Returns a right border line properties object. Read-only.
     *
     * @return the right border line format
     */
    ILineFormat getBorderRight();

    /**
     * Returns a bottom border line properties object. Read-only.
     *
     * @return the bottom border line format
     */
    ILineFormat getBorderBottom();

    /**
     * Returns a top-left to bottom-right diagonal line properties object. Read-only.
     *
     * @return the diagonal down line format
     */
    ILineFormat getBorderDiagonalDown();

    /**
     * Returns a bottom-left to top-right diagonal line properties object. Read-only.
     *
     * @return the diagonal up line format
     */
    ILineFormat getBorderDiagonalUp();
}
