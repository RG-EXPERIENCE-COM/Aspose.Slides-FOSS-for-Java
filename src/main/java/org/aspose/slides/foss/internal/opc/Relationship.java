package org.aspose.slides.foss.internal.opc;

import java.util.Optional;

/**
 * Represents a single relationship in an OPC {@code .rels} file.
 *
 * @param id         the relationship identifier (e.g., "rId1")
 * @param type       the relationship type URI
 * @param target     the target part path (relative to source)
 * @param targetMode optional target mode ("External" for external targets)
 */
public record Relationship(
        String id,
        String type,
        String target,
        Optional<String> targetMode
) {

    /**
     * Creates a relationship with no target mode.
     *
     * @param id     the relationship identifier
     * @param type   the relationship type URI
     * @param target the target part path
     */
    public Relationship(String id, String type, String target) {
        this(id, type, target, Optional.empty());
    }
}
