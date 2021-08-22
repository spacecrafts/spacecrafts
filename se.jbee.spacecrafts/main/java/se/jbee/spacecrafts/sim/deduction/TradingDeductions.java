package se.jbee.spacecrafts.sim.deduction;

import se.jbee.spacecrafts.sim.Balance;
import se.jbee.spacecrafts.sim.Game;
import se.jbee.spacecrafts.sim.Game.Deducting;
import se.jbee.spacecrafts.sim.Game.Deduction;
import se.jbee.spacecrafts.sim.Trading;
import se.jbee.spacecrafts.sim.decision.TradingDecisions.CancelHire;
import se.jbee.turnmaster.Engine.Flow;
import se.jbee.turnmaster.Turn;
import se.jbee.turnmaster.data.Register;

public interface TradingDeductions {

    Deducting ObeyingDeals = game -> new ObeyingDeals(game.objects().deals());

    Deducting CancelingOverdueHires = game -> new CancelingOverdueHires(
        game.turn(), game.objects().hires());

    record ObeyingDeals(Register<Deal> deals) implements Trading, Deduction {

        @Override
        public void manifest(Flow<Game> flow) {
            deals.forEach(this::obey);
        }

        private void obey(Deal deal) {
            var host = deal.header().by();
            var customer = deal.with();
            Balance.transfer(host, customer, deal.given());
            Balance.transfer(customer, host, deal.taken());
        }
    }

    record CancelingOverdueHires(
        Turn turn,
        Register<Hire> hires
    ) implements Trading, Deduction {

        @Override
        public void manifest(Flow<Game> flow) {
            hires.forEach(hire -> flow.manifest(hire, CancelHire::new,
                turn.after(hire.deadline())));
        }
    }
}
