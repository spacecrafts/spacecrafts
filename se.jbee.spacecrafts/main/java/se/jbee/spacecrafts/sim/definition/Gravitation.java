package se.jbee.spacecrafts.sim.definition;

import se.jbee.spacecrafts.sim.Game;
import se.jbee.spacecrafts.sim.Resourcing;
import se.jbee.spacecrafts.sim.Resourcing.Influence;
import se.jbee.spacecrafts.sim.Resourcing.Phenomenon;

/**
 * Gravitation is a {@link Phenomenon} ranging from low to high gravity {@link
 * Influence}.
 */
public record Gravitation(
    String code,
    Resourcing.Process progression
) implements Game.Module, Definitions.InfluenceSource {

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
