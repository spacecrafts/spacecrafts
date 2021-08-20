package se.jbee.spacecrafts.sim.decision;

import se.jbee.spacecrafts.sim.*;
import se.jbee.spacecrafts.sim.Crafting.Deck;
import se.jbee.spacecrafts.sim.Governing.Fraction;
import se.jbee.spacecrafts.sim.Governing.Governed;
import se.jbee.spacecrafts.sim.decision.CraftingDecisions.CloneCraft;
import se.jbee.spacecrafts.sim.decision.CraftingDecisions.PerishCraft;
import se.jbee.spacecrafts.sim.decision.GoverningDecisions.DischargeExistingLeader;
import se.jbee.spacecrafts.sim.decision.TradingDecisions.CancelHire;
import se.jbee.spacecrafts.sim.decision.TradingDecisions.FailExistingMissions;
import se.jbee.spacecrafts.sim.engine.Any.Created;
import se.jbee.spacecrafts.sim.engine.Any.Text;
import se.jbee.spacecrafts.sim.engine.Collection;
import se.jbee.spacecrafts.sim.engine.Decision;
import se.jbee.spacecrafts.sim.engine.Maybe;

public interface ConqueringDecisions {

    record FoundSpaceStation(
            Exploring.SolarSystem in,
            Fraction by,
            Collection<Spaceship> from
    ) implements Conquering, Decision {

        @Override
        public void manifestIn(Game game, Processor processor) {
            //TODO find leader instead from 1st
            var ship1 = from.first().get();
            var name = in.header().name().get();
            var craft = game.objects().crafts().spawn(
                    serial -> new Crafting.Craft(
                            new Created(serial, new Text("DSS " + name)),
                            game.newNumbers(), Maybe.nothing(),
                            game.newFlux(Resourcing.Influence.class),
                            game.newTop(Deck.class),
                            game.newTop(Resourcing.Resource.class)));
            processor.manifest(new CloneCraft(ship1.structure(), craft));
            var station = game.objects().stations().spawn(
                    serial -> new SpaceStation(
                            new Governed(serial, new Text(name), by), craft,
                            in));
            by.governed().stations().add(station);
            by.awareOf().stations().add(station);
            from.forEach(ship -> {
                if (ship != ship1)
                    processor.manifest(new DockToSpaceStation(ship, station));
            });
        }
    }

    record DockToSpaceStation(
            Spaceship docking,
            SpaceStation to
    ) implements Conquering, Decision {

        @Override
        public void manifestIn(Game game, Processor processor) {
            to.structure().decks().pushBottom(docking.structure().decks());
            docking.structure().decks().clear(); // prevent perish from docked decks
            processor.manifest(new PerishSpaceship(docking));
        }
    }

    record FoundColony(
            Exploring.Planet on,
            Fraction by,
            Deck from,
            Spaceship origin
    ) implements Conquering, Decision {

        @Override
        public void manifestIn(Game game, Processor processor) {

        }
    }

    record FoundOutpost(
            Exploring.Moon on,
            Fraction by,
            Deck from,
            Spaceship origin
    ) implements Conquering, Decision {

        @Override
        public void manifestIn(Game game, Processor processor) {
            var decks = origin.structure().decks();
            decks.remove(from);
            if (decks.isEmpty()) {
                processor.manifest(new PerishSpaceship(origin));
                game.objects().outposts().spawn(serial -> new LunarOutpost(
                        new Governed(serial, origin.header().name(), by),
                        origin().structure(), on));
            } else {

            }
        }
    }

    record LaunchOrbitalStation(
            Fraction by,
            Deck from,
            Colony origin
    ) implements Conquering, Decision {

        @Override
        public void manifestIn(Game game, Processor processor) {

        }
    }

    record JoinFleet(
            Spaceship joining,
            Fleet with
    ) implements Conquering, Decision {

        @Override
        public void manifestIn(Game game, Processor processor) {
            with.members().add(joining);
        }
    }

    record LeaveFleet(
            Spaceship leaving,
            Fleet from
    ) implements Conquering, Decision {

        @Override
        public void manifestIn(Game game, Processor processor) {
            from.members().remove(leaving);
            if (from.members().isEmpty())
                processor.manifest(new PerishFleet(from));
        }
    }

