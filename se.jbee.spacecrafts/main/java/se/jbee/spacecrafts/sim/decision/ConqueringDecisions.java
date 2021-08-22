package se.jbee.spacecrafts.sim.decision;

import se.jbee.spacecrafts.sim.Conquering;
import se.jbee.spacecrafts.sim.Crafting.Craft;
import se.jbee.spacecrafts.sim.Crafting.Deck;
import se.jbee.spacecrafts.sim.Crafting.Section;
import se.jbee.spacecrafts.sim.Exploring.Moon;
import se.jbee.spacecrafts.sim.Exploring.Planet;
import se.jbee.spacecrafts.sim.Exploring.SolarSystem;
import se.jbee.spacecrafts.sim.Game;
import se.jbee.spacecrafts.sim.Game.Byproduct;
import se.jbee.spacecrafts.sim.Game.Decision;
import se.jbee.spacecrafts.sim.Governing.Fraction;
import se.jbee.spacecrafts.sim.decision.CraftingDecisions.PerishCraft;
import se.jbee.spacecrafts.sim.decision.CraftingDecisions.SpawnCraft;
import se.jbee.spacecrafts.sim.decision.GoverningDecisions.DischargeExistingLeader;
import se.jbee.spacecrafts.sim.decision.TradingDecisions.CancelHire;
import se.jbee.spacecrafts.sim.decision.TradingDecisions.FailExistingMissions;
import se.jbee.turnmaster.Any.Text;
import se.jbee.turnmaster.Engine.Flow;
import se.jbee.turnmaster.data.Maybe;
import se.jbee.turnmaster.data.Q;
import se.jbee.turnmaster.data.Stasis;
import se.jbee.turnmaster.data.Vary;

public interface ConqueringDecisions {

    record FoundSpaceStation(
        SolarSystem in,
        Fraction by,
        Section from,
        Spaceship origin
    ) implements Conquering, Decision {

        @Override
        public void manifestIn(Game game, Flow<Game> flow) {
            SpaceStation station;
            var name = in.header().name();
            if (origin.structure().sections().size() == 1) {
                station = game.objects().stations().spawn(
                    serial -> new SpaceStation(
                        game.newGoverned(serial, name, by), origin.structure(),
                        in));
                flow.manifest(new PerishSpaceship(origin, true));
            } else {
                var craft = flow.andManifest(
                    new SpawnCraft(origin.structure().header().name(), from));
                station = game.objects().stations().spawn(
                    serial -> new SpaceStation(
                        game.newGoverned(serial, name, by), craft, in));
                origin.structure().sections().remove(from);
            }
            by.governed().stations().add(station);
            by.awareOf().stations().add(station);
        }
    }

    record DockSpaceStation(
        Section from,
        Spaceship origin,
        SpaceStation to
    ) implements Conquering, Decision {

        @Override
        public void manifestIn(Game game, Flow<Game> flow) {
            to.structure().sections().pushBottom(from);
            // prevent perish from docked sections
            origin.structure().sections().remove(from);
            if (origin.structure().sections().isEmpty())
                flow.manifest(new PerishSpaceship(origin, false));
        }
    }

    record FoundColony(
        Planet on,
        Fraction by,
        Section from,
        Spaceship origin
    ) implements Conquering, Decision {

        @Override
        public void manifestIn(Game game, Flow<Game> flow) {
            Colony colony;
            var name = on.header().name();
            Q<Deck> decks = game.newQ(3);
            var surface = from.decks().first().get();
            surface.name().set("surface");
            var capacity = on.surface().capacity();
            surface.units().trimTo(capacity);
            decks.append(surface);
            decks.append(
                new Deck(new Text("underground"), true, game.newXY(capacity),
                    game.newTop(5)));
            decks.append(
                new Deck(new Text("subterranean"), false, game.newXY(capacity),
                    game.newTop(5)));
            var section = game.objects().sections().spawn(serial -> new Section(
                game.newCreated(serial, from.header().name()), from.structure(),
                from.plating(), from.totals(), from.properties(), decks.seal(),
                from.commissions()));
            var craft = flow.andManifest(
                new SpawnCraft(origin.structure().header().name(), section));
            colony = game.objects().colonies().spawn(
                serial -> new Colony(game.newGoverned(serial, name, by), craft,
                    on));
            origin.structure().sections().remove(from);
            if (origin.structure().sections().isEmpty())
                flow.manifest(new PerishSpaceship(origin, true));
            by.governed().colonies().add(colony);
            by.awareOf().colonies().add(colony);
        }
    }

