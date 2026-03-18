package org.aspose.slides.foss.internal.pptx;

import org.aspose.slides.foss.internal.opc.OpcPackage;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TemplateTest {

    @Test
    void getTemplatePath_returnsNonEmptyPath() throws FileNotFoundException {
        String path = Template.getTemplatePath();
        assertThat(path).isNotEmpty();
        assertThat(path).contains("Template.pptx");
    }

    @Test
    void loadTemplate_copiesPartsIntoTargetPackage() throws IOException {
        OpcPackage target = OpcPackage.createNew();

        Template.loadTemplate(target);

        // The minimal template contains at least [Content_Types].xml and _rels/.rels
        assertThat(target.getPartNames()).isNotEmpty();
        assertThat(target.hasPart("[Content_Types].xml")).isTrue();
        assertThat(target.hasPart("_rels/.rels")).isTrue();
    }

    @Test
    void loadTemplate_targetPackagePartsHaveContent() throws IOException {
        OpcPackage target = OpcPackage.createNew();

        Template.loadTemplate(target);

        for (String partName : target.getPartNames()) {
            assertThat(target.getPart(partName))
                    .as("Part '%s' should have content", partName)
                    .isPresent();
            assertThat(target.getPart(partName).get().length)
                    .as("Part '%s' should not be empty", partName)
                    .isGreaterThan(0);
        }
    }
}
