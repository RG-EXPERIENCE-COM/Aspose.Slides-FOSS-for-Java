package org.aspose.slides.foss;

import org.aspose.slides.foss.internal.pptx.ImageUtils;
import org.aspose.slides.foss.internal.pptx.OpcPackage;

import java.util.Objects;

/**
 * Represents an image in a presentation.
 *
 * <p>A {@code PPImage} is backed by an {@link OpcPackage} part and exposes
 * the image data, dimensions, content type, and supports replacement.</p>
 */
public final class PPImage implements IPPImage {

    private OpcPackage pkg;
    private String partName;
    private byte[] imageData;
    private String contentType;
    private int width;
    private int height;

    /**
     * Creates an uninitialized {@code PPImage}.
     *
     * <p>Call {@link #initInternal(OpcPackage, String, byte[], String)} to populate.</p>
     */
    public PPImage() {
        // default constructor; requires initInternal to be called
    }

    /**
     * Internal initialization with OPC package reference.
     *
     * @param pkg         the OPC package containing the image
     * @param partName    the part path (e.g. {@code "ppt/media/image1.jpg"})
     * @param imageData   the raw image bytes
     * @param contentType the MIME type of the image
     */
    public void initInternal(OpcPackage pkg, String partName,
                             byte[] imageData, String contentType) {
        this.pkg = Objects.requireNonNull(pkg, "pkg");
        this.partName = Objects.requireNonNull(partName, "partName");
        this.imageData = Objects.requireNonNull(imageData, "imageData").clone();
        this.contentType = Objects.requireNonNull(contentType, "contentType");
        int[] dims = ImageUtils.getImageDimensions(imageData);
        this.width = dims[0];
        this.height = dims[1];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] getBinaryData() {
        return imageData.clone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IImage getImage() {
        Image img = new Image();
        img.initInternal(imageData.clone(), contentType);
        return img;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getContentType() {
        return contentType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWidth() {
        return width;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHeight() {
        return height;
    }

    /**
     * {@inheritDoc}
     *
     * @return always {@code 0}
     */
    @Override
    public int getX() {
        return 0;
    }

    /**
     * {@inheritDoc}
     *
     * @return always {@code 0}
     */
    @Override
    public int getY() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void replaceImage(byte[] newImageData) {
        Objects.requireNonNull(newImageData, "newImageData");
        this.imageData = newImageData.clone();
        this.contentType = ImageUtils.guessContentType(newImageData);
        int[] dims = ImageUtils.getImageDimensions(newImageData);
        this.width = dims[0];
        this.height = dims[1];
        pkg.setPartBytes(partName, newImageData);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void replaceImage(IImage newImage) {
        Objects.requireNonNull(newImage, "newImage");
        byte[] data;
        if (newImage instanceof Image img) {
            data = img.getData();
        } else {
            throw new IllegalArgumentException(
                    "Unsupported IImage implementation: " + newImage.getClass().getName());
        }
        replaceImage(data);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void replaceImage(IPPImage newImage) {
        Objects.requireNonNull(newImage, "newImage");
        replaceImage(newImage.getBinaryData());
    }

    /**
     * Returns the internal raw image data reference (not a copy).
     *
     * <p>Package-private — for internal use only.</p>
     *
     * @return the raw image bytes
     */
    byte[] getImageDataInternal() {
        return imageData;
    }

    /**
     * Returns the OPC part name for this image (e.g. {@code "ppt/media/image1.jpg"}).
     *
     * <p>Package-private — for internal use only.</p>
     *
     * @return the part name
     */
    String getPartName() {
        return partName;
    }
}
