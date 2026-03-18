package org.aspose.slides.foss.effects;

import org.aspose.slides.foss.FillBlendMode;
import org.aspose.slides.foss.IFillFormat;

/**
 * Represents a Fill Overlay effect. A fill overlay may be used to specify
 * an additional fill for an object and blend the two fills together.
 */
public interface IFillOverlay extends IImageTransformOperation {

    /**
     * Returns the fill format. Read-only {@link IFillFormat}.
     *
     * @return the fill format
     */
    IFillFormat getFillFormat();

    /**
     * Returns the fill blend mode. Read/write {@link FillBlendMode}.
     *
     * @return the blend mode
     */
    FillBlendMode getBlend();

    /**
     * Sets the fill blend mode.
     *
     * @param value the blend mode
     */
    void setBlend(FillBlendMode value);
}
