package se.jbee.game.scs.screen;

import static se.jbee.game.common.state.Entity.codePoints;
import static se.jbee.game.scs.gfx.Objects.background;
import static se.jbee.game.scs.gfx.Objects.text;
import static se.jbee.game.scs.screen.View.dotDiameter;
import se.jbee.game.common.gfx.Dimension;
import se.jbee.game.common.gfx.Stage;
import se.jbee.game.common.screen.Screen;
import se.jbee.game.common.screen.ScreenNo;
import se.jbee.game.common.state.State;
import se.jbee.game.scs.gfx.Gfx;

@ScreenNo(GameScreen.SCREEN_LOADING_GAME)
public class LoadingGame implements Screen, Gfx {

	private static final int[] LOADING = codePoints("LOADING...");

	@Override
	public void show(State user, State game, Dimension screen, Stage stage) {
		stage.enter(background(0, 0, screen.width, screen.height, BG_BLACK));
		
		int dotDia = dotDiameter(screen);
		int x0 = (screen.width-(dotDia*LOADING.length*5-1))/2;
		int y0 = (screen.height-(dotDia*5)) /2;
		stage.enter(text(x0, y0, FONT_DOTS, dotDia, COLOR_TEXT_HIGHLIGHT, 1));
		stage.enter(LOADING);
		
		stage.disableInputs();
	}

}
