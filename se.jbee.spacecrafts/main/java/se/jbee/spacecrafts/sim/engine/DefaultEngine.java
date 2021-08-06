package se.jbee.spacecrafts.sim.engine;

import se.jbee.spacecrafts.sim.Any;
import se.jbee.spacecrafts.sim.Any.Entity;
import se.jbee.spacecrafts.sim.Game;
import se.jbee.spacecrafts.sim.Resourcing;
import se.jbee.spacecrafts.sim.collection.Index;
import se.jbee.spacecrafts.sim.collection.Pool;
import se.jbee.spacecrafts.sim.collection.Q;
import se.jbee.spacecrafts.sim.collection.Range;

public class DefaultEngine {
    public <T extends Entity> Q<T> newQ(Game game, int initialCapacity) {
        return new ArrayQ<>(initialCapacity);
    }

    public Resourcing.Numbers newNumbers(Game game) {
        return new ArrayNumbers(game.entities().properties());
    }

    public Resourcing.Tags newTags(Game game) {
        return new ArrayTags(game.entities().tags());
    }

    public <T> Any.Controls<T> newControls(Game game, Class<T> type) {
        return new ArrayControls<>(game.entities().controlGroups().first(g -> g.of() == type).controls());
    }

    public <T extends Entity> Pool<T> newPool(Class<T> type, int initialCapacity) {
        return new ArrayPool<>(type, initialCapacity);
    }

    public <T extends Any.Definition> Index<T> newIndex(Class<T> type, int initialCapacity) {
        return new ArrayIndex<>(type, initialCapacity);
    }

    public <T extends Any.Quality> Range<T> newRange(Class<T> type, int initialCapacity) {
        return new ArrayRange<>(type, initialCapacity);
    }
}
