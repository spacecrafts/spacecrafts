package se.jbee.game.scs.screen;

import static se.jbee.game.any.state.Entity.codePoints;
import static se.jbee.game.scs.gfx.Objects.background;
import static se.jbee.game.scs.gfx.Objects.text;
import static se.jbee.game.scs.screen.Viewport.dotDiameter;
import se.jbee.game.any.gfx.Dimension;
import se.jbee.game.any.gfx.Stage;
import se.jbee.game.any.screen.Screen;
import se.jbee.game.any.screen.ScreenNo;
import se.jbee.game.any.state.State;
import se.jbee.game.scs.gfx.Gfx;

@ScreenNo(GameScreen.SCREEN_LOADING_GAME)
public class LoadingGame implements Screen, Gfx {

	private static final int[] LOADING = codePoints("LOADING...");

	@Override
	public void show(State user, State game, Dimension screen, Stage stage) {
		stage.inFront(background(0, 0, screen.width, screen.height, BG_BLACK));
		
		int dotDia = dotDiameter(screen);
		int x0 = (screen.width-(dotDia*LOADING.length*5-1))/2;
		int y0 = (screen.height-(dotDia*5)) /2;
		stage.inFront(text(1, x0, y0, FONT_DOTS, dotDia, COLOR_TEXT_HIGHLIGHT));
		stage.inFront(LOADING);
		
		stage.disableInputs();
	}

}
