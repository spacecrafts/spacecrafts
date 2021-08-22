package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.Conquering.Fleet;
import se.jbee.spacecrafts.sim.Resourcing.Influence;
import se.jbee.spacecrafts.sim.Resourcing.Substance;
import se.jbee.turnmaster.data.Any.Creation;
import se.jbee.turnmaster.data.Any.Generated;
import se.jbee.turnmaster.data.Flux;
import se.jbee.turnmaster.data.XY;

public interface Exploring {

    record Coordinate(
        int x,
        int y,
        int z
    ) {}

    record Sector(
        Generated header,
        int vastness,
        Flux<SolarSystem> systems,
        Flux<Planet> planets,
        Flux<Moon> moons
    ) implements Creation {}

    record SolarSystem(
        Generated header,
        int vastness,
        Sector in,
        Coordinate at,
        Flux<Planet> planets,
        // orbiting
        Flux<Fleet> fleets
    ) implements Creation, Conquering.Fleets {}

    record Planet(
        Generated header,
        int vastness,
        SolarSystem in,
        Flux<Influence> features,
        XY<Substance> surface,
        Flux<Moon> moons
    ) implements Creation {}

    record Moon(
        Generated header,
        int vastness,
        Planet by,
        Flux<Influence> features,
        XY<Substance> surface
    ) implements Creation {}
}
