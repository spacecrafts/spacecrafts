package se.jbee.spacecrafts.sim.engine;

import se.jbee.spacecrafts.sim.Game;

public interface EngineModule {

    Class<? extends EngineModule> id();

    Q<Class<? extends EngineModule>> requires();

    void installIn(Game game, Engine engine);
}
