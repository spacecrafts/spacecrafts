package se.jbee.spacecrafts.sim.collection;

import se.jbee.spacecrafts.sim.Any;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;

/**
 * A {@link Pool} is a set where elements accessed are expected to exist.
 * <p>
 * Each {@link Pool} manages its serial numbers (IDs). Serials may be reused
 * when {@link #remove(int)}ed.
 * <p>
 * The serial assigned to a newly {@link #add(IntFunction)}ed element is
 * determined and returned when adding the element.
 *
 * @param <T>
 */
public interface Pool<T extends Any.Entity> {

    int size();

    int span();

    default boolean isEmpty() {
        return size() == 0;
    }

    T get(int serial) throws NoSuchElementException;

    T add(IntFunction<T> factory) throws IllegalStateException;

    T remove(int serial) throws IllegalStateException;

    void forEach(Consumer<? super T> f);

    T first(Predicate<? super T> test) throws NoSuchElementException;

}