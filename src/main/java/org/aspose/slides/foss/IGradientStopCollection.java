package org.aspose.slides.foss;

import org.aspose.slides.foss.drawing.Color;

import java.util.List;

/**
 * Represents a collection of gradient stops.
 */
public interface IGradientStopCollection {

    /**
     * Gets the gradient stop at the specified index.
     *
     * @param index the zero-based index
     * @return the gradient stop
     */
    IGradientStop get(int index);

    /**
     * Returns the number of gradient stops.
     *
     * @return the count
     */
    int size();

    /**
     * Creates and adds a new gradient stop with the specified position and color.
     *
     * @param position the position (0..1)
     * @param color    the color
     * @return the new gradient stop
     */
    IGradientStop add(double position, Color color);

    /**
     * Creates and adds a new gradient stop with the specified position and preset color.
     *
     * @param position    the position (0..1)
     * @param presetColor the preset color
     * @return the new gradient stop
     */
    IGradientStop add(double position, PresetColor presetColor);

    /**
     * Creates and adds a new gradient stop with the specified position and scheme color.
     *
     * @param position    the position (0..1)
     * @param schemeColor the scheme color
     * @return the new gradient stop
     */
    IGradientStop add(double position, SchemeColor schemeColor);

    /**
     * Inserts a new gradient stop at the specified index with the given position and color.
     *
     * @param index    the zero-based index at which to insert
     * @param position the position (0..1)
     * @param color    the color
     */
    void insert(int index, double position, Color color);

    /**
     * Inserts a new gradient stop at the specified index with the given position and preset color.
     *
     * @param index       the zero-based index at which to insert
     * @param position    the position (0..1)
     * @param presetColor the preset color
     */
    void insert(int index, double position, PresetColor presetColor);

    /**
     * Inserts a new gradient stop at the specified index with the given position and scheme color.
     *
     * @param index       the zero-based index at which to insert
     * @param position    the position (0..1)
     * @param schemeColor the scheme color
     */
    void insert(int index, double position, SchemeColor schemeColor);

    /**
     * Removes the gradient stop at the specified index.
     *
     * @param index the zero-based index
     */
    void removeAt(int index);

    /**
     * Removes all gradient stops.
     */
    void clear();

    /**
     * Returns all gradient stops as a list.
     *
     * @return a list of gradient stops
     */
    List<IGradientStop> asICollection();

    /**
     * Returns all gradient stops as an iterable.
     *
     * @return an iterable of gradient stops
     */
    Iterable<IGradientStop> asIEnumerable();
}
