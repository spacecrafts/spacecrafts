package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.Any.Created;
import se.jbee.spacecrafts.sim.Any.Creation;
import se.jbee.spacecrafts.sim.Any.Embedded;
import se.jbee.spacecrafts.sim.Any.Text;
import se.jbee.spacecrafts.sim.Resourcing.Influence;
import se.jbee.spacecrafts.sim.Resourcing.Numbers;
import se.jbee.spacecrafts.sim.Resourcing.Tags;
import se.jbee.spacecrafts.sim.collection.Flux;
import se.jbee.spacecrafts.sim.collection.Maybe;
import se.jbee.spacecrafts.sim.collection.Q;

public interface Governing {

    record Fraction(
            Created header,
            Numbers score,
            Tags attributes,
            Governance governed
    ) implements Creation {}

    record Leader(
            Created header,
            Q<Influence> influences,
            Numbers multipliers,
            Maybe<Asset> assignment
    ) implements Creation {}

    interface Asset extends Creation {

        @Override
        GovernedHeader header();

        Crafting.Craft structure();
    }

    interface GovernedHeader extends Any.CreatedHeader {}

    record Governed(
            int serial,
            Text name,
            Fraction origin
    ) implements GovernedHeader {}

    /**
     * Ownership by a {@link Fraction} is given when an {@link Asset} is
     * contained in any of the asserts lists of this record.
     * <p>
     * Shared ownership simply means an {@link Asset} occurs in lists of
     * multiple {@link Fraction}s.
     */
    record Governance(
            // Assets
            Flux<Conquering.Colony> colonies,
            Flux<Conquering.LunarBase> bases,
            Flux<Conquering.OrbitalStation> orbitals,
            Flux<Conquering.Spaceship> spaceships,
            Flux<Conquering.SpaceStation> stations,

            // Non assets
            Flux<Conquering.Fleet> fleets,
            Flux<Leader> leaders
    ) implements Embedded {}

}
