package se.jbee.game.scs.screen;

import static se.jbee.game.any.state.Change.set;
import static se.jbee.game.scs.gfx.Draw.background;
import static se.jbee.game.scs.gfx.Draw.border;
import static se.jbee.game.scs.gfx.Draw.icon;
import static se.jbee.game.scs.gfx.Draw.label;
import static se.jbee.game.scs.gfx.Draw.planetCut;
import static se.jbee.game.scs.gfx.Gfx.FontStyle.LIGHT;
import static se.jbee.game.scs.gfx.Gfx.FontStyle.THIN;
import static se.jbee.game.scs.gfx.Hue.EMPTY_SLOT;
import static se.jbee.game.scs.gfx.Hue.TEXT_HIGHLIGHT;
import static se.jbee.game.scs.gfx.Hue.TEXT_NORMAL;
import static se.jbee.game.scs.screen.GameScreen.SCREEN_SOLAR_SYSTEM;
import static se.jbee.game.scs.screen.Viewport.cellDiameter;

import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

import se.jbee.game.any.gfx.Dimension;
import se.jbee.game.any.gfx.Stage;
import se.jbee.game.any.screen.Screen;
import se.jbee.game.any.screen.ScreenNo;
import se.jbee.game.any.state.Change;
import se.jbee.game.any.state.State;
import se.jbee.game.scs.gfx.Draw;
import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.state.GameComponent;

@ScreenNo(GameScreen.SCREEN_ORBIT)
public class Orbit implements Screen, Gfx, GameComponent {

	@Override
	public void show(State game, Dimension screen, Stage stage) {

		int w = screen.width;
		int h = screen.height;
		stage.atFront(background(0, 0, w, h, BG_SPACE, 42L)); //TODO use seed from planet

		int d = h*2;
		int x = -d+h/8;
		int y = -h/2;
		stage.atFront(planetCut(x, y, d, 0xFF5014, 0));

		int m = screen.width/3;
		d = cellDiameter(screen);
		int x0 = (m*2-(32*d))/2;
		int xs = x0;
		int y0 = (screen.height-32*d)/2;
		for (int i = 0; i < 32; i++) {
			for (int j = 0; j < 32; j++) {
				stage.atFront(icon(ICON_SLOT, x0, y0, d, EMPTY_SLOT));
				stage.in(new Ellipse2D.Float(x0, y0, d, d), icon(ICON_SLOT, x0, y0, d, TEXT_HIGHLIGHT));
				x0 += d;
			}
			x0=xs;
			y0+=d;
		}

		stage.atFront(label(w-m, w/32+48, THIN, 48, TEXT_NORMAL, "Mars"));
		stage.atFront(label(w-m, w/32+48+28, LIGHT, 24, TEXT_NORMAL, "Orbit"));

		int hb = (h-w/8)/3;
		stage.atFront(border(w-m, w/16+hb, m-w/32, hb));

		stage.atFront(border(w-m, w/16+hb+hb+w/32, m-w/32, hb));

		Change gotoColony = set(State.ROOT, SCREEN, SCREEN_SOLAR_SYSTEM);
		stage.onLeftClickIn(new Rectangle(0, 0, h/8, screen.height), gotoColony);
		stage.onKey('c', gotoColony);
	}

}
