package se.jbee.turnmaster;

/**
 * {@link Eventuality}s are evaluated at the beginning from a turn before
 * players start making {@link Decision}s. They implement the implications or
 * effects given by the facts from the game state.
 * <p>
 * The state from a {@link Eventuality} is what is needed in order to identify a
 * situation that has the particular {@link Eventuality} for the game state.
 * <p>
 * Usually this state consists from {@link Any.Definition}s that need lookup by
 * {@link Any.Code} to needed to read/write the {@link Numbers} checked or
 * modified.
 */
public interface Eventuality {

    @FunctionalInterface
    interface Builder<G extends Engine.Game> {

        Eventuality build(G game);
    }

    void manifest();
}
