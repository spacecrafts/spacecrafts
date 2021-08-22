package se.jbee.turnmaster.eval;

import se.jbee.turnmaster.Any;
import se.jbee.turnmaster.Engine;

public interface Decision<G extends Engine.Game> extends Any.Computed {

    interface Byproduct<G extends Engine.Game, T> extends Decision<G> {

        @Override
        default void manifestIn(G game, Engine.Flow<G> flow) {
            andManifestIn(game, flow);
        }

        T andManifestIn(G game, Engine.Flow<G> flow);
    }

    void manifestIn(G game, Engine.Flow<G> flow);

}
