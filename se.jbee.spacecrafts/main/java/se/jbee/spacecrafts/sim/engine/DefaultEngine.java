package se.jbee.spacecrafts.sim.engine;

import se.jbee.spacecrafts.sim.Any;
import se.jbee.spacecrafts.sim.Any.Entity;
import se.jbee.spacecrafts.sim.Game;
import se.jbee.spacecrafts.sim.Resourcing;
import se.jbee.spacecrafts.sim.state.*;

public class DefaultEngine {

    public <T extends Entity> Q<T> newQ(Game game, int initialCapacity) {
        //TODO empty Q
        return new ArrayQ<>(initialCapacity);
    }

    public Resourcing.Numbers newNumbers(Game game) {
        return new ArrayNumbers(game.entities().properties());
    }

    public Resourcing.Marks newTags(Game game) {
        return new ArrayMarks(game.entities().indicators());
    }

    public <T> Any.Controls<T> newControls(Game game, Class<T> type) {
        var controls = game.entities().controlGroups() //
                .first(g -> g.of() == type) //
                .map(Any.ControlGroup::controls) //
                .orElse(null);
        return new ArrayControls<>(controls);
    }

    public <T extends Any.Creation> Register<T> newRegister(Class<T> type, int initialCapacity) {
        return new ArrayRegister<>(type, initialCapacity);
    }

    public <T extends Any.Definition> Index<T> newIndex(Class<T> type, int initialCapacity) {
        return new ArrayIndex<>(type, initialCapacity);
    }

    public <T extends Any.Quality> Range<T> newRange(Class<T> type, int initialCapacity) {
        return new ArrayRange<>(type, initialCapacity);
    }

    public <T extends Entity> Flux<T> newFlux(Pool<T> of) {
        return new BitMaskFlux<>(of);
    }
}
