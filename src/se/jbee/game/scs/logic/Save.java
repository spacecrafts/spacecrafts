package se.jbee.game.scs.logic;

import java.io.File;
import java.io.IOException;

import se.jbee.game.common.logic.Progress;
import se.jbee.game.common.state.Entity;
import se.jbee.game.common.state.State;
import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.scs.state.UserComponent;

public class Save implements Progress, GameComponent, UserComponent {

	@Override
	public void progress(State user, State game) {
		Entity gamE = game.single(GAME);
		File file = new File(user.single(USER).text(SAVEGAME_DIR), gamE.text(SAVEGAME)+".game");
		gamE.erase(ACTION);
		gamE.erase(SAVEGAME);
		try {
			game.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
