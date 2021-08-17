package se.jbee.spacecrafts.sim.consequence;

import se.jbee.spacecrafts.sim.Balance;
import se.jbee.spacecrafts.sim.Trading;
import se.jbee.spacecrafts.sim.engine.Consequence;
import se.jbee.spacecrafts.sim.engine.Consequence.Supplier;
import se.jbee.spacecrafts.sim.engine.Register;

public interface TradingConsequences {

    Supplier ObeyDeals = game -> new ObeyDeals(game.objects().deals());

    record ObeyDeals(Register<Deal> deals) implements Trading, Consequence {

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
}
