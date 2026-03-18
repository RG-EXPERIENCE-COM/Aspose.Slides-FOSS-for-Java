package org.aspose.slides.foss;

import org.aspose.slides.foss.export.SaveFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Method;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Broad contract test that verifies the public API of all 40 enums.
 * Each enum must expose the expected number of constants and every
 * constant must return a non-null, non-empty PascalCase (or all-caps) value
 * from {@code getValue()}.
 */
class EnumsContractTest {

    // ---- Parameterized constant-count verification ----

    static Stream<Arguments> enumConstantCounts() {
        return Stream.of(
            Arguments.of(BevelPresetType.class, 13),
            Arguments.of(BulletType.class, 5),
            Arguments.of(CameraPresetType.class, 63),
            Arguments.of(ColorType.class, 7),
            Arguments.of(FillBlendMode.class, 5),
            Arguments.of(FillType.class, 7),
            Arguments.of(FontAlignment.class, 6),
            Arguments.of(GradientDirection.class, 6),
            Arguments.of(GradientShape.class, 5),
            Arguments.of(LightingDirection.class, 9),
            Arguments.of(LightRigPresetType.class, 28),
            Arguments.of(LineAlignment.class, 3),
            Arguments.of(LineArrowheadLength.class, 4),
            Arguments.of(LineArrowheadStyle.class, 7),
            Arguments.of(LineArrowheadWidth.class, 4),
            Arguments.of(LineCapStyle.class, 4),
            Arguments.of(LineJoinStyle.class, 4),
            Arguments.of(LineStyle.class, 6),
            Arguments.of(MaterialPresetType.class, 16),
            Arguments.of(NullableBool.class, 3),
            Arguments.of(NumberedBulletStyle.class, 42),
            Arguments.of(PatternStyle.class, 56),
            Arguments.of(PictureFillMode.class, 2),
            Arguments.of(PresetColor.class, 141),
            Arguments.of(PresetShadowType.class, 20),
            Arguments.of(RectangleAlignment.class, 10),
            Arguments.of(SchemeColor.class, 18),
            Arguments.of(SlideLayoutType.class, 36),
            Arguments.of(SourceFormat.class, 3),
            Arguments.of(TableStylePreset.class, 76),
            Arguments.of(TextAlignment.class, 7),
            Arguments.of(TextAnchorType.class, 6),
            Arguments.of(TextAutofitType.class, 4),
            Arguments.of(TextCapType.class, 4),
            Arguments.of(TextShapeType.class, 43),
            Arguments.of(TextStrikethroughType.class, 4),
            Arguments.of(TextUnderlineType.class, 19),
            Arguments.of(TextVerticalType.class, 8),
            Arguments.of(TileFlip.class, 5)
        );
    }

    @ParameterizedTest(name = "{0} should have {1} constants")
    @MethodSource("enumConstantCounts")
    void enumHasExpectedConstantCount(Class<? extends Enum<?>> enumClass, int expectedCount) {
        assertThat(enumClass.getEnumConstants()).hasSize(expectedCount);
    }

    // ---- Every constant's getValue() returns non-null, non-empty string ----

    static Stream<Arguments> allEnumConstants() {
        return enumConstantCounts()
                .flatMap(args -> {
                    @SuppressWarnings("unchecked")
                    Class<? extends Enum<?>> enumClass = (Class<? extends Enum<?>>) args.get()[0];
                    return Stream.of(enumClass.getEnumConstants())
                            .map(c -> Arguments.of(enumClass.getSimpleName(), c));
                });
    }

    @ParameterizedTest(name = "{0}.{1} getValue() is non-null and non-empty")
    @MethodSource("allEnumConstants")
    void getValueReturnsNonNullNonEmptyString(String enumName, Enum<?> constant) throws Exception {
        Method getValue = constant.getClass().getMethod("getValue");
        String value = (String) getValue.invoke(constant);
        assertThat(value)
                .as("%s.%s.getValue()", enumName, constant.name())
                .isNotNull()
                .isNotEmpty();
    }

    // ---- Every constant's getValue() starts with uppercase or is all-uppercase ----

    @ParameterizedTest(name = "{0}.{1} getValue() starts with uppercase")
    @MethodSource("allEnumConstants")
    void getValueStartsWithUppercaseOrAllCaps(String enumName, Enum<?> constant) throws Exception {
        Method getValue = constant.getClass().getMethod("getValue");
        String value = (String) getValue.invoke(constant);
        assertThat(Character.isUpperCase(value.charAt(0)))
                .as("%s.%s.getValue() = \"%s\" should start with uppercase", enumName, constant.name(), value)
                .isTrue();
    }

    // ---- Specific key values ----

    @Test
    void fillTypeSolidValue() {
        assertThat(FillType.SOLID.getValue()).isEqualTo("Solid");
    }

    @Test
    void presetColorAliceBlueValue() {
        assertThat(PresetColor.ALICE_BLUE.getValue()).isEqualTo("AliceBlue");
    }

    @Test
    void colorTypeRgbValue() {
        assertThat(ColorType.RGB.getValue()).isEqualTo("RGB");
    }

    @Test
    void colorTypeHslValue() {
        assertThat(ColorType.HSL.getValue()).isEqualTo("HSL");
    }

    @Test
    void lineCapStyleRoundValue() {
        assertThat(LineCapStyle.ROUND.getValue()).isEqualTo("Round");
    }

    @Test
    void lineStyleSingleValue() {
        assertThat(LineStyle.SINGLE.getValue()).isEqualTo("Single");
    }

    @Test
    void nullableBoolHasThreeConstants() {
        assertThat(NullableBool.values()).hasSize(3);
    }

    // ---- SaveFormat (export package) ----

    @Test
    void saveFormatPptExists() {
        assertThat(SaveFormat.PPT.getValue()).isEqualTo("Ppt");
    }

    @Test
    void saveFormatPptxExists() {
        assertThat(SaveFormat.PPTX.getValue()).isEqualTo("Pptx");
    }

    @Test
    void saveFormatPdfExists() {
        assertThat(SaveFormat.PDF.getValue()).isEqualTo("Pdf");
    }

    @ParameterizedTest(name = "SaveFormat.{0} getValue() is non-null and non-empty")
    @EnumSource(SaveFormat.class)
    void saveFormatConstantsHaveNonNullValues(SaveFormat sf) {
        assertThat(sf.getValue())
                .as("SaveFormat.%s.getValue()", sf.name())
                .isNotNull()
                .isNotEmpty();
    }
}
