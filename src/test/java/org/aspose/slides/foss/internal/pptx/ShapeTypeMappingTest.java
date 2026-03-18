package org.aspose.slides.foss.internal.pptx;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ShapeTypeMappingTest {

    @Test
    void ooxmlPrstToShapeTypeName_knownPreset() {
        assertThat(ShapeTypeMapping.ooxmlPrstToShapeTypeName("rect"))
                .isEqualTo(Optional.of("RECTANGLE"));
    }

    @Test
    void ooxmlPrstToShapeTypeName_anotherPreset() {
        assertThat(ShapeTypeMapping.ooxmlPrstToShapeTypeName("star5"))
                .isEqualTo(Optional.of("FIVE_POINTED_STAR"));
    }

    @Test
    void ooxmlPrstToShapeTypeName_unknownPreset() {
        assertThat(ShapeTypeMapping.ooxmlPrstToShapeTypeName("nonExistent"))
                .isEmpty();
    }

    @Test
    void shapeTypeNameToOoxmlPrst_knownName() {
        assertThat(ShapeTypeMapping.shapeTypeNameToOoxmlPrst("RECTANGLE"))
                .isEqualTo(Optional.of("rect"));
    }

    @Test
    void shapeTypeNameToOoxmlPrst_anotherName() {
        assertThat(ShapeTypeMapping.shapeTypeNameToOoxmlPrst("FIVE_POINTED_STAR"))
                .isEqualTo(Optional.of("star5"));
    }

    @Test
    void shapeTypeNameToOoxmlPrst_unknownName() {
        assertThat(ShapeTypeMapping.shapeTypeNameToOoxmlPrst("BOGUS"))
                .isEmpty();
    }

    @Test
    void shapeTypeNameToOoxmlPrst_notDefinedHasNoOoxml() {
        assertThat(ShapeTypeMapping.shapeTypeNameToOoxmlPrst("NOT_DEFINED"))
                .isEmpty();
    }

    @Test
    void roundTrip() {
        String prst = "flowChartDecision";
        Optional<String> name = ShapeTypeMapping.ooxmlPrstToShapeTypeName(prst);
        assertThat(name).isPresent();
        assertThat(ShapeTypeMapping.shapeTypeNameToOoxmlPrst(name.get()))
                .isEqualTo(Optional.of(prst));
    }

    @Test
    void legacyAlias_roundRectangle() {
        // ROUND_RECTANGLE is a legacy alias that maps to the same OOXML as ROUND_CORNER_RECTANGLE
        assertThat(ShapeTypeMapping.shapeTypeNameToOoxmlPrst("ROUND_RECTANGLE"))
                .isEqualTo(Optional.of("roundRect"));
    }
}
