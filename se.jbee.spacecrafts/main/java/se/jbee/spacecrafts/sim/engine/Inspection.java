package se.jbee.spacecrafts.sim.engine;

/**
 * Process game state to find situations that need a decision to be made.
 * <p>
 * This is used both to present these situations to human player and to have AA
 * make a decision.
 * <p>
 * This implies that no state is kept on what needs task. This is entirely
 * derived from the state of game {@link se.jbee.spacecrafts.sim.engine.Any.Entity}s.
 * <p>
 * In addition this allows to customise the behaviour quite simple by using
 * different lists of active {@link Inspection}s for human players (their
 * choice) and for AA (e.g. different sets for different AA difficulties).
 */
public interface Inspection {

}
