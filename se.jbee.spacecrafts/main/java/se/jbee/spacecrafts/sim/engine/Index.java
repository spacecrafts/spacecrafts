package se.jbee.spacecrafts.sim.engine;

import se.jbee.spacecrafts.sim.Any;

import java.util.NoSuchElementException;

public interface Index<T extends Any.Definition> extends Pool<T> {

    default T get(Any.Code code) throws NoSuchElementException {
        return first(e -> e.header().code().equals(code)) //
                .orElseThrow(() -> new NoSuchElementException(of().getSimpleName() + " with code: " + code));
    }
}