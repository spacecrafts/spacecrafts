package se.jbee.turnmaster;

import java.util.function.Function;

public interface Decision<G extends Engine.Game> extends Any.Computed {

    interface Byproduct<G extends Engine.Game, T> extends Decision<G> {

        @Override
        default void manifestIn(G game, Karma<G> karma) {
            andManifestIn(game, karma);
        }

        T andManifestIn(G game, Karma<G> karma);
    }

    interface Karma<G extends Engine.Game> {

        //Maybe have a boolean arg to say if the decision is optional
        // to have the player chose if that part should happen as well
        <G extends Engine.Game, E extends Record & Decision<G>> void manifest(E e);

        <G extends Engine.Game, T, E extends Record & Byproduct<G, T>> T andManifest(E e);

        default <G extends Engine.Game, T, E extends Record & Decision<G>> void manifest(Function<T, E> newDecision, Optional<T> source) {
            if (source.isSome()) manifest(newDecision.apply(source.get()));
        }
    }

    void manifestIn(G game, Karma<G> karma);

    /**
     * @return the turn in which to apply the event, or -1 if it should be
     * applied immediately
     */
    default int turn() {
        return -1;
    }

    /**
     * A generic wrapper to make any other event a future event in a specific
     * turn.
     */
    record DelayedDecision<G extends Engine.Game, E extends Record & Decision<G>>(
        E of,
        int turn
    ) implements Decision<G> {

        @Override
        public void manifestIn(G game, Karma<G> karma) {
            karma.manifest(of);
        }
    }
}
