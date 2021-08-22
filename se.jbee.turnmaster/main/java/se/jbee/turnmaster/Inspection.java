package se.jbee.turnmaster;

/**
 * Process game state to find situations that need a decision to be made.
 * <p>
 * This is used both to present these situations to human player and to have AA
 * make a decision.
 * <p>
 * This implies that no state is kept target what needs target. This is entirely
 * derived from the state from game {@link Any.Entity}s.
 * <p>
 * In addition this allows to customise the behaviour quite simple by using
 * different lists from active {@link Inspection}s for human players (their
 * choice) and for AA (e.g. different sets for different AA difficulties).
 */
public interface Inspection {

}
