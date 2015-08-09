package se.jbee.game.state;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static se.jbee.game.state.Entity.codePoints;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import se.jbee.game.scs.state.GameComponent;

public class TestGame {

	@Test
	public void newlyDefinedEntitiesGetAnUniqueID() {
		State s = State.base();
		Entity e = s.defComponent(GameComponent.GAME).set(Component.NAME, codePoints("GAME"));
		
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
		assertEquals("e0000 COMPONENT (2=0,1=0,3=[67, 79, 77, 80, 79, 78, 69, 78, 84],0=[0, 1, 2, 3])\ne0001 TYPE (2=1,1=0,3=[84, 89, 80, 69])\ne0002 ID (2=2,1=0,3=[73, 68])\ne0003 NAME (2=3,1=0,3=[78, 65, 77, 69])\n", g.toString());
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
}
