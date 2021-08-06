package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.Any.CreatedHeader;
import se.jbee.spacecrafts.sim.Any.Creation;
import se.jbee.spacecrafts.sim.collection.Q;

public interface Trading {

    interface Offer extends Creation {
        @Override
        OfferedHeader header();
    }

    interface OfferedHeader extends CreatedHeader {

        Governing.Fraction by();
    }

    record Offered(
            int serial,
            Governing.Fraction by
    ) implements OfferedHeader {}

    record Trade(
            Offered header,
            Governing.Fraction with,
            Q<Resourcing.Quantity> wants,
            Q<Resourcing.Resource> payments
    ) implements Offer {}

    record Deal(
            Offered header,
            Conquering.Spaceship of,
            int crew,
            Q<Resourcing.Quantity> price,
            Resourcing.Numbers details
    ) implements Offer {}

}
