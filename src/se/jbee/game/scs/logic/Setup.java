package se.jbee.game.scs.logic;

import static se.jbee.game.scs.screen.GameScreen.SCREEN_SETUP_PLAYER;
import se.jbee.game.any.logic.Transition;
import se.jbee.game.any.state.Entity;
import se.jbee.game.any.state.State;
import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.scs.state.PlayerStatus;

/**
 * Creating a game... 
 */
public class Setup implements Transition, GameComponent {

	@Override
	public void transit(State user, State game) {
		Entity gamE = game.single(GAME);
		int[] setup = gamE.list(SETUP);
		for (int i = 1; i < setup[SETUP_NUMBER_OF_PLAYERS]; i++) {
			Entity player = game.defEntity(PLAYER);
			player.set(NO, i+1);
			player.set(TURN, -1);
			gamE.append(PLAYERS, player.id());
		}
		for (int i = 0; i < setup[SETUP_NUMBER_OF_AIS]; i++) {
			Entity ai = game.defEntity(PLAYER);
			ai.setBits(STATUS, PlayerStatus.AI);
			ai.set(TURN, 0);
			gamE.append(PLAYERS, ai.id());
		}
		gamE.set(SCREEN, SCREEN_SETUP_PLAYER);
	}

}
