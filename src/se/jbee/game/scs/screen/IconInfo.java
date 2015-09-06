package se.jbee.game.scs.screen;

import static se.jbee.game.scs.gfx.Objects.background;
import static se.jbee.game.scs.gfx.Objects.component;
import se.jbee.game.scs.gfx.Gfx;
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
		int[] types = {1,2,3,4,5,11,12,13,14,15,21,22,23,24,31,32,33};
		for (int s = 0; s <= 16; s++) {
			for (int type : types) {
				stage.inFront(component(type, x, y, d));
				y += d+d+d;
			}
			x += 3*d;
			y = 20;
			d += 1;
		}
	}

}
