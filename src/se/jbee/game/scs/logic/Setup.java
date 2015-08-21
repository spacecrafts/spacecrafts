package se.jbee.game.scs.logic;

import static se.jbee.game.scs.screen.GameScreen.SCREEN_SETUP_PLAYER;
import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.scs.state.Status;
import se.jbee.game.uni.logic.Progress;
import se.jbee.game.uni.state.Entity;
import se.jbee.game.uni.state.State;

public class Setup implements Progress, GameComponent {

	@Override
	public void progress(State user, State game) {
		Entity gamE = game.single(GAME);
		int[] setup = gamE.list(SETUP);
		for (int i = 1; i < setup[SETUP_NUMBER_OF_PLAYERS]; i++) {
			Entity player = game.defEntity(PLAYER);
			player.put(NO, i+1);
			player.put(TURN, -1);
			gamE.append(PLAYERS, player.id());
		}
		for (int i = 0; i < setup[SETUP_NUMBER_OF_AIS]; i++) {
			Entity ai = game.defEntity(PLAYER);
			ai.set(STATUS, Status.AI);
			ai.put(TURN, 0);
			gamE.append(PLAYERS, ai.id());
		}
		gamE.put(SCREEN, SCREEN_SETUP_PLAYER);
	}

}
