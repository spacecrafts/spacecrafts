package se.jbee.game.scs.screen;

import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.uni.gfx.Dimension;
import se.jbee.game.uni.gfx.Stage;
import se.jbee.game.uni.screen.Screen;
import se.jbee.game.uni.screen.ScreenNo;
import se.jbee.game.uni.state.State;

/**
 * Used as {@link GameComponent#SCREEN} when an {@link GameComponent#ACTION} has
 * been set that will determine the actual target screen based on game logic.
 * 
 * While this screen is blank it will not change the monitor screen as it appears
 * since it simply does not paint something over the content of the current
 * canvas.
 */
@ScreenNo(GameScreen.SCREEN_BLANK)
public class Blank implements Screen, GameComponent, Gfx {

	@Override
	public void show(State user, State game, Dimension screen, Stage stage) {
		stage.disableInputs();
	}

}
