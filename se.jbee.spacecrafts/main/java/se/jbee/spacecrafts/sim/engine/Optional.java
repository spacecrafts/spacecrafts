package se.jbee.spacecrafts.sim.engine;

import java.util.NoSuchElementException;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * An {@link Optional} is either an immutable {@link Maybe} container or a
 * mutable {@link Vary} container.
 *
 * @see Maybe
 * @see Vary
 */
public sealed interface Optional<T> permits Maybe, Vary {

    boolean isSome();

    boolean is(Predicate<T> test);

    T get() throws NoSuchElementException;

    T orElse(T value);

    <E extends RuntimeException> T orElseThrow(Supplier<E> ex) throws E;

}
