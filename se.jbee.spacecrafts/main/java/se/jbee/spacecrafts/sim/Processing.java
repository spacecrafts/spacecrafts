package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.Conquering.Colony;
import se.jbee.spacecrafts.sim.Governing.Fraction;
import se.jbee.spacecrafts.sim.Governing.Governed;
import se.jbee.spacecrafts.sim.engine.Any;
import se.jbee.spacecrafts.sim.engine.Any.Text;

public interface Processing {

    interface EventDispatcher {

        void dispatch(Event e);
    }

    interface Event extends Any.Computed {

        void handle(Game game, EventDispatcher dispatcher);

        /**
         * @return the turn in which to apply the event, or -1 if it should be
         * applied immediately
         */
        default int turn() {
            return -1;
        }
    }

    /**
     * A generic wrapper to make any other event a future event in a specific
     * turn.
     */
    record FutureEvent(
            Event of,
            int turn
    ) implements Event {
        @Override
        public void handle(Game game, EventDispatcher dispatcher) {
            dispatcher.dispatch(of);
        }
    }

    record FoundColonyEvent(
            Fraction founder,
            Text name
    ) implements Event {

        @Override
        public void handle(Game game, EventDispatcher dispatcher) {
            Colony founded = game.entities().colonies().add(serial -> new Colony(
                    new Governed(serial, name, founder),
                    null,
                    null));
            founder.governed().colonies().add(founded);
        }
    }

}
