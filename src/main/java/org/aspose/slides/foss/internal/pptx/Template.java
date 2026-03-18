package org.aspose.slides.foss.internal.pptx;

import org.aspose.slides.foss.internal.opc.OpcPackage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * PPTX template loading.
 *
 * <p>Loads the {@code Template.pptx} file for new presentations.
 * The template is resolved first as a classpath resource next to this class,
 * then as a file on disk (for development/testing scenarios).
 */
public final class Template {

    /**
     * Resource name of the template file (co-located with this class on the classpath).
     */
    public static final String TEMPLATE_RESOURCE = "Template.pptx";

    private Template() {
        // utility class
    }

    /**
     * Get a path description for the template resource (for error messages).
     *
     * <p>This checks that the template is available as a classpath resource.
     *
     * @return a description of the template location
     * @throws FileNotFoundException if the template resource cannot be found
     */
    public static String getTemplatePath() throws FileNotFoundException {
        try (InputStream probe = Template.class.getResourceAsStream(TEMPLATE_RESOURCE)) {
            if (probe == null) {
                throw new FileNotFoundException(
                        "Template.pptx not found as classpath resource relative to "
                                + Template.class.getName()
                                + ". Please ensure the template file exists.");
            }
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw new FileNotFoundException(
                    "Error probing template resource: " + e.getMessage());
        }
        return Template.class.getResource(TEMPLATE_RESOURCE).toString();
    }

    /**
     * Load the {@code Template.pptx} into the given package.
     *
     * <p>Opens the template and copies every part into {@code targetPackage},
     * replacing any existing parts with the same names.
     *
     * @param targetPackage the OPC package to populate with template contents
     * @throws FileNotFoundException if {@code Template.pptx} cannot be found
     * @throws IOException           if an I/O error occurs while reading the template
     */
    public static void loadTemplate(OpcPackage targetPackage) throws IOException {
        // Verify template exists
        getTemplatePath();

        try (InputStream in = Template.class.getResourceAsStream(TEMPLATE_RESOURCE)) {
            OpcPackage templatePackage = OpcPackage.open(in);
            try {
                for (String partName : templatePackage.getPartNames()) {
                    templatePackage.getPart(partName)
                            .ifPresent(content -> targetPackage.setPart(partName, content));
                }
            } finally {
                templatePackage.close();
            }
        }
    }
}
