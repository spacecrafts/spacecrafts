package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.Any.Controls;
import se.jbee.spacecrafts.sim.Any.Creation;
import se.jbee.spacecrafts.sim.Any.Definition;
import se.jbee.spacecrafts.sim.Any.Quality;
import se.jbee.spacecrafts.sim.engine.*;

public interface Factory {

    interface CacheFactory {

    }

    interface FluxFactory {

    }

    interface RegisterFactory {
        <T extends Creation> Register<T> newRegister(Class<T> type, int initialCapacity);
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
        Marks newTags(Game game);
    }

    interface NumbersFactory {
        Numbers newNumbers(Game game);
    }
}
