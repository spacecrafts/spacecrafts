package se.jbee.spacecrafts.sim.eventuality;

import se.jbee.spacecrafts.sim.Conquering;
import se.jbee.turnmaster.data.Register;
import se.jbee.turnmaster.eval.Eventuality;

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
