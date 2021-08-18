package se.jbee.spacecrafts.sim.consequence;

import se.jbee.spacecrafts.sim.Conquering;
import se.jbee.spacecrafts.sim.engine.Consequence;

public interface ConqueringConsequences {

    record MovingFleets() implements Conquering, Consequence {

        @Override
        public void manifest() {

        }
    }

    record SpottingSolarSystems() implements Conquering, Consequence {

        @Override
        public void manifest() {

        }
    }

    record SpottingPlanets() implements Conquering, Consequence {

        @Override
        public void manifest() {

        }
    }

    record SpottingMoons() implements Conquering, Consequence {

        @Override
        public void manifest() {

        }
    }
}
