package se.jbee.game.scs.logic;

import static se.jbee.game.any.state.Entity.codePoints;
import se.jbee.game.any.logic.Logic;
import se.jbee.game.any.logic.Transition;
import se.jbee.game.any.state.Entity;
import se.jbee.game.any.state.State;
import se.jbee.game.scs.state.GameComponent;

/**
 * What happens when a game should be "auto-saved".
 */
public class Autosave implements Transition, GameComponent {

	@Override
	public State transit(State game, Logic logic) {
		Entity gamE = game.root();
		if (gamE.num(TURN) > 1) {
			gamE.set(SAVEGAME, codePoints(gamePath(gamE))); 
			return logic.run(Save.class, game);
		}
		return null;
	}

	public static String gamePath(Entity gamE) {
		return Save.gamePath(gamE)+".auto";
	}

}
