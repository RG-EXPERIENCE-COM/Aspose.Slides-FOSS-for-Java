package org.aspose.slides.foss;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Represents a connector shape.
 *
 * <p>A connector links two shapes via connection sites. It manages start/end shape
 * connections, connection site indices, and can recalculate its bounding box via
 * {@link #reroute()}.</p>
 */
public final class Connector extends GeometryShape implements IConnector {

    /**
     * Creates a Connector backed by the given XML element.
     *
     * @param xmlElement   the {@code <p:cxnSp>} XML element
     * @param saveCallback callback invoked after mutations; may be {@code null}
     */
    public Connector(Element xmlElement, Runnable saveCallback) {
        super(xmlElement, saveCallback);
    }

    /**
     * Creates a Connector with no backing element.
     */
    public Connector() {
        super();
    }

    /**
     * Returns the shape style.
     *
     * @return always {@code null} for connectors
     */
    @Override
    public IShapeStyle getShapeStyle() {
        return null;
    }

    // --- Internal helpers ---

    /**
     * Finds the {@code p:cNvCxnSpPr} element.
     */
    private Element getCNvCxnSpPr() {
        if (xmlElement == null) return null;
        Element nvCxnSpPr = findChild(xmlElement, NS_P, "nvCxnSpPr");
        if (nvCxnSpPr == null) return null;
        return findChild(nvCxnSpPr, NS_P, "cNvCxnSpPr");
    }

    /**
     * Gets or creates the {@code p:cNvCxnSpPr} element.
     */
    private Element ensureCNvCxnSpPr() {
        if (xmlElement == null) {
            throw new IllegalStateException("Connector has no XML element");
        }
        Element nvCxnSpPr = findChild(xmlElement, NS_P, "nvCxnSpPr");
        if (nvCxnSpPr == null) {
            throw new IllegalStateException("Connector has no nvCxnSpPr element");
        }
        return ensureChild(nvCxnSpPr, NS_P, "cNvCxnSpPr", "p:cNvCxnSpPr");
    }

    /**
     * Searches sibling shapes for a shape with the given {@code cNvPr@id}.
     */
    private IShape findShapeById(int shapeId) {
        if (parentShapes == null) return null;
        for (IShape shape : parentShapes) {
            Element cNvPr = shape.getCNvPr();
            if (cNvPr != null) {
                String idStr = cNvPr.getAttribute("id");
                if (idStr != null && !idStr.isEmpty()) {
                    try {
                        if (Integer.parseInt(idStr) == shapeId) {
                            return shape;
                        }
                    } catch (NumberFormatException ignored) {
                        // Non-numeric value; use default
                    }
                }
            }
        }
        return null;
    }

    /**
     * Returns the connection point (x, y) in points for a connection site on a shape.
     *
     * <p>4-site model, counter-clockwise from top (matches OOXML standard for preset shapes):
     * <ul>
     *   <li>0 = top-center</li>
     *   <li>1 = left-center</li>
     *   <li>2 = bottom-center</li>
     *   <li>3 = right-center</li>
     *   <li>fallback = shape center</li>
     * </ul>
     */
    private double[] getConnectionPoint(IShape shape, int siteIndex) {
        double x = shape.getX();
        double y = shape.getY();
        double w = shape.getWidth();
        double h = shape.getHeight();
        return switch (siteIndex) {
            case 0 -> new double[]{x + w / 2, y};           // top-center
            case 1 -> new double[]{x, y + h / 2};           // left-center
            case 2 -> new double[]{x + w / 2, y + h};       // bottom-center
            case 3 -> new double[]{x + w, y + h / 2};       // right-center
            default -> new double[]{x + w / 2, y + h / 2};  // center
        };
    }

    // --- IConnector properties ---

    @Override
    public IConnectorLock getConnectorLock() {
        return null;
    }

    @Override
    public IShape getStartShapeConnectedTo() {
        Element cNvCxnSpPr = getCNvCxnSpPr();
        if (cNvCxnSpPr == null) return null;
        Element stCxn = findChild(cNvCxnSpPr, NS_A, "stCxn");
        if (stCxn == null) return null;
        String shapeIdStr = stCxn.getAttribute("id");
        if (shapeIdStr == null || shapeIdStr.isEmpty()) return null;
        return findShapeById(Integer.parseInt(shapeIdStr));
    }

    @Override
    public void setStartShapeConnectedTo(IShape value) {
        if (value == null) {
            Element cNvCxnSpPr = getCNvCxnSpPr();
            if (cNvCxnSpPr != null) {
                Element stCxn = findChild(cNvCxnSpPr, NS_A, "stCxn");
                if (stCxn != null) {
                    cNvCxnSpPr.removeChild(stCxn);
                }
                if (saveCallback != null) saveCallback.run();
            }
            return;
        }
        Element cNvPr = value.getCNvPr();
        if (cNvPr == null) return;
        String shapeId = cNvPr.getAttribute("id");
        if (shapeId == null || shapeId.isEmpty()) return;

        Element cNvCxnSpPr = ensureCNvCxnSpPr();
        Element stCxn = findChild(cNvCxnSpPr, NS_A, "stCxn");
        if (stCxn == null) {
            stCxn = xmlElement.getOwnerDocument().createElementNS(NS_A, "a:stCxn");
            cNvCxnSpPr.appendChild(stCxn);
        }
        stCxn.setAttribute("id", shapeId);
        if (!stCxn.hasAttribute("idx")) {
            stCxn.setAttribute("idx", "0");
        }
        if (saveCallback != null) saveCallback.run();
        reroute();
    }

    @Override
    public IShape getEndShapeConnectedTo() {
        Element cNvCxnSpPr = getCNvCxnSpPr();
        if (cNvCxnSpPr == null) return null;
        Element endCxn = findChild(cNvCxnSpPr, NS_A, "endCxn");
        if (endCxn == null) return null;
        String shapeIdStr = endCxn.getAttribute("id");
        if (shapeIdStr == null || shapeIdStr.isEmpty()) return null;
        return findShapeById(Integer.parseInt(shapeIdStr));
    }

    @Override
    public void setEndShapeConnectedTo(IShape value) {
        if (value == null) {
            Element cNvCxnSpPr = getCNvCxnSpPr();
            if (cNvCxnSpPr != null) {
                Element endCxn = findChild(cNvCxnSpPr, NS_A, "endCxn");
                if (endCxn != null) {
                    cNvCxnSpPr.removeChild(endCxn);
                }
                if (saveCallback != null) saveCallback.run();
            }
            return;
        }
        Element cNvPr = value.getCNvPr();
        if (cNvPr == null) return;
        String shapeId = cNvPr.getAttribute("id");
        if (shapeId == null || shapeId.isEmpty()) return;

        Element cNvCxnSpPr = ensureCNvCxnSpPr();
        Element endCxn = findChild(cNvCxnSpPr, NS_A, "endCxn");
        if (endCxn == null) {
            endCxn = xmlElement.getOwnerDocument().createElementNS(NS_A, "a:endCxn");
            cNvCxnSpPr.appendChild(endCxn);
        }
        endCxn.setAttribute("id", shapeId);
        if (!endCxn.hasAttribute("idx")) {
            endCxn.setAttribute("idx", "0");
        }
        if (saveCallback != null) saveCallback.run();
        reroute();
    }

    @Override
    public int getStartShapeConnectionSiteIndex() {
        Element cNvCxnSpPr = getCNvCxnSpPr();
        if (cNvCxnSpPr == null) return 0;
        Element stCxn = findChild(cNvCxnSpPr, NS_A, "stCxn");
        if (stCxn == null) return 0;
        try {
            String idx = stCxn.getAttribute("idx");
            return (idx == null || idx.isEmpty()) ? 0 : Integer.parseInt(idx);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public void setStartShapeConnectionSiteIndex(int value) {
        Element cNvCxnSpPr = ensureCNvCxnSpPr();
        Element stCxn = findChild(cNvCxnSpPr, NS_A, "stCxn");
        if (stCxn == null) {
            stCxn = xmlElement.getOwnerDocument().createElementNS(NS_A, "a:stCxn");
            stCxn.setAttribute("id", "0");
            cNvCxnSpPr.appendChild(stCxn);
        }
        stCxn.setAttribute("idx", String.valueOf(value));
        if (saveCallback != null) saveCallback.run();
        reroute();
    }

    @Override
    public int getEndShapeConnectionSiteIndex() {
        Element cNvCxnSpPr = getCNvCxnSpPr();
        if (cNvCxnSpPr == null) return 0;
        Element endCxn = findChild(cNvCxnSpPr, NS_A, "endCxn");
        if (endCxn == null) return 0;
        try {
            String idx = endCxn.getAttribute("idx");
            return (idx == null || idx.isEmpty()) ? 0 : Integer.parseInt(idx);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public void setEndShapeConnectionSiteIndex(int value) {
        Element cNvCxnSpPr = ensureCNvCxnSpPr();
        Element endCxn = findChild(cNvCxnSpPr, NS_A, "endCxn");
        if (endCxn == null) {
            endCxn = xmlElement.getOwnerDocument().createElementNS(NS_A, "a:endCxn");
            endCxn.setAttribute("id", "0");
            cNvCxnSpPr.appendChild(endCxn);
        }
        endCxn.setAttribute("idx", String.valueOf(value));
        if (saveCallback != null) saveCallback.run();
        reroute();
    }

    @Override
    public IGeometryShape getAsIGeometryShape() {
        return this;
    }

    @Override
    public void reroute() {
        IShape startShape = getStartShapeConnectedTo();
        IShape endShape = getEndShapeConnectedTo();
        if (startShape == null && endShape == null) return;

        int startIdx = getStartShapeConnectionSiteIndex();
        int endIdx = getEndShapeConnectionSiteIndex();

        double sx, sy;
        if (startShape != null) {
            double[] pt = getConnectionPoint(startShape, startIdx);
            sx = pt[0];
            sy = pt[1];
        } else {
            sx = getX();
            sy = getY();
        }

        double ex, ey;
        if (endShape != null) {
            double[] pt = getConnectionPoint(endShape, endIdx);
            ex = pt[0];
            ey = pt[1];
        } else {
            ex = getX() + getWidth();
            ey = getY() + getHeight();
        }

        double newX = Math.min(sx, ex);
        double newY = Math.min(sy, ey);
        double newW = Math.abs(ex - sx);
        double newH = Math.abs(ey - sy);
        boolean flipH = sx > ex;
        boolean flipV = sy > ey;

        Element xfrm = ensureXfrm();
        Element off = ensureChild(xfrm, NS_A, "off", "a:off");
        Element ext = ensureChild(xfrm, NS_A, "ext", "a:ext");

        off.setAttribute("x", String.valueOf(Math.round(newX * EMU_PER_POINT)));
        off.setAttribute("y", String.valueOf(Math.round(newY * EMU_PER_POINT)));
        ext.setAttribute("cx", String.valueOf(Math.round(newW * EMU_PER_POINT)));
        ext.setAttribute("cy", String.valueOf(Math.round(newH * EMU_PER_POINT)));

        if (flipH) {
            xfrm.setAttribute("flipH", "1");
        } else {
            xfrm.removeAttribute("flipH");
        }

        if (flipV) {
            xfrm.setAttribute("flipV", "1");
        } else {
            xfrm.removeAttribute("flipV");
        }

        if (saveCallback != null) saveCallback.run();
    }
}
