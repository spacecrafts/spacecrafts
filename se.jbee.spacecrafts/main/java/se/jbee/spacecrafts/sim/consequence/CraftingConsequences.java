package se.jbee.spacecrafts.sim.consequence;

import se.jbee.spacecrafts.sim.Crafting;
import se.jbee.spacecrafts.sim.engine.Consequence;

public interface CraftingConsequences {

    record CompletingUnits() implements Crafting, Consequence {

        @Override
        public void manifest() {

        }
    }

    // 0. zero `actuals`
    // 1. apply influences to units `actuals`
    // 2. distribute staff in units following priorities and lock ins
    // 3. process resources in order of range:
    // -energy
    // -rare materials
    // -construction points
    // -food
    // -knowledge
    // -wisdom
    // -research
    // -population (staff)
    // => for each Unit add to their `actuals`
    // 4. aggregate resources amounts from Units to Deck and Decks to Craft
    // 5. use construction points to complete construction queue items
}
