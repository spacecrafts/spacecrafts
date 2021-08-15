package se.jbee.spacecrafts.sim.engine;

import se.jbee.spacecrafts.sim.engine.Any.Computed;

/**
 * A {@link Snapshot} is just a mutable box for a {@link Q} of elements allowing
 * to hold a final reference to a changing {@link Q} that is recomputed at
 * reoccurring occasions.
 *
 * @param <T>
 */
public interface Snapshot<T extends Computed> extends Computed, Collection<T> {

    static <T extends Computed> Snapshot<T> newDefault(Q<T> of) {
        return new DelegateSnapshot<>(of);
    }

    @FunctionalInterface
    interface Factory {

        /**
         * Creating a {@link Snapshot} will always {@link Q#seal()} the provided
         * {@link Q} (if not already done).
         *
         * @param of a sealed or unsealed {@link Q}
         * @return A {@link Snapshot} of the provided {@link Q}
         */
        <T extends Computed> Snapshot<T> newSnapshot(Q<T> of);

        default <T extends Computed> Snapshot<T> newSnapshot() {
            return newSnapshot(Q.newDefault(0));
        }
    }

    void update(Q<T> items) throws NullPointerException;

}
