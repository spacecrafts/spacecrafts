package se.jbee.spacecrafts.sim.definition;

import se.jbee.spacecrafts.sim.Game;
import se.jbee.spacecrafts.sim.Resourcing;
import se.jbee.spacecrafts.sim.Resourcing.Influence;
import se.jbee.spacecrafts.sim.Resourcing.Phenomenon;
import se.jbee.turnmaster.data.Pick;

/**
 * Gravitation is a {@link Phenomenon} ranging from low to high gravity {@link
 * Influence}.
 */
public record Gravitation(
    String code,
    Pick<Resourcing.Process> progression
) implements Game.Module, Definitions.InfluenceSource {

    //TODO affects physical work performance only!

    // effect is different depending on indicators
    // when robotic: low, normal, high => 100%, very-low => 110%, very-high => 90%
    // when low-g-home:
    // when high-g-home:
    // otherwise

    // also note that spaceships, orbital- and space-stations need gravitation dynamically evaluated
    // one of their drawbacks is that gravitation has to be established using equipments
    // this means gravitation levels are per cell or equipment based on the spread of the gravitation-generator

    @Override
    public void installIn(Game game) {
        game.addPhenomenon("gravitation", //
            new Gravitation("gravitation.very-low", null), //
            new Gravitation("gravitation.low", null), //
            new Gravitation("gravitation.normal", null), //
            new Gravitation("gravitation.high", null), //
            new Gravitation("gravitation.very-high", null));
    }
}
