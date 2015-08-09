package se.jbee.game.state;

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
}
