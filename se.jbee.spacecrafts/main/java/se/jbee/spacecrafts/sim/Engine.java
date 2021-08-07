package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.Any.Code;
import se.jbee.spacecrafts.sim.state.Box;
import se.jbee.spacecrafts.sim.state.Q;

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
            String name,
            Code code
    ) {}

    public record Factories(Box<Factory.RegisterFactory> newPool) {}
}
