package se.jbee.game.scs.screen;

import static se.jbee.game.any.gfx.Texts.textKey;
import static se.jbee.game.scs.gfx.GfxObjs.background;
import static se.jbee.game.scs.gfx.GfxObjs.flextext;
import static se.jbee.game.scs.screen.Viewport.dotDiameter;
import se.jbee.game.any.gfx.Dimension;
import se.jbee.game.any.gfx.Stage;
import se.jbee.game.any.screen.Screen;
import se.jbee.game.any.screen.ScreenNo;
import se.jbee.game.any.state.State;
import se.jbee.game.scs.gfx.Gfx;

@ScreenNo(GameScreen.SCREEN_LOADING_GAME)
public class LoadingGame implements Screen, Gfx {

	@Override
	public void show(State user, State game, Dimension screen, Stage stage) {
		stage.atFront(background(0, 0, screen.width, screen.height, BG_BLACK));
		stage.atFront(flextext(textKey('G', 'i', 'l'), 0, 0, FONT_DOTS, dotDiameter(screen), COLOR_TEXT_HIGHLIGHT, ALIGN_EYE, screen.width, screen.height));
		stage.disableInputs();
	}

}
