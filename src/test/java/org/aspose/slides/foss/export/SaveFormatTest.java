package org.aspose.slides.foss.export;

import org.aspose.slides.foss.Presentation;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SaveFormat}.
 */
class SaveFormatTest {

    /**
     * Saving to an OutputStream produces a non-empty buffer.
     */
    @Test
    void saveToStreamProducesNonEmptyBuffer() throws IOException {
        try (var pres = new Presentation()) {
            var buf = new ByteArrayOutputStream();
            pres.save(buf, SaveFormat.PPTX);
            assertThat(buf.size()).isGreaterThan(0);
        }
    }

    /**
     * Every enum constant has a non-null, non-empty string value.
     */
    @Test
    void allConstantsHaveNonEmptyValue() {
        for (SaveFormat format : SaveFormat.values()) {
            assertThat(format.getValue())
                    .as("Value for %s", format.name())
                    .isNotNull()
                    .isNotEmpty();
        }
    }

    /**
     * Verifies all expected constants are present.
     */
    @Test
    void containsAllDefinedConstants() {
        assertThat(SaveFormat.valueOf("PPT")).isNotNull();
        assertThat(SaveFormat.valueOf("PDF")).isNotNull();
        assertThat(SaveFormat.valueOf("XPS")).isNotNull();
        assertThat(SaveFormat.valueOf("PPTX")).isNotNull();
        assertThat(SaveFormat.valueOf("PPSX")).isNotNull();
        assertThat(SaveFormat.valueOf("TIFF")).isNotNull();
        assertThat(SaveFormat.valueOf("ODP")).isNotNull();
        assertThat(SaveFormat.valueOf("PPTM")).isNotNull();
        assertThat(SaveFormat.valueOf("PPSM")).isNotNull();
        assertThat(SaveFormat.valueOf("POTX")).isNotNull();
        assertThat(SaveFormat.valueOf("POTM")).isNotNull();
        assertThat(SaveFormat.valueOf("HTML")).isNotNull();
        assertThat(SaveFormat.valueOf("SWF")).isNotNull();
        assertThat(SaveFormat.valueOf("OTP")).isNotNull();
        assertThat(SaveFormat.valueOf("PPS")).isNotNull();
        assertThat(SaveFormat.valueOf("POT")).isNotNull();
        assertThat(SaveFormat.valueOf("FODP")).isNotNull();
        assertThat(SaveFormat.valueOf("GIF")).isNotNull();
        assertThat(SaveFormat.valueOf("HTML5")).isNotNull();
        assertThat(SaveFormat.valueOf("MD")).isNotNull();
        assertThat(SaveFormat.valueOf("XML")).isNotNull();
    }

    /**
     * Verifies string values match expected enum definitions.
     */
    @Test
    void stringValuesMatchDefinition() {
        assertThat(SaveFormat.PPT.getValue()).isEqualTo("Ppt");
        assertThat(SaveFormat.PDF.getValue()).isEqualTo("Pdf");
        assertThat(SaveFormat.XPS.getValue()).isEqualTo("Xps");
        assertThat(SaveFormat.PPTX.getValue()).isEqualTo("Pptx");
        assertThat(SaveFormat.PPSX.getValue()).isEqualTo("Ppsx");
        assertThat(SaveFormat.TIFF.getValue()).isEqualTo("Tiff");
        assertThat(SaveFormat.ODP.getValue()).isEqualTo("Odp");
        assertThat(SaveFormat.PPTM.getValue()).isEqualTo("Pptm");
        assertThat(SaveFormat.PPSM.getValue()).isEqualTo("Ppsm");
        assertThat(SaveFormat.POTX.getValue()).isEqualTo("Potx");
        assertThat(SaveFormat.POTM.getValue()).isEqualTo("Potm");
        assertThat(SaveFormat.HTML.getValue()).isEqualTo("Html");
        assertThat(SaveFormat.SWF.getValue()).isEqualTo("Swf");
        assertThat(SaveFormat.OTP.getValue()).isEqualTo("Otp");
        assertThat(SaveFormat.PPS.getValue()).isEqualTo("Pps");
        assertThat(SaveFormat.POT.getValue()).isEqualTo("Pot");
        assertThat(SaveFormat.FODP.getValue()).isEqualTo("Fodp");
        assertThat(SaveFormat.GIF.getValue()).isEqualTo("Gif");
        assertThat(SaveFormat.HTML5.getValue()).isEqualTo("Html5");
        assertThat(SaveFormat.MD.getValue()).isEqualTo("Md");
        assertThat(SaveFormat.XML.getValue()).isEqualTo("Xml");
    }
}
