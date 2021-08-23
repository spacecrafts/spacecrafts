package test.integration.turnmaster;

import org.junit.jupiter.api.Test;
import se.jbee.turnmaster.data.Maybe;
import se.jbee.turnmaster.data.Per;
import se.jbee.turnmaster.data.Per.Value;
import se.jbee.turnmaster.data.Register;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static test.integration.utils.Assertions.assertForEach;

class TestArrayPer {

    private final Register<Car> cars = Register.newDefault(Car.class, 3);
    private final Per<Car, String> per = Per.newDefault(cars, 2);

    @Test
    void size_0() {
        assertDoesNotThrow(per::size);
    }

    @Test
    void size() {
        var a = cars.spawn(Car::new);
        var b = cars.spawn(Car::new);
        var c = cars.spawn(Car::new);
        per.set(a, "a");
        assertEquals(1, per.size());
        per.set(b, "b");
        assertEquals(2, per.size());
        per.set(c, "c");
        assertEquals(3, per.size());
        per.remove(c);
        assertEquals(2, per.size());
        per.update(a, v -> "a2");
        assertEquals(2, per.size());
        per.update(c, v -> "c2");
        assertEquals(2, per.size());
    }

    @Test
    void forEach_0() {
        per.forEach(e -> fail("Should not be called for empty"));
    }

    @Test
    void forEach() {
        var a = cars.spawn(Car::new);
        var b = cars.spawn(Car::new);
        per.set(a, "a");
        per.set(b, "b");
        assertForEach(asList(new Value<>(a, "a"), new Value<>(b, "b")),
            per::forEach);
    }

    @Test
    void first_0() {
        assertFalse(per.first().isSome());
    }

    @Test
    void first() {
        per.set(cars.spawn(Car::new), "a");
        per.set(cars.spawn(Car::new), "b");
        per.set(cars.spawn(Car::new), "c");
        var first = per.first(e -> e.value().equals("b"));
        assertTrue(first.isSome());
        assertEquals("b", first.get().value());
    }

    @Test
    void get_0() {
        assertThrows(NullPointerException.class, () -> per.get(null));
        assertFalse(per.get(cars.spawn(Car::new)).isSome());
    }

    @Test
    void get() {
        var a = cars.spawn(Car::new);
        per.set(a, "a");
        assertEquals(Maybe.some("a"), per.get(a));
    }

    @Test
    void set_0() {
        var a = cars.spawn(Car::new);
        assertThrows(NullPointerException.class, () -> per.set(null, "a"));
        assertThrows(NullPointerException.class, () -> per.set(a, null));
        assertFalse(per.set(a, "a").isSome());
    }

    @Test
    void set() {
        var a = cars.spawn(Car::new);
        assertFalse(per.set(a, "a").isSome());
        assertEquals("a", per.get(a).get());
        assertEquals("a", per.set(a, "a2").get());
        assertEquals("a2", per.get(a).get());
        assertFalse(per.set(cars.spawn(Car::new), "b").isSome());
        assertFalse(per.set(cars.spawn(Car::new), "c").isSome());
    }

    @Test
    void remove_0() {
        assertThrows(NullPointerException.class, () -> per.remove(null));
        assertFalse(per.remove(cars.spawn(Car::new)).isSome());
    }

    @Test
    void remove() {
        per.set(cars.spawn(Car::new), "a");
        var b = cars.spawn(Car::new);
        per.set(b, "b");
        per.set(cars.spawn(Car::new), "c");
        assertEquals("b", per.remove(b).get());
    }

    @Test
    void update_0() {
        assertThrows(NullPointerException.class,
            () -> per.update(null, e -> e));
    }

    @Test
    void update() {
        var a = cars.spawn(Car::new);
        per.set(a, "a");
        per.update(a, v -> "a2");
        assertEquals("a2", per.get(a).get());
    }

    @Test
    void clear_0() {
        assertDoesNotThrow(per::clear);
        assertEquals(0, per.size());
    }

    @Test
    void clear() {
        per.set(cars.spawn(Car::new), "a");
        per.set(cars.spawn(Car::new), "b");
        per.set(cars.spawn(Car::new), "c");
        assertEquals(3, per.size());
        per.clear();
        assertEquals(0, per.size());
        per.forEach(e -> fail("Should be empty"));
    }
}
