package se.jbee.spacecrafts.sim.consequence;

import se.jbee.spacecrafts.sim.Balance;
import se.jbee.spacecrafts.sim.Trading;
import se.jbee.spacecrafts.sim.engine.Consequence;
import se.jbee.spacecrafts.sim.engine.Consequence.Binder;
import se.jbee.spacecrafts.sim.engine.Register;

public interface TradingConsequences {

    Binder ObeyingDeals = game -> new ObeyingDeals(game.objects().deals());

    record ObeyingDeals(Register<Deal> deals) implements Trading, Consequence {

        @Override
        public void manifest() {
            deals.forEach(this::obey);
        }

        private void obey(Deal deal) {
            var host = deal.header().by();
            var customer = deal.with();
            Balance.transfer(host, customer, deal.give());
            Balance.transfer(customer, host, deal.take());
        }
    }

    record FailingMissions(Register<Mission> missions) implements Trading, Consequence {

        @Override
        public void manifest() {
            //TODO find missions assigned
            //check turn they time out against current turn
        }
    }
}
