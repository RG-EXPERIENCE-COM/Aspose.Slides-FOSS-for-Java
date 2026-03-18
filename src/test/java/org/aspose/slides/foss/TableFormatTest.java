package org.aspose.slides.foss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link TableFormat} and the {@link ITableFormat} contract.
 *
 * <p>Verifies that the table format exposes a fill format consistent with the underlying XML.</p>
 */
class TableFormatTest {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";

    private Document doc;

    @BeforeEach
    void setUp() throws Exception {
        doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .newDocument();
    }

    private Element createTblPr() {
        Element tblPr = doc.createElementNS(NS_A, "a:tblPr");
        doc.appendChild(tblPr);
        return tblPr;
    }

    @Test
    void getFillFormat_returnsNonNullWhenBackedByElement() {
        Element tblPr = createTblPr();
        ITableFormat tf = new TableFormat().initInternal(tblPr, null, null);
        assertThat(tf.getFillFormat()).isNotNull();
    }

    @Test
    void getFillFormat_returnsNullWhenNoBackingElement() {
        ITableFormat tf = new TableFormat();
        assertThat(tf.getFillFormat()).isNull();
    }

    @Test
    void getFillFormat_returnsFillFormatInstance() {
        Element tblPr = createTblPr();
        ITableFormat tf = new TableFormat().initInternal(tblPr, null, null);
        assertThat(tf.getFillFormat()).isInstanceOf(IFillFormat.class);
    }

    @Test
    void getFillFormat_solidFillPersistsViaTableFormat() {
        Element tblPr = createTblPr();
        ITableFormat tf = new TableFormat().initInternal(tblPr, null, null);

        IFillFormat ff = tf.getFillFormat();
        ff.setFillType(FillType.SOLID);

        // Re-read through a new TableFormat wrapping the same element
        ITableFormat tf2 = new TableFormat().initInternal(tblPr, null, null);
        assertThat(tf2.getFillFormat().getFillType()).isEqualTo(FillType.SOLID);
    }

    @Test
    void getFillFormat_noFillPersistsViaTableFormat() {
        Element tblPr = createTblPr();
        ITableFormat tf = new TableFormat().initInternal(tblPr, null, null);

        tf.getFillFormat().setFillType(FillType.NO_FILL);

        ITableFormat tf2 = new TableFormat().initInternal(tblPr, null, null);
        assertThat(tf2.getFillFormat().getFillType()).isEqualTo(FillType.NO_FILL);
    }

    @Test
    void saveCallback_invokedOnFillTypeChange() {
        Element tblPr = createTblPr();
        int[] callCount = {0};
        ITableFormat tf = new TableFormat().initInternal(tblPr, () -> callCount[0]++, null);

        tf.getFillFormat().setFillType(FillType.SOLID);
        assertThat(callCount[0]).isGreaterThan(0);
    }
}
