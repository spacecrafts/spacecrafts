package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.Any.Text;
import se.jbee.spacecrafts.sim.Conquering.Colony;
import se.jbee.spacecrafts.sim.Governing.Fraction;
import se.jbee.spacecrafts.sim.Governing.Governed;

public interface Processing {

    //TODO how to limit an influence in time/turns?
    record Temporary() {}

    interface Event<T extends Event<T>> extends Any.Computed {

        void handle(Game game, T e);
    }

    record FoundColonyEvent(
            Fraction founder,
            Text name
    ) implements Event<FoundColonyEvent> {

        @Override
        public void handle(Game game, FoundColonyEvent e) {
            Colony founded = game.entities().colonies().add(serial -> new Colony(
                    new Governed(serial, name, founder),
                    null));
            e.founder().governed().colonies().add(founded);
        }
    }

}
