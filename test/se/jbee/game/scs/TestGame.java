package se.jbee.game.scs;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import se.jbee.game.scs.process.Game;
import se.jbee.game.state.State;

public class TestGame {

	@Test
	public void gameInitialisation() {
		State game = State.base();
		Game.initComponents(game);
		
		assertTrue(game.size() > 4);
		System.out.println(game);
	}
}
