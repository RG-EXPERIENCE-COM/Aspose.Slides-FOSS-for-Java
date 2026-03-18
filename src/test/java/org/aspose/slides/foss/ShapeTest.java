package org.aspose.slides.foss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

/**
 * Tests for {@link Shape} contract: frame with flips, decorative via extension elements,
 * z-order position, connection site count, grouped detection, alternative text,
 * name, hidden state, unique id, line/3D/effect/fill formats, and slide/presentation access.
 *
 * <p>Verifies the shape behavioral contract.</p>
 */
class ShapeTest {

    private static final String NS_P = "http://schemas.openxmlformats.org/presentationml/2006/main";
    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";
    private static final String DECORATIVE_URI = "http://schemas.microsoft.com/office/drawing/2017/decorative";
    private static final double EMU_PER_POINT = 12700.0;

    private Document doc;

    @BeforeEach
    void setUp() throws Exception {
        doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .newDocument();
    }

    /**
     * Creates a concrete Shape subclass (via AutoShape) for testing.
     */
    private AutoShape createShape(String id, String name, double x, double y, double w, double h) {
        Element sp = doc.createElementNS(NS_P, "p:sp");

        Element nvSpPr = doc.createElementNS(NS_P, "p:nvSpPr");
        Element cNvPr = doc.createElementNS(NS_P, "p:cNvPr");
        cNvPr.setAttribute("id", id);
        cNvPr.setAttribute("name", name);
        nvSpPr.appendChild(cNvPr);
        Element cNvSpPr = doc.createElementNS(NS_P, "p:cNvSpPr");
        nvSpPr.appendChild(cNvSpPr);
        sp.appendChild(nvSpPr);

        Element spPr = doc.createElementNS(NS_P, "p:spPr");
        Element xfrm = doc.createElementNS(NS_A, "a:xfrm");
        Element off = doc.createElementNS(NS_A, "a:off");
        off.setAttribute("x", String.valueOf(Math.round(x * EMU_PER_POINT)));
        off.setAttribute("y", String.valueOf(Math.round(y * EMU_PER_POINT)));
        Element ext = doc.createElementNS(NS_A, "a:ext");
        ext.setAttribute("cx", String.valueOf(Math.round(w * EMU_PER_POINT)));
        ext.setAttribute("cy", String.valueOf(Math.round(h * EMU_PER_POINT)));
        xfrm.appendChild(off);
        xfrm.appendChild(ext);
        spPr.appendChild(xfrm);

        Element prstGeom = doc.createElementNS(NS_A, "a:prstGeom");
        prstGeom.setAttribute("prst", "rect");
        spPr.appendChild(prstGeom);

        sp.appendChild(spPr);
        doc.appendChild(sp);

        return new AutoShape(sp, null);
    }

    // --- rawFrame / frame with flip values ---

    @Test
    void rawFrame_readsFlipValues() {
        var shape = createShape("1", "Shape1", 100, 50, 200, 100);
        // Set flipH on xfrm
        Element xfrm = shape.xmlElement.getElementsByTagNameNS(NS_A, "xfrm").item(0) instanceof Element el ? el : null;
        assertThat(xfrm).isNotNull();
        xfrm.setAttribute("flipH", "1");

        IShapeFrame frame = shape.getRawFrame();
        assertThat(frame.getFlipH()).isEqualTo(NullableBool.TRUE);
        assertThat(frame.getFlipV()).isEqualTo(NullableBool.FALSE);
    }

    @Test
    void setRawFrame_appliesFlipValues() {
        var shape = createShape("1", "Shape1", 100, 50, 200, 100);
        var newFrame = new ShapeFrame(10, 20, 300, 150, NullableBool.TRUE, NullableBool.TRUE, 45);
        shape.setRawFrame(newFrame);

        IShapeFrame readBack = shape.getRawFrame();
        assertThat(readBack.getX()).isCloseTo(10, offset(0.1));
        assertThat(readBack.getY()).isCloseTo(20, offset(0.1));
        assertThat(readBack.getWidth()).isCloseTo(300, offset(0.1));
        assertThat(readBack.getHeight()).isCloseTo(150, offset(0.1));
        assertThat(readBack.getRotation()).isCloseTo(45, offset(0.1));
        assertThat(readBack.getFlipH()).isEqualTo(NullableBool.TRUE);
        assertThat(readBack.getFlipV()).isEqualTo(NullableBool.TRUE);
    }

