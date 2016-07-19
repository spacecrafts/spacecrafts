package se.jbee.game.scs.logic;

import java.io.File;
import java.io.IOException;

import se.jbee.game.any.logic.Transition;
import se.jbee.game.any.state.Entity;
import se.jbee.game.any.state.State;
import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.scs.state.UserComponent;

public class Save implements Transition, GameComponent, UserComponent {

	public static final Transition INSTANCE = new Save();
	
	@Override
	public void transit(State user, State game) {
		Entity gamE = game.single(GAME);
		File file = new File(user.single(USER).string(SAVEGAME_DIR), gamE.string(SAVEGAME)+".game");
		gamE.unset(ACTION);
		gamE.unset(SAVEGAME);
		try {
			game.save(file);
		} catch (IOException e) {
			e.printStackTrace();
			// TODO write a error journal that is shown in the error screen
			gamE.set(ACTION, ACTION_ERROR);
		}		
	}

}
