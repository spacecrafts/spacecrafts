package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.engine.Any.Code;

public interface Properties {

    /**
     * Estimated Time of Arrival
     * <p>
     * Points to the {@link se.jbee.spacecrafts.sim.engine.Turn} in which a
     * {@link se.jbee.spacecrafts.sim.Conquering.Fleet} or {@link
     * se.jbee.spacecrafts.sim.Governing.Leader} arrives at their destination.
     * <p>
     * It might also be used to buffer computations of when something is
     * completed.
     */
    Code eta = new Code("eta");

    Code morale = new Code("morale");

    /**
     * Amount of experience a {@link se.jbee.spacecrafts.sim.Governing.Leader}
     * or {@link se.jbee.spacecrafts.sim.Conquering.Spaceship} has.
     */
    Code xp = new Code("xp");

    /**
     * Experience level of {@link se.jbee.spacecrafts.sim.Governing.Leader}s or
     * {@link se.jbee.spacecrafts.sim.Conquering.Spaceship}.
     */
    Code level = new Code("level");
}
