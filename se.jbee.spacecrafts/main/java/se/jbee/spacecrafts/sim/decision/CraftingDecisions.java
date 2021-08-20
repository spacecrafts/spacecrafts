package se.jbee.spacecrafts.sim.decision;

import se.jbee.spacecrafts.sim.Crafting;
import se.jbee.spacecrafts.sim.Game;
import se.jbee.spacecrafts.sim.engine.Collection;
import se.jbee.spacecrafts.sim.engine.Decision;
import se.jbee.spacecrafts.sim.engine.Maybe;
import se.jbee.spacecrafts.sim.engine.XY;

public interface CraftingDecisions {

    record WrackUnits(
            Deck on,
            Collection<Unit> units
    ) implements Crafting, Decision {

        @Override
        public void manifestIn(Game game, Processor processor) {
            units.forEach(unit -> on.units().clear(unit.at()));
        }
    }

    record BuildUnit(
            Deck on,
            Component of,
            XY.Location at
    ) implements Crafting, Decision {

        @Override
        public void manifestIn(Game game, Processor processor) {
            on.construction().pushBottom(new Unit(of, at, game.newNumbers()));
        }
    }

    record BuildDeck(
            Craft in,
            Material structure,
            Maybe<Material> plating
    ) implements Crafting, Decision {

        @Override
        public void manifestIn(Game game, Processor processor) {

        }
    }

    record CloneDeck(
            Craft in,
            Deck cloned
    ) implements Crafting, Decision {

        @Override
        public void manifestIn(Game game, Processor processor) {

        }
    }

    record CloneCraft(
            Craft cloned,
            Craft in
    ) implements Crafting, Decision {

        @Override
        public void manifestIn(Game game, Processor processor) {

        }
    }

    record TransferDeck(
            Deck transferred,
            Craft from,
            Craft to
    ) implements Crafting, Decision {

        @Override
        public void manifestIn(Game game, Processor processor) {

        }
    }

    record PerishCraft(Craft perished) implements Crafting, Decision {

        @Override
        public void manifestIn(Game game, Processor processor) {
            if (game.objects().crafts().first(
                    craft -> craft.cloneOf().is(c -> c == perished)).isSome()) {
                // this is used as a design => keep it as such
                //TODO mark as design
                return;
            }
            game.objects().fractions().forEach(
                    fraction -> fraction.awareOf().crafts().remove(perished));
            perished.decks().forEach(
                    deck -> processor.manifest(new PerishDeck(deck)));
            game.objects().crafts().perish(perished);
        }
    }

    record PerishDeck(Deck perished) implements Crafting, Decision {

        @Override
        public void manifestIn(Game game, Processor processor) {
            game.objects().decks().perish(perished);
        }
    }
}
