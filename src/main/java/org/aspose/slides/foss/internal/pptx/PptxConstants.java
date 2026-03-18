package org.aspose.slides.foss.internal.pptx;

import java.util.Map;

/**
 * Module-level constants for PPTX format.
 *
 * <p>Defines XML namespace mappings and unit conversion factors
 * used throughout PowerPoint Open XML processing.</p>
 */
public final class PptxConstants {

    private PptxConstants() {
        // utility class
    }

    /**
     * Namespace prefix-to-URI map for all XML namespaces used in PPTX documents.
     */
    public static final Map<String, String> NAMESPACES = Map.ofEntries(
            // PresentationML namespace (main presentation elements)
            Map.entry("p", "http://schemas.openxmlformats.org/presentationml/2006/main"),

            // DrawingML namespace (shapes, text, effects)
            Map.entry("a", "http://schemas.openxmlformats.org/drawingml/2006/main"),

            // Relationships namespace (in XML content, e.g., r:id attributes)
            Map.entry("r", "http://schemas.openxmlformats.org/officeDocument/2006/relationships"),

            // Package relationships namespace (in .rels files)
            Map.entry("pr", "http://schemas.openxmlformats.org/package/2006/relationships"),

            // Content types namespace
            Map.entry("ct", "http://schemas.openxmlformats.org/package/2006/content-types"),

            // Core properties namespace (Dublin Core)
            Map.entry("cp", "http://schemas.openxmlformats.org/package/2006/metadata/core-properties"),
            Map.entry("dc", "http://purl.org/dc/elements/1.1/"),
            Map.entry("dcterms", "http://purl.org/dc/terms/"),

            // Extended properties namespace
            Map.entry("ep", "http://schemas.openxmlformats.org/officeDocument/2006/extended-properties"),

            // VML namespace (legacy vector markup)
            Map.entry("v", "urn:schemas-microsoft-com:vml"),

            // Office namespace
            Map.entry("o", "urn:schemas-microsoft-com:office:office"),

            // Chart namespace
            Map.entry("c", "http://schemas.openxmlformats.org/drawingml/2006/chart"),

            // Diagram namespace
            Map.entry("dgm", "http://schemas.openxmlformats.org/drawingml/2006/diagram"),

            // Picture namespace
            Map.entry("pic", "http://schemas.openxmlformats.org/drawingml/2006/picture"),

            // Math namespace
            Map.entry("m", "http://schemas.openxmlformats.org/officeDocument/2006/math"),

            // Microsoft Office extensions (2010+)
            Map.entry("p14", "http://schemas.microsoft.com/office/powerpoint/2010/main"),
            Map.entry("p15", "http://schemas.microsoft.com/office/powerpoint/2012/main"),
            Map.entry("a14", "http://schemas.microsoft.com/office/drawing/2010/main"),

            // Markup Compatibility namespace
            Map.entry("mc", "http://schemas.openxmlformats.org/markup-compatibility/2006")
    );

    /** 1 point = 12,700 EMUs (914,400 EMU/inch / 72 points/inch). */
    public static final int EMU_PER_POINT = 12700;

    /** OOXML stores rotation in 60,000ths of a degree. */
    public static final int ROTATION_UNIT = 60000;
}
