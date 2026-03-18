package org.aspose.slides.foss;

/**
 * Exception thrown when a PPT file is corrupt and cannot be processed.
 */
public final class PptCorruptFileException extends PptReadException {

    /**
     * Creates a new {@code PptCorruptFileException} with no detail message.
     */
    public PptCorruptFileException() {
    }

    /**
     * Creates a new {@code PptCorruptFileException} with the specified detail message.
     *
     * @param message the detail message
     */
    public PptCorruptFileException(String message) {
        super(message);
    }

    /**
     * Creates a new {@code PptCorruptFileException} with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of this exception
     */
    public PptCorruptFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
