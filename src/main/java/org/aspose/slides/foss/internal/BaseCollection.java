package org.aspose.slides.foss.internal;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Internal base for every *Collection class.
 *
 * <p>Concrete subclasses must implement:
 * <ul>
 *   <li>{@link #size()} — number of elements</li>
 *   <li>{@link #get(int)} — element at the given index</li>
 * </ul>
 *
 * <p>In return they automatically get:
 * <ul>
 *   <li>{@link #length()} — equivalent to Aspose .NET {@code ICollection.Count}</li>
 *   <li>{@link #iterator()} — sequential index-based iteration</li>
 *   <li>{@link #contains(Object)} — identity + equality membership test</li>
 * </ul>
 *
 * @param <T> the element type
 */
public abstract class BaseCollection<T> implements Iterable<T> {

    /**
     * Returns the number of elements in this collection.
     *
     * @return the element count
     */
    public abstract int size();

    /**
     * Returns the element at the specified index.
     *
     * @param index zero-based index
     * @return the element at {@code index}
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public abstract T get(int index);

    /**
     * Returns the number of elements. Read-only.
     * Equivalent to Aspose .NET {@code ICollection.Count}.
     *
     * @return the element count
     */
    public int length() {
        return size();
    }

    /**
     * Returns {@code true} if this collection contains no elements.
     *
     * @return {@code true} if empty
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns an iterator over the elements in this collection, in index order.
     *
     * @return an iterator
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int cursor = 0;

            @Override
            public boolean hasNext() {
                return cursor < size();
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return get(cursor++);
            }
        };
    }

    /**
     * Tests whether this collection contains the specified element.
     * Uses identity comparison ({@code ==}) first, then {@link Objects#equals}.
     *
     * @param item the element to search for
     * @return {@code true} if the element is found
     */
    public boolean contains(Object item) {
        for (T element : this) {
            if (element == item || Objects.equals(element, item)) {
                return true;
            }
        }
        return false;
    }
}
