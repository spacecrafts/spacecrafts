package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.engine.Any.CreatedHeader;
import se.jbee.spacecrafts.sim.engine.Any.Creation;
import se.jbee.spacecrafts.sim.engine.Flux;
import se.jbee.spacecrafts.sim.engine.Numbers;
import se.jbee.spacecrafts.sim.engine.Q;
import se.jbee.spacecrafts.sim.engine.Stasis;

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

    /**
     * A player makes a trade proposal
     */
    record Trade(
            Offered header,
            Q<Governing.Fraction> recipients,
            boolean perTern,
            Q<Resourcing.Quantity> wants,
            Stasis<Resourcing.Resource> payments,
            Flux<Bid> bids
    ) implements Offer {}

    /**
     * Other players can make a {@link Bid} on a {@link Trade} proposal. If the
     * player accepts a bid it becomes a {@link Deal} (and the {@link Bid} is
     * removed again).
     */
    record Bid(
            Offered header,
            Trade on,
            Q<Resourcing.Quantity> amounts
    ) implements Offer {}

    record Deal(
            Offered header,
            Governing.Fraction with,
            boolean perTurn,
            Q<Resourcing.Quantity> ins,
            Q<Resourcing.Quantity> outs
    ) implements Offer {}

    record Sale(
            Offered header,
            Conquering.Spaceship of,
            int crew,
            Q<Resourcing.Quantity> price,
            Numbers details
    ) implements Offer {}

}
