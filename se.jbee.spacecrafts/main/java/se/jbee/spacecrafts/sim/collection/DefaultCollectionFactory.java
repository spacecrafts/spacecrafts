package se.jbee.spacecrafts.sim.collection;

import se.jbee.spacecrafts.sim.Any;
import se.jbee.spacecrafts.sim.Game;
import se.jbee.spacecrafts.sim.Resourcing;

public class DefaultCollectionFactory implements CollectionFactory {
    @Override
    public Resourcing.Numbers newNumbers(Game game) {
        return new ArrayNumbers(game.entities().properties());
    }

    @Override
    public Resourcing.Tags newTags(Game game) {
        return new ArrayTags(game.entities().tags());
    }

    @Override
    public <T> Any.Controls<T> newControls(Game game, Class<T> type) {
        return new ArrayControls<>(game.entities().controlGroups().first(g -> g.of() == type).controls());
    }

    @Override
    public <T extends Any.Entity> Pool<T> newPool(Class<T> type, int initialCapacity) {
        return new ArrayPool<>(type, initialCapacity);
    }

    @Override
    public <T extends Any.Definition> Index<T> newIndex(Class<T> type, int initialCapacity) {
        return new ArrayIndex<>(type, initialCapacity);
    }

    @Override
    public <T extends Any.Quality> Range<T> newRange(Class<T> type, int initialCapacity) {
        return new ArrayRange<>(type, initialCapacity);
    }
}
