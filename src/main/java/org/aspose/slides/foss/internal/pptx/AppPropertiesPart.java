package org.aspose.slides.foss.internal.pptx;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Parses and serializes {@code docProps/app.xml} (extended properties).
 *
 * <p>Handles: Application, AppVersion, Company, Manager, PresentationFormat,
 * Template, TotalTime, Slides, HiddenSlides, Notes, Paragraphs, Words,
 * MMClips, ScaleCrop, LinksUpToDate, SharedDoc, HyperlinksChanged,
 * HyperlinkBase, HeadingPairs, TitlesOfParts.</p>
 */
public final class AppPropertiesPart {

    /** The part name within the OPC package. */
    public static final String partName = "docProps/app.xml";

    private static final String NS_EP =
            "http://schemas.openxmlformats.org/officeDocument/2006/extended-properties";
    private static final String NS_VT =
            "http://schemas.openxmlformats.org/officeDocument/2006/docPropsVTypes";

    private final OpcPackage pkg;
    private Element root;
    private boolean dirty;

    // String properties
    private String application;
    private String appVersion;
    private String company;
    private String manager;
    private String presentationFormat;
    private String template;
    private String hyperlinkBase;

    // Integer properties
    private Integer totalTime;
    private Integer slides;
    private Integer hiddenSlides;
    private Integer notes;
    private Integer paragraphs;
    private Integer words;
    private Integer mmClips;

    // Boolean properties
    private Boolean scaleCrop;
    private Boolean linksUpToDate;
    private Boolean sharedDoc;
    private Boolean hyperlinksChanged;

    // Vector properties
    private List<HeadingPairData> headingPairs = new ArrayList<>();
    private List<String> titlesOfParts = new ArrayList<>();

    /**
     * Creates a part backed by the given package and parses existing content.
     *
     * @param pkg the OPC package
     */
    public AppPropertiesPart(OpcPackage pkg) {
        this.pkg = pkg;
        parse();
    }

    /**
     * Parses the XML content from the package into field values.
     */
    public void parse() {
        byte[] data = pkg.getPartBytes(partName);
        if (data == null) {
            root = null;
            return;
        }

        Document doc = pkg.parseXml(partName);
        if (doc == null) {
            root = null;
            return;
        }
        root = doc.getDocumentElement();

        application = getText("Application");
        appVersion = getText("AppVersion");
        company = getText("Company");
        manager = getText("Manager");
        presentationFormat = getText("PresentationFormat");
        template = getText("Template");
        hyperlinkBase = getText("HyperlinkBase");

        totalTime = getInt("TotalTime");
        slides = getInt("Slides");
        hiddenSlides = getInt("HiddenSlides");
        notes = getInt("Notes");
        paragraphs = getInt("Paragraphs");
        words = getInt("Words");
        mmClips = getInt("MMClips");

        scaleCrop = getBool("ScaleCrop");
        linksUpToDate = getBool("LinksUpToDate");
        sharedDoc = getBool("SharedDoc");
        hyperlinksChanged = getBool("HyperlinksChanged");

        parseHeadingPairs();
        parseTitlesOfParts();
    }

    // ---- XML read helpers ----

    /**
     * Returns the text content of the element with the given local name,
     * or {@code null} if not found or empty.
     *
     * @param localName the element local name
     * @return the text content, or {@code null}
     */
    public String getText(String localName) {
        if (root == null) {
            return null;
        }
        NodeList list = root.getElementsByTagNameNS(NS_EP, localName);
        if (list.getLength() == 0) {
            list = root.getElementsByTagName(localName);
        }
        if (list.getLength() > 0) {
            String text = list.item(0).getTextContent();
            if (text != null && !text.isEmpty()) {
                return text;
            }
        }
        return null;
    }

    /**
     * Returns the integer value of the element with the given local name,
     * or {@code null} if not found or not a valid integer.
     *
     * @param localName the element local name
     * @return the integer value, or {@code null}
     */
    public Integer getInt(String localName) {
        String text = getText(localName);
        if (text != null) {
            try {
                return Integer.parseInt(text.strip());
            } catch (NumberFormatException e) {
                // fall through
            }
        }
        return null;
    }

