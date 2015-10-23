package se.jbee.game.scs.screen;

import static java.awt.event.KeyEvent.VK_ESCAPE;
import static se.jbee.game.any.state.Change.set;
import static se.jbee.game.any.state.Entity.codePoints;
import static se.jbee.game.scs.gfx.Objects.background;
import static se.jbee.game.scs.gfx.Objects.focusBox;
import static se.jbee.game.scs.gfx.Objects.path;
import static se.jbee.game.scs.gfx.Objects.planet;
import static se.jbee.game.scs.gfx.Objects.starClip;
import static se.jbee.game.scs.gfx.Objects.text;

import java.awt.Rectangle;

import se.jbee.game.any.gfx.Dimension;
import se.jbee.game.any.gfx.Stage;
import se.jbee.game.any.screen.Screen;
import se.jbee.game.any.screen.ScreenNo;
import se.jbee.game.any.state.Change;
import se.jbee.game.any.state.Entity;
import se.jbee.game.any.state.Rnd;
import se.jbee.game.any.state.State;
import se.jbee.game.any.state.Texts;
import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.process.Game;
import se.jbee.game.scs.state.GameComponent;
import data.Data;

@ScreenNo(GameScreen.SCREEN_SOLAR_SYSTEM)
public class SolarSystem implements Screen, GameComponent, Gfx, GameScreen {

	@Override
	public void show(State user, State game, Dimension screen, Stage stage) {
		Entity gamE = game.single(GAME);
		int starID = gamE.num(SCREEN_ENTITY);
		if (starID == 0) {
			randomSolarSystem(game, screen, stage);
			return;
		}

		int gID = gamE.id();
		Change[] backToGalaxy = {
				set(gID, SCREEN, SCREEN_GALAXY),
				set(gID, SCREEN_ENTITY, game.single(GALAXY).id()) };
		stage.onKey(VK_ESCAPE, backToGalaxy);

		Entity player = Game.currentPlayer(game);
		Entity star = game.entity(starID);

		int w = screen.width;
		int h = screen.height;
		stage.inFront(background(0,0,w, h, BG_SPACE, star.list(SEED)));
		int d = h*2;
		int size = star.num(SIZE);
		d = (int) (d / (20f/size));
		int r = d/2;
		int y = (screen.height-d)/2;

		Rectangle view = Viewport.centerView(screen);

		stage.inFront(path(PATH_EDGY, COLOR_TEXT_NORMAL,1, w-150, view.y, w-10, view.y+140));
		stage.inFront(starClip(w-r/8, y, d, star.num(RGBA)));
		stage.onLeftClickIn(new Rectangle(w-r/8, 0, r/8, screen.height), backToGalaxy );
		stage.inFront(text(1, 0, 0, FONT_LIGHT, 32, COLOR_TEXT_NORMAL, ALIGN_SE, w-150, view.y)).inFront(star.list(NAME));
		Texts texts = new Texts();
		texts.index(Data.class, "star-class.texts");
		Entity type = game.entity(star.num(STAR_CLASS));
		stage.inFront(text(1, 0, view.y, FONT_LIGHT, 18, COLOR_TEXT_NORMAL, ALIGN_SE, w-150, view.y+30)).inFront(codePoints(texts.lookup(Texts.encode('S', 'n', type.num(CODE)))));

		int[] planets = star.list(PLANETS);
		int ym = screen.height /2;
		int x0 = screen.width/16;
		for (int i = 0; i < planets.length; i++) {
			Entity planet = game.entity(planets[i]);
			int dia = 200;
			stage.inFront(planet(x0, ym-dia/2, dia, 0x45852c, 0));
			x0 += dia+screen.width/16;
		}
	}

	private void randomSolarSystem(State game, Dimension screen, Stage stage) {
		Entity gamE = game.single(GAME);
		Rectangle area = new Rectangle(690, 390, 220, 220);
		Rnd rnd = new Rnd(42);

		int w = screen.width;
		int h = screen.height;
		stage.inFront(background(0,0,w, h, BG_SPACE, 34, 45));
		stage.inFront(starClip(w-h/8, -h/2, h*2, 0xFFFFFF00));

		stage.inFront(planet(700, 400, 200, 0, 0xFF5014));
		stage.inFront(planet(100, 300, 300, 0, 0x0000FF));

		stage.inFront(text(1, 690, 360, FONT_LIGHT, 24, COLOR_TEXT_NORMAL));
		stage.inFront(codePoints("Mars"));
		stage.inFront(text(1, 690, 380, FONT_LIGHT, 16, COLOR_TEXT_NORMAL));
		stage.inFront(codePoints("Small, Tundra"));

		stage.inFront(text(1, 90, 260, FONT_LIGHT, 24, COLOR_TEXT_NORMAL));
		stage.inFront(codePoints("Uranus"));
		stage.inFront(text(1, 90, 280, FONT_LIGHT, 16, COLOR_TEXT_NORMAL));
		stage.inFront(codePoints("Large, Toxic"));

		stage.inFront(text(1, 20, 20+48, FONT_THIN, 48, COLOR_TEXT_NORMAL));
		stage.inFront(codePoints("Solar System"));
		stage.inFront(text(1, 20, 20+48+28, FONT_LIGHT, 24, COLOR_TEXT_NORMAL));
		stage.inFront(codePoints("Type G"));

		stage.onLeftClickIn(area, set(gamE.id(), SCREEN, SCREEN_ORBIT));
		stage.in(area, focusBox(690, 390, 220, 220));

		area = new Rectangle(90, 290, 420, 420);
		stage.onLeftClickIn(area, set(gamE.id(), SCREEN, SCREEN_ORBIT));
		stage.in(area, focusBox(90, 290, 420, 420));
	}

}
