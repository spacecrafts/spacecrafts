package test.integration.turnmaster;

import org.junit.jupiter.api.Test;
import se.jbee.turnmaster.Register;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestArrayPool {

    private final Register<Car> cars = Register.newDefault(Car.class, 8);

    @Test
    void of() {
        assertEquals(Car.class, cars.of());
    }

    @Test
    void span_spawn1() {
        assertEquals(-1, cars.span());
        Car f0 = cars.spawn(Car::new);
        assertEquals(0, cars.span());
        assertEquals(0, f0.header().serial());
    }

    @Test
    void get_unknown() {
        assertThrows(NoSuchElementException.class, () -> cars.get(2));
    }

}
