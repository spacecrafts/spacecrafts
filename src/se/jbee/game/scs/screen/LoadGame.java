package se.jbee.game.scs.screen;

import static java.awt.event.KeyEvent.VK_ESCAPE;
import static java.lang.Math.min;
import static se.jbee.game.any.state.Change.set;
import static se.jbee.game.any.state.Entity.codePoints;
import static se.jbee.game.scs.gfx.Draw.background;
import static se.jbee.game.scs.gfx.Draw.fixtext;
import static se.jbee.game.scs.gfx.Draw.icon;
import static se.jbee.game.scs.gfx.Draw.timeLine;
import static se.jbee.game.scs.gfx.Gfx.FontStyle.REGULAR;

import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import se.jbee.game.any.gfx.Dimension;
import se.jbee.game.any.gfx.Stage;
import se.jbee.game.any.screen.Screen;
import se.jbee.game.any.screen.ScreenNo;
import se.jbee.game.any.state.Change;
import se.jbee.game.any.state.Entity;
import se.jbee.game.any.state.State;
import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.gfx.Hue;
import se.jbee.game.scs.state.GameComponent;

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
public class LoadGame implements Screen, GameComponent, Gfx, GameScreen {

	@Override
	public void show(State game, Dimension screen, Stage stage) {
		//TODO add a "cursor" and keyboard support + paging

		Entity gamE = game.root();

		// cancel (ESC override, to not set return screen)
		final int gID = gamE.id();
		stage.onKey(VK_ESCAPE, set(gID, SCREEN, SCREEN_MENU));

		stage.atFront(background(0, 0, screen.width, screen.height, BG_BLACK, 0L));

		Rectangle center = Viewport.centerView(screen);
		int x0 = center.x;
		int y0 = center.y;

		int gameHeight = 30;
		int nameWidth = 150;
		int lineWidth = center.width-nameWidth;
		int maxTurn = 1000;
		int r = 4;
		int d = r*2+1;
		int page = gamE.num(PAGE);
		int pageSize = center.height/gameHeight;

		List<File[]> gameFiles = gameFiles(gamE);
		if (gameFiles.isEmpty())
			return;
		gameFiles = gameFiles.subList(min(gameFiles.size()-1, page*pageSize), min(gameFiles.size(), (page+1)*pageSize + 1));
		int y = y0;
		Change screenCs = set(gID, SCREEN, SCREEN_LOADING_GAME);
		for (File[] saves : gameFiles) {
			stage.atFront(fixtext(x0, y, REGULAR, 14, Hue.TEXT_NORMAL, Align.E, x0+nameWidth-5, y+d, codePoints(saves[0].getParentFile().getName().replace('_', ' '))));
			String highestTrunSave = saves[saves.length-1].getName();
			int highestTurn = Integer.parseInt(highestTrunSave.substring(0, highestTrunSave.indexOf('.')));
			stage.atFront(timeLine(x0+nameWidth, y0+r, x0+nameWidth+highestTurn*lineWidth/maxTurn, y0+r));
			int xLast = 0;
			for (int i = 0; i < saves.length; i++) {
				File save = saves[i];
				int turn = Integer.parseInt(save.getName().substring(0, save.getName().indexOf('.')));
				int x = x0 + nameWidth + turn * lineWidth/maxTurn;
				if (x - d < xLast) {
					x = xLast+d;
				}
				xLast = x;
				Hue color = save.getName().contains(".auto.") ? Hue.TEXT_NORMAL : Hue.FARM;
				stage.atFront(icon(ICON_BUILDING, x, y, d, color));
				Rectangle area = new Rectangle(x, y, d, d);
				stage.in(area, icon(ICON_BUILDING, x-2, y-2, d+4, Hue.TEXT_HIGHLIGHT), fixtext(x, y-20, REGULAR, 14, Hue.TEXT_HIGHLIGHT, codePoints(String.valueOf(turn))));
				stage.onLeftClickIn(area,
					set(gID, SAVEGAME, codePoints(save.getParentFile().getName()+"/"+save.getName() )),
					screenCs);
			}
			y+=gameHeight;
		}
	}

	private List<File[]> gameFiles(Entity gamE) {
		String dir = gamE.string(SAVEGAME_DIR);
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
						int an = a.getName().indexOf('.');
						int bn = b.getName().indexOf('.');
						if (an != bn)
							return an < bn ? -1 : 1;
						int res = a.getName().substring(0, an).compareTo(b.getName().substring(0, bn));
						if (res != 0)
							return res;
						return Long.compare(a.lastModified(), b.lastModified()) ;
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
