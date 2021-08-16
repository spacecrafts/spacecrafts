package se.jbee.spacecrafts.sim.engine;

import se.jbee.spacecrafts.sim.Game;

public interface Event extends Any.Computed {

    interface Dispatcher {

        <E extends Record & Event> void dispatch(E e);
    }

    void applyTo(Game game, Dispatcher dispatcher);

    /**
     * @return the turn in which to apply the event, or -1 if it should be
     * applied immediately
     */
    default int turn() {
        return -1;
    }

    /**
     * A generic wrapper to make any other event a future event in a specific
     * turn.
     */
    record FutureEvent<E extends Record & Event>(
            E of,
            int turn
    ) implements Event {
        @Override
        public void applyTo(Game game, Dispatcher dispatcher) {
            dispatcher.dispatch(of);
        }
    }
}
