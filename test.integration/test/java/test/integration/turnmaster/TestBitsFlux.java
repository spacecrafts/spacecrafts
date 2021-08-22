package test.integration.turnmaster;

import org.junit.jupiter.api.Test;
import se.jbee.turnmaster.data.Flux;
import se.jbee.turnmaster.data.Register;
import se.jbee.turnmaster.data.Stasis;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static test.integration.utils.Assertions.assertForEach;

class TestBitsFlux {

    private final Register<Car> cars = Register.newDefault(Car.class, 3);
    private final Flux<Car> eCars = Flux.newDefault(cars);

    @Test
    void size_0() {
        assertEquals(0, eCars.size());
    }

    @Test
    void size() {
        eCars.add(cars.spawn(Car::new));
        assertEquals(1, eCars.size());
        eCars.add(cars.spawn(Car::new));
        assertEquals(2, eCars.size());
        eCars.add(cars.spawn(Car::new));
        assertEquals(3, eCars.size());
    }

    @Test
    void size_addingMultipleTimesHasNoEffect() {
        eCars.add(cars.spawn(Car::new));
        eCars.add(cars.spawn(Car::new));
        assertEquals(2, eCars.size());
        eCars.add(cars.get(1));
        assertEquals(2, eCars.size());
    }

    @Test
    void size_removingMultipleTimesHasNoEffect() {
        eCars.add(cars.spawn(Car::new));
        eCars.add(cars.spawn(Car::new));
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
        eCars.add(cars.spawn(Car::new));
        eCars.forEach(c -> assertEquals(cars.get(0), c));
        eCars.add(cars.spawn(Car::new));
        assertForEach(asList(cars.get(0), cars.get(1)), eCars::forEach);
    }

    @Test
    void contains_0() {
        assertFalse(eCars.contains(cars.spawn(Car::new)));
    }

    @Test
    void contains() {
        var c1 = cars.spawn(Car::new);
        eCars.add(c1);
        assertTrue(eCars.contains(c1));
    }

    @Test
    void first_0() {
        assertFalse(eCars.first(c -> c.header().serial() == 13).isSome());
    }

    @Test
    void first() {
        var c1 = cars.spawn(Car::new);
        eCars.add(c1);
        assertTrue(eCars.first(c -> c.header().serial() == 0).isSome());
    }

    @Test
    void isEmpty_0() {
        assertTrue(eCars.isEmpty());
    }

    @Test
    void isEmpty() {
        eCars.add(cars.spawn(Car::new));
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
        eCars.add(cars.spawn(Car::new));
        eCars.clear();
        assertEquals(0, eCars.size());
        assertTrue(eCars.isEmpty());
    }

    @Test
    void addCollection_0() {
        Stasis<Car> other = eCars.inStasis();
        eCars.add(other);
        assertTrue(eCars.isEmpty());
    }

    @Test
    void addCollection() {
        eCars.add(cars.spawn(Car::new));
        eCars.add(cars.spawn(Car::new));
        Stasis<Car> other = eCars.inStasis();
        eCars.clear();
        assertTrue(eCars.isEmpty());
        eCars.add(other);
        assertForEach(asList(cars.get(0), cars.get(1)), eCars::forEach);
    }

    @Test
    void removeCollection_0() {
        Stasis<Car> other = eCars.inStasis();
        eCars.remove(other);
        assertEquals(0, eCars.size());
    }

    @Test
    void removeCollection() {
        eCars.add(cars.spawn(Car::new));
        Stasis<Car> other = eCars.inStasis();
        eCars.add(cars.spawn(Car::new));
        assertEquals(2, eCars.size());
        eCars.remove(other);
        assertForEach(singletonList(cars.get(1)), eCars::forEach);
    }
}
