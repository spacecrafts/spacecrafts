package se.jbee.game.scs.screen;

import static java.awt.event.KeyEvent.VK_ESCAPE;
import static java.lang.Math.min;
import static se.jbee.game.scs.gfx.Objects.background;
import static se.jbee.game.scs.gfx.Objects.icon;
import static se.jbee.game.scs.gfx.Objects.text;
import static se.jbee.game.scs.gfx.Objects.timeLine;
import static se.jbee.game.uni.state.Change.put;
import static se.jbee.game.uni.state.Entity.codePoints;

import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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
		final int gID = gamE.id();
		stage.onKey(VK_ESCAPE, put(gID, SCREEN, SCREEN_MAIN));

		stage.inFront(background(0, 0, screen.width, screen.height, BG_BLACK));


		int gap = 20;
		int x0 = gap;
		int y0 = 50;
		int w = (screen.width - (4*gap)) / 3;
		int h = (screen.height - (4*gap)) / 3;

		int gameHeight = 30;
		int nameWidth = 150;
		int r = 4;
		int d = r*2+1;
		int page = gamE.num(PAGE);
		int pageSize = screen.height/gameHeight;

		List<File[]> gameFiles = gameFiles(user);
		gameFiles = gameFiles.subList(min(gameFiles.size()-1, page*pageSize), min(gameFiles.size(), (page+1)*pageSize + 1));
		int y = y0;
		for (File[] saves : gameFiles) {
			stage.inFront(timeLine(x0+nameWidth, y0+r, x0+w, y0+r, 1, COLOR_TEXT_NORMAL));
			stage.inFront(text(1, x0, y, FONT_REGULAR, 14, COLOR_TEXT_NORMAL, ALIGN_E, x0+nameWidth-10, y+d));
			stage.inFront(codePoints(saves[0].getParentFile().getName().replace('_', ' ')));
			for (File save : saves) {
				int turn = Integer.parseInt(save.getName().substring(0, save.getName().indexOf('.')));
				int x = x0 + nameWidth + turn * d;
				stage.inFront(icon(ICON_BUILDING, x, y, d, COLOR_FARM));
				stage.onLeftClickIn(new Rectangle(x, y, d, d),
					put(gID, SAVEGAME, codePoints(save.getParentFile().getName()+"/"+save.getName() )),
					put(gID, SCREEN, SCREEN_LOADING_GAME),
					put(gID, ACTION, ACTION_LOAD));
			}
			y+=gameHeight;
		}
	}

	private List<File[]> gameFiles(State user) {
		String dir = user.single(USER).text(SAVEGAME_DIR);
		File[] games = new File(dir).listFiles();
		if (games == null) {
			return Collections.emptyList();
		}
		List<File[]> gameFilesByDate = new ArrayList<>();
		for (File g : games) {
			if (g.isDirectory()) {
				File[] files = g.listFiles();
				Arrays.sort(files, new Comparator<File>() {

					@Override
					public int compare(File a, File b) {
						return a.lastModified() > b.lastModified() ? -1 : 1 ;
					}
				});
				gameFilesByDate.add(files);
			}
		}
		gameFilesByDate.sort(new Comparator<File[]>() {

			@Override
			public int compare(File[] a, File[] b) {
				long at = a[a.length-1].lastModified();
				long bt = b[b.length-1].lastModified();
				return at < bt ? -1 : 1;
			}

		});
		return gameFilesByDate;
	}

}
