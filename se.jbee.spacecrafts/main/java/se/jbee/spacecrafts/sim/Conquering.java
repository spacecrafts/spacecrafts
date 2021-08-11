package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.Any.Created;
import se.jbee.spacecrafts.sim.Any.Creation;
import se.jbee.spacecrafts.sim.Crafting.Craft;
import se.jbee.spacecrafts.sim.Governing.Asset;
import se.jbee.spacecrafts.sim.Governing.Governed;
import se.jbee.spacecrafts.sim.Resourcing.Influence;
import se.jbee.spacecrafts.sim.Resourcing.Substance;
import se.jbee.spacecrafts.sim.engine.Flux;
import se.jbee.spacecrafts.sim.engine.Maybe;
import se.jbee.spacecrafts.sim.engine.Numbers;
import se.jbee.spacecrafts.sim.engine.XY;

public interface Conquering {

    record Galaxy(
            Created header,
            Flux<SolarSystem> systems,
            Flux<Plant> planets,
            Flux<Moon> moons
    ) implements Creation {}

    record Plant(
            Created header,
            Flux<Influence> features,
            XY<Substance> surface,
            Flux<Moon> moons,
            Maybe<OrbitalStation> orbit
    ) implements Creation {}

    record Moon(
            Created header,
            Flux<Influence> features,
            XY<Substance> surface
    ) implements Creation {}

    record SolarSystem(
            Created header,
            Flux<Plant> planets,
            Maybe<SpaceStation> station,
            Flux<Spaceship> proximity
    ) implements Creation {}

    /*
    Assets
     */

    record Colony(
            Governed header,
            Craft structure,
            Plant on
    ) implements Asset {}

    record Spaceship(
            Governed header,
            Craft structure
    ) implements Asset {}

    record OrbitalStation(
            Governed header,
            Craft structure,
            Plant by
    ) implements Asset {}

    record SpaceStation(
            Governed header,
            Craft structure,
            SolarSystem in
    ) implements Asset {}

    record LunarBase(
            Governed header,
            Craft structure,
            Moon on
    ) implements Asset {}


    /**
     * Note that a {@link Fleet} is not an {@link Asset} as it is not a physical
     * entity rather than a concept.
     */
    record Fleet(
            Created header,
            Flux<Spaceship> members,
            Numbers actuals
    ) implements Creation {}

}
