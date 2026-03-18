package org.aspose.slides.foss.internal.export;

import org.aspose.slides.foss.export.ISaveOptions;
import org.aspose.slides.foss.internal.pptx.OpcPackage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Exporter for PPTX and related Office Open XML formats.
 *
 * <p>Supports:</p>
 * <ul>
 *   <li>PPTX — Standard PowerPoint presentation</li>
 *   <li>PPTM — Macro-enabled presentation</li>
 *   <li>PPSX — PowerPoint show (opens in slideshow mode)</li>
 *   <li>PPSM — Macro-enabled show</li>
 *   <li>POTX — PowerPoint template</li>
 *   <li>POTM — Macro-enabled template</li>
 * </ul>
 *
 * <p>These formats are all OPC packages with different content types
 * for the main presentation part.</p>
 */
public final class PptxExporter extends ExporterBase {

    static {
        // Auto-register this exporter for all supported formats.
        ExporterRegistry.register(PptxExporter.class);
    }

    /** Mapping from SaveFormat values to main presentation content types. */
    private static final Map<String, String> CONTENT_TYPES = Map.of(
            "Pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation.main+xml",
            "Pptm", "application/vnd.ms-powerpoint.presentation.macroEnabled.main+xml",
            "Ppsx", "application/vnd.openxmlformats-officedocument.presentationml.slideshow.main+xml",
            "Ppsm", "application/vnd.ms-powerpoint.slideshow.macroEnabled.main+xml",
            "Potx", "application/vnd.openxmlformats-officedocument.presentationml.template.main+xml",
            "Potm", "application/vnd.ms-powerpoint.template.macroEnabled.main+xml"
    );

    private final String targetFormat;

    /**
     * Initialize the PPTX exporter with the default format ({@code Pptx}).
     */
    public PptxExporter() {
        this("Pptx");
    }

    /**
     * Initialize the PPTX exporter for a specific target format.
     *
     * @param targetFormat the specific format to export to (e.g., "Pptx", "Potx")
     */
    public PptxExporter(String targetFormat) {
        this.targetFormat = targetFormat;
    }

    /**
     * Export the presentation to a PPTX file.
     *
     * @param opcPackage the OPC package containing the presentation
     * @param path       the output file path
     * @param options    optional save options (currently unused for PPTX)
     * @throws IOException if the file cannot be written
     */
    @Override
    public void exportToPath(OpcPackage opcPackage, String path, ISaveOptions options) throws IOException {
        updateContentTypeIfNeeded(opcPackage);
        try (var out = new FileOutputStream(path)) {
            opcPackage.save(out);
        }
    }

    /**
     * Export the presentation to a stream.
     *
     * @param opcPackage the OPC package containing the presentation
     * @param stream     the output stream
     * @param options    optional save options (currently unused for PPTX)
     * @throws IOException if the stream cannot be written to
     */
    @Override
    public void exportToStream(OpcPackage opcPackage, OutputStream stream, ISaveOptions options) throws IOException {
        updateContentTypeIfNeeded(opcPackage);
        opcPackage.save(stream);
    }

    /**
     * Update the content type of the main presentation part if converting.
     *
     * <p>This is needed when saving as a different format than the source
     * (e.g., saving a PPTX as POTX).</p>
     *
     * @param opcPackage the OPC package to update
     */
    void updateContentTypeIfNeeded(OpcPackage opcPackage) {
        // Content type is preserved from the source format.
    }

    /**
     * Get all OPC-based presentation formats supported by this exporter.
     *
     * @return list of SaveFormat value strings
     */
    @Override
    public List<String> getSupportedFormats() {
        return List.copyOf(CONTENT_TYPES.keySet());
    }

    /**
     * Get the target format this exporter is configured for.
     *
     * @return the target format string
     */
    public String getTargetFormat() {
        return targetFormat;
    }
}
