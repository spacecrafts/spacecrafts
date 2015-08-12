package se.jbee.game.scs.screen;

import static se.jbee.game.common.state.Entity.codePoints;
import static se.jbee.game.scs.gfx.Objects.background;
import static se.jbee.game.scs.gfx.Objects.border;
import static se.jbee.game.scs.gfx.Objects.focusBox;
import static se.jbee.game.scs.gfx.Objects.text;

import java.awt.Dimension;
import java.awt.Rectangle;

import se.jbee.game.common.process.Scene;
import se.jbee.game.common.screen.Screen;
import se.jbee.game.common.state.Change;
import se.jbee.game.common.state.Change.Op;
import se.jbee.game.common.state.Entity;
import se.jbee.game.common.state.State;
import se.jbee.game.scs.gfx.Colors;
import se.jbee.game.scs.gfx.Fonts;
import se.jbee.game.scs.state.GameComponent;

public class SplashScreen implements Screen, GameComponent {

	@Override
	public void show(State user, State game, Dimension screen, Scene scene) {
		Entity g1 = game.single(GAME);
		
		scene.place(background(0,0, screen.width, screen.height, 1));
		scene.place(text(450, screen.height/2-128, Fonts.TYPE_CAPS, 128, Colors.HIGHLIGHT_TEXT, 1));
		scene.place(codePoints("SPACECRAFTS"));
		
		int w = 200;
		int h = 40;
		int x0 = (screen.width-w)/2;
		int y0 = screen.height/2;
		scene.place(border(x0, y0, w, h));
		Rectangle save = new Rectangle(x0,y0,w,h);
		scene.bind(save, focusBox(x0-3, y0-3, w+6, h+6));
		scene.place(text(x0+20, y0+28, Fonts.TYPE_REGULAR, 24, Colors.NORMAL_TEXT, 1));
		scene.place(codePoints("SAVE GAME"));
		
		scene.bindLeftClick(save, new Change(g1.id(), SCREEN, Op.PUT, 1), new Change(g1.id(), RETURN_SCREEN, Op.PUT, 0));
	}

}
