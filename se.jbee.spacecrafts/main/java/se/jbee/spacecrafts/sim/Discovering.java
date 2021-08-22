package se.jbee.spacecrafts.sim;

import se.jbee.turnmaster.Any.Defined;
import se.jbee.turnmaster.Any.Definition;
import se.jbee.turnmaster.Any.Grade;
import se.jbee.turnmaster.data.Numbers;
import se.jbee.turnmaster.data.Stasis;

public interface Discovering {

    record Field(
        Defined header,
        int ordinal
    ) implements Grade {}

    record Discovery(
        Defined header,
        Field in,
        int level,
        Numbers preconditions,
        Stasis<Crafting.Component> provided
    ) implements Definition {}

}
