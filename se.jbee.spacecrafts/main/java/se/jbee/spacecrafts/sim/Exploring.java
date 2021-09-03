package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.Conquering.Fleet;
import se.jbee.spacecrafts.sim.Resourcing.Influence;
import se.jbee.spacecrafts.sim.Resourcing.Substance;
import se.jbee.turnmaster.data.Any;
import se.jbee.turnmaster.data.Any.Creation;
import se.jbee.turnmaster.data.Any.Generated;
import se.jbee.turnmaster.data.Flux;
import se.jbee.turnmaster.data.XY;

import static java.lang.Math.round;

public interface Exploring {

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

    record Coordinate(
        int x,
        int y,
        int z
    ) {

        public Coordinate movedTowards(Coordinate target, int distance) {
            assert distance >= 0;
            int dx = target.x - x;
            int dy = target.y - y;
            int dz = target.z - z;
            double total = Math.sqrt((double) dx * dx + dy * dy + dz * dz);
            if (total <= distance) return target;
            float factor = (float) (distance / total);
            return new Coordinate(round(dx * factor), round(dy * factor),
                round(dy * factor));
        }
    }

    record Sighting(
        Coordinate at,
        int inTurn
    ) implements Any.Embedded {}
}
