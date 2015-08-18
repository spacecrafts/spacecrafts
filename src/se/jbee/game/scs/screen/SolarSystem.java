package se.jbee.game.scs.screen;

import static se.jbee.game.common.state.Change.put;
import static se.jbee.game.common.state.Entity.codePoints;
import static se.jbee.game.scs.gfx.Objects.background;
import static se.jbee.game.scs.gfx.Objects.focusBox;
import static se.jbee.game.scs.gfx.Objects.planet;
import static se.jbee.game.scs.gfx.Objects.starClip;
import static se.jbee.game.scs.gfx.Objects.text;

import java.awt.Rectangle;

import se.jbee.game.common.gfx.Dimension;
import se.jbee.game.common.gfx.Stage;
import se.jbee.game.common.screen.Screen;
import se.jbee.game.common.screen.ScreenNo;
import se.jbee.game.common.state.Entity;
import se.jbee.game.common.state.State;
import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.state.GameComponent;

@ScreenNo(GameScreen.SCREEN_SOLAR_SYSTEM)
public class SolarSystem implements Screen, GameComponent, Gfx, GameScreen {

	@Override
	public void show(State user, State game, Dimension screen, Stage stage) {
		Entity gamE = game.single(GAME);
		Rectangle area = new Rectangle(690, 390, 220, 220);

		int w = screen.width;
		int h = screen.height;
		stage.enter(background(0,0,w, h, BG_SPACE));
		stage.enter(starClip(w-h/8, -h/2, h*2, 0xaaaa00, 0)); // g can be altered to increase or decrease red part of sun

		stage.enter(planet(700, 400, 200, 0xFF5014, 0));
		stage.enter(planet(100, 300, 400, 0x44FF99, 0));
		
		stage.enter(text(690, 360, FONT_LIGHT, 24, COLOR_TEXT_NORMAL, 1));
		stage.enter(codePoints("Mars"));
		stage.enter(text(690, 380, FONT_LIGHT, 16, COLOR_TEXT_NORMAL, 1));
		stage.enter(codePoints("Small, Tundra"));
		
		stage.enter(text(90, 260, FONT_LIGHT, 24, COLOR_TEXT_NORMAL, 1));
		stage.enter(codePoints("Uranus"));
		stage.enter(text(90, 280, FONT_LIGHT, 16, COLOR_TEXT_NORMAL, 1));
		stage.enter(codePoints("Large, Toxic"));
		
		stage.enter(text(20, 20+48, FONT_THIN, 48, COLOR_TEXT_NORMAL, 1));
		stage.enter(codePoints("Solar System"));
		stage.enter(text(20, 20+48+28, FONT_LIGHT, 24, COLOR_TEXT_NORMAL, 1));
		stage.enter(codePoints("Type G"));
		
		stage.onLeftClickIn(area, put(gamE.id(), SCREEN, SCREEN_ORBIT));
		stage.in(area, focusBox(690, 390, 220, 220));
		
		area = new Rectangle(90, 290, 420, 420);
		stage.onLeftClickIn(area, put(gamE.id(), SCREEN, SCREEN_ORBIT));
		stage.in(area, focusBox(90, 290, 420, 420));
		
	}

}
