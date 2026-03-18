package org.aspose.slides.foss;

/**
 * Base class for shape locks. Provides a common type for all shape lock implementations.
 */
public class BaseShapeLock {

    /**
     * Returns {@code true} if all lock properties are disabled (no restrictions are set).
     *
     * <p>Subclasses should override this method to inspect their specific lock attributes.</p>
     *
     * @return {@code true} if no locks are active
     */
    public boolean noLocks() {
        return true;
    }
}
