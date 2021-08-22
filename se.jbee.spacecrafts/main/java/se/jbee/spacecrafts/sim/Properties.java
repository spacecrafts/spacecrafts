package se.jbee.spacecrafts.sim;

import se.jbee.turnmaster.Any.Code;
import se.jbee.turnmaster.Turn;

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
    Code eta = new Code("eta");

    Code morale = new Code("morale");

    /**
     * Amount from experience a {@link se.jbee.spacecrafts.sim.Governing.Leader}
     * or {@link se.jbee.spacecrafts.sim.Conquering.Spaceship} has.
     */
    Code xp = new Code("xp");

    /**
     * Experience level from {@link se.jbee.spacecrafts.sim.Governing.Leader}s
     * or {@link se.jbee.spacecrafts.sim.Conquering.Spaceship}.
     */
    Code level = new Code("level");
}
