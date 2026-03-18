package org.aspose.slides.foss;

import org.aspose.slides.foss.internal.pptx.ContentTypesManager;
import org.aspose.slides.foss.internal.pptx.MasterReference;
import org.aspose.slides.foss.internal.pptx.OpcPackage;
import org.aspose.slides.foss.internal.pptx.PresentationPart;
import org.aspose.slides.foss.internal.pptx.RelsHelper;
import org.aspose.slides.foss.internal.pptx.SlidePart;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a collection of master slides in a presentation.
 */
public final class MasterSlideCollection implements IMasterSlideCollection, Iterable<IMasterSlide> {

    private static final Pattern MASTER_FILE_PATTERN =
            Pattern.compile("ppt/slideMasters/slideMaster(\\d+)\\.xml$");
    private static final Pattern LAYOUT_FILE_PATTERN =
            Pattern.compile("ppt/slideLayouts/slideLayout(\\d+)\\.xml$");

    private static final String NS_P =
            "http://schemas.openxmlformats.org/presentationml/2006/main";

    private static final String REL_TYPE_SLIDE_LAYOUT =
            "http://schemas.openxmlformats.org/officeDocument/2006/relationships/slideLayout";
    private static final String REL_TYPE_SLIDE_MASTER =
            "http://schemas.openxmlformats.org/officeDocument/2006/relationships/slideMaster";

    private List<IMasterSlide> masters = new ArrayList<>();
    private IPresentation presentation;
    private OpcPackage opcPackage;
    private PresentationPart presentationPart;

    /**
     * Creates an empty MasterSlideCollection.
     */
    public MasterSlideCollection() {
    }

    /**
     * Performs internal initialization for the master slide collection.
     *
     * @param presentation     the parent presentation
     * @param opcPackage       the OPC package
     * @param presentationPart the presentation part manager
     * @param masterSlides     initial list of master slides, or {@code null}
     */
    public void initInternal(IPresentation presentation, OpcPackage opcPackage,
                             PresentationPart presentationPart,
                             List<IMasterSlide> masterSlides) {
        this.presentation = presentation;
        this.opcPackage = opcPackage;
        this.presentationPart = presentationPart;
        this.masters = masterSlides != null ? new ArrayList<>(masterSlides) : new ArrayList<>();
    }

    @Override
    public IMasterSlide get(int index) {
        return masters.get(index);
    }

    @Override
    public int size() {
        return masters.size();
    }

    @Override
    public List<IMasterSlide> asICollection() {
        return Collections.unmodifiableList(new ArrayList<>(masters));
    }

    @Override
    public Iterable<IMasterSlide> asIEnumerable() {
        return asICollection();
    }

    @Override
    public Iterator<IMasterSlide> iterator() {
        return masters.iterator();
    }

    @Override
    public IMasterSlide addClone(IMasterSlide sourceMaster) {
        // Clone layout slides from the source master
        var clonedLayouts = new MasterLayoutSlideCollection();
        ILayoutSlideCollection sourceLayouts = sourceMaster.getLayoutSlides();
        if (sourceLayouts != null) {
            for (int i = 0; i < sourceLayouts.size(); i++) {
                ILayoutSlide sourceLayout = sourceLayouts.get(i);
                var clonedLayout = new LayoutSlide();
                clonedLayout.setName(sourceLayout.getName());
                clonedLayout.setLayoutType(sourceLayout.getLayoutType());
                clonedLayouts.getInternalList().add(clonedLayout);
            }
        }

        MasterSlide cloned = new MasterSlide(
                sourceMaster.getName(),
                masters.size() + 1,
                clonedLayouts
        );

        // Set master slide reference on each cloned layout
        for (int i = 0; i < clonedLayouts.size(); i++) {
            ILayoutSlide layout = clonedLayouts.get(i);
            if (layout instanceof LayoutSlide ls) {
                ls.setMasterSlide(cloned);
            }
        }

        masters.add(cloned);
        return cloned;
    }

    /**
     * Adds a master slide to the collection.
     *
     * @param master the master slide to add
     */
    public void add(IMasterSlide master) {
        masters.add(master);
    }

    /**
     * Returns the internal list (for framework use).
     *
     * @return the internal list
     */
    List<IMasterSlide> getInternalList() {
        return masters;
    }

    /**
     * Finds the next available master slide file number in the package.
     *
     * @return the next unused file number for a slide master part
     */
    public int getNextMasterFileNumber() {
        Set<Integer> existingNums = new HashSet<>();
        for (String partName : opcPackage.getPartNames()) {
            Matcher m = MASTER_FILE_PATTERN.matcher(partName);
            if (m.matches()) {
                existingNums.add(Integer.parseInt(m.group(1)));
            }
        }
        int num = 1;
        while (existingNums.contains(num)) {
            num++;
        }
        return num;
    }

