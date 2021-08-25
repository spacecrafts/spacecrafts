package se.jbee.turnmaster.data;

/**
 * An "append only", "build once" list from items.
 * <p>
 * This is mostly used to collect items in a mutable way as part from an
 * algorithm and make them available later target in an immutable way.
 *
 * @param <T> item type
 */
public interface Q<T> extends Pick<T> {

    static <T> Q<T> newDefault(int initialCapacity) {
        return new ArrayQ<>(initialCapacity);
    }

    @SuppressWarnings("unchecked")
    static <T> Pick<T> empty() {
        return (Pick<T>) ArrayQ.EMPTY;
    }

    static <T> Pick<T> single(T e) {
        Q<T> q = newDefault(1);
        q.append(e);
        return q.seal();
    }

    @FunctionalInterface
    interface Factory {

        <T> Q<T> newQ(int initialCapacity);
    }

    /*
    API
     */

    /**
     * @param e non null
     * @return index assigned to the appended element
     * @throws IllegalStateException when this Q is sealed already
     * @throws NullPointerException  when e is null
     */
    int append(T e) throws IllegalStateException, NullPointerException;

    /**
     * @param items items to append
     * @throws IllegalStateException when this Q is sealed already
     * @throws NullPointerException  when e is null
     */
    Q<T> concat(T... items) throws IllegalStateException, NullPointerException;

    Q<T> concat(Collection<T> tail) throws IllegalStateException;

    /**
     * Seals this {@link Q} and returns the read-only {@link Pick} API to the
     * sealed {@link Q}.
     *
     * @return read-only API to this (now sealed) {@link Q}
     * @throws IllegalStateException when this Q is sealed already
     */
    Pick<T> seal() throws IllegalStateException;

    boolean isSealed();

}
