package se.jbee.spacecrafts.sim.engine;

public interface EngineBundle {

    String name();

    Class<? extends EngineModule> modules();
}