    /**
     * Returns the boolean value of the element with the given local name,
     * or {@code null} if not found. Recognizes {@code "true"} and {@code "1"} as true.
     *
     * @param localName the element local name
     * @return the boolean value, or {@code null}
     */
    public Boolean getBool(String localName) {
        String text = getText(localName);
        if (text != null) {
            String lower = text.strip().toLowerCase(java.util.Locale.ROOT);
            return "true".equals(lower) || "1".equals(lower);
        }
        return null;
    }

    /**
     * Parses HeadingPairs from the {@code vt:vector} element.
     */
    public void parseHeadingPairs() {
        headingPairs.clear();
        if (root == null) {
            return;
        }
        NodeList hpList = root.getElementsByTagNameNS(NS_EP, "HeadingPairs");
        if (hpList.getLength() == 0) {
            return;
        }
        Element hpEl = (Element) hpList.item(0);
        NodeList vectors = hpEl.getElementsByTagNameNS(NS_VT, "vector");
        if (vectors.getLength() == 0) {
            return;
        }
        Element vector = (Element) vectors.item(0);
        NodeList variants = vector.getElementsByTagNameNS(NS_VT, "variant");

        int i = 0;
        while (i + 1 < variants.getLength()) {
            Element nameVariant = (Element) variants.item(i);
            Element countVariant = (Element) variants.item(i + 1);

            NodeList nameEls = nameVariant.getElementsByTagNameNS(NS_VT, "lpstr");
            NodeList countEls = countVariant.getElementsByTagNameNS(NS_VT, "i4");

            if (nameEls.getLength() > 0 && countEls.getLength() > 0) {
                String name = nameEls.item(0).getTextContent();
                if (name == null) name = "";
                int count;
                try {
                    count = Integer.parseInt(countEls.item(0).getTextContent());
                } catch (NumberFormatException | NullPointerException e) {
                    count = 0;
                }
                headingPairs.add(new HeadingPairData(name, count));
            }
            i += 2;
        }
    }

    /**
     * Parses TitlesOfParts from the {@code vt:vector} element.
     */
    public void parseTitlesOfParts() {
        titlesOfParts.clear();
        if (root == null) {
            return;
        }
        NodeList tpList = root.getElementsByTagNameNS(NS_EP, "TitlesOfParts");
        if (tpList.getLength() == 0) {
            return;
        }
        Element tpEl = (Element) tpList.item(0);
        NodeList vectors = tpEl.getElementsByTagNameNS(NS_VT, "vector");
        if (vectors.getLength() == 0) {
            return;
        }
        Element vector = (Element) vectors.item(0);
        NodeList strings = vector.getElementsByTagNameNS(NS_VT, "lpstr");
        for (int i = 0; i < strings.getLength(); i++) {
            String text = strings.item(i).getTextContent();
            titlesOfParts.add(text != null ? text : "");
        }
    }

    // ---- dirty tracking ----

    /** Marks this part as dirty so it will be serialized on next save. */
    public void markDirty() {
        dirty = true;
    }

    // ---- save / serialize ----

    /**
     * Serializes the properties back to the package.
     *
     * <p>If the part is not dirty and was previously parsed, this is a no-op.</p>
     */
    public void save() {
        if (!dirty && root != null) {
            return;
        }

        Document doc = OpcPackage.newDocument();
        Element newRoot = doc.createElementNS(NS_EP, "Properties");
        newRoot.setAttribute("xmlns:vt", NS_VT);
        doc.appendChild(newRoot);

        setText(doc, newRoot, "Application", application);
        setText(doc, newRoot, "AppVersion", appVersion);
        setText(doc, newRoot, "Company", company);
        setText(doc, newRoot, "Manager", manager);
        setText(doc, newRoot, "PresentationFormat", presentationFormat);
        setText(doc, newRoot, "Template", template);
        setText(doc, newRoot, "HyperlinkBase", hyperlinkBase);

        setInt(doc, newRoot, "TotalTime", totalTime);
        setInt(doc, newRoot, "Slides", slides);
        setInt(doc, newRoot, "HiddenSlides", hiddenSlides);
        setInt(doc, newRoot, "Notes", notes);
        setInt(doc, newRoot, "Paragraphs", paragraphs);
        setInt(doc, newRoot, "Words", words);
        setInt(doc, newRoot, "MMClips", mmClips);

        setBool(doc, newRoot, "ScaleCrop", scaleCrop);
        setBool(doc, newRoot, "LinksUpToDate", linksUpToDate);
        setBool(doc, newRoot, "SharedDoc", sharedDoc);
        setBool(doc, newRoot, "HyperlinksChanged", hyperlinksChanged);

        if (!headingPairs.isEmpty()) {
            writeHeadingPairs(doc, newRoot);
        }
        if (!titlesOfParts.isEmpty()) {
            writeTitlesOfParts(doc, newRoot);
        }

        pkg.serializeXml(partName, doc);
        dirty = false;
    }

