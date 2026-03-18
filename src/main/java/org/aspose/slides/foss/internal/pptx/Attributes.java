package org.aspose.slides.foss.internal.pptx;

/**
 * Common PPTX attribute names.
 *
 * <p>Includes both namespace-qualified attributes (e.g. {@code r:id})
 * and plain attribute names used throughout PPTX documents.</p>
 */
public final class Attributes {

    private Attributes() {
        // utility class
    }

    // ── Namespace-qualified attributes ─────────────────────────────────

    /** {@code r:id} — relationship ID attribute. */
    public static final String R_ID = Ns.R + "id";

    /** {@code r:embed} — embedded relationship attribute. */
    public static final String R_EMBED = Ns.R + "embed";

    // ── Plain attributes ───────────────────────────────────────────────

    /** {@code id} attribute. */
    public static final String ID = "id";

    /** {@code name} attribute. */
    public static final String NAME = "name";

    /** {@code cx} attribute (width in EMUs). */
    public static final String CX = "cx";

    /** {@code cy} attribute (height in EMUs). */
    public static final String CY = "cy";

    /** {@code x} attribute (horizontal offset in EMUs). */
    public static final String X = "x";

    /** {@code y} attribute (vertical offset in EMUs). */
    public static final String Y = "y";
}
