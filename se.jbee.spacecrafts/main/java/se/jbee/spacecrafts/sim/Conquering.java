package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.Crafting.Craft;
import se.jbee.spacecrafts.sim.Governing.Asset;
import se.jbee.spacecrafts.sim.Governing.Fraction;
import se.jbee.spacecrafts.sim.Governing.Governed;
import se.jbee.spacecrafts.sim.Resourcing.Influence;
import se.jbee.spacecrafts.sim.Resourcing.Substance;
import se.jbee.spacecrafts.sim.engine.Any.Created;
import se.jbee.spacecrafts.sim.engine.Any.Creation;
import se.jbee.spacecrafts.sim.engine.Flux;
import se.jbee.spacecrafts.sim.engine.Numbers;
import se.jbee.spacecrafts.sim.engine.Vary;
import se.jbee.spacecrafts.sim.engine.XY;

public interface Conquering {

    record Galaxy(
            Created header,
            Flux<SolarSystem> systems,
            Flux<Planet> planets,
            Flux<Moon> moons
    ) implements Creation {}

    record Planet(
            Created header,
            Flux<Influence> features,
            XY<Substance> surface,
            Flux<Moon> moons,
            Vary<OrbitalStation> orbit
    ) implements Creation {}

    record Moon(
            Created header,
            Flux<Influence> features,
            XY<Substance> surface
    ) implements Creation {}

    record SolarSystem(
            Created header,
            Flux<Planet> planets,
            Vary<SpaceStation> station,
            Flux<Spaceship> proximity
    ) implements Creation {}

    /*
    Assets
     */

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
            Governed header,
            Flux<Spaceship> members,
            Numbers actuals
    ) implements Creation {}

    /**
     * A {@link Fleet} can be turned into a {@link MercenaryUnit}. As such it
     * can be hired by other {@link Fraction}s to complete a {@link
     * Trading.Mission}.
     */
    record MercenaryUnit(
            Governed header,
            Fleet unit,
            Vary<Trading.Mission> mission
    ) implements Creation {}

}
