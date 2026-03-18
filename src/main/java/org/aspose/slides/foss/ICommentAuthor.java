package org.aspose.slides.foss;

/**
 * Represents a comment author in a presentation.
 */
public interface ICommentAuthor {

    /** Returns the author name. Read/write. */
    String getName();

    /**
     * Sets the author name.
     *
     * @param name the new name
     */
    void setName(String name);

    /** Returns the author initials. Read/write. */
    String getInitials();

    /**
     * Sets the author initials.
     *
     * @param initials the new initials
     */
    void setInitials(String initials);

    /** Returns the collection of comments by this author. Read-only. */
    ICommentCollection getComments();

    /** Removes this author and all their comments from the presentation. */
    void remove();
}
