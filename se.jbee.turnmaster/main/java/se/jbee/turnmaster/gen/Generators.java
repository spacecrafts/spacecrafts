package se.jbee.turnmaster.gen;

import se.jbee.turnmaster.Engine;
import se.jbee.turnmaster.data.Any;

public interface Generators<G extends Engine.Game> {

    <T extends Any.Entity> Generator<G, T> generating(Class<T> type);

    //TODO options?
}
