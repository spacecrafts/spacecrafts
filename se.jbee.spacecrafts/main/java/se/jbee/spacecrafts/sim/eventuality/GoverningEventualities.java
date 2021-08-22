package se.jbee.spacecrafts.sim.eventuality;

import se.jbee.spacecrafts.sim.Game.Builder;
import se.jbee.spacecrafts.sim.Governing;
import se.jbee.spacecrafts.sim.Properties;
import se.jbee.turnmaster.Any.Property;
import se.jbee.turnmaster.data.Register;
import se.jbee.turnmaster.eval.Eventuality;

public interface GoverningEventualities {

    Builder ResigningLeaders = game -> new ResigningLeaders(
        game.objects().properties().get(Properties.morale));

    Builder RetiringLeaders = game -> new RetiringLeaders(
        game.objects().leaders());

    record NoticingLeaders() implements Governing, Eventuality {

        @Override
        public void manifest() {

        }
    }

    record ResigningLeaders(Property morale) implements Governing, Eventuality {

        @Override
        public void manifest() {
            // TODO if morale is too low => resign
            // TODO if affection for another fraction is much higher => resign
        }
    }

    record RetiringLeaders(Register<Leader> leaders) implements Governing, Eventuality {

        @Override
        public void manifest() {

        }
    }

    record ArisingLeaders() implements Governing, Eventuality {

        @Override
        public void manifest() {

        }
    }
}
