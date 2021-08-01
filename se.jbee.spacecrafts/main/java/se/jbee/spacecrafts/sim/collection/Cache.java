package se.jbee.spacecrafts.sim.collection;


import se.jbee.spacecrafts.sim.Any;

import java.util.function.Consumer;

public interface Cache<T extends Any.Computed> extends Any.Computed {

    void add(T e);

    void remove(T e);

    void clear();

    void forEach(Consumer<? super T> f);
}
