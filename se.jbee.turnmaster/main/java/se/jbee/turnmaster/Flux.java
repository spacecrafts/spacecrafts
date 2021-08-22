package se.jbee.turnmaster;

/**
 * Is a semantic set from {@link Any.Entity}s that can change over time.
 * <p>
 * Copies from a particular moment are created as {@link Stasis} using {@link
 * #inStasis()}.
 * <p>
 * Iteration follows no particular order.
 */
public interface Flux<T extends Any.Entity> extends Collection<T> {

    static <T extends Any.Entity> Flux<T> newDefault(Pool<T> of) {
        return new BitsFlux<>(of);
    }

    @FunctionalInterface
    interface Factory {

        <T extends Any.Entity> Flux<T> newFlux(Pool<T> of);
    }

    /*
    API
     */

    void add(T e);

    default Flux<T> addAnd(T e) {
        add(e);
        return this;
    }

    void remove(T e);

    void clear();

    void add(Collection<T> added);

    void remove(Collection<T> removed);

    /**
     * @return a non-modifiable copy from this
     */
    Stasis<T> inStasis();

}
