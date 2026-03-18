package org.aspose.slides.foss;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents format of a line.
 *
 * <p>Wraps an OOXML parent element and reads/writes line format properties
 * from the child {@code <a:ln>} (or custom tag like {@code <a:uLn>}) element.</p>
 */
public final class LineFormat implements ILineFormat {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";
    private static final String DEFAULT_LN_TAG = "ln";
    private static final double EMU_PER_POINT = 12700.0;
    private static final double PERCENTAGE_SCALE = 100_000.0;

    // OOXML dash preset -> LineDashStyle
    private static final Map<String, LineDashStyle> DASH_MAP = Map.ofEntries(
            Map.entry("solid", LineDashStyle.SOLID),
            Map.entry("dot", LineDashStyle.DOT),
            Map.entry("dash", LineDashStyle.DASH),
            Map.entry("lgDash", LineDashStyle.LARGE_DASH),
            Map.entry("dashDot", LineDashStyle.DASH_DOT),
            Map.entry("lgDashDot", LineDashStyle.LARGE_DASH_DOT),
            Map.entry("lgDashDotDot", LineDashStyle.LARGE_DASH_DOT_DOT),
            Map.entry("sysDash", LineDashStyle.SYSTEM_DASH),
            Map.entry("sysDot", LineDashStyle.SYSTEM_DOT),
            Map.entry("sysDashDot", LineDashStyle.SYSTEM_DASH_DOT),
            Map.entry("sysDashDotDot", LineDashStyle.SYSTEM_DASH_DOT_DOT)
    );
    private static final Map<LineDashStyle, String> DASH_MAP_REV = Map.ofEntries(
            Map.entry(LineDashStyle.SOLID, "solid"),
            Map.entry(LineDashStyle.DOT, "dot"),
            Map.entry(LineDashStyle.DASH, "dash"),
            Map.entry(LineDashStyle.LARGE_DASH, "lgDash"),
            Map.entry(LineDashStyle.DASH_DOT, "dashDot"),
            Map.entry(LineDashStyle.LARGE_DASH_DOT, "lgDashDot"),
            Map.entry(LineDashStyle.LARGE_DASH_DOT_DOT, "lgDashDotDot"),
            Map.entry(LineDashStyle.SYSTEM_DASH, "sysDash"),
            Map.entry(LineDashStyle.SYSTEM_DOT, "sysDot"),
            Map.entry(LineDashStyle.SYSTEM_DASH_DOT, "sysDashDot"),
            Map.entry(LineDashStyle.SYSTEM_DASH_DOT_DOT, "sysDashDotDot")
    );

    // OOXML cap -> LineCapStyle
    private static final Map<String, LineCapStyle> CAP_MAP = Map.of(
            "rnd", LineCapStyle.ROUND, "sq", LineCapStyle.SQUARE, "flat", LineCapStyle.FLAT
    );
    private static final Map<LineCapStyle, String> CAP_MAP_REV = Map.of(
            LineCapStyle.ROUND, "rnd", LineCapStyle.SQUARE, "sq", LineCapStyle.FLAT, "flat"
    );

    // OOXML cmpd -> LineStyle
    private static final Map<String, LineStyle> CMPD_MAP = Map.of(
            "sng", LineStyle.SINGLE, "dbl", LineStyle.THIN_THIN,
            "thickThin", LineStyle.THICK_THIN, "thinThick", LineStyle.THIN_THICK,
            "tri", LineStyle.THICK_BETWEEN_THIN
    );
    private static final Map<LineStyle, String> CMPD_MAP_REV = Map.of(
            LineStyle.SINGLE, "sng", LineStyle.THIN_THIN, "dbl",
            LineStyle.THICK_THIN, "thickThin", LineStyle.THIN_THICK, "thinThick",
            LineStyle.THICK_BETWEEN_THIN, "tri"
    );

