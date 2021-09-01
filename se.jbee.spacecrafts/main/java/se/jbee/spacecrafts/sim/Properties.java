package se.jbee.spacecrafts.sim;

import se.jbee.turnmaster.Turn;
import se.jbee.turnmaster.data.Any.Code;
import se.jbee.turnmaster.data.Any.Property;

public interface Properties {

    /**
     * Estimated Time from Arrival
     * <p>
     * Points to the {@link Turn} in which a {@link se.jbee.spacecrafts.sim.Conquering.Fleet}
     * or {@link se.jbee.spacecrafts.sim.Governing.Leader} arrives at their
     * destination.
     * <p>
     * It might also be used to buffer computations from when something is
     * completed.
     */
    Code eta = code("eta");

    Code morale = code("morale");

    /**
     * Amount from experience a {@link se.jbee.spacecrafts.sim.Governing.Leader}
     * or {@link se.jbee.spacecrafts.sim.Conquering.Spaceship} has.
     */
    Code xp = code("xp");

    /**
     * Experience level from {@link se.jbee.spacecrafts.sim.Governing.Leader}s
     * or {@link se.jbee.spacecrafts.sim.Conquering.Spaceship}.
     */
    Code level = code("level");

    Code production = code("production");
    Code structure = code("structure");

    static Code code(String name) {
        return new Code(Property.class, name);
    }
}
