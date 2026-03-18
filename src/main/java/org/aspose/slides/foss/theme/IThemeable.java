package org.aspose.slides.foss.theme;

import org.aspose.slides.foss.IPresentation;
import org.aspose.slides.foss.IPresentationComponent;
import org.aspose.slides.foss.ISlideComponent;

/**
 * Represents objects that can be themed.
 */
public interface IThemeable extends ISlideComponent, IPresentationComponent {

    /**
     * Returns the presentation containing this themeable object.
     * Read-only {@link IPresentation}.
     *
     * @return the parent presentation
     */
    @Override
    IPresentation getPresentation();
}
