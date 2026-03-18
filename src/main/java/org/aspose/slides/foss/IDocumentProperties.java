package org.aspose.slides.foss;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents properties of a presentation document.
 *
 * <p>Provides access to built-in, extended, and custom document properties
 * stored in the OPC package.</p>
 */
public interface IDocumentProperties {

    /** Returns or sets the title. Read/write. */
    String getTitle();
    /** Sets the title. */
    void setTitle(String value);

    /** Returns or sets the subject. Read/write. */
    String getSubject();
    /** Sets the subject. */
    void setSubject(String value);

    /** Returns or sets the author. Read/write. */
    String getAuthor();
    /** Sets the author. */
    void setAuthor(String value);

    /** Returns or sets the keywords. Read/write. */
    String getKeywords();
    /** Sets the keywords. */
    void setKeywords(String value);

    /** Returns or sets the comments. Read/write. */
    String getComments();
    /** Sets the comments. */
    void setComments(String value);

    /** Returns or sets the category. Read/write. */
    String getCategory();
    /** Sets the category. */
    void setCategory(String value);

    /** Returns or sets the content status. Read/write. */
    String getContentStatus();
    /** Sets the content status. */
    void setContentStatus(String value);

    /** Returns or sets the content type. Read/write. */
    String getContentType();
    /** Sets the content type. */
    void setContentType(String value);

    /** Returns or sets the last person who modified the presentation. Read/write. */
    String getLastSavedBy();
    /** Sets the last saved by name. */
    void setLastSavedBy(String value);

    /** Returns or sets the revision number. Read/write. */
    int getRevisionNumber();
    /** Sets the revision number. */
    void setRevisionNumber(int value);

    /** Returns or sets the date the presentation was created (UTC). Read/write. */
    LocalDateTime getCreatedTime();
    /** Sets the created time. */
    void setCreatedTime(LocalDateTime value);

    /** Returns or sets the date the presentation was last modified (UTC). Read/write. */
    LocalDateTime getLastSavedTime();
    /** Sets the last saved time. */
    void setLastSavedTime(LocalDateTime value);

    /** Returns or sets the date the presentation was last printed. Read/write. */
    LocalDateTime getLastPrinted();
    /** Sets the last printed time. */
    void setLastPrinted(LocalDateTime value);

    /** Returns the application version. Read-only. */
    String getAppVersion();

    /** Returns or sets the name of the application. Read/write. */
    String getNameOfApplication();
    /** Sets the application name. */
    void setNameOfApplication(String value);

    /** Returns or sets the company. Read/write. */
    String getCompany();
    /** Sets the company. */
    void setCompany(String value);

    /** Returns or sets the manager. Read/write. */
    String getManager();
    /** Sets the manager. */
    void setManager(String value);

    /** Returns or sets the presentation format. Read/write. */
    String getPresentationFormat();
    /** Sets the presentation format. */
    void setPresentationFormat(String value);

    /** Returns or sets the application template. Read/write. */
    String getApplicationTemplate();
    /** Sets the application template. */
    void setApplicationTemplate(String value);

    /** Returns or sets the hyperlink base. Read/write. */
    String getHyperlinkBase();
    /** Sets the hyperlink base. */
    void setHyperlinkBase(String value);

    /** Returns or sets the total editing time. Read/write. */
    Duration getTotalEditingTime();
    /** Sets the total editing time. */
    void setTotalEditingTime(Duration value);

    /** Returns or sets whether the document is shared. Read/write. */
    boolean getSharedDoc();
    /** Sets the shared doc flag. */
    void setSharedDoc(boolean value);

    /** Returns or sets whether to scale or crop the thumbnail. Read/write. */
    boolean getScaleCrop();
    /** Sets the scale crop flag. */
    void setScaleCrop(boolean value);

    /** Returns or sets whether hyperlinks are up to date. Read/write. */
    boolean getLinksUpToDate();
    /** Sets the links up to date flag. */
    void setLinksUpToDate(boolean value);

    /** Returns or sets whether hyperlinks have changed. Read/write. */
    boolean getHyperlinksChanged();
    /** Sets the hyperlinks changed flag. */
    void setHyperlinksChanged(boolean value);

    /** Returns the total number of slides. Read-only. */
    int getSlides();

    /** Returns the number of hidden slides. Read-only. */
    int getHiddenSlides();

    /** Returns the number of slides containing notes. Read-only. */
    int getNotes();

    /** Returns the total number of paragraphs. Read-only. */
    int getParagraphs();

    /** Returns the total number of words. Read-only. */
    int getWords();

    /** Returns the total number of multimedia clips. Read-only. */
    int getMultimediaClips();

    /** Returns the heading pairs. Read-only. */
    List<IHeadingPair> getHeadingPairs();

    /** Returns the titles of parts. Read-only. */
    List<String> getTitlesOfParts();

    /** Returns the number of custom properties. Read-only. */
    int getCountOfCustomProperties();

    /**
     * Gets a named custom property value and places it in the output list.
     *
     * @param name the property name
     * @param out  a list that will be cleared and receive the value (if found)
     */
    void getCustomPropertyValue(String name, List<Object> out);

    /**
     * Sets a named custom property value.
     *
     * @param name  the property name
     * @param value the property value
     */
    void setCustomPropertyValue(String name, Object value);

    /**
     * Returns the name of the custom property at the given index.
     *
     * @param index zero-based index
     * @return the property name
     */
    String getCustomPropertyName(int index);

    /**
     * Removes a named custom property.
     *
     * @param name the property name
     * @return {@code true} if the property was found and removed
     */
    boolean removeCustomProperty(String name);

    /**
     * Returns whether a named custom property exists.
     *
     * @param name the property name
     * @return {@code true} if the property exists
     */
    boolean containsCustomProperty(String name);

    /** Removes all custom properties. */
    void clearCustomProperties();

    /** Clears all built-in properties (core and app). */
    void clearBuiltInProperties();
}
