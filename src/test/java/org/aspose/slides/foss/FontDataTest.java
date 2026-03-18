package org.aspose.slides.foss;

import org.junit.jupiter.api.Test;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for {@link FontData}.
 *
 * <p>Covers font name read/write, equality, and round-trip persistence.</p>
 */
class FontDataTest {

    private Presentation roundTrip(Presentation pres) throws IOException {
        var baos = new ByteArrayOutputStream();
        pres.save(baos);
        pres.dispose();
        return new Presentation(new ByteArrayInputStream(baos.toByteArray()));
    }

    /**
     * Returns the {@link TextFrame} from the given shape.
     */
    private static TextFrame textFrame(IAutoShape shape) {
        return (TextFrame) shape.getTextFrame();
    }

    @Test
    void fontName_returnsConstructorValue() {
        var fd = new FontData("Arial");
        assertThat(fd.getFontName()).isEqualTo("Arial");
        assertThat(fd.fontName()).isEqualTo("Arial");
    }

    @Test
    void constructor_rejectsNull() {
        assertThatThrownBy(() -> new FontData(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void equality_sameNameAreEqual() {
        assertThat(new FontData("Arial")).isEqualTo(new FontData("Arial"));
    }

    @Test
    void equality_differentNamesAreNotEqual() {
        assertThat(new FontData("Arial")).isNotEqualTo(new FontData("Calibri"));
    }

    /**
     * Latin font persists after save/reload.
     * Verifies latin font persists after save/reload.
     */
    @Test
    void latinFont_persistsAfterRoundTrip() throws IOException {
        var pres = new Presentation();
        var shape = pres.getSlides().get(0).getShapes()
                .addAutoShape(ShapeType.RECTANGLE, 50, 50, 400, 60);
        var tf = textFrame(shape);
        tf.setText("Sample");

        var fmt = tf.getParagraphs().get(0).getPortions().get(0).getPortionFormat();
        fmt.setLatinFont(new FontData("Courier New"));

        try (var pres2 = roundTrip(pres)) {
            var shape2 = (IAutoShape) pres2.getSlides().get(0).getShapes().get(0);
            var tf2 = textFrame(shape2);
            var fmt2 = tf2.getParagraphs().get(0).getPortions().get(0).getPortionFormat();

            assertThat(fmt2.getLatinFont()).isNotNull();
            assertThat(fmt2.getLatinFont().getFontName()).isEqualTo("Courier New");
        }
    }
}
