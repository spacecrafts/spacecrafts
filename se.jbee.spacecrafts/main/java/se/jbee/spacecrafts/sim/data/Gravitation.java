package se.jbee.spacecrafts.sim.data;

import se.jbee.spacecrafts.sim.Game;
import se.jbee.spacecrafts.sim.Resourcing.Influence;
import se.jbee.spacecrafts.sim.Resourcing.Phenomenon;

/**
 * Gravitation is a {@link Phenomenon} ranging from low to high gravity {@link
 * Influence}.
 */
public record Gravitation(
        String code

) {

    static final Gravitation LOW = new Gravitation("g-low");
    static final Gravitation NORMAL = new Gravitation("g-normal");
    static final Gravitation HIGH = new Gravitation("g-high");

    public void init(Game game) {
        Influence low = add(LOW, game);
        Influence normal = add(NORMAL, game);
        Influence high = add(HIGH, game);
        add(game, low, normal, high);
    }

    private Influence add(Gravitation g, Game game) {
        return null;
    }

    private Phenomenon add(Game game, Influence... members) {

        return null;
    }

}
