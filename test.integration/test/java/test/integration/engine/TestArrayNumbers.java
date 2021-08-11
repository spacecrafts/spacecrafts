package test.integration.engine;

import org.junit.jupiter.api.Test;
import se.jbee.spacecrafts.sim.Any.Defined;
import se.jbee.spacecrafts.sim.Resourcing.Property;
import se.jbee.spacecrafts.sim.engine.Numbers;
import se.jbee.spacecrafts.sim.engine.Range;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

class TestArrayNumbers {

    private final Property a, b, c;

    private final Range<Property> properties;

    {
        properties = Range.newDefault(Property.class, 3);
        a = properties.add(TestArrayNumbers::newProperty);
        b = properties.add(TestArrayNumbers::newProperty);
        c = properties.add(TestArrayNumbers::newProperty);
    }

    private final Numbers numbers = Numbers.newDefault(properties);

    @Test
    void get_0() {
        numbers.clear();
        assertThrows(NoSuchElementException.class, () -> numbers.get(a));
    }

    @Test
    void get() {
        assertEquals(0, numbers.get(a));
        numbers.set(b, 2);
        assertEquals(2, numbers.get(b));
    }

    @Test
    void add() {
        numbers.clear();
        numbers.add(b, 3);
        assertEquals(3, numbers.get(b));
        numbers.add(b, -1);
        assertEquals(2, numbers.get(b));
    }

    @Test
    void forEach_0() {
        numbers.clear();
        numbers.forEach(((key, value) -> fail("Should not be called for empty")));
    }

    @Test
    void forEach() {
        Map<Property, Integer> actuals = new LinkedHashMap<>();
        numbers.forEach(actuals::put);
        assertEquals(asList(a, b, c), new ArrayList<>(actuals.keySet()));
        assertEquals(asList(0, 0, 0), new ArrayList<>(actuals.values()));
    }

    @Test
    void zero() {
        Numbers zeros = Numbers.newDefault(properties);
        zeros.set(b, 2);
        numbers.set(a, 3);
        numbers.set(b, 1);
        numbers.set(c, 2);
        numbers.zero(zeros);
        assertEquals(2, numbers.get(b));
        assertEquals(0, numbers.get(a));
    }

    @Test
    void cap() {
        Numbers cap = Numbers.newDefault(properties);
        cap.clear();
        cap.set(b, 2);
        cap.set(c, 5);
        numbers.set(a, 3);
        numbers.set(b, 4);
        numbers.set(c, 1);
        numbers.cap(cap);
        assertEquals(2, numbers.get(b));
        assertEquals(1, numbers.get(c));
    }

    @Test
    void sub() {
        Numbers subtracted = Numbers.newDefault(properties);
        subtracted.clear();
        subtracted.set(b, 1);

        numbers.set(a, 3);
        numbers.set(b, 5);
        numbers.sub(subtracted);
        assertEquals(3, numbers.get(a));
        assertEquals(4, numbers.get(b));
    }

    @Test
    void addNumbers() {
        Numbers added = Numbers.newDefault(properties);
        added.clear();
        added.set(c, 1);

        numbers.set(a, 3);
        numbers.set(c, 4);
        numbers.add(added);
        assertEquals(3, numbers.get(a));
        assertEquals(5, numbers.get(c));
    }

    @Test
    void size_0() {
        numbers.clear();
        assertEquals(0, numbers.size());
    }

    @Test
    void size() {
        assertEquals(3, numbers.size());
    }

    @Test
    void first_0() {
        assertFalse(numbers.first(value -> value.value() > 0).isSome());
    }

    @Test
    void first() {
        numbers.set(b, 2);
        var first = numbers.first(v -> v.value() > 0);
        assertTrue(first.isSome());
        assertEquals(b, first.get().key());
        assertEquals(2, first.get().value());
    }

    static Property newProperty(int serial) {
        return new Property(new Defined(serial, null, "prop" + ('a' + serial)),
                serial,
                null);
    }
}
