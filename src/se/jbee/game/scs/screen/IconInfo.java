package se.jbee.game.scs.screen;

import static se.jbee.game.scs.gfx.Objects.background;
import static se.jbee.game.scs.gfx.Objects.icon;
import static se.jbee.game.scs.gfx.Objects.knob;
import static se.jbee.game.scs.gfx.Objects.ring;
import static se.jbee.game.scs.gfx.Objects.techwheel;
import static se.jbee.game.uni.state.Entity.codePoints;

import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.gfx.Objects;
import se.jbee.game.scs.gfx.obj.Techwheel;
import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.uni.gfx.Dimension;
import se.jbee.game.uni.gfx.Point;
import se.jbee.game.uni.gfx.Stage;
import se.jbee.game.uni.screen.Screen;
import se.jbee.game.uni.screen.ScreenNo;
import se.jbee.game.uni.state.Change;
import se.jbee.game.uni.state.Entity;
import se.jbee.game.uni.state.State;

@ScreenNo(GameScreen.SCREEN_ICON_INFO)
public class IconInfo implements Screen, Gfx, GameComponent {

	@Override
	public void show(State user, State game, Dimension screen, Stage stage) {
		stage.inFront(background(0, 0, screen.width, screen.height, BG_BLACK));

		int x = 100;
		int y = 20;
		int d = 8;
		int[] types = {ICON_BUILDING, ICON_BUILDING, ICON_BUILDING, ICON_BUILDING, ICON_BUILDING};
		int[] colors = {COLOR_ACADEMY, COLOR_BIOSPHERE, COLOR_FARM, COLOR_LAB, COLOR_YARD};
		for (int s = 0; s <= 16; s++) {
			for (int i = 0; i <  types.length; i++) {
				stage.inFront(icon(types[i], x, y, d, colors[i]));
				y += d+d+d;
			}
			x += 3*d;
			y = 20;
			d += 1;
		}
		stage.inFront(knob(300, 300, 100, COLOR_BIOSPHERE, COLOR_BLACK, 1));
		stage.inFront(codePoints("1%"));
		stage.in(new Ellipse2D.Float(300f, 300f, 100f, 100f), Cursor.HAND_CURSOR, Objects.border(300, 300, 100, 100));

		stage.inFront(techwheel(400, 400, 800, COLOR_TEXT_NORMAL));
		stage.inFront(techwheel(900, 400, 200, COLOR_TEXT_NORMAL));

		Entity gamE = game.single(GAME);
		for (int sec = 0; sec < 8; sec++) {
			for (int lev = 1; lev <= 5; lev++) {
				for (int i = 0; i < lev; i++) {
					Point c = Techwheel.centerOfWheelPosition(400, 400, 800, sec, lev, i);
					stage.inFront(ring(c.x, c.y, 50, 3, COLOR_YARD));
					Rectangle area = new Rectangle(c.x-25, c.y-5, 50, 50);
					stage.in(area, ring(c.x, c.y, 50, 3, COLOR_ENERGY));
					stage.onLeftClickIn(area, Change.put(gamE.id(), SCREEN, GameScreen.SCREEN_MAIN));
				}
			}
		}
	}
}
