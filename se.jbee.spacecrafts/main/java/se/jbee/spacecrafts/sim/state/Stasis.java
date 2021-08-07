package se.jbee.spacecrafts.sim.state;

import se.jbee.spacecrafts.sim.Any;

public interface Stasis<T extends Any.Entity> extends Collection<T> {

    Flux<T> flux();
}
