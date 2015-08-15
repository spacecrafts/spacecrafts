package se.jbee.game.scs.screen;

import static se.jbee.game.scs.gfx.Objects.background;

import java.awt.Dimension;

import se.jbee.game.common.process.Stage;
import se.jbee.game.common.screen.Screen;
import se.jbee.game.common.screen.ScreenNo;
import se.jbee.game.common.state.State;
import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.state.GameComponent;

@ScreenNo(GameScreen.SCREEN_GALAXY)
public class Galaxy implements Screen, Gfx, GameComponent {

	@Override
	public void show(State user, State game, Dimension screen, Stage stage) {
		
		stage.enter(background(0, 0, screen.width, screen.height, BG_SPACE));
		
	}

}
