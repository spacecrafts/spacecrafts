package se.jbee.spacecrafts.sim.engine;

import se.jbee.spacecrafts.sim.Game;

/**
 * State of the game engine - this is the state that is independent of any
 * {@link Game}.
 */
public record Engine(
        Q<Mod> mods,
        Factories factories
) {

    /**
     * A {@link Mod} is a package of code and data.
     */
    public record Mod(
            Class<? extends EngineBundle> from,
            String name,
            Q<Feature> features
    ) {}

    public record Feature(
            Class<? extends EngineModule> from,
            String name
    ) {}

    public record Factories() {}

    public interface EngineBundle {

        String name();

        Class<? extends EngineModule> modules();
    }

    public interface EngineModule {

        Class<? extends EngineModule> id();

        Q<Class<? extends EngineModule>> requires();

        void installIn(Game game, Engine engine);
    }
}
