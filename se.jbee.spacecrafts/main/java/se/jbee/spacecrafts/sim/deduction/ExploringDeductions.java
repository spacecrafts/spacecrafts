package se.jbee.spacecrafts.sim.deduction;

import se.jbee.spacecrafts.sim.Conquering;
import se.jbee.spacecrafts.sim.Game;
import se.jbee.spacecrafts.sim.Game.Deduction;
import se.jbee.turnmaster.Engine.Flow;
import se.jbee.turnmaster.data.Register;

public class ExploringDeductions {

    record MovingFleets(Register<Fleet> fleets) implements Conquering, Deduction {

        @Override
        public void manifest(Flow<Game> flow) {

        }
    }

    record SpottingSectors() implements Conquering, Deduction {

        @Override
        public void manifest(Flow<Game> flow) {

        }
    }

    record SpottingSolarSystems() implements Conquering, Deduction {

        @Override
        public void manifest(Flow<Game> flow) {

        }
    }

    record SpottingPlanets() implements Conquering, Deduction {

        @Override
        public void manifest(Flow<Game> flow) {

        }
    }

    record SpottingMoons() implements Conquering, Deduction {

        @Override
        public void manifest(Flow<Game> flow) {

        }
    }
}
