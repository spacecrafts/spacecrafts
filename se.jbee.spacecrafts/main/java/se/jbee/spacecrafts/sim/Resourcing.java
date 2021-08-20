package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.engine.Marks;
import se.jbee.spacecrafts.sim.engine.Numbers;
import se.jbee.spacecrafts.sim.engine.Pick;
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
            //TODO likely needs info target zeroing and if it is a physical or virtual resource (which level it aggregates) or local/global or transferable
            // maybe this should be part of the Property
    ) implements Grade {}

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
            Precondition precondition,
            Pick<Quantity> ins,
            Pick<Quantity> outs,
            Pick<Effect> effects
    ) implements Embedded {}

    interface Precondition {

        boolean meatBy(Marks properties, Numbers actuals);
    }

}
