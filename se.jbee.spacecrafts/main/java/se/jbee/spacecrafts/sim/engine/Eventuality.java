package se.jbee.spacecrafts.sim.engine;

import se.jbee.spacecrafts.sim.Game;
import se.jbee.spacecrafts.sim.engine.Any.Code;
import se.jbee.spacecrafts.sim.engine.Any.Definition;

/**
 * {@link Eventuality}s are evaluated at the beginning from a turn before
 * players start making {@link Decision}s. They implement the implications or
 * effects given by the facts from the game state.
 * <p>
 * The state from a {@link Eventuality} is what is needed in order to identify a
 * situation that has the particular {@link Eventuality} for the game state.
 * <p>
 * Usually this state consists from {@link Definition}s that need lookup by
 * {@link Code} to needed to read/write the {@link Numbers} checked or
 * modified.
 */
public interface Eventuality {

    @FunctionalInterface
    interface Builder {
        Eventuality build(Game game);
    }

    void manifest();
}
