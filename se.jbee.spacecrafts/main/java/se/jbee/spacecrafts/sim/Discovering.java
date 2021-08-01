package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.Any.Defined;
import se.jbee.spacecrafts.sim.Any.Definition;

public interface Discovering {

    record Discovery(Defined header) implements Definition {}

}
