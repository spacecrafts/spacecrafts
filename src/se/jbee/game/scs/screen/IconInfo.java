package se.jbee.game.scs.screen;

import static se.jbee.game.scs.gfx.Objects.background;
import static se.jbee.game.scs.gfx.Objects.icon;
import static se.jbee.game.scs.gfx.Objects.knob;
import static se.jbee.game.scs.gfx.Objects.techwheel;
import static se.jbee.game.uni.state.Entity.codePoints;

import java.awt.Cursor;
import java.awt.geom.Ellipse2D;

import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.gfx.Objects;
import se.jbee.game.uni.gfx.Dimension;
import se.jbee.game.uni.gfx.Stage;
import se.jbee.game.uni.screen.Screen;
import se.jbee.game.uni.screen.ScreenNo;
import se.jbee.game.uni.state.State;

@ScreenNo(GameScreen.SCREEN_ICON_INFO)
public class IconInfo implements Screen, Gfx {

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
		
		int r = 800/12;
		int xa = 400;
		int ya = 400;
		int d1 = r/2+r/12;
		int d2 = r+r/2-r/8;
		stage.inFront(Objects.ring(xa+d1, ya+d2, 50, 3, COLOR_ACADEMY, COLOR_BLACK));
		stage.inFront(Objects.ring(xa-d1, ya+d2, 50, 3, COLOR_ACADEMY, COLOR_BLACK));
		stage.inFront(Objects.ring(xa-d1, ya-d2, 50, 3, COLOR_ACADEMY, COLOR_BLACK));
		stage.inFront(Objects.ring(xa-d2, ya-d1, 50, 3, COLOR_ACADEMY, COLOR_BLACK));
		stage.inFront(Objects.ring(xa+d2, ya-d1, 50, 3, COLOR_ACADEMY, COLOR_BLACK));
		
	}

}
