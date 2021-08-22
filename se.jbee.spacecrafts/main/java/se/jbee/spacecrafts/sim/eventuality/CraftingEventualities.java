package se.jbee.spacecrafts.sim.eventuality;

import se.jbee.spacecrafts.sim.Crafting;
import se.jbee.turnmaster.Eventuality;

public interface CraftingEventualities {

    record CompletingUnits() implements Crafting, Eventuality {

        @Override
        public void manifest() {

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
