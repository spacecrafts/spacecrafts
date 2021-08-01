package se.jbee.spacecrafts.sim.collection;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * A "build once" list of items.
 * <p>
 * This is mostly used to collect items in a mutable way as part of an algorithm
 * and make them available later on in an immutable way.
 *
 * @param <T> item type
 */
public interface Q<T> {

    void add(T e);

    void seal();

    int size();

    void forEach(Consumer<T> f);

    T first(Predicate<T> test);

    default boolean isEmpty() {
        return size() == 0;
    }

    default boolean contains(Predicate<T> test) {
        return first(test) != null;
    }

    int findIndex(T e);

}
