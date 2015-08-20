package se.jbee.game.scs.screen;

import static se.jbee.game.scs.gfx.Objects.background;
import static se.jbee.game.scs.gfx.Objects.border;
import static se.jbee.game.scs.gfx.Objects.planetClip;
import static se.jbee.game.scs.gfx.Objects.text;
import static se.jbee.game.scs.screen.GameScreen.SCREEN_SOLAR_SYSTEM;
import static se.jbee.game.scs.screen.View.cellDiameter;
import static se.jbee.game.uni.state.Change.put;
import static se.jbee.game.uni.state.Entity.codePoints;

import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.uni.gfx.Dimension;
import se.jbee.game.uni.gfx.Stage;
import se.jbee.game.uni.screen.Screen;
import se.jbee.game.uni.screen.ScreenNo;
import se.jbee.game.uni.state.Change;
import se.jbee.game.uni.state.State;

@ScreenNo(GameScreen.SCREEN_ORBIT)
public class Orbit implements Screen, Gfx, GameComponent {

	@Override
	public void show(State user, State game, Dimension screen, Stage stage) {

		int w = screen.width;
		int h = screen.height;
		stage.enter(background(0, 0, w, h, BG_SPACE));
		
		int d = h*2;
		int x = -d+h/8;
		int y = -h/2;
		stage.enter(planetClip(x, y, d, 0xFF5014, 0));

		int m = screen.width/3;
		d = cellDiameter(screen);
		int x0 = (m*2-(32*d))/2;
		int xs = x0;
		int y0 = (screen.height-32*d)/2;
		for (int i = 0; i < 32; i++) {
			for (int j = 0; j < 32; j++) {
				stage.enter(new int[] {OBJ_SLOT, x0, y0, d});
				stage.in(new Ellipse2D.Float(x0, y0, d, d), new int[] {OBJ_RESOURCE, x0, y0, d});
				x0 += d;
			}
			x0=xs;
			y0+=d;
		}
		
		stage.enter(text(w-m, w/32+48, FONT_THIN, 48, COLOR_TEXT_NORMAL, 1));
		stage.enter(codePoints("Mars"));
		stage.enter(text(w-m, w/32+48+28, FONT_LIGHT, 24, COLOR_TEXT_NORMAL, 1));
		stage.enter(codePoints("Orbit"));

		
		int hb = (h-w/8)/3;
		stage.enter(border(w-m, w/16+hb, m-w/32, hb));
		
		stage.enter(border(w-m, w/16+hb+hb+w/32, m-w/32, hb));

		Change gotoColony = put(game.single(GAME).id(), SCREEN, SCREEN_SOLAR_SYSTEM);
		stage.onLeftClickIn(new Rectangle(0, 0, h/8, screen.height), gotoColony);
		stage.onKey('c', gotoColony);
	}

}
