package se.jbee.spacecrafts.sim.decision;

import se.jbee.spacecrafts.sim.Crafting;
import se.jbee.spacecrafts.sim.Crafting.Craft;
import se.jbee.spacecrafts.sim.Game;
import se.jbee.spacecrafts.sim.Game.Byproduct;
import se.jbee.spacecrafts.sim.Game.Decision;
import se.jbee.spacecrafts.sim.Resourcing;
import se.jbee.turnmaster.Any.Text;
import se.jbee.turnmaster.Engine.Flow;
import se.jbee.turnmaster.data.Maybe;
import se.jbee.turnmaster.data.Q;
import se.jbee.turnmaster.data.XY;

public interface CraftingDecisions {

    record DismantleUnit(
        Section in,
        Deck on,
        XY.Location at
    ) implements Crafting, Decision {

        @Override
        public void manifestIn(Game game, Flow<Game> flow) {
            in.commissions()
              .pushBottom(new Commission(on, on.units().at(at).get(), true));
        }
    }

    record CommissionUnit(
        Component of,
        Section in,
        Deck on,
        XY.Location at
    ) implements Crafting, Decision {

        @Override
        public void manifestIn(Game game, Flow<Game> flow) {
            var unit = new Unit(of, at, game.newNumbers());
            on.units().put(at, unit);
            in.commissions().pushBottom(new Commission(on, unit, false));
        }
    }

    record ConstructSection(
        Craft in,
        Material structure,
        Maybe<Material> plating,
        int decks,
        XY.Location capacity
    ) implements Crafting, Decision {

        @Override
        public void manifestIn(Game game, Flow<Game> flow) {
            Q<Deck> decksQ = game.newQ(decks);
            for (int i = 0; i < decks; i++)
                decksQ.append(new Deck(new Text("Deck " + i), false,
                    game.runtime().newXY().newXY(capacity), game.newTop(8)));

            var name = "" + ('A' + in.sections().size());
            game.objects().sections().spawn(
                serial -> new Section(game.newCreated(serial, new Text(name)),
                    structure, plating, game.newNumbers(), game.newMarks(),
                    decksQ.seal(), game.newTop(5)));
        }
    }

    record CloneSection(
        Craft in,
        Section cloned,
        XY.Location offset
    ) implements Crafting, Decision {

        @Override
        public void manifestIn(Game game, Flow<Game> flow) {

        }
    }

    record CloneCraft(
        Craft cloned,
        Craft in
    ) implements Crafting, Decision {

        @Override
        public void manifestIn(Game game, Flow<Game> flow) {
            // TODO would be to build it section by section, launch and merge it again
        }
    }

    /*
    Byproducts
     */

    record SpawnCraft(
        Text name,
        Section with,
        Maybe<Craft> cloneOf
    ) implements Crafting, Byproduct<Craft> {

        SpawnCraft(Text name, Section with) {
            this(name, with, Maybe.nothing());
        }

        @Override
        public Craft andManifestIn(Game game, Flow<Game> flow) {
            var craft = game.objects().crafts().spawn(
                serial -> new Craft(game.newCreated(serial, name),
                    game.newNumbers(), cloneOf,
                    game.newFlux(Resourcing.Influence.class),
                    game.newTop(Section.class),
                    game.newTop(Resourcing.Resource.class)));
            craft.sections().pushBottom(with);
            return craft;
        }
    }

    record PerishCraft(Craft perished) implements Crafting, Byproduct<Void> {

        @Override
        public Void andManifestIn(Game game, Flow<Game> flow) {
            if (game.objects().crafts()
                    .first(craft -> craft.cloneOf().is(c -> c == perished))
                    .isSome()) {
                // this is used as a design => keep it as such
                //TODO mark as design?
                return null;
            }
            game.objects().fractions().forEach(
                fraction -> fraction.awareOf().crafts().remove(perished));
            perished.sections().forEach(
                section -> flow.manifest(new PerishSection(section)));
            game.objects().crafts().perish(perished);
            return null;
        }
    }

    record PerishSection(Section perished) implements Crafting, Byproduct<Void> {

        @Override
        public Void andManifestIn(Game game, Flow<Game> flow) {
            game.objects().sections().perish(perished);
            return null;
        }
    }
}
