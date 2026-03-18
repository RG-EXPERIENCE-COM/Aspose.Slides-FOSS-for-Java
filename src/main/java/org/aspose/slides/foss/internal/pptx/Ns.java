package org.aspose.slides.foss.internal.pptx;

/**
 * Namespace helper class providing formatted namespace strings.
 *
 * <p>Use the constants (e.g. {@link #P}, {@link #A}) for
 * {@code {http://...}tagname}-style lookups in XML elements.</p>
 */
public final class Ns {

    private Ns() {
        // utility class
    }

    /** PresentationML formatted namespace. */
    public static final String P = "{" + PptxConstants.NAMESPACES.get("p") + "}";

    /** DrawingML formatted namespace. */
    public static final String A = "{" + PptxConstants.NAMESPACES.get("a") + "}";

    /** Document relationships formatted namespace. */
    public static final String R = "{" + PptxConstants.NAMESPACES.get("r") + "}";

    /** Package relationships formatted namespace. */
    public static final String PR = "{" + PptxConstants.NAMESPACES.get("pr") + "}";

    /** Content types formatted namespace. */
    public static final String CT = "{" + PptxConstants.NAMESPACES.get("ct") + "}";

    /** Charts formatted namespace. */
    public static final String C = "{" + PptxConstants.NAMESPACES.get("c") + "}";

    /** Pictures formatted namespace. */
    public static final String PIC = "{" + PptxConstants.NAMESPACES.get("pic") + "}";

    /**
     * Returns the formatted namespace string for the given prefix.
     *
     * @param prefix namespace prefix (e.g. {@code "p"}, {@code "a"}, {@code "r"})
     * @return formatted namespace string like {@code "{http://...}"}
     * @throws IllegalArgumentException if the prefix is not found
     */
    public static String get(String prefix) {
        String uri = PptxConstants.NAMESPACES.get(prefix);
        if (uri == null) {
            throw new IllegalArgumentException("Unknown namespace prefix: " + prefix);
        }
        return "{" + uri + "}";
    }
}
