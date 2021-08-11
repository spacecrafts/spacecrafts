package test.integration.engine;

import org.junit.jupiter.api.Test;
import se.jbee.spacecrafts.sim.engine.DefaultEngine;
import se.jbee.spacecrafts.sim.state.Flux;
import se.jbee.spacecrafts.sim.state.Register;
import se.jbee.spacecrafts.sim.state.Stasis;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static test.integration.utils.Assertions.assertForEach;

class TestBitMaskFlux {

    private final Register<Car> cars;
    private final Flux<Car> eCars;

    {
        var engine = new DefaultEngine();
        cars = engine.newRegister(Car.class, 3);
        eCars = engine.newFlux(cars);
    }

    @Test
    void size_0() {
        assertEquals(0, eCars.size());
    }

    @Test
    void size() {
        eCars.add(cars.add(Car::new));
        assertEquals(1, eCars.size());
        eCars.add(cars.add(Car::new));
        assertEquals(2, eCars.size());
        eCars.add(cars.add(Car::new));
        assertEquals(3, eCars.size());
    }

    @Test
    void size_addingMultipleTimesHasNoEffect() {
        eCars.add(cars.add(Car::new));
        eCars.add(cars.add(Car::new));
        assertEquals(2, eCars.size());
        eCars.add(cars.get(1));
        assertEquals(2, eCars.size());
    }

    @Test
    void size_removingMultipleTimesHasNoEffect() {
        eCars.add(cars.add(Car::new));
        eCars.add(cars.add(Car::new));
        assertEquals(2, eCars.size());
        eCars.remove(cars.get(1));
        assertEquals(1, eCars.size());
        eCars.remove(cars.get(1));
        assertEquals(1, eCars.size());
    }

    @Test
    void forEach_0() {
        eCars.forEach(c -> fail("Should not be called"));
    }

    @Test
    void forEach() {
        eCars.add(cars.add(Car::new));
        eCars.forEach(c -> assertEquals(cars.get(0), c));
        eCars.add(cars.add(Car::new));
        assertForEach(asList(cars.get(0), cars.get(1)), eCars::forEach);
    }

    @Test
    void contains_0() {
        assertFalse(eCars.contains(cars.add(Car::new)));
    }

    @Test
    void contains() {
        var c1 = cars.add(Car::new);
        eCars.add(c1);
        assertTrue(eCars.contains(c1));
    }

    @Test
    void first_0() {
        assertFalse(eCars.first(c -> c.header().serial() == 13).isSome());
    }

    @Test
    void first() {
        var c1 = cars.add(Car::new);
        eCars.add(c1);
        assertTrue(eCars.first(c -> c.header().serial() == 0).isSome());
    }

    @Test
    void isEmpty_0() {
        assertTrue(eCars.isEmpty());
    }

    @Test
    void isEmpty() {
        eCars.add(cars.add(Car::new));
        assertFalse(eCars.isEmpty());
    }

    @Test
    void clear_0() {
        eCars.clear();
        assertEquals(0, eCars.size());
        assertTrue(eCars.isEmpty());
    }

    @Test
    void clear() {
        eCars.add(cars.add(Car::new));
        eCars.clear();
        assertEquals(0, eCars.size());
        assertTrue(eCars.isEmpty());
    }

    @Test
    void addCollection_0() {
        Stasis<Car> other = eCars.stasis();
        eCars.add(other);
        assertTrue(eCars.isEmpty());
    }

    @Test
    void addCollection() {
        eCars.add(cars.add(Car::new));
        eCars.add(cars.add(Car::new));
        Stasis<Car> other = eCars.stasis();
        eCars.clear();
        assertTrue(eCars.isEmpty());
        eCars.add(other);
        assertForEach(asList(cars.get(0), cars.get(1)), eCars::forEach);
    }

    @Test
    void removeCollection_0() {
        Stasis<Car> other = eCars.stasis();
        eCars.remove(other);
        assertEquals(0, eCars.size());
    }

    @Test
    void removeCollection() {
        eCars.add(cars.add(Car::new));
        Stasis<Car> other = eCars.stasis();
        eCars.add(cars.add(Car::new));
        assertEquals(2, eCars.size());
        eCars.remove(other);
        assertForEach(singletonList(cars.get(1)), eCars::forEach);
    }

    //Hallo
}
