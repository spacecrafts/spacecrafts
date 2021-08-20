package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.Crafting.Craft;
import se.jbee.spacecrafts.sim.Exploring.SolarSystem;
import se.jbee.spacecrafts.sim.Governing.Asset;
import se.jbee.spacecrafts.sim.Governing.Fraction;
import se.jbee.spacecrafts.sim.Governing.Governed;
import se.jbee.spacecrafts.sim.engine.Any.Creation;
import se.jbee.spacecrafts.sim.engine.Flux;
import se.jbee.spacecrafts.sim.engine.Numbers;
import se.jbee.spacecrafts.sim.engine.Vary;

public interface Conquering {

    /*
    Assets
     */

    record Colony(
            Governed header,
            Craft structure,
            Exploring.Planet on
    ) implements Asset {}

    record Spaceship(
            Governed header,
            Craft structure
    ) implements Asset {}

    record OrbitalStation(
            Governed header,
            Craft structure,
            Exploring.Planet by
    ) implements Asset {}

    record SpaceStation(
            Governed header,
            Craft structure,
            SolarSystem in
    ) implements Asset {}

    record LunarOutpost(
            Governed header,
            Craft structure,
            Exploring.Moon on
    ) implements Asset {}

    /**
     * Note that a {@link Fleet} is not an {@link Asset} as it is not a physical
     * entity rather than a concept.
     */
    record Fleet(
            Governed header,
            Flux<Spaceship> members,
            Numbers actuals,
            Vary<SolarSystem> destination
    ) implements Creation {}

    /**
     * A {@link Fleet} can be turned into a {@link MercenaryUnit}. As such it
     * can be accepted by other {@link Fraction}s to complete a {@link
     * Trading.Mission}.
     */
    record MercenaryUnit(
            Governed header,
            Fleet unit
    ) implements Creation {}

}