    // OOXML algn -> LineAlignment
    private static final Map<String, LineAlignment> ALGN_MAP = Map.of(
            "ctr", LineAlignment.CENTER, "in", LineAlignment.INSET
    );
    private static final Map<LineAlignment, String> ALGN_MAP_REV = Map.of(
            LineAlignment.CENTER, "ctr", LineAlignment.INSET, "in"
    );

    // Arrowhead type -> LineArrowheadStyle
    private static final Map<String, LineArrowheadStyle> ARROW_TYPE_MAP = Map.of(
            "none", LineArrowheadStyle.NONE, "triangle", LineArrowheadStyle.TRIANGLE,
            "stealth", LineArrowheadStyle.STEALTH, "diamond", LineArrowheadStyle.DIAMOND,
            "oval", LineArrowheadStyle.OVAL, "arrow", LineArrowheadStyle.OPEN
    );
    private static final Map<LineArrowheadStyle, String> ARROW_TYPE_MAP_REV = Map.of(
            LineArrowheadStyle.NONE, "none", LineArrowheadStyle.TRIANGLE, "triangle",
            LineArrowheadStyle.STEALTH, "stealth", LineArrowheadStyle.DIAMOND, "diamond",
            LineArrowheadStyle.OVAL, "oval", LineArrowheadStyle.OPEN, "arrow"
    );

    // Arrowhead width -> LineArrowheadWidth
    private static final Map<String, LineArrowheadWidth> ARROW_W_MAP = Map.of(
            "sm", LineArrowheadWidth.NARROW, "med", LineArrowheadWidth.MEDIUM, "lg", LineArrowheadWidth.WIDE
    );
    private static final Map<LineArrowheadWidth, String> ARROW_W_MAP_REV = Map.of(
            LineArrowheadWidth.NARROW, "sm", LineArrowheadWidth.MEDIUM, "med", LineArrowheadWidth.WIDE, "lg"
    );

    // Arrowhead length -> LineArrowheadLength
    private static final Map<String, LineArrowheadLength> ARROW_LEN_MAP = Map.of(
            "sm", LineArrowheadLength.SHORT, "med", LineArrowheadLength.MEDIUM, "lg", LineArrowheadLength.LONG
    );
    private static final Map<LineArrowheadLength, String> ARROW_LEN_MAP_REV = Map.of(
            LineArrowheadLength.SHORT, "sm", LineArrowheadLength.MEDIUM, "med", LineArrowheadLength.LONG, "lg"
    );

    // CT_LineProperties child ordering within <a:ln>
    private static final List<String> LN_CHILD_ORDER = List.of(
            "noFill", "solidFill", "gradFill", "pattFill",
            "prstDash", "custDash",
            "round", "bevel", "miter",
            "headEnd", "tailEnd",
            "extLst"
    );

    // CT_TableCellProperties border line ordering
    private static final List<String> TC_PR_CHILD_ORDER = List.of(
            "lnL", "lnR", "lnT", "lnB", "lnTlToBr", "lnBlToTr"
    );

    private final Element parentElement;
    private final Runnable saveCallback;
    private final String lnTag;

    /**
     * Creates a new LineFormat backed by the given parent XML element.
     *
     * @param parentElement the parent XML element (e.g., {@code <p:spPr>}, {@code <a:rPr>})
     * @param saveCallback  callback invoked after mutations; may be {@code null}
     * @param lnTag         the local name of the line element (e.g., "ln" or "uLn")
     */
    public LineFormat(Element parentElement, Runnable saveCallback, String lnTag) {
        this.parentElement = parentElement;
        this.saveCallback = saveCallback;
        this.lnTag = lnTag;
    }

    /**
     * Creates a new LineFormat with the default line tag "ln".
     *
     * @param parentElement the parent XML element
     * @param saveCallback  callback invoked after mutations; may be {@code null}
     */
    public LineFormat(Element parentElement, Runnable saveCallback) {
        this(parentElement, saveCallback, DEFAULT_LN_TAG);
    }

