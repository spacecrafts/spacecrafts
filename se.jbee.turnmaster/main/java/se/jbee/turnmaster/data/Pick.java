package se.jbee.turnmaster.data;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * {@link Pick} is read-only API from a sealed {@link Q}.
 */
public interface Pick<T> extends Collection<T> {

    static <T> Pick<T> ofDefault(T[] items) {
        return Q.<T>newDefault(items.length).concat(items).seal();
    }

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
