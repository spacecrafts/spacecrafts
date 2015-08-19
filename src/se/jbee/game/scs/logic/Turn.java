package se.jbee.game.scs.logic;

import se.jbee.game.common.logic.Progress;
import se.jbee.game.common.state.State;

/**
 * The most important game transition.
 * 
 * When all players have reached same turn as the game the game forwards to next
 * turn by accumulating costs, earnings and building progress.
 */
public class Turn implements Progress {

	@Override
	public void progress(State user, State game) {
		// TODO Auto-generated method stub
		
	}

}
