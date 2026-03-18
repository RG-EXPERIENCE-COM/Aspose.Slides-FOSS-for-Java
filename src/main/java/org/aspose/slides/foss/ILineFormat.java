package org.aspose.slides.foss;

import java.util.List;

/**
 * Represents format of a line.
 */
public interface ILineFormat extends ILineParamSource {

    /**
     * Returns {@code true} if line format is not defined (as just created, default).
     *
     * @return {@code true} if format is not defined
     */
    boolean isFormatNotDefined();

    /**
     * Returns the fill format of a line. Read-only.
     *
     * @return the line fill format
     */
    ILineFillFormat getFillFormat();

    /**
     * Returns the width of a line in points.
     *
     * @return the line width
     */
    double getWidth();

    /**
     * Sets the width of a line in points.
     *
     * @param value the line width
     */
    void setWidth(double value);

    /**
     * Returns the line dash style.
     *
     * @return the dash style
     */
    LineDashStyle getDashStyle();

    /**
     * Sets the line dash style.
     *
     * @param value the dash style
     */
    void setDashStyle(LineDashStyle value);

    /**
     * Returns the custom dash pattern as pairs of [dash, space, ...].
     *
     * @return the custom dash pattern
     */
    List<Double> getCustomDashPattern();

    /**
     * Sets the custom dash pattern as pairs of [dash, space, ...].
     *
     * @param value the custom dash pattern
     */
    void setCustomDashPattern(List<Double> value);

    /**
     * Returns the line cap style.
     *
     * @return the cap style
     */
    LineCapStyle getCapStyle();

    /**
     * Sets the line cap style.
     *
     * @param value the cap style
     */
    void setCapStyle(LineCapStyle value);

    /**
     * Returns the line style.
     *
     * @return the line style
     */
    LineStyle getStyle();

    /**
     * Sets the line style.
     *
     * @param value the line style
     */
    void setStyle(LineStyle value);

    /**
     * Returns the line alignment.
     *
     * @return the alignment
     */
    LineAlignment getAlignment();

    /**
     * Sets the line alignment.
     *
     * @param value the alignment
     */
    void setAlignment(LineAlignment value);

    /**
     * Returns the lines join style.
     *
     * @return the join style
     */
    LineJoinStyle getJoinStyle();

    /**
     * Sets the lines join style.
     *
     * @param value the join style
     */
    void setJoinStyle(LineJoinStyle value);

    /**
     * Returns the miter limit of a line.
     *
     * @return the miter limit
     */
    double getMiterLimit();

    /**
     * Sets the miter limit of a line.
     *
     * @param value the miter limit
     */
    void setMiterLimit(double value);

    /**
     * Returns the arrowhead style at the beginning of a line.
     *
     * @return the begin arrowhead style
     */
    LineArrowheadStyle getBeginArrowheadStyle();

    /**
     * Sets the arrowhead style at the beginning of a line.
     *
     * @param value the begin arrowhead style
     */
    void setBeginArrowheadStyle(LineArrowheadStyle value);

    /**
     * Returns the arrowhead style at the end of a line.
     *
     * @return the end arrowhead style
     */
    LineArrowheadStyle getEndArrowheadStyle();

    /**
     * Sets the arrowhead style at the end of a line.
     *
     * @param value the end arrowhead style
     */
    void setEndArrowheadStyle(LineArrowheadStyle value);

    /**
     * Returns the arrowhead width at the beginning of a line.
     *
     * @return the begin arrowhead width
     */
    LineArrowheadWidth getBeginArrowheadWidth();

    /**
     * Sets the arrowhead width at the beginning of a line.
     *
     * @param value the begin arrowhead width
     */
    void setBeginArrowheadWidth(LineArrowheadWidth value);

    /**
     * Returns the arrowhead width at the end of a line.
     *
     * @return the end arrowhead width
     */
    LineArrowheadWidth getEndArrowheadWidth();

    /**
     * Sets the arrowhead width at the end of a line.
     *
     * @param value the end arrowhead width
     */
    void setEndArrowheadWidth(LineArrowheadWidth value);

    /**
     * Returns the arrowhead length at the beginning of a line.
     *
     * @return the begin arrowhead length
     */
    LineArrowheadLength getBeginArrowheadLength();

    /**
     * Sets the arrowhead length at the beginning of a line.
     *
     * @param value the begin arrowhead length
     */
    void setBeginArrowheadLength(LineArrowheadLength value);

    /**
     * Returns the arrowhead length at the end of a line.
     *
     * @return the end arrowhead length
     */
    LineArrowheadLength getEndArrowheadLength();

    /**
     * Sets the arrowhead length at the end of a line.
     *
     * @param value the end arrowhead length
     */
    void setEndArrowheadLength(LineArrowheadLength value);
}
