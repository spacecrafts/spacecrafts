package se.jbee.game.scs.screen;

import static se.jbee.game.scs.gfx.GfxObjs.ring;
import static se.jbee.game.scs.gfx.GfxObjs.techwheel;

import java.awt.Rectangle;

import se.jbee.game.any.gfx.Dimension;
import se.jbee.game.any.gfx.Point;
import se.jbee.game.any.gfx.Stage;
import se.jbee.game.any.screen.Screen;
import se.jbee.game.any.screen.ScreenNo;
import se.jbee.game.any.state.Change;
import se.jbee.game.any.state.Entity;
import se.jbee.game.any.state.State;
import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.gfx.GfxObjs;
import se.jbee.game.scs.gfx.obj.Techwheel;
import se.jbee.game.scs.state.GameComponent;

@ScreenNo(GameScreen.SCREEN_TECH_WHEEL)
public class TechWheelScreen implements Screen, Gfx, GameComponent {

	@Override
	public void show(State game, Dimension screen, Stage stage) {

		stage.atFront(GfxObjs.background(0, 0, screen.width, screen.height, BG_BLACK, 0));
		stage.atFront(techwheel(400, 400, 800, COLOR_TEXT_NORMAL));
		stage.atFront(techwheel(900, 400, 200, COLOR_TEXT_NORMAL));

		Entity gamE = game.root();
		for (int sec = 0; sec < 8; sec++) {
			for (int lev = 1; lev <= 5; lev++) {
				for (int i = 0; i < lev; i++) {
					Point c = Techwheel.centerOfWheelPosition(400, 400, 800, sec, lev, i);
					stage.atFront(ring(c.x, c.y, 50, 3, COLOR_YARD));
					Rectangle area = new Rectangle(c.x-25, c.y-5, 50, 50);
					stage.in(area, ring(c.x, c.y, 50, 3, COLOR_ENERGY));
					stage.onLeftClickIn(area, Change.set(gamE.id(), SCREEN, GameScreen.SCREEN_MENU));
				}
			}
		}
	}

}