    record FoundOutpost(
        Moon on,
        Fraction by,
        Section from,
        Spaceship origin
    ) implements Conquering, Decision {

        @Override
        public void manifestIn(Game game, Flow<Game> flow) {
            LunarOutpost outpost;
            var name = on.header().name();
            if (origin.structure().sections().size() == 1) {
                outpost = game.objects().outposts().spawn(
                    serial -> new LunarOutpost(
                        game.newGoverned(serial, name, by), origin.structure(),
                        on));
                flow.manifest(new PerishSpaceship(origin, true));
            } else {
                var craft = flow.andManifest(
                    new SpawnCraft(origin.structure().header().name(), from));
                outpost = game.objects().outposts().spawn(
                    serial -> new LunarOutpost(
                        game.newGoverned(serial, name, by), craft, on));
                origin.structure().sections().remove(from);
            }
            from.decks().first().get().name().set("surface");
            by.governed().outposts().add(outpost);
            by.awareOf().outposts().add(outpost);
        }
    }

    record LaunchOrbitalStation(
        Fraction by,
        Section from,
        Colony origin
    ) implements Conquering, Decision {

        @Override
        public void manifestIn(Game game, Flow<Game> flow) {
            var craft = flow.andManifest(
                new SpawnCraft(from.header().name(), from));
            game.objects().orbitals().spawn(serial -> new OrbitalStation(
                game.newGoverned(serial, origin.header().name(), by), craft,
                origin.on()));
        }
    }

    record LaunchSpaceship(
        Fraction by,
        Section from,
        Colony origin,
        Maybe<Craft> cloneOf
    ) implements Conquering, Decision {

        @Override
        public void manifestIn(Game game, Flow<Game> flow) {
            var name = Text.EMPTY;
            var craft = flow.andManifest(new SpawnCraft(name, from, cloneOf));
            Spaceship ship = game.objects().spaceships().spawn(
                serial -> new Spaceship(game.newGoverned(serial, name, by),
                    craft));
            by.awareOf().spaceships().add(ship);
            by.governed().spaceships().add(ship);
            flow.manifest(new JoinOrFoundFleet(ship, origin.on().in(), by));
        }
    }

    record FoundFleet(
        Stasis<Spaceship> members,
        Fraction by,
        SolarSystem in
    ) implements Conquering, Decision {

        @Override
        public void manifestIn(Game game, Flow<Game> flow) {
            if (members.isEmpty()) return;
            Spaceship ship1 = members.first().get();
            var fleet = game.objects().fleets().spawn(serial -> new Fleet(
                game.newGoverned(serial, ship1.header().name(), by),
                members.inFlux(), game.newNumbers(), Vary.some(in.location()),
                Vary.nothing()));
            by.awareOf().fleets().add(fleet);
            by.governed().fleets().add(fleet);
            in.fleets().add(fleet);
        }
    }

    record ReassignFleet(
        Spaceship reassigned,
        Fleet from,
        Fleet to
    ) implements Conquering, Decision {

        @Override
        public void manifestIn(Game game, Flow<Game> flow) {
            from.members().remove(reassigned);
            if (from.members().isEmpty()) flow.manifest(new PerishFleet(from));
            to.members().add(reassigned);
        }
    }

    record BandMercenaryUnit(Fleet banded) implements Conquering, Decision {

        @Override
        public void manifestIn(Game game, Flow<Game> flow) {
            var fraction = banded.header().origin();
            var unit = game.objects().mercenaries().spawn(
                serial -> new MercenaryUnit(game.newGoverned(serial,
                    new Text(banded.header().name().get()), fraction), banded));
            fraction.awareOf().mercenaries().add(unit);
            fraction.governed().fleets().remove(banded);
            fraction.governed().mercenaries().add(unit);
            // MCs are not tracked as armies
            game.objects().systems()
                .forEach(solarSystem -> solarSystem.fleets().remove(banded));
        }
    }

    record DisbandMercenaryUnit(MercenaryUnit disbanded) implements Conquering, Decision {

        @Override
        public void manifestIn(Game game, Flow<Game> flow) {
            flow.manifest(CancelHire::new,
                game.objects().hires().first(hire -> hire.from() == disbanded));
            var faction = disbanded.header().origin();
            faction.governed().fleets().add(disbanded.unit());
            game.objects().fractions().forEach(fraction -> {
                fraction.awareOf().mercenaries().remove(disbanded);
                fraction.governed().mercenaries().remove(disbanded);
            });
            game.objects().mercenaries().perish(disbanded);
        }
    }

