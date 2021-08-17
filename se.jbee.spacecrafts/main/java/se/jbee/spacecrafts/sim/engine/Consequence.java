package se.jbee.spacecrafts.sim.engine;

import se.jbee.spacecrafts.sim.Game;

/**
 * {@link Consequence}s are evaluated at the beginning of a turn before players
 * start making {@link Decision}s. They implement the effects given by the facts
 * of the game state.
 * <p>
 * The state of a {@link Consequence} is what is needed in order to identify a
 * situation that has the particular {@link Consequence}. Usually these are
 * {@link se.jbee.spacecrafts.sim.engine.Any.Property}s and other {@link
 * se.jbee.spacecrafts.sim.engine.Any.Definition}s needed to extract the {@link
 * Numbers} to check.
 */
public interface Consequence {

    @FunctionalInterface
    interface Supplier {
        Consequence prepareFor(Game game);
    }

    void manifest();
}
