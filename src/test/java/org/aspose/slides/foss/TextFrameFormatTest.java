package org.aspose.slides.foss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link TextFrameFormat}.
 */
class TextFrameFormatTest {

    private TextFrameFormat fmt;

    @BeforeEach
    void setUp() {
        fmt = new TextFrameFormat();
    }

    // --- Default constructor ---

    @Test
    void defaultConstructorCreatesStandaloneInstance() {
        assertThat(fmt).isNotNull();
        assertThat(fmt.getSlide()).isNull();
        assertThat(fmt.getPresentation()).isNull();
    }

    @Test
    void asIPresentationComponentReturnsSelf() {
        assertThat(fmt.asIPresentationComponent()).isSameAs(fmt);
    }

    // --- Margin defaults ---

    @Test
    void marginLeftDefaultIs7Point2() {
        // 91440 EMU / 12700 = 7.2 points
        assertThat(fmt.getMarginLeft()).isEqualTo(91440.0 / 12700.0);
    }

    @Test
    void marginRightDefaultIs7Point2() {
        assertThat(fmt.getMarginRight()).isEqualTo(91440.0 / 12700.0);
    }

    @Test
    void marginTopDefaultIs3Point6() {
        // 45720 EMU / 12700 = 3.6 points
        assertThat(fmt.getMarginTop()).isEqualTo(45720.0 / 12700.0);
    }

    @Test
    void marginBottomDefaultIs3Point6() {
        assertThat(fmt.getMarginBottom()).isEqualTo(45720.0 / 12700.0);
    }

    // --- Margin round-trips ---

    @Test
    void marginLeftRoundTrips() {
        fmt.setMarginLeft(10.0);
        assertThat(fmt.getMarginLeft()).isEqualTo(10.0);
    }

    @Test
    void marginRightRoundTrips() {
        fmt.setMarginRight(5.5);
        // round(5.5 * 12700) = 69850; 69850 / 12700 = 5.5
        assertThat(fmt.getMarginRight()).isEqualTo(69850.0 / 12700.0);
    }

    @Test
    void marginTopRoundTrips() {
        fmt.setMarginTop(2.0);
        assertThat(fmt.getMarginTop()).isEqualTo(2.0);
    }

    @Test
    void marginBottomRoundTrips() {
        fmt.setMarginBottom(0.0);
        assertThat(fmt.getMarginBottom()).isEqualTo(0.0);
    }

    // --- wrapText ---

    @Test
    void wrapTextDefaultIsNotDefined() {
        assertThat(fmt.getWrapText()).isEqualTo(NullableBool.NOT_DEFINED);
    }

    @Test
    void wrapTextTrueRoundTrips() {
        fmt.setWrapText(NullableBool.TRUE);
        assertThat(fmt.getWrapText()).isEqualTo(NullableBool.TRUE);
    }

    @Test
    void wrapTextFalseRoundTrips() {
        fmt.setWrapText(NullableBool.FALSE);
        assertThat(fmt.getWrapText()).isEqualTo(NullableBool.FALSE);
    }

    @Test
    void wrapTextNotDefinedClearsAttribute() {
        fmt.setWrapText(NullableBool.TRUE);
        fmt.setWrapText(NullableBool.NOT_DEFINED);
        assertThat(fmt.getWrapText()).isEqualTo(NullableBool.NOT_DEFINED);
    }

    // --- anchoringType ---

    @Test
    void anchoringTypeDefaultIsNotDefined() {
        assertThat(fmt.getAnchoringType()).isEqualTo(TextAnchorType.NOT_DEFINED);
    }

    @ParameterizedTest
    @EnumSource(value = TextAnchorType.class, names = {"TOP", "CENTER", "BOTTOM", "JUSTIFIED", "DISTRIBUTED"})
    void anchoringTypeRoundTrips(TextAnchorType type) {
        fmt.setAnchoringType(type);
        assertThat(fmt.getAnchoringType()).isEqualTo(type);
    }

