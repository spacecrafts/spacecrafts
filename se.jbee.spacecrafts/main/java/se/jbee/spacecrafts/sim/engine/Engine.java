package se.jbee.spacecrafts.sim.engine;

import se.jbee.spacecrafts.sim.Game;
import se.jbee.spacecrafts.sim.engine.Any.Connectable;

/**
 * State of the game engine - this is the state that is independent of any
 * {@link Game}.
 */
public interface Engine {

    //TODO move to se.jbee.turnmaster package

    // 1. load all Modules and Bundles via ServiceLoader
    // 2. build Engine record (bare Modules in synthetic Bundle Kit)
    // 3. user selects kits and features based on Engine
    // 4. when user starts a game selected features get installed in order

    /**
     * Represents the possible options to chose from when creating a {@link
     * Runtime}. These can only be changed before loading/starting a new {@link
     * Game}.
     */
    record Configuration(
            Pick<Kit> kits,
            Top<Option<Pools.Factory>> newPools,
            Top<Option<Q.Factory>> newQs,
            Top<Option<Top.Factory>> newTops,
            Top<Option<Register.Factory>> newRegisters,
            Top<Option<Index.Factory>> newIndexes,
            Top<Option<Index.Factory>> newRanges,
            Top<Option<Flux.Factory>> newFluxes,
            Top<Option<Marks.Factory>> newMarks,
            Top<Option<Numbers.Factory>> newNumbers,
            Top<Option<NumberPer.Factory>> newNumberPer
    ) {}

    /**
     * A {@link Runtime} is the engine as used by a particular {@link Game}
     */
    record Runtime(
            Pools.Factory newPools,
            Q.Factory newQ,
            Top.Factory newTop,
            Register.Factory newRegister,
            Index.Factory newIndex,
            Range.Factory newRange,
            Flux.Factory newFlux,
            Marks.Factory newMarks,
            Numbers.Factory newNumbers,
            NumberPer.Factory newNumberPer
    ) {}

    /**
     * A {@link Kit} is a semantic package of game functionality.
     */
    record Kit(
            Class<? extends Bundle> from,
            String name,
            Vary<Boolean> disabled,
            Pick<Feature> features
    ) implements Connectable {}

    record Feature(
            Class<? extends Bundle> in,
            Class<? extends Module> from,
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

    interface Bundle {

        default Class<? extends Bundle> id() {
            return getClass();
        }

        default Pick<Class<? extends Module>> modules() {
            return Q.empty();
        }

        default void setup(Configuration config) {
            // by default: nothing to do
        }
    }

    interface Module {

        default Class<? extends Module> id() {
            return getClass();
        }

        default Pick<Class<? extends Module>> requires() {
            return Q.empty();
        }

        default void installIn0(Game game) {
            // by default: nothing to do
        }

        default void installIn1(Game game) {
            // by default: nothing to do
        }
    }

    interface Starter<G> {

        G newGame();
    }

}
