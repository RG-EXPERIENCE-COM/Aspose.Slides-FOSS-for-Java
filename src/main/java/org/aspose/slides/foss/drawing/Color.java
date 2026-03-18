package org.aspose.slides.foss.drawing;

import java.util.Objects;

/**
 * Immutable value type representing an ARGB color.
 */
public final class Color {

    private final int a;
    private final int r;
    private final int g;
    private final int b;

    /**
     * Initializes a new Color with the specified ARGB components.
     *
     * @param a the alpha component
     * @param r the red component
     * @param g the green component
     * @param b the blue component
     */
    public Color(int a, int r, int g, int b) {
        this.a = a;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    /**
     * Initializes a new Color with the specified RGB components and default alpha of 255.
     *
     * @param r the red component
     * @param g the green component
     * @param b the blue component
     */
    public Color(int r, int g, int b) {
        this(255, r, g, b);
    }

    /**
     * Initializes a new Color with default ARGB components (opaque black).
     */
    public Color() {
        this(255, 0, 0, 0);
    }

    /**
     * Gets the alpha component.
     *
     * @return the alpha component
     */
    public int a() {
        return a;
    }

    /**
     * Gets the alpha component.
     *
     * @return the alpha component
     */
    public int getA() {
        return a;
    }

    /**
     * Gets the red component.
     *
     * @return the red component
     */
    public int r() {
        return r;
    }

    /**
     * Gets the red component.
     *
     * @return the red component
     */
    public int getR() {
        return r;
    }

    /**
     * Gets the green component.
     *
     * @return the green component
     */
    public int g() {
        return g;
    }

    /**
     * Gets the green component.
     *
     * @return the green component
     */
    public int getG() {
        return g;
    }

    /**
     * Gets the blue component.
     *
     * @return the blue component
     */
    public int b() {
        return b;
    }

    /**
     * Gets the blue component.
     *
     * @return the blue component
     */
    public int getB() {
        return b;
    }

    /**
     * Creates a new Color from the specified ARGB components.
     *
     * @param a the alpha component
     * @param r the red component
     * @param g the green component
     * @param b the blue component
     * @return a new Color instance
     */
    public static Color fromArgb(int a, int r, int g, int b) {
        return new Color(a, r, g, b);
    }

    /**
     * Creates a new Color from the specified RGB components with alpha 255.
     *
     * @param r the red component
     * @param g the green component
     * @param b the blue component
     * @return a new Color instance
     */
    public static Color fromArgb(int r, int g, int b) {
        return new Color(255, r, g, b);
    }

    /**
     * Creates a new Color from a packed 32-bit ARGB value.
     *
     * @param argb the packed ARGB value
     * @return a new Color instance
     */
    public static Color fromArgb(int argb) {
        return new Color(
                (argb >> 24) & 0xFF,
                (argb >> 16) & 0xFF,
                (argb >> 8) & 0xFF,
                argb & 0xFF
        );
    }

    /**
     * Packs this color into a 32-bit ARGB integer.
     *
     * @return the packed ARGB value
     */
    public int toArgb() {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    /**
     * Returns {@code true} if this color is empty (all components are zero).
     *
     * @return true if empty
     */
    public boolean isEmpty() {
        return a == 0 && r == 0 && g == 0 && b == 0;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Color other
                && a == other.a && r == other.r && g == other.g && b == other.b;
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, r, g, b);
    }

    @Override
    public String toString() {
        return a == 255
                ? "Color(r=%d, g=%d, b=%d)".formatted(r, g, b)
                : "Color(a=%d, r=%d, g=%d, b=%d)".formatted(a, r, g, b);
    }

    // Named color constants
    /** Transparent (alpha=0, white). */
    public static final Color TRANSPARENT = new Color(0, 255, 255, 255);
    /** AliceBlue. */
    public static final Color ALICE_BLUE = new Color(240, 248, 255);
    /** AntiqueWhite. */
    public static final Color ANTIQUE_WHITE = new Color(250, 235, 215);
    /** Aqua. */
    public static final Color AQUA = new Color(0, 255, 255);
    /** Aquamarine. */
    public static final Color AQUAMARINE = new Color(127, 255, 212);
    /** Azure. */
    public static final Color AZURE = new Color(240, 255, 255);
    /** Beige. */
    public static final Color BEIGE = new Color(245, 245, 220);
    /** Bisque. */
    public static final Color BISQUE = new Color(255, 228, 196);
    /** Black. */
    public static final Color BLACK = new Color(0, 0, 0);
    /** BlanchedAlmond. */
    public static final Color BLANCHED_ALMOND = new Color(255, 235, 205);
    /** Blue. */
    public static final Color BLUE = new Color(0, 0, 255);
    /** BlueViolet. */
    public static final Color BLUE_VIOLET = new Color(138, 43, 226);
    /** Brown. */
    public static final Color BROWN = new Color(165, 42, 42);
    /** BurlyWood. */
    public static final Color BURLY_WOOD = new Color(222, 184, 135);
    /** CadetBlue. */
    public static final Color CADET_BLUE = new Color(95, 158, 160);
    /** Chartreuse. */
    public static final Color CHARTREUSE = new Color(127, 255, 0);
    /** Chocolate. */
    public static final Color CHOCOLATE = new Color(210, 105, 30);
    /** Coral. */
    public static final Color CORAL = new Color(255, 127, 80);
    /** CornflowerBlue. */
    public static final Color CORNFLOWER_BLUE = new Color(100, 149, 237);
    /** Cornsilk. */
    public static final Color CORNSILK = new Color(255, 248, 220);
    /** Crimson. */
    public static final Color CRIMSON = new Color(220, 20, 60);
    /** Cyan. */
    public static final Color CYAN = new Color(0, 255, 255);
    /** DarkBlue. */
    public static final Color DARK_BLUE = new Color(0, 0, 139);
    /** DarkCyan. */
    public static final Color DARK_CYAN = new Color(0, 139, 139);
    /** DarkGoldenrod. */
    public static final Color DARK_GOLDENROD = new Color(184, 134, 11);
    /** DarkGray. */
    public static final Color DARK_GRAY = new Color(169, 169, 169);
    /** DarkGreen. */
    public static final Color DARK_GREEN = new Color(0, 100, 0);
    /** DarkKhaki. */
    public static final Color DARK_KHAKI = new Color(189, 183, 107);
    /** DarkMagenta. */
    public static final Color DARK_MAGENTA = new Color(139, 0, 139);
    /** DarkOliveGreen. */
    public static final Color DARK_OLIVE_GREEN = new Color(85, 107, 47);
    /** DarkOrange. */
    public static final Color DARK_ORANGE = new Color(255, 140, 0);
    /** DarkOrchid. */
    public static final Color DARK_ORCHID = new Color(153, 50, 204);
    /** DarkRed. */
    public static final Color DARK_RED = new Color(139, 0, 0);
    /** DarkSalmon. */
    public static final Color DARK_SALMON = new Color(233, 150, 122);
    /** DarkSeaGreen. */
    public static final Color DARK_SEA_GREEN = new Color(143, 188, 143);
    /** DarkSlateBlue. */
    public static final Color DARK_SLATE_BLUE = new Color(72, 61, 139);
    /** DarkSlateGray. */
    public static final Color DARK_SLATE_GRAY = new Color(47, 79, 79);
    /** DarkTurquoise. */
    public static final Color DARK_TURQUOISE = new Color(0, 206, 209);
    /** DarkViolet. */
    public static final Color DARK_VIOLET = new Color(148, 0, 211);
    /** DeepPink. */
    public static final Color DEEP_PINK = new Color(255, 20, 147);
    /** DeepSkyBlue. */
    public static final Color DEEP_SKY_BLUE = new Color(0, 191, 255);
    /** DimGray. */
    public static final Color DIM_GRAY = new Color(105, 105, 105);
    /** DodgerBlue. */
    public static final Color DODGER_BLUE = new Color(30, 144, 255);
    /** Firebrick. */
    public static final Color FIREBRICK = new Color(178, 34, 34);
    /** FloralWhite. */
    public static final Color FLORAL_WHITE = new Color(255, 250, 240);
    /** ForestGreen. */
    public static final Color FOREST_GREEN = new Color(34, 139, 34);
    /** Fuchsia. */
    public static final Color FUCHSIA = new Color(255, 0, 255);
    /** Gainsboro. */
    public static final Color GAINSBORO = new Color(220, 220, 220);
    /** GhostWhite. */
    public static final Color GHOST_WHITE = new Color(248, 248, 255);
    /** Gold. */
    public static final Color GOLD = new Color(255, 215, 0);
    /** Goldenrod. */
    public static final Color GOLDENROD = new Color(218, 165, 32);
    /** Gray. */
    public static final Color GRAY = new Color(128, 128, 128);
    /** Green. */
    public static final Color GREEN = new Color(0, 128, 0);
    /** GreenYellow. */
    public static final Color GREEN_YELLOW = new Color(173, 255, 47);
    /** Honeydew. */
    public static final Color HONEYDEW = new Color(240, 255, 240);
    /** HotPink. */
    public static final Color HOT_PINK = new Color(255, 105, 180);
    /** IndianRed. */
    public static final Color INDIAN_RED = new Color(205, 92, 92);
    /** Indigo. */
    public static final Color INDIGO = new Color(75, 0, 130);
    /** Ivory. */
    public static final Color IVORY = new Color(255, 255, 240);
    /** Khaki. */
    public static final Color KHAKI = new Color(240, 230, 140);
    /** Lavender. */
    public static final Color LAVENDER = new Color(230, 230, 250);
    /** LavenderBlush. */
    public static final Color LAVENDER_BLUSH = new Color(255, 240, 245);
    /** LawnGreen. */
    public static final Color LAWN_GREEN = new Color(124, 252, 0);
    /** LemonChiffon. */
    public static final Color LEMON_CHIFFON = new Color(255, 250, 205);
    /** LightBlue. */
    public static final Color LIGHT_BLUE = new Color(173, 216, 230);
    /** LightCoral. */
    public static final Color LIGHT_CORAL = new Color(240, 128, 128);
    /** LightCyan. */
    public static final Color LIGHT_CYAN = new Color(224, 255, 255);
    /** LightGoldenrodYellow. */
    public static final Color LIGHT_GOLDENROD_YELLOW = new Color(250, 250, 210);
    /** LightGray. */
    public static final Color LIGHT_GRAY = new Color(211, 211, 211);
    /** LightGreen. */
    public static final Color LIGHT_GREEN = new Color(144, 238, 144);
    /** LightPink. */
    public static final Color LIGHT_PINK = new Color(255, 182, 193);
    /** LightSalmon. */
    public static final Color LIGHT_SALMON = new Color(255, 160, 122);
    /** LightSeaGreen. */
    public static final Color LIGHT_SEA_GREEN = new Color(32, 178, 170);
    /** LightSkyBlue. */
    public static final Color LIGHT_SKY_BLUE = new Color(135, 206, 250);
    /** LightSlateGray. */
    public static final Color LIGHT_SLATE_GRAY = new Color(119, 136, 153);
    /** LightSteelBlue. */
    public static final Color LIGHT_STEEL_BLUE = new Color(176, 196, 222);
    /** LightYellow. */
    public static final Color LIGHT_YELLOW = new Color(255, 255, 224);
    /** Lime. */
    public static final Color LIME = new Color(0, 255, 0);
    /** LimeGreen. */
    public static final Color LIME_GREEN = new Color(50, 205, 50);
    /** Linen. */
    public static final Color LINEN = new Color(250, 240, 230);
    /** Magenta. */
    public static final Color MAGENTA = new Color(255, 0, 255);
    /** Maroon. */
    public static final Color MAROON = new Color(128, 0, 0);
    /** MediumAquamarine. */
    public static final Color MEDIUM_AQUAMARINE = new Color(102, 205, 170);
    /** MediumBlue. */
    public static final Color MEDIUM_BLUE = new Color(0, 0, 205);
    /** MediumOrchid. */
    public static final Color MEDIUM_ORCHID = new Color(186, 85, 211);
    /** MediumPurple. */
    public static final Color MEDIUM_PURPLE = new Color(147, 112, 219);
    /** MediumSeaGreen. */
    public static final Color MEDIUM_SEA_GREEN = new Color(60, 179, 113);
    /** MediumSlateBlue. */
    public static final Color MEDIUM_SLATE_BLUE = new Color(123, 104, 238);
    /** MediumSpringGreen. */
    public static final Color MEDIUM_SPRING_GREEN = new Color(0, 250, 154);
    /** MediumTurquoise. */
    public static final Color MEDIUM_TURQUOISE = new Color(72, 209, 204);
    /** MediumVioletRed. */
    public static final Color MEDIUM_VIOLET_RED = new Color(199, 21, 133);
    /** MidnightBlue. */
    public static final Color MIDNIGHT_BLUE = new Color(25, 25, 112);
    /** MintCream. */
    public static final Color MINT_CREAM = new Color(245, 255, 250);
    /** MistyRose. */
    public static final Color MISTY_ROSE = new Color(255, 228, 225);
    /** Moccasin. */
    public static final Color MOCCASIN = new Color(255, 228, 181);
    /** NavajoWhite. */
    public static final Color NAVAJO_WHITE = new Color(255, 222, 173);
    /** Navy. */
    public static final Color NAVY = new Color(0, 0, 128);
    /** OldLace. */
    public static final Color OLD_LACE = new Color(253, 245, 230);
    /** Olive. */
    public static final Color OLIVE = new Color(128, 128, 0);
    /** OliveDrab. */
    public static final Color OLIVE_DRAB = new Color(107, 142, 35);
    /** Orange. */
    public static final Color ORANGE = new Color(255, 165, 0);
    /** OrangeRed. */
    public static final Color ORANGE_RED = new Color(255, 69, 0);
    /** Orchid. */
    public static final Color ORCHID = new Color(218, 112, 214);
    /** PaleGoldenrod. */
    public static final Color PALE_GOLDENROD = new Color(238, 232, 170);
    /** PaleGreen. */
    public static final Color PALE_GREEN = new Color(152, 251, 152);
    /** PaleTurquoise. */
    public static final Color PALE_TURQUOISE = new Color(175, 238, 238);
    /** PaleVioletRed. */
    public static final Color PALE_VIOLET_RED = new Color(219, 112, 147);
    /** PapayaWhip. */
    public static final Color PAPAYA_WHIP = new Color(255, 239, 213);
    /** PeachPuff. */
    public static final Color PEACH_PUFF = new Color(255, 218, 185);
    /** Peru. */
    public static final Color PERU = new Color(205, 133, 63);
    /** Pink. */
    public static final Color PINK = new Color(255, 192, 203);
    /** Plum. */
    public static final Color PLUM = new Color(221, 160, 221);
    /** PowderBlue. */
    public static final Color POWDER_BLUE = new Color(176, 224, 230);
    /** Purple. */
    public static final Color PURPLE = new Color(128, 0, 128);
    /** Red. */
    public static final Color RED = new Color(255, 0, 0);
    /** RosyBrown. */
    public static final Color ROSY_BROWN = new Color(188, 143, 143);
    /** RoyalBlue. */
    public static final Color ROYAL_BLUE = new Color(65, 105, 225);
    /** SaddleBrown. */
    public static final Color SADDLE_BROWN = new Color(139, 69, 19);
    /** Salmon. */
    public static final Color SALMON = new Color(250, 128, 114);
    /** SandyBrown. */
    public static final Color SANDY_BROWN = new Color(244, 164, 96);
    /** SeaGreen. */
    public static final Color SEA_GREEN = new Color(46, 139, 87);
    /** SeaShell. */
    public static final Color SEA_SHELL = new Color(255, 245, 238);
    /** Sienna. */
    public static final Color SIENNA = new Color(160, 82, 45);
    /** Silver. */
    public static final Color SILVER = new Color(192, 192, 192);
    /** SkyBlue. */
    public static final Color SKY_BLUE = new Color(135, 206, 235);
    /** SlateBlue. */
    public static final Color SLATE_BLUE = new Color(106, 90, 205);
    /** SlateGray. */
    public static final Color SLATE_GRAY = new Color(112, 128, 144);
    /** Snow. */
    public static final Color SNOW = new Color(255, 250, 250);
    /** SpringGreen. */
    public static final Color SPRING_GREEN = new Color(0, 255, 127);
    /** SteelBlue. */
    public static final Color STEEL_BLUE = new Color(70, 130, 180);
    /** Tan. */
    public static final Color TAN = new Color(210, 180, 140);
    /** Teal. */
    public static final Color TEAL = new Color(0, 128, 128);
    /** Thistle. */
    public static final Color THISTLE = new Color(216, 191, 216);
    /** Tomato. */
    public static final Color TOMATO = new Color(255, 99, 71);
    /** Turquoise. */
    public static final Color TURQUOISE = new Color(64, 224, 208);
    /** Violet. */
    public static final Color VIOLET = new Color(238, 130, 238);
    /** Wheat. */
    public static final Color WHEAT = new Color(245, 222, 179);
    /** White. */
    public static final Color WHITE = new Color(255, 255, 255);
    /** WhiteSmoke. */
    public static final Color WHITE_SMOKE = new Color(245, 245, 245);
    /** Yellow. */
    public static final Color YELLOW = new Color(255, 255, 0);
    /** YellowGreen. */
    public static final Color YELLOW_GREEN = new Color(154, 205, 50);
    /** Empty (all zeros). */
    public static final Color EMPTY = new Color(0, 0, 0, 0);
}
