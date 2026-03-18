package org.aspose.slides.foss;

/**
 * Represents a component of a presentation.
 */
public interface IPresentationComponent {

    /**
     * Returns the presentation containing this component.
     * Read-only {@link IPresentation}.
     *
     * @return the parent presentation
     */
    IPresentation getPresentation();
}
