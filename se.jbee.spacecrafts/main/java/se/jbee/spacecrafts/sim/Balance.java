package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.engine.Collection;
import se.jbee.spacecrafts.sim.engine.Numbers;

@FunctionalInterface
public interface Balance {

    Numbers score();

    default void credit(Collection<Resourcing.Quantity> amounts) {
        amounts.forEach(a -> score().add(a.of().amount(), a.n()));
    }

    default void debit(Collection<Resourcing.Quantity> amounts) {
        amounts.forEach(a -> score().add(a.of().amount(), -a.n()));
    }

    static void transfer(Balance from, Balance to, Collection<Resourcing.Quantity> amounts) {
        from.debit(amounts);
        to.credit(amounts);
    }
}
