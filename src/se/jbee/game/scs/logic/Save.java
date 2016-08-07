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
	public State transit(State game, Logic logic) throws IOException {
		Entity gamE = game.root();
		File file = new File(gamE.string(SAVEGAME_DIR), gamE.string(SAVEGAME)+".game");
		gamE.unset(SAVEGAME);
		// set return screen as screen so game jumps into last screen when loaded
		gamE.set(SCREEN, gamE.num(RETURN_SCREEN));
		gamE.unset(RETURN_SCREEN);		
		game.saveTo(file);
		return game;
	}

	public static String gameFolder(String name) {
		return name.replace(' ', '_')+"/";
	}

	public static String gamePath(Entity gamE) {
		return gameFolder(gamE.string(NAME))+String.valueOf(gamE.num(TURN));
	}

}
