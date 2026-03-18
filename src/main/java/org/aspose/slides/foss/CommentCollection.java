package org.aspose.slides.foss;

import org.aspose.slides.foss.drawing.PointF;
import org.aspose.slides.foss.internal.pptx.AuthorData;
import org.aspose.slides.foss.internal.pptx.CommentAuthorsPart;
import org.aspose.slides.foss.internal.pptx.CommentData;
import org.aspose.slides.foss.internal.pptx.CommentsPart;
import org.aspose.slides.foss.internal.pptx.OpcPackage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents a collection of comments of one author.
 */
public final class CommentCollection implements ICommentCollection {

    private final List<Comment> comments = new ArrayList<>();
    private final ICommentAuthor author;
    private int nextIdx = 1;

    // Package-backed fields (set via initInternal)
    private AuthorData authorData;
    private CommentAuthorsPart authorsPart;
    private OpcPackage pkg;
    private Presentation presentation;
    private Map<String, CommentsPart> cpCache;

    /**
     * Creates a comment collection for the given author.
     *
     * @param author the owning author
     */
    public CommentCollection(ICommentAuthor author) {
        this.author = author;
    }

    /**
     * Initializes this collection with package-level backing data.
     *
     * @param authorData  the raw author data from the XML part
     * @param authorsPart the comment authors part
     * @param pkg         the OPC package
     * @param presentation the owning presentation, or {@code null}
     */
    public void initInternal(AuthorData authorData, CommentAuthorsPart authorsPart,
                             OpcPackage pkg, Presentation presentation) {
        this.authorData = authorData;
        this.authorsPart = authorsPart;
        this.pkg = pkg;
        this.presentation = presentation;
    }

    /**
     * Returns the {@link ISlide} whose part name matches the given part name, or {@code null}.
     *
     * @param partName the OPC part name to match (e.g. {@code "ppt/slides/slide1.xml"})
     * @return the matching slide, or {@code null} if not found or no presentation is set
     */
    public ISlide resolveSlide(String partName) {
        if (presentation == null) {
            return null;
        }
        var slides = presentation.getSlides();
        for (int i = 0; i < slides.size(); i++) {
            ISlide slide = slides.get(i);
            if (slide instanceof Slide s && partName.equals(s.getSlidePartUri())) {
                return slide;
            }
        }
        return null;
    }

    /**
     * Resolves the owning {@link ICommentAuthor} object from the backing author data.
     *
     * @return the comment author
     */
    public ICommentAuthor getAuthorObj() {
        var ca = new CommentAuthor(authorData.getName(), authorData.getInitials(), authorData.getId());
        return ca;
    }

    /**
     * Returns all {@link CommentsPart} instances across all slides that contain
     * comments by this author.
     *
     * @return list of (CommentsPart, slidePartName) pairs
     */
    public List<CommentsPartEntry> getAllCommentsParts() {
        var result = new ArrayList<CommentsPartEntry>();
        int authorId = authorData.getId();

        for (String partName : pkg.getPartNames()) {
            if (!(partName.startsWith("ppt/slides/slide") && partName.endsWith(".xml"))) {
                continue;
            }
            Optional<CommentsPart> cpOpt = CommentsPart.loadForSlide(pkg, partName);
            if (cpOpt.isEmpty()) {
                continue;
            }
            CommentsPart cp = cpOpt.get();
            List<CommentData> authorComments = cp.getCommentsByAuthor(authorId);
            if (!authorComments.isEmpty()) {
                result.add(new CommentsPartEntry(cp, partName));
            }
        }
        return result;
    }

    /**
     * Gets or creates the {@link CommentsPart} for the given slide part name.
     * Results are cached on a per-instance basis.
     *
     * @param slidePartName the slide part name
     * @return the comments part
     */
    public CommentsPart getOrCreateCommentsPart(String slidePartName) {
        if (cpCache == null) {
            cpCache = new HashMap<>();
        }
        return cpCache.computeIfAbsent(slidePartName, spn -> {
            Optional<CommentsPart> cpOpt = CommentsPart.loadForSlide(pkg, spn);
            return cpOpt.orElseGet(() -> CommentsPart.createForSlide(pkg, spn));
        });
    }

