package se.jbee.game.any.state;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static se.jbee.game.any.state.Entity.codePoints;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import se.jbee.game.scs.state.GameComponent;

public class TestState {

	@Test
	public void newlyDefinedEntitiesGetAnUniqueID() {
		State s = State.base();
		Entity e = s.defComponent(GameComponent.GAME).put(Component.NAME, codePoints("GAME"));

		assertEquals(s.size()-1, e.num(Component.ID));
		assertEquals(0, e.num(Component.TYPE));
		assertSame(e, s.entity(s.size()-1));

		Entity e2 = s.defEntity(GameComponent.GAME);
		assertNotSame(e, e2);
		assertNotEquals(e.num(Component.ID), e2.num(Component.ID));
		assertEquals(GameComponent.GAME, e2.num(Component.TYPE));
		assertSame(e, s.component(GameComponent.GAME));
	}

	@Test
	public void freshGameHasBasicComponents() {
		State g = State.base();
		assertEquals(5, g.size());
		assertEquals("e0000 COMP (2=0,1=0,4=0,3=[*4],0=[*5])\ne0001 TYPE (2=1,1=0,4=1,3=[*4])\ne0002 ID (2=2,1=0,4=2,3=[*2])\ne0003 NAME (2=3,1=0,4=3,3=[*4])\ne0004 CODE (2=4,1=0,4=4,3=[*4])\n", g.toString());
	}

	@Test
	public void saveLoadRoundtripOfAnEmptyGame() throws IOException {
		State g = State.base();

		File f = File.createTempFile("saveLoadRoundtrip", ".game");
		g.save(f);

		State g2 = State.load(f);

		assertEquals(g.size(), g2.size());
		assertEquals(g.toString(), g2.toString());
	}

	@Test
	public void saveExtractEntityFromFile() throws IOException {
		State g = State.base();

		final int someType = 10; // just something we try to extract from a save file again.

		g.defComponent(someType);
		Entity e = g.defEntity(someType);
		e.put(Component.NAME, codePoints("foo"));

		File f = File.createTempFile("saveExtractEntity", ".game");
		g.save(f);

		Entity e2 = State.load(f, someType);

		assertEquals(e.num(Component.ID), e2.num(Component.ID));
		assertEquals("foo", e2.text(Component.NAME));
	}

	@Test
	public void allSelector() {
		State g = State.base();

		assertArrayEquals(new int[] {0,1,2,3,4}, g.all(Component.COMP));
		assertArrayEquals(new int[0], g.all(42));
	}

}
