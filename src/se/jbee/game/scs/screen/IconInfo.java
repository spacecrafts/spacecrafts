package se.jbee.game.scs.screen;

import static se.jbee.game.scs.gfx.GfxObjs.background;
import static se.jbee.game.scs.gfx.GfxObjs.icon;
import se.jbee.game.any.gfx.Dimension;
import se.jbee.game.any.gfx.Stage;
import se.jbee.game.any.screen.Screen;
import se.jbee.game.any.screen.ScreenNo;
import se.jbee.game.any.state.State;
import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.state.GameComponent;

@ScreenNo(GameScreen.SCREEN_ICON_INFO)
public class IconInfo implements Screen, Gfx, GameComponent {

	@Override
	public void show(State user, State game, Dimension screen, Stage stage) {
		stage.atFront(background(0, 0, screen.width, screen.height, BG_BLACK));

		int x = 100;
		int y = 20;
		int d = 8;
		int[] types = {ICON_BUILDING, ICON_BUILDING, ICON_BUILDING, ICON_BUILDING, ICON_BUILDING};
		int[] colors = {COLOR_ACADEMY, COLOR_BIOSPHERE, COLOR_FARM, COLOR_LAB, COLOR_YARD};
		for (int s = 0; s <= 16; s++) {
			for (int i = 0; i <  types.length; i++) {
				stage.atFront(icon(types[i], x, y, d, colors[i]));
				y += d+d+d;
			}
			x += 3*d;
			y = 20;
			d += 1;
		}
		y += 100;
		for (int i = 1; i < 50; i++) {
			stage.atFront(icon(i, x, y, d, COLOR_BIOSPHERE));
			x+=d+d;
			if (x > screen.width) {
				x= 0;
				y+=d+d;
			}
		}
	}
}
