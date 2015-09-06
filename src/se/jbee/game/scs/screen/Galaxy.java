package se.jbee.game.scs.screen;

import static java.lang.Math.min;
import static se.jbee.game.scs.gfx.Objects.background;
import static se.jbee.game.scs.gfx.Objects.border;
import static se.jbee.game.scs.gfx.Objects.star;
import static se.jbee.game.uni.state.Change.put;

import java.awt.Rectangle;

import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.gfx.Objects;
import se.jbee.game.scs.process.Game;
import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.uni.gfx.Dimension;
import se.jbee.game.uni.gfx.Stage;
import se.jbee.game.uni.screen.Screen;
import se.jbee.game.uni.screen.ScreenNo;
import se.jbee.game.uni.state.Entity;
import se.jbee.game.uni.state.Rnd;
import se.jbee.game.uni.state.State;

@ScreenNo(GameScreen.SCREEN_GALAXY)
public class Galaxy implements Screen, Gfx, GameComponent, GameScreen {

	@Override
	public void show(State user, State game, Dimension screen, Stage stage) {
		
		Entity gamE = game.single(GAME);
		Entity player = Game.currentPlayer(game);
		Entity galaxy = game.entity(gamE.num(SCREEN_ENTITY));

		stage.inFront(background(0, 0, screen.width, screen.height, BG_SPACE));
		
		Rectangle center = View.centerView(screen);
		int wh = min(center.width, center.height);
		int size = galaxy.num(SIZE);
		int x0 = center.x+(center.width-wh)/2;
		int y0 = center.y+(center.height-wh)/2;
		int[] stars = galaxy.list(STARS);
		int playerStar = game.entity(player.num(HOME)).num(STAR);
		for (int starID : stars) {
			Entity star = game.entity(starID);
			long seed = star.longNum(SEED);
			Rnd rnd = new Rnd(seed);
			int[] xyz = star.list(POSITION);
			int x = x0+xyz[0]*wh/size;
			int y = y0+xyz[1]*wh/size;
			int dia = rnd.nextInt(14, 22);
			stage.inFront(star(x, y, dia, rnd.nextInt(255), 0));
			if (playerStar == starID || star.has(HOME)) {
				stage.inFront(border(x-5, y-5, dia+10, dia+10));
				Rectangle area = new Rectangle(x-5, y-5, dia+10, dia+10);
				stage.in(area, Objects.focusBox(x-5, y-5, dia+10, dia+10));
				stage.onLeftClickIn(area, put(gamE.id(), SCREEN, SCREEN_SOLAR_SYSTEM), put(gamE.id(), SCREEN_ENTITY, starID));
			}
		}
	}

	private void randomGalaxy(State game, Dimension screen, Stage stage) {
		int w = screen.width;
		int h = screen.height;
		stage.inFront(background(0, 0, w, h, BG_SPACE));
		
		Entity gamE = game.single(GAME);
		
		Rnd rnd = new Rnd(56);
		
		int concentration = 100;
		int cr = 200-concentration;
		int systems = (w/cr)*(h/cr);
		
		for (int i = 0; i < systems; i++) {
			int x = rnd.nextInt(w);
			if (x -50 < 0) {
				x += 50;
			} else if (x + 50 > w) {
				x-=50;
			}
			int y = rnd.nextInt(h);
			if (y -50 < 0) {
				y += 50;
			} else if (y + 50 > h) {
				y-=50;
			}
			int d = rnd.nextInt(12,22);
			stage.inFront(star(x, y, d, rnd.nextInt(255), 0));
			int box = 2*d;
			Rectangle area = new Rectangle(x-d/2,y-d/2,box,box);
			stage.in(area, Objects.focusBox(x-d/2, y-d/2, box, box));
			stage.onLeftClickIn(area, put(gamE.id(), SCREEN, SCREEN_SOLAR_SYSTEM));
			
			// draw straight lines for all systems that can be reached for the currently selected fleet
			// draw dashed/dotted lines for all systems that can be reached given the current technology
		}
	}

}
