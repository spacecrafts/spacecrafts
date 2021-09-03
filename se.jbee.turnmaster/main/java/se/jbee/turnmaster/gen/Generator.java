package se.jbee.turnmaster.gen;

import se.jbee.turnmaster.Engine;
import se.jbee.turnmaster.data.Any;

public interface Generator<G extends Engine.Game, T extends Any.Entity> {

    T generateIn(G game);
}
