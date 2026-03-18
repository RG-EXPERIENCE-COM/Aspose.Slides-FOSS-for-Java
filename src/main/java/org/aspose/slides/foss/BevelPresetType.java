package org.aspose.slides.foss;

/**
 * Constants which define 3D bevel of shape.
 */
public enum BevelPresetType {
    /** Not defined. */
    NOT_DEFINED("NotDefined"),
    /** Angle. */
    ANGLE("Angle"),
    /** Art Deco. */
    ART_DECO("ArtDeco"),
    /** Circle. */
    CIRCLE("Circle"),
    /** Convex. */
    CONVEX("Convex"),
    /** Cool Slant. */
    COOL_SLANT("CoolSlant"),
    /** Cross. */
    CROSS("Cross"),
    /** Divot. */
    DIVOT("Divot"),
    /** Hard Edge. */
    HARD_EDGE("HardEdge"),
    /** Relaxed Inset. */
    RELAXED_INSET("RelaxedInset"),
    /** Riblet. */
    RIBLET("Riblet"),
    /** Slope. */
    SLOPE("Slope"),
    /** Soft Round. */
    SOFT_ROUND("SoftRound");

    private final String value;

    BevelPresetType(String value) {
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
