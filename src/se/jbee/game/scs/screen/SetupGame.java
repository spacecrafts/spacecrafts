package se.jbee.game.scs.screen;

import se.jbee.game.common.gfx.Dimension;
import se.jbee.game.common.gfx.Stage;
import se.jbee.game.common.screen.Screen;
import se.jbee.game.common.screen.ScreenNo;
import se.jbee.game.common.state.State;

/**
 * Used in turn zero to setup overall game properties.
 */
@ScreenNo(GameScreen.SCREEN_SETUP_GAME)
public class SetupGame implements Screen {

	@Override
	public void show(State user, State game, Dimension screen, Stage stage) {
		// TODO Auto-generated method stub
		
		// # players
		// # AI
		// # stars in a cluster
		// # clusters in galaxy
		// (things about events, independent parties, etc.)
		
		// new entities should only be created by the game
		// especially as entities cannot be removed the player should first be created at the end of the zero'th turn.
		
	}

}
