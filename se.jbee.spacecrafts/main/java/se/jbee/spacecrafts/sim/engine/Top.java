package se.jbee.spacecrafts.sim.engine;

/**
 * A {@link Top} is an ordered list ordered from most important (top) at index
 * zero to least important (bottom) at last index.
 *
 * @param <T> list item type
 */
public interface Top<T> extends Collection<T> {

    static <T> Top<T> newDefault(int initialCapacity, int maxCapacity) {
        return new ArrayTop<>(initialCapacity, maxCapacity);
    }

    @FunctionalInterface
    interface Factory {

        <T> Top<T> newTop(int initialCapacity, int maxCapacity);
    }

    /*
    API
     */

    void moveToTop(int index) throws IndexOutOfBoundsException;

    void moveToBottom(int index) throws IndexOutOfBoundsException;

    void moveUp(int index) throws IndexOutOfBoundsException;

    void moveDown(int index) throws IndexOutOfBoundsException;

    void pushTop(T e) throws NullPointerException;

    void pushBottom(T e) throws NullPointerException;

    T peek(int index) throws IndexOutOfBoundsException;

    T remove(int index) throws IndexOutOfBoundsException;

    void remove(int fromIndex, int toIndex) throws IndexOutOfBoundsException;

    int capacity();

    Top<T> slice(int fromIndex, int toIndex) throws IndexOutOfBoundsException;

    default Top<T> cut(int fromIndex, int toIndex) throws IndexOutOfBoundsException {
        var cut = slice(fromIndex, toIndex);
        remove(fromIndex, toIndex);
        return cut;
    }

    default T popTop() throws IndexOutOfBoundsException {
        return remove(0);
    }

    default T popBottom() throws IndexOutOfBoundsException {
        return remove(size() - 1);
    }

    default T peekTop() throws IndexOutOfBoundsException {
        return peek(0);
    }

    default T peekBottom() throws IndexOutOfBoundsException {
        return peek(size() - 1);
    }

    default void popTop(int n) throws IndexOutOfBoundsException {
        remove(0, n - 1);
    }

    default void popBottom(int n) throws IndexOutOfBoundsException {
        int i0 = size() - n;
        remove(i0, i0 + n - 1);
    }

    default void pushTop(T... es) {
        for (int i = es.length - 1; i >= 0; i--)
            pushTop(es[i]);
    }

    default void pushBottom(T... es) {
        for (T e : es)
            pushBottom(e);
    }

    default void pushBottom(Collection<T> es) {
        es.forEach(this::pushBottom);
    }

    default void moveUp(int fromIndex, int toIndex) {
        for (int i = fromIndex; i <= toIndex; i++)
            moveUp(i);
    }

    default void moveDown(int fromIndex, int toIndex) {
        for (int i = toIndex; i >= fromIndex; i--)
            moveDown(i);
    }

    default void moveToTop(int fromIndex, int toIndex) {
        for (int i = toIndex; i >= fromIndex; i--)
            moveToTop(toIndex);
    }

    default void moveToBottom(int fromIndex, int toIndex) {
        for (int i = fromIndex; i <= toIndex; i++)
            moveToBottom(fromIndex);
    }
}
