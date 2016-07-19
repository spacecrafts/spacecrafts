package se.jbee.game.scs.screen;

import static java.awt.event.KeyEvent.VK_ESCAPE;
import static se.jbee.game.any.gfx.Texts.textKey;
import static se.jbee.game.any.state.Change.set;
import static se.jbee.game.any.state.Entity.codePoints;
import static se.jbee.game.scs.gfx.GfxObjs.background;
import static se.jbee.game.scs.gfx.GfxObjs.flextext;
import static se.jbee.game.scs.gfx.GfxObjs.focusBox;
import static se.jbee.game.scs.gfx.GfxObjs.path;
import static se.jbee.game.scs.gfx.GfxObjs.planet;
import static se.jbee.game.scs.gfx.GfxObjs.starClip;
import static se.jbee.game.scs.gfx.GfxObjs.text;

import java.awt.Rectangle;

import se.jbee.game.any.gfx.Dimension;
import se.jbee.game.any.gfx.Stage;
import se.jbee.game.any.screen.Screen;
import se.jbee.game.any.screen.ScreenNo;
import se.jbee.game.any.state.Change;
import se.jbee.game.any.state.Entity;
import se.jbee.game.any.state.Rnd;
import se.jbee.game.any.state.State;
import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.process.Game;
import se.jbee.game.scs.state.GameComponent;

@ScreenNo(GameScreen.SCREEN_SOLAR_SYSTEM)
public class SolarSystem implements Screen, GameComponent, Gfx, GameScreen {

	@Override
	public void show(State user, State game, Dimension screen, Stage stage) {
		Entity gamE = game.single(GAME);
		int starID = gamE.num(BASE_ENTITY);
		if (starID == 0) {
			randomSolarSystem(game, screen, stage);
			return;
		}

		int gID = gamE.id();
		Change[] backToGalaxy = {
				set(gID, SCREEN, SCREEN_GALAXY),
				set(gID, BASE_ENTITY, game.single(GALAXY).id()) };
		stage.onKey(VK_ESCAPE, backToGalaxy);

		Entity player = Game.currentPlayer(game);
		Entity star = game.entity(starID);

		int w = screen.width;
		int h = screen.height;
		stage.atFront(background(0,0,w, h, BG_SPACE, star.list(SEED)));
		int d = h*2;
		int size = star.num(SIZE);
		d = (int) (d / (20f/size));
		int r = d/2;
		int y = (screen.height-d)/2;

		Rectangle view = Viewport.centerView(screen);

		stage.atFront(path(PATH_EDGY, COLOR_TEXT_NORMAL,1, w-150, view.y, w-10, view.y+140));
		stage.atFront(starClip(w-r/8, y, d, star.num(RGB)));
		stage.onLeftClickIn(new Rectangle(w-r/8, 0, r/8, screen.height), backToGalaxy );
		stage.atFront(text(1, 0, 0, FONT_LIGHT, 32, COLOR_TEXT_NORMAL, ALIGN_SE, w-150, view.y)).atFront(star.list(NAME));
		Entity type = game.entity(star.num(STAR_CLASS));
		stage.atFront(flextext(textKey('S', 'n', type.num(CODE)), 0, view.y, FONT_LIGHT, 18, COLOR_TEXT_NORMAL, ALIGN_SE, w-150, view.y+30));

		int[] planets = star.list(PLANETS);
		int ym = screen.height /2;
		int x0 = screen.width/16;
		for (int i = 0; i < planets.length; i++) {
			Entity planet = game.entity(planets[i]);
			Entity cls = game.entity(planet.num(PLANET_CLASS));
			int dia = 6 + planet.num(SIZE) * 6;
			stage.atFront(planet(x0, ym-dia/2, dia, 0, planet.num(RGB)));
			stage.atFront(flextext(textKey('P', 'n', cls.num(CODE)), x0,ym-dia/2-5, FONT_LIGHT, 14, COLOR_TEXT_NORMAL));
			x0 += dia+screen.width/16;
		}
	}

	private void randomSolarSystem(State game, Dimension screen, Stage stage) {
		Entity gamE = game.single(GAME);
		Rectangle area = new Rectangle(690, 390, 220, 220);
		Rnd rnd = new Rnd(42);

		int w = screen.width;
		int h = screen.height;
		stage.atFront(background(0,0,w, h, BG_SPACE, 34, 45));
		stage.atFront(starClip(w-h/8, -h/2, h*2, 0xFFFFFF00));

		stage.atFront(planet(700, 400, 200, 0, 0xFF5014));
		stage.atFront(planet(100, 300, 300, 0, 0x0000FF));

		stage.atFront(text(1, 690, 360, FONT_LIGHT, 24, COLOR_TEXT_NORMAL));
		stage.atFront(codePoints("Mars"));
		stage.atFront(text(1, 690, 380, FONT_LIGHT, 16, COLOR_TEXT_NORMAL));
		stage.atFront(codePoints("Small, Tundra"));

		stage.atFront(text(1, 90, 260, FONT_LIGHT, 24, COLOR_TEXT_NORMAL));
		stage.atFront(codePoints("Uranus"));
		stage.atFront(text(1, 90, 280, FONT_LIGHT, 16, COLOR_TEXT_NORMAL));
		stage.atFront(codePoints("Large, Toxic"));

		stage.atFront(text(1, 20, 20+48, FONT_THIN, 48, COLOR_TEXT_NORMAL));
		stage.atFront(codePoints("Solar System"));
		stage.atFront(text(1, 20, 20+48+28, FONT_LIGHT, 24, COLOR_TEXT_NORMAL));
		stage.atFront(codePoints("Type G"));

		stage.onLeftClickIn(area, set(gamE.id(), SCREEN, SCREEN_ORBIT));
		stage.in(area, focusBox(690, 390, 220, 220));

		area = new Rectangle(90, 290, 420, 420);
		stage.onLeftClickIn(area, set(gamE.id(), SCREEN, SCREEN_ORBIT));
		stage.in(area, focusBox(90, 290, 420, 420));
	}

}
