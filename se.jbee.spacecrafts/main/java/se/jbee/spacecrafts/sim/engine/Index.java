package se.jbee.spacecrafts.sim.engine;

import java.util.NoSuchElementException;

public interface Index<T extends Any.Definition> extends Pool<T> {

    static <T extends Any.Definition> Index<T> newDefault(Class<T> of, int initialCapacity) {
        return new ArrayIndex<>(of, initialCapacity);
    }

    @FunctionalInterface
    interface Factory {
        <T extends Any.Definition> Index<T> newIndex(Class<T> of, int initialCapacity);
    }

    /*
    API
     */

    default T get(Any.Code code) throws NoSuchElementException {
        return first(e -> e.header().code().equals(code)) //
                .orElseThrow(() -> new NoSuchElementException(of().getSimpleName() + " with code: " + code));
    }
}
