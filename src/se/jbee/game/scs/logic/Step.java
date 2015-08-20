package se.jbee.game.scs.logic;

import se.jbee.game.scs.screen.GameScreen;
import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.uni.logic.Progress;
import se.jbee.game.uni.state.Entity;
import se.jbee.game.uni.state.State;

/**
 * The second most important game transition after {@link Turn}.
 * 
 * A step is change of plan, plan participant or player.
 */
public class Step implements Progress, GameComponent {

	@Override
	public void progress(State user, State game) {
		Entity gamE = game.single(GAME);
		if (gamE.num(TURN) == 0) {
			int[] players = gamE.list(PLAYERS);
			for (int i = 0; i < players.length; i++) {
				Entity player = game.entity(players[i]);
				if (player.has(NO) && player.num(TURN) < 0) {
					gamE.put(SCREEN_ENTITY, player.id());
					return;
				}
			}
			gamE.put(SCREEN, GameScreen.SCREEN_ENCOUNTER);
		}
		
	}

}