    /*
    Byproducts
     */

    record JoinOrFoundFleet(
        Spaceship joining,
        SolarSystem in,
        Fraction by
    ) implements Conquering, Decision {

        @Override
        public void manifestIn(Game game, Flow<Game> flow) {
            var fleet = in.fleet(by);
            if (!fleet.isSome()) {
                flow.manifest(new FoundFleet(
                    game.newFlux(Spaceship.class).addAnd(joining).inStasis(),
                    by, in));
                fleet = in.fleet(by);
            }
            fleet.get().members().add(joining);
        }
    }

    record PerishFleet(Fleet perished) implements Conquering, Byproduct<Void> {

        @Override
        public Void andManifestIn(Game game, Flow<Game> flow) {
            flow.manifest(DisbandMercenaryUnit::new,
                game.objects().mercenaries() //
                    .first(m -> m.unit() == perished));

            game.objects().systems()
                .forEach(system -> system.fleets().remove(perished));

            game.objects().fractions().forEach(fraction -> {
                fraction.awareOf().fleets().remove(perished);
                fraction.governed().fleets().remove(perished);
            });
            game.objects().fleets().perish(perished);
            return null;
        }
    }

    record PerishSpaceship(
        Spaceship perished,
        boolean craftTransformation
    ) implements Conquering, Byproduct<Void> {

        @Override
        public Void andManifestIn(Game game, Flow<Game> flow) {
            game.objects().fleets()
                .forEach(fleet -> fleet.members().remove(perished));

            flow.manifest(new DischargeExistingLeader(perished));
            flow.manifest(new FailExistingMissions(perished));

            game.objects().fractions().forEach(fraction -> {
                fraction.awareOf().spaceships().remove(perished);
                fraction.governed().spaceships().remove(perished);
            });

            if (!craftTransformation)
                flow.manifest(new PerishCraft(perished.structure()));
            game.objects().spaceships().perish(perished);
            return null;
        }
    }

    record PerishSpaceStation(SpaceStation perished) implements Conquering, Byproduct<Void> {

        @Override
        public Void andManifestIn(Game game, Flow<Game> flow) {
            flow.manifest(new DischargeExistingLeader(perished));
            flow.manifest(new FailExistingMissions(perished));

            game.objects().fractions().forEach(fraction -> {
                fraction.awareOf().stations().remove(perished);
                fraction.governed().stations().remove(perished);
            });

            flow.manifest(new PerishCraft(perished.structure()));
            game.objects().stations().perish(perished);
            return null;
        }
    }

    record PerishColony(Colony perished) implements Conquering, Byproduct<Void> {

        @Override
        public Void andManifestIn(Game game, Flow<Game> flow) {
            flow.manifest(new DischargeExistingLeader(perished));
            flow.manifest(new FailExistingMissions(perished));

            game.objects().fractions().forEach(fraction -> {
                fraction.awareOf().colonies().remove(perished);
                fraction.governed().colonies().remove(perished);
            });

            flow.manifest(new PerishCraft(perished.structure()));
            game.objects().colonies().perish(perished);
            return null;
        }
    }

    record PerishOutpost(LunarOutpost perished) implements Conquering, Byproduct<Void> {

        @Override
        public Void andManifestIn(Game game, Flow<Game> flow) {
            flow.manifest(new DischargeExistingLeader(perished));
            flow.manifest(new FailExistingMissions(perished));

            game.objects().fractions().forEach(fraction -> {
                fraction.awareOf().outposts().remove(perished);
                fraction.governed().outposts().remove(perished);
            });

            flow.manifest(new PerishCraft(perished.structure()));
            game.objects().outposts().perish(perished);
            return null;
        }
    }

    record PerishOrbitalStation(OrbitalStation perished) implements Conquering, Byproduct<Void> {

        @Override
        public Void andManifestIn(Game game, Flow<Game> flow) {
            flow.manifest(new DischargeExistingLeader(perished));
            flow.manifest(new FailExistingMissions(perished));

            game.objects().fractions().forEach(fraction -> {
                fraction.awareOf().orbitals().remove(perished);
                fraction.governed().orbitals().remove(perished);
            });

            flow.manifest(new PerishCraft(perished.structure()));
            game.objects().orbitals().perish(perished);
            return null;
        }
    }

}
