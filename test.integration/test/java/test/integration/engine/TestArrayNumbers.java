package test.integration.engine;

import org.junit.jupiter.api.Test;
import se.jbee.spacecrafts.sim.Any.Defined;
import se.jbee.spacecrafts.sim.Resourcing.Property;
import se.jbee.spacecrafts.sim.engine.Numbers;
import se.jbee.spacecrafts.sim.engine.Range;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    static Property newProperty(int serial) {
        return new Property(new Defined(serial, null, "prop" + ('a' + serial)),
                serial,
                null);
    }
}
