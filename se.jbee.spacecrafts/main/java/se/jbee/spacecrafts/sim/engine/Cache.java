package se.jbee.spacecrafts.sim.engine;


public interface Cache<T extends Any.Computed> extends Any.Computed, Collection<T> {

    void add(T e);

    void remove(T e);

    void clear();

}
