package se.jbee.spacecrafts.sim.eventuality;

import se.jbee.spacecrafts.sim.Conquering;
import se.jbee.spacecrafts.sim.engine.Eventuality;
import se.jbee.spacecrafts.sim.engine.Register;

public class ExploringEventualities {

    record MovingFleets(Register<Fleet> fleets) implements Conquering, Eventuality {

        @Override
        public void manifest() {

        }
    }

    record SpottingSolarSystems() implements Conquering, Eventuality {

        @Override
        public void manifest() {

        }
    }

    record SpottingPlanets() implements Conquering, Eventuality {

        @Override
        public void manifest() {

        }
    }

    record SpottingMoons() implements Conquering, Eventuality {

        @Override
        public void manifest() {

        }
    }
}
