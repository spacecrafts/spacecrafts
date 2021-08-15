package test.integration.engine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import se.jbee.spacecrafts.sim.engine.Collection;
import se.jbee.spacecrafts.sim.engine.Q;
import se.jbee.spacecrafts.sim.engine.Top;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static test.integration.utils.Assertions.assertForEach;

class TestArrayTop {

    private final Top<String> top = Top.newDefault(3, 6);

    @Test
    void size_0() {
        assertEquals(0, top.size());
    }

    @Test
    void size() {
        top.pushBottom("a");
        assertEquals(1, top.size());
        top.pushBottom("b");
        assertEquals(2, top.size());
        top.popTop();
        assertEquals(1, top.size());
    }

    @Test
    void forEach_0() {
        top.forEach(e -> fail("Should not be called for empty"));
    }

    @Test
    void forEach() {
        top.pushBottom("a", "b");
        assertForEach(asList("a", "b"), top::forEach);
    }

    @Test
    void first_0() {
        assertFalse(top.first(e -> true).isSome());
    }

    @Test
    void first() {
        top.pushBottom("a", "b", "c", "d");
        var first = top.first(e -> e.charAt(0) == 'c');
        assertTrue(first.isSome());
        assertEquals("c", first.get());
    }

    @Test
    void moveToTop_0() {
        assertThrows(IndexOutOfBoundsException.class, () -> top.moveToTop(0));
    }

    @Test
    void moveToTop() {
        top.pushBottom("a", "b", "c", "d");
        top.moveToTop(2);
        assertForEach(asList("c", "a", "b", "d"), top::forEach);
    }

    @Test
    void moveToTop_second() {
        top.pushBottom("a", "b", "c", "d");
        top.moveToTop(1);
        assertForEach(asList("b", "a", "c", "d"), top::forEach);
    }

    @Test
    void moveToTop_0range() {
        top.pushBottom("a", "b");
        assertThrows(IllegalArgumentException.class, () -> top.moveToTop(1, 0));
    }

    @Test
    void moveToTop_range() {
        top.pushBottom("a", "b", "c", "d", "e", "f");
        top.moveToTop(2, 3);
        assertEquals(6, top.size());
        assertForEach(asList("c", "d", "a", "b", "e", "f"), top::forEach);
    }

    @Test
    void moveToBottom_0() {
        assertThrows(IndexOutOfBoundsException.class,
                () -> top.moveToBottom(0));
    }

    @Test
    void moveToBottom() {
        top.pushBottom("a", "b", "c", "d");
        top.moveToBottom(1);
        assertForEach(asList("a", "c", "d", "b"), top::forEach);
    }

    @Test
    void moveToBottom_0range() {
        top.pushBottom("a", "b");
        assertThrows(IllegalArgumentException.class,
                () -> top.moveToBottom(1, 0));
    }

    @Test
    void moveToBottom_range() {
        top.pushBottom("a", "b", "c", "d", "e", "f");
        top.moveToBottom(2, 3);
        assertEquals(6, top.size());
        assertForEach(asList("a", "b", "e", "f", "c", "d"), top::forEach);
    }

    @Test
    void moveToBottom_secondLast() {
        top.pushBottom("a", "b", "c", "d");
        top.moveToBottom(2);
        assertForEach(asList("a", "b", "d", "c"), top::forEach);
    }

    @Test
    void moveUp_0() {
        assertThrows(IndexOutOfBoundsException.class, () -> top.moveUp(0));
    }

    @Test
    void moveUp() {
        top.pushBottom("a", "b", "c", "d");
        top.moveUp(2);
        assertForEach(asList("a", "c", "b", "d"), top::forEach);
    }

    @Test
    void moveUp_0range() {
        top.pushBottom("a", "b");
        assertThrows(IllegalArgumentException.class, () -> top.moveUp(1, 0));
    }

    @Test
    void moveUp_range() {
        top.pushBottom("a", "b", "c", "d", "e", "f");
        top.moveUp(2, 4);
        assertEquals(6, top.size());
        assertForEach(asList("a", "c", "d", "e", "b", "f"), top::forEach);
    }

