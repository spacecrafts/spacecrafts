package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.engine.Any.Creation;
import se.jbee.spacecrafts.sim.engine.Any.IsCreated;
import se.jbee.spacecrafts.sim.engine.Flux;
import se.jbee.spacecrafts.sim.engine.Pick;
import se.jbee.spacecrafts.sim.engine.Stasis;

public interface Trading {

    interface Offer extends Creation {
        @Override
        IsOffered header();
    }

    interface IsOffered extends IsCreated {

        Governing.Fraction by();
    }

    record Offered(
            int serial,
            Governing.Fraction by
    ) implements IsOffered {}

    /**
     * A player makes a trade proposal
     */
    record Trade(
            Offered header,
            Flux<Governing.Fraction> recipients,
            boolean perTern,
            Stasis<Resourcing.Resource> give,
            Pick<Resourcing.Quantity> take,
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
            Pick<Resourcing.Quantity> amounts
    ) implements Offer {}

    record Deal(
            Offered header,
            Governing.Fraction with,
            boolean perTurn,
            Pick<Resourcing.Quantity> ins,
            Pick<Resourcing.Quantity> outs
    ) implements Offer {}

    record Sale(
            Offered header,
            Conquering.Spaceship of,
            int crew,
            Pick<Resourcing.Quantity> price
    ) implements Offer {}

}
