package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.Conquering.Colony;
import se.jbee.spacecrafts.sim.Conquering.Fleet;
import se.jbee.spacecrafts.sim.Conquering.Fleets;
import se.jbee.spacecrafts.sim.Conquering.LunarOutpost;
import se.jbee.spacecrafts.sim.Conquering.MercenaryUnit;
import se.jbee.spacecrafts.sim.Conquering.OrbitalStation;
import se.jbee.spacecrafts.sim.Conquering.SpaceStation;
import se.jbee.spacecrafts.sim.Conquering.Spaceship;
import se.jbee.spacecrafts.sim.Crafting.Component;
import se.jbee.spacecrafts.sim.Crafting.Craft;
import se.jbee.spacecrafts.sim.Discovering.Discovery;
import se.jbee.spacecrafts.sim.Resourcing.Influence;
import se.jbee.turnmaster.Any.Created;
import se.jbee.turnmaster.Any.Creation;
import se.jbee.turnmaster.Any.Defined;
import se.jbee.turnmaster.Any.Definition;
import se.jbee.turnmaster.Any.Embedded;
import se.jbee.turnmaster.Any.IsCreated;
import se.jbee.turnmaster.Any.Text;
import se.jbee.turnmaster.data.Flux;
import se.jbee.turnmaster.data.Marks;
import se.jbee.turnmaster.data.NumberPer;
import se.jbee.turnmaster.data.Numbers;
import se.jbee.turnmaster.data.Q;
import se.jbee.turnmaster.data.Stasis;
import se.jbee.turnmaster.data.Vary;

public interface Governing {

    record Controller(
        boolean artificialAgent,
        int fromTurn
    ) {}

    record Fraction(
        Created header,
        Q<Controller> controlledBy,
        Numbers score,
        Marks properties,
        Flux<Trait> traits,
        Governance governed,
        Awareness awareOf
    ) implements Creation, Balance {

        @Override
        public Numbers totals() {
            return score;
        }
    }

    record Trait(
        Defined header,
        Numbers bonuses,
        Stasis<Influence> influences
    ) implements Definition {}

    record Sphere(
        Defined header,
        Stasis<Trait> members
    ) implements Definition {}

    record Leader(
        Created header,
        Stasis<Influence> influences,
        Numbers profile,
        NumberPer<Fraction> affection,
        Vary<Asset> assignment
    ) implements Creation {}

    interface Asset extends Creation {

        @Override
        IsGoverned header();

        Craft structure();
    }

    interface IsGoverned extends IsCreated {}

    record Governed(
        int serial,
        Text name,
        Fraction origin
    ) implements IsGoverned {}

    /**
     * Ownership by a {@link Fraction} is given when an {@link Asset} is
     * contained in any from the asserts lists from this record.
     * <p>
     * Shared ownership simply means an {@link Asset} occurs in lists from
     * multiple {@link Fraction}s.
     */
    record Governance(
        // Assets
        Flux<Colony> colonies,
        Flux<LunarOutpost> outposts,
        Flux<OrbitalStation> orbitals,
        Flux<Spaceship> spaceships,
        Flux<SpaceStation> stations,

        // Non assets
        Flux<Fleet> fleets,
        Flux<MercenaryUnit> mercenaries,
        Flux<Leader> leaders
    ) implements Embedded, Fleets {}

    record Awareness(
        // galaxy
        Flux<Exploring.Sector> galaxies,
        Flux<Exploring.SolarSystem> systems,
        Flux<Exploring.Planet> planets,
        Flux<Exploring.Moon> moons,

        // Assets
        Flux<Colony> colonies,
        Flux<LunarOutpost> outposts,
        Flux<OrbitalStation> orbitals,
        Flux<Spaceship> spaceships,
        Flux<SpaceStation> stations,

        // Non assets
        Flux<Craft> crafts,
        Flux<Fraction> fractions,
        Flux<Leader> leaders,
        Flux<Fleet> fleets,
        Flux<MercenaryUnit> mercenaries,
        Flux<Discovery> discoveries,
        Flux<Component> components
    ) implements Fleets {}
}
