package se.jbee.spacecrafts.sim.engine;

import se.jbee.spacecrafts.sim.Game;

/**
 * {@link Consequence}s are evaluated at the beginning of a turn before players
 * start making {@link Decision}s. They implement the implications or effects
 * given by the facts of the game state.
 * <p>
 * The state of a {@link Consequence} is what is needed in order to identify a
 * situation that has the particular {@link Consequence} for the game state.
 * <p>
 * Usually this state consists of {@link se.jbee.spacecrafts.sim.engine.Any.Definition}s
 * that need lookup by {@link se.jbee.spacecrafts.sim.engine.Any.Code} to needed
 * to read/write the {@link Numbers} checked or modified.
 */
public interface Consequence {

    @FunctionalInterface
    interface Binder {
        Consequence bindTo(Game game);
    }

    void manifest();
}
