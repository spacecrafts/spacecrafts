package se.jbee.spacecrafts.sim.event;

import se.jbee.spacecrafts.sim.Game;
import se.jbee.spacecrafts.sim.Governing;
import se.jbee.spacecrafts.sim.Resourcing;
import se.jbee.spacecrafts.sim.Trading;
import se.jbee.spacecrafts.sim.engine.Event;
import se.jbee.spacecrafts.sim.engine.Flux;
import se.jbee.spacecrafts.sim.engine.Pick;
import se.jbee.spacecrafts.sim.engine.Stasis;

public interface WhenTrading {

    record ProposeTrade(
            Governing.Fraction by,
            Flux<Governing.Fraction> to,
            boolean perTern,
            Stasis<Resourcing.Resource> give,
            Pick<Resourcing.Quantity> take
    ) implements Trading, Event {

        @Override
        public void applyTo(Game game, Dispatcher dispatcher) {
            var emptyBids = game.runtime().newFlux().newFlux(game.objects().bids());
            game.objects().trades().spawn(serial -> new Trade(//
                    new Offered(serial, by), //
                    to, perTern, give, take, emptyBids));
        }
    }

    record WithdrawTrade(Trade withdrawn) implements Trading, Event {

        @Override
        public void applyTo(Game game, Dispatcher dispatcher) {
            game.objects().bids().perish(bid -> bid.on() == withdrawn);
            game.objects().trades().perish(withdrawn);
        }
    }

    record MakeBid(
            Trade on,
            Governing.Fraction by,
            Pick<Resourcing.Quantity> amounts
    ) implements Trading, Event {

        @Override
        public void applyTo(Game game, Dispatcher dispatcher) {
            on.bids().add(game.objects().bids().spawn(serial -> new Bid( //
                    new Offered(serial, by), on, amounts)));
        }
    }

    record WithdrawBid(Bid withdrawn) implements Trading, Event {

        @Override
        public void applyTo(Game game, Dispatcher dispatcher) {
            withdrawn.on().bids().remove(withdrawn);
            game.objects().bids().perish(withdrawn);
        }
    }

    record AcceptBid(
            Bid accepted
    ) implements Trading, Event {

        @Override
        public void applyTo(Game game, Dispatcher dispatcher) {
            var trade = accepted.on();
            var partnerA = trade.header().by();
            var partnerB = accepted.header().by();
            trade.bids().remove(accepted);
            trade.recipients().remove(partnerB);
            if (trade.recipients().isEmpty())
                game.objects().trades().perish(trade);
            game.objects().bids().perish(accepted);
            game.objects().deals().spawn(serial -> new Deal( //
                    new Offered(serial, partnerA), //
                    partnerB, trade.perTern(), trade.take(), //
                    accepted.amounts()));
        }
    }

    record TerminateDeal(
            Deal terminated,
            Governing.Fraction by
    ) implements Trading, Event {

        @Override
        public void applyTo(Game game, Dispatcher dispatcher) {
            game.objects().deals().perish(terminated);
            //TODO must leave some sort of info to the other party (if human)
        }
    }

}