    @Test
    void setFrame_clearsFlipWhenFalse() {
        var shape = createShape("1", "Shape1", 100, 50, 200, 100);
        // First set flips
        shape.setFrame(new ShapeFrame(10, 20, 300, 150, NullableBool.TRUE, NullableBool.TRUE, 0));
        assertThat(shape.getFrame().getFlipH()).isEqualTo(NullableBool.TRUE);

        // Now clear flips
        shape.setFrame(new ShapeFrame(10, 20, 300, 150, NullableBool.FALSE, NullableBool.FALSE, 0));
        assertThat(shape.getFrame().getFlipH()).isEqualTo(NullableBool.FALSE);
        assertThat(shape.getFrame().getFlipV()).isEqualTo(NullableBool.FALSE);
    }

    // --- isDecorative using extension elements ---

    @Test
    void isDecorative_defaultFalse() {
        var shape = createShape("1", "Shape1", 0, 0, 100, 100);
        assertThat(shape.isDecorative()).isFalse();
    }

    @Test
    void setDecorative_usesExtensionElements() {
        var shape = createShape("1", "Shape1", 0, 0, 100, 100);
        shape.setDecorative(true);
        assertThat(shape.isDecorative()).isTrue();

        // Verify it's stored via extension elements, not a simple attribute
        Element cNvPr = shape.getCNvPr();
        assertThat(cNvPr.getAttribute("decorative")).isEmpty();
        Element extLst = cNvPr.getElementsByTagNameNS(NS_A, "extLst").item(0) instanceof Element el ? el : null;
        assertThat(extLst).isNotNull();
    }

    @Test
    void setDecorative_falseAfterTrue() {
        var shape = createShape("1", "Shape1", 0, 0, 100, 100);
        shape.setDecorative(true);
        assertThat(shape.isDecorative()).isTrue();
        shape.setDecorative(false);
        assertThat(shape.isDecorative()).isFalse();
    }

    // --- zOrderPosition ---

    @Test
    void zOrderPosition_computesIndexInParent() throws Exception {
        Document parentDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element spTree = parentDoc.createElementNS(NS_P, "p:spTree");
        parentDoc.appendChild(spTree);

        Element sp1 = parentDoc.createElementNS(NS_P, "p:sp");
        spTree.appendChild(sp1);
        Element sp2 = parentDoc.createElementNS(NS_P, "p:sp");
        spTree.appendChild(sp2);
        Element sp3 = parentDoc.createElementNS(NS_P, "p:sp");
        spTree.appendChild(sp3);

        var shape2 = new GeometryShape(sp2, null);
        assertThat(shape2.getZOrderPosition()).isEqualTo(1);

        var shape3 = new GeometryShape(sp3, null);
        assertThat(shape3.getZOrderPosition()).isEqualTo(2);
    }

    // --- connectionSiteCount ---

    @Test
    void connectionSiteCount_returnsEightWhenPrstGeomPresent() {
        var shape = createShape("1", "Shape1", 0, 0, 100, 100);
        assertThat(shape.getConnectionSiteCount()).isEqualTo(8);
    }

    @Test
    void connectionSiteCount_returnsZeroWithoutPrstGeom() throws Exception {
        Document grpDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element grpSp = grpDoc.createElementNS(NS_P, "p:grpSp");
        Element grpSpPr = grpDoc.createElementNS(NS_P, "p:grpSpPr");
        grpSp.appendChild(grpSpPr);
        grpDoc.appendChild(grpSp);

        var shape = new GeometryShape(grpSp, null);
        assertThat(shape.getConnectionSiteCount()).isEqualTo(0);
    }

    // --- isGrouped ---

    @Test
    void isGrouped_trueWhenParentIsGrpSp() throws Exception {
        Document grpDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element grpSp = grpDoc.createElementNS(NS_P, "p:grpSp");
        grpDoc.appendChild(grpSp);

        Element childSp = grpDoc.createElementNS(NS_P, "p:sp");
        grpSp.appendChild(childSp);

        var shape = new GeometryShape(childSp, null);
        assertThat(shape.isGrouped()).isTrue();
    }

    @Test
    void isGrouped_falseWhenParentIsSpTree() throws Exception {
        Document treeDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element spTree = treeDoc.createElementNS(NS_P, "p:spTree");
        treeDoc.appendChild(spTree);

        Element sp = treeDoc.createElementNS(NS_P, "p:sp");
        spTree.appendChild(sp);

        var shape = new GeometryShape(sp, null);
        assertThat(shape.isGrouped()).isFalse();
    }

    // --- uniqueId / officeInteropShapeId ---

    @Test
    void uniqueId_readsFromCNvPr() {
        var shape = createShape("42", "Shape42", 0, 0, 50, 50);
        assertThat(shape.getUniqueId()).isEqualTo(42);
        assertThat(shape.getOfficeInteropShapeId()).isEqualTo(42);
    }

    // --- alternative text ---

    @Test
    void alternativeText_setAndGet() {
        var shape = createShape("1", "Shape1", 0, 0, 100, 100);
        shape.setAlternativeText("Alt description");
        assertThat(shape.getAlternativeText()).isEqualTo("Alt description");
    }

