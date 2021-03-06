package se.jbee.game.scs.screen;

import static se.jbee.game.any.gfx.Texts.textKey;
import static se.jbee.game.scs.gfx.Draw.background;
import static se.jbee.game.scs.gfx.Draw.text;
import static se.jbee.game.scs.gfx.Gfx.FontStyle.DOTS;
import static se.jbee.game.scs.screen.Viewport.dotDiameter;
import se.jbee.game.any.gfx.Dimension;
import se.jbee.game.any.gfx.Stage;
import se.jbee.game.any.screen.Screen;
import se.jbee.game.any.screen.ScreenNo;
import se.jbee.game.any.state.Entity;
import se.jbee.game.any.state.State;
import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.gfx.Hue;
import se.jbee.game.scs.state.GameComponent;

/**
 * The loading is in progress... 
 */
@ScreenNo(GameScreen.SCREEN_LOADING_GAME)
public class LoadingGame implements Screen, Gfx, GameComponent {

	@Override
	public void show(State game, Dimension screen, Stage stage) {
		stage.disableInputs();
		stage.atFront(background(0, 0, screen.width, screen.height, BG_BLACK, 0L));
		stage.atFront(text(textKey('G', 'i', 'l'), 0, 0, DOTS, dotDiameter(screen), Hue.TEXT_HIGHLIGHT, Align.EYE, screen.width, screen.height));
		
		Entity gamE = game.root();
		gamE.set(ACTIONS, ACTION_STOP_AI, ACTION_AUTOSAVE, ACTION_LOAD, ACTION_RUN_AI);		
	}

}
