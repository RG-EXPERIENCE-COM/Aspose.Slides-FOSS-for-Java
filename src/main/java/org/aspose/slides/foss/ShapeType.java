package org.aspose.slides.foss;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Represents preset geometry of geometry shapes.
 */
public enum ShapeType {

    /** Not defined. */
    NOT_DEFINED,
    /** Custom shape. This is a return-only value. */
    CUSTOM,
    /** Line Shape. */
    LINE,
    /** Line Inverse Shape. */
    LINE_INVERSE,
    /** Triangle Shape. */
    TRIANGLE,
    /** Right Triangle Shape. */
    RIGHT_TRIANGLE,
    /** Rectangle Shape. */
    RECTANGLE,
    /** Diamond Shape. */
    DIAMOND,
    /** Parallelogram Shape. */
    PARALLELOGRAM,
    /** Trapezoid Shape. */
    TRAPEZOID,
    /** Non-Isosceles Trapezoid Shape. */
    NON_ISOSCELES_TRAPEZOID,
    /** Pentagon Shape. */
    PENTAGON,
    /** Hexagon Shape. */
    HEXAGON,
    /** Heptagon Shape. */
    HEPTAGON,
    /** Octagon Shape. */
    OCTAGON,
    /** Decagon Shape. */
    DECAGON,
    /** Dodecagon Shape. */
    DODECAGON,
    /** Four Pointed Star Shape. */
    FOUR_POINTED_STAR,
    /** Five Pointed Star Shape. */
    FIVE_POINTED_STAR,
    /** Six Pointed Star Shape. */
    SIX_POINTED_STAR,
    /** Seven Pointed Star Shape. */
    SEVEN_POINTED_STAR,
    /** Eight Pointed Star Shape. */
    EIGHT_POINTED_STAR,
    /** Ten Pointed Star Shape. */
    TEN_POINTED_STAR,
    /** Twelve Pointed Star Shape. */
    TWELVE_POINTED_STAR,
    /** Sixteen Pointed Star Shape. */
    SIXTEEN_POINTED_STAR,
    /** Twenty Four Pointed Star Shape. */
    TWENTY_FOUR_POINTED_STAR,
    /** Thirty Two Pointed Star Shape. */
    THIRTY_TWO_POINTED_STAR,
    /** Round Corner Rectangle Shape. */
    ROUND_CORNER_RECTANGLE,
    /** One Round Corner Rectangle Shape. */
    ONE_ROUND_CORNER_RECTANGLE,
    /** Two Same-side Round Corner Rectangle Shape. */
    TWO_SAMESIDE_ROUND_CORNER_RECTANGLE,
    /** Two Diagonal Round Corner Rectangle Shape. */
    TWO_DIAGONAL_ROUND_CORNER_RECTANGLE,
    /** One Snip One Round Corner Rectangle Shape. */
    ONE_SNIP_ONE_ROUND_CORNER_RECTANGLE,
    /** One Snip Corner Rectangle Shape. */
    ONE_SNIP_CORNER_RECTANGLE,
    /** Two Same-side Snip Corner Rectangle Shape. */
    TWO_SAMESIDE_SNIP_CORNER_RECTANGLE,
    /** Two Diagonal Snip Corner Rectangle Shape. */
    TWO_DIAGONAL_SNIP_CORNER_RECTANGLE,
    /** Plaque Shape. */
    PLAQUE,
    /** Ellipse Shape. */
    ELLIPSE,
    /** Teardrop Shape. */
    TEARDROP,
    /** Home Plate Shape. */
    HOME_PLATE,
    /** Chevron Shape. */
    CHEVRON,
    /** Pie Wedge Shape. */
    PIE_WEDGE,
    /** Pie Shape. */
    PIE,
    /** Block Arc Shape. */
    BLOCK_ARC,
    /** Donut Shape. */
    DONUT,
    /** No Smoking Shape. */
    NO_SMOKING,
    /** Right Arrow Shape. */
    RIGHT_ARROW,
    /** Left Arrow Shape. */
    LEFT_ARROW,
    /** Up Arrow Shape. */
    UP_ARROW,
    /** Down Arrow Shape. */
    DOWN_ARROW,
    /** Striped Right Arrow Shape. */
    STRIPED_RIGHT_ARROW,
    /** Notched Right Arrow Shape. */
    NOTCHED_RIGHT_ARROW,
    /** Bent Up Arrow Shape. */
    BENT_UP_ARROW,
    /** Left Right Arrow Shape. */
    LEFT_RIGHT_ARROW,
    /** Up Down Arrow Shape. */
    UP_DOWN_ARROW,
    /** Left Up Arrow Shape. */
    LEFT_UP_ARROW,
    /** Left Right Up Arrow Shape. */
    LEFT_RIGHT_UP_ARROW,
    /** Quad-Arrow Shape. */
    QUAD_ARROW,
    /** Callout Left Arrow Shape. */
    CALLOUT_LEFT_ARROW,
    /** Callout Right Arrow Shape. */
    CALLOUT_RIGHT_ARROW,
    /** Callout Up Arrow Shape. */
    CALLOUT_UP_ARROW,
    /** Callout Down Arrow Shape. */
    CALLOUT_DOWN_ARROW,
    /** Callout Left Right Arrow Shape. */
    CALLOUT_LEFT_RIGHT_ARROW,
    /** Callout Up Down Arrow Shape. */
    CALLOUT_UP_DOWN_ARROW,
    /** Callout Quad-Arrow Shape. */
    CALLOUT_QUAD_ARROW,
    /** Bent Arrow Shape. */
    BENT_ARROW,
    /** U-Turn Arrow Shape. */
    U_TURN_ARROW,
    /** Circular Arrow Shape. */
    CIRCULAR_ARROW,
    /** Left Circular Arrow Shape. */
    LEFT_CIRCULAR_ARROW,
    /** Left Right Circular Arrow Shape. */
    LEFT_RIGHT_CIRCULAR_ARROW,
    /** Curved Right Arrow Shape. */
    CURVED_RIGHT_ARROW,
    /** Curved Left Arrow Shape. */
    CURVED_LEFT_ARROW,
    /** Curved Up Arrow Shape. */
    CURVED_UP_ARROW,
    /** Curved Down Arrow Shape. */
    CURVED_DOWN_ARROW,
    /** Swoosh Arrow Shape. */
    SWOOSH_ARROW,
    /** Cube Shape. */
    CUBE,
    /** Can Shape. */
    CAN,
    /** Lightning Bolt Shape. */
    LIGHTNING_BOLT,
    /** Heart Shape. */
    HEART,
    /** Sun Shape. */
    SUN,
    /** Moon Shape. */
    MOON,
    /** Smiley Face Shape. */
    SMILEY_FACE,
    /** Irregular Seal 1 Shape. */
    IRREGULAR_SEAL1,
    /** Irregular Seal 2 Shape. */
    IRREGULAR_SEAL2,
    /** Folded Corner Shape. */
    FOLDED_CORNER,
    /** Bevel Shape. */
    BEVEL,
    /** Frame Shape. */
    FRAME,
    /** Half Frame Shape. */
    HALF_FRAME,
    /** Corner Shape. */
    CORNER,
    /** Diagonal Stripe Shape. */
    DIAGONAL_STRIPE,
    /** Chord Shape. */
    CHORD,
    /** Curved Arc Shape. */
    CURVED_ARC,
    /** Left Bracket Shape. */
    LEFT_BRACKET,
    /** Right Bracket Shape. */
    RIGHT_BRACKET,
    /** Left Brace Shape. */
    LEFT_BRACE,
    /** Right Brace Shape. */
    RIGHT_BRACE,
    /** Bracket Pair Shape. */
    BRACKET_PAIR,
    /** Brace Pair Shape. */
    BRACE_PAIR,
    /** Straight Connector 1 Shape. */
    STRAIGHT_CONNECTOR1,
    /** Bent Connector 2 Shape. */
    BENT_CONNECTOR2,
    /** Bent Connector 3 Shape. */
    BENT_CONNECTOR3,
    /** Bent Connector 4 Shape. */
    BENT_CONNECTOR4,
    /** Bent Connector 5 Shape. */
    BENT_CONNECTOR5,
    /** Curved Connector 2 Shape. */
    CURVED_CONNECTOR2,
    /** Curved Connector 3 Shape. */
    CURVED_CONNECTOR3,
    /** Curved Connector 4 Shape. */
    CURVED_CONNECTOR4,
    /** Curved Connector 5 Shape. */
    CURVED_CONNECTOR5,
    /** Callout 1 Shape. */
    CALLOUT1,
    /** Callout 2 Shape. */
    CALLOUT2,
    /** Callout 3 Shape. */
    CALLOUT3,
    /** Callout 1 with Accent Shape. */
    CALLOUT_1_WITH_ACCENT,
    /** Callout 2 with Accent Shape. */
    CALLOUT_2_WITH_ACCENT,
    /** Callout 3 with Accent Shape. */
    CALLOUT_3_WITH_ACCENT,
    /** Callout 1 with Border Shape. */
    CALLOUT_1_WITH_BORDER,
    /** Callout 2 with Border Shape. */
    CALLOUT_2_WITH_BORDER,
    /** Callout 3 with Border Shape. */
    CALLOUT_3_WITH_BORDER,
    /** Callout 1 with Border and Accent Shape. */
    CALLOUT_1_WITH_BORDER_AND_ACCENT,
    /** Callout 2 with Border and Accent Shape. */
    CALLOUT_2_WITH_BORDER_AND_ACCENT,
    /** Callout 3 with Border and Accent Shape. */
    CALLOUT_3_WITH_BORDER_AND_ACCENT,
    /** Callout Wedge Rectangle Shape. */
    CALLOUT_WEDGE_RECTANGLE,
    /** Callout Wedge Round Rectangle Shape. */
    CALLOUT_WEDGE_ROUND_RECTANGLE,
    /** Callout Wedge Ellipse Shape. */
    CALLOUT_WEDGE_ELLIPSE,
    /** Callout Cloud Shape. */
    CALLOUT_CLOUD,
    /** Cloud Shape. */
    CLOUD,
    /** Ribbon Shape. */
    RIBBON,
    /** Ribbon 2 Shape. */
    RIBBON2,
    /** Ellipse Ribbon Shape. */
    ELLIPSE_RIBBON,
    /** Ellipse Ribbon 2 Shape. */
    ELLIPSE_RIBBON2,
    /** Left Right Ribbon Shape. */
    LEFT_RIGHT_RIBBON,
    /** Vertical Scroll Shape. */
    VERTICAL_SCROLL,
    /** Horizontal Scroll Shape. */
    HORIZONTAL_SCROLL,
    /** Wave Shape. */
    WAVE,
    /** Double Wave Shape. */
    DOUBLE_WAVE,
    /** Plus Shape. */
    PLUS,
    /** Process Flow Shape. */
    PROCESS_FLOW,
    /** Decision Flow Shape. */
    DECISION_FLOW,
    /** Input Output Flow Shape. */
    INPUT_OUTPUT_FLOW,
    /** Predefined Process Flow Shape. */
    PREDEFINED_PROCESS_FLOW,
    /** Internal Storage Flow Shape. */
    INTERNAL_STORAGE_FLOW,
    /** Document Flow Shape. */
    DOCUMENT_FLOW,
    /** Multi-Document Flow Shape. */
    MULTI_DOCUMENT_FLOW,
    /** Terminator Flow Shape. */
    TERMINATOR_FLOW,
    /** Preparation Flow Shape. */
    PREPARATION_FLOW,
    /** Manual Input Flow Shape. */
    MANUAL_INPUT_FLOW,
    /** Manual Operation Flow Shape. */
    MANUAL_OPERATION_FLOW,
    /** Connector Flow Shape. */
    CONNECTOR_FLOW,
    /** Punched Card Flow Shape. */
    PUNCHED_CARD_FLOW,
    /** Punched Tape Flow Shape. */
    PUNCHED_TAPE_FLOW,
    /** Summing Junction Flow Shape. */
    SUMMING_JUNCTION_FLOW,
    /** Or Flow Shape. */
    OR_FLOW,
    /** Collate Flow Shape. */
    COLLATE_FLOW,
    /** Sort Flow Shape. */
    SORT_FLOW,
    /** Extract Flow Shape. */
    EXTRACT_FLOW,
    /** Merge Flow Shape. */
    MERGE_FLOW,
    /** Offline Storage Flow Shape. */
    OFFLINE_STORAGE_FLOW,
    /** Online Storage Flow Shape. */
    ONLINE_STORAGE_FLOW,
    /** Magnetic Tape Flow Shape. */
    MAGNETIC_TAPE_FLOW,
    /** Magnetic Disk Flow Shape. */
    MAGNETIC_DISK_FLOW,
    /** Magnetic Drum Flow Shape. */
    MAGNETIC_DRUM_FLOW,
    /** Display Flow Shape. */
    DISPLAY_FLOW,
    /** Delay Flow Shape. */
    DELAY_FLOW,
    /** Alternate Process Flow Shape. */
    ALTERNATE_PROCESS_FLOW,
    /** Off-Page Connector Flow Shape. */
    OFF_PAGE_CONNECTOR_FLOW,
    /** Blank Button Shape. */
    BLANK_BUTTON,
    /** Home Button Shape. */
    HOME_BUTTON,
    /** Help Button Shape. */
    HELP_BUTTON,
    /** Information Button Shape. */
    INFORMATION_BUTTON,
    /** Forward or Next Button Shape. */
    FORWARD_OR_NEXT_BUTTON,
    /** Back or Previous Button Shape. */
    BACK_OR_PREVIOUS_BUTTON,
    /** End Button Shape. */
    END_BUTTON,
    /** Beginning Button Shape. */
    BEGINNING_BUTTON,
    /** Return Button Shape. */
    RETURN_BUTTON,
    /** Document Button Shape. */
    DOCUMENT_BUTTON,
    /** Sound Button Shape. */
    SOUND_BUTTON,
    /** Movie Button Shape. */
    MOVIE_BUTTON,
    /** Gear 6 Shape. */
    GEAR6,
    /** Gear 9 Shape. */
    GEAR9,
    /** Funnel Shape. */
    FUNNEL,
    /** Plus Math Shape. */
    PLUS_MATH,
    /** Minus Math Shape. */
    MINUS_MATH,
    /** Multiply Math Shape. */
    MULTIPLY_MATH,
    /** Divide Math Shape. */
    DIVIDE_MATH,
    /** Equal Math Shape. */
    EQUAL_MATH,
    /** Not Equal Math Shape. */
    NOT_EQUAL_MATH,
    /** Corner Tabs Shape. */
    CORNER_TABS,
    /** Square Tabs Shape. */
    SQUARE_TABS,
    /** Plaque Tabs Shape. */
    PLAQUE_TABS,
    /** Chart X Shape. */
    CHART_X,
    /** Chart Star Shape. */
    CHART_STAR,
    /** Chart Plus Shape. */
    CHART_PLUS,

