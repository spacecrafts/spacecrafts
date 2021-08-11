package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.Any.Defined;
import se.jbee.spacecrafts.sim.Any.Definition;
import se.jbee.spacecrafts.sim.Any.Quality;
import se.jbee.spacecrafts.sim.engine.Numbers;
import se.jbee.spacecrafts.sim.engine.Stasis;

public interface Discovering {

    record Field(
            Defined header,
            int ordinal
    ) implements Quality {}

    record Discovery(
            Defined header,
            Field in,
            int level,
            Numbers preconditions,
            Stasis<Crafting.Component> provided
    ) implements Definition {}

}
