package org.aspose.slides.foss.internal.export;

import org.aspose.slides.foss.export.ISaveOptions;
import org.aspose.slides.foss.internal.pptx.OpcPackage;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Abstract base class for presentation format exporters.
 *
 * <p>Each exporter handles conversion from the internal presentation
 * representation to a specific output format (PPTX, PDF, HTML, etc.).</p>
 *
 * <p>Subclasses must implement:</p>
 * <ul>
 *   <li>{@link #exportToPath(OpcPackage, String, ISaveOptions)} — export to a file path</li>
 *   <li>{@link #exportToStream(OpcPackage, OutputStream, ISaveOptions)} — export to a binary stream</li>
 *   <li>{@link #getSupportedFormats()} — return list of SaveFormat value strings supported</li>
 * </ul>
 */
public abstract class ExporterBase {

    /**
     * Export the presentation to a file path.
     *
     * @param opcPackage the OPC package containing the presentation data
     * @param path       the output file path
     * @param options    optional export options specific to the format, may be {@code null}
     * @throws IOException       if the file cannot be written
     * @throws IllegalArgumentException if the options are invalid
     */
    public abstract void exportToPath(OpcPackage opcPackage, String path, ISaveOptions options) throws IOException;

    /**
     * Export the presentation to a binary stream.
     *
     * @param opcPackage the OPC package containing the presentation data
     * @param stream     the output stream with write capability
     * @param options    optional export options specific to the format, may be {@code null}
     * @throws IOException       if the stream cannot be written to
     * @throws IllegalArgumentException if the options are invalid
     */
    public abstract void exportToStream(OpcPackage opcPackage, OutputStream stream, ISaveOptions options) throws IOException;

    /**
     * Get the list of SaveFormat values this exporter supports.
     *
     * @return list of SaveFormat enum value strings (e.g., {@code ["Pptx", "Pptm"]})
     */
    public abstract List<String> getSupportedFormats();

    /**
     * Export to either a file path or stream.
     *
     * <p>Delegates to {@link #exportToPath} when {@code destination} is a {@link String},
     * or to {@link #exportToStream} when it is an {@link OutputStream}.</p>
     *
     * @param opcPackage  the OPC package containing the presentation data
     * @param destination file path ({@link String}) or binary stream ({@link OutputStream})
     * @param options     optional export options specific to the format, may be {@code null}
     * @throws IOException              if the output cannot be written
     * @throws IllegalArgumentException if {@code destination} is neither a String nor an OutputStream
     */
    public void export(OpcPackage opcPackage, Object destination, ISaveOptions options) throws IOException {
        if (destination instanceof String path) {
            exportToPath(opcPackage, path, options);
        } else if (destination instanceof OutputStream stream) {
            exportToStream(opcPackage, stream, options);
        } else {
            throw new IllegalArgumentException(
                    "destination must be a String (file path) or OutputStream, got: "
                            + (destination == null ? "null" : destination.getClass().getName()));
        }
    }
}
