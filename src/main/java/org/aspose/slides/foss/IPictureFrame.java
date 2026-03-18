package org.aspose.slides.foss;

/**
 * Represents a frame with a picture inside.
 */
public interface IPictureFrame extends IGeometryShape {

    /**
     * Returns the shape's locks. Read-only {@link IPictureFrameLock}.
     *
     * @return the picture frame lock, or {@code null}
     */
    IPictureFrameLock getPictureFrameLock();

    /**
     * Returns the {@link IPictureFillFormat} object for the picture frame. Read-only.
     *
     * @return the picture fill format, or {@code null}
     */
    IPictureFillFormat getPictureFormat();

    /**
     * Returns the scale of height relative to the original picture size.
     * Value {@code 1.0} corresponds to 100%. Read/write.
     *
     * @return the relative scale height
     */
    double getRelativeScaleHeight();

    /**
     * Sets the scale of height relative to the original picture size.
     *
     * @param value the relative scale height
     */
    void setRelativeScaleHeight(double value);

    /**
     * Returns the scale of width relative to the original picture size.
     * Value {@code 1.0} corresponds to 100%. Read/write.
     *
     * @return the relative scale width
     */
    double getRelativeScaleWidth();

    /**
     * Sets the scale of width relative to the original picture size.
     *
     * @param value the relative scale width
     */
    void setRelativeScaleWidth(double value);

    /**
     * Determines whether the PictureFrame is a Cameo object or not. Read-only.
     *
     * @return {@code true} if cameo, {@code false} otherwise
     */
    boolean isCameo();
}