    @Test
    void moveDown_0() {
        assertThrows(IndexOutOfBoundsException.class, () -> top.moveDown(0));
    }

    @Test
    void moveDown() {
        top.pushBottom("a", "b", "c", "d");
        top.moveDown(1);
        assertForEach(asList("a", "c", "b", "d"), top::forEach);
    }

    @Test
    void moveDown_0range() {
        top.pushBottom("a", "b");
        assertThrows(IllegalArgumentException.class, () -> top.moveDown(1, 0));
    }

    @Test
    void moveDown_range() {
        top.pushBottom("a", "b", "c", "d", "e", "f");
        top.moveDown(2, 4);
        assertEquals(6, top.size());
        assertForEach(asList("a", "b", "f", "c", "d", "e"), top::forEach);
    }

    @Test
    void pushTop_0() {
        assertThrows(NullPointerException.class,
                () -> top.pushTop((String) null));
    }

    @Test
    void pushTop() {
        top.pushTop("a");
        assertEquals(1, top.size());
        top.pushTop("b");
        assertEquals(2, top.size());
        assertForEach(asList("b", "a"), top::forEach);
    }

    @Test
    void pushTop_grow() {
        top.pushTop("a");
        top.pushTop("b");
        top.pushTop("c");
        top.pushTop("d");
        top.pushTop("e");
        top.pushTop("f");
        top.pushTop("g");
        assertEquals(6, top.size());
        assertForEach(asList("g", "f", "e", "d", "c", "b"), top::forEach);
    }

    @Test
    void pushBottom_0() {
        assertThrows(NullPointerException.class,
                () -> top.pushBottom((String) null));
    }

    @Test
    void pushBottom() {
        top.pushBottom("a");
        assertEquals(1, top.size());
        top.pushBottom("b");
        assertEquals(2, top.size());
        assertForEach(asList("a", "b"), top::forEach);
    }

    @Test
    void pushBottom_full() {
        top.pushBottom("a", "b", "c", "d", "e", "f");
        top.pushBottom("g");
        top.pushBottom("h");
        assertEquals(6, top.size());
        assertForEach(asList("a", "b", "c", "d", "e", "h"), top::forEach);
    }

    @Test
    void peek_0() {
        assertThrows(IndexOutOfBoundsException.class, () -> top.peek(0));
    }

    @Test
    void remove_0() {
        assertThrows(IndexOutOfBoundsException.class, () -> top.remove(0));
    }

    @Test
    void remove() {
        top.pushBottom("a", "b", "c", "d");
        top.remove(2);
        assertEquals(3, top.size());
        assertForEach(asList("a", "b", "d"), top::forEach);
    }

    @Test
    void remove_0range() {
        top.pushTop("a", "b");
        assertThrows(IllegalArgumentException.class, () -> top.remove(1, 0));
    }

    @Test
    void remove_range() {
        top.pushBottom("a", "b", "c", "d");
        top.remove(1, 2);
        assertEquals(2, top.size());
        assertForEach(asList("a", "d"), top::forEach);
    }

    @Test
    void remove_endRange() {
        top.pushBottom("a", "b", "c", "d");
        top.remove(2, 3);
        assertEquals(2, top.size());
        assertForEach(asList("a", "b"), top::forEach);
    }

    @Test
    void popTop_0() {
        assertThrows(IndexOutOfBoundsException.class, top::popTop);
    }

    @Test
    void popTop() {
        top.pushBottom("a", "b", "c", "d");
        assertEquals("a", top.popTop());
        assertForEach(asList("b", "c", "d"), top::forEach);
        assertEquals(3, top.size());
    }

    @Test
    void popBottom_0() {
        assertThrows(IndexOutOfBoundsException.class, top::popBottom);
    }

    @Test
    void popBottom() {
        top.pushBottom("a", "b", "c", "d");
        assertEquals("d", top.popBottom());
        assertForEach(asList("a", "b", "c"), top::forEach);
        assertEquals(3, top.size());

    }

    @Test
    void peekTop_0() {
        assertThrows(IndexOutOfBoundsException.class, top::peekTop);
    }

