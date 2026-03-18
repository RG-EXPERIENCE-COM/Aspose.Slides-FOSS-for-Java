package org.aspose.slides.foss;

import java.util.List;

/**
 * Represents a collection of comment authors in a presentation.
 */
public interface ICommentAuthorCollection {

    /**
     * Adds a new author.
     *
     * @param name     the author name
     * @param initials the author initials
     * @return the new comment author
     */
    ICommentAuthor addAuthor(String name, String initials);

    /**
     * Returns all authors as an array.
     *
     * @return array of all comment authors
     */
    ICommentAuthor[] toArray();

    /**
     * Finds authors by name.
     *
     * @param name the name to search for
     * @return list of matching authors
     */
    List<ICommentAuthor> findByName(String name);

    /**
     * Finds authors by name and initials.
     *
     * @param name     the name to search for
     * @param initials the initials to search for
     * @return list of matching authors
     */
    List<ICommentAuthor> findByNameAndInitials(String name, String initials);

    /**
     * Removes the author at the given index, including all their comments.
     *
     * @param index the zero-based index
     */
    void removeAt(int index);

    /**
     * Removes an author from the collection.
     *
     * @param author the author to remove
     */
    void remove(ICommentAuthor author);

    /**
     * Removes all authors and their comments from the collection.
     */
    void clear();

    /**
     * Returns the author at the given index.
     *
     * @param index the zero-based index
     * @return the comment author
     */
    ICommentAuthor get(int index);

    /** Returns the number of authors. */
    int size();

    /**
     * Returns the collection as a {@link List}.
     *
     * @return list view of all authors
     */
    List<ICommentAuthor> asICollection();

    /**
     * Returns the collection as an {@link Iterable}.
     *
     * @return iterable over all authors
     */
    Iterable<ICommentAuthor> asIEnumerable();
}
