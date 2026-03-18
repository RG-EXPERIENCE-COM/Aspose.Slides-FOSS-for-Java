package org.aspose.slides.foss;

import org.aspose.slides.foss.drawing.SizeF;

/**
 * Represents a size of notes slide.
 */
public interface INotesSize {

    /**
     * Returns the size of the notes slide in points.
     * Read/write {@link SizeF}.
     *
     * @return the notes slide size
     */
    SizeF getSize();

    /**
     * Sets the size of the notes slide in points.
     *
     * @param value the new notes slide size
     */
    void setSize(SizeF value);
}