    @Test
    void peekTop() {
        top.pushBottom("a", "b", "c", "d");
        assertEquals("a", top.peekTop());
        assertForEach(asList("a", "b", "c", "d"), top::forEach);
        assertEquals(4, top.size());
    }

    @Test
    void peekBottom_0() {
        assertThrows(IndexOutOfBoundsException.class, top::peekBottom);
    }

    @Test
    void peekBottom() {
        top.pushBottom("a", "b", "c", "d");
        assertEquals("d", top.peekBottom());
        assertForEach(asList("a", "b", "c", "d"), top::forEach);
        assertEquals(4, top.size());
    }

    @Test
    void capacity_0() {
        assertEquals(6, top.capacity());
    }

    @Test
    void capacity() {
        top.pushBottom("a", "b", "c");
        assertEquals(6, top.capacity());
    }

    @Test
    void pushTop_0array() {
        assertDoesNotThrow((Executable) top::pushTop);
        assertThrows(NullPointerException.class,
                () -> top.pushTop((String[]) null));
    }

    @Test
    void pushTop_array() {
        top.pushTop("a", "b");
        top.pushTop("c", "d");
        top.pushTop("e", "f");
        assertEquals(6, top.size());
        assertForEach(asList("e", "f", "c", "d", "a", "b"), top::forEach);
    }

    @Test
    void pushTop_overflow() {
        top.pushTop("a", "b");
        top.pushTop("c", "d");
        top.pushTop("e", "f", "g");
        assertEquals(6, top.size());
        assertForEach(asList("e", "f", "g", "c", "d", "a"), top::forEach);
    }

    @Test
    void pushBottom_0array() {
        assertDoesNotThrow((Executable) top::pushBottom);
        assertThrows(NullPointerException.class,
                () -> top.pushBottom((String[]) null));
    }

    @Test
    void pushBottom_array() {
        top.pushBottom("a", "b");
        top.pushBottom("c", "d");
        top.pushBottom("e", "f");
        assertEquals(6, top.size());
        assertForEach(asList("a", "b", "c", "d", "e", "f"), top::forEach);
    }

    @Test
    void pushBottom_0collection() {
        Q<String> tail = Q.newDefault(2);
        assertDoesNotThrow(() -> top.pushBottom(tail));
        assertThrows(NullPointerException.class,
                () -> top.pushBottom((Collection<String>) null));
    }

    @Test
    void pushBottom_collectionQ() {
        Q<String> cd = Q.newDefault(2);
        cd.append("c");
        cd.append("d");
        top.pushBottom("a", "b");
        top.pushBottom(cd);
        top.pushBottom("e", "f");
        assertEquals(6, top.size());
        assertForEach(asList("a", "b", "c", "d", "e", "f"), top::forEach);
    }

    @Test
    void pushBottom_collectionTop() {
        Top<String> cd = Top.newDefault(2, 2);
        cd.pushBottom("c", "d");
        top.pushBottom("a", "b");
        top.pushBottom(cd);
        top.pushBottom("e", "f");
        assertEquals(6, top.size());
        assertForEach(asList("a", "b", "c", "d", "e", "f"), top::forEach);
    }

    @Test
    void pushBottom_overflow() {
        top.pushBottom("a", "b", "c", "d");
        top.pushBottom("e", "f", "g");
        assertEquals(6, top.size());
        assertForEach(asList("a", "b", "c", "e", "f", "g"), top::forEach);
    }

    @Test
    void pushBottom_overflowQ() {
        top.pushBottom("a", "b", "c", "d");
        Q<String> efg = Q.newDefault(2);
        efg.append("e");
        efg.append("f");
        efg.append("g");
        top.pushBottom(efg);
        assertEquals(6, top.size());
        assertForEach(asList("a", "b", "c", "e", "f", "g"), top::forEach);
    }

    @Test
    void slice_0() {
        top.pushBottom("a", "b", "c", "d", "e", "f");
        assertThrows(IllegalArgumentException.class, () -> top.slice(4, 3));
    }

    @Test
    void slice() {
        top.pushBottom("a", "b", "c", "d", "e", "f");
        var slice = top.slice(2, 4);
        assertEquals(3, slice.size());
        assertForEach(asList("c", "d", "e"), slice::forEach);
    }
}
