package se.jbee.spacecrafts.sim.deduction;

import se.jbee.spacecrafts.sim.Balance;
import se.jbee.spacecrafts.sim.Game.Deducting;
import se.jbee.spacecrafts.sim.Trading;
import se.jbee.turnmaster.data.Register;
import se.jbee.turnmaster.eval.Deduction;

public interface TradingDeductions {

    Deducting ObeyingDeals = game -> new ObeyingDeals(game.objects().deals());

    record ObeyingDeals(Register<Deal> deals) implements Trading, Deduction {

        @Override
        public void manifest() {
            deals.forEach(this::obey);
        }

        private void obey(Deal deal) {
            var host = deal.header().by();
            var customer = deal.with();
            Balance.transfer(host, customer, deal.given());
            Balance.transfer(customer, host, deal.taken());
        }
    }

    record FailingMissions(Register<Mission> missions) implements Trading, Deduction {

        @Override
        public void manifest() {
            //TODO find missions assigned
            //check turn they time out against current turn
        }
    }
}
