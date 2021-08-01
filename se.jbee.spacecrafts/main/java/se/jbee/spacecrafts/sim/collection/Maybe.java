package se.jbee.spacecrafts.sim.collection;

import java.util.NoSuchElementException;

public interface Maybe<T> {

    boolean isPresent();

    T get() throws NoSuchElementException;

    T getOrElse(T value);
}
