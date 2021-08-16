package se.jbee.spacecrafts.sim.definition;

import se.jbee.spacecrafts.sim.Game;
import se.jbee.spacecrafts.sim.Resourcing.Influence;
import se.jbee.spacecrafts.sim.Resourcing.Phenomenon;
import se.jbee.spacecrafts.sim.engine.Any;
import se.jbee.spacecrafts.sim.engine.Engine;

/**
 * Gravitation is a {@link Phenomenon} ranging from low to high gravity {@link
 * Influence}.
 */
public record Gravitation(
        String code

) implements Engine.Module {

    static final Gravitation LOW = new Gravitation("g-low");
    static final Gravitation NORMAL = new Gravitation("g-normal");
    static final Gravitation HIGH = new Gravitation("g-high");

    @Override
    public void installIn1(Game game) {
        Influence low = add(LOW, game);
        Influence normal = add(NORMAL, game);
        Influence high = add(HIGH, game);
        addPhenomenon(game, low, normal, high);
    }

    private Influence add(Gravitation g, Game game) {
        return null;
    }

    private Phenomenon addPhenomenon(Game game, Influence... members) {

        return game.objects().phenomena().spawn(serial -> new Phenomenon(new Any.Defined(
                serial,
                null,
                ""), null));
    }
}
