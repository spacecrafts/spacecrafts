package se.jbee.game.any.state;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestEntity {

	@Test
	public void frseshEntityHasIdTypeName() {
		Entity e = new Entity(1, -1);

		assertEquals(1, e.num(Component.ID));
		assertEquals(2, e.size());
		assertEquals("e0001 (2=1,1=1)", e.toString());
	}

	@Test
	public void longNumber() {
		Entity e = new Entity(1, -1);

		long val = System.currentTimeMillis();
		e.set(5, val);

		assertEquals(val, e.longNum(5));
	}

	@Test
	public void componentCanHaveMapValues() {
		Entity e = new Entity(1, -1);
		e.put(5, 2, 3);
		e.put(5, 3, 4);

		assertEquals(3, e.lookup(5, 2, 0));
		assertEquals(4, e.lookup(5, 3, 0));
		assertEquals(0, e.lookup(5, 8, 0));

		e.unput(5, 2);
		assertEquals(0, e.lookup(5, 2, 0));
		assertEquals(4, e.lookup(5, 3, 0));

		e.unput(5, 8); // key 8 is not set
		assertEquals(0, e.lookup(5, 2, 0));
		assertEquals(4, e.lookup(5, 3, 0));

		e.unput(5, 3);
		assertEquals(0, e.lookup(5, 2, 0));
		assertEquals(0, e.lookup(5, 3, 0));
	}
}
