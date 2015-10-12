package se.jbee.game.scs.screen;

import static se.jbee.game.scs.gfx.Objects.background;
import static se.jbee.game.scs.gfx.Objects.focusBox;
import static se.jbee.game.scs.gfx.Objects.planet;
import static se.jbee.game.scs.gfx.Objects.starClip;
import static se.jbee.game.scs.gfx.Objects.text;
import static se.jbee.game.uni.state.Change.put;
import static se.jbee.game.uni.state.Entity.codePoints;

import java.awt.Rectangle;

import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.process.Game;
import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.uni.gfx.Dimension;
import se.jbee.game.uni.gfx.Stage;
import se.jbee.game.uni.screen.Screen;
import se.jbee.game.uni.screen.ScreenNo;
import se.jbee.game.uni.state.Entity;
import se.jbee.game.uni.state.State;

@ScreenNo(GameScreen.SCREEN_SOLAR_SYSTEM)
public class SolarSystem implements Screen, GameComponent, Gfx, GameScreen {

	@Override
	public void show(State user, State game, Dimension screen, Stage stage) {
		Entity gamE = game.single(GAME);

		Entity player = Game.currentPlayer(game);
		Entity star = game.entity(gamE.num(SCREEN_ENTITY));

		int w = screen.width;
		int h = screen.height;
		stage.inFront(background(0,0,w, h, BG_SPACE, star.list(SEED)));
		stage.inFront(starClip(w-h/8, -h/2, h*2, 0xaaaa00, 0)); // g can be altered to increase or decrease red part of sun

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

		int w = screen.width;
		int h = screen.height;
		stage.inFront(background(0,0,w, h, BG_SPACE));
		stage.inFront(starClip(w-h/8, -h/2, h*2, 0xaaaa00, 0)); // g can be altered to increase or decrease red part of sun

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
