package org.aspose.slides.foss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link AdjustValue}.
 *
 * <p>Covers adjustment value read/write, bounds validation, and connector adjustments.</p>
 */
class AdjustValueTest {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";

    private Document doc;

    @BeforeEach
    void setUp() throws Exception {
        doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .newDocument();
    }

    private Element createGdElement(String name, String fmla) {
        Element gd = doc.createElementNS(NS_A, "a:gd");
        gd.setAttribute("name", name);
        gd.setAttribute("fmla", fmla);
        doc.appendChild(gd);
        return gd;
    }

    // --- name ---

    @Test
    void getName_returnsAttributeValue() {
        Element gd = createGdElement("adj1", "val 50000");
        var adj = new AdjustValue(gd, null);
        assertThat(adj.getName()).isEqualTo("adj1");
    }

    @Test
    void getName_returnsEmptyStringWhenMissing() {
        Element gd = doc.createElementNS(NS_A, "a:gd");
        doc.appendChild(gd);
        var adj = new AdjustValue(gd, null);
        assertThat(adj.getName()).isEmpty();
    }

    // --- raw_value ---

    @Test
    void getRawValue_parsesValFormula() {
        Element gd = createGdElement("adj1", "val 50000");
        var adj = new AdjustValue(gd, null);
        assertThat(adj.getRawValue()).isEqualTo(50000);
    }

    @Test
    void getRawValue_returnsZeroForNonValFormula() {
        Element gd = createGdElement("adj1", "*/w 100");
        var adj = new AdjustValue(gd, null);
        assertThat(adj.getRawValue()).isEqualTo(0);
    }

    @Test
    void getRawValue_returnsZeroForEmptyFormula() {
        Element gd = createGdElement("adj1", "");
        var adj = new AdjustValue(gd, null);
        assertThat(adj.getRawValue()).isEqualTo(0);
    }

    @Test
    void getRawValue_returnsZeroForInvalidNumber() {
        Element gd = createGdElement("adj1", "val notanumber");
        var adj = new AdjustValue(gd, null);
        assertThat(adj.getRawValue()).isEqualTo(0);
    }

    @Test
    void setRawValue_updatesFormulaAttribute() {
        Element gd = createGdElement("adj1", "val 50000");
        var adj = new AdjustValue(gd, null);
        adj.setRawValue(30000);
        assertThat(adj.getRawValue()).isEqualTo(30000);
        assertThat(gd.getAttribute("fmla")).isEqualTo("val 30000");
    }

    @Test
    void setRawValue_invokesSaveCallback() {
        Element gd = createGdElement("adj1", "val 50000");
        AtomicBoolean saved = new AtomicBoolean(false);
        var adj = new AdjustValue(gd, () -> saved.set(true));
        adj.setRawValue(10000);
        assertThat(saved).isTrue();
    }

    @Test
    void setRawValue_noCallbackDoesNotThrow() {
        Element gd = createGdElement("adj1", "val 50000");
        var adj = new AdjustValue(gd, null);
        adj.setRawValue(10000);
        assertThat(adj.getRawValue()).isEqualTo(10000);
    }

    // --- angle_value ---

    @Test
    void getAngleValue_convertsFromRawValue() {
        Element gd = createGdElement("adj1", "val 5400000");
        var adj = new AdjustValue(gd, null);
        assertThat(adj.getAngleValue()).isEqualTo(90.0);
    }

    @Test
    void getAngleValue_zeroWhenRawValueIsZero() {
        Element gd = createGdElement("adj1", "val 0");
        var adj = new AdjustValue(gd, null);
        assertThat(adj.getAngleValue()).isEqualTo(0.0);
    }

    @Test
    void setAngleValue_convertsToRawValue() {
        Element gd = createGdElement("adj1", "val 0");
        var adj = new AdjustValue(gd, null);
        adj.setAngleValue(90.0);
        assertThat(adj.getRawValue()).isEqualTo(5400000);
    }

    @Test
    void setAngleValue_roundsCorrectly() {
        Element gd = createGdElement("adj1", "val 0");
        var adj = new AdjustValue(gd, null);
        adj.setAngleValue(45.5);
        assertThat(adj.getRawValue()).isEqualTo(2730000);
    }

    @Test
    void angleValue_roundTrip() {
        Element gd = createGdElement("adj1", "val 0");
        var adj = new AdjustValue(gd, null);
        adj.setAngleValue(123.456);
        // Round-trip: set angle, then get angle back
        double result = adj.getAngleValue();
        assertThat(result).isCloseTo(123.456, org.assertj.core.data.Offset.offset(0.001));
    }

    // --- adjustment_properties ---

    @Test
    void adjustmentValues_exposeNameRawValueAngleValue() {
        Element gd = createGdElement("adj1", "val 50000");
        var adj = new AdjustValue(gd, null);
        assertThat(adj.getName()).isNotNull();
        assertThat(adj.getRawValue()).isInstanceOf(Long.class);
        assertThat(adj.getAngleValue()).isInstanceOf(Double.class);
    }

    // --- bent_connector_adjustments ---

    @Test
    void rawValue_persistsAfterModification() {
        Element gd = createGdElement("adj1", "val 50000");
        var adj = new AdjustValue(gd, null);
        adj.setRawValue(30000);

        // Re-read from the same XML element (simulates save/reload)
        var adj2 = new AdjustValue(gd, null);
        assertThat(adj2.getRawValue()).isEqualTo(30000);
    }
}
