package org.aspose.slides.foss;

import org.aspose.slides.foss.drawing.Color;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Map;
import java.util.Set;

/**
 * Represents a color used in a presentation.
 *
 * <p>Manages color state backed by OOXML elements within a parent XML element
 * (e.g., {@code <a:solidFill>}, {@code <a:fgClr>}).</p>
 */
public final class ColorFormat implements IColorFormat, IFillParamSource {

    // OOXML Drawing namespace
    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";

    // Color element local names
    private static final String SRGB_CLR = "srgbClr";
    private static final String SCHEME_CLR = "schemeClr";
    private static final String PRST_CLR = "prstClr";
    private static final String SYS_CLR = "sysClr";
    private static final String HLS_CLR = "hlsClr";
    private static final String SCR_GB_CLR = "scrgbClr";

    private static final Set<String> COLOR_LOCAL_NAMES = Set.of(
            SRGB_CLR, SCHEME_CLR, PRST_CLR, SYS_CLR, HLS_CLR, SCR_GB_CLR
    );

    /** Maps OOXML scheme color attribute values to {@link SchemeColor} enum names. */
    private static final Map<String, String> SCHEME_CLR_TO_ENUM = Map.ofEntries(
            Map.entry("bg1", "BACKGROUND1"), Map.entry("tx1", "TEXT1"),
            Map.entry("bg2", "BACKGROUND2"), Map.entry("tx2", "TEXT2"),
            Map.entry("accent1", "ACCENT1"), Map.entry("accent2", "ACCENT2"),
            Map.entry("accent3", "ACCENT3"), Map.entry("accent4", "ACCENT4"),
            Map.entry("accent5", "ACCENT5"), Map.entry("accent6", "ACCENT6"),
            Map.entry("hlink", "HYPERLINK"), Map.entry("folHlink", "FOLLOWED_HYPERLINK"),
            Map.entry("dk1", "DARK1"), Map.entry("lt1", "LIGHT1"),
            Map.entry("dk2", "DARK2"), Map.entry("lt2", "LIGHT2")
    );

    /** Reverse map: enum name to OOXML attribute value. */
    private static final Map<String, String> ENUM_TO_SCHEME_CLR = Map.ofEntries(
            Map.entry("BACKGROUND1", "bg1"), Map.entry("TEXT1", "tx1"),
            Map.entry("BACKGROUND2", "bg2"), Map.entry("TEXT2", "tx2"),
            Map.entry("ACCENT1", "accent1"), Map.entry("ACCENT2", "accent2"),
            Map.entry("ACCENT3", "accent3"), Map.entry("ACCENT4", "accent4"),
            Map.entry("ACCENT5", "accent5"), Map.entry("ACCENT6", "accent6"),
            Map.entry("HYPERLINK", "hlink"), Map.entry("FOLLOWED_HYPERLINK", "folHlink"),
            Map.entry("DARK1", "dk1"), Map.entry("LIGHT1", "lt1"),
            Map.entry("DARK2", "dk2"), Map.entry("LIGHT2", "lt2")
    );

    private final Element parentElement;
    private final Runnable saveCallback;

    /**
     * Creates a new ColorFormat backed by the given parent XML element.
     *
     * @param parentElement the XML element that contains color child elements
     *                      (e.g., {@code <a:solidFill>})
     * @param saveCallback  callback invoked after mutations to persist changes; may be {@code null}
     */
    public ColorFormat(Element parentElement, Runnable saveCallback) {
        this.parentElement = parentElement;
        this.saveCallback = saveCallback;
    }

    /**
     * Creates a new ColorFormat backed by the given parent XML element with no save callback.
     *
     * @param parentElement the XML element that contains color child elements
     */
    public ColorFormat(Element parentElement) {
        this(parentElement, null);
    }

    // ---- internal helpers ----

