package se.jbee.spacecrafts.sim.collection;

import se.jbee.spacecrafts.sim.Any.Entity;

/**
 * @param <T>
 */
public interface Flux<T extends Entity> extends Collection<T> {

    boolean contains(T e);

    void add(T e);

    void remove(T e);

    void clear();

    // can be implemented by a bit mask and a ref to all entities of T
}
