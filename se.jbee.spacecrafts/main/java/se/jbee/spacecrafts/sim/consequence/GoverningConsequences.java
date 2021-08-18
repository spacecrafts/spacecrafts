package se.jbee.spacecrafts.sim.consequence;

import se.jbee.spacecrafts.sim.Governing;
import se.jbee.spacecrafts.sim.Properties;
import se.jbee.spacecrafts.sim.engine.Any.Property;
import se.jbee.spacecrafts.sim.engine.Consequence;
import se.jbee.spacecrafts.sim.engine.Consequence.Binder;
import se.jbee.spacecrafts.sim.engine.Register;

public interface GoverningConsequences {

    Binder ResigningLeaders = game -> new ResigningLeaders(
            game.objects().properties().get(Properties.morale));

    Binder RetiringLeaders = game -> new RetiringLeaders(
            game.objects().leaders());

    record NoticingLeaders() implements Governing, Consequence {

        @Override
        public void manifest() {

        }
    }

    record ResigningLeaders(Property morale) implements Governing, Consequence {
        @Override
        public void manifest() {
            // TODO if morale is too low => resign
            // TODO if affection for another fraction is much higher => resign
        }
    }

    record RetiringLeaders(Register<Leader> leaders) implements Governing, Consequence {

        @Override
        public void manifest() {

        }
    }

    record ArisingLeaders() implements Governing, Consequence {

        @Override
        public void manifest() {
            
        }
    }
}
