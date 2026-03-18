package org.aspose.slides.foss;

/**
 * Determines which operations are disabled on the parent {@link IPictureFrame}.
 */
public interface IPictureFrameLock {

    /**
     * Determines whether adding this shape to a group is forbidden.
     *
     * @return {@code true} if grouping is locked
     */
    boolean isGroupingLocked();

    /**
     * Sets whether adding this shape to a group is forbidden.
     *
     * @param value {@code true} to lock grouping
     */
    void setGroupingLocked(boolean value);

    /**
     * Determines whether selecting this shape is forbidden.
     *
     * @return {@code true} if selection is locked
     */
    boolean isSelectLocked();

    /**
     * Sets whether selecting this shape is forbidden.
     *
     * @param value {@code true} to lock selection
     */
    void setSelectLocked(boolean value);

    /**
     * Determines whether changing the rotation angle of this shape is forbidden.
     *
     * @return {@code true} if rotation is locked
     */
    boolean isRotationLocked();

    /**
     * Sets whether changing the rotation angle of this shape is forbidden.
     *
     * @param value {@code true} to lock rotation
     */
    void setRotationLocked(boolean value);

    /**
     * Determines whether the shape must preserve its aspect ratio on resizing.
     *
     * @return {@code true} if aspect ratio is locked
     */
    boolean isAspectRatioLocked();

    /**
     * Sets whether the shape must preserve its aspect ratio on resizing.
     *
     * @param value {@code true} to lock aspect ratio
     */
    void setAspectRatioLocked(boolean value);

    /**
     * Determines whether moving this shape is forbidden.
     *
     * @return {@code true} if position is locked
     */
    boolean isPositionLocked();

    /**
     * Sets whether moving this shape is forbidden.
     *
     * @param value {@code true} to lock position
     */
    void setPositionLocked(boolean value);

    /**
     * Determines whether resizing this shape is forbidden.
     *
     * @return {@code true} if size is locked
     */
    boolean isSizeLocked();

    /**
     * Sets whether resizing this shape is forbidden.
     *
     * @param value {@code true} to lock size
     */
    void setSizeLocked(boolean value);

    /**
     * Determines whether direct changing of the contour of this shape is forbidden.
     *
     * @return {@code true} if edit points are locked
     */
    boolean isEditPointsLocked();

    /**
     * Sets whether direct changing of the contour of this shape is forbidden.
     *
     * @param value {@code true} to lock edit points
     */
    void setEditPointsLocked(boolean value);

    /**
     * Determines whether changing adjust values is forbidden.
     *
     * @return {@code true} if adjust handles are locked
     */
    boolean isAdjustHandlesLocked();

    /**
     * Sets whether changing adjust values is forbidden.
     *
     * @param value {@code true} to lock adjust handles
     */
    void setAdjustHandlesLocked(boolean value);

    /**
     * Determines whether changing arrowheads is forbidden.
     *
     * @return {@code true} if arrowheads are locked
     */
    boolean isArrowheadsLocked();

    /**
     * Sets whether changing arrowheads is forbidden.
     *
     * @param value {@code true} to lock arrowheads
     */
    void setArrowheadsLocked(boolean value);

    /**
     * Determines whether changing the shape type is forbidden.
     *
     * @return {@code true} if shape type is locked
     */
    boolean isShapeTypeLocked();

    /**
     * Sets whether changing the shape type is forbidden.
     *
     * @param value {@code true} to lock shape type
     */
    void setShapeTypeLocked(boolean value);

    /**
     * Determines whether image cropping is forbidden.
     *
     * @return {@code true} if cropping is locked
     */
    boolean isCropLocked();

    /**
     * Sets whether image cropping is forbidden.
     *
     * @param value {@code true} to lock cropping
     */
    void setCropLocked(boolean value);

    /**
     * Returns {@code true} if all lock attributes are absent or disabled.
     *
     * @return {@code true} if no locks are active
     */
    boolean hasNoLocks();
}
