package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.Any.Defined;
import se.jbee.spacecrafts.sim.Any.Definition;
import se.jbee.spacecrafts.sim.Any.Embedded;
import se.jbee.spacecrafts.sim.Any.Quality;
import se.jbee.spacecrafts.sim.Resourcing.Numbers;
import se.jbee.spacecrafts.sim.collection.Q;

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
            Insights provides
    ) implements Definition {}

    record Insights(Q<Crafting.Component> components) implements Embedded {}
}
