package se.jbee.spacecrafts.sim.decision;

import se.jbee.spacecrafts.sim.Game;
import se.jbee.spacecrafts.sim.Game.Decision;
import se.jbee.spacecrafts.sim.Governing;
import se.jbee.spacecrafts.sim.Properties;
import se.jbee.turnmaster.Engine.Flow;

public interface GoverningDecisions {

    record HireLeader(
        Leader hired,
        Fraction by
    ) implements Governing, Decision {

        @Override
        public void manifestIn(Game game, Flow<Game> flow) {
            by.governed().leaders().add(hired);
        }
    }

    record DismissLeader(
        Leader dismissed,
        Fraction by
    ) implements Governing, Decision {

        @Override
        public void manifestIn(Game game, Flow<Game> flow) {
            by.governed().leaders().remove(dismissed);
        }
    }

    record AssignLeader(
        Leader assigned,
        Asset to
    ) implements Governing, Decision {

        @Override
        public void manifestIn(Game game, Flow<Game> flow) {
            var prior = assigned.assignment().set(to);
            var eta = game.objects().properties().get(Properties.eta);
            //TODO calculate actual eta costs
            assigned.profile().set(eta, game.turn().current() + 5);
        }
    }

    record DischargeLeader(Leader discharged) implements Governing, Decision {

        @Override
        public void manifestIn(Game game, Flow<Game> flow) {
            discharged.assignment().set(null);
        }
    }

    record DischargeExistingLeader(Asset perished) implements Governing, Decision {

        @Override
        public void manifestIn(Game game, Flow<Game> flow) {
            flow.manifest(DischargeLeader::new, game.objects().leaders() //
                                                    .first(l -> l.assignment()
                                                                 .is(a -> a == perished)));
        }
    }
}