    /**
     * Builds a {@link Comment} from raw comment data, a comments part, and an optional slide.
     *
     * @param data     the raw comment data
     * @param cp       the comments part containing the data
     * @param slideObj the slide the comment belongs to, or {@code null}
     * @return the built comment
     */
    public Comment buildComment(CommentData data, CommentsPart cp, ISlide slideObj) {
        ICommentAuthor authorObj = getAuthorObj();
        var position = new PointF((float) data.getPosX(), (float) data.getPosY());
        LocalDateTime createdTime = CommentsPart.strToDt(data.getDtStr()).orElse(null);
        var c = new Comment(data.getText(), authorObj, slideObj, position, createdTime);
        c.setOwningCollection(this);
        c.setIdx(data.getIdx());
        return c;
    }

    /**
     * Collects all comments by this author across all slides in the package.
     *
     * @return list of (CommentData, CommentsPart, slidePartName) triples
     */
    public List<MyCommentEntry> collectMyComments() {
        var result = new ArrayList<MyCommentEntry>();
        int authorId = authorData.getId();

        for (String partName : pkg.getPartNames()) {
            if (!(partName.startsWith("ppt/slides/slide") && partName.endsWith(".xml"))) {
                continue;
            }
            Optional<CommentsPart> cpOpt = CommentsPart.loadForSlide(pkg, partName);
            if (cpOpt.isEmpty()) {
                continue;
            }
            CommentsPart cp = cpOpt.get();
            for (CommentData cd : cp.getCommentsByAuthor(authorId)) {
                result.add(new MyCommentEntry(cd, cp, partName));
            }
        }
        return result;
    }

    @Override
    public IComment addComment(String text, ISlide slide, PointF position, LocalDateTime createdTime) {
        Objects.requireNonNull(text, "text");
        Objects.requireNonNull(slide, "slide");
        Objects.requireNonNull(position, "position");
        Objects.requireNonNull(createdTime, "createdTime");
        var comment = new Comment(text, author, slide, position, createdTime);
        comment.setOwningCollection(this);
        comment.setIdx(nextIdx++);
        comments.add(comment);
        return comment;
    }

    @Override
    public IComment insertComment(int index, String text, ISlide slide, PointF position, LocalDateTime createdTime) {
        Objects.requireNonNull(text, "text");
        Objects.requireNonNull(slide, "slide");
        Objects.requireNonNull(position, "position");
        Objects.requireNonNull(createdTime, "createdTime");
        var comment = new Comment(text, author, slide, position, createdTime);
        comment.setOwningCollection(this);
        comment.setIdx(nextIdx++);
        comments.add(index, comment);
        return comment;
    }

    @Override
    public void removeAt(int index) {
        comments.remove(index);
    }

    @Override
    public void remove(IComment comment) {
        comment.remove();
    }

    @Override
    public void clear() {
        comments.clear();
    }

    @Override
    public IComment get(int index) {
        return comments.get(index);
    }

    @Override
    public int size() {
        return comments.size();
    }

    @Override
    public IComment[] toArray() {
        return comments.toArray(new IComment[0]);
    }

    @Override
    public IComment[] toArray(int startIndex, int count) {
        return comments.subList(startIndex, startIndex + count).toArray(new IComment[0]);
    }

    @Override
    public List<IComment> asICollection() {
        return List.copyOf(comments);
    }

    @Override
    public Iterable<IComment> asIEnumerable() {
        return List.copyOf(comments);
    }

    @Override
    public Optional<IComment> findCommentByIdx(int idx) {
        return comments.stream()
                .filter(c -> c.getIdx() == idx)
                .map(c -> (IComment) c)
                .findFirst();
    }

    /**
     * Returns the internal list of comments (for serialization).
     *
     * @return the backing list
     */
    List<Comment> getInternalList() {
        return comments;
    }

    /**
     * Sets the next idx counter (for deserialization).
     *
     * @param nextIdx the next index value
     */
    void setNextIdx(int nextIdx) {
        this.nextIdx = nextIdx;
    }

    /**
     * A (CommentsPart, slidePartName) pair returned by {@link #getAllCommentsParts()}.
     *
     * @param commentsPart  the comments part
     * @param slidePartName the slide part name
     */
    public record CommentsPartEntry(CommentsPart commentsPart, String slidePartName) {}

    /**
     * A (CommentData, CommentsPart, slidePartName) triple returned by {@link #collectMyComments()}.
     *
     * @param commentData   the raw comment data
     * @param commentsPart  the comments part containing the data
     * @param slidePartName the slide part name
     */
    public record MyCommentEntry(CommentData commentData, CommentsPart commentsPart, String slidePartName) {}
}
