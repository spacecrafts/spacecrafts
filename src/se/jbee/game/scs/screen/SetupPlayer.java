package se.jbee.game.scs.screen;

import se.jbee.game.common.gfx.Dimension;
import se.jbee.game.common.gfx.Stage;
import se.jbee.game.common.screen.Screen;
import se.jbee.game.common.screen.ScreenNo;
import se.jbee.game.common.state.State;

/**
 * Is used in turn zero to setup a human players properties.
 * 
 * Already in turn zero the active human player switches between the player
 * created. Each of them use their turn to chose their race abilities.
 */
@ScreenNo(GameScreen.SCREEN_SETUP_PLAYER)
public class SetupPlayer implements Screen {

	@Override
	public void show(State user, State game, Dimension screen, Stage stage) {
		// TODO Auto-generated method stub
		
	}

}
