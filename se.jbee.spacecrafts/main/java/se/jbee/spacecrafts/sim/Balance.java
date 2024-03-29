package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.Resourcing.Quantity;
import se.jbee.turnmaster.data.Collection;
import se.jbee.turnmaster.data.Numbers;

@FunctionalInterface
public interface Balance {

    static void transfer(Balance from, Balance to, Collection<Quantity> amounts) {
        from.debit(amounts);
        to.credit(amounts);
    }

    Numbers totals();

    default void credit(Collection<Quantity> amounts) {
        amounts.forEach(a -> totals().add(a.of().amount(), a.n()));
    }

    default void debit(Collection<Quantity> amounts) {
        amounts.forEach(a -> totals().sub(a.of().amount(), a.n()));
    }

    default void debitUnconditionals(Collection<Quantity> amounts) {
        amounts.forEach(a -> totals().sub(a.of().amount(), a.n()),
            Quantity::unconditional);
    }
}
