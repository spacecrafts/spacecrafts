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
import java.util.ArrayList;
import java.util.List;

import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.scs.state.UserComponent;
import se.jbee.game.uni.gfx.Dimension;
import se.jbee.game.uni.gfx.Stage;
import se.jbee.game.uni.screen.Screen;
import se.jbee.game.uni.screen.ScreenNo;
import se.jbee.game.uni.state.Entity;
import se.jbee.game.uni.state.State;

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
		for (File savegame : pageFiles(user, game)) {
			try {
				Entity savegamE = State.load(savegame, GAME);
				stage.inFront(text(x0+w*2/3, y0+h, FONT_LIGHT, h/2, COLOR_TEXT_NORMAL, 1));
				stage.inFront(codePoints(String.valueOf(savegamE.num(TURN))));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			stage.inFront(border(x0, y0, w, h));
			stage.inFront(text(x0+gap, y0+h, FONT_REGULAR, h/6, COLOR_TEXT_NORMAL, 1));
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

	private List<File> pageFiles(State user, State game) {
		List<File> page = new ArrayList<File>();
		String dir = user.single(USER).text(SAVEGAME_DIR);
		File[] files = new File(dir).listFiles();
		if (files == null) {
			return page;
		}
		int offset = 8 * game.single(GAME).num(PAGE);
		int i = 0;
		while (page.size() < 8 && i < files.length) {
			if (files[i].getName().endsWith(".game")) {
				if (offset > 0) {
					offset--;
				} else {
					page.add(files[i]);
				}
			}
			i++;
		}
		return page;
	}

}
