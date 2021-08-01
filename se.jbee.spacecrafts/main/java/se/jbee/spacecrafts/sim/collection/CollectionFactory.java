package se.jbee.spacecrafts.sim.collection;

import se.jbee.spacecrafts.sim.Any;
import se.jbee.spacecrafts.sim.Game;
import se.jbee.spacecrafts.sim.Resourcing;

public interface CollectionFactory {

    Resourcing.Numbers newNumbers(Game game);

    Resourcing.Tags newTags(Game game);

    <T> Any.Controls<T> newControls(Game game, Class<T> type);

    <T extends Any.Entity> Pool<T> newPool(Class<T> type, int initialCapacity);

    <T extends Any.Definition> Index<T> newIndex(Class<T> type, int initialCapacity);

    <T extends Any.Quality> Range<T> newRange(Class<T> type, int initialCapacity);
}
