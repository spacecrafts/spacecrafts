package se.jbee.spacecrafts.sim.state;

import se.jbee.spacecrafts.sim.Any;

import java.util.function.Consumer;

public interface Range<T extends Any.Quality> extends Index<T> {

    /**
     * Iterates the items in {@link Any.Quality#ordinal()} order
     *
     * @param f to call for each item
     */
    void forEachInOrder(Consumer<? super T> f);
}
