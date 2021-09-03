package se.jbee.turnmaster.eval;

import se.jbee.turnmaster.Engine;
import se.jbee.turnmaster.data.Any;
import se.jbee.turnmaster.data.Numbers;
import se.jbee.turnmaster.data.Pick;
import se.jbee.turnmaster.data.Q;

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

    /**
     * {@link Deduction}s might be installed "out of order" in regard to their
     * desired execution order since the order of modules installed might be
     * different to the order in which {@link Deduction}s should be made.
     * Therefore, the definitive order of {@link Deduction}s - as used by the
     * game - is first computable when all modules have been installed.
     *
     * @param <G>
     */
    record Item<G extends Engine.Game>(
        Deduction<G> of,
        Pick<Class<? extends Deduction<G>>> before,
        Pick<Class<? extends Deduction<G>>> after
    ) {}

    interface Sequence<G extends Engine.Game> {

        void add(Item<G> item);

        default void add(Deduction<G> deduction) {
            add(new Item<>(deduction, Q.empty(), Q.empty()));
        }

        @SuppressWarnings("unchecked")
        default void addBefore(Deduction<G> deduction, Class<? extends Deduction<G>>... before) {
            add(new Item<>(deduction, Pick.ofDefault(before), Q.empty()));
        }

        @SuppressWarnings("unchecked")
        default void addAfter(Deduction<G> deduction, Class<? extends Deduction<G>>... after) {
            add(new Item<>(deduction, Q.empty(), Pick.ofDefault(after)));
        }
    }
}
