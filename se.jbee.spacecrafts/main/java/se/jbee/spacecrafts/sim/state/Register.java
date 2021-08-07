package se.jbee.spacecrafts.sim.state;

import se.jbee.spacecrafts.sim.Any;

/**
 * A {@link Register} is a {@link Pool} of {@link se.jbee.spacecrafts.sim.Any.Creation}s
 * that also allows to {@link #remove(int)} elements. Removed serials might get
 * reused.
 *
 * @param <T>
 */
public interface Register<T extends Any.Creation> extends Pool<T> {

    T remove(int serial) throws IllegalStateException;

}
