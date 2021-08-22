package se.jbee.spacecrafts.sim.decision;

import se.jbee.spacecrafts.sim.Conquering.Fleet;
import se.jbee.spacecrafts.sim.Exploring;
import se.jbee.spacecrafts.sim.Game;
import se.jbee.spacecrafts.sim.Game.Decision;
import se.jbee.turnmaster.Engine.Flow;

public interface ExploringDecisions {

    record DispatchFleet(
        Fleet dispatched,
        SolarSystem to
    ) implements Exploring, Decision {

        @Override
        public void manifestIn(Game game, Flow<Game> flow) {
            dispatched.destination().set(to);
        }
    }
}