    // --- XML helpers ---

    private Element findChild(Element parent, String localName) {
        if (parent == null) return null;
        NodeList children = parent.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i) instanceof Element el) {
                if (NS_A.equals(el.getNamespaceURI()) && localName.equals(el.getLocalName())) {
                    return el;
                }
            }
        }
        return null;
    }

    private Element getLn() {
        return findChild(parentElement, lnTag);
    }

    private Element ensureLn() {
        Element ln = getLn();
        if (ln != null) return ln;

        Document doc = parentElement.getOwnerDocument();
        Element el = doc.createElementNS(NS_A, "a:" + lnTag);

        // For table cell border elements, use tcPr child ordering
        int tcRank = TC_PR_CHILD_ORDER.indexOf(lnTag);
        if (tcRank >= 0) {
            NodeList children = parentElement.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                if (children.item(i) instanceof Element child) {
                    int childRank = TC_PR_CHILD_ORDER.indexOf(child.getLocalName());
                    if (childRank < 0) {
                        // Non-border child comes after all borders
                        parentElement.insertBefore(el, child);
                        return el;
                    }
                    if (childRank > tcRank) {
                        parentElement.insertBefore(el, child);
                        return el;
                    }
                }
            }
            parentElement.appendChild(el);
            return el;
        }

        // Default: insert before effect/3d/extLst elements
        Node insertBefore = null;
        NodeList children = parentElement.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i) instanceof Element child) {
                String name = child.getLocalName();
                if ("effectLst".equals(name) || "effectDag".equals(name)
                        || "scene3d".equals(name) || "sp3d".equals(name) || "extLst".equals(name)) {
                    insertBefore = child;
                    break;
                }
            }
        }
        if (insertBefore != null) {
            parentElement.insertBefore(el, insertBefore);
        } else {
            parentElement.appendChild(el);
        }
        return el;
    }

    private Element insertLnChild(Element ln, String localName) {
        Document doc = ln.getOwnerDocument();
        Element newEl = doc.createElementNS(NS_A, "a:" + localName);
        int newRank = LN_CHILD_ORDER.indexOf(localName);
        if (newRank < 0) {
            ln.appendChild(newEl);
            return newEl;
        }
        NodeList children = ln.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i) instanceof Element child) {
                int childRank = LN_CHILD_ORDER.indexOf(child.getLocalName());
                if (childRank >= 0 && childRank > newRank) {
                    ln.insertBefore(newEl, child);
                    return newEl;
                }
            }
        }
        ln.appendChild(newEl);
        return newEl;
    }

    private void removeChild(Element parent, String localName) {
        Element child = findChild(parent, localName);
        if (child != null) {
            parent.removeChild(child);
        }
    }

    private void save() {
        if (saveCallback != null) saveCallback.run();
    }

    // --- Properties ---

    @Override
    public boolean isFormatNotDefined() {
        Element ln = getLn();
        if (ln == null) return true;
        return !ln.hasAttributes() && !ln.hasChildNodes();
    }

    @Override
    public ILineFillFormat getFillFormat() {
        Element ln = ensureLn();
        return new LineFillFormat(ln, saveCallback);
    }

    @Override
    public double getWidth() {
        Element ln = getLn();
        if (ln == null) return 0.75;
        String w = ln.getAttribute("w");
        if (w == null || w.isEmpty()) return 0.75;
        try {
            return Long.parseLong(w) / EMU_PER_POINT;
        } catch (NumberFormatException e) {
            return 0.75;
        }
    }

    @Override
    public void setWidth(double value) {
        Element ln = ensureLn();
        ln.setAttribute("w", String.valueOf(Math.round(value * EMU_PER_POINT)));
        save();
    }

    @Override
    public LineDashStyle getDashStyle() {
        Element ln = getLn();
        if (ln == null) return LineDashStyle.NOT_DEFINED;
        if (findChild(ln, "custDash") != null) return LineDashStyle.CUSTOM;
        Element prstDash = findChild(ln, "prstDash");
        if (prstDash == null) return LineDashStyle.NOT_DEFINED;
        String val = prstDash.getAttribute("val");
        if (val == null || val.isEmpty()) return LineDashStyle.NOT_DEFINED;
        return DASH_MAP.getOrDefault(val, LineDashStyle.NOT_DEFINED);
    }

    @Override
    public void setDashStyle(LineDashStyle value) {
        Element ln = ensureLn();
        removeChild(ln, "prstDash");
        removeChild(ln, "custDash");
        if (value == LineDashStyle.NOT_DEFINED) {
            // No dash element needed
        } else if (value == LineDashStyle.CUSTOM) {
            insertLnChild(ln, "custDash");
        } else {
            String ooxmlVal = DASH_MAP_REV.get(value);
            if (ooxmlVal != null) {
                Element prstDash = insertLnChild(ln, "prstDash");
                prstDash.setAttribute("val", ooxmlVal);
            }
        }
        save();
    }

    @Override
    public List<Double> getCustomDashPattern() {
        Element ln = getLn();
        if (ln == null) return List.of();
        Element custDash = findChild(ln, "custDash");
        if (custDash == null) return List.of();
        List<Double> result = new ArrayList<>();
        NodeList children = custDash.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i) instanceof Element ds) {
                String d = ds.getAttribute("d");
                String sp = ds.getAttribute("sp");
                result.add(parseLongOrZero(d) / PERCENTAGE_SCALE);
                result.add(parseLongOrZero(sp) / PERCENTAGE_SCALE);
            }
        }
        return result;
    }

    @Override
    public void setCustomDashPattern(List<Double> value) {
        Element ln = ensureLn();
        Element custDash = findChild(ln, "custDash");
        if (custDash == null) {
            removeChild(ln, "prstDash");
            custDash = insertLnChild(ln, "custDash");
        } else {
            // Clear existing children
            while (custDash.hasChildNodes()) {
                custDash.removeChild(custDash.getFirstChild());
            }
        }
        Document doc = ln.getOwnerDocument();
        for (int i = 0; i < value.size() - 1; i += 2) {
            Element ds = doc.createElementNS(NS_A, "a:ds");
            ds.setAttribute("d", String.valueOf(Math.round(value.get(i) * PERCENTAGE_SCALE)));
            ds.setAttribute("sp", String.valueOf(Math.round(value.get(i + 1) * PERCENTAGE_SCALE)));
            custDash.appendChild(ds);
        }
        save();
    }

    @Override
    public LineCapStyle getCapStyle() {
        Element ln = getLn();
        if (ln == null) return LineCapStyle.NOT_DEFINED;
        String val = ln.getAttribute("cap");
        if (val == null || val.isEmpty()) return LineCapStyle.NOT_DEFINED;
        return CAP_MAP.getOrDefault(val, LineCapStyle.NOT_DEFINED);
    }

    @Override
    public void setCapStyle(LineCapStyle value) {
        Element ln = ensureLn();
        if (value == LineCapStyle.NOT_DEFINED) {
            ln.removeAttribute("cap");
        } else {
            String ooxmlVal = CAP_MAP_REV.get(value);
            if (ooxmlVal != null) ln.setAttribute("cap", ooxmlVal);
        }
        save();
    }

    @Override
    public LineStyle getStyle() {
        Element ln = getLn();
        if (ln == null) return LineStyle.NOT_DEFINED;
        String val = ln.getAttribute("cmpd");
        if (val == null || val.isEmpty()) return LineStyle.NOT_DEFINED;
        return CMPD_MAP.getOrDefault(val, LineStyle.NOT_DEFINED);
    }

    @Override
    public void setStyle(LineStyle value) {
        Element ln = ensureLn();
        if (value == LineStyle.NOT_DEFINED) {
            ln.removeAttribute("cmpd");
        } else {
            String ooxmlVal = CMPD_MAP_REV.get(value);
            if (ooxmlVal != null) ln.setAttribute("cmpd", ooxmlVal);
        }
        save();
    }

    @Override
    public LineAlignment getAlignment() {
        Element ln = getLn();
        if (ln == null) return LineAlignment.NOT_DEFINED;
        String val = ln.getAttribute("algn");
        if (val == null || val.isEmpty()) return LineAlignment.NOT_DEFINED;
        return ALGN_MAP.getOrDefault(val, LineAlignment.NOT_DEFINED);
    }

    @Override
    public void setAlignment(LineAlignment value) {
        Element ln = ensureLn();
        if (value == LineAlignment.NOT_DEFINED) {
            ln.removeAttribute("algn");
        } else {
            String ooxmlVal = ALGN_MAP_REV.get(value);
            if (ooxmlVal != null) ln.setAttribute("algn", ooxmlVal);
        }
        save();
    }

    @Override
    public LineJoinStyle getJoinStyle() {
        Element ln = getLn();
        if (ln == null) return LineJoinStyle.NOT_DEFINED;
        if (findChild(ln, "round") != null) return LineJoinStyle.ROUND;
        if (findChild(ln, "bevel") != null) return LineJoinStyle.BEVEL;
        if (findChild(ln, "miter") != null) return LineJoinStyle.MITER;
        return LineJoinStyle.NOT_DEFINED;
    }

    @Override
    public void setJoinStyle(LineJoinStyle value) {
        Element ln = ensureLn();
        removeChild(ln, "round");
        removeChild(ln, "bevel");
        removeChild(ln, "miter");
        switch (value) {
            case ROUND -> insertLnChild(ln, "round");
            case BEVEL -> insertLnChild(ln, "bevel");
            case MITER -> insertLnChild(ln, "miter");
            default -> { /* NOT_DEFINED: no element */ }
        }
        save();
    }

    @Override
    public double getMiterLimit() {
        Element ln = getLn();
        if (ln == null) return 0.0;
        Element miter = findChild(ln, "miter");
        if (miter == null) return 0.0;
        String lim = miter.getAttribute("lim");
        if (lim == null || lim.isEmpty()) return 0.0;
        return parseLongOrZero(lim) / PERCENTAGE_SCALE;
    }

    @Override
    public void setMiterLimit(double value) {
        Element ln = ensureLn();
        Element miter = findChild(ln, "miter");
        if (miter == null) {
            removeChild(ln, "round");
            removeChild(ln, "bevel");
            miter = insertLnChild(ln, "miter");
        }
        miter.setAttribute("lim", String.valueOf(Math.round(value * PERCENTAGE_SCALE)));
        save();
    }

    // --- Arrow helpers ---

    private String getArrowAttr(String endLocalName, String attr) {
        Element ln = getLn();
        if (ln == null) return null;
        Element endElem = findChild(ln, endLocalName);
        if (endElem == null) return null;
        String val = endElem.getAttribute(attr);
        return (val == null || val.isEmpty()) ? null : val;
    }

    private void setArrowAttr(String endLocalName, String attr, String value) {
        Element ln = ensureLn();
        Element endElem = findChild(ln, endLocalName);
        if (endElem == null) {
            endElem = insertLnChild(ln, endLocalName);
        }
        if (value == null) {
            endElem.removeAttribute(attr);
        } else {
            endElem.setAttribute(attr, value);
        }
        save();
    }

    // --- Arrowhead style ---

    @Override
    public LineArrowheadStyle getBeginArrowheadStyle() {
        String val = getArrowAttr("headEnd", "type");
        if (val == null) return LineArrowheadStyle.NOT_DEFINED;
        return ARROW_TYPE_MAP.getOrDefault(val, LineArrowheadStyle.NOT_DEFINED);
    }

    @Override
    public void setBeginArrowheadStyle(LineArrowheadStyle value) {
        if (value == LineArrowheadStyle.NOT_DEFINED) {
            setArrowAttr("headEnd", "type", null);
        } else {
            setArrowAttr("headEnd", "type", ARROW_TYPE_MAP_REV.get(value));
        }
    }

    @Override
    public LineArrowheadStyle getEndArrowheadStyle() {
        String val = getArrowAttr("tailEnd", "type");
        if (val == null) return LineArrowheadStyle.NOT_DEFINED;
        return ARROW_TYPE_MAP.getOrDefault(val, LineArrowheadStyle.NOT_DEFINED);
    }

    @Override
    public void setEndArrowheadStyle(LineArrowheadStyle value) {
        if (value == LineArrowheadStyle.NOT_DEFINED) {
            setArrowAttr("tailEnd", "type", null);
        } else {
            setArrowAttr("tailEnd", "type", ARROW_TYPE_MAP_REV.get(value));
        }
    }

    // --- Arrowhead width ---

    @Override
    public LineArrowheadWidth getBeginArrowheadWidth() {
        String val = getArrowAttr("headEnd", "w");
        if (val == null) return LineArrowheadWidth.NOT_DEFINED;
        return ARROW_W_MAP.getOrDefault(val, LineArrowheadWidth.NOT_DEFINED);
    }

    @Override
    public void setBeginArrowheadWidth(LineArrowheadWidth value) {
        if (value == LineArrowheadWidth.NOT_DEFINED) {
            setArrowAttr("headEnd", "w", null);
        } else {
            setArrowAttr("headEnd", "w", ARROW_W_MAP_REV.get(value));
        }
    }

    @Override
    public LineArrowheadWidth getEndArrowheadWidth() {
        String val = getArrowAttr("tailEnd", "w");
        if (val == null) return LineArrowheadWidth.NOT_DEFINED;
        return ARROW_W_MAP.getOrDefault(val, LineArrowheadWidth.NOT_DEFINED);
    }

    @Override
    public void setEndArrowheadWidth(LineArrowheadWidth value) {
        if (value == LineArrowheadWidth.NOT_DEFINED) {
            setArrowAttr("tailEnd", "w", null);
        } else {
            setArrowAttr("tailEnd", "w", ARROW_W_MAP_REV.get(value));
        }
    }

    // --- Arrowhead length ---

    @Override
    public LineArrowheadLength getBeginArrowheadLength() {
        String val = getArrowAttr("headEnd", "len");
        if (val == null) return LineArrowheadLength.NOT_DEFINED;
        return ARROW_LEN_MAP.getOrDefault(val, LineArrowheadLength.NOT_DEFINED);
    }

    @Override
    public void setBeginArrowheadLength(LineArrowheadLength value) {
        if (value == LineArrowheadLength.NOT_DEFINED) {
            setArrowAttr("headEnd", "len", null);
        } else {
            setArrowAttr("headEnd", "len", ARROW_LEN_MAP_REV.get(value));
        }
    }

    @Override
    public LineArrowheadLength getEndArrowheadLength() {
        String val = getArrowAttr("tailEnd", "len");
        if (val == null) return LineArrowheadLength.NOT_DEFINED;
        return ARROW_LEN_MAP.getOrDefault(val, LineArrowheadLength.NOT_DEFINED);
    }

    @Override
    public void setEndArrowheadLength(LineArrowheadLength value) {
        if (value == LineArrowheadLength.NOT_DEFINED) {
            setArrowAttr("tailEnd", "len", null);
        } else {
            setArrowAttr("tailEnd", "len", ARROW_LEN_MAP_REV.get(value));
        }
    }

    // --- Utility ---

    private static long parseLongOrZero(String s) {
        if (s == null || s.isEmpty()) return 0;
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
