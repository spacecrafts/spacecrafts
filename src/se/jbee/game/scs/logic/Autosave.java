package se.jbee.game.scs.logic;

import static se.jbee.game.any.state.Entity.codePoints;
import static se.jbee.game.scs.process.Game.autosavegamePath;
import se.jbee.game.any.logic.Transition;
import se.jbee.game.any.state.Entity;
import se.jbee.game.any.state.State;
import se.jbee.game.scs.state.GameComponent;

/**
 * What happens on when a game should be "auto-saved".
 */
public class Autosave implements Transition, GameComponent {

	public static final Transition INSTANCE = new Autosave();

	@Override
	public void transit(State user, State game) {
		Entity gamE = game.single(GAME);
		if (gamE.num(TURN) > 1) {
			gamE.set(SAVEGAME, codePoints(autosavegamePath(gamE))); Save.INSTANCE.transit(user, game);
		}
	}

}