    /**
     * Rounded rectangle (legacy alias).
     * @see #ROUND_CORNER_RECTANGLE
     */
    ROUND_RECTANGLE;

    private static final Map<String, ShapeType> FROM_OOXML;
    private static final Map<ShapeType, String> TO_OOXML;

    static {
        var from = new HashMap<String, ShapeType>();
        var to = new EnumMap<ShapeType, String>(ShapeType.class);

        register(from, to, "line", LINE);
        register(from, to, "lineInv", LINE_INVERSE);
        register(from, to, "triangle", TRIANGLE);
        register(from, to, "rtTriangle", RIGHT_TRIANGLE);
        register(from, to, "rect", RECTANGLE);
        register(from, to, "diamond", DIAMOND);
        register(from, to, "parallelogram", PARALLELOGRAM);
        register(from, to, "trapezoid", TRAPEZOID);
        register(from, to, "nonIsoscelesTrapezoid", NON_ISOSCELES_TRAPEZOID);
        register(from, to, "pentagon", PENTAGON);
        register(from, to, "hexagon", HEXAGON);
        register(from, to, "heptagon", HEPTAGON);
        register(from, to, "octagon", OCTAGON);
        register(from, to, "decagon", DECAGON);
        register(from, to, "dodecagon", DODECAGON);
        register(from, to, "star4", FOUR_POINTED_STAR);
        register(from, to, "star5", FIVE_POINTED_STAR);
        register(from, to, "star6", SIX_POINTED_STAR);
        register(from, to, "star7", SEVEN_POINTED_STAR);
        register(from, to, "star8", EIGHT_POINTED_STAR);
        register(from, to, "star10", TEN_POINTED_STAR);
        register(from, to, "star12", TWELVE_POINTED_STAR);
        register(from, to, "star16", SIXTEEN_POINTED_STAR);
        register(from, to, "star24", TWENTY_FOUR_POINTED_STAR);
        register(from, to, "star32", THIRTY_TWO_POINTED_STAR);
        register(from, to, "roundRect", ROUND_CORNER_RECTANGLE);
        register(from, to, "round1Rect", ONE_ROUND_CORNER_RECTANGLE);
        register(from, to, "round2SameRect", TWO_SAMESIDE_ROUND_CORNER_RECTANGLE);
        register(from, to, "round2DiagRect", TWO_DIAGONAL_ROUND_CORNER_RECTANGLE);
        register(from, to, "snipRoundRect", ONE_SNIP_ONE_ROUND_CORNER_RECTANGLE);
        register(from, to, "snip1Rect", ONE_SNIP_CORNER_RECTANGLE);
        register(from, to, "snip2SameRect", TWO_SAMESIDE_SNIP_CORNER_RECTANGLE);
        register(from, to, "snip2DiagRect", TWO_DIAGONAL_SNIP_CORNER_RECTANGLE);
        register(from, to, "plaque", PLAQUE);
        register(from, to, "ellipse", ELLIPSE);
        register(from, to, "teardrop", TEARDROP);
        register(from, to, "homePlate", HOME_PLATE);
        register(from, to, "chevron", CHEVRON);
        register(from, to, "pieWedge", PIE_WEDGE);
        register(from, to, "pie", PIE);
        register(from, to, "blockArc", BLOCK_ARC);
        register(from, to, "donut", DONUT);
        register(from, to, "noSmoking", NO_SMOKING);
        register(from, to, "rightArrow", RIGHT_ARROW);
        register(from, to, "leftArrow", LEFT_ARROW);
        register(from, to, "upArrow", UP_ARROW);
        register(from, to, "downArrow", DOWN_ARROW);
        register(from, to, "stripedRightArrow", STRIPED_RIGHT_ARROW);
        register(from, to, "notchedRightArrow", NOTCHED_RIGHT_ARROW);
        register(from, to, "bentUpArrow", BENT_UP_ARROW);
        register(from, to, "leftRightArrow", LEFT_RIGHT_ARROW);
        register(from, to, "upDownArrow", UP_DOWN_ARROW);
        register(from, to, "leftUpArrow", LEFT_UP_ARROW);
        register(from, to, "leftRightUpArrow", LEFT_RIGHT_UP_ARROW);
        register(from, to, "quadArrow", QUAD_ARROW);
        register(from, to, "leftArrowCallout", CALLOUT_LEFT_ARROW);
        register(from, to, "rightArrowCallout", CALLOUT_RIGHT_ARROW);
        register(from, to, "upArrowCallout", CALLOUT_UP_ARROW);
        register(from, to, "downArrowCallout", CALLOUT_DOWN_ARROW);
        register(from, to, "leftRightArrowCallout", CALLOUT_LEFT_RIGHT_ARROW);
        register(from, to, "upDownArrowCallout", CALLOUT_UP_DOWN_ARROW);
        register(from, to, "quadArrowCallout", CALLOUT_QUAD_ARROW);
        register(from, to, "bentArrow", BENT_ARROW);
        register(from, to, "uturnArrow", U_TURN_ARROW);
        register(from, to, "circularArrow", CIRCULAR_ARROW);
        register(from, to, "leftCircularArrow", LEFT_CIRCULAR_ARROW);
        register(from, to, "leftRightCircularArrow", LEFT_RIGHT_CIRCULAR_ARROW);
        register(from, to, "curvedRightArrow", CURVED_RIGHT_ARROW);
        register(from, to, "curvedLeftArrow", CURVED_LEFT_ARROW);
        register(from, to, "curvedUpArrow", CURVED_UP_ARROW);
        register(from, to, "curvedDownArrow", CURVED_DOWN_ARROW);
        register(from, to, "swooshArrow", SWOOSH_ARROW);
        register(from, to, "cube", CUBE);
        register(from, to, "can", CAN);
        register(from, to, "lightningBolt", LIGHTNING_BOLT);
        register(from, to, "heart", HEART);
        register(from, to, "sun", SUN);
        register(from, to, "moon", MOON);
        register(from, to, "smileyFace", SMILEY_FACE);
        register(from, to, "irregularSeal1", IRREGULAR_SEAL1);
        register(from, to, "irregularSeal2", IRREGULAR_SEAL2);
        register(from, to, "foldedCorner", FOLDED_CORNER);
        register(from, to, "bevel", BEVEL);
        register(from, to, "frame", FRAME);
        register(from, to, "halfFrame", HALF_FRAME);
        register(from, to, "corner", CORNER);
        register(from, to, "diagStripe", DIAGONAL_STRIPE);
        register(from, to, "chord", CHORD);
        register(from, to, "arc", CURVED_ARC);
        register(from, to, "leftBracket", LEFT_BRACKET);
        register(from, to, "rightBracket", RIGHT_BRACKET);
        register(from, to, "leftBrace", LEFT_BRACE);
        register(from, to, "rightBrace", RIGHT_BRACE);
        register(from, to, "bracketPair", BRACKET_PAIR);
        register(from, to, "bracePair", BRACE_PAIR);
        register(from, to, "straightConnector1", STRAIGHT_CONNECTOR1);
        register(from, to, "bentConnector2", BENT_CONNECTOR2);
        register(from, to, "bentConnector3", BENT_CONNECTOR3);
        register(from, to, "bentConnector4", BENT_CONNECTOR4);
        register(from, to, "bentConnector5", BENT_CONNECTOR5);
        register(from, to, "curvedConnector2", CURVED_CONNECTOR2);
        register(from, to, "curvedConnector3", CURVED_CONNECTOR3);
        register(from, to, "curvedConnector4", CURVED_CONNECTOR4);
        register(from, to, "curvedConnector5", CURVED_CONNECTOR5);
        register(from, to, "callout1", CALLOUT1);
        register(from, to, "callout2", CALLOUT2);
        register(from, to, "callout3", CALLOUT3);
        register(from, to, "accentCallout1", CALLOUT_1_WITH_ACCENT);
        register(from, to, "accentCallout2", CALLOUT_2_WITH_ACCENT);
        register(from, to, "accentCallout3", CALLOUT_3_WITH_ACCENT);
        register(from, to, "borderCallout1", CALLOUT_1_WITH_BORDER);
        register(from, to, "borderCallout2", CALLOUT_2_WITH_BORDER);
        register(from, to, "borderCallout3", CALLOUT_3_WITH_BORDER);
        register(from, to, "accentBorderCallout1", CALLOUT_1_WITH_BORDER_AND_ACCENT);
        register(from, to, "accentBorderCallout2", CALLOUT_2_WITH_BORDER_AND_ACCENT);
        register(from, to, "accentBorderCallout3", CALLOUT_3_WITH_BORDER_AND_ACCENT);
        register(from, to, "wedgeRectCallout", CALLOUT_WEDGE_RECTANGLE);
        register(from, to, "wedgeRoundRectCallout", CALLOUT_WEDGE_ROUND_RECTANGLE);
        register(from, to, "wedgeEllipseCallout", CALLOUT_WEDGE_ELLIPSE);
        register(from, to, "cloudCallout", CALLOUT_CLOUD);
        register(from, to, "cloud", CLOUD);
        register(from, to, "ribbon", RIBBON);
        register(from, to, "ribbon2", RIBBON2);
        register(from, to, "ellipseRibbon", ELLIPSE_RIBBON);
        register(from, to, "ellipseRibbon2", ELLIPSE_RIBBON2);
        register(from, to, "leftRightRibbon", LEFT_RIGHT_RIBBON);
        register(from, to, "verticalScroll", VERTICAL_SCROLL);
        register(from, to, "horizontalScroll", HORIZONTAL_SCROLL);
        register(from, to, "wave", WAVE);
        register(from, to, "doubleWave", DOUBLE_WAVE);
        register(from, to, "plus", PLUS);
        register(from, to, "flowChartProcess", PROCESS_FLOW);
        register(from, to, "flowChartDecision", DECISION_FLOW);
        register(from, to, "flowChartInputOutput", INPUT_OUTPUT_FLOW);
        register(from, to, "flowChartPredefinedProcess", PREDEFINED_PROCESS_FLOW);
        register(from, to, "flowChartInternalStorage", INTERNAL_STORAGE_FLOW);
        register(from, to, "flowChartDocument", DOCUMENT_FLOW);
        register(from, to, "flowChartMultidocument", MULTI_DOCUMENT_FLOW);
        register(from, to, "flowChartTerminator", TERMINATOR_FLOW);
        register(from, to, "flowChartPreparation", PREPARATION_FLOW);
        register(from, to, "flowChartManualInput", MANUAL_INPUT_FLOW);
        register(from, to, "flowChartManualOperation", MANUAL_OPERATION_FLOW);
        register(from, to, "flowChartConnector", CONNECTOR_FLOW);
        register(from, to, "flowChartPunchedCard", PUNCHED_CARD_FLOW);
        register(from, to, "flowChartPunchedTape", PUNCHED_TAPE_FLOW);
        register(from, to, "flowChartSummingJunction", SUMMING_JUNCTION_FLOW);
        register(from, to, "flowChartOr", OR_FLOW);
        register(from, to, "flowChartCollate", COLLATE_FLOW);
        register(from, to, "flowChartSort", SORT_FLOW);
        register(from, to, "flowChartExtract", EXTRACT_FLOW);
        register(from, to, "flowChartMerge", MERGE_FLOW);
        register(from, to, "flowChartOfflineStorage", OFFLINE_STORAGE_FLOW);
        register(from, to, "flowChartOnlineStorage", ONLINE_STORAGE_FLOW);
        register(from, to, "flowChartMagneticTape", MAGNETIC_TAPE_FLOW);
        register(from, to, "flowChartMagneticDisk", MAGNETIC_DISK_FLOW);
        register(from, to, "flowChartMagneticDrum", MAGNETIC_DRUM_FLOW);
        register(from, to, "flowChartDisplay", DISPLAY_FLOW);
        register(from, to, "flowChartDelay", DELAY_FLOW);
        register(from, to, "flowChartAlternateProcess", ALTERNATE_PROCESS_FLOW);
        register(from, to, "flowChartOffpageConnector", OFF_PAGE_CONNECTOR_FLOW);
        register(from, to, "actionButtonBlank", BLANK_BUTTON);
        register(from, to, "actionButtonHome", HOME_BUTTON);
        register(from, to, "actionButtonHelp", HELP_BUTTON);
        register(from, to, "actionButtonInformation", INFORMATION_BUTTON);
        register(from, to, "actionButtonForwardNext", FORWARD_OR_NEXT_BUTTON);
        register(from, to, "actionButtonBackPrevious", BACK_OR_PREVIOUS_BUTTON);
        register(from, to, "actionButtonEnd", END_BUTTON);
        register(from, to, "actionButtonBeginning", BEGINNING_BUTTON);
        register(from, to, "actionButtonReturn", RETURN_BUTTON);
        register(from, to, "actionButtonDocument", DOCUMENT_BUTTON);
        register(from, to, "actionButtonSound", SOUND_BUTTON);
        register(from, to, "actionButtonMovie", MOVIE_BUTTON);
        register(from, to, "gear6", GEAR6);
        register(from, to, "gear9", GEAR9);
        register(from, to, "funnel", FUNNEL);
        register(from, to, "mathPlus", PLUS_MATH);
        register(from, to, "mathMinus", MINUS_MATH);
        register(from, to, "mathMultiply", MULTIPLY_MATH);
        register(from, to, "mathDivide", DIVIDE_MATH);
        register(from, to, "mathEqual", EQUAL_MATH);
        register(from, to, "mathNotEqual", NOT_EQUAL_MATH);
        register(from, to, "cornerTabs", CORNER_TABS);
        register(from, to, "squareTabs", SQUARE_TABS);
        register(from, to, "plaqueTabs", PLAQUE_TABS);
        register(from, to, "chartX", CHART_X);
        register(from, to, "chartStar", CHART_STAR);
        register(from, to, "chartPlus", CHART_PLUS);

        // Legacy alias: ROUND_RECTANGLE maps to same OOXML as ROUND_CORNER_RECTANGLE
        to.put(ROUND_RECTANGLE, "roundRect");

        FROM_OOXML = Map.copyOf(from);
        TO_OOXML = Map.copyOf(to);
    }

    private static void register(Map<String, ShapeType> from, Map<ShapeType, String> to,
                                  String ooxmlName, ShapeType type) {
        from.put(ooxmlName, type);
        to.put(type, ooxmlName);
    }

    /**
     * Resolves an OOXML preset geometry name to a {@link ShapeType}.
     *
     * @param prst the OOXML preset name (e.g. "rect", "straightConnector1")
     * @return the matching ShapeType, or empty if not recognized
     */
    public static Optional<ShapeType> fromOoxml(String prst) {
        return Optional.ofNullable(FROM_OOXML.get(prst));
    }

    /**
     * Returns the OOXML preset geometry name for this shape type.
     *
     * @return the OOXML name, or empty for NOT_DEFINED/CUSTOM
     */
    public Optional<String> toOoxml() {
        return Optional.ofNullable(TO_OOXML.get(this));
    }
}
