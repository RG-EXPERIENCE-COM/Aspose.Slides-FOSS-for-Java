package org.aspose.slides.foss;

import org.aspose.slides.foss.internal.pptx.MasterSlidePart;
import org.aspose.slides.foss.internal.pptx.OpcPackage;

import java.util.List;

/**
 * Represents a master slide in a presentation.
 */
public final class MasterSlide extends BaseSlide implements IMasterSlide {

    private ILayoutSlideCollection layoutSlides;

    // Internal fields for package-loaded master slides
    private IPresentation presentationRef;
    private OpcPackage opcPackage;
    private String partName;
    private MasterSlidePart masterPart;

    /**
     * Creates a MasterSlide with the given layout slides.
     *
     * @param layoutSlides the collection of layout slides belonging to this master
     */
    public MasterSlide(ILayoutSlideCollection layoutSlides) {
        super();
        this.layoutSlides = layoutSlides != null ? layoutSlides : new MasterLayoutSlideCollection();
    }

    /**
     * Creates a MasterSlide with the given name, ID, and layout slides.
     *
     * @param name         the master slide name
     * @param slideId      the slide ID
     * @param layoutSlides the collection of layout slides
     */
    public MasterSlide(String name, int slideId, ILayoutSlideCollection layoutSlides) {
        super(name, slideId);
        this.layoutSlides = layoutSlides != null ? layoutSlides : new MasterLayoutSlideCollection();
    }

    /**
     * Creates a MasterSlide with no layout slides.
     */
    public MasterSlide() {
        this((ILayoutSlideCollection) null);
    }

    /**
     * Internal initialization for a master slide loaded from a package.
     *
     * @param presentation the parent Presentation object
     * @param opcPackage   the OPC package
     * @param partName     the part name of this master slide
     * @param masterPart   the parsed MasterSlidePart
     * @param layoutSlides the layout slides belonging to this master
     */
    public void initInternal(IPresentation presentation, OpcPackage opcPackage,
                             String partName, MasterSlidePart masterPart,
                             List<ILayoutSlide> layoutSlides) {
        this.presentationRef = presentation;
        this.opcPackage = opcPackage;
        this.partName = partName;
        this.masterPart = masterPart;
        var collection = new MasterLayoutSlideCollection();
        collection.initInternal(layoutSlides);
        this.layoutSlides = collection;
    }

    @Override
    public ILayoutSlideCollection getLayoutSlides() {
        return layoutSlides;
    }

    @Override
    public IPresentation getPresentation() {
        if (presentationRef != null) {
            return presentationRef;
        }
        return super.getPresentation();
    }
}
