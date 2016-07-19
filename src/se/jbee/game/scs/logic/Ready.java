package se.jbee.game.scs.logic;

import se.jbee.game.any.logic.Transition;
import se.jbee.game.any.state.Entity;
import se.jbee.game.any.state.State;
import se.jbee.game.scs.screen.GameScreen;
import se.jbee.game.scs.state.GameComponent;

/**
 * The second most important game transition after {@link Turn}.
 * 
 * The player has indicated that he is ready with the current entity 
 * (and wants to be guided to the next one needing input).
 */
public class Ready implements Transition, GameComponent, GameScreen {

	@Override
	public void transit(State user, State game) {
		if (game.single(GAME).num(TURN) == 0) {
			selectPlayerToSetup(game);
		} else {
			// TODO
			// this is far more complicated - finding the active player, finding the active plan, finding the active entity within the plan
			// a entity becomes active when it finished building something, or in case of a star-ship when it arrived somewhere
			// in general when a process attached to the entity has ended (completed or canceled)
			Entity gamE = game.single(GAME);
			// the galaxy as seen by player 1
			gamE.set(SCREEN, SCREEN_GALAXY).set(BASE_ENTITY, gamE.num(GALAXIES));
		}
	}

	private static void selectPlayerToSetup(State game) {
		Entity gamE = game.single(GAME);
		int[] players = gamE.list(PLAYERS);
		for (int i = 0; i < players.length; i++) {
			Entity player = game.entity(players[i]);
			if (player.has(NO) && player.num(TURN) < 0) {
				gamE.set(SCREEN, SCREEN_SETUP_PLAYER);
				return;
			}
		}
		// now all players are setup, the game process will forward from turn 0 to turn 1, until then inputs are look by the following screen
		gamE.set(SCREEN, SCREEN_ENCOUNTER);
	}

}
