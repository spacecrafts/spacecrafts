package se.jbee.game.scs.logic;

import java.io.File;
import java.io.IOException;

import se.jbee.game.any.logic.Logic;
import se.jbee.game.any.logic.Transition;
import se.jbee.game.any.state.Entity;
import se.jbee.game.any.state.State;
import se.jbee.game.scs.state.GameComponent;

public class Load implements Transition, GameComponent {

	@Override
	public State transit(State game, Logic logic) throws IOException {
		Entity gamE = game.single(GAME);
		game = State.loadFrom(new File(gamE.string(SAVEGAME_DIR), gamE.string(SAVEGAME)));
		// also done for loaded game so that one can be sure that the current code has all the components.
		game.defComponents(GameComponent.class); 
		return game;
	}
	
}
