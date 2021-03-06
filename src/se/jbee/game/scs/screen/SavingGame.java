package se.jbee.game.scs.screen;

import static se.jbee.game.any.gfx.Texts.textKey;
import static se.jbee.game.any.state.Entity.codePoints;
import static se.jbee.game.scs.gfx.Draw.background;
import static se.jbee.game.scs.gfx.Draw.border;
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
import se.jbee.game.scs.logic.Save;
import se.jbee.game.scs.state.GameComponent;

@ScreenNo(GameScreen.SCREEN_SAVING_GAME)
public class SavingGame implements Screen, GameComponent, Gfx {

	@Override
	public void show(State game, Dimension screen, Stage stage) {
		int w = screen.width/2;
		int h = screen.height/2;
		int x0 = screen.width/4;
		int y0 = screen.height/4;
		
		stage.disableInputs();
		stage.atFront(background(x0-1, y0-1, w+3, h+3, BG_BLACK, 0L));
		stage.atFront(border(x0, y0, w, h));
		stage.atFront(text(textKey('G', 'i', 's'), x0+20, y0+h/2, DOTS, dotDiameter(screen), Hue.TEXT_HIGHLIGHT));
		
		Entity gamE = game.root();
		gamE.set(ACTIONS, ACTION_STOP_AI, ACTION_SAVE, ACTION_RUN_AI);
		if (!gamE.has(SAVEGAME)) {
			gamE.set(SAVEGAME, codePoints(Save.gamePath(gamE)));
		}
	}

}
