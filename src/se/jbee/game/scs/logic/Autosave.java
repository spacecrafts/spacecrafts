package se.jbee.game.scs.logic;

import static se.jbee.game.scs.process.Game.autosavegamePath;
import static se.jbee.game.uni.state.Entity.codePoints;

import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.uni.logic.Progress;
import se.jbee.game.uni.state.Entity;
import se.jbee.game.uni.state.State;

/**
 * What happens on when a game should be "auto-saved".
 */
public class Autosave implements Progress, GameComponent {

	public static final Progress INSTANCE = new Autosave();

	@Override
	public void progress(State user, State game) {
		Entity gamE = game.single(GAME);
		if (gamE.num(TURN) > 0) {
			gamE.put(SAVEGAME, codePoints(autosavegamePath(gamE))); Save.INSTANCE.progress(user, game);
		}
	}

}
