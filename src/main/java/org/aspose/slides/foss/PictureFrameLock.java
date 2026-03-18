package org.aspose.slides.foss;

import java.util.List;

import org.w3c.dom.Element;

/**
 * Represents the locks for a {@link PictureFrame}.
 *
 * <p>Wraps an OOXML {@code <a:picLocks>} element inside
 * {@code <p:nvPicPr>/<p:cNvPicPr>}.</p>
 */
public final class PictureFrameLock extends BaseShapeLock implements IPictureFrameLock {

    private Element picLocksElement;
    private Runnable saveCallback;

    /**
     * Creates a new PictureFrameLock.
     */
    public PictureFrameLock() {
    }

    /**
     * Initializes internal state from the backing XML element.
     *
     * @param picLocksElement the {@code <a:picLocks>} element; may be {@code null}
     * @param saveCallback    callback invoked after mutations; may be {@code null}
     */
    public void initInternal(Element picLocksElement, Runnable saveCallback) {
        this.picLocksElement = picLocksElement;
        this.saveCallback = saveCallback;
    }

    @Override
    public boolean isGroupingLocked() {
        return getBoolAttribute("noGrp");
    }

    @Override
    public void setGroupingLocked(boolean value) {
        setBoolAttribute("noGrp", value);
    }

    @Override
    public boolean isSelectLocked() {
        return getBoolAttribute("noSelect");
    }

    @Override
    public void setSelectLocked(boolean value) {
        setBoolAttribute("noSelect", value);
    }

    @Override
    public boolean isRotationLocked() {
        return getBoolAttribute("noRot");
    }

    @Override
    public void setRotationLocked(boolean value) {
        setBoolAttribute("noRot", value);
    }

    @Override
    public boolean isAspectRatioLocked() {
        return getBoolAttribute("noChangeAspect");
    }

    @Override
    public void setAspectRatioLocked(boolean value) {
        setBoolAttribute("noChangeAspect", value);
    }

    @Override
    public boolean isPositionLocked() {
        return getBoolAttribute("noMove");
    }

    @Override
    public void setPositionLocked(boolean value) {
        setBoolAttribute("noMove", value);
    }

    @Override
    public boolean isSizeLocked() {
        return getBoolAttribute("noResize");
    }

    @Override
    public void setSizeLocked(boolean value) {
        setBoolAttribute("noResize", value);
    }

    @Override
    public boolean isEditPointsLocked() {
        return getBoolAttribute("noEditPoints");
    }

    @Override
    public void setEditPointsLocked(boolean value) {
        setBoolAttribute("noEditPoints", value);
    }

    @Override
    public boolean isAdjustHandlesLocked() {
        return getBoolAttribute("noAdjustHandles");
    }

    @Override
    public void setAdjustHandlesLocked(boolean value) {
        setBoolAttribute("noAdjustHandles", value);
    }

    @Override
    public boolean isArrowheadsLocked() {
        return getBoolAttribute("noChangeArrowheads");
    }

    @Override
    public void setArrowheadsLocked(boolean value) {
        setBoolAttribute("noChangeArrowheads", value);
    }

    @Override
    public boolean isShapeTypeLocked() {
        return getBoolAttribute("noChangeShapeType");
    }

    @Override
    public void setShapeTypeLocked(boolean value) {
        setBoolAttribute("noChangeShapeType", value);
    }

    @Override
    public boolean isCropLocked() {
        return getBoolAttribute("noCrop");
    }

    @Override
    public void setCropLocked(boolean value) {
        setBoolAttribute("noCrop", value);
    }

    private static final List<String> LOCK_ATTRIBUTES = List.of(
            "noGrp", "noSelect", "noRot", "noChangeAspect", "noMove",
            "noResize", "noEditPoints", "noAdjustHandles",
            "noChangeArrowheads", "noChangeShapeType", "noCrop"
    );

    @Override
    public boolean noLocks() {
        if (picLocksElement == null) {
            return true;
        }
        for (String attr : LOCK_ATTRIBUTES) {
            if ("1".equals(picLocksElement.getAttribute(attr))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean hasNoLocks() {
        return noLocks();
    }

    private boolean getBoolAttribute(String name) {
        if (picLocksElement == null) {
            return false;
        }
        String val = picLocksElement.getAttribute(name);
        return "1".equals(val) || "true".equals(val);
    }

    private void setBoolAttribute(String name, boolean value) {
        if (picLocksElement == null) {
            return;
        }
        if (value) {
            picLocksElement.setAttribute(name, "1");
        } else {
            picLocksElement.removeAttribute(name);
        }
        if (saveCallback != null) {
            saveCallback.run();
        }
    }
}
