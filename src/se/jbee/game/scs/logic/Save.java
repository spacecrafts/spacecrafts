package se.jbee.game.scs.logic;

import java.io.File;
import java.io.IOException;

import se.jbee.game.any.logic.Logic;
import se.jbee.game.any.logic.Transition;
import se.jbee.game.any.state.Entity;
import se.jbee.game.any.state.State;
import se.jbee.game.scs.state.GameComponent;

/**
 * Stores the game on disk. Expects {@link GameComponent#SAVEGAME_DIR} and
 * {@link GameComponent#SAVEGAME} to be set.
 */
public class Save implements Transition, GameComponent {

	@Override
	public State transit(State game, Logic logic) {
		Entity gamE = game.single(GAME);
		File file = new File(gamE.string(SAVEGAME_DIR), gamE.string(SAVEGAME)+".game");
		gamE.unset(ACTION);
		gamE.unset(SAVEGAME);
		// set return screen as screen so game jumps into last screen when loaded
		gamE.set(SCREEN, gamE.num(RETURN_SCREEN));
		gamE.unset(RETURN_SCREEN);		
		try {
			game.saveTo(file);
		} catch (IOException e) {
			e.printStackTrace();
			// TODO write a error journal that is shown in the error screen
			// or even better: make such try-catches unnecessary and do this in general for all transitions on the outside => Logic has the code to call a transition. 
			gamE.set(ACTION, ACTION_ERROR);
		}
		return game;
	}

	public static String gameFolder(String name) {
		return name.replace(' ', '_')+"/";
	}

	public static String gamePath(Entity gamE) {
		return gameFolder(gamE.string(NAME))+String.valueOf(gamE.num(TURN));
	}

}
