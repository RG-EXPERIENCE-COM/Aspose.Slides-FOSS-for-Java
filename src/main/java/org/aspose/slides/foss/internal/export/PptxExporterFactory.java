package org.aspose.slides.foss.internal.export;

/**
 * Factory for creating PPTX exporters with specific target formats.
 *
 * <p>This allows creating format-specific exporter instances rather than
 * relying on the default no-arg constructor used by the registry.</p>
 */
public final class PptxExporterFactory {

    private PptxExporterFactory() {
        // utility class
    }

    /**
     * Create a PPTX exporter for a specific format.
     *
     * @param formatValue the target format (e.g., "Pptx", "Potx", "Ppsx")
     * @return a new {@link PptxExporter} configured for the given format
     */
    public static PptxExporter createForFormat(String formatValue) {
        return new PptxExporter(formatValue);
    }
}
