package se.jbee.spacecrafts.sim.decision;

import se.jbee.spacecrafts.sim.Game;
import se.jbee.spacecrafts.sim.Governing;
import se.jbee.spacecrafts.sim.engine.Decision;

public interface GoverningDecisions {

    record HireLeader() implements Governing, Decision {

        @Override
        public void enforceIn(Game game, Enforcer enforcer) {

        }
    }

    record DismissLeader() implements Governing, Decision {

        @Override
        public void enforceIn(Game game, Enforcer enforcer) {

        }
    }
}
