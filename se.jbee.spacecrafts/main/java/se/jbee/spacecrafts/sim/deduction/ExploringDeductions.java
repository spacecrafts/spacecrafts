package se.jbee.spacecrafts.sim.deduction;

import se.jbee.spacecrafts.sim.Conquering;
import se.jbee.turnmaster.data.Register;
import se.jbee.turnmaster.eval.Deduction;

public class ExploringDeductions {

    record MovingFleets(Register<Fleet> fleets) implements Conquering, Deduction {

        @Override
        public void manifest() {

        }
    }

    record SpottingSolarSystems() implements Conquering, Deduction {

        @Override
        public void manifest() {

        }
    }

    record SpottingPlanets() implements Conquering, Deduction {

        @Override
        public void manifest() {

        }
    }

    record SpottingMoons() implements Conquering, Deduction {

        @Override
        public void manifest() {

        }
    }
}
