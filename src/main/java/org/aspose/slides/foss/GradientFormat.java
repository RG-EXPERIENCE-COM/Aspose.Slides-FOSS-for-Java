package org.aspose.slides.foss;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.Map;

/**
 * Represents a gradient format.
 *
 * <p>Wraps an OOXML {@code <a:gradFill>} element.</p>
 */
public final class GradientFormat extends PVIObject implements IGradientFormat, IFillParamSource {

    private static final String NS_A = "http://schemas.openxmlformats.org/drawingml/2006/main";

    private static final Map<String, String> FLIP_MAP = Map.of(
            "none", "NO_FLIP", "x", "FLIP_X", "y", "FLIP_Y", "xy", "FLIP_BOTH"
    );
    private static final Map<String, String> FLIP_MAP_REV = Map.of(
            "NO_FLIP", "none", "FLIP_X", "x", "FLIP_Y", "y", "FLIP_BOTH", "xy"
    );

    private Element gradFill;
    private Runnable saveCallback;

    /**
     * Creates a new uninitialized GradientFormat.
     *
     * <p>Call {@link #initInternal(Element, Runnable, IBaseSlide)} to complete initialization.</p>
     */
    public GradientFormat() {
    }

    /**
     * Creates a new GradientFormat backed by the given {@code <a:gradFill>} element.
     *
     * @param gradFill     the gradFill XML element
     * @param saveCallback callback invoked after mutations; may be {@code null}
     */
    public GradientFormat(Element gradFill, Runnable saveCallback) {
        this.gradFill = gradFill;
        this.saveCallback = saveCallback;
    }

    /**
     * Internal initialization.
     *
     * @param gradFillElement the {@code <a:gradFill>} XML element
     * @param saveCallback    callback invoked after mutations to persist changes; may be {@code null}
     * @param parentSlide     the parent slide object
     */
    public void initInternal(Element gradFillElement, Runnable saveCallback, IBaseSlide parentSlide) {
        this.gradFill = gradFillElement;
        this.saveCallback = saveCallback;
        this.parentSlide = parentSlide;
    }

    /**
     * Persists changes by invoking the save callback, if one was provided.
     */
    void save() {
        if (saveCallback != null) saveCallback.run();
    }

