package org.aspose.slides.foss.internal.pptx;

import org.aspose.slides.foss.ShapeType;

import java.util.Optional;

/**
 * Mapping between OOXML preset geometry names ({@code ST_ShapeType}) and
 * {@link ShapeType} enum member names (as strings).
 *
 * <p>This class delegates to the bidirectional lookup already maintained by
 * {@link ShapeType} but exposes a pure string-based API for callers that work
 * with enum names rather than enum instances.</p>
 */
public final class ShapeTypeMapping {

    private ShapeTypeMapping() {
        // utility class
    }

    /**
     * Converts an OOXML {@code prstGeom} {@code prst} attribute value to the
     * corresponding {@link ShapeType} enum member name.
     *
     * @param prst the OOXML preset name (e.g. {@code "rect"}, {@code "star5"})
     * @return the matching {@link ShapeType} enum constant name, or empty if
     *         the preset name is not recognized
     */
    public static Optional<String> ooxmlPrstToShapeTypeName(String prst) {
        return ShapeType.fromOoxml(prst).map(Enum::name);
    }

    /**
     * Converts a {@link ShapeType} enum member name to its OOXML
     * {@code prstGeom} {@code prst} attribute value.
     *
     * @param name the {@link ShapeType} enum constant name
     *             (e.g. {@code "RECTANGLE"}, {@code "FIVE_POINTED_STAR"})
     * @return the OOXML preset name, or empty if the name does not correspond
     *         to a known {@link ShapeType} or the type has no OOXML mapping
     */
    public static Optional<String> shapeTypeNameToOoxmlPrst(String name) {
        try {
            return ShapeType.valueOf(name).toOoxml();
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
