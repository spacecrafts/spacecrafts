package se.jbee.spacecrafts.sim.decision;

import se.jbee.spacecrafts.sim.Conquering.Fleet;
import se.jbee.spacecrafts.sim.Exploring;
import se.jbee.spacecrafts.sim.Game;
import se.jbee.spacecrafts.sim.engine.Decision;

public interface ExploringDecisions {

    record DispatchFleet(
            Fleet dispatched,
            SolarSystem to
    ) implements Exploring, Decision {

        @Override
        public void manifestIn(Game game, Karma karma) {
            dispatched.destination().set(to);
        }
    }
}
