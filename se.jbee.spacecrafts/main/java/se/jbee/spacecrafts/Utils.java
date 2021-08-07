package se.jbee.spacecrafts;

import se.jbee.spacecrafts.sim.state.Maybe;

import java.util.function.Supplier;

public final class Utils {

    private Utils() {
        throw new UnsupportedOperationException("util");
    }

    public static <T> Maybe<T> ofThrowing(Supplier<T> get) {
        try {
            return Maybe.some(get.get());
        } catch (RuntimeException ex) {
            return Maybe.nothing();
        }
    }
}
