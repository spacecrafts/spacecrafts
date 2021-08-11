package test.integration.utils;

import se.jbee.spacecrafts.sim.engine.Collection;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Spcial assertions for the project.
 */
public class Assertions {

    private Assertions() {
        throw new UnsupportedOperationException("util");
    }

    public static <T> void assertForEach(java.util.Collection<T> expected, Consumer<Consumer<T>> actual) {
        List<T> actuals = new ArrayList<>();
        actual.accept(actuals::add);
        assertEquals(expected.size(),
                actuals.size(),
                "forEach did not call for the expected number of elements");
        assertEquals(expected, actuals);
    }

    public static <T> void assertForEach(Collection<T> expected, Consumer<Consumer<T>> actual) {
        List<T> actuals = new ArrayList<>();
        actual.accept(actuals::add);
        assertEquals(expected.size(),
                actuals.size(),
                "forEach did not call for the expected number of elements");
        List<T> exp = new ArrayList<>();
        expected.forEach(exp::add);
        assertEquals(exp, actuals);
    }
}
