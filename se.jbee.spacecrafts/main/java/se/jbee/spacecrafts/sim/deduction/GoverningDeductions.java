package se.jbee.spacecrafts.sim.deduction;

import se.jbee.spacecrafts.sim.Game;
import se.jbee.spacecrafts.sim.Game.Deducting;
import se.jbee.spacecrafts.sim.Game.Deduction;
import se.jbee.spacecrafts.sim.Governing;
import se.jbee.spacecrafts.sim.Properties;
import se.jbee.turnmaster.Engine.Flow;
import se.jbee.turnmaster.data.Any.Property;
import se.jbee.turnmaster.data.Register;

public interface GoverningDeductions {

    Deducting ResigningLeaders = game -> new ResigningLeaders(
        game.objects().properties().get(Properties.morale));

    Deducting RetiringLeaders = game -> new RetiringLeaders(
        game.objects().leaders());

    record NoticingLeaders() implements Governing, Deduction {

        @Override
        public void manifest(Flow<Game> flow) {

        }
    }

    record ResigningLeaders(Property morale) implements Governing, Deduction {

        @Override
        public void manifest(Flow<Game> flow) {
            // TODO if morale is too low => resign
            // TODO if affection for another fraction is much higher => resign
        }
    }

    record RetiringLeaders(Register<Leader> leaders) implements Governing, Deduction {

        @Override
        public void manifest(Flow<Game> flow) {

        }
    }

    record ArisingLeaders() implements Governing, Deduction {

        @Override
        public void manifest(Flow<Game> flow) {

        }
    }
}
