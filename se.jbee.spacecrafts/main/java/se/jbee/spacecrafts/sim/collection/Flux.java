package se.jbee.spacecrafts.sim.collection;

import se.jbee.spacecrafts.sim.Any.Entity;

import java.util.function.Predicate;

/**
 * @param <T>
 */
public interface Flux<T extends Entity> extends Collection<T> {

    boolean contains(T e);

    T first(Predicate<? super T> test);

    void add(T e);

    void remove(T e);

    void clear();

    void add(Flux<T> added);

    void remove(Flux<T> removed);
    // can be implemented by a bit mask and a ref to all entities of T
}
