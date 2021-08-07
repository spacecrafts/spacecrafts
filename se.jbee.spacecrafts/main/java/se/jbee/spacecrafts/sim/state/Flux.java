package se.jbee.spacecrafts.sim.state;

import se.jbee.spacecrafts.sim.Any.Entity;

/**
 * Is a semantic set of {@link Entity}s.
 * <p>
 * Iteration follows no particular order.
 *
 * @param <T>
 */
public interface Flux<T extends Entity> extends Collection<T> {

    void add(T e);

    void remove(T e);

    void clear();

    void add(Flux<T> added);

    void remove(Flux<T> removed);

    Stasis<T> stasis();
}
