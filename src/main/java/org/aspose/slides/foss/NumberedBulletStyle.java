package org.aspose.slides.foss;

/**
 * Represents the style of the numbered bullets.
 */
public enum NumberedBulletStyle {
    /** Not defined. */
    NOT_DEFINED("NotDefined"),
    /** a., b., c., ... */
    BULLET_ALPHA_LC_PERIOD("BulletAlphaLCPeriod"),
    /** A., B., C., ... */
    BULLET_ALPHA_UC_PERIOD("BulletAlphaUCPeriod"),
    /** 1), 2), 3), ... */
    BULLET_ARABIC_PAREN_RIGHT("BulletArabicParenRight"),
    /** 1., 2., 3., ... */
    BULLET_ARABIC_PERIOD("BulletArabicPeriod"),
    /** (i), (ii), (iii), ... */
    BULLET_ROMAN_LC_PAREN_BOTH("BulletRomanLCParenBoth"),
    /** i), ii), iii), ... */
    BULLET_ROMAN_LC_PAREN_RIGHT("BulletRomanLCParenRight"),
    /** i., ii., iii., ... */
    BULLET_ROMAN_LC_PERIOD("BulletRomanLCPeriod"),
    /** I., II., III., ... */
    BULLET_ROMAN_UC_PERIOD("BulletRomanUCPeriod"),
    /** (a), (b), (c), ... */
    BULLET_ALPHA_LC_PAREN_BOTH("BulletAlphaLCParenBoth"),
    /** a), b), c), ... */
    BULLET_ALPHA_LC_PAREN_RIGHT("BulletAlphaLCParenRight"),
    /** (A), (B), (C), ... */
    BULLET_ALPHA_UC_PAREN_BOTH("BulletAlphaUCParenBoth"),
    /** A), B), C), ... */
    BULLET_ALPHA_UC_PAREN_RIGHT("BulletAlphaUCParenRight"),
    /** (1), (2), (3), ... */
    BULLET_ARABIC_PAREN_BOTH("BulletArabicParenBoth"),
    /** 1, 2, 3, ... */
    BULLET_ARABIC_PLAIN("BulletArabicPlain"),
    /** (I), (II), (III), ... */
    BULLET_ROMAN_UC_PAREN_BOTH("BulletRomanUCParenBoth"),
    /** I), II), III), ... */
    BULLET_ROMAN_UC_PAREN_RIGHT("BulletRomanUCParenRight"),
    /** Simplified Chinese plain. */
    BULLET_SIMP_CHIN_PLAIN("BulletSimpChinPlain"),
    /** Simplified Chinese period. */
    BULLET_SIMP_CHIN_PERIOD("BulletSimpChinPeriod"),
    /** Circle number double-byte plain. */
    BULLET_CIRCLE_NUM_DB_PLAIN("BulletCircleNumDBPlain"),
    /** Circle number wide white plain. */
    BULLET_CIRCLE_NUM_WD_WHITE_PLAIN("BulletCircleNumWDWhitePlain"),
    /** Circle number wide black plain. */
    BULLET_CIRCLE_NUM_WD_BLACK_PLAIN("BulletCircleNumWDBlackPlain"),
    /** Traditional Chinese plain. */
    BULLET_TRAD_CHIN_PLAIN("BulletTradChinPlain"),
    /** Traditional Chinese period. */
    BULLET_TRAD_CHIN_PERIOD("BulletTradChinPeriod"),
    /** Arabic alpha dash. */
    BULLET_ARABIC_ALPHA_DASH("BulletArabicAlphaDash"),
    /** Arabic abjad dash. */
    BULLET_ARABIC_ABJAD_DASH("BulletArabicAbjadDash"),
    /** Hebrew alpha dash. */
    BULLET_HEBREW_ALPHA_DASH("BulletHebrewAlphaDash"),
    /** Kanji Korean plain. */
    BULLET_KANJI_KOREAN_PLAIN("BulletKanjiKoreanPlain"),
    /** Kanji Korean period. */
    BULLET_KANJI_KOREAN_PERIOD("BulletKanjiKoreanPeriod"),
    /** Arabic double-byte plain. */
    BULLET_ARABIC_DB_PLAIN("BulletArabicDBPlain"),
    /** Arabic double-byte period. */
    BULLET_ARABIC_DB_PERIOD("BulletArabicDBPeriod"),
    /** Thai alpha period. */
    BULLET_THAI_ALPHA_PERIOD("BulletThaiAlphaPeriod"),
    /** Thai alpha paren right. */
    BULLET_THAI_ALPHA_PAREN_RIGHT("BulletThaiAlphaParenRight"),
    /** Thai alpha paren both. */
    BULLET_THAI_ALPHA_PAREN_BOTH("BulletThaiAlphaParenBoth"),
    /** Thai number period. */
    BULLET_THAI_NUM_PERIOD("BulletThaiNumPeriod"),
    /** Thai number paren right. */
    BULLET_THAI_NUM_PAREN_RIGHT("BulletThaiNumParenRight"),
    /** Thai number paren both. */
    BULLET_THAI_NUM_PAREN_BOTH("BulletThaiNumParenBoth"),
    /** Hindi alpha period. */
    BULLET_HINDI_ALPHA_PERIOD("BulletHindiAlphaPeriod"),
    /** Hindi number period. */
    BULLET_HINDI_NUM_PERIOD("BulletHindiNumPeriod"),
    /** Kanji simplified Chinese double-byte period. */
    BULLET_KANJI_SIMP_CHIN_DB_PERIOD("BulletKanjiSimpChinDBPeriod"),
    /** Hindi number paren right. */
    BULLET_HINDI_NUM_PAREN_RIGHT("BulletHindiNumParenRight"),
    /** Hindi alpha 1 period. */
    BULLET_HINDI_ALPHA_1_PERIOD("BulletHindiAlpha1Period");

    private final String value;

    NumberedBulletStyle(String value) {
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