    // ---- XML write helpers ----

    /**
     * Writes a text element as a child of the given parent, if value is non-null.
     *
     * @param doc       the owning document
     * @param parent    the parent element
     * @param localName the element local name
     * @param value     the text value, or {@code null} to skip
     */
    public void setText(Document doc, Element parent, String localName, String value) {
        if (value != null) {
            Element el = doc.createElementNS(NS_EP, localName);
            el.setTextContent(value);
            parent.appendChild(el);
        }
    }

    /**
     * Writes an integer element as a child of the given parent, if value is non-null.
     *
     * @param doc       the owning document
     * @param parent    the parent element
     * @param localName the element local name
     * @param value     the integer value, or {@code null} to skip
     */
    public void setInt(Document doc, Element parent, String localName, Integer value) {
        if (value != null) {
            Element el = doc.createElementNS(NS_EP, localName);
            el.setTextContent(String.valueOf(value));
            parent.appendChild(el);
        }
    }

    /**
     * Writes a boolean element as a child of the given parent, if value is non-null.
     *
     * @param doc       the owning document
     * @param parent    the parent element
     * @param localName the element local name
     * @param value     the boolean value, or {@code null} to skip
     */
    public void setBool(Document doc, Element parent, String localName, Boolean value) {
        if (value != null) {
            Element el = doc.createElementNS(NS_EP, localName);
            el.setTextContent(value ? "true" : "false");
            parent.appendChild(el);
        }
    }

    /**
     * Writes the heading pairs into a {@code HeadingPairs/vt:vector} structure.
     *
     * @param doc    the owning document
     * @param parent the parent element
     */
    public void writeHeadingPairs(Document doc, Element parent) {
        Element hpEl = doc.createElementNS(NS_EP, "HeadingPairs");
        parent.appendChild(hpEl);

        Element vector = doc.createElementNS(NS_VT, "vt:vector");
        vector.setAttribute("size", String.valueOf(headingPairs.size() * 2));
        vector.setAttribute("baseType", "variant");
        hpEl.appendChild(vector);

        for (HeadingPairData pair : headingPairs) {
            Element v1 = doc.createElementNS(NS_VT, "vt:variant");
            vector.appendChild(v1);
            Element lpstr = doc.createElementNS(NS_VT, "vt:lpstr");
            lpstr.setTextContent(pair.name());
            v1.appendChild(lpstr);

            Element v2 = doc.createElementNS(NS_VT, "vt:variant");
            vector.appendChild(v2);
            Element i4 = doc.createElementNS(NS_VT, "vt:i4");
            i4.setTextContent(String.valueOf(pair.count()));
            v2.appendChild(i4);
        }
    }

    /**
     * Writes the titles of parts into a {@code TitlesOfParts/vt:vector} structure.
     *
     * @param doc    the owning document
     * @param parent the parent element
     */
    public void writeTitlesOfParts(Document doc, Element parent) {
        Element tpEl = doc.createElementNS(NS_EP, "TitlesOfParts");
        parent.appendChild(tpEl);

        Element vector = doc.createElementNS(NS_VT, "vt:vector");
        vector.setAttribute("size", String.valueOf(titlesOfParts.size()));
        vector.setAttribute("baseType", "lpstr");
        tpEl.appendChild(vector);

        for (String title : titlesOfParts) {
            Element lpstr = doc.createElementNS(NS_VT, "vt:lpstr");
            lpstr.setTextContent(title);
            vector.appendChild(lpstr);
        }
    }

    // ---- property accessors ----

    /** Returns the Application property. */
    public String getApplication() { return application; }

    /** Sets the Application property. */
    public void setApplication(String value) { application = value; }

    /** Returns the AppVersion property. */
    public String getAppVersion() { return appVersion; }

    /** Sets the AppVersion property. */
    public void setAppVersion(String value) { appVersion = value; }

    /** Returns the Company property. */
    public String getCompany() { return company; }

