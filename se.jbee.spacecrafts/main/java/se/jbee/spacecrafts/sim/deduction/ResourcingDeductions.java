package se.jbee.spacecrafts.sim.deduction;

public interface ResourcingDeductions {

    // there are two types of processes:
    // 1. those generating resources
    // 2. those the only transfer or consume resources, e.g. accumulate totals or complete building items

    // 1. sort is done at the beginning of the turn so that one can see what is gained
    // it is zeroed and recomputed should the player change something
    // => crafts cannot have accumulators (otherwise we would not be able to zero)
    //

}
