package se.jbee.spacecrafts.sim.engine;

import java.util.NoSuchElementException;
import java.util.function.IntFunction;

/**
 * A {@link Pool} is a set where elements accessed are expected to exist.
 * <p>
 * Each {@link Pool} manages its serial numbers (IDs). Serials may be reused
 * when removed.
 * <p>
 * The serial assigned to a newly {@link #spawn(IntFunction)}ed element is
 * determined and returned when adding the element.
 *
 * @param <T> element type
 * @see Register
 * @see Index
 * @see Range
 */
public interface Pool<T extends Any.Entity> extends Collection<T> {

    Class<T> of();

    /**
     * @return Highest serial used so far
     */
    int span();

    T get(int serial) throws NoSuchElementException;

    T spawn(IntFunction<T> factory);

}
