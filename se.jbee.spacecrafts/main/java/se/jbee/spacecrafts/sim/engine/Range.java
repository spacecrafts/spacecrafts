package se.jbee.spacecrafts.sim.engine;

import java.util.function.Consumer;

public interface Range<T extends Any.Quality> extends Index<T> {

    static <T extends Any.Quality> Range<T> newDefault(Class<T> of, int initialCapacity) {
        return new ArrayRange<>(of, initialCapacity);
    }

    interface Factory {
        <T extends Any.Quality> Range<T> newRange(Class<T> of, int initialCapacity);
    }

    /*
    API
     */

    /**
     * Iterates the items in {@link Any.Quality#ordinal()} order
     *
     * @param f to call for each item
     */
    void forEachInOrder(Consumer<? super T> f);
}
