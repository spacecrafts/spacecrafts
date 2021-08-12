package se.jbee.spacecrafts.sim.engine;

import java.util.function.Predicate;

/**
 * A "build once" list of items.
 * <p>
 * This is mostly used to collect items in a mutable way as part of an algorithm
 * and make them available later on in an immutable way.
 *
 * @param <T> item type
 */
public interface Q<T> extends Collection<T> {

    static <T> Q<T> newDefault(int initialCapacity) {
        return new ArrayQ<>(initialCapacity);
    }

    @FunctionalInterface
    interface Factory {
        <T> Q<T> newQ(int initialCapacity);
    }

    /*
    API
     */

    T get(int index) throws IndexOutOfBoundsException;

    /**
     * @param e non null
     * @throws IllegalStateException when this Q is sealed already
     * @throws NullPointerException  when e is null
     */
    void append(T e) throws IllegalStateException, NullPointerException;

    /**
     * @return this (now sealed) Q instance for chaining
     * @throws IllegalStateException when this Q is sealed already
     */
    Q<T> seal() throws IllegalStateException;

    int firstIndex(Predicate<? super T> test);

    default int firstIndex(T sample) {
        return firstIndex(e -> e.equals(sample));
    }
}
