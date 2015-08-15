package se.jbee.game.scs.screen;

import static se.jbee.game.common.state.Entity.codePoints;
import static se.jbee.game.scs.gfx.Objects.background;
import static se.jbee.game.scs.gfx.Objects.border;
import static se.jbee.game.scs.gfx.Objects.focusBox;
import static se.jbee.game.scs.gfx.Objects.text;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import se.jbee.game.common.process.Stage;
import se.jbee.game.common.screen.Screen;
import se.jbee.game.common.screen.ScreenNo;
import se.jbee.game.common.state.Change;
import se.jbee.game.common.state.Change.Op;
import se.jbee.game.common.state.Entity;
import se.jbee.game.common.state.State;
import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.scs.state.UserComponent;

@ScreenNo(GameScreen.SCREEN_LOAD_GAME)
public class LoadGame implements Screen, UserComponent, GameComponent, Gfx {

	@Override
	public void show(State user, State game, Dimension screen, Stage stage) {
		Entity gamE = game.single(GAME);

		// cancel (ESC override, to not set return screen)
		stage.onKey((char)27, new Change(gamE.id(), SCREEN, Op.PUT, GameScreen.SCREEN_MAIN));
		
		stage.enter(background(0, 0, screen.width, screen.height, BG_BLACK));
		

		int gap = 20;
		int x0 = gap;
		int y0 = gap;
		int w = (screen.width - (4*gap)) / 3;
		int h = (screen.height - (4*gap)) / 3;
		
		int n = 0;
		for (File savegame : pageFiles(user, game)) {
			try {
				Entity savegamE = State.load(savegame, GAME);
				stage.enter(text(x0+w*2/3, y0+h, FONT_LIGHT, h/2, COLOR_TEXT_NORMAL, 1));
				stage.enter(codePoints(String.valueOf(savegamE.num(TURN))));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			stage.enter(border(x0, y0, w, h));
			stage.enter(text(x0+gap, y0+h, FONT_REGULAR, h/6, COLOR_TEXT_NORMAL, 1));
			int[] name = codePoints(savegame.getName());
			stage.enter(name);
			Rectangle area = new Rectangle(x0, y0, w, h);
			stage.in(area, focusBox(x0, y0, w, h));
			stage.onLeftClickIn(area,
					new Change(gamE.id(), SAVEGAME, Op.PUT, name),
					new Change(gamE.id(), SCREEN, Op.PUT, gamE.num(RETURN_SCREEN)), 
					new Change(gamE.id(), ACTION, Op.PUT, ACTION_LOAD));
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
		String dir = user.single(USER).text(SAVEGAME_DIR);
		File[] files = new File(dir).listFiles();
		int offset = 8 * game.single(GAME).num(PAGE);
		List<File> page = new ArrayList<File>();
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
