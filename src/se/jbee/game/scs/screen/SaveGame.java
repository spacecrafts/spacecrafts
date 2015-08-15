package se.jbee.game.scs.screen;

import static java.util.Arrays.copyOf;
import static se.jbee.game.common.state.Entity.codePoints;
import static se.jbee.game.scs.gfx.Objects.background;
import static se.jbee.game.scs.gfx.Objects.border;
import static se.jbee.game.scs.gfx.Objects.text;

import java.awt.Dimension;

import se.jbee.game.common.process.Stage;
import se.jbee.game.common.screen.Screen;
import se.jbee.game.common.screen.ScreenNo;
import se.jbee.game.common.state.Change;
import se.jbee.game.common.state.Change.Op;
import se.jbee.game.common.state.Entity;
import se.jbee.game.common.state.State;
import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.state.GameComponent;

@ScreenNo(GameScreen.SCREEN_SAVE_GAME)
public class SaveGame implements Screen, GameComponent, Gfx, GameScreen {

	@Override
	public void show(State user, State game, Dimension screen, Stage stage) {
		Entity gamE = game.single(GAME);

		// cancel (ESC override, to not set return screen)
		stage.onKey((char)27, new Change(gamE.id(), SCREEN, Op.PUT, GameScreen.SCREEN_MAIN));

		int w = screen.width/2;
		int h = screen.height/2;
		int x0 = screen.width/4;
		int y0 = screen.height/4;
		
		if (gamE.has(RETURN_SCREEN)) {
			stage.enter(background(x0-1, y0-1, w+3, h+3, BG_BLACK));
		} else {
			stage.enter(background(0, 0, screen.width, screen.height, BG_BLACK));
		}
		
		stage.enter(border(x0, y0, w, h));
		stage.enter(border(x0+20, y0+20, w*2/3-40, h*2/3-40));
	
		stage.enter(text(x0+(w*2/3), y0+h, FONT_REGULAR, h/3, COLOR_TEXT_NORMAL, 1));
		stage.enter(codePoints(String.valueOf(gamE.num(TURN))));
		
		int inputSize = h/7;
		int[] savegame = gamE.list(SAVEGAME);
		stage.enter(text(x0+20, y0+h-20, FONT_LIGHT, inputSize, COLOR_TEXT_NORMAL, 1));
		stage.enter(codePoints(">"));
		stage.enter(text(x0+20+50, y0+h-20, FONT_LIGHT, inputSize, COLOR_TEXT_HIGHLIGHT, 1));
		stage.enter(savegame);
		
		// keyboard input
		int gid = gamE.id();
		if (savegame.length < 12) {
			for (char c = 'a'; c <= 'z'; c++) {
				stage.onKey(c, new Change(gid, SAVEGAME, Op.APPEND, c));
			}
		}
		if (savegame.length > 0) {
			stage.onKey('\b', new Change(gid, SAVEGAME, Op.PUT, copyOf(savegame, savegame.length-1) ));
			stage.onKey('\n', new Change(gid, SCREEN, Op.PUT, SCREEN_SAVING_GAME));
		}

	}

}
