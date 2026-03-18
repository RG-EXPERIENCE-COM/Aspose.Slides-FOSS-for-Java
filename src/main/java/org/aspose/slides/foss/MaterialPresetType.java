package org.aspose.slides.foss;

/**
 * Constants which define material of shape.
 */
public enum MaterialPresetType {
    /** Not defined. */
    NOT_DEFINED("NotDefined"),
    /** Clear. */
    CLEAR("Clear"),
    /** Dark Edge. */
    DK_EDGE("DkEdge"),
    /** Flat. */
    FLAT("Flat"),
    /** Legacy Matte. */
    LEGACY_MATTE("LegacyMatte"),
    /** Legacy Metal. */
    LEGACY_METAL("LegacyMetal"),
    /** Legacy Plastic. */
    LEGACY_PLASTIC("LegacyPlastic"),
    /** Legacy Wireframe. */
    LEGACY_WIREFRAME("LegacyWireframe"),
    /** Matte. */
    MATTE("Matte"),
    /** Metal. */
    METAL("Metal"),
    /** Plastic. */
    PLASTIC("Plastic"),
    /** Powder. */
    POWDER("Powder"),
    /** Soft Edge. */
    SOFT_EDGE("SoftEdge"),
    /** Softmetal. */
    SOFTMETAL("Softmetal"),
    /** Translucent Powder. */
    TRANSLUCENT_POWDER("TranslucentPowder"),
    /** Warm Matte. */
    WARM_MATTE("WarmMatte");

    private final String value;

    MaterialPresetType(String value) {
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
