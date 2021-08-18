package se.jbee.spacecrafts.sim.decision;

import se.jbee.spacecrafts.sim.*;
import se.jbee.spacecrafts.sim.Governing.Fraction;
import se.jbee.spacecrafts.sim.engine.*;

public interface TradingDecisions {

    record ProposeTrade(
            Fraction by,
            Flux<Fraction> to,
            boolean perTern,
            Stasis<Resourcing.Resource> give,
            Pick<Resourcing.Quantity> take
    ) implements Trading, Decision {

        @Override
        public void manifestIn(Game game, Processor processor) {
            var emptyBids = game.runtime().newFlux().newFlux(
                    game.objects().bids());
            game.objects().trades().spawn(
                    serial -> new Trade(new Offered(serial, by), to, perTern,
                            give, take, emptyBids));
        }
    }

    record WithdrawTrade(Trade withdrawn) implements Trading, Decision {

        @Override
        public void manifestIn(Game game, Processor processor) {
            game.objects().bids().perish(bid -> bid.on() == withdrawn);
            game.objects().trades().perish(withdrawn);
        }
    }

    record MakeBid(
            Trade on,
            Fraction by,
            Pick<Resourcing.Quantity> bid
    ) implements Trading, Decision {

        @Override
        public void manifestIn(Game game, Processor processor) {
            on.bids().add(game.objects().bids().spawn(serial -> new Bid( //
                    new Offered(serial, by), on, bid)));
        }
    }

    record WithdrawBid(Bid withdrawn) implements Trading, Decision {

        @Override
        public void manifestIn(Game game, Processor processor) {
            withdrawn.on().bids().remove(withdrawn);
            game.objects().bids().perish(withdrawn);
        }
    }

    record AcceptBid(
            Bid accepted
    ) implements Trading, Decision {

        @Override
        public void manifestIn(Game game, Processor processor) {
            var trade = accepted.on();
            var host = trade.header().by();
            var customer = accepted.header().by();
            trade.bids().remove(accepted);
            trade.recipients().remove(customer);
            if (trade.recipients().isEmpty())
                game.objects().trades().perish(trade);
            game.objects().bids().perish(accepted);
            if (trade.perTern()) {
                game.objects().deals().spawn(serial -> new Deal( //
                        new Offered(serial, host), customer, accepted.give(),
                        trade.take()));
            } else {
                Balance.transfer(host, customer, accepted.give());
                Balance.transfer(customer, host, trade.take());
            }
        }
    }

    record TerminateDeal(
            Deal terminated,
            Fraction by
    ) implements Trading, Decision {

        @Override
        public void manifestIn(Game game, Processor processor) {
            game.objects().deals().perish(terminated);
            //TODO must leave some sort of info to the other party (if human)
        }
    }

    record PutUpForSale(
            Fraction by,
            Conquering.Spaceship item,
            int crew,
            Pick<Resourcing.Quantity> price
    ) implements Trading, Decision {

        @Override
        public void manifestIn(Game game, Processor processor) {
            game.objects().sales().spawn(serial -> new Sale( //
                    new Offered(serial, by), item, crew, price));
        }
    }

    record WithdrawFromSale(Sale withdrawn) implements Trading, Decision {

        @Override
        public void manifestIn(Game game, Processor processor) {
            game.objects().sales().perish(withdrawn);
        }
    }

    record CloseSale(
            Sale closed,
            Fraction buyer
    ) implements Trading, Decision {

        @Override
        public void manifestIn(Game game, Processor processor) {
            var seller = closed.header().by();
            var ship = closed.of();
            seller.governed().spaceships().remove(ship);
            buyer.governed().spaceships().add(ship);
            game.objects().sales().perish(closed);
            Balance.transfer(buyer, seller, closed().price());
        }
    }

    record PutBountyOnMission(
            Fraction by,
            Conquering.SolarSystem in,
            Governing.Asset on,
            Maybe<Crafting.Deck> deck,
            Maybe<Crafting.Unit> unit,
            Pick<Resourcing.Quantity> bounty
    ) implements Trading, Decision {

        @Override
        public void manifestIn(Game game, Processor processor) {
            game.objects().missions().spawn(serial -> new Mission( //
                    new Offered(serial, by), in, on, deck, unit, bounty));
        }
    }

    record HireForMission(Hire hired) implements Trading, Decision {

        @Override
        public void manifestIn(Game game, Processor processor) {
            game.objects().hires().perish(hire -> hire.task() == hired.task());
            hired.of().mission().set(hired.task());
        }
    }

    record CancelMission(Mission cancelled) implements Trading, Decision {

        @Override
        public void manifestIn(Game game, Processor processor) {
            processor.manifest(new PerishMission(cancelled));
        }
    }

    record PerishMission(Mission perished) implements Trading, Decision {

        @Override
        public void manifestIn(Game game, Processor processor) {
            var givenTo = game.objects().mercenaries() //
                    .first(unit -> unit.mission().is(m -> m == perished));
            if (givenTo.isSome()) givenTo.get().mission().clear();
            game.objects().missions().perish(perished);
        }
    }
}
