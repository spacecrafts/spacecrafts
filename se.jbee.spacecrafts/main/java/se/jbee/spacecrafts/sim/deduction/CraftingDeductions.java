package se.jbee.spacecrafts.sim.deduction;

import se.jbee.spacecrafts.sim.Crafting;
import se.jbee.spacecrafts.sim.Domains;
import se.jbee.spacecrafts.sim.Game;
import se.jbee.spacecrafts.sim.Game.Deducting;
import se.jbee.spacecrafts.sim.Game.Deduction;
import se.jbee.spacecrafts.sim.Properties;
import se.jbee.turnmaster.Engine.Flow;
import se.jbee.turnmaster.data.Any;
import se.jbee.turnmaster.data.Any.Property;
import se.jbee.turnmaster.data.Numbers;
import se.jbee.turnmaster.data.Register;

public interface CraftingDeductions {

    Deducting CompletingUnits = game -> new CompletingUnits(
        game.objects().sections(),
        game.objects().domains().get(Domains.operation),
        game.property(Properties.production),
        game.property(Properties.structure));

    record CompletingUnits(
        Register<Section> sections,
        Any.Domain operation,
        Property production,
        Property structure
    ) implements Crafting, Deduction {

        @Override
        public void manifest(Flow<Game> flow) {
            sections.forEach(this::completeCommissions);
        }

        private void completeCommissions(Section section) {
            Numbers inventory = section.totals();
            section.commissions().forEach(commission -> {
                if (inventory.get(production) <= 0) return;
                if (switch (commission.type()) {
                    case REPAIR -> completeRepair(commission, inventory);
                    case DEMOLITION -> completeDemolition(commission,
                        inventory);
                    case CONSTRUCTION -> completeConstruction(commission,
                        inventory);
                }) section.commissions().remove(commission);
            });
        }

        private boolean completeConstruction(Commission construction, Numbers inventory) {
            Numbers actuals = construction.item().actuals();
            inventory.sub(production,
                actuals.subZero(structure, inventory.get(production)));
            actuals.forEach((property, val) -> {
                if (val > 0) inventory.sub(property,
                    actuals.subZero(property, inventory.get(property)));
            });
            boolean done = actuals.isZero();
            if (done) actuals.clear()
                .zero(construction.item().of().profile(), operation.members());
            return done;
        }

        private boolean completeDemolition(Commission demolition, Numbers inventory) {
            Numbers target = demolition.item().actuals();
            inventory.sub(production,
                target.subZero(structure, inventory.get(production)));
            return target.get(structure) <= 0;
        }

        private boolean completeRepair(Commission repair, Numbers inventory) {
            Numbers target = repair.item().actuals();
            int full = repair.item().of().profile().get(structure);
            inventory.sub(production,
                target.addUpTo(structure, inventory.get(production), full));
            return target.get(structure) >= full;
        }
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
