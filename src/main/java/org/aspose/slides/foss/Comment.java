package org.aspose.slides.foss;

import org.aspose.slides.foss.drawing.PointF;
import org.aspose.slides.foss.internal.pptx.CommentAuthorsPart;
import org.aspose.slides.foss.internal.pptx.CommentData;
import org.aspose.slides.foss.internal.pptx.CommentsPart;
import org.aspose.slides.foss.internal.pptx.OpcPackage;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents a comment on a presentation slide.
 */
public final class Comment implements IComment {

    private String text;
    private ICommentAuthor author;
    private ISlide slide;
    private PointF position;
    private LocalDateTime createdTime;
    private IComment parentComment;
    private CommentCollection owningCollection;
    private int idx;

    // Internal backing fields (set via initInternal)
    private CommentData data;
    private CommentsPart commentsPart;
    private CommentAuthorsPart authorsPart;
    private OpcPackage opcPackage;
    private IPresentation presentation;

    /**
     * Creates a new comment.
     *
     * @param text        the comment text
     * @param author      the comment author
     * @param slide       the target slide
     * @param position    the position on the slide
     * @param createdTime the creation date/time
     */
    public Comment(String text, ICommentAuthor author, ISlide slide,
                   PointF position, LocalDateTime createdTime) {
        this.text = Objects.requireNonNull(text, "text");
        this.author = Objects.requireNonNull(author, "author");
        this.slide = Objects.requireNonNull(slide, "slide");
        this.position = Objects.requireNonNull(position, "position");
        this.createdTime = Objects.requireNonNull(createdTime, "createdTime");
    }

    /**
     * Initialises this comment with internal PPTX backing data.
     *
     * <p>After this call, property accessors such as {@link #getText()} delegate
     * to the underlying {@link CommentData} so that mutations are reflected in
     * the package XML.</p>
     *
     * @param data         the raw comment data from the XML part
     * @param commentsPart the comments part that owns this comment
     * @param authorsPart  the comment authors part for the presentation
     * @param slide        the slide this comment belongs to
     * @param author       the author of this comment
     * @param opcPackage   the OPC package, or {@code null}
     * @param presentation the owning presentation, or {@code null}
     */
    void initInternal(CommentData data, CommentsPart commentsPart,
                      CommentAuthorsPart authorsPart, ISlide slide,
                      ICommentAuthor author, OpcPackage opcPackage,
                      IPresentation presentation) {
        this.data = data;
        this.commentsPart = commentsPart;
        this.authorsPart = authorsPart;
        this.slide = slide;
        this.author = author;
        this.opcPackage = opcPackage;
        this.presentation = presentation;
    }

    @Override
    public String getText() {
        if (data != null) {
            return data.getText();
        }
        return text;
    }

    @Override
    public void setText(String value) {
        Objects.requireNonNull(value, "value");
        if (data != null) {
            data.setText(value);
        }
        this.text = value;
    }

    @Override
    public ICommentAuthor getAuthor() {
        return author;
    }

    @Override
    public ISlide getSlide() {
        return slide;
    }

    @Override
    public PointF getPosition() {
        return position;
    }

    @Override
    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    @Override
    public Optional<IComment> getParentComment() {
        return Optional.ofNullable(parentComment);
    }

    @Override
    public void setParentComment(IComment parent) {
        this.parentComment = parent;
    }

    @Override
    public void remove() {
        if (owningCollection == null) {
            return;
        }
        // Remove all replies that reference this comment as their parent
        owningCollection.getInternalList().removeIf(
                c -> c != this && c.parentComment == this
        );
        // Remove this comment itself
        owningCollection.getInternalList().remove(this);
    }

    /**
     * Sets the owning collection (for internal use by {@link CommentCollection}).
     *
     * @param collection the owning collection
     */
    void setOwningCollection(CommentCollection collection) {
        this.owningCollection = collection;
    }

    /**
     * Returns the unique index of this comment within its author's comments.
     *
     * @return the comment index
     */
    int getIdx() {
        return idx;
    }

    /**
     * Sets the unique index of this comment (for internal use by {@link CommentCollection}).
     *
     * @param idx the comment index
     */
    void setIdx(int idx) {
        this.idx = idx;
    }

    /**
     * Returns the internal comment data, or {@code null} if not initialised.
     *
     * @return the backing {@link CommentData}
     */
    CommentData getData() {
        return data;
    }

    /**
     * Returns the internal comments part, or {@code null} if not initialised.
     *
     * @return the backing {@link CommentsPart}
     */
    CommentsPart getCommentsPart() {
        return commentsPart;
    }

    /**
     * Returns the internal comment authors part, or {@code null} if not initialised.
     *
     * @return the backing {@link CommentAuthorsPart}
     */
    CommentAuthorsPart getAuthorsPart() {
        return authorsPart;
    }

    /**
     * Returns the OPC package, or {@code null} if not initialised.
     *
     * @return the backing {@link OpcPackage}
     */
    OpcPackage getOpcPackage() {
        return opcPackage;
    }

    /**
     * Returns the owning presentation, or {@code null} if not initialised.
     *
     * @return the backing {@link IPresentation}
     */
    IPresentation getPresentation() {
        return presentation;
    }
}
