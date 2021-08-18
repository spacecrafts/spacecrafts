package se.jbee.spacecrafts.sim.consequence;

import se.jbee.spacecrafts.sim.Crafting;
import se.jbee.spacecrafts.sim.engine.Consequence;

public interface CraftingConsequences {

    record CompletingUnits() implements Crafting, Consequence {

        @Override
        public void manifest() {

        }
    }
}
