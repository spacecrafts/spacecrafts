package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.Conquering.MercenaryUnit;
import se.jbee.spacecrafts.sim.Governing.Fraction;
import se.jbee.spacecrafts.sim.engine.Any.Composed;
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
     * Other players can make a {@link Bid} target a {@link Trade} proposal. If
     * the player accepts a bid it becomes a {@link Deal} (and the {@link Bid}
     * is removed again).
     */
    record Bid(
            Offered header,
            Trade on,
            Pick<Resourcing.Quantity> give
    ) implements Offer {}

    record Deal(
            Offered header,
            Fraction with,
            Pick<Resourcing.Quantity> given,
            Pick<Resourcing.Quantity> taken
    ) implements Offer {}

    record Sale(
            Offered header,
            Conquering.Spaceship of,
            int crew,
            Pick<Resourcing.Quantity> price
    ) implements Offer {}

    /*
    Mission, Approach and Hire
     */

    record Mission(
            Offered header,
            Exploring.SolarSystem in,
            Governing.Asset target,
            Maybe<Crafting.Deck> deck,
            Maybe<Crafting.Unit> unit,
            Pick<Resourcing.Quantity> salary
    ) implements Offer {}

    record Approach(
            Offered header,
            MercenaryUnit from,
            Mission on,
            int deadline
    ) implements Offer {}

    record Hire(
            Composed header,
            MercenaryUnit from,
            Mission on,
            int deadline
    ) implements Creation {}

}
