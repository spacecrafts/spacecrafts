package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.Conquering.Fleet;
import se.jbee.spacecrafts.sim.Resourcing.Influence;
import se.jbee.spacecrafts.sim.Resourcing.Substance;
import se.jbee.spacecrafts.sim.engine.Any.Created;
import se.jbee.spacecrafts.sim.engine.Any.Creation;
import se.jbee.spacecrafts.sim.engine.Flux;
import se.jbee.spacecrafts.sim.engine.XY;

public interface Exploring {

    record Sector(
            Created header,
            Flux<SolarSystem> systems,
            Flux<Planet> planets,
            Flux<Moon> moons
    ) implements Creation {}

    record SolarSystem(
            Created header,
            Flux<Planet> planets,
            Flux<Fleet> proximity
    ) implements Creation {}

    record Planet(
            Created header,
            Flux<Influence> features,
            XY<Substance> surface,
            Flux<Moon> moons
    ) implements Creation {}

    record Moon(
            Created header,
            Flux<Influence> features,
            XY<Substance> surface
    ) implements Creation {}
}
