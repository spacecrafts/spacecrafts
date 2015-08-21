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
public class Step implements Progress, GameComponent, GameScreen {

	@Override
	public void progress(State user, State game) {
		if (game.single(GAME).num(TURN) == 0) {
			setupThePlayerNotSetupYet(game);
		} else {
			// TODO
			// this is far more complicated - finding the active player, finding the active plan, finding the active entity within the plan
			// a entity becomes active when it finished building something, or in case of a star-ship when it arrived somewhere
			// in general when a process attached to the entity has ended (completed or canceled)
			Entity gamE = game.single(GAME);
			// the galaxy as seen by player 1
			gamE.put(SCREEN, SCREEN_GALAXY).put(SCREEN_ENTITY, gamE.num(PLAYERS), gamE.num(GALAXIES));
		}
	}

	private static void setupThePlayerNotSetupYet(State game) {
		Entity gamE = game.single(GAME);
		int[] players = gamE.list(PLAYERS);
		for (int i = 0; i < players.length; i++) {
			Entity player = game.entity(players[i]);
			if (player.has(NO) && player.num(TURN) < 0) {
				gamE.put(SCREEN_ENTITY, player.id());
				return;
			}
		}
		// now all players are setup, the game process will forward from turn 0 to turn 1, until then inputs are look by the following screen
		gamE.put(SCREEN, SCREEN_ENCOUNTER);
	}

}
