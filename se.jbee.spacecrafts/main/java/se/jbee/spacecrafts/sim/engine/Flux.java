package se.jbee.spacecrafts.sim.engine;

import se.jbee.spacecrafts.sim.engine.Any.Entity;

/**
 * Is a semantic set of {@link Entity}s.
 * <p>
 * Iteration follows no particular order.
 *
 * @param <T>
 */
public interface Flux<T extends Entity> extends Collection<T> {

    static <T extends Entity> Flux<T> newDefault(Pool<T> of) {
        return new BitsFlux<>(of);
    }

    interface Factory {
        <T extends Entity> Flux<T> newFlux(Pool<T> of);
    }

    /*
    API
     */

    void add(T e);

    void remove(T e);

    void clear();

    void add(Collection<T> added);

    void remove(Collection<T> removed);

    /**
     * @return a non-modifiable copy of this
     */
    Stasis<T> inStasis();
}
