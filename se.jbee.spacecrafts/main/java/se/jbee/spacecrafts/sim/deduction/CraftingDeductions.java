package se.jbee.spacecrafts.sim.deduction;

import se.jbee.spacecrafts.sim.Crafting;
import se.jbee.spacecrafts.sim.Game;
import se.jbee.turnmaster.Engine.Flow;

public interface CraftingDeductions {

    record CompletingUnits() implements Crafting, Game.Deduction {

        @Override
        public void manifest(Flow<Game> flow) {

        }
    }

    // 0. zero `actuals`
    // 1. apply influences to units `actuals`
    // 2. distribute staff in units following priorities and lock ins
    // 3. process resources in order at range:
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
    // 5. use construction points to complete construction commissions items
}