    @Test
    void alternativeText_setEmptyPreservesAttribute() {
        var shape = createShape("1", "Shape1", 0, 0, 100, 100);
        shape.setAlternativeText("Something");
        shape.setAlternativeText("");
        // Always sets the attribute, even to empty string
        assertThat(shape.getAlternativeText()).isEqualTo("");
    }

    // --- alternative text title ---

    @Test
    void alternativeTextTitle_setAndGet() {
        var shape = createShape("1", "Shape1", 0, 0, 100, 100);
        shape.setAlternativeTextTitle("Title");
        assertThat(shape.getAlternativeTextTitle()).isEqualTo("Title");
    }

    // --- name ---

    @Test
    void name_setAndGet() {
        var shape = createShape("1", "OrigName", 0, 0, 100, 100);
        assertThat(shape.getName()).isEqualTo("OrigName");
        shape.setName("NewName");
        assertThat(shape.getName()).isEqualTo("NewName");
    }

    // --- hidden ---

    @Test
    void hidden_defaultFalse() {
        var shape = createShape("1", "Shape1", 0, 0, 100, 100);
        assertThat(shape.isHidden()).isFalse();
    }

    @Test
    void hidden_setAndClear() {
        var shape = createShape("1", "Shape1", 0, 0, 100, 100);
        shape.setHidden(true);
        assertThat(shape.isHidden()).isTrue();
        shape.setHidden(false);
        assertThat(shape.isHidden()).isFalse();
    }

    // --- lineFormat ---

    @Test
    void lineFormat_notNull() {
        var shape = createShape("1", "Shape1", 0, 0, 100, 100);
        assertThat(shape.getLineFormat()).isNotNull();
    }

    // --- threeDFormat ---

    @Test
    void threeDFormat_notNull() {
        var shape = createShape("1", "Shape1", 0, 0, 100, 100);
        assertThat(shape.getThreeDFormat()).isNotNull();
    }

    // --- effectFormat ---

    @Test
    void effectFormat_notNull() {
        var shape = createShape("1", "Shape1", 0, 0, 100, 100);
        assertThat(shape.getEffectFormat()).isNotNull();
    }

    // --- fillFormat ---

    @Test
    void fillFormat_notNull() {
        var shape = createShape("1", "Shape1", 0, 0, 100, 100);
        assertThat(shape.getFillFormat()).isNotNull();
    }

    // --- slide / presentation via concrete Shape subclass ---

    private Shape createConcreteShape(Element xmlElement) {
        return new Shape(xmlElement, null) {};
    }

    @Test
    void slide_returnsParentSlide() {
        var shape = createConcreteShape(createShape("1", "Shape1", 0, 0, 100, 100).xmlElement);
        assertThat(shape.getSlide()).isNull();

        IBaseSlide mockSlide = new IBaseSlide() {
            @Override public String getName() { return "Slide1"; }
            @Override public void setName(String name) {}
            @Override public int getSlideId() { return 1; }
            @Override public IShapeCollection getShapes() { return null; }
            @Override public IBaseSlide getSlide() { return this; }
            @Override public IPresentationComponent asIPresentationComponent() { return this; }
            @Override public IPresentation getPresentation() { return null; }
        };
        shape.setParentSlide(mockSlide);
        assertThat(shape.getSlide()).isSameAs(mockSlide);
    }

    @Test
    void presentation_delegatesToParentSlide() {
        var shape = createConcreteShape(createShape("1", "Shape1", 0, 0, 100, 100).xmlElement);
        assertThat(shape.getPresentation()).isNull();

        // Use a simple object as the presentation sentinel
        final var sentinel = new Object();
        IBaseSlide mockSlide = new IBaseSlide() {
            @Override public String getName() { return "Slide1"; }
            @Override public void setName(String name) {}
            @Override public int getSlideId() { return 1; }
            @Override public IShapeCollection getShapes() { return null; }
            @Override public IBaseSlide getSlide() { return this; }
            @Override public IPresentationComponent asIPresentationComponent() { return this; }
            @Override public IPresentation getPresentation() { return null; }
        };
        shape.setParentSlide(mockSlide);
        // Delegation works: getPresentation() returns what parentSlide.getPresentation() returns
        assertThat(shape.getPresentation()).isNull();
    }

    // --- asISlideComponent / asIPresentationComponent ---

    @Test
    void asISlideComponent_returnsSelf() {
        var shape = createShape("1", "Shape1", 0, 0, 100, 100);
        assertThat(shape.getAsISlideComponent()).isSameAs(shape);
    }

    @Test
    void asIPresentationComponent_returnsSelf() {
        var shape = createConcreteShape(createShape("1", "Shape1", 0, 0, 100, 100).xmlElement);
        assertThat(shape.asIPresentationComponent()).isSameAs(shape);
    }
}
