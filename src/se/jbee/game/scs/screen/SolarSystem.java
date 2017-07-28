package se.jbee.game.scs.screen;

import static java.awt.event.KeyEvent.VK_ESCAPE;
import static se.jbee.game.any.gfx.Texts.textKey;
import static se.jbee.game.any.state.Change.set;
import static se.jbee.game.any.state.Entity.codePoints;
import static se.jbee.game.scs.gfx.Draw.background;
import static se.jbee.game.scs.gfx.Draw.fixtext;
import static se.jbee.game.scs.gfx.Draw.focusBox;
import static se.jbee.game.scs.gfx.Draw.path;
import static se.jbee.game.scs.gfx.Draw.planet;
import static se.jbee.game.scs.gfx.Draw.starCut;
import static se.jbee.game.scs.gfx.Draw.text;
import static se.jbee.game.scs.gfx.Gfx.FontStyle.LIGHT;

import java.awt.Rectangle;

import se.jbee.game.any.gfx.Dimension;
import se.jbee.game.any.gfx.Stage;
import se.jbee.game.any.screen.Screen;
import se.jbee.game.any.screen.ScreenNo;
import se.jbee.game.any.state.Change;
import se.jbee.game.any.state.Entity;
import se.jbee.game.any.state.State;
import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.gfx.Hue;
import se.jbee.game.scs.logic.Turn;
import se.jbee.game.scs.state.GameComponent;

@ScreenNo(GameScreen.SCREEN_SOLAR_SYSTEM)
public class SolarSystem implements Screen, GameComponent, Gfx, GameScreen {

	@Override
	public void show(State game, Dimension screen, Stage stage) {
		Entity gamE = game.root();
		int starID = gamE.num(BASE_ENTITY);
		if (starID == 0) {
			//TODO jornal error
			return;
		}

		int gID = gamE.id();
		Change[] backToGalaxy = {
				set(gID, SCREEN, SCREEN_GALAXY),
				set(gID, BASE_ENTITY, game.single(GALAXY).id()) };
		stage.onKey(VK_ESCAPE, backToGalaxy);

		Entity player = Turn.currentPlayer(game);
		Entity star = game.entity(starID);

		int w = screen.width;
		int h = screen.height;
		stage.atFront(background(0,0,w, h, BG_SPACE, star.longNum(SEED)));
		int d = h*2;
		int size = star.num(SIZE);
		d = (int) (d / (20f/size));
		int r = d/2;
		int y = (screen.height-d)/2;

		Rectangle view = Viewport.centerView(screen);

		stage.atFront(path(PATH_EDGY, Hue.TEXT_NORMAL,1, w-150, view.y, w-10, view.y+140));
		stage.atFront(starCut(w-Math.min(r/2,200), y, d, star.num(RGB), star.longNum(SEED)));
		stage.onLeftClickIn(new Rectangle(w-r/8, 0, r/8, screen.height), backToGalaxy );
		stage.atFront(fixtext(0, 0, LIGHT, 32, Hue.TEXT_NORMAL, Align.SE, w-150, view.y, star.list(NAME)));
		Entity type = game.entity(star.num(STAR_CLASS));
		stage.atFront(text(textKey('S', 'k', type.num(CODE)), 0, view.y, LIGHT, 18, Hue.TEXT_NORMAL, Align.SE, w-150, view.y+30));

		int[] planets = star.list(PLANETS);
		int ym = screen.height /2;
		int x0 = screen.width/16;
		for (int i = 0; i < planets.length; i++) {
			Entity planet = game.entity(planets[i]);
			Entity cls = game.entity(planet.num(PLANET_CLASS));
			int dia = 6 + planet.num(SIZE) * 6;
			stage.atFront(planet(x0, ym-dia/2, dia, 0, planet.num(RGB)));
			stage.onLeftClickIn(new Rectangle(x0, ym-dia/2, dia, dia), 
					set(gID, SCREEN, SCREEN_COLONY), 
					set(gID, BASE_ENTITY, planet.id()));
			stage.atFront(text(textKey('P', 'n', cls.num(CODE)), x0,ym-dia/2-14, LIGHT, 14, Hue.TEXT_NORMAL));
			x0 += dia+screen.width/16;
		}
	}

}
