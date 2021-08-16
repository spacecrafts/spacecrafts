package test.integration.engine;

import org.junit.jupiter.api.Test;
import se.jbee.spacecrafts.sim.engine.Q;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static test.integration.utils.Assertions.assertForEach;

class TestArrayQ {

    private final Q<Integer> q = Q.newDefault(3);

    @Test
    void size_0() {
        assertEquals(0, q.size());
    }

    @Test
    void size() {
        q.append(1);
        assertEquals(1, q.size());
    }

    @Test
    void get_0() {
        assertThrows(IndexOutOfBoundsException.class, () -> q.get(0));
    }

    @Test
    void get() {
        q.append(1);
        assertEquals(1, q.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> q.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> q.get(-1));
    }

    @Test
    void append_0() {
        assertThrows(NullPointerException.class, () -> q.append(null));
        q.seal();
        assertThrows(IllegalStateException.class, () -> q.append(13));
    }

    @Test
    void append() {
        assertEquals(0, q.append(1));
        assertEquals(1, q.append(2));
        assertForEach(asList(1, 2), q::forEach);
    }

    @Test
    void append_grow() {
        q.append(1);
        q.append(2);
        q.append(3);
        q.append(4);
        assertForEach(asList(1, 2, 3, 4), q::forEach);
    }

    @Test
    void concat_0() {
        assertThrows(NullPointerException.class, () -> q.concat(12, null));
        q.seal();
        assertThrows(IllegalStateException.class, () -> q.concat(12));
    }

    @Test
    void concat() {
        q.append(1);
        q.concat(2, 3, 4, 5);
        assertEquals(5, q.size());
        assertForEach(asList(1, 2, 3, 4, 5), q::forEach);
    }

    @Test
    void concat_0Q() {
        q.seal();
        Q<Integer> tail = Q.newDefault(0);
        assertThrows(IllegalStateException.class, () -> q.concat(tail));
    }

    @Test
    void concat_Q() {
        var tail = Q.<Integer>newDefault(3).concat(3, 4, 5);
        q.concat(1).concat(2);
        q.concat(tail);
        assertEquals(5, q.size());
        assertForEach(asList(1, 2, 3, 4, 5), q::forEach);
    }

    @Test
    void firstIndex_0() {
        assertEquals(-1, q.firstIndex(2));
    }

    @Test
    void firstIndex() {
        q.append(4);
        q.append(5);
        assertEquals(0, q.firstIndex(4));
        assertEquals(1, q.firstIndex(5));
    }

    @Test
    void firstIndex_test() {
        q.append(4);
        q.append(5);
        assertEquals(1, q.firstIndex(e -> e > 4));
    }

    @Test
    void seal_0() {
        assertDoesNotThrow(q::seal);
    }

    @Test
    void seal() {
        q.append(1);
        q.seal();
        assertThrows(IllegalStateException.class, q::seal);
        assertThrows(IllegalStateException.class, () -> q.append(42));
    }

    @Test
    void isSealed_0() {
        assertFalse(q.isSealed());
    }

    @Test
    void isSealed() {
        q.seal();
        assertTrue(q.isSealed());
    }

    @Test
    void forEach_0() {
        q.forEach(e -> fail("Should not be called for empty"));
    }

    @Test
    void forEach() {
        q.append(1);
        q.append(2);
        List<Integer> actuals = new ArrayList<>();
        q.forEach(actuals::add);
        assertEquals(asList(1, 2), actuals);
    }

    @Test
    void first_0() {
        assertFalse(q.first(e -> e > 2).isSome());
    }

    @Test
    void first() {
        q.append(2);
        q.append(4);
        var first = q.first(e -> e == 4);
        assertTrue(first.isSome());
        assertEquals(4, first.get());
    }
}
