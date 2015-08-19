package se.jbee.game.scs.screen;

import static se.jbee.game.common.state.Entity.codePoints;
import static se.jbee.game.scs.gfx.Objects.background;
import static se.jbee.game.scs.gfx.Objects.border;
import static se.jbee.game.scs.gfx.Objects.text;
import static se.jbee.game.scs.screen.View.dotDiameter;
import se.jbee.game.common.gfx.Dimension;
import se.jbee.game.common.gfx.Stage;
import se.jbee.game.common.screen.Screen;
import se.jbee.game.common.screen.ScreenNo;
import se.jbee.game.common.state.Entity;
import se.jbee.game.common.state.State;
import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.state.GameComponent;

@ScreenNo(GameScreen.SCREEN_SAVING_GAME)
public class SavingGame implements Screen, GameComponent, Gfx {

	@Override
	public void show(State user, State game, Dimension screen, Stage stage) {
		int w = screen.width/2;
		int h = screen.height/2;
		int x0 = screen.width/4;
		int y0 = screen.height/4;
		
		Entity gamE = game.single(GAME);

		stage.enter(background(x0-1, y0-1, w+3, h+3, BG_BLACK));
		stage.enter(border(x0, y0, w, h));
		stage.enter(text(x0+20, y0+h/2, FONT_DOTS, dotDiameter(screen), COLOR_TEXT_HIGHLIGHT, 1));
		stage.enter(codePoints("SAVING..."));
		
		// setup return to screen, done after action
		gamE.put(SCREEN, gamE.num(RETURN_SCREEN));
		gamE.erase(RETURN_SCREEN);
		gamE.put(ACTION, ACTION_SAVE);

		stage.disableInputs();
	}

}
