package se.jbee.game.scs.screen;

import static se.jbee.game.any.state.Change.copy;
import static se.jbee.game.any.state.Change.set;

import java.awt.event.KeyEvent;

import se.jbee.game.any.gfx.Dimension;
import se.jbee.game.any.gfx.Stage;
import se.jbee.game.any.screen.Screen;
import se.jbee.game.any.screen.ScreenNo;
import se.jbee.game.any.state.Entity;
import se.jbee.game.any.state.State;
import se.jbee.game.scs.state.GameComponent;

@ScreenNo(GameScreen.SCREEN_SETUP_CONTROLLS)
public class GlobalControls implements Screen, GameComponent {

	@Override
	public void show(State game, Dimension screen, Stage stage) {
		stage.onGlobalKey(KeyEvent.VK_ESCAPE,
				copy(State.ROOT, RETURN_SCREEN, SCREEN),
				set(State.ROOT, SCREEN, GameScreen.SCREEN_MAIN));

		// TODO below just for test purposes
		stage.onGlobalKey(KeyEvent.VK_I,
				copy(State.ROOT, RETURN_SCREEN, SCREEN),
				set(State.ROOT, SCREEN, GameScreen.SCREEN_ICON_INFO));
		
		Entity gamE = game.root();
		gamE.set(SCREEN, gamE.num(RETURN_SCREEN));
		gamE.unset(RETURN_SCREEN);
	}

}
