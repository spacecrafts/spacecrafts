package se.jbee.spacecrafts.sim.engine;

import se.jbee.spacecrafts.sim.Game;

public interface Decision extends Any.Computed {

    interface Enforcer {

        <E extends Record & Decision> void enforce(E e);
    }

    void enforceIn(Game game, Enforcer enforcer);

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
    record DelayedDecision<E extends Record & Decision>(
            E of,
            int turn
    ) implements Decision {
        @Override
        public void enforceIn(Game game, Enforcer enforcer) {
            enforcer.enforce(of);
        }
    }
}
