package se.jbee.turnmaster.data;

import se.jbee.turnmaster.Any;

public interface Stasis<T extends Any.Entity> extends Collection<T> {

    /**
     * @return a modifiable copy from this
     */
    Flux<T> inFlux();
}
