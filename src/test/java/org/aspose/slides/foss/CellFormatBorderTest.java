package org.aspose.slides.foss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

/**
 * Tests for {@link CellFormat} border properties.
 *
 * <p>Verifies cell border formatting and persistence.</p>
 */
class CellFormatBorderTest {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";

    private Document doc;

    @BeforeEach
    void setUp() throws Exception {
        doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .newDocument();
    }

    private Element createTcPrElement() {
        Element tcPr = doc.createElementNS(NS_A, "a:tcPr");
        doc.appendChild(tcPr);
        return tcPr;
    }

    @Test
    void borderProperties_returnNonNull() {
        Element tcPr = createTcPrElement();
        var fmt = new CellFormat(tcPr, null);
        assertThat(fmt.getBorderTop()).isNotNull();
        assertThat(fmt.getBorderBottom()).isNotNull();
        assertThat(fmt.getBorderLeft()).isNotNull();
        assertThat(fmt.getBorderRight()).isNotNull();
        assertThat(fmt.getBorderDiagonalDown()).isNotNull();
        assertThat(fmt.getBorderDiagonalUp()).isNotNull();
    }

    @Test
    void borderWidth_setAndGet() {
        Element tcPr = createTcPrElement();
        var fmt = new CellFormat(tcPr, null);

        fmt.getBorderTop().setWidth(3.0);
        fmt.getBorderBottom().setWidth(3.0);
        fmt.getBorderLeft().setWidth(3.0);
        fmt.getBorderRight().setWidth(3.0);

        // Re-read from the same XML (simulates save/reload)
        var fmt2 = new CellFormat(tcPr, null);
        assertThat(fmt2.getBorderTop().getWidth()).isCloseTo(3.0, offset(0.001));
        assertThat(fmt2.getBorderBottom().getWidth()).isCloseTo(3.0, offset(0.001));
        assertThat(fmt2.getBorderLeft().getWidth()).isCloseTo(3.0, offset(0.001));
        assertThat(fmt2.getBorderRight().getWidth()).isCloseTo(3.0, offset(0.001));
    }

    @Test
    void borderFillFormat_returnsNonNull() {
        Element tcPr = createTcPrElement();
        var fmt = new CellFormat(tcPr, null);

        ILineFormat border = fmt.getBorderTop();
        border.setWidth(3.0);
        assertThat(border.getFillFormat()).isNotNull();
    }

    @Test
    void borderWidth_invokesSaveCallback() {
        Element tcPr = createTcPrElement();
        AtomicBoolean saved = new AtomicBoolean(false);
        var fmt = new CellFormat(tcPr, () -> saved.set(true));

        fmt.getBorderTop().setWidth(2.0);
        assertThat(saved).isTrue();
    }
}
