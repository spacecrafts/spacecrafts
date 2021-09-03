package se.jbee.spacecrafts.sim.deduction;

import se.jbee.spacecrafts.sim.Game;
import se.jbee.spacecrafts.sim.Resourcing;
import se.jbee.turnmaster.Engine;

public interface ResourcingDeductions {

    // there are two types of processes:
    // 1. those generating resources
    // 2. those the only transfer or consume resources, e.g. accumulate totals or complete building items

    // 1. sort is done at the beginning of the turn so that one can see what is gained
    // it is zeroed and recomputed should the player change something
    // => crafts cannot have accumulators (otherwise we would not be able to zero)
    //

    // eval equipment
    // staff equipment

    /**
     * Tax money is a commodity. It turns the taxation percentage of the outputs
     * 1:1 into money (create Taxed Domain).
     * <p>
     * Taxation takes place after all outputs have been computed but before they
     * are aggregated.
     * <p>
     * Not all fractions can raise taxes. They need the <code>statehood</code>
     * indicator.
     */
    record TaxingGains() implements Resourcing, Game.Deduction {

        @Override
        public void manifest(Engine.Flow<Game> flow) {

        }
    }
}
