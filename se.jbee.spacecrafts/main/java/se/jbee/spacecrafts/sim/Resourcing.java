package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.engine.Collection;
import se.jbee.spacecrafts.sim.engine.Numbers;
import se.jbee.spacecrafts.sim.engine.Q;
import se.jbee.spacecrafts.sim.engine.Stasis;

import static se.jbee.spacecrafts.sim.engine.Any.*;

public interface Resourcing {


    /**
     * Elevates a {@link Property} to a physical {@link Resource}.
     * <p>
     * The order is the order in which {@link Resource}s are processed.
     */
    record Resource(
            Defined header,
            int ordinal,
            Property amount,
            Property boost
    ) implements Quality {}

    record Substance(
            Defined header,
            Stasis<Resource> deposits,
            Stasis<Influence> regional,
            Stasis<Influence> widely
    ) implements Definition {}

    record Influence(
            Defined header,
            int ordinal,
            Process progression,
            Numbers zeros
    ) implements Quality {}

    /**
     * Groups multiple {@link Influence}s.
     */
    record Phenomenon(
            Defined header,
            Stasis<Influence> members
    ) implements Definition {}

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

    record Process(
            Q<Check> preconditions,
            Q<Quantity> ins,
            Q<Quantity> outs,
            Q<Effect> shifts
    ) implements Embedded {}

    record Check(
            Stasis<Property> on,
            Numbers limits,
            Algorithm<Comparison> target
    ) implements Embedded {}

    interface Comparison {

        boolean test(Collection<Property> on, Numbers actuals, Numbers limits);

        //TODO add a way so that non-acceptance is explained?
    }

}
