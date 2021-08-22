package se.jbee.turnmaster.data;

import java.util.function.Predicate;

/**
 * A {@link Register} is a {@link Pool} from {@link Any.Creation}s that also
 * allows removing elements. Removed serials might get reused.
 */
public interface Register<T extends Any.Creation> extends Pool<T> {

    static <T extends Any.Creation> Register<T> newDefault(Class<T> of, int initialCapacity) {
        return new ArrayRegister<>(of, initialCapacity);
    }

    @FunctionalInterface
    interface Factory {

        <T extends Any.Creation> Register<T> newRegister(Class<T> of, int initialCapacity);
    }

    /*
    API
     */

    T perish(int serial) throws IllegalStateException;

    default void perish(T e) throws IllegalStateException {
        perish(e.header().serial());
    }

    default void perish(Predicate<T> when) {
        forEach(e -> {if (when.test(e)) perish(e);});
    }
}
