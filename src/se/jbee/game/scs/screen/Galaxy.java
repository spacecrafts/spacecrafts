package se.jbee.game.scs.screen;

import static se.jbee.game.any.state.Change.set;
import static se.jbee.game.scs.gfx.GfxObjs.background;
import static se.jbee.game.scs.gfx.GfxObjs.fixtext;
import static se.jbee.game.scs.gfx.GfxObjs.path;
import static se.jbee.game.scs.gfx.GfxObjs.star;

import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

import se.jbee.game.any.gfx.Dimension;
import se.jbee.game.any.gfx.Stage;
import se.jbee.game.any.screen.Screen;
import se.jbee.game.any.screen.ScreenNo;
import se.jbee.game.any.state.Entity;
import se.jbee.game.any.state.State;
import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.logic.Turn;
import se.jbee.game.scs.state.GameComponent;

@ScreenNo(GameScreen.SCREEN_GALAXY)
public class Galaxy implements Screen, Gfx, GameComponent, GameScreen {

	@Override
	public void show(State game, Dimension screen, Stage stage) {
		Entity gamE = game.single(GAME);
		Entity player = Turn.currentPlayer(game);
		Entity galaxy = game.entity(gamE.num(BASE_ENTITY));

		stage.atFront(background(0, 0, screen.width, screen.height, BG_SPACE, galaxy.list(SEED)));

		Rectangle view = Viewport.fullView(screen);
		int[] size = galaxy.list(SIZE);
		int sx = size[0];
		int sy = size[1];
		double scale = Math.min(view.width/(double)sx, view.height/(double)sy);
		int xc = view.x + view.width/2;
		int yc = view.y + view.height/2;
		int[] stars = galaxy.list(STARS);
		int playerStar = game.entity(player.num(HOME)).num(STAR);

		for (int starID : stars) {
			Entity star = game.entity(starID);
			int[] xyz = star.list(POSITION);
			int x = xc +(int)((xyz[0]-sx/2)*scale);
			int y = yc +(int)((xyz[1]-sy/2)*scale);
			//TODO scaling must be automatic adapting to star types occuring
			int dia = 1+ (int) (Math.log(star.num(SIZE))*3.2d);
			int r = dia/2-1;
			int touch = (int) (scale * star.num(CLOSEST)/2);
			Ellipse2D area = new Ellipse2D.Float(x+r-touch, y+r-touch, touch+touch, touch+touch);
			if (playerStar == starID || star.has(HOME)) {
				stage.atFront(path(PATH_EDGY, COLOR_TEXT_NORMAL, 1, x+r, y+r, x+r+10, y+r-10));
				stage.atFront(fixtext(x+5,y-20,FONT_THIN, 13, COLOR_TEXT_NORMAL, star.list(NAME)));
				stage.in(area, fixtext(x+5,y-20, FONT_THIN, 13, COLOR_TEXT_HIGHLIGHT, star.list(NAME)));
			}
			stage.onLeftClickIn(area, set(gamE.id(), SCREEN, SCREEN_SOLAR_SYSTEM), set(gamE.id(), BASE_ENTITY, starID));
			stage.atFront(star(x, y, dia, star.num(RGB)));
		}
	}

}
