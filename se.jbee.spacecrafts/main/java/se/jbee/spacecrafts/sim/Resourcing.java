package se.jbee.spacecrafts.sim;

import se.jbee.turnmaster.data.Marks;
import se.jbee.turnmaster.data.Numbers;
import se.jbee.turnmaster.data.Pick;
import se.jbee.turnmaster.data.Stasis;

import static se.jbee.turnmaster.data.Any.*;

public interface Resourcing {

    /**
     * Elevates a {@link Property} to a physical {@link Resource}.
     * <p>
     * The order is the order in which {@link Resource}s are processed.
     */
    record Resource(
        Defined header,
        Property amount,
        Property boost,
        boolean physical
    ) implements Grade {}

    record Substance(
        Defined header,
        Stasis<Resource> deposits,
        Stasis<Influence> regional,
        Stasis<Influence> widely
    ) implements Definition {}

    record Influence(
        Defined header,
        Pick<Process> progressions,
        Numbers zeros
    ) implements Grade {}

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
        Marks when,
        Pick<Quantity> ins,
        Pick<Quantity> outs,
        Pick<Effect> then
    ) implements Embedded {}

}
