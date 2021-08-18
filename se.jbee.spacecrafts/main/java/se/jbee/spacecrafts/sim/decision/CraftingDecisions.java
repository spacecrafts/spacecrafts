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

        }
    }

    record BuildUnit(
            Deck on,
            Component of,
            XY.Location at
    ) implements Crafting, Decision {

        @Override
        public void manifestIn(Game game, Processor processor) {

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
        }
    }
}
