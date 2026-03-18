package org.aspose.slides.foss;

/**
 * Base exception for PPT-related errors.
 */
public class PptException extends RuntimeException {

    /**
     * Creates a new {@code PptException} with no detail message.
     */
    public PptException() {
    }

    /**
     * Creates a new {@code PptException} with the specified detail message.
     *
     * @param message the detail message
     */
    public PptException(String message) {
        super(message);
    }

    /**
     * Creates a new {@code PptException} with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of this exception
     */
    public PptException(String message, Throwable cause) {
        super(message, cause);
    }
}
