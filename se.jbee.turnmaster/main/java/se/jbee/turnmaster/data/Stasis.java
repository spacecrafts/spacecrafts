package se.jbee.turnmaster.data;

public interface Stasis<T extends Any.Entity> extends Collection<T> {

    /**
     * @return a modifiable copy from this
     */
    Flux<T> inFlux();
}
