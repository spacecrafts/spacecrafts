package se.jbee.spacecrafts.sim.decision;

import se.jbee.spacecrafts.sim.Conquering.Fleet;
import se.jbee.spacecrafts.sim.Exploring;
import se.jbee.spacecrafts.sim.Game;
import se.jbee.spacecrafts.sim.Game.Decision;

public interface ExploringDecisions {

    record DispatchFleet(
        Fleet dispatched,
        SolarSystem to
    ) implements Exploring, Decision {

        @Override
        public void manifestIn(Game game, Karma<Game> karma) {
            dispatched.destination().set(to);
        }
    }
}
