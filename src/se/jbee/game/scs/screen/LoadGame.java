package se.jbee.game.scs.screen;

import static java.awt.event.KeyEvent.VK_ESCAPE;
import static se.jbee.game.scs.gfx.Objects.background;
import static se.jbee.game.scs.gfx.Objects.border;
import static se.jbee.game.scs.gfx.Objects.focusBox;
import static se.jbee.game.scs.gfx.Objects.text;
import static se.jbee.game.uni.state.Change.put;
import static se.jbee.game.uni.state.Entity.codePoints;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.scs.state.UserComponent;
import se.jbee.game.uni.gfx.Dimension;
import se.jbee.game.uni.gfx.Stage;
import se.jbee.game.uni.screen.Screen;
import se.jbee.game.uni.screen.ScreenNo;
import se.jbee.game.uni.state.Entity;
import se.jbee.game.uni.state.State;

/**
 * Shows the games to load each with a in-game time-line left to right
 * and the real-time time-line (of last use) top to bottom.
 * <pre>
 * a ------o--------o----------o---------o-o--o
 * c -----o----o----o---------o-o
 * d --------o
 * b -----------o-----o--------o-------o
 * t ----o----o-----o-----o
 * </pre>
 * The time-line start with the name of the game.
 * Auto-saves are shown in different color. 
 */
@ScreenNo(GameScreen.SCREEN_LOAD_GAME)
public class LoadGame implements Screen, UserComponent, GameComponent, Gfx, GameScreen {

	@Override
	public void show(State user, State game, Dimension screen, Stage stage) {
		Entity gamE = game.single(GAME);

		// cancel (ESC override, to not set return screen)
		stage.onKey(VK_ESCAPE, put(gamE.id(), SCREEN, SCREEN_MAIN));

		stage.inFront(background(0, 0, screen.width, screen.height, BG_BLACK));


		int gap = 20;
		int x0 = gap;
		int y0 = gap;
		int w = (screen.width - (4*gap)) / 3;
		int h = (screen.height - (4*gap)) / 3;

		int n = 0;
		for (File savegame : new File[0]) {
			try {
				Entity savegamE = State.load(savegame, GAME);
				stage.inFront(text(1, x0+w*2/3, y0+h, FONT_LIGHT, h/2, COLOR_TEXT_NORMAL));
				stage.inFront(codePoints(String.valueOf(savegamE.num(TURN))));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			stage.inFront(border(x0, y0, w, h));
			stage.inFront(text(1, x0+gap, y0+h, FONT_REGULAR, h/6, COLOR_TEXT_NORMAL));
			int[] name = codePoints(savegame.getName());
			stage.inFront(name);
			Rectangle area = new Rectangle(x0, y0, w, h);
			stage.in(area, focusBox(x0, y0, w, h));
			stage.onLeftClickIn(area,
					put(gamE.id(), SAVEGAME, name),
					put(gamE.id(), SCREEN, SCREEN_LOADING_GAME),
					put(gamE.id(), ACTION, ACTION_LOAD));
			x0 += w + gap;
			n++;
			if (n == 3) {
				x0 = gap;
				y0 += gap+h;
				n = 0;
			}
		}
	}

	private File[][] gameFiles(State user, State game, int pageSize) {
		String dir = user.single(USER).text(SAVEGAME_DIR);
		File[] files = new File(dir).listFiles();
		if (files == null) {
			return new File[0][];
		}
		File[][] gameFiles = new File[pageSize][]; 
		for (File f : files) {
			if (f.getName().endsWith(".game")) {
			}
		}
		int page = game.single(GAME).num(PAGE);
		
		return gameFiles;
	}

}
