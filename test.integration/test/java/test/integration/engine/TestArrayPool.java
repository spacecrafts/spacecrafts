package test.integration.engine;

import org.junit.jupiter.api.Test;
import se.jbee.spacecrafts.sim.engine.DefaultEngine;
import se.jbee.spacecrafts.sim.state.Register;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestArrayPool {

    private final Register<Car> register;

    {
        var engine = new DefaultEngine();
        register = engine.newRegister(Car.class, 8);
    }

    @Test
    void of() {
        assertEquals(Car.class, register.of());
    }

    @Test
    void span_add1() {
        assertEquals(-1, register.span());
        Car f0 = register.add(Car::new);
        assertEquals(0, register.span());
        assertEquals(0, f0.header().serial());
    }

    @Test
    void get_unknown() {
        assertThrows(NoSuchElementException.class, () -> register.get(2));
    }

}
