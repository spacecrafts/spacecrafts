package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.Any.Algorithm;
import se.jbee.spacecrafts.sim.Any.Defined;
import se.jbee.spacecrafts.sim.Any.Quality;

import static se.jbee.spacecrafts.sim.Any.Embedded;

public interface Resourcing {

    record Property(
            Defined header,
            int ordinal,
            Integer min,
            Integer max
    ) implements Quality {}

    /**
     * Elevates a {@link Property} to a physical {@link Resource}.
     * <p>
     * The order is the order in which {@link Resource}s are processed.
     */
    record Resource(
            Defined header,
            int ordinal,
            Property of
    ) implements Quality {}

    record Quantity(
            int n,
            Resource of,
            boolean unconditional
    ) implements Embedded {}

    record Effect(
            int n,
            Property of,
            boolean unconditional
    ) implements Embedded {}

    interface BiPredicate {

        boolean test(Numbers actuals, Numbers limits);

        //TODO add a way so that non-acceptance is explained?
    }

    record Check(
            Property on,
            Numbers limits,
            Algorithm<BiPredicate> target
    ) implements Embedded {}

    record Process(
            Q<Check> needs,
            Q<Quantity> ins,
            Q<Quantity> outs,
            Q<Effect> shifts
    ) implements Embedded {}

    record Manifestation(
            Defined header,
            int ordinal,
            Process progression,
            Numbers zeros
    ) implements Quality {}

    interface Numbers {
        int get(Property key);

        void set(Property key, int value);

        void inc(Property key, int delta);

        void zero(Numbers zeros);
    }

    // Ideas:
    // Tags as a way to tag a slot
    // Preconditions
}
