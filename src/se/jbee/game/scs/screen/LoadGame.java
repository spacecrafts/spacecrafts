package se.jbee.game.scs.screen;

import java.awt.Dimension;

import se.jbee.game.common.process.Stage;
import se.jbee.game.common.screen.Screen;
import se.jbee.game.common.screen.ScreenNo;
import se.jbee.game.common.state.State;
import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.scs.state.UserComponent;

@ScreenNo(GameScreen.SCREEN_LOAD_GAME)
public class LoadGame implements Screen, UserComponent, GameComponent {

	@Override
	public void show(State user, State game, Dimension screen, Stage stage) {
		
		String dir = user.single(USER).text(SAVEGAME_DIR);
		
		
	}

}
