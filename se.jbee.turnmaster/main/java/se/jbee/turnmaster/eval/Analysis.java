package se.jbee.turnmaster.eval;

import se.jbee.turnmaster.Engine;

@FunctionalInterface
public interface Analysis<G extends Engine.Game, T> {

    T analysing(G game);
}
