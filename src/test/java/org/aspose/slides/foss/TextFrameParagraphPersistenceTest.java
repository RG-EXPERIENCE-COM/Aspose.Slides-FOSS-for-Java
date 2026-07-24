package org.aspose.slides.foss;

import org.aspose.slides.foss.export.SaveFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Regression: paragraphs.add() must persist {@code <a:p>} nodes on save.
 */
class TextFrameParagraphPersistenceTest {

    @Test
    void addParagraphs_persistAcrossSaveReload(@TempDir Path tmp) throws Exception {
        Path file = tmp.resolve("bullets.pptx");

        try (Presentation prs = new Presentation()) {
            ISlide slide = prs.getSlides().get(0);
            IAutoShape shape = slide.getShapes().addAutoShape(ShapeType.RECTANGLE, 40, 40, 300, 120);
            shape.setName("Bullets");
            shape.addTextFrame("First");
            ITextFrame tf = shape.getTextFrame();
            IParagraph first = tf.getParagraphs().get(0);
            first.getParagraphFormat().getBullet().setType(BulletType.SYMBOL);
            first.getParagraphFormat().getBullet().setChar("\u2022");

            Paragraph second = new Paragraph();
            second.setText("Second");
            second.getParagraphFormat().getBullet().setType(BulletType.SYMBOL);
            second.getParagraphFormat().getBullet().setChar("\u2022");
            tf.getParagraphs().add(second);

            Paragraph third = new Paragraph();
            third.setText("Third");
            third.getParagraphFormat().getBullet().setType(BulletType.SYMBOL);
            third.getParagraphFormat().getBullet().setChar("\u2022");
            tf.getParagraphs().add(third);

            assertThat(tf.getParagraphs().size()).isEqualTo(3);
            prs.save(file.toString(), SaveFormat.PPTX);
        }

        try (Presentation reopened = new Presentation(file.toString())) {
            ITextFrame tf = null;
            ISlide slide = reopened.getSlides().get(0);
            for (int i = 0; i < slide.getShapes().size(); i++) {
                IShape s = slide.getShapes().get(i);
                if ("Bullets".equals(s.getName()) && s instanceof IAutoShape auto) {
                    tf = auto.getTextFrame();
                    break;
                }
            }
            assertThat(tf).isNotNull();
            assertThat(tf.getParagraphs().size()).isEqualTo(3);
            assertThat(tf.getParagraphs().get(0).getText()).isEqualTo("First");
            assertThat(tf.getParagraphs().get(1).getText()).isEqualTo("Second");
            assertThat(tf.getParagraphs().get(2).getText()).isEqualTo("Third");
            assertThat(tf.getParagraphs().get(1).getParagraphFormat().getBullet().getType())
                    .isEqualTo(BulletType.SYMBOL);
            assertThat(tf.getParagraphs().get(2).getParagraphFormat().getBullet().getType())
                    .isEqualTo(BulletType.SYMBOL);
        }
    }

    @Test
    void removeAt_removesParagraphFromSavedFile(@TempDir Path tmp) throws Exception {
        Path file = tmp.resolve("trim.pptx");

        try (Presentation prs = new Presentation()) {
            ISlide slide = prs.getSlides().get(0);
            IAutoShape shape = slide.getShapes().addAutoShape(ShapeType.RECTANGLE, 40, 40, 300, 120);
            shape.addTextFrame("A");
            ITextFrame tf = shape.getTextFrame();
            Paragraph b = new Paragraph();
            b.setText("B");
            Paragraph c = new Paragraph();
            c.setText("C");
            tf.getParagraphs().add(b);
            tf.getParagraphs().add(c);
            tf.getParagraphs().removeAt(1);
            assertThat(tf.getParagraphs().size()).isEqualTo(2);
            assertThat(tf.getText()).isEqualTo("A\nC");
            prs.save(file.toString(), SaveFormat.PPTX);
        }

        try (Presentation reopened = new Presentation(file.toString())) {
            ITextFrame tf = ((IAutoShape) reopened.getSlides().get(0).getShapes().get(0)).getTextFrame();
            assertThat(tf.getParagraphs().size()).isEqualTo(2);
            assertThat(tf.getParagraphs().get(0).getText()).isEqualTo("A");
            assertThat(tf.getParagraphs().get(1).getText()).isEqualTo("C");
        }
    }
}
