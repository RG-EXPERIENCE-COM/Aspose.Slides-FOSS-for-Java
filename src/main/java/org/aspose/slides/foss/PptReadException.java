package org.aspose.slides.foss;

/**
 * Exception thrown when a PPT file cannot be read.
 */
public class PptReadException extends PptException {

    /**
     * Creates a new {@code PptReadException} with no detail message.
     */
    public PptReadException() {
    }

    /**
     * Creates a new {@code PptReadException} with the specified detail message.
     *
     * @param message the detail message
     */
    public PptReadException(String message) {
        super(message);
    }

    /**
     * Creates a new {@code PptReadException} with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of this exception
     */
    public PptReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
