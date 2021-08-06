package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.Any.Controls;
import se.jbee.spacecrafts.sim.Any.Definition;
import se.jbee.spacecrafts.sim.Any.Entity;
import se.jbee.spacecrafts.sim.Any.Quality;
import se.jbee.spacecrafts.sim.collection.Index;
import se.jbee.spacecrafts.sim.collection.Pool;
import se.jbee.spacecrafts.sim.collection.Q;
import se.jbee.spacecrafts.sim.collection.Range;

public interface Factory {

    interface CacheFactory {

    }

    interface FluxFactory {

    }

    interface PoolFactory {
        <T extends Entity> Pool<T> newPool(Class<T> type, int initialCapacity);
    }

    interface IndexFactory {
        <T extends Definition> Index<T> newIndex(Class<T> type, int initialCapacity);
    }

    interface RangeFactory {
        <T extends Quality> Range<T> newRange(Class<T> type, int initialCapacity);
    }

    interface QFactory {
        <T> Q<T> newQ(Game game, int initialCapacity);
    }

    interface TopFactory {

    }

    interface XYFactory {

    }

    interface ControlsFactory {
        <T> Controls<T> newControls(Game game, Class<T> type);
    }

    interface TagsFactory {
        Resourcing.Tags newTags(Game game);
    }

    interface NumbersFactory {
        Resourcing.Numbers newNumbers(Game game);
    }
}
