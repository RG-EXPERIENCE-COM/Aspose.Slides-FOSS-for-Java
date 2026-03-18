package org.aspose.slides.foss;

import org.aspose.slides.foss.internal.pptx.AuthorData;
import org.aspose.slides.foss.internal.pptx.CommentAuthorsPart;
import org.aspose.slides.foss.internal.pptx.OpcPackage;

import java.util.Objects;

/**
 * Represents an author of comments.
 */
public final class CommentAuthor implements ICommentAuthor {

    private String name;
    private String initials;
    private final CommentCollection comments;
    private final int id;
    private CommentAuthorCollection parentCollection;

    // Internal backing fields (set via initInternal)
    private AuthorData data;
    private CommentAuthorsPart authorsPart;
    private OpcPackage pkg;
    private Presentation presentation;
    private CommentCollection commentsCache;

    /**
     * Creates a new comment author.
     *
     * @param name     the author name
     * @param initials the author initials
     * @param id       the internal author id
     */
    public CommentAuthor(String name, String initials, int id) {
        this.name = Objects.requireNonNull(name, "name");
        this.initials = Objects.requireNonNull(initials, "initials");
        this.id = id;
        this.comments = new CommentCollection(this);
    }

    /**
     * Initializes internal backing references that link this author to the
     * underlying OPC package data.
     *
     * <p>When initialized via this method, property accessors such as
     * {@link #getName()} and {@link #getInitials()} delegate to the backing
     * {@link AuthorData}, ensuring changes are reflected in the XML DOM.</p>
     *
     * @param data        the backing author data from the XML part
     * @param authorsPart the comment authors part that owns this author
     * @param pkg         the OPC package
     * @param presentation the owning presentation, or {@code null}
     */
    public void initInternal(
            AuthorData data,
            CommentAuthorsPart authorsPart,
            OpcPackage pkg,
            Presentation presentation) {
        this.data = data;
        this.authorsPart = authorsPart;
        this.pkg = pkg;
        this.presentation = presentation;
        this.commentsCache = null;
    }

    @Override
    public String getName() {
        if (data != null) {
            return data.getName();
        }
        return name;
    }

    @Override
    public void setName(String name) {
        Objects.requireNonNull(name, "name");
        if (data != null) {
            data.setName(name);
        }
        this.name = name;
    }

    @Override
    public String getInitials() {
        if (data != null) {
            return data.getInitials();
        }
        return initials;
    }

    @Override
    public void setInitials(String initials) {
        Objects.requireNonNull(initials, "initials");
        if (data != null) {
            data.setInitials(initials);
        }
        this.initials = initials;
    }

    @Override
    public ICommentCollection getComments() {
        return comments;
    }

    @Override
    public void remove() {
        comments.clear();
        if (parentCollection != null) {
            parentCollection.remove(this);
        }
    }

    /**
     * Returns the internal author id.
     *
     * @return the author id
     */
    public int getId() {
        if (data != null) {
            return data.getId();
        }
        return id;
    }

    /**
     * Returns the backing {@link AuthorData}, or {@code null} if not initialized
     * via {@link #initInternal}.
     *
     * @return the author data, or {@code null}
     */
    AuthorData getData() {
        return data;
    }

    /**
     * Returns the backing {@link CommentAuthorsPart}, or {@code null} if not
     * initialized via {@link #initInternal}.
     *
     * @return the authors part, or {@code null}
     */
    CommentAuthorsPart getAuthorsPart() {
        return authorsPart;
    }

    /**
     * Returns the backing {@link OpcPackage}, or {@code null} if not initialized
     * via {@link #initInternal}.
     *
     * @return the OPC package, or {@code null}
     */
    OpcPackage getPackage() {
        return pkg;
    }

    /**
     * Returns the owning {@link Presentation}, or {@code null}.
     *
     * @return the presentation, or {@code null}
     */
    Presentation getPresentation() {
        return presentation;
    }

    /**
     * Returns the comment collection as its concrete type (for serialization).
     *
     * @return the concrete comment collection
     */
    CommentCollection getCommentCollection() {
        return comments;
    }

    /**
     * Sets the parent collection reference (called by {@link CommentAuthorCollection}).
     *
     * @param parentCollection the owning collection
     */
    void setParentCollection(CommentAuthorCollection parentCollection) {
        this.parentCollection = parentCollection;
    }
}
