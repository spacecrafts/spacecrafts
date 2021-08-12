package se.jbee.spacecrafts.sim.engine;

public interface Stasis<T extends Any.Entity> extends Collection<T> {

    /**
     * @return a modifiable copy of this
     */
    Flux<T> inFlux();
}
