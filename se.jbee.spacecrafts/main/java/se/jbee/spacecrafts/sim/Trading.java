package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.Governing.Fraction;
import se.jbee.spacecrafts.sim.engine.Any.Creation;
import se.jbee.spacecrafts.sim.engine.Any.IsCreated;
import se.jbee.spacecrafts.sim.engine.Flux;
import se.jbee.spacecrafts.sim.engine.Maybe;
import se.jbee.spacecrafts.sim.engine.Pick;
import se.jbee.spacecrafts.sim.engine.Stasis;

public interface Trading {

    interface Offer extends Creation {
        @Override
        IsOffered header();
    }

    interface IsOffered extends IsCreated {

        Fraction by();
    }

    record Offered(
            int serial,
            Fraction by
    ) implements IsOffered {}

    /**
     * A player makes a trade proposal
     */
    record Trade(
            Offered header,
            Flux<Fraction> recipients,
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
            Pick<Resourcing.Quantity> give
    ) implements Offer {}

    record Deal(
            Offered header,
            Fraction with,
            Pick<Resourcing.Quantity> give,
            Pick<Resourcing.Quantity> take
    ) implements Offer {}

    record Sale(
            Offered header,
            Conquering.Spaceship of,
            int crew,
            Pick<Resourcing.Quantity> price
    ) implements Offer {}

    record Mission(
            Offered header,
            Conquering.SolarSystem in,
            Governing.Asset on,
            Maybe<Crafting.Deck> deck,
            Maybe<Crafting.Unit> unit,
            Pick<Resourcing.Quantity> bounty
    ) implements Offer {}

    record Hire(
            Offered header,
            Conquering.MercenaryUnit of,
            Mission task
    ) implements Offer {}
}
