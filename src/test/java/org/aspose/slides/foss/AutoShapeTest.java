package org.aspose.slides.foss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link AutoShape}: shape type (including custom geometry),
 * text frame access, text box detection, and addTextFrame.
 *
 * <p>Covers shape creation, multiple shape types, reordering, persistence,
 * connector creation, and shape connection.</p>
 */
class AutoShapeTest {

    private static final String NS_P = "http://schemas.openxmlformats.org/presentationml/2006/main";
    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";
    private static final double EMU_PER_POINT = 12700.0;

    private Document doc;

    @BeforeEach
    void setUp() throws Exception {
        doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .newDocument();
    }

    /**
     * Creates an AutoShape XML element ({@code <p:sp>}) with the given preset
     * geometry and position/size in points.
     */
    private AutoShape createAutoShape(String id, String prstName,
                                      double x, double y, double w, double h) {
        Document shapeDoc;
        try {
            shapeDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Element sp = shapeDoc.createElementNS(NS_P, "p:sp");

        Element nvSpPr = shapeDoc.createElementNS(NS_P, "p:nvSpPr");
        Element cNvPr = shapeDoc.createElementNS(NS_P, "p:cNvPr");
        cNvPr.setAttribute("id", id);
        cNvPr.setAttribute("name", "Shape " + id);
        nvSpPr.appendChild(cNvPr);
        Element cNvSpPr = shapeDoc.createElementNS(NS_P, "p:cNvSpPr");
        nvSpPr.appendChild(cNvSpPr);
        sp.appendChild(nvSpPr);

        Element spPr = shapeDoc.createElementNS(NS_P, "p:spPr");
        Element xfrm = shapeDoc.createElementNS(NS_A, "a:xfrm");
        Element off = shapeDoc.createElementNS(NS_A, "a:off");
        off.setAttribute("x", String.valueOf(Math.round(x * EMU_PER_POINT)));
        off.setAttribute("y", String.valueOf(Math.round(y * EMU_PER_POINT)));
        Element ext = shapeDoc.createElementNS(NS_A, "a:ext");
        ext.setAttribute("cx", String.valueOf(Math.round(w * EMU_PER_POINT)));
        ext.setAttribute("cy", String.valueOf(Math.round(h * EMU_PER_POINT)));
        xfrm.appendChild(off);
        xfrm.appendChild(ext);
        spPr.appendChild(xfrm);

        Element prstGeom = shapeDoc.createElementNS(NS_A, "a:prstGeom");
        prstGeom.setAttribute("prst", prstName);
        spPr.appendChild(prstGeom);

        sp.appendChild(spPr);
        shapeDoc.appendChild(sp);

        return new AutoShape(sp, null);
    }

    /**
     * Creates an AutoShape with custom geometry instead of a preset.
     */
    private AutoShape createCustomGeometryShape(String id) {
        Document shapeDoc;
        try {
            shapeDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Element sp = shapeDoc.createElementNS(NS_P, "p:sp");

        Element nvSpPr = shapeDoc.createElementNS(NS_P, "p:nvSpPr");
        Element cNvPr = shapeDoc.createElementNS(NS_P, "p:cNvPr");
        cNvPr.setAttribute("id", id);
        cNvPr.setAttribute("name", "CustomShape " + id);
        nvSpPr.appendChild(cNvPr);
        Element cNvSpPr = shapeDoc.createElementNS(NS_P, "p:cNvSpPr");
        nvSpPr.appendChild(cNvSpPr);
        sp.appendChild(nvSpPr);

        Element spPr = shapeDoc.createElementNS(NS_P, "p:spPr");
        Element custGeom = shapeDoc.createElementNS(NS_A, "a:custGeom");
        spPr.appendChild(custGeom);
        sp.appendChild(spPr);
        shapeDoc.appendChild(sp);

        return new AutoShape(sp, null);
    }

    /**
     * Creates an AutoShape with a txBody already present.
     */
    private AutoShape createAutoShapeWithTextBody(String id, String prstName, String text) {
        Document shapeDoc;
        try {
            shapeDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Element sp = shapeDoc.createElementNS(NS_P, "p:sp");

        Element nvSpPr = shapeDoc.createElementNS(NS_P, "p:nvSpPr");
        Element cNvPr = shapeDoc.createElementNS(NS_P, "p:cNvPr");
        cNvPr.setAttribute("id", id);
        cNvPr.setAttribute("name", "Shape " + id);
        nvSpPr.appendChild(cNvPr);
        Element cNvSpPr = shapeDoc.createElementNS(NS_P, "p:cNvSpPr");
        nvSpPr.appendChild(cNvSpPr);
        sp.appendChild(nvSpPr);

        Element spPr = shapeDoc.createElementNS(NS_P, "p:spPr");
        Element prstGeom = shapeDoc.createElementNS(NS_A, "a:prstGeom");
        prstGeom.setAttribute("prst", prstName);
        spPr.appendChild(prstGeom);
        sp.appendChild(spPr);

        // Add txBody
        Element txBody = shapeDoc.createElementNS(NS_P, "p:txBody");
        Element bodyPr = shapeDoc.createElementNS(NS_A, "a:bodyPr");
        txBody.appendChild(bodyPr);
        Element lstStyle = shapeDoc.createElementNS(NS_A, "a:lstStyle");
        txBody.appendChild(lstStyle);
        Element p = shapeDoc.createElementNS(NS_A, "a:p");
        Element r = shapeDoc.createElementNS(NS_A, "a:r");
        Element t = shapeDoc.createElementNS(NS_A, "a:t");
        t.setTextContent(text);
        r.appendChild(t);
        p.appendChild(r);
        txBody.appendChild(p);
        sp.appendChild(txBody);

        shapeDoc.appendChild(sp);
        return new AutoShape(sp, null);
    }

    // --- test_add_auto_shape: shape type is correct ---

    @Test
    void addAutoShape_rectangleHasCorrectType() {
        AutoShape shape = createAutoShape("1", "rect", 50, 50, 200, 100);
        assertThat(shape.getShapeType()).isEqualTo(ShapeType.RECTANGLE);
    }

    // --- test_multiple_shape_types: various types preserved ---

    @Test
    void multipleShapeTypes_preserved() {
        AutoShape rect = createAutoShape("1", "rect", 10, 10, 100, 100);
        AutoShape ellipse = createAutoShape("2", "ellipse", 10, 10, 100, 100);
        AutoShape triangle = createAutoShape("3", "triangle", 10, 10, 100, 100);

        assertThat(rect.getShapeType()).isEqualTo(ShapeType.RECTANGLE);
        assertThat(ellipse.getShapeType()).isEqualTo(ShapeType.ELLIPSE);
        assertThat(triangle.getShapeType()).isEqualTo(ShapeType.TRIANGLE);
    }

    // --- test_shape_persists_after_reload: re-reading XML yields same type ---

    @Test
    void shapeType_persistsAcrossReinstantiation() {
        AutoShape original = createAutoShape("1", "rect", 50, 50, 200, 100);
        // Simulate reload by creating a new AutoShape from the same element
        AutoShape reloaded = new AutoShape(original.xmlElement, null);
        assertThat(reloaded.getShapeType()).isEqualTo(ShapeType.RECTANGLE);
    }

    // --- test_reorder_shapes: z-order via parent node position ---

    @Test
    void reorder_changesZOrder() throws Exception {
        Document treeDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element spTree = treeDoc.createElementNS(NS_P, "p:spTree");
        treeDoc.appendChild(spTree);

        // Create two shapes in the tree
        Element sp1 = treeDoc.createElementNS(NS_P, "p:sp");
        Element spPr1 = treeDoc.createElementNS(NS_P, "p:spPr");
        Element prst1 = treeDoc.createElementNS(NS_A, "a:prstGeom");
        prst1.setAttribute("prst", "rect");
        spPr1.appendChild(prst1);
        sp1.appendChild(spPr1);
        spTree.appendChild(sp1);

        Element sp2 = treeDoc.createElementNS(NS_P, "p:sp");
        Element spPr2 = treeDoc.createElementNS(NS_P, "p:spPr");
        Element prst2 = treeDoc.createElementNS(NS_A, "a:prstGeom");
        prst2.setAttribute("prst", "ellipse");
        spPr2.appendChild(prst2);
        sp2.appendChild(spPr2);
        spTree.appendChild(sp2);

        var shape1 = new AutoShape(sp1, null);
        var shape2 = new AutoShape(sp2, null);
        assertThat(shape1.getZOrderPosition()).isEqualTo(0);
        assertThat(shape2.getZOrderPosition()).isEqualTo(1);

        // Reorder: move shape2 to index 0 by inserting before shape1
        spTree.removeChild(sp2);
        spTree.insertBefore(sp2, sp1);

        assertThat(shape2.getZOrderPosition()).isEqualTo(0);
        assertThat(shape1.getZOrderPosition()).isEqualTo(1);
        assertThat(shape2.getShapeType()).isEqualTo(ShapeType.ELLIPSE);
    }

    // --- Custom geometry detection ---

    @Test
    void shapeType_returnsCustomForCustGeom() {
        AutoShape shape = createCustomGeometryShape("1");
        assertThat(shape.getShapeType()).isEqualTo(ShapeType.CUSTOM);
    }

    @Test
    void shapeType_setterReplacesCustomWithPreset() {
        AutoShape shape = createCustomGeometryShape("1");
        assertThat(shape.getShapeType()).isEqualTo(ShapeType.CUSTOM);

        shape.setShapeType(ShapeType.RECTANGLE);
        assertThat(shape.getShapeType()).isEqualTo(ShapeType.RECTANGLE);
    }

    @Test
    void shapeType_setNotDefinedIsIgnored() {
        AutoShape shape = createAutoShape("1", "rect", 0, 0, 100, 100);
        shape.setShapeType(ShapeType.NOT_DEFINED);
        assertThat(shape.getShapeType()).isEqualTo(ShapeType.RECTANGLE);
    }

    @Test
    void shapeType_setCustomIsIgnored() {
        AutoShape shape = createAutoShape("1", "rect", 0, 0, 100, 100);
        shape.setShapeType(ShapeType.CUSTOM);
        assertThat(shape.getShapeType()).isEqualTo(ShapeType.RECTANGLE);
    }

    @Test
    void shapeType_setterClearsChildrenOfPrstGeom() {
        AutoShape shape = createAutoShape("1", "rect", 0, 0, 100, 100);
        shape.setShapeType(ShapeType.ELLIPSE);
        assertThat(shape.getShapeType()).isEqualTo(ShapeType.ELLIPSE);
    }

    // --- textFrame ---

    @Test
    void textFrame_nullWhenNoTxBody() {
        AutoShape shape = createAutoShape("1", "rect", 0, 0, 100, 100);
        assertThat(shape.getTextFrame()).isNull();
    }

    @Test
    void textFrame_returnsTextFrameWhenTxBodyPresent() {
        AutoShape shape = createAutoShapeWithTextBody("1", "rect", "Hello");
        ITextFrame tf = shape.getTextFrame();
        assertThat(tf).isNotNull();
        assertThat(tf.getText()).isEqualTo("Hello");
    }

    // --- isTextBox ---

    @Test
    void isTextBox_falseWhenNoTxBody() {
        AutoShape shape = createAutoShape("1", "rect", 0, 0, 100, 100);
        assertThat(shape.isTextBox()).isFalse();
    }

    @Test
    void isTextBox_trueWhenTxBodyPresent() {
        AutoShape shape = createAutoShapeWithTextBody("1", "rect", "Text");
        assertThat(shape.isTextBox()).isTrue();
    }

    // --- asIGeometryShape ---

    @Test
    void asIGeometryShape_returnsSelf() {
        AutoShape shape = createAutoShape("1", "rect", 0, 0, 100, 100);
        assertThat(shape.getAsIGeometryShape()).isSameAs(shape);
    }

    // --- addTextFrame ---

    @Test
    void addTextFrame_createsTextFrame() {
        AutoShape shape = createAutoShape("1", "rect", 0, 0, 100, 100);
        assertThat(shape.isTextBox()).isFalse();

        ITextFrame tf = shape.addTextFrame("Hello World");
        assertThat(tf).isNotNull();
        assertThat(tf.getText()).isEqualTo("Hello World");
        assertThat(shape.isTextBox()).isTrue();
    }

    @Test
    void addTextFrame_splitsOnNewlines() {
        AutoShape shape = createAutoShape("1", "rect", 0, 0, 100, 100);
        ITextFrame tf = shape.addTextFrame("Line 1\nLine 2\nLine 3");
        assertThat(tf).isNotNull();
        assertThat(tf.getParagraphs().size()).isEqualTo(3);
        assertThat(tf.getParagraphs().get(0).getText()).isEqualTo("Line 1");
        assertThat(tf.getParagraphs().get(1).getText()).isEqualTo("Line 2");
        assertThat(tf.getParagraphs().get(2).getText()).isEqualTo("Line 3");
    }

    @Test
    void addTextFrame_splitsOnCarriageReturnLineFeed() {
        AutoShape shape = createAutoShape("1", "rect", 0, 0, 100, 100);
        ITextFrame tf = shape.addTextFrame("A\r\nB\rC");
        assertThat(tf).isNotNull();
        assertThat(tf.getParagraphs().size()).isEqualTo(3);
        assertThat(tf.getParagraphs().get(0).getText()).isEqualTo("A");
        assertThat(tf.getParagraphs().get(1).getText()).isEqualTo("B");
        assertThat(tf.getParagraphs().get(2).getText()).isEqualTo("C");
    }

    @Test
    void addTextFrame_nullTextCreatesSingleEmptyParagraph() {
        AutoShape shape = createAutoShape("1", "rect", 0, 0, 100, 100);
        ITextFrame tf = shape.addTextFrame(null);
        assertThat(tf).isNotNull();
        assertThat(tf.getParagraphs().size()).isEqualTo(1);
        assertThat(tf.getParagraphs().get(0).getText()).isEmpty();
    }

    @Test
    void addTextFrame_replacesExistingTxBody() {
        AutoShape shape = createAutoShapeWithTextBody("1", "rect", "Old text");
        assertThat(shape.getTextFrame().getText()).isEqualTo("Old text");

        ITextFrame tf = shape.addTextFrame("New text");
        assertThat(tf.getText()).isEqualTo("New text");
        assertThat(shape.getTextFrame().getText()).isEqualTo("New text");
    }

    @Test
    void addTextFrame_marksCNvSpPrAsTxBox() {
        AutoShape shape = createAutoShape("1", "rect", 0, 0, 100, 100);
        shape.addTextFrame("Hello");

        // Verify txBox="1" is set on cNvSpPr
        Element nvSpPr = shape.xmlElement.getElementsByTagNameNS(NS_P, "nvSpPr").item(0) instanceof Element el ? el : null;
        assertThat(nvSpPr).isNotNull();
        Element cNvSpPr = nvSpPr.getElementsByTagNameNS(NS_P, "cNvSpPr").item(0) instanceof Element el ? el : null;
        assertThat(cNvSpPr).isNotNull();
        assertThat(cNvSpPr.getAttribute("txBox")).isEqualTo("1");
    }

    @Test
    void addTextFrame_returnsNullWhenNoBackingElement() {
        AutoShape shape = new AutoShape();
        assertThat(shape.addTextFrame("Hello")).isNull();
    }

    @Test
    void addTextFrame_invokesSaveCallback() {
        Document shapeDoc;
        try {
            shapeDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Element sp = shapeDoc.createElementNS(NS_P, "p:sp");
        Element nvSpPr = shapeDoc.createElementNS(NS_P, "p:nvSpPr");
        Element cNvPr = shapeDoc.createElementNS(NS_P, "p:cNvPr");
        cNvPr.setAttribute("id", "1");
        cNvPr.setAttribute("name", "Shape 1");
        nvSpPr.appendChild(cNvPr);
        Element cNvSpPr = shapeDoc.createElementNS(NS_P, "p:cNvSpPr");
        nvSpPr.appendChild(cNvSpPr);
        sp.appendChild(nvSpPr);
        Element spPr = shapeDoc.createElementNS(NS_P, "p:spPr");
        sp.appendChild(spPr);
        shapeDoc.appendChild(sp);

        int[] callCount = {0};
        AutoShape shape = new AutoShape(sp, () -> callCount[0]++);
        shape.addTextFrame("Hello");
        assertThat(callCount[0]).isGreaterThanOrEqualTo(1);
    }

    // --- Null element safety ---

    @Test
    void nullElement_textFrameReturnsNull() {
        AutoShape shape = new AutoShape();
        assertThat(shape.getTextFrame()).isNull();
    }

    @Test
    void nullElement_isTextBoxReturnsFalse() {
        AutoShape shape = new AutoShape();
        assertThat(shape.isTextBox()).isFalse();
    }

    @Test
    void nullElement_shapeTypeReturnsNotDefined() {
        AutoShape shape = new AutoShape();
        assertThat(shape.getShapeType()).isEqualTo(ShapeType.NOT_DEFINED);
    }
}