    private Element findColorElement() {
        NodeList children = parentElement.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child instanceof Element el && isColorElement(el)) {
                return el;
            }
        }
        return null;
    }

    private void clearColorElements() {
        NodeList children = parentElement.getChildNodes();
        for (int i = children.getLength() - 1; i >= 0; i--) {
            Node child = children.item(i);
            if (child instanceof Element el && isColorElement(el)) {
                parentElement.removeChild(el);
            }
        }
    }

    private static boolean isColorElement(Element el) {
        return NS_A.equals(el.getNamespaceURI()) && COLOR_LOCAL_NAMES.contains(el.getLocalName());
    }

    private Element createChildElement(String localName) {
        Document doc = parentElement.getOwnerDocument();
        return (Element) parentElement.appendChild(doc.createElementNS(NS_A, "a:" + localName));
    }

    private void save() {
        if (saveCallback != null) {
            saveCallback.run();
        }
    }

    private int readAlpha(Element colorElement) {
        NodeList alphaNodes = colorElement.getElementsByTagNameNS(NS_A, "alpha");
        if (alphaNodes.getLength() == 0) {
            return 255;
        }
        Element alphaEl = (Element) alphaNodes.item(0);
        int val = Integer.parseInt(alphaEl.getAttribute("val"));
        return (int) Math.round(val * 255.0 / 100_000);
    }

    private void writeAlpha(Element colorElement, int alpha) {
        // Remove existing alpha
        NodeList alphaNodes = colorElement.getElementsByTagNameNS(NS_A, "alpha");
        for (int i = alphaNodes.getLength() - 1; i >= 0; i--) {
            colorElement.removeChild(alphaNodes.item(i));
        }
        if (alpha < 255) {
            int val = (int) Math.round(alpha * 100_000.0 / 255);
            Document doc = colorElement.getOwnerDocument();
            Element alphaEl = doc.createElementNS(NS_A, "a:alpha");
            alphaEl.setAttribute("val", String.valueOf(val));
            colorElement.appendChild(alphaEl);
        }
    }

    // ---- public API ----

    @Override
    public ColorType getColorType() {
        Element el = findColorElement();
        if (el == null) {
            return ColorType.NOT_DEFINED;
        }
        return switch (el.getLocalName()) {
            case SRGB_CLR -> ColorType.RGB;
            case SCHEME_CLR -> ColorType.SCHEME;
            case PRST_CLR -> ColorType.PRESET;
            case SYS_CLR -> ColorType.SYSTEM;
            case SCR_GB_CLR -> ColorType.RGB_PERCENTAGE;
            case HLS_CLR -> ColorType.HSL;
            default -> ColorType.NOT_DEFINED;
        };
    }

    @Override
    public void setColorType(ColorType value) {
        if (value == getColorType()) {
            return;
        }
        clearColorElements();
        switch (value) {
            case RGB -> createChildElement(SRGB_CLR).setAttribute("val", "000000");
            case SCHEME -> createChildElement(SCHEME_CLR).setAttribute("val", "tx1");
            case PRESET -> createChildElement(PRST_CLR).setAttribute("val", "black");
            case SYSTEM -> createChildElement(SYS_CLR).setAttribute("val", "windowText");
            case HSL -> {
                Element el = createChildElement(HLS_CLR);
                el.setAttribute("hue", "0");
                el.setAttribute("sat", "0");
                el.setAttribute("lum", "0");
            }
            case RGB_PERCENTAGE -> {
                Element el = createChildElement(SCR_GB_CLR);
                el.setAttribute("r", "0");
                el.setAttribute("g", "0");
                el.setAttribute("b", "0");
            }
            case NOT_DEFINED -> { /* clearing is enough */ }
        }
        save();
    }

    @Override
    public Color getColor() {
        Element el = findColorElement();
        if (el == null) {
            return new Color(255, 0, 0, 0);
        }
        if (SRGB_CLR.equals(el.getLocalName())) {
            String hexVal = el.getAttribute("val");
            if (hexVal == null || hexVal.isEmpty()) {
                hexVal = "000000";
            }
            int r = Integer.parseInt(hexVal.substring(0, 2), 16);
            int g = Integer.parseInt(hexVal.substring(2, 4), 16);
            int b = Integer.parseInt(hexVal.substring(4, 6), 16);
            int a = readAlpha(el);
            return new Color(a, r, g, b);
        }
        return new Color(255, 0, 0, 0);
    }

    @Override
    public void setColor(Color value) {
        clearColorElements();
        String hexVal = "%02X%02X%02X".formatted(value.getR(), value.getG(), value.getB());
        Element clrEl = createChildElement(SRGB_CLR);
        clrEl.setAttribute("val", hexVal);
        writeAlpha(clrEl, value.getA());
        save();
    }

    @Override
    public PresetColor getPresetColor() {
        Element el = findColorElement();
        if (el != null && PRST_CLR.equals(el.getLocalName())) {
            String val = el.getAttribute("val");
            if (val != null && !val.isEmpty()) {
                String name = camelToUpperSnake(val);
                try {
                    return PresetColor.valueOf(name);
                } catch (IllegalArgumentException ignored) {
                    // fall through
                }
            }
        }
        return PresetColor.NOT_DEFINED;
    }

    @Override
    public void setPresetColor(PresetColor value) {
        if (value == PresetColor.NOT_DEFINED) {
            return;
        }
        clearColorElements();
        String ooxmlVal = upperSnakeToCamel(value.name());
        createChildElement(PRST_CLR).setAttribute("val", ooxmlVal);
        save();
    }

    @Override
    public SchemeColor getSchemeColor() {
        Element el = findColorElement();
        if (el != null && SCHEME_CLR.equals(el.getLocalName())) {
            String val = el.getAttribute("val");
            String enumName = SCHEME_CLR_TO_ENUM.get(val);
            if (enumName != null) {
                return SchemeColor.valueOf(enumName);
            }
        }
        return SchemeColor.NOT_DEFINED;
    }

    @Override
    public void setSchemeColor(SchemeColor value) {
        if (value == SchemeColor.NOT_DEFINED) {
            return;
        }
        String ooxmlVal = ENUM_TO_SCHEME_CLR.get(value.name());
        if (ooxmlVal == null) {
            return;
        }
        clearColorElements();
        createChildElement(SCHEME_CLR).setAttribute("val", ooxmlVal);
        save();
    }

    @Override
    public int getR() {
        return getColor().getR();
    }

    @Override
    public void setR(int value) {
        Color c = getColor();
        setColor(new Color(c.getA(), value, c.getG(), c.getB()));
    }

    @Override
    public int getG() {
        return getColor().getG();
    }

    @Override
    public void setG(int value) {
        Color c = getColor();
        setColor(new Color(c.getA(), c.getR(), value, c.getB()));
    }

    @Override
    public int getB() {
        return getColor().getB();
    }

    @Override
    public void setB(int value) {
        Color c = getColor();
        setColor(new Color(c.getA(), c.getR(), c.getG(), value));
    }

    @Override
    public float getFloatR() {
        return getR() / 255.0f;
    }

    @Override
    public void setFloatR(float value) {
        setR(Math.round(value * 255));
    }

    @Override
    public float getFloatG() {
        return getG() / 255.0f;
    }

    @Override
    public void setFloatG(float value) {
        setG(Math.round(value * 255));
    }

    @Override
    public float getFloatB() {
        return getB() / 255.0f;
    }

    @Override
    public void setFloatB(float value) {
        setB(Math.round(value * 255));
    }

    @Override
    public float getHue() {
        Element el = findColorElement();
        if (el == null || !HLS_CLR.equals(el.getLocalName())) {
            return 0f;
        }
        String val = el.getAttribute("hue");
        if (val == null || val.isEmpty()) {
            return 0f;
        }
        return Integer.parseInt(val) / 60000f;
    }

    @Override
    public void setHue(float value) {
        Element el = findColorElement();
        if (el == null || !HLS_CLR.equals(el.getLocalName())) {
            return;
        }
        el.setAttribute("hue", String.valueOf((int) (value * 60000f)));
        save();
    }

    @Override
    public float getSaturation() {
        Element el = findColorElement();
        if (el == null || !HLS_CLR.equals(el.getLocalName())) {
            return 0f;
        }
        String val = el.getAttribute("sat");
        if (val == null || val.isEmpty()) {
            return 0f;
        }
        return Integer.parseInt(val) / 1000f;
    }

    @Override
    public void setSaturation(float value) {
        Element el = findColorElement();
        if (el == null || !HLS_CLR.equals(el.getLocalName())) {
            return;
        }
        el.setAttribute("sat", String.valueOf((int) (value * 1000f)));
        save();
    }

    @Override
    public float getLuminance() {
        Element el = findColorElement();
        if (el == null || !HLS_CLR.equals(el.getLocalName())) {
            return 0f;
        }
        String val = el.getAttribute("lum");
        if (val == null || val.isEmpty()) {
            return 0f;
        }
        return Integer.parseInt(val) / 1000f;
    }

    @Override
    public void setLuminance(float value) {
        Element el = findColorElement();
        if (el == null || !HLS_CLR.equals(el.getLocalName())) {
            return;
        }
        el.setAttribute("lum", String.valueOf((int) (value * 1000f)));
        save();
    }

    // ---- utility methods ----

    /**
     * Converts camelCase to UPPER_SNAKE_CASE. E.g., "aliceBlue" becomes "ALICE_BLUE".
     *
     * @param name the camelCase name
     * @return the UPPER_SNAKE_CASE equivalent
     */
    static String camelToUpperSnake(String name) {
        var sb = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char ch = name.charAt(i);
            if (Character.isUpperCase(ch) && i > 0) {
                sb.append('_');
            }
            sb.append(Character.toUpperCase(ch));
        }
        return sb.toString();
    }

    /**
     * Converts UPPER_SNAKE_CASE to camelCase. E.g., "ALICE_BLUE" becomes "aliceBlue".
     *
     * @param name the UPPER_SNAKE_CASE name
     * @return the camelCase equivalent
     */
    static String upperSnakeToCamel(String name) {
        String[] parts = name.toLowerCase(java.util.Locale.ROOT).split("_");
        var sb = new StringBuilder(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            if (!parts[i].isEmpty()) {
                sb.append(Character.toUpperCase(parts[i].charAt(0)));
                sb.append(parts[i].substring(1));
            }
        }
        return sb.toString();
    }
}
