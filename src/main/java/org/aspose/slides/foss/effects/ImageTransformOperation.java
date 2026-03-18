package org.aspose.slides.foss.effects;

import org.aspose.slides.foss.IBaseSlide;
import org.aspose.slides.foss.PVIObject;

/**
 * Represents an image-transform operation applied to an image effect.
 *
 * <p>Extends {@link PVIObject} for property-value-inheritance support
 * and implements {@link IImageTransformOperation}.</p>
 */
public class ImageTransformOperation extends PVIObject implements IImageTransformOperation {

    /**
     * Creates an {@code ImageTransformOperation} with no parent slide.
     */
    public ImageTransformOperation() {
    }

    /**
     * Creates an {@code ImageTransformOperation} associated with the given slide.
     *
     * @param parentSlide the parent slide
     */
    public ImageTransformOperation(IBaseSlide parentSlide) {
        super(parentSlide);
    }

    @Override
    public IImageTransformOperation asIImageTransformOperation() {
        return this;
    }
}
