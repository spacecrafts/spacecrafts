package se.jbee.spacecrafts.sim.deduction;

import se.jbee.spacecrafts.sim.Conquering;
import se.jbee.spacecrafts.sim.Game;
import se.jbee.spacecrafts.sim.Game.Deduction;
import se.jbee.turnmaster.Engine.Flow;
import se.jbee.turnmaster.data.Any.Property;
import se.jbee.turnmaster.data.Register;

public interface ExploringDeductions {

    record MovingFleets(
        Register<Fleet> fleets,
        Property speed
    ) implements Conquering, Deduction {

        @Override
        public void manifest(Flow<Game> flow) {
            fleets.forEach(this::move);
        }

        private void move(Fleet fleet) {
            if (!fleet.destination().isSome()) return;
            var solarSystem = fleet.destination().get();
            var target = solarSystem.at();
            fleet.location().update(
                pos -> pos.movedTowards(target, fleet.actuals().get(speed)));
            if (fleet.location().isEqual(target)) {
                solarSystem.fleets().add(fleet);
                fleet.destination().clear();
                //TODO might have spotted SS
            }
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