    /**
     * Finds the next available layout slide file number in the package.
     *
     * @return the next unused file number for a slide layout part
     */
    public int getNextLayoutFileNumber() {
        Set<Integer> existingNums = new HashSet<>();
        for (String partName : opcPackage.getPartNames()) {
            Matcher m = LAYOUT_FILE_PATTERN.matcher(partName);
            if (m.matches()) {
                existingNums.add(Integer.parseInt(m.group(1)));
            }
        }
        int num = 1;
        while (existingNums.contains(num)) {
            num++;
        }
        return num;
    }

    /**
     * Clones a master slide part and its related resources (except layouts) from one
     * package to another.
     *
     * @param sourcePackage    the source OPC package
     * @param sourcePartName   the source master slide part name
     * @param destPackage      the destination OPC package
     * @param destPartName     the destination master slide part name
     * @return a mapping of old relationship IDs to new relationship IDs (non-layout only)
     * @throws IllegalArgumentException if the source master slide part is not found
     */
    public Map<String, String> cloneMasterPart(OpcPackage sourcePackage, String sourcePartName,
                                               OpcPackage destPackage, String destPartName) {
        // Copy master XML
        byte[] sourceContent = sourcePackage.getPartBytes(sourcePartName);
        if (sourceContent == null) {
            throw new IllegalArgumentException("Master slide not found: " + sourcePartName);
        }

        Document destDoc = SlidePart.parseXml(sourceContent);
        Element destRoot = destDoc.getDocumentElement();

        // Copy relationships (theme, images, etc.) - but not layouts (handled separately)
        var sourceRels = new RelsHelper(sourcePackage, sourcePartName);
        var destRels = new RelsHelper(destPackage, destPartName);
        Map<String, String> ridMapping = new HashMap<>();

        for (RelsHelper.RelEntry rel : sourceRels.getAllRelationships()) {
            if (REL_TYPE_SLIDE_LAYOUT.equals(rel.type())) {
                // Skip layouts - they're handled separately
                continue;
            } else if ("External".equals(rel.targetMode())) {
                String newRid = destRels.addRelationship(
                        rel.type(), rel.target(), "External");
                ridMapping.put(rel.id(), newRid);
            } else {
                // Clone related parts (theme, images)
                String sourceTarget = SlidePart.resolveTargetStatic(sourcePartName, rel.target());
                String destTarget = SlidePart.cloneRelatedPart(
                        sourcePackage, sourceTarget,
                        destPackage, destPartName, rel.type());
                String relativeTarget = SlidePart.computeRelativeTarget(destPartName, destTarget);
                String newRid = destRels.addRelationship(rel.type(), relativeTarget);
                ridMapping.put(rel.id(), newRid);
            }
        }

        // Update r:id references in master XML (only for non-layout refs)
        SlidePart.updateRidReferences(destRoot, ridMapping);

        // Save master XML (will be updated again later with layout refs)
        destPackage.setPartBytes(destPartName, SlidePart.serializeXml(destRoot));
        destRels.save();

        // Add content type
        var ctManager = new ContentTypesManager(destPackage);
        ctManager.addOverride(destPartName, ContentTypesManager.CONTENT_TYPES.get("slide_master"));
        ctManager.save();

        return ridMapping;
    }

    /**
     * Clones a layout slide part from one package to another.
     *
     * @param sourcePackage       the source OPC package
     * @param sourcePartName      the source layout slide part name
     * @param destPackage         the destination OPC package
     * @param destPartName        the destination layout slide part name
     * @param destMasterPartName  the destination master slide part name to reference
     * @throws IllegalArgumentException if the source layout slide part is not found
     */
    public void cloneLayoutPart(OpcPackage sourcePackage, String sourcePartName,
                                OpcPackage destPackage, String destPartName,
                                String destMasterPartName) {
        // Copy layout XML
        byte[] sourceContent = sourcePackage.getPartBytes(sourcePartName);
        if (sourceContent == null) {
            throw new IllegalArgumentException("Layout slide not found: " + sourcePartName);
        }

        Document destDoc = SlidePart.parseXml(sourceContent);
        Element destRoot = destDoc.getDocumentElement();

        // Copy relationships
        var sourceRels = new RelsHelper(sourcePackage, sourcePartName);
        var destRels = new RelsHelper(destPackage, destPartName);
        Map<String, String> ridMapping = new HashMap<>();

        for (RelsHelper.RelEntry rel : sourceRels.getAllRelationships()) {
            if (REL_TYPE_SLIDE_MASTER.equals(rel.type())) {
                // Point to the cloned master
                String relativeTarget = SlidePart.computeRelativeTarget(
                        destPartName, destMasterPartName);
                String newRid = destRels.addRelationship(rel.type(), relativeTarget);
                ridMapping.put(rel.id(), newRid);
            } else if ("External".equals(rel.targetMode())) {
                String newRid = destRels.addRelationship(
                        rel.type(), rel.target(), "External");
                ridMapping.put(rel.id(), newRid);
            } else {
                String sourceTarget = SlidePart.resolveTargetStatic(sourcePartName, rel.target());
                String destTarget = SlidePart.cloneRelatedPart(
                        sourcePackage, sourceTarget,
                        destPackage, destPartName, rel.type());
                String relativeTarget = SlidePart.computeRelativeTarget(destPartName, destTarget);
                String newRid = destRels.addRelationship(rel.type(), relativeTarget);
                ridMapping.put(rel.id(), newRid);
            }
        }

        // Update r:id references
        SlidePart.updateRidReferences(destRoot, ridMapping);

        // Save layout XML
        destPackage.setPartBytes(destPartName, SlidePart.serializeXml(destRoot));
        destRels.save();

        // Add content type
        var ctManager = new ContentTypesManager(destPackage);
        ctManager.addOverride(destPartName, ContentTypesManager.CONTENT_TYPES.get("slide_layout"));
        ctManager.save();
    }