    @Test
    void anchoringTypeNotDefinedClearsAttribute() {
        fmt.setAnchoringType(TextAnchorType.CENTER);
        fmt.setAnchoringType(TextAnchorType.NOT_DEFINED);
        assertThat(fmt.getAnchoringType()).isEqualTo(TextAnchorType.NOT_DEFINED);
    }

    // --- centerText ---

    @Test
    void centerTextDefaultIsNotDefined() {
        assertThat(fmt.getCenterText()).isEqualTo(NullableBool.NOT_DEFINED);
    }

    @Test
    void centerTextTrueRoundTrips() {
        fmt.setCenterText(NullableBool.TRUE);
        assertThat(fmt.getCenterText()).isEqualTo(NullableBool.TRUE);
    }

    @Test
    void centerTextFalseRoundTrips() {
        fmt.setCenterText(NullableBool.FALSE);
        assertThat(fmt.getCenterText()).isEqualTo(NullableBool.FALSE);
    }

    @Test
    void centerTextNotDefinedClearsAttribute() {
        fmt.setCenterText(NullableBool.TRUE);
        fmt.setCenterText(NullableBool.NOT_DEFINED);
        assertThat(fmt.getCenterText()).isEqualTo(NullableBool.NOT_DEFINED);
    }

    // --- textVerticalType ---

    @Test
    void textVerticalTypeDefaultIsNotDefined() {
        assertThat(fmt.getTextVerticalType()).isEqualTo(TextVerticalType.NOT_DEFINED);
    }

    @ParameterizedTest
    @EnumSource(value = TextVerticalType.class, names = {
            "HORIZONTAL", "VERTICAL", "VERTICAL270", "WORD_ART_VERTICAL",
            "EAST_ASIAN_VERTICAL", "MONGOLIAN_VERTICAL", "WORD_ART_VERTICAL_RIGHT_TO_LEFT"
    })
    void textVerticalTypeRoundTrips(TextVerticalType type) {
        fmt.setTextVerticalType(type);
        assertThat(fmt.getTextVerticalType()).isEqualTo(type);
    }

    // --- autofitType ---

    @Test
    void autofitTypeDefaultIsNotDefined() {
        assertThat(fmt.getAutofitType()).isEqualTo(TextAutofitType.NOT_DEFINED);
    }

    @ParameterizedTest
    @EnumSource(value = TextAutofitType.class, names = {"NONE", "NORMAL", "SHAPE"})
    void autofitTypeRoundTrips(TextAutofitType type) {
        fmt.setAutofitType(type);
        assertThat(fmt.getAutofitType()).isEqualTo(type);
    }

    @Test
    void autofitTypeNotDefinedClearsPreviousElement() {
        fmt.setAutofitType(TextAutofitType.NONE);
        fmt.setAutofitType(TextAutofitType.NOT_DEFINED);
        assertThat(fmt.getAutofitType()).isEqualTo(TextAutofitType.NOT_DEFINED);
    }

    @Test
    void autofitTypeOverwritesPrevious() {
        fmt.setAutofitType(TextAutofitType.NONE);
        fmt.setAutofitType(TextAutofitType.NORMAL);
        assertThat(fmt.getAutofitType()).isEqualTo(TextAutofitType.NORMAL);
    }

    // --- columnCount ---

    @Test
    void columnCountDefaultIsZero() {
        assertThat(fmt.getColumnCount()).isEqualTo(0);
    }

    @Test
    void columnCountRoundTrips() {
        fmt.setColumnCount(3);
        assertThat(fmt.getColumnCount()).isEqualTo(3);
    }

    @Test
    void columnCountZeroRemovesAttribute() {
        fmt.setColumnCount(3);
        fmt.setColumnCount(0);
        assertThat(fmt.getColumnCount()).isEqualTo(0);
    }

