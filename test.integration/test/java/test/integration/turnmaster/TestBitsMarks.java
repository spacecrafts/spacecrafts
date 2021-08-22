package test.integration.turnmaster;

import org.junit.jupiter.api.Test;
import se.jbee.turnmaster.Any.Defined;
import se.jbee.turnmaster.Any.Indicator;
import se.jbee.turnmaster.data.Marks;
import se.jbee.turnmaster.data.Range;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static test.integration.utils.Assertions.assertForEach;

class TestBitsMarks {

    private final Indicator a, b, c;

    private final Range<Indicator> indicators;

    {
        indicators = Range.newDefault(Indicator.class, 3);
        a = indicators.spawn(TestBitsMarks::newIndicator);
        b = indicators.spawn(TestBitsMarks::newIndicator);
        c = indicators.spawn(TestBitsMarks::newIndicator);
    }

    private final Marks marks = Marks.newDefault(indicators);

    @Test
    void has_0() {
        assertFalse(marks.has(a));
    }

    @Test
    void has() {
        marks.set(a, true);
        assertTrue(marks.has(a));
    }

    @Test
    void set_0() {
        marks.set(a, false);
        assertFalse(marks.has(a));
    }

    @Test
    void set() {
        marks.set(b, true);
        assertTrue(marks.has(b));
    }

    @Test
    void clear_0() {
        assertDoesNotThrow(marks::clear);
    }

    @Test
    void clear() {
        marks.set(a, true);
        marks.clear();
        marks.forEach(e -> fail("Should not be called for cleared"));
    }

    @Test
    void forEach_0() {
        marks.forEach(e -> fail("Should not be called for empty"));
    }

    @Test
    void forEach() {
        marks.set(a);
        marks.set(c);
        assertForEach(Arrays.asList(a, c), marks::forEach);
    }

    @Test
    void zeros() {
        Marks zeros = Marks.newDefault(indicators);
        zeros.set(b);
        marks.zero(zeros);
        assertTrue(marks.has(b));
    }

    @Test
    void size_0() {
        assertEquals(0, marks.size());
    }

    @Test
    void size() {
        marks.set(a);
        marks.set(c);
        assertEquals(2, marks.size());
    }

    @Test
    void first_0() {
        assertFalse(marks.first(e -> true).isSome());
    }

    @Test
    void first() {
        marks.set(c);
        var first = marks.first(e -> !e.hidden());
        assertTrue(first.isSome());
        assertEquals(c, first.get());
    }

    private static Indicator newIndicator(int serial) {
        return new Indicator(new Defined(serial, null, "indi" + serial), serial,
            false);
    }
}