    /**
     * Finds the maximum ID across all master slide IDs and layout IDs in the presentation.
     *
     * <p>In PPTX, {@code sldMasterIdLst} and {@code sldLayoutIdLst} share the same ID space.</p>
     *
     * @return the maximum ID found, or {@code 2147483647} if none exceed the convention start
     */
    public long getMaxMasterLayoutIdInPresentation() {
        long maxId = 2147483647L; // One below the PPTX convention start

        // Check master IDs from presentation.xml
        for (MasterReference ref : presentationPart.masterReferences()) {
            if (ref.getMasterId() > maxId) {
                maxId = ref.getMasterId();
            }
        }

        // Check layout IDs from all master slide XML files
        for (String partName : opcPackage.getPartNames()) {
            if (partName.startsWith("ppt/slideMasters/") && partName.endsWith(".xml")) {
                Document masterDoc = opcPackage.parseXml(partName);
                if (masterDoc != null) {
                    NodeList layouts = masterDoc.getElementsByTagNameNS(NS_P, "sldLayoutId");
                    for (int i = 0; i < layouts.getLength(); i++) {
                        Element el = (Element) layouts.item(i);
                        String idStr = el.getAttribute("id");
                        if (!idStr.isEmpty()) {
                            long layoutId = Long.parseLong(idStr);
                            if (layoutId > maxId) {
                                maxId = layoutId;
                            }
                        }
                    }
                }
            }
        }

        return maxId;
    }

    /**
     * Updates a master slide's relationships and XML to point to cloned layouts.
     *
     * @param sourcePackage          the source OPC package
     * @param sourceMasterPartName   the source master's part name
     * @param destMasterPartName     the destination master's part name
     * @param layoutMapping          map of old layout path to new layout path
     * @param sourceLayoutRids       map of source layout path to source relationship ID
     */
    public void updateMasterLayoutRelationships(OpcPackage sourcePackage,
                                                String sourceMasterPartName,
                                                String destMasterPartName,
                                                Map<String, String> layoutMapping,
                                                Map<String, String> sourceLayoutRids) {
        var masterRels = new RelsHelper(opcPackage, destMasterPartName);

        // Track source r:id -> new r:id mapping for layout relationships
        Map<String, String> layoutRidMapping = new HashMap<>();

        for (var entry : layoutMapping.entrySet()) {
            String oldLayoutPath = entry.getKey();
            String newLayoutPath = entry.getValue();

            String relativeTarget = SlidePart.computeRelativeTarget(
                    destMasterPartName, newLayoutPath);
            String newRid = masterRels.addRelationship(REL_TYPE_SLIDE_LAYOUT, relativeTarget);

            // Map old rid to new rid
            String oldRid = sourceLayoutRids.get(oldLayoutPath);
            if (oldRid != null) {
                layoutRidMapping.put(oldRid, newRid);
            }
        }

        masterRels.save();

        // Update the master XML's sldLayoutIdLst references
        Document masterDoc = opcPackage.parseXml(destMasterPartName);
        if (masterDoc != null) {
            Element masterRoot = masterDoc.getDocumentElement();

            // Update r:id references in sldLayoutIdLst
            SlidePart.updateRidReferences(masterRoot, layoutRidMapping);

            // Renumber layout IDs to avoid conflicts with existing masters
            long nextLayoutId = getMaxMasterLayoutIdInPresentation() + 1;
            NodeList layoutIdElements = masterRoot.getElementsByTagNameNS(NS_P, "sldLayoutId");
            for (int i = 0; i < layoutIdElements.getLength(); i++) {
                Element elem = (Element) layoutIdElements.item(i);
                elem.setAttribute("id", String.valueOf(nextLayoutId));
                nextLayoutId++;
            }

            // Save updated master XML
            opcPackage.serializeXml(destMasterPartName, masterDoc);
        }
    }
}
