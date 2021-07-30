package se.jbee.spacecrafts.sim;

import java.util.function.Consumer;

public interface Range<T extends Any.Quality> extends Pool<T> {

    /**
     * Iterates the items in {@link Any.Quality#ordinal()} order
     *
     * @param f to call for each item
     */
    void forEachInOrder(Consumer<T> f);
}
