package org.aspose.slides.foss;

/**
 * Represents a connector.
 */
public interface IConnector extends IGeometryShape {

    /**
     * Returns connector's locks. Read-only.
     *
     * @return the connector lock, or null
     */
    IConnectorLock getConnectorLock();

    /**
     * Returns the shape to attach the beginning of the connector to. Read/write.
     *
     * @return the start shape, or null
     */
    IShape getStartShapeConnectedTo();

    /**
     * Sets the shape to attach the beginning of the connector to.
     *
     * @param value the start shape, or null to disconnect
     */
    void setStartShapeConnectedTo(IShape value);

    /**
     * Returns the shape to attach the end of the connector to. Read/write.
     *
     * @return the end shape, or null
     */
    IShape getEndShapeConnectedTo();

    /**
     * Sets the shape to attach the end of the connector to.
     *
     * @param value the end shape, or null to disconnect
     */
    void setEndShapeConnectedTo(IShape value);

    /**
     * Returns the index of connection site for start shape. Read/write.
     *
     * @return the connection site index
     */
    int getStartShapeConnectionSiteIndex();

    /**
     * Sets the index of connection site for start shape.
     *
     * @param value the connection site index
     */
    void setStartShapeConnectionSiteIndex(int value);

    /**
     * Returns the index of connection site for end shape. Read/write.
     *
     * @return the connection site index
     */
    int getEndShapeConnectionSiteIndex();

    /**
     * Sets the index of connection site for end shape.
     *
     * @param value the connection site index
     */
    void setEndShapeConnectionSiteIndex(int value);

    /**
     * Allows to get base IGeometryShape interface. Read-only.
     *
     * @return this connector as IGeometryShape
     */
    IGeometryShape getAsIGeometryShape();

    /**
     * Recalculates connector bounding box based on connected shapes.
     */
    void reroute();
}
