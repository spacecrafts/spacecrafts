package se.jbee.game.scs.screen;

import static java.awt.event.KeyEvent.VK_ESCAPE;
import static se.jbee.game.any.state.Change.put;
import static se.jbee.game.any.state.Entity.codePoints;
import static se.jbee.game.scs.gfx.Objects.background;
import static se.jbee.game.scs.gfx.Objects.focusBox;
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
import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.gfx.Objects;
import se.jbee.game.scs.process.Game;
import se.jbee.game.scs.state.GameComponent;

@ScreenNo(GameScreen.SCREEN_SOLAR_SYSTEM)
public class SolarSystem implements Screen, GameComponent, Gfx, GameScreen {

	@Override
	public void show(State user, State game, Dimension screen, Stage stage) {
		Entity gamE = game.single(GAME);

		int gID = gamE.id();
		Change[] backToGalaxy = { 
				put(gID, SCREEN, SCREEN_GALAXY),
				put(gID, SCREEN_ENTITY, game.single(GALAXY).id()) };
		stage.onKey(VK_ESCAPE, backToGalaxy);
		
		Entity player = Game.currentPlayer(game);
		Entity star = game.entity(gamE.num(SCREEN_ENTITY));

		int w = screen.width;
		int h = screen.height;
		stage.inFront(background(0,0,w, h, BG_SPACE, star.list(SEED)));
		int d = h*2;
		int size = star.num(SIZE);
		d = (int) (d / (12f/size));
		int r = d/2;
		int y = (screen.height-d)/2;
		
		Rectangle view = Viewport.centerView(screen);
		
		stage.inFront(Objects.path(PATH_EDGY, COLOR_TEXT_NORMAL,1, w-150, view.y, w-10, view.y+140));
		stage.inFront(starClip(w-r/8, y, d, star.num(RGBA)));
		stage.onLeftClickIn(new Rectangle(w-r/8, 0, r/8, screen.height), backToGalaxy );
		stage.inFront(text(1, 0, 0, FONT_THIN, 36, COLOR_TEXT_NORMAL, ALIGN_SE, w-150, view.y)).inFront(star.list(NAME));
		
		int[] planets = star.list(PLANETS);
		int ym = screen.height /2;
		int x0 = screen.width/16;
		for (int i = 0; i < planets.length; i++) {
			Entity planet = game.entity(planets[i]);
			int dia = 200;
			stage.inFront(planet(x0, ym-dia/2, dia, 0xFF5014, 0));
			x0 += dia+screen.width/16;
		}
	}

	private void randomSolarSystem(State game, Dimension screen, Stage stage) {
		Entity gamE = game.single(GAME);
		Rectangle area = new Rectangle(690, 390, 220, 220);
		Rnd rnd = new Rnd(42);

		int w = screen.width;
		int h = screen.height;
		stage.inFront(background(0,0,w, h, BG_SPACE));
		stage.inFront(starClip(w-h/8, -h/2, h*2, rnd.nextInt(0, 255))); // g can be altered to increase or decrease red part of sun

		stage.inFront(planet(700, 400, 200, 0xFF5014, 0));
		stage.inFront(planet(100, 300, 400, 0x44FF99, 0));

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

		stage.onLeftClickIn(area, put(gamE.id(), SCREEN, SCREEN_ORBIT));
		stage.in(area, focusBox(690, 390, 220, 220));

		area = new Rectangle(90, 290, 420, 420);
		stage.onLeftClickIn(area, put(gamE.id(), SCREEN, SCREEN_ORBIT));
		stage.in(area, focusBox(90, 290, 420, 420));
	}

}
