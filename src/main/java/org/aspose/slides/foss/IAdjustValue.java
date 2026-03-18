package org.aspose.slides.foss;

/**
 * Represents a geometry shape adjustment value.
 */
public interface IAdjustValue {

    /**
     * Gets the name of the adjustment (e.g. "adj1").
     *
     * @return the adjustment name
     */
    String getName();

    /**
     * Gets the raw adjustment value.
     *
     * @return the raw value
     */
    long getRawValue();

    /**
     * Sets the raw adjustment value.
     *
     * @param value the raw value
     */
    void setRawValue(long value);

    /**
     * Gets the adjustment value interpreted as an angle in degrees.
     * The conversion is {@code rawValue / 60000.0}.
     *
     * @return the angle value in degrees
     */
    double getAngleValue();

    /**
     * Sets the adjustment value by interpreting the given angle in degrees.
     * The conversion is {@code Math.round(value * 60000.0)}.
     *
     * @param value the angle value in degrees
     */
    void setAngleValue(double value);
}
