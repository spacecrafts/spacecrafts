package se.jbee.game.scs.screen;


import se.jbee.game.common.gfx.Dimension;
import se.jbee.game.common.gfx.Stage;
import se.jbee.game.common.screen.Screen;
import se.jbee.game.common.screen.ScreenNo;
import se.jbee.game.common.state.State;
import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.state.GameComponent;

/**
 * Does a minimal overlay in the region of the name renamed. In any case
 * (ok=enter; cancel=esc) it goes back to the
 * {@link GameComponent#RETURN_SCREEN}. The information on the value to rename
 * are passed via {@link GameComponent#RENAME}.
 */
@ScreenNo(GameScreen.SCREEN_RENAME)
public class Rename implements Screen, GameComponent, Gfx {

	@Override
	public void show(State user, State game, Dimension screen, Stage stage) {
		// TODO Auto-generated method stub
		
	}

}