    /** Sets the Company property. */
    public void setCompany(String value) { company = value; }

    /** Returns the Manager property. */
    public String getManager() { return manager; }

    /** Sets the Manager property. */
    public void setManager(String value) { manager = value; }

    /** Returns the PresentationFormat property. */
    public String getPresentationFormat() { return presentationFormat; }

    /** Sets the PresentationFormat property. */
    public void setPresentationFormat(String value) { presentationFormat = value; }

    /** Returns the Template property. */
    public String getTemplate() { return template; }

    /** Sets the Template property. */
    public void setTemplate(String value) { template = value; }

    /** Returns the HyperlinkBase property. */
    public String getHyperlinkBase() { return hyperlinkBase; }

    /** Sets the HyperlinkBase property. */
    public void setHyperlinkBase(String value) { hyperlinkBase = value; }

    /** Returns the TotalTime property. */
    public Integer getTotalTime() { return totalTime; }

    /** Sets the TotalTime property. */
    public void setTotalTime(Integer value) { totalTime = value; }

    /** Returns the Slides property. */
    public Integer getSlides() { return slides; }

    /** Sets the Slides property. */
    public void setSlides(Integer value) { slides = value; }

    /** Returns the HiddenSlides property. */
    public Integer getHiddenSlides() { return hiddenSlides; }

    /** Sets the HiddenSlides property. */
    public void setHiddenSlides(Integer value) { hiddenSlides = value; }

    /** Returns the Notes property. */
    public Integer getNotes() { return notes; }

    /** Sets the Notes property. */
    public void setNotes(Integer value) { notes = value; }

    /** Returns the Paragraphs property. */
    public Integer getParagraphs() { return paragraphs; }

    /** Sets the Paragraphs property. */
    public void setParagraphs(Integer value) { paragraphs = value; }

    /** Returns the Words property. */
    public Integer getWords() { return words; }

    /** Sets the Words property. */
    public void setWords(Integer value) { words = value; }

    /** Returns the MMClips property. */
    public Integer getMmClips() { return mmClips; }

    /** Sets the MMClips property. */
    public void setMmClips(Integer value) { mmClips = value; }

    /** Returns the ScaleCrop property. */
    public Boolean getScaleCrop() { return scaleCrop; }

    /** Sets the ScaleCrop property. */
    public void setScaleCrop(Boolean value) { scaleCrop = value; }

    /** Returns the LinksUpToDate property. */
    public Boolean getLinksUpToDate() { return linksUpToDate; }

    /** Sets the LinksUpToDate property. */
    public void setLinksUpToDate(Boolean value) { linksUpToDate = value; }

    /** Returns the SharedDoc property. */
    public Boolean getSharedDoc() { return sharedDoc; }

    /** Sets the SharedDoc property. */
    public void setSharedDoc(Boolean value) { sharedDoc = value; }

    /** Returns the HyperlinksChanged property. */
    public Boolean getHyperlinksChanged() { return hyperlinksChanged; }

    /** Sets the HyperlinksChanged property. */
    public void setHyperlinksChanged(Boolean value) { hyperlinksChanged = value; }

    /** Returns the heading pairs list as an unmodifiable view. */
    public List<HeadingPairData> getHeadingPairs() { return List.copyOf(headingPairs); }

    /** Sets the heading pairs list. */
    public void setHeadingPairs(List<HeadingPairData> value) { headingPairs = value; }

    /** Returns the titles of parts list as an unmodifiable view. */
    public List<String> getTitlesOfParts() { return List.copyOf(titlesOfParts); }

    /** Sets the titles of parts list. */
    public void setTitlesOfParts(List<String> value) { titlesOfParts = value; }

    /**
     * Resets all properties to {@code null}/empty and marks the part as dirty.
     */
    public void clear() {
        application = null;
        appVersion = null;
        company = null;
        manager = null;
        presentationFormat = null;
        template = null;
        hyperlinkBase = null;
        totalTime = null;
        slides = null;
        hiddenSlides = null;
        notes = null;
        paragraphs = null;
        words = null;
        mmClips = null;
        scaleCrop = null;
        linksUpToDate = null;
        sharedDoc = null;
        hyperlinksChanged = null;
        headingPairs = new ArrayList<>();
        titlesOfParts = new ArrayList<>();
        dirty = true;
    }
}
