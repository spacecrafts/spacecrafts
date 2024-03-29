package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.Crafting.Craft;
import se.jbee.spacecrafts.sim.Exploring.Coordinate;
import se.jbee.spacecrafts.sim.Exploring.Moon;
import se.jbee.spacecrafts.sim.Exploring.Planet;
import se.jbee.spacecrafts.sim.Exploring.SolarSystem;
import se.jbee.spacecrafts.sim.Governing.Asset;
import se.jbee.spacecrafts.sim.Governing.Commanded;
import se.jbee.spacecrafts.sim.Governing.Fraction;
import se.jbee.spacecrafts.sim.Governing.Governed;
import se.jbee.turnmaster.data.Any.Creation;
import se.jbee.turnmaster.data.Flux;
import se.jbee.turnmaster.data.Maybe;
import se.jbee.turnmaster.data.Numbers;
import se.jbee.turnmaster.data.Vary;

public interface Conquering {

    /*
    Assets
     */

    @FunctionalInterface
    interface Fleets {

        Flux<Fleet> fleets();

        default Maybe<Fleet> fleet(Fraction by) {
            return fleets().first(fleet -> fleet.header().origin() == by);
        }
    }

    record Colony(
        Governed header,
        Craft structure,
        Planet on
    ) implements Asset {}

    record Spaceship(
        Governed header,
        Craft structure
    ) implements Asset {}

    record OrbitalStation(
        Governed header,
        Craft structure,
        Planet by
    ) implements Asset {}

    record SpaceStation(
        Governed header,
        Craft structure,
        SolarSystem in
    ) implements Asset {}

    record LunarOutpost(
        Governed header,
        Craft structure,
        Moon on
    ) implements Asset {}

    /**
     * Note that a {@link Fleet} is not an {@link Asset} as it is not a physical
     * entity rather than a concept.
     */
    record Fleet(
        Commanded header,
        Flux<Spaceship> members,
        Numbers actuals,
        Vary<Coordinate> location,
        Vary<SolarSystem> destination
    ) implements Creation {}

    /**
     * A {@link Fleet} can be turned into a {@link MercenaryUnit}. As such it
     * can be accepted by other {@link Fraction}s to complete a {@link
     * Trading.Mission}.
     */
    record MercenaryUnit(
        Commanded header,
        Fleet unit
    ) implements Creation {}
}
