package se.jbee.turnmaster;

import se.jbee.turnmaster.data.Any.Connectable;
import se.jbee.turnmaster.data.Flux;
import se.jbee.turnmaster.data.Index;
import se.jbee.turnmaster.data.NumberPer;
import se.jbee.turnmaster.data.Numbers;
import se.jbee.turnmaster.data.Optional;
import se.jbee.turnmaster.data.Per;
import se.jbee.turnmaster.data.Pick;
import se.jbee.turnmaster.data.Pools;
import se.jbee.turnmaster.data.Q;
import se.jbee.turnmaster.data.Range;
import se.jbee.turnmaster.data.Register;
import se.jbee.turnmaster.data.TagPer;
import se.jbee.turnmaster.data.Tags;
import se.jbee.turnmaster.data.Top;
import se.jbee.turnmaster.data.Vary;
import se.jbee.turnmaster.data.XY;
import se.jbee.turnmaster.eval.Decision;
import se.jbee.turnmaster.eval.Deduction;
import se.jbee.turnmaster.eval.Inspection;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * State from the game engine - this is the state that is independent of any
 * {@link Game}.
 */
public interface Engine {

    interface Game {

        Runtime runtime();

        Turn turn();

        RNG rng();
    }

    // 1. load all Modules and Bundles via ServiceLoader
    // 2. build Engine record (bare Modules in synthetic Bundle Kit)
    // 3. user selects kits and features based target Engine
    // 4. when user starts a game selected features get installed in order

    /**
     * Represents the possible options to chose from when creating a {@link
     * Runtime}. These can only be changed before loading/starting a new {@link
     * Game}.
     */
    record Configuration(
        Pick<Kit> kits,
        Top<Option<Pools.Factory>> poolOptions,
        Top<Option<Q.Factory>> qOptions,
        Top<Option<Top.Factory>> topOptions,
        Top<Option<Register.Factory>> registerOptions,
        Top<Option<Index.Factory>> indexOptions,
        Top<Option<Index.Factory>> rangeOptions,
        Top<Option<Flux.Factory>> fluxOptions,
        Top<Option<Tags.Factory>> tagOptions,
        Top<Option<TagPer.Factory>> tagPerOptions,
        Top<Option<Numbers.Factory>> numbersOptions,
        Top<Option<NumberPer.Factory>> numberPerOptions,
        Top<Option<Per.Factory>> perOptions,
        Top<Option<XY.Factory>> xyOptions
    ) {}

    /**
     * A {@link Runtime} is the engine as used by a particular {@link Game}. It
     * contains the chosen options. These can be changed for an existing
     * save-game.
     */
    record Runtime(
        Pools.Factory newPools,
        Q.Factory newQ,
        Top.Factory newTop,
        Register.Factory newRegister,
        Index.Factory newIndex,
        Range.Factory newRange,
        Flux.Factory newFlux,
        Tags.Factory newTags,
        TagPer.Factory newTagPer,
        Numbers.Factory newNumbers,
        NumberPer.Factory newNumberPer,
        Per.Factory newPer,
        XY.Factory newXY
    ) {}

    /**
     * A {@link Kit} is a semantic package from game functionality.
     */
    record Kit(
        Class<? extends Bundle> from,
        String name,
        Vary<Boolean> disabled,
        Pick<Feature> features
    ) implements Connectable {}

    record Feature(
        Class<? extends Bundle> in,
        Class<? extends Module<?>> from,
        String name,
        Vary<Boolean> disabled,
        Pick<Feature> requires,
        Pick<Feature> transitives
    ) implements Connectable {}

    record Option<T>(
        String name,
        String desc,
        T of
    ) {}

    record Loop<G extends Game>(
        Deduction.Sequence<G> atStartOfTurn,
        Inspection.Sequence<G> duringTurn,
        Deduction.Sequence<G> atEndOfTurn
    ) {}

    /**
     * A engine setup is a semantic set of {@link Bundle}s. {@link Bundle}s are
     * located and loaded in no particular order using the {@link
     * java.util.ServiceLoader}.
     */
    interface Bundle {

        default Class<? extends Bundle> id() {
            return getClass();
        }

        default Pick<Class<? extends Module<?>>> modules() {
            return Q.empty();
        }

        default void setup(Configuration config) {
            // by default: nothing to do
        }
    }

    interface Module<G extends Game> {

        @SuppressWarnings("unchecked")
        default Class<? extends Module<G>> id() {
            return (Class<? extends Module<G>>) getClass();
        }

        /**
         * @return set of modules this module needs to be installed before this
         * module can be installed
         */
        default Pick<Class<? extends Module<G>>> requires() {
            return Q.empty();
        }

        /**
         * @return set of modules that needs to be installed after this module
         * was installed
         */
        default Pick<Class<? extends Module<G>>> supports() {
            return Q.empty();
        }

        default void installIn(G game) {
            // by default: nothing to do
        }
    }

    interface Flow<G extends Game> {

        //Maybe have a boolean arg to say if the decision is optional
        // to have the player chose if that part should happen as well
        <D extends Record & Decision<G>> void manifest(D decision);

        <T, D extends Record & Decision.Implication<G, T>> T andManifest(D decision);

        default <T, E extends Record & Decision<G>> void manifest(Function<T, E> newDecision, Optional<T> source) {
            if (source.isSome()) manifest(newDecision.apply(source.get()));
        }

        default <T, E extends Record & Decision<G>> void manifest(T value, Function<T, E> newDecision, Predicate<T> when) {
            if (when.test(value)) manifest(newDecision.apply(value));
        }

        default <T, E extends Record & Decision<G>> void manifest(T value, Function<T, E> newDecision, boolean when) {
            if (when) manifest(newDecision.apply(value));
        }
    }

}
