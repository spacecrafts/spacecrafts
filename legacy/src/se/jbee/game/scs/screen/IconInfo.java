package se.jbee.game.scs.screen;

import static se.jbee.game.scs.gfx.Draw.background;
import static se.jbee.game.scs.gfx.Draw.icon;
import static se.jbee.game.scs.gfx.Hue.ACADEMY;
import static se.jbee.game.scs.gfx.Hue.BIOSPHERE;
import static se.jbee.game.scs.gfx.Hue.FARM;
import static se.jbee.game.scs.gfx.Hue.LAB;
import static se.jbee.game.scs.gfx.Hue.YARD;
import se.jbee.game.any.gfx.Dimension;
import se.jbee.game.any.gfx.Stage;
import se.jbee.game.any.screen.Screen;
import se.jbee.game.any.screen.ScreenNo;
import se.jbee.game.any.state.State;
import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.gfx.Hue;
import se.jbee.game.scs.state.GameComponent;

@ScreenNo(GameScreen.SCREEN_ICON_INFO)
public class IconInfo implements Screen, Gfx, GameComponent {

	@Override
	public void show(State game, Dimension screen, Stage stage) {
		stage.atFront(background(0, 0, screen.width, screen.height, BG_BLACK, 0L));

		int x = 100;
		int y = 100;
		int d = 8;
		int[] types = {ICON_BUILDING, ICON_BUILDING, ICON_BUILDING, ICON_BUILDING, ICON_BUILDING};
		Hue[] colors = {ACADEMY, BIOSPHERE, FARM, LAB, YARD};
		if (false) {
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
		}
		d = 16;
		for (int i = 1; i < 50; i++) {
			stage.atFront(icon(i, x, y, d, LAB));
			x+=d+d;
			if (x > screen.width) {
				x= 0;
				y+=d+d;
			}
		}
	}
}
