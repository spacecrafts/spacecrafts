package se.jbee.spacecrafts.sim.decision;

import se.jbee.spacecrafts.sim.Crafting;
import se.jbee.spacecrafts.sim.Crafting.Craft;
import se.jbee.spacecrafts.sim.Domains;
import se.jbee.spacecrafts.sim.Game;
import se.jbee.spacecrafts.sim.Game.Decision;
import se.jbee.spacecrafts.sim.Game.Implication;
import se.jbee.spacecrafts.sim.Resourcing;
import se.jbee.turnmaster.Engine.Flow;
import se.jbee.turnmaster.data.Any.Text;
import se.jbee.turnmaster.data.Maybe;
import se.jbee.turnmaster.data.Numbers;
import se.jbee.turnmaster.data.Q;
import se.jbee.turnmaster.data.XY;

public interface CraftingDecisions {

    record DemolishUnit(
        Section in,
        Deck on,
        XY.Location at
    ) implements Crafting, Decision {

        @Override
        public void manifestIn(Game game, Flow<Game> flow) {
            // unit structure needs to become zero using production * trait dependent factor
            in.commissions().pushBottom(
                new Commission(on, on.units().at(at).get(),
                    Commission.Type.DEMOLITION));
        }
    }

    record RepairUnit(
        Section in,
        Deck on,
        XY.Location at
    ) implements Crafting, Decision {

        @Override
        public void manifestIn(Game game, Flow<Game> flow) {
            // unit structure needs to become same as component structure again using production
            in.commissions().pushBottom(
                new Commission(on, on.units().at(at).get(),
                    Commission.Type.REPAIR));
        }
    }

    record ConstructUnit(
        Component of,
        Section in,
        Deck on,
        XY.Location at
    ) implements Crafting, Decision {

        @Override
        public void manifestIn(Game game, Flow<Game> flow) {
            // init unit to costs of the component
            Numbers actuals = game.newNumbers().clear();
            actuals.zero(of.profile(),
                game.objects().domains().get(Domains.construction).members());
            in.commissions().pushBottom(
                new Commission(on, new Unit(of, at, actuals),
                    Commission.Type.CONSTRUCTION));
        }
    }

    record ConstructSection(
        Craft in,
        Material structure,
        Maybe<Material> plating,
        int decks,
        XY.Location offset,
        XY.Dimension capacity
    ) implements Crafting, Decision {

        @Override
        public void manifestIn(Game game, Flow<Game> flow) {
            Q<Deck> decksQ = game.newQ(decks);
            for (int i = 0; i < decks; i++)
                decksQ.append(new Deck(new Text("Deck " + i), false,
                    game.newXY(offset, capacity)));

            var name = "" + ('A' + in.sections().size());
            game.objects().sections().spawn(
                serial -> new Section(game.newCreated(serial, name), structure,
                    plating, game.newMarks(), game.newNumbers(),
                    game.newNumbers(), decksQ.seal(), game.newTop(5),
                    game.newTop(8), game.newTop(8)));
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
    ) implements Crafting, Implication<Craft> {

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

    record PerishCraft(Craft perished) implements Crafting, Implication<Void> {

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
            perished.sections()
                .forEach(section -> flow.manifest(new PerishSection(section)));
            game.objects().crafts().perish(perished);
            return null;
        }
    }

    record PerishSection(Section perished) implements Crafting, Implication<Void> {

        @Override
        public Void andManifestIn(Game game, Flow<Game> flow) {
            game.objects().sections().perish(perished);
            return null;
        }
    }
}
