package org.aspose.slides.foss.internal.pptx;

import org.aspose.slides.foss.AutoShape;
import org.aspose.slides.foss.Connector;
import org.aspose.slides.foss.GroupShape;
import org.aspose.slides.foss.IBaseSlide;
import org.aspose.slides.foss.IShape;
import org.aspose.slides.foss.PictureFrame;
import org.aspose.slides.foss.Shape;
import org.aspose.slides.foss.Table;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.Optional;

/**
 * Factory for creating the correct shape type from OOXML elements.
 *
 * <p>Inspects the tag name (local name + namespace) of a shape XML element
 * and returns an appropriately typed {@link IShape} wrapper.  For
 * {@code <p:graphicFrame>} elements the factory further inspects the
 * {@code <a:graphicData>} URI to distinguish tables, charts, and SmartArt.</p>
 */
public final class ShapeFactory {

    private static final String NS_A =
            PptxConstants.NAMESPACES.get("a");

    private ShapeFactory() {
        // utility class
    }

    /**
     * Creates the appropriate shape object based on the XML element type.
     *
     * <p>Maps element local names to shape types:
     * <ul>
     *   <li>{@code sp} &rarr; {@link AutoShape}</li>
     *   <li>{@code pic} &rarr; {@link PictureFrame}</li>
     *   <li>{@code grpSp} &rarr; {@link GroupShape}</li>
     *   <li>{@code cxnSp} &rarr; {@link Connector}</li>
     *   <li>{@code graphicFrame} &rarr; delegates to {@link #createGraphicalObject}</li>
     * </ul>
     *
     * @param xmlElement  the XML element representing the shape
     * @param slidePart   the SlidePart context (reserved for future use)
     * @param parentSlide the parent slide object
     * @return the shape instance, or {@link Optional#empty()} if the element is unrecognised
     */
    public static Optional<IShape> createShape(Element xmlElement,
                                                SlidePart slidePart,
                                                IBaseSlide parentSlide) {
        if (xmlElement == null) {
            return Optional.empty();
        }

        String localName = xmlElement.getLocalName();
        if (localName == null) {
            return Optional.empty();
        }

        return switch (localName) {
            case "sp" -> {
                var shape = new AutoShape(xmlElement, null);
                yield Optional.of(shape);
            }
            case "pic" -> {
                var shape = new PictureFrame(xmlElement, null);
                yield Optional.of(shape);
            }
            case "grpSp" -> {
                var shape = new GroupShape();
                shape.initInternal(xmlElement, null, null, null, parentSlide);
                yield Optional.of(shape);
            }
            case "cxnSp" -> {
                var shape = new Connector(xmlElement, null);
                yield Optional.of(shape);
            }
            case "graphicFrame" -> createGraphicalObject(xmlElement, slidePart, parentSlide);
            default -> Optional.empty();
        };
    }

    /**
     * Creates the appropriate graphical object (Table, Chart, SmartArt)
     * from a {@code <p:graphicFrame>} element.
     *
     * <p>Locates the {@code <a:graphicData>} child and inspects its
     * {@code uri} attribute to determine the concrete type:</p>
     * <ul>
     *   <li>URI containing {@code "table"} &rarr; {@link Table}</li>
     *   <li>URI containing {@code "chart"} &rarr; unsupported, returns empty</li>
     *   <li>URI containing {@code "smartart"} or {@code "diagram"} &rarr; unsupported, returns empty</li>
     * </ul>
     *
     * @param xmlElement  the {@code <p:graphicFrame>} XML element
     * @param slidePart   the SlidePart context (reserved for future use)
     * @param parentSlide the parent slide object
     * @return the shape instance, or {@link Optional#empty()} if the content type is unknown or unsupported
     */
    public static Optional<IShape> createGraphicalObject(Element xmlElement,
                                                          SlidePart slidePart,
                                                          IBaseSlide parentSlide) {
        if (xmlElement == null) {
            return Optional.empty();
        }

        // Find the graphicData element
        Element graphicData = findDescendant(xmlElement, NS_A, "graphicData");
        if (graphicData == null) {
            return Optional.empty();
        }

        String uri = graphicData.getAttribute("uri");
        if (uri == null || uri.isEmpty()) {
            return Optional.empty();
        }

        String uriLower = uri.toLowerCase(java.util.Locale.ROOT);

        if (uriLower.contains("table")) {
            var table = new Table();
            table.initInternal(xmlElement, null, parentSlide);
            return Optional.of(table);
        } else if (uriLower.contains("chart")) {
            // Charts are not supported
            return Optional.empty();
        } else if (uriLower.contains("smartart") || uriLower.contains("diagram")) {
            // SmartArt is not supported
            return Optional.empty();
        }

        return Optional.empty();
    }

    // ── Private helpers ─────────────────────────────────────────────────

    /**
     * Finds the first descendant element matching the given namespace and local name.
     */
    private static Element findDescendant(Element parent, String nsUri, String localName) {
        NodeList nodes = parent.getElementsByTagNameNS(nsUri, localName);
        if (nodes.getLength() > 0) {
            return (Element) nodes.item(0);
        }
        return null;
    }
}
