package data;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Test;

import se.jbee.game.any.state.Entity;
import se.jbee.game.any.state.State;
import se.jbee.game.scs.state.GameComponent;

public class TestData {

	@Test
	public void loadsDataFilesWithTypeHeaders() throws IOException, URISyntaxException {
		State s = State.base();
		s.defComponent(42).name("A");
		s.defComponent(43).name("B");
		s.defComponent(44).name("C");
		s.defComponent(45).name("D");

		Data.load(Data.class, "test.data", s);

		assertEquals(15, s.size());
		int[] es = s.all(42);
		assertEquals(6, es.length);

		Entity e1 = s.entity(es[0]);
		assertEquals(1, e1.num(43));
		assertEquals('a', e1.num(44));
		assertArrayEquals(new int[] {1, 2}, e1.list(45));

		Entity e2 = s.entity(es[1]);
		assertEquals(2, e2.num(43));
		assertEquals(3, e2.num(45));
		assertArrayEquals(new int[] {'b', 'c'}, e2.list(44));

		Entity e3 = s.entity(es[2]);
		assertEquals(4, e3.size());
		assertEquals(3, e3.num(43));
		assertArrayEquals(new int[] {4, 555, 66}, e3.list(45));

		Entity e4 = s.entity(es[3]);
		assertEquals('a', e4.num(44));

		Entity e5 = s.entity(es[4]);
		assertEquals(5, e5.num(43));
		assertEquals(4, e5.list(45).length);
		assertEquals(1, e5.get(45, 'a'));
		assertEquals(2, e5.get(45, 'b'));
		assertEquals(255, e5.num(44));
		
		Entity e6 = s.entity(es[5]);
		assertEquals(6, e6.num(43));
		assertEquals(0x1A, e6.num(44));
		assertEquals(34, e6.num(45));
	}

	@Test
	public void loadsDataFilesWithNameHeaders() throws IOException, URISyntaxException {
		State s = State.base().defComponents(GameComponent.class);
		Data.load(Data.class, "tech.data", s);

		int[] techs = s.all(GameComponent.TECHNOLOGY);

		assertEquals(2, techs.length);

		Entity t1 = s.entity(techs[0]);
		assertEquals(3, t1.num(GameComponent.COMPONENTS));
		assertEquals(1, t1.num(GameComponent.LEVEL));
		assertEquals(1, t1.num(GameComponent.BRANCH));
		assertEquals(1, t1.num(GameComponent.POSITION));

	}
}
