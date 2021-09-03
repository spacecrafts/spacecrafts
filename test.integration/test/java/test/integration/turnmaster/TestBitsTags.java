package test.integration.turnmaster;

import org.junit.jupiter.api.Test;
import se.jbee.turnmaster.data.Any.Defined;
import se.jbee.turnmaster.data.Any.Indicator;
import se.jbee.turnmaster.data.Range;
import se.jbee.turnmaster.data.Tags;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static test.integration.utils.Assertions.assertForEach;

class TestBitsTags {

    private final Indicator a, b, c;

    private final Range<Indicator> indicators;

    {
        indicators = Range.newDefault(Indicator.class, 3);
        a = indicators.spawn(TestBitsTags::newIndicator);
        b = indicators.spawn(TestBitsTags::newIndicator);
        c = indicators.spawn(TestBitsTags::newIndicator);
    }

    private final Tags tags = Tags.newDefault(indicators);

    @Test
    void has_0() {
        assertFalse(tags.has(a));
    }

    @Test
    void has() {
        tags.set(a, true);
        assertTrue(tags.has(a));
    }

    @Test
    void set_0() {
        tags.set(a, false);
        assertFalse(tags.has(a));
    }

    @Test
    void set() {
        tags.set(b, true);
        assertTrue(tags.has(b));
    }

    @Test
    void clear_0() {
        assertDoesNotThrow(tags::clear);
    }

    @Test
    void clear() {
        tags.set(a, true);
        tags.clear();
        tags.forEach(e -> fail("Should not be called for cleared"));
    }

    @Test
    void forEach_0() {
        tags.forEach(e -> fail("Should not be called for empty"));
    }

    @Test
    void forEach() {
        tags.set(a);
        tags.set(c);
        assertForEach(Arrays.asList(a, c), tags::forEach);
    }

    @Test
    void zeros() {
        Tags zeros = Tags.newDefault(indicators);
        zeros.set(b);
        tags.zero(zeros);
        assertTrue(tags.has(b));
    }

    @Test
    void size_0() {
        assertEquals(0, tags.size());
    }

    @Test
    void size() {
        tags.set(a);
        tags.set(c);
        assertEquals(2, tags.size());
    }

    @Test
    void first_0() {
        assertFalse(tags.first(e -> true).isSome());
    }

    @Test
    void first() {
        tags.set(c);
        var first = tags.first(e -> !e.hidden());
        assertTrue(first.isSome());
        assertEquals(c, first.get());
    }

    private static Indicator newIndicator(int serial) {
        return new Indicator(new Defined(serial, null, "indi" + serial), false);
    }
}
