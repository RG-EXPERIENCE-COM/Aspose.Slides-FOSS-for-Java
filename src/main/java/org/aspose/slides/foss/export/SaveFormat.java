package org.aspose.slides.foss.export;

/**
 * Constants which define the format of a saved presentation.
 */
public enum SaveFormat {
    /** Save presentation in PPT format. */
    PPT("Ppt"),
    /** Save presentation in PDF format. */
    PDF("Pdf"),
    /** Save presentation in XPS format. */
    XPS("Xps"),
    /** Save presentation in PPTX format. */
    PPTX("Pptx"),
    /** Save presentation in PPSX (slideshow) format. */
    PPSX("Ppsx"),
    /** Save presentation as multi-page TIFF image. */
    TIFF("Tiff"),
    /** Save presentation in ODP format. */
    ODP("Odp"),
    /** Save presentation in PPTM (macro-enabled presentation) format. */
    PPTM("Pptm"),
    /** Save presentation in PPSM (macro-enabled slideshow) format. */
    PPSM("Ppsm"),
    /** Save presentation in POTX (template) format. */
    POTX("Potx"),
    /** Save presentation in POTM (macro-enabled template) format. */
    POTM("Potm"),
    /** Save presentation in HTML format. */
    HTML("Html"),
    /** Save presentation in SWF format. */
    SWF("Swf"),
    /** Save presentation in OTP (presentation template) format. */
    OTP("Otp"),
    /** Save presentation in PPS format. */
    PPS("Pps"),
    /** Save presentation in POT format. */
    POT("Pot"),
    /** Save presentation in FODP format. */
    FODP("Fodp"),
    /** Save presentation in GIF format. */
    GIF("Gif"),
    /** Save presentation in HTML5 format using new HTML5 templating system. */
    HTML5("Html5"),
    /** Save presentation in Markdown format. */
    MD("Md"),
    /** Save presentation in PowerPoint XML Presentation format. */
    XML("Xml"),
    /** Save presentation in SVG format. */
    SVG("Svg"),
    /** Save presentation in JPEG format. */
    JPEG("Jpeg"),
    /** Save presentation in PNG format. */
    PNG("Png"),
    /** Save presentation in BMP format. */
    BMP("Bmp");

    private final String value;

    SaveFormat(String value) {
        this.value = value;
    }

    /**
     * Returns the string value of this constant.
     *
     * @return the string value
     */
    public String getValue() {
        return value;
    }
}
