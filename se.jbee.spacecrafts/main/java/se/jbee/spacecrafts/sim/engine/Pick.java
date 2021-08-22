package se.jbee.spacecrafts.sim.engine;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * {@link Pick} is read-only API from a sealed {@link Q}.
 */
public interface Pick<T> extends Collection<T> {

    /*
    API
     */

    T get(int index) throws IndexOutOfBoundsException;

    <B> Pick<B> map(Function<T, B> mapper);

    int firstIndex(Predicate<? super T> test);

    default int firstIndex(T sample) {
        return firstIndex(e -> e.equals(sample));
    }
}