    record BandMercenaryUnit(Fleet banded) implements Conquering, Decision {

        @Override
        public void manifestIn(Game game, Processor processor) {
            var fraction = banded.header().origin();
            var unit = game.objects().mercenaries().spawn(
                    serial -> new MercenaryUnit(new Governed(serial,
                            new Text(banded.header().name().get()), fraction),
                            banded));
            fraction.awareOf().mercenaries().add(unit);
            fraction.governed().fleets().remove(banded);
            fraction.governed().mercenaries().add(unit);
        }
    }

    record DisbandMercenaryUnit(MercenaryUnit disbanded) implements Conquering, Decision {

        @Override
        public void manifestIn(Game game, Processor processor) {
            processor.manifest(CancelHire::new, game.objects().hires().first(
                    hire -> hire.from() == disbanded));
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
    Support Decisions
     */

    record PerishFleet(Fleet perished) implements Conquering, Decision {

        @Override
        public void manifestIn(Game game, Processor processor) {
            processor.manifest(DisbandMercenaryUnit::new,
                    game.objects().mercenaries() //
                            .first(m -> m.unit() == perished));

            game.objects().systems().forEach(
                    system -> system.proximity().remove(perished));

            game.objects().fractions().forEach(fraction -> {
                fraction.awareOf().fleets().remove(perished);
                fraction.governed().fleets().remove(perished);
            });
            game.objects().fleets().perish(perished);
        }
    }

    record PerishSpaceship(Spaceship perished) implements Conquering, Decision {

        @Override
        public void manifestIn(Game game, Processor processor) {
            game.objects().fleets().forEach(
                    fleet -> fleet.members().remove(perished));

            processor.manifest(new DischargeExistingLeader(perished));
            processor.manifest(new FailExistingMissions(perished));

            game.objects().fractions().forEach(fraction -> {
                fraction.awareOf().spaceships().remove(perished);
                fraction.governed().spaceships().remove(perished);
            });

            processor.manifest(new PerishCraft(perished.structure()));
            game.objects().spaceships().perish(perished);
        }

    }

    record PerishSpaceStation(SpaceStation perished) implements Conquering, Decision {

        @Override
        public void manifestIn(Game game, Processor processor) {
            processor.manifest(new DischargeExistingLeader(perished));
            processor.manifest(new FailExistingMissions(perished));

            game.objects().fractions().forEach(fraction -> {
                fraction.awareOf().stations().remove(perished);
                fraction.governed().stations().remove(perished);
            });

            processor.manifest(new PerishCraft(perished.structure()));
            game.objects().stations().perish(perished);
        }
    }

    record PerishColony(Colony perished) implements Conquering, Decision {

        @Override
        public void manifestIn(Game game, Processor processor) {
            processor.manifest(new DischargeExistingLeader(perished));
            processor.manifest(new FailExistingMissions(perished));

            game.objects().fractions().forEach(fraction -> {
                fraction.awareOf().colonies().remove(perished);
                fraction.governed().colonies().remove(perished);
            });

            processor.manifest(new PerishCraft(perished.structure()));
            game.objects().colonies().perish(perished);
        }
    }

    record PerishOutpost(LunarOutpost perished) implements Conquering, Decision {

        @Override
        public void manifestIn(Game game, Processor processor) {
            processor.manifest(new DischargeExistingLeader(perished));
            processor.manifest(new FailExistingMissions(perished));

            game.objects().fractions().forEach(fraction -> {
                fraction.awareOf().outposts().remove(perished);
                fraction.governed().outposts().remove(perished);
            });

            processor.manifest(new PerishCraft(perished.structure()));
            game.objects().outposts().perish(perished);
        }
    }

    record PerishOrbitalStation(OrbitalStation perished) implements Conquering, Decision {

        @Override
        public void manifestIn(Game game, Processor processor) {
            processor.manifest(new DischargeExistingLeader(perished));
            processor.manifest(new FailExistingMissions(perished));

            game.objects().fractions().forEach(fraction -> {
                fraction.awareOf().orbitals().remove(perished);
                fraction.governed().orbitals().remove(perished);
            });

            processor.manifest(new PerishCraft(perished.structure()));
            game.objects().orbitals().perish(perished);
        }
    }

}
