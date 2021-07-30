package se.jbee.game.scs;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import se.jbee.game.any.state.State;
import se.jbee.game.scs.state.GameComponent;

public class TestGame {

	@Test
	public void gameInitialisation() {
		State game = State.base().defComponents(GameComponent.class);
		
		assertTrue(game.size() > 4);
		System.out.println(game);
	}
}
