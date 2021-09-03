package se.jbee.spacecrafts.sim;

import se.jbee.turnmaster.data.Any.Defined;
import se.jbee.turnmaster.data.Any.Definition;
import se.jbee.turnmaster.data.Any.Grade;
import se.jbee.turnmaster.data.Constants;
import se.jbee.turnmaster.data.Stasis;

public interface Discovering {

    record AreaOfInterest(
        Defined header
    ) implements Grade {}

    record Discovery(
        Defined header,
        AreaOfInterest in,
        int level,
        Constants preconditions,
        Stasis<Crafting.Component> enables,
        Constants contributes
    ) implements Definition {}

}