    private Element findChild(String localName) {
        NodeList children = gradFill.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i) instanceof Element el
                    && NS_A.equals(el.getNamespaceURI())
                    && localName.equals(el.getLocalName())) {
                return el;
            }
        }
        return null;
    }

    private void removeChild(String localName) {
        Element el = findChild(localName);
        if (el != null) gradFill.removeChild(el);
    }

    @Override
    public TileFlip getTileFlip() {
        String val = gradFill.getAttribute("flip");
        if (val == null || val.isEmpty()) return TileFlip.NOT_DEFINED;
        String name = FLIP_MAP.get(val);
        if (name == null) return TileFlip.NOT_DEFINED;
        return TileFlip.valueOf(name);
    }

    @Override
    public void setTileFlip(TileFlip value) {
        if (value == TileFlip.NOT_DEFINED) {
            gradFill.removeAttribute("flip");
        } else {
            String ooxmlVal = FLIP_MAP_REV.get(value.name());
            if (ooxmlVal != null) gradFill.setAttribute("flip", ooxmlVal);
        }
        save();
    }

    @Override
    public GradientDirection getGradientDirection() {
        Element lin = findChild("lin");
        if (lin != null) {
            String angStr = lin.getAttribute("ang");
            long ang = (angStr != null && !angStr.isEmpty()) ? Long.parseLong(angStr) : 0;
            return switch ((int) ang) {
                case 0 -> GradientDirection.FROM_CORNER1;
                case 5400000 -> GradientDirection.FROM_CORNER2;
                case 10800000 -> GradientDirection.FROM_CORNER4;
                case 16200000 -> GradientDirection.FROM_CORNER3;
                default -> GradientDirection.NOT_DEFINED;
            };
        }
        if (findChild("path") != null) {
            return GradientDirection.FROM_CENTER;
        }
        return GradientDirection.NOT_DEFINED;
    }

    @Override
    public void setGradientDirection(GradientDirection value) {
        if (value == GradientDirection.NOT_DEFINED) return;
        if (value == GradientDirection.FROM_CENTER) {
            removeChild("lin");
            if (findChild("path") == null) {
                Document doc = gradFill.getOwnerDocument();
                Element path = doc.createElementNS(NS_A, "a:path");
                path.setAttribute("path", "circle");
                gradFill.appendChild(path);
            }
        } else {
            removeChild("path");
            int ang = switch (value) {
                case FROM_CORNER1 -> 0;
                case FROM_CORNER2 -> 5400000;
                case FROM_CORNER3 -> 16200000;
                case FROM_CORNER4 -> 10800000;
                default -> 0;
            };
            Element lin = findChild("lin");
            if (lin == null) {
                Document doc = gradFill.getOwnerDocument();
                lin = doc.createElementNS(NS_A, "a:lin");
                gradFill.appendChild(lin);
            }
            lin.setAttribute("ang", String.valueOf(ang));
        }
        save();
    }

    @Override
    public double getLinearGradientAngle() {
        Element lin = findChild("lin");
        if (lin == null) return 0.0;
        String angStr = lin.getAttribute("ang");
        if (angStr == null || angStr.isEmpty()) return 0.0;
        return Long.parseLong(angStr) / 60000.0;
    }

    @Override
    public void setLinearGradientAngle(double value) {
        Element lin = findChild("lin");
        if (lin == null) {
            Document doc = gradFill.getOwnerDocument();
            lin = doc.createElementNS(NS_A, "a:lin");
            gradFill.appendChild(lin);
        }
        lin.setAttribute("ang", String.valueOf(Math.round(value * 60000)));
        save();
    }

    @Override
    public NullableBool getLinearGradientScaled() {
        Element lin = findChild("lin");
        if (lin == null) return NullableBool.NOT_DEFINED;
        String val = lin.getAttribute("scaled");
        if (val == null || val.isEmpty()) return NullableBool.NOT_DEFINED;
        return "1".equals(val) ? NullableBool.TRUE : NullableBool.FALSE;
    }

    @Override
    public void setLinearGradientScaled(NullableBool value) {
        Element lin = findChild("lin");
        if (lin == null) {
            Document doc = gradFill.getOwnerDocument();
            lin = doc.createElementNS(NS_A, "a:lin");
            gradFill.appendChild(lin);
        }
        if (value == NullableBool.NOT_DEFINED) {
            lin.removeAttribute("scaled");
        } else {
            lin.setAttribute("scaled", value == NullableBool.TRUE ? "1" : "0");
        }
        save();
    }

    @Override
    public GradientShape getGradientShape() {
        if (findChild("lin") != null) return GradientShape.LINEAR;
        Element path = findChild("path");
        if (path != null) {
            String pathVal = path.getAttribute("path");
            return switch (pathVal != null ? pathVal : "") {
                case "rect" -> GradientShape.RECTANGLE;
                case "circle" -> GradientShape.RADIAL;
                case "shape" -> GradientShape.PATH;
                default -> GradientShape.NOT_DEFINED;
            };
        }
        return GradientShape.NOT_DEFINED;
    }

    @Override
    public void setGradientShape(GradientShape value) {
        if (value == GradientShape.NOT_DEFINED) return;
        removeChild("lin");
        removeChild("path");
        Document doc = gradFill.getOwnerDocument();
        if (value == GradientShape.LINEAR) {
            Element lin = doc.createElementNS(NS_A, "a:lin");
            lin.setAttribute("ang", "0");
            lin.setAttribute("scaled", "1");
            gradFill.appendChild(lin);
        } else {
            String pathVal = switch (value) {
                case RECTANGLE -> "rect";
                case RADIAL -> "circle";
                case PATH -> "shape";
                default -> "rect";
            };
            Element path = doc.createElementNS(NS_A, "a:path");
            path.setAttribute("path", pathVal);
            gradFill.appendChild(path);
        }
        save();
    }

    @Override
    public IGradientStopCollection getGradientStops() {
        Element gsLst = findChild("gsLst");
        if (gsLst == null) {
            Document doc = gradFill.getOwnerDocument();
            gsLst = doc.createElementNS(NS_A, "a:gsLst");
            // gsLst should come first
            if (gradFill.getFirstChild() != null) {
                gradFill.insertBefore(gsLst, gradFill.getFirstChild());
            } else {
                gradFill.appendChild(gsLst);
            }
        }
        return new GradientStopCollection(gsLst, saveCallback);
    }
}
