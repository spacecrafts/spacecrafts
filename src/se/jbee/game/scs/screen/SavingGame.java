package se.jbee.game.scs.screen;

import static se.jbee.game.scs.gfx.Objects.background;
import static se.jbee.game.scs.gfx.Objects.border;
import static se.jbee.game.scs.gfx.Objects.text;
import static se.jbee.game.scs.screen.Viewport.dotDiameter;
import static se.jbee.game.uni.state.Entity.codePoints;
import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.uni.gfx.Dimension;
import se.jbee.game.uni.gfx.Stage;
import se.jbee.game.uni.screen.Screen;
import se.jbee.game.uni.screen.ScreenNo;
import se.jbee.game.uni.state.Entity;
import se.jbee.game.uni.state.State;

@ScreenNo(GameScreen.SCREEN_SAVING_GAME)
public class SavingGame implements Screen, GameComponent, Gfx {

	@Override
	public void show(State user, State game, Dimension screen, Stage stage) {
		int w = screen.width/2;
		int h = screen.height/2;
		int x0 = screen.width/4;
		int y0 = screen.height/4;
		
		Entity gamE = game.single(GAME);

		stage.inFront(background(x0-1, y0-1, w+3, h+3, BG_BLACK));
		stage.inFront(border(x0, y0, w, h));
		stage.inFront(text(x0+20, y0+h/2, FONT_DOTS, dotDiameter(screen), COLOR_TEXT_HIGHLIGHT, 1));
		stage.inFront(codePoints("SAVING..."));
		
		gamE.put(ACTION, ACTION_SAVE);
		gamE.put(SAVEGAME, codePoints(gamE.text(NAME)+"-"+String.valueOf(gamE.num(TURN))));
		// set return screen as screen so game jumps into last screen before save
		gamE.put(SCREEN, gamE.num(RETURN_SCREEN));
		gamE.erase(RETURN_SCREEN);

		stage.disableInputs();
	}

}