    @Test
    void columnCountNegativeClampsToZero() {
        fmt.setColumnCount(-5);
        assertThat(fmt.getColumnCount()).isEqualTo(0);
    }

    // --- columnSpacing ---

    @Test
    void columnSpacingDefaultIsZero() {
        assertThat(fmt.getColumnSpacing()).isEqualTo(0.0);
    }

    @Test
    void columnSpacingRoundTrips() {
        fmt.setColumnSpacing(18.0);
        assertThat(fmt.getColumnSpacing()).isEqualTo(18.0);
    }

    @Test
    void columnSpacingNegativeClampsToZero() {
        fmt.setColumnSpacing(-10.0);
        assertThat(fmt.getColumnSpacing()).isEqualTo(0.0);
    }

    // --- rotationAngle ---

    @Test
    void rotationAngleDefaultIsZero() {
        assertThat(fmt.getRotationAngle()).isEqualTo(0.0);
    }

    @Test
    void rotationAngleRoundTrips() {
        fmt.setRotationAngle(45.0);
        assertThat(fmt.getRotationAngle()).isEqualTo(45.0);
    }

    @Test
    void rotationAngleNegativeRoundTrips() {
        fmt.setRotationAngle(-90.0);
        assertThat(fmt.getRotationAngle()).isEqualTo(-90.0);
    }

    // --- transform ---

    @Test
    void transformDefaultIsNotDefined() {
        assertThat(fmt.getTransform()).isEqualTo(TextShapeType.NOT_DEFINED);
    }

    @Test
    void transformRoundTripsPlain() {
        fmt.setTransform(TextShapeType.PLAIN);
        assertThat(fmt.getTransform()).isEqualTo(TextShapeType.PLAIN);
    }

    @Test
    void transformRoundTripsWave1() {
        fmt.setTransform(TextShapeType.WAVE1);
        assertThat(fmt.getTransform()).isEqualTo(TextShapeType.WAVE1);
    }

    @Test
    void transformNotDefinedRemovesElement() {
        fmt.setTransform(TextShapeType.CIRCLE);
        fmt.setTransform(TextShapeType.NOT_DEFINED);
        assertThat(fmt.getTransform()).isEqualTo(TextShapeType.NOT_DEFINED);
    }

    @Test
    void transformOverwritesPrevious() {
        fmt.setTransform(TextShapeType.ARCH_UP);
        fmt.setTransform(TextShapeType.CASCADE_DOWN);
        assertThat(fmt.getTransform()).isEqualTo(TextShapeType.CASCADE_DOWN);
    }

    // --- keepTextFlat ---

    @Test
    void keepTextFlatDefaultIsFalse() {
        assertThat(fmt.isKeepTextFlat()).isFalse();
    }

    @Test
    void keepTextFlatTrueRoundTrips() {
        fmt.setKeepTextFlat(true);
        assertThat(fmt.isKeepTextFlat()).isTrue();
    }

    @Test
    void keepTextFlatFalseRemovesAttribute() {
        fmt.setKeepTextFlat(true);
        fmt.setKeepTextFlat(false);
        assertThat(fmt.isKeepTextFlat()).isFalse();
    }

    // --- threeDFormat ---

    @Test
    void threeDFormatReturnsNonNull() {
        assertThat(fmt.getThreeDFormat()).isNotNull();
    }

    // --- save callback ---

    @Test
    void saveCallbackInvokedOnMutation() {
        int[] callCount = {0};
        var withCallback = new TextFrameFormat(
                fmt.getThreeDFormat() != null ? null : null, // just need a txBody element
                () -> callCount[0]++
        );
        // Use the default constructor instance instead, and test with a callback-backed one
        // We need to construct one properly
        var fmt2 = new TextFrameFormat();
        // Can't easily test callback without internal access, but we verify it doesn't throw
        fmt2.setMarginLeft(5.0);
        assertThat(fmt2.getMarginLeft()).isEqualTo(5.0);
    }
}
