package se.jbee.game.scs.logic;

import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.uni.logic.Progress;
import se.jbee.game.uni.state.Entity;
import se.jbee.game.uni.state.State;

/**
 * The most important game transition.
 * 
 * When all players have reached same turn as the game the game forwards to next
 * turn by accumulating costs, earnings and building progress.
 */
public class Turn implements Progress, GameComponent {

	@Override
	public void progress(State user, State game) {
		Entity gamE = game.single(GAME);
		
	}

}
