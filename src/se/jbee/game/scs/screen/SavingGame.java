package se.jbee.game.scs.screen;

import static se.jbee.game.common.state.Entity.codePoints;
import static se.jbee.game.scs.gfx.Objects.background;
import static se.jbee.game.scs.gfx.Objects.border;
import static se.jbee.game.scs.gfx.Objects.text;

import java.awt.Dimension;

import se.jbee.game.common.process.Scene;
import se.jbee.game.common.screen.Screen;
import se.jbee.game.common.state.Entity;
import se.jbee.game.common.state.State;
import se.jbee.game.scs.gfx.Colors;
import se.jbee.game.scs.gfx.Fonts;
import se.jbee.game.scs.state.GameComponent;

public class SavingGame implements Screen, GameComponent {

	@Override
	public void show(State user, State game, Dimension screen, Scene scene) {
		int w = screen.width/2;
		int h = screen.height/2;
		int x0 = screen.width/4;
		int y0 = screen.height/4;
		
		Entity game1 = game.single(GAME);

		scene.place(background(x0-1, y0-1, w+3, h+3, 2));
		scene.place(border(x0, y0, w, h));
		scene.place(text(x0+20, y0+h/2, Fonts.TYPE_LIGHT, 48, Colors.HIGHLIGHT_TEXT, 3));
		scene.place(codePoints("saving"));
		scene.place(game1.list(SAVEGAME));
		scene.place(codePoints("..."));
		
		// setup return to screen, done after action
		game1.put(SCREEN, game1.num(RETURN_SCREEN));
		game1.erase(RETURN_SCREEN);
		game1.put(ACTION, 1);
	}

}
