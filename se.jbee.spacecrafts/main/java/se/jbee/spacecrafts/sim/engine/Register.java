package se.jbee.spacecrafts.sim.engine;

/**
 * A {@link Register} is a {@link Pool} of {@link Any.Creation}s that also
 * allows to {@link #remove(int)} elements. Removed serials might get reused.
 *
 * @param <T>
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

    T remove(int serial) throws IllegalStateException;

}
