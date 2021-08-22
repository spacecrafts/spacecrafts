package se.jbee.spacecrafts.sim.decision;

import se.jbee.spacecrafts.sim.Conquering.Fleet;
import se.jbee.spacecrafts.sim.Exploring;
import se.jbee.spacecrafts.sim.Exploring.Moon;
import se.jbee.spacecrafts.sim.Exploring.Planet;
import se.jbee.spacecrafts.sim.Exploring.Sector;
import se.jbee.spacecrafts.sim.Exploring.SolarSystem;
import se.jbee.spacecrafts.sim.Game;
import se.jbee.spacecrafts.sim.Game.Decision;
import se.jbee.spacecrafts.sim.Game.Implication;
import se.jbee.turnmaster.Engine.Flow;

public interface ExploringDecisions {

    record DispatchFleet(
        Fleet dispatched,
        SolarSystem to
    ) implements Exploring, Decision {

        @Override
        public void manifestIn(Game game, Flow<Game> flow) {
            dispatched.destination().set(to);
        }
    }

    /*
    Byproducts
     */

    record SpawnSector() implements Exploring, Implication<Sector> {

        @Override
        public Sector andManifestIn(Game game, Flow<Game> flow) {
            return null;
        }
    }

    record SpawnSolarSystem(Sector in) implements Exploring, Implication<SolarSystem> {

        @Override
        public SolarSystem andManifestIn(Game game, Flow<Game> flow) {
            return null;
        }
    }

    record SpawnPlanet(SolarSystem in) implements Exploring, Implication<Planet> {

        @Override
        public Planet andManifestIn(Game game, Flow<Game> flow) {
            return null;
        }
    }

    record SpawnMoon(Planet with) implements Exploring, Implication<Moon> {

        @Override
        public Moon andManifestIn(Game game, Flow<Game> flow) {
            return null;
        }
    }
}
