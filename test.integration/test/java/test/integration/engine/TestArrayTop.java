package test.integration.engine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import se.jbee.spacecrafts.sim.engine.Top;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static test.integration.utils.Assertions.assertForEach;

class TestArrayTop {

    private final Top<String> top = Top.newDefault(5, 10);

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
        assertEquals(10, top.capacity());
    }

    @Test
    void capacity() {
        top.pushBottom("a", "b", "c");
        assertEquals(10, top.capacity());
    }

    @Test
    void pushTop_0array() {
        assertDoesNotThrow((Executable) top::pushTop);
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
    void pushBottom_0array() {
        assertDoesNotThrow((Executable) top::pushBottom);
    }

    @Test
    void pushBottom_array() {
        top.pushBottom("a", "b");
        top.pushBottom("c", "d");
        top.pushBottom("e", "f");
        assertEquals(6, top.size());
        assertForEach(asList("a", "b", "c", "d", "e", "f"), top::forEach);
    }

}
