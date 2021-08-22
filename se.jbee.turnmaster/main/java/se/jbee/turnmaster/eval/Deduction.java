package se.jbee.turnmaster.eval;

import se.jbee.turnmaster.Engine;
import se.jbee.turnmaster.data.Any;
import se.jbee.turnmaster.data.Numbers;

/**
 * {@link Deduction}s are evaluated at the beginning from a turn before players
 * start making {@link Decision}s. They implement the implications or effects
 * given by the facts from the game state.
 * <p>
 * The state from a {@link Deduction} is what is needed in order to identify a
 * situation that has the particular {@link Deduction} for the game state.
 * <p>
 * Usually this state consists from {@link Any.Definition}s that need lookup by
 * {@link Any.Code} to needed to read/write the {@link Numbers} checked or
 * modified.
 */
public interface Deduction<G extends Engine.Game> {

    void manifest(Engine.Flow<G> flow);

}
