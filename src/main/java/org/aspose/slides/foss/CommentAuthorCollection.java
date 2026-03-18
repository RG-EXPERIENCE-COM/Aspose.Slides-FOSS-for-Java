package org.aspose.slides.foss;

import org.aspose.slides.foss.internal.BaseCollection;
import org.aspose.slides.foss.internal.pptx.AuthorData;
import org.aspose.slides.foss.internal.pptx.CommentAuthorsPart;
import org.aspose.slides.foss.internal.pptx.OpcPackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a collection of comment authors in a presentation.
 *
 * <p>This collection supports two operating modes:
 * <ul>
 *   <li><b>In-memory mode</b> — authors are stored in an internal list
 *       (used when constructed via the default constructor).</li>
 *   <li><b>XML-backed mode</b> — authors are delegated to the underlying
 *       {@link CommentAuthorsPart} (activated via {@link #initInternal}).</li>
 * </ul>
 */
public final class CommentAuthorCollection extends BaseCollection<ICommentAuthor>
        implements ICommentAuthorCollection {

    private final List<CommentAuthor> authors = new ArrayList<>();
    private int nextId = 0;

    // XML-backed mode fields (set via initInternal)
    private CommentAuthorsPart authorsPart;
    private OpcPackage pkg;
    private Presentation presentation;

    /**
     * Initializes internal backing references that link this collection to the
     * underlying OPC package data.
     *
     * <p>When initialized via this method, {@link #get(int)} and {@link #size()}
     * delegate to the backing {@link CommentAuthorsPart}, and {@link #buildAuthor}
     * can be used to hydrate {@link CommentAuthor} instances from XML data.</p>
     *
     * @param authorsPart  the comment authors XML part
     * @param pkg          the OPC package
     * @param presentation the owning presentation, or {@code null}
     */
    public void initInternal(
            CommentAuthorsPart authorsPart,
            OpcPackage pkg,
            Presentation presentation) {
        this.authorsPart = authorsPart;
        this.pkg = pkg;
        this.presentation = presentation;
    }

    /**
     * Builds a {@link CommentAuthor} from the given XML-backed {@link AuthorData}.
     *
     * <p>The returned author is fully initialized with internal references
     * to the authors part, package, and presentation.</p>
     *
     * @param data the author data from the XML part
     * @return a new {@link CommentAuthor} initialized with the backing data
     */
    public CommentAuthor buildAuthor(AuthorData data) {
        var ca = new CommentAuthor(data.getName(), data.getInitials(), data.getId());
        ca.initInternal(data, authorsPart, pkg, presentation);
        return ca;
    }

    private boolean isXmlBacked() {
        return authorsPart != null;
    }

    @Override
    public ICommentAuthor addAuthor(String name, String initials) {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(initials, "initials");
        var author = new CommentAuthor(name, initials, nextId++);
        author.setParentCollection(this);
        authors.add(author);
        return author;
    }

    @Override
    public ICommentAuthor[] toArray() {
        if (isXmlBacked()) {
            return authorsPart.getAuthors().stream()
                    .map(this::buildAuthor)
                    .toArray(ICommentAuthor[]::new);
        }
        return authors.toArray(new ICommentAuthor[0]);
    }

    @Override
    public List<ICommentAuthor> findByName(String name) {
        if (isXmlBacked()) {
            return authorsPart.getAuthors().stream()
                    .map(this::buildAuthor)
                    .filter(a -> Objects.equals(a.getName(), name))
                    .map(a -> (ICommentAuthor) a)
                    .toList();
        }
        return authors.stream()
                .filter(a -> Objects.equals(a.getName(), name))
                .map(a -> (ICommentAuthor) a)
                .toList();
    }

    @Override
    public List<ICommentAuthor> findByNameAndInitials(String name, String initials) {
        if (isXmlBacked()) {
            return authorsPart.getAuthors().stream()
                    .map(this::buildAuthor)
                    .filter(a -> Objects.equals(a.getName(), name) && Objects.equals(a.getInitials(), initials))
                    .map(a -> (ICommentAuthor) a)
                    .toList();
        }
        return authors.stream()
                .filter(a -> Objects.equals(a.getName(), name) && Objects.equals(a.getInitials(), initials))
                .map(a -> (ICommentAuthor) a)
                .toList();
    }

    @Override
    public void removeAt(int index) {
        if (isXmlBacked()) {
            var authorsList = authorsPart.getAuthors();
            if (index >= 0 && index < authorsList.size()) {
                var author = buildAuthor(authorsList.get(index));
                author.getComments().clear();
                authorsPart.removeAuthor(authorsList.get(index).getId());
            }
            return;
        }
        if (index >= 0 && index < authors.size()) {
            var author = authors.get(index);
            author.getComments().clear();
            authors.remove(index);
        }
    }

    @Override
    public void remove(ICommentAuthor author) {
        authors.remove(author);
    }

    @Override
    public void clear() {
        if (isXmlBacked()) {
            for (var data : authorsPart.getAuthors()) {
                buildAuthor(data).getComments().clear();
            }
            authorsPart.clear();
            return;
        }
        for (var author : authors) {
            author.getComments().clear();
        }
        authors.clear();
    }

    /**
     * {@inheritDoc}
     *
     * <p>In XML-backed mode, delegates to {@link CommentAuthorsPart#getAuthors()}
     * and hydrates the author at the given index. Throws {@link IndexOutOfBoundsException}
     * if the index is out of range.</p>
     */
    @Override
    public ICommentAuthor get(int index) {
        if (isXmlBacked()) {
            var authorsList = authorsPart.getAuthors();
            if (index < 0 || index >= authorsList.size()) {
                throw new IndexOutOfBoundsException(
                        "Index " + index + " out of range for size " + authorsList.size());
            }
            return buildAuthor(authorsList.get(index));
        }
        return authors.get(index);
    }

    /**
     * {@inheritDoc}
     *
     * <p>In XML-backed mode, returns the count from
     * {@link CommentAuthorsPart#getAuthors()}.</p>
     */
    @Override
    public int size() {
        if (isXmlBacked()) {
            return authorsPart.getAuthors().size();
        }
        return authors.size();
    }

    @Override
    public List<ICommentAuthor> asICollection() {
        if (isXmlBacked()) {
            return authorsPart.getAuthors().stream()
                    .map(this::buildAuthor)
                    .map(a -> (ICommentAuthor) a)
                    .toList();
        }
        return List.copyOf(authors);
    }

    @Override
    public Iterable<ICommentAuthor> asIEnumerable() {
        return asICollection();
    }

    /**
     * Returns the internal list of authors (for serialization).
     *
     * @return the backing list
     */
    List<CommentAuthor> getInternalList() {
        return authors;
    }

    /**
     * Sets the next author ID (used during deserialization).
     *
     * @param nextId the next id to use
     */
    void setNextId(int nextId) {
        this.nextId = nextId;
    }
}
