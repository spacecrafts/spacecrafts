package se.jbee.game.uni.state;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static se.jbee.game.uni.state.Entity.codePoints;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.uni.state.Component;
import se.jbee.game.uni.state.Data;
import se.jbee.game.uni.state.Entity;
import se.jbee.game.uni.state.State;

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
		assertEquals(4, g.size());
		assertEquals("e0000 COMPONENT (2=0,1=0,3=[*9],0=[*4])\ne0001 TYPE (2=1,1=0,3=[*4])\ne0002 ID (2=2,1=0,3=[*2])\ne0003 NAME (2=3,1=0,3=[*4])\n", g.toString());
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
		
		assertArrayEquals(new int[] {0,1,2,3}, g.all(Component.COMPONENT));
		assertArrayEquals(new int[0], g.all(42));
	}
	
	@Test
	public void loadDataFile() throws IOException {
		State s = State.base();
		s.defComponent(42);
		s.defComponent(43);
		s.defComponent(44);
		s.defComponent(45);
		
		Data.load(new File("data/test.data"), s);
		
		assertEquals(11, s.size());
		int[] es = s.all(42);
		assertEquals(3, es.length);
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
	}
}
