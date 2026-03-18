package org.aspose.slides.foss;

/**
 * Represents text wrapping shape.
 */
public enum TextShapeType {
    /** Not defined. */
    NOT_DEFINED("NotDefined"),
    /** None. */
    NONE("None"),
    /** Plain. */
    PLAIN("Plain"),
    /** Stop. */
    STOP("Stop"),
    /** Triangle. */
    TRIANGLE("Triangle"),
    /** Triangle Inverted. */
    TRIANGLE_INVERTED("TriangleInverted"),
    /** Chevron. */
    CHEVRON("Chevron"),
    /** Chevron Inverted. */
    CHEVRON_INVERTED("ChevronInverted"),
    /** Ring Inside. */
    RING_INSIDE("RingInside"),
    /** Ring Outside. */
    RING_OUTSIDE("RingOutside"),
    /** Arch Up. */
    ARCH_UP("ArchUp"),
    /** Arch Down. */
    ARCH_DOWN("ArchDown"),
    /** Circle. */
    CIRCLE("Circle"),
    /** Button. */
    BUTTON("Button"),
    /** Arch Up Pour. */
    ARCH_UP_POUR("ArchUpPour"),
    /** Arch Down Pour. */
    ARCH_DOWN_POUR("ArchDownPour"),
    /** Circle Pour. */
    CIRCLE_POUR("CirclePour"),
    /** Button Pour. */
    BUTTON_POUR("ButtonPour"),
    /** Curve Up. */
    CURVE_UP("CurveUp"),
    /** Curve Down. */
    CURVE_DOWN("CurveDown"),
    /** Can Up. */
    CAN_UP("CanUp"),
    /** Can Down. */
    CAN_DOWN("CanDown"),
    /** Wave 1. */
    WAVE1("Wave1"),
    /** Wave 2. */
    WAVE2("Wave2"),
    /** Double Wave 1. */
    DOUBLE_WAVE1("DoubleWave1"),
    /** Wave 4. */
    WAVE4("Wave4"),
    /** Inflate. */
    INFLATE("Inflate"),
    /** Deflate. */
    DEFLATE("Deflate"),
    /** Inflate Bottom. */
    INFLATE_BOTTOM("InflateBottom"),
    /** Deflate Bottom. */
    DEFLATE_BOTTOM("DeflateBottom"),
    /** Inflate Top. */
    INFLATE_TOP("InflateTop"),
    /** Deflate Top. */
    DEFLATE_TOP("DeflateTop"),
    /** Deflate Inflate. */
    DEFLATE_INFLATE("DeflateInflate"),
    /** Deflate Inflate Deflate. */
    DEFLATE_INFLATE_DEFLATE("DeflateInflateDeflate"),
    /** Fade Right. */
    FADE_RIGHT("FadeRight"),
    /** Fade Left. */
    FADE_LEFT("FadeLeft"),
    /** Fade Up. */
    FADE_UP("FadeUp"),
    /** Fade Down. */
    FADE_DOWN("FadeDown"),
    /** Slant Up. */
    SLANT_UP("SlantUp"),
    /** Slant Down. */
    SLANT_DOWN("SlantDown"),
    /** Cascade Up. */
    CASCADE_UP("CascadeUp"),
    /** Cascade Down. */
    CASCADE_DOWN("CascadeDown"),
    /** Custom. */
    CUSTOM("Custom");

    private final String value;

    TextShapeType(String value) {
        this.value = value;
    }

    /**
     * Returns the string value of this constant.
     *
     * @return the string value
     */
    public String getValue() {
        return value;
    }
}
