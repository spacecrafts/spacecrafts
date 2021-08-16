package se.jbee.spacecrafts.sim.engine;

import java.util.function.Consumer;

public interface Range<T extends Any.Grade> extends Index<T> {

    static <T extends Any.Grade> Range<T> newDefault(Class<T> of, int initialCapacity) {
        return new ArrayRange<>(of, initialCapacity);
    }

    @FunctionalInterface
    interface Factory {
        <T extends Any.Grade> Range<T> newRange(Class<T> of, int initialCapacity);
    }

    /*
    API
     */

    /**
     * Iterates the items in {@link Any.Grade#ordinal()} order
     *
     * @param f to call for each item
     */
    void forEachInOrder(Consumer<? super T> f);
}
